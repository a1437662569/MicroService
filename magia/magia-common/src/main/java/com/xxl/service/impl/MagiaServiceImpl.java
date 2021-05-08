package com.xxl.service.impl;

import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.*;
import com.xxl.config.MaigaShareNacosConfiguration;
import com.xxl.entity.*;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.po.MagiaMacPushListPo;
import com.xxl.entity.vo.*;
import com.xxl.mapper.master.WAccountMapper;
import com.xxl.mapper.read.*;
import com.xxl.service.*;
import com.xxl.util.HttpRequestUtil;
import com.xxl.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("magiaService")
public class MagiaServiceImpl implements IMagiaService {

    private GeoipUtil geoipUtil = new GeoipUtil();

    @Autowired
    private MaigaShareNacosConfiguration maigaShareNacosConfiguration;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RAccountMapper rAccountMapper;

    @Autowired
    private WAccountMapper wAccountMapper;


    @Autowired
    RedisService redisService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public R getEmailVerifyCodeService(GetEmailVerifyCodeReq getEmailVerifyCodeReq) {
        R result = R.ok();
        try {
            // 统一标准，需要用到的参数必须首先获取到
            @NotBlank String email = getEmailVerifyCodeReq.getEmail();
            @NotBlank String type = getEmailVerifyCodeReq.getType();
            String lang = getEmailVerifyCodeReq.getLang();

            // 需要将邮箱统一转换为小写，应该有自动转换为小写的注解，但是没有找到，所以这里手动处理一下
            email = email.toLowerCase();

            /**
             * 需求描述：
             * 1、若填写的邮箱号已注册，则提示：邮箱已注册；
             * 2、若填写的邮箱号未注册且无效时，则提示：邮箱不存在
             * 3、若填写的邮箱号未注册且有效时，则校验通过，在验证码输入框下方提示：验证码已发送至邮箱(123214***@gmail.com)，请注意查收！
             * 4、相同邮箱获取验证码次数达到当日上限（默认：50次），再次点击获取验证码，验证码输入框下方提示：今日发送邮件次数已达上限！
             * 5、【获取验证码】按钮变为【180S】倒计时，此时按钮置灰不可点击；当180S倒计时结束后若用户未离开此页面，按钮变为【重新获取】；
             */

            // 判断邮箱的后缀是否符合要求，如果不符合指定的邮箱格式，则返回错误码：21 email格式错误
            String emailSuffix = maigaShareNacosConfiguration.getEmailSuffix();
            String[] emailSuffixArray = emailSuffix.split(CommonConsts.COMMA);
            long count = Arrays.stream(emailSuffixArray).filter(email::endsWith).count();
            if (count == CommonConsts.NUMBER_ZERO) {
                return R.EMAIL_SUFFIX_ERROR();
            }

            // 判断邮箱是否有效
            boolean isEmailFlag = StringUtil.isEmail(email);
            if (!isEmailFlag) {
                return R.EMAIL_FORMAT_ERROR();
            }

            // 这里是针对gmail邮箱的特殊处理
            email = StringUtil.emailChangeFun(email);

            // 判断邮箱是否已注册，这里直接查询数据库即可
            Account account = redisService.accountSelectByEmailFromRedis(email);
            // 如果当前是要注册，且当前的邮箱已经注册过了，那么返回错误码
            if (null != account && CommonConsts.NUMBER_ONE_STR.equals(type)) {
                return R.EMAIL_REGISTERED_ERROR();
            }

            // 生成验证码
            // 获取到每天能发送的邮件的最大次数
            Integer sendTotalCount = maigaShareNacosConfiguration.getSendTotalCount();
            // 判断当前已经发送了多少次了 按约定从redis里面获取到
            String emailSendCountKey = String.format(RedisEnum.REDIS_EMAIL_SEND_TOTLE_COUNT_$EMAIL.getKey(), email);
            // 获取到这个邮箱已经发送的邮件的封数
            String hasSendCount = redisUtil.get(emailSendCountKey);
            // 如果redis里面确实缓存了这个数据，那么需要对比一下
            if (!StringUtil.isEmpty(hasSendCount)) {
                // 如果配置的每天发送的最大量，大于了已经发送的量。则返回错误码
                if (sendTotalCount <= Integer.parseInt(hasSendCount)) {
                    return R.SEND_EMAIL_LIMIT_ERROR();
                }
            }

            // 发送了验证码后，180s之内不能再次发送 思路：发送邮件成功后，往REDIS_EMAIL_SEND_180_SECOND_LIMIT_$EMAIL
            // 里面的值设置一个1，且这个时效性为180s
            String sendTimeLimit = String.format(RedisEnum.REDIS_EMAIL_SEND_180_SECOND_LIMIT_$EMAIL.getKey(), email);
            String sendTimeLimitValue = redisUtil.get(sendTimeLimit);
            if (CommonConsts.NUMBER_ONE_STR.equals(sendTimeLimitValue)) {
                return R.SEND_EMAIL_180S_LIMIT_ERROR();
            }

            // 开始生成验证码，注意，如果验证码发送了以后，且验证码没有被使用掉，那么后续一直发送这个验证码
            String randomVerifiCode = null;
            String verifiCodeKey = String.format(RedisEnum.REDIS_EMAIL_VERIFICODET_$EMAIL_$TYPE.getKey(), email, type);
            String beforeVerifiCode = redisUtil.get(verifiCodeKey);
            // 如果验证码是存在的，那么直接用老的验证码
            if (!StringUtil.isEmpty(beforeVerifiCode)) {
                randomVerifiCode = beforeVerifiCode;
            }
            // 如果验证码是不存在的，那么就需要生成验证码了
            else {
                // 获取验证码在redis中保存的时长，这里默认是10分钟
                Integer verifiCodeRedisTime = maigaShareNacosConfiguration.getVerifiCodeRedisTime();
                // 生成验证码
                randomVerifiCode = StringUtil.getRandomVerifiCode();
                // 将这个验证码保存到redis中，并设置有效时长
                redisUtil.setEx(verifiCodeKey, randomVerifiCode, verifiCodeRedisTime, TimeUnit.MINUTES);
            }

            // 验证码开始调用noticeCore进行发送
            String emailtempnoticeUrl = maigaShareNacosConfiguration.getEmailtempnoticeUrl();
            if (StringUtil.isEmpty(emailtempnoticeUrl)) {
                log.error("未配置properties.url.noticeCore.emailtempnotice_url的值");
                return R.error();
            }

            // 如果获取连接正常，那么组装参数发送http请求
            Map<String, String> emailParamMap = new HashMap<>();
            // 模板里面暂时只需要展示邮箱和验证码，所以暂时只传输这两个
            emailParamMap.put("$email", email);
            emailParamMap.put("$verifyCode", randomVerifiCode);

            EmailTempNoticeVo emailTempNoticeVo = new EmailTempNoticeVo();
            emailTempNoticeVo.setTemplateType(lang);
            emailTempNoticeVo.setObjJson(emailParamMap);
            emailTempNoticeVo.setCustomerEmail(email);

            // 组装source参数和邮件主题subject
            switch (type) {
                case CommonConsts.NUMBER_ONE_STR:
                    emailTempNoticeVo.setSource(CommonConsts.MAGIA_REGISTER);
                    switch (lang) {
                        case CommonConsts.EN:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectRegisterEn());
                            break;

                        case CommonConsts.ES:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectRegisterEs());
                            break;

                        case CommonConsts.PT:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectRegisterPt());
                            break;
                        default:
                            break;
                    }
                    break;
                case CommonConsts.NUMBER_TWO_STR:
                    emailTempNoticeVo.setSource(CommonConsts.MAGIA_FORGETPASSWORD);
                    switch (lang) {
                        case CommonConsts.EN:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectForgetpasswordEn());
                            break;

                        case CommonConsts.ES:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectForgetpasswordEs());
                            break;

                        case CommonConsts.PT:
                            emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectForgetpasswordPt());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }

            // 发送邮件
            String data = HttpRequestUtil.getInstance().executePostByStream(emailtempnoticeUrl, JsonUtil.writeObject2JSON(emailTempNoticeVo));
            if (StringUtil.isEmpty(data)) {
                return R.error();
            }
            // 将noticeCore的返回结果最终返回
            result = (R) JsonUtil.writeJSON2Object(data, R.class);

            // 如果验证码发送成功，为了180s不能重发发送，需要做一个标记
            if (CommonConsts.NUMBER_ZERO_STR.equals(result.getReturnCode())) {
                redisUtil.setEx(sendTimeLimit, CommonConsts.NUMBER_ONE_STR, 180, TimeUnit.SECONDS);
            }

            // 还需要给当前用户的发送次数增加1
            if (!StringUtil.isEmpty(hasSendCount)) {
                redisUtil.incrBy(emailSendCountKey, CommonConsts.NUMBER_ONE);
            }
            // 如果是空，那么设置一下为24小时
            else {
                redisUtil.setEx(emailSendCountKey, CommonConsts.NUMBER_ONE_STR, 24, TimeUnit.HOURS);
            }

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R registerService(RegisterReq registerReq) {
        R result = R.ok();
        try {
            @NotBlank String email = registerReq.getEmail();
            @NotBlank String password = registerReq.getPassword();
            @NotBlank String verificationCode = registerReq.getVerificationCode();

            // 需要将邮箱统一转换为小写，应该有自动转换为小写的注解，但是没有找到，所以这里手动处理一下
            email = email.toLowerCase();

            // 判断邮箱的后缀是否符合要求，如果不符合指定的邮箱格式，则返回错误码：21 email格式错误
            String emailSuffix = maigaShareNacosConfiguration.getEmailSuffix();
            String[] emailSuffixArray = emailSuffix.split(CommonConsts.COMMA);
            long count = Arrays.stream(emailSuffixArray).filter(email::endsWith).count();
            if (count == CommonConsts.NUMBER_ZERO) {
                return R.EMAIL_SUFFIX_ERROR();
            }

            // 判断邮箱是否有效
            boolean isEmailFlag = StringUtil.isEmail(email);
            if (!isEmailFlag) {
                return R.EMAIL_FORMAT_ERROR();
            }

            // 这里是针对gmail邮箱的特殊处理
            email = StringUtil.emailChangeFun(email);

            // 判断邮箱是否已注册，这里直接查询数据库即可
            Account account = redisService.accountSelectByEmailFromRedis(email);
            // 如果当前是要注册，且当前的邮箱已经注册过了，那么返回错误码
            if (null != account) {
                return R.EMAIL_REGISTERED_ERROR();
            }

            // 判断验证码是否正确
            String verifiCodeKey = String.format(RedisEnum.REDIS_EMAIL_VERIFICODET_$EMAIL_$TYPE.getKey(), email, CommonConsts.NUMBER_ONE_STR);
            String verifiCodeValue = redisUtil.get(verifiCodeKey);
            // 如果验证码不匹配，则返回对应的错误码
            if (!verificationCode.equals(verifiCodeValue)) {
                return R.VERIFICATION_CODE_ERROR();
            }

            // 相关验证都通过以后，需要将信息填入到t_account表里面去，这个时候的注册，只需要保存用户的email和password就可以了
            Account insertAccount = Account.builder().email(email).password(password).build();
            wAccountMapper.insertSelective(insertAccount);

            // 数据库更新完成后，需要删除验证码
            redisUtil.delete(verifiCodeKey);

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R emailResetService(EmailResetReq emailResetReq) {
        R result = R.ok();
        try {
            @NotBlank String email = emailResetReq.getEmail();
            @NotBlank String password = emailResetReq.getPassword();
            @NotBlank String verificationCode = emailResetReq.getVerificationCode();

            // 需要将邮箱统一转换为小写，应该有自动转换为小写的注解，但是没有找到，所以这里手动处理一下
            email = email.toLowerCase();

            // 判断邮箱的后缀是否符合要求，如果不符合指定的邮箱格式，则返回错误码：21 email格式错误
            String emailSuffix = maigaShareNacosConfiguration.getEmailSuffix();
            String[] emailSuffixArray = emailSuffix.split(CommonConsts.COMMA);
            long count = Arrays.stream(emailSuffixArray).filter(email::endsWith).count();
            if (count == CommonConsts.NUMBER_ZERO) {
                return R.EMAIL_SUFFIX_ERROR();
            }

            // 判断邮箱是否有效
            boolean isEmailFlag = StringUtil.isEmail(email);
            if (!isEmailFlag) {
                return R.EMAIL_FORMAT_ERROR();
            }

            // 这里是针对gmail邮箱的特殊处理
            email = StringUtil.emailChangeFun(email);

            // 判断邮箱是否已注册，这里直接查询数据库即可
            Account account = redisService.accountSelectByEmailFromRedis(email);
            // 如果当前用户未注册，则返回错误码
            if (null == account) {
                return R.EMAIL_NO_REGISTERED_ERROR();
            }

            // 判断验证码是否正确
            String verifiCodeKey = String.format(RedisEnum.REDIS_EMAIL_VERIFICODET_$EMAIL_$TYPE.getKey(), email, CommonConsts.NUMBER_TWO_STR);
            String verifiCodeValue = redisUtil.get(verifiCodeKey);
            // 如果验证码不匹配，则返回对应的错误码
            if (!verificationCode.equals(verifiCodeValue)) {
                return R.VERIFICATION_CODE_ERROR();
            }

            // 验证码正确的话，直接根据email更细password即可
            Account updateAccount = Account.builder().email(email).password(password).build();
            wAccountMapper.updateByEmailSelective(updateAccount);
            // 更新完密码后，需要清理缓存，避免脏数据
            redisService.cleanAccountRedis(account);

            // 数据库更新完成后，需要删除验证码
            redisUtil.delete(verifiCodeKey);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R<LoginRsp> loginService(LoginReq loginReq) {
        R<LoginRsp> result = R.ok();
        try {
            // 当前的登录方式只考虑1：ID账号登录和2：邮箱账号登录这两种方式
            @NotBlank String accountType = loginReq.getAccountType();
            @NotBlank String userName = loginReq.getUserName();
            String password = loginReq.getPassword();

            // 定义好account对象，用来保存用户信息
            Account account = null;
            // 根据请求参数中的accountType，如果是邮箱的话需要做特殊处理
            switch (accountType) {
                // 如果用户的登录方式是邮箱
                case CommonConsts.NUMBER_TWO_STR:
                    // 使用邮箱登录的时候，密码是必选项
                    if (StringUtil.isEmpty(password)) {
                        return R.paramError();
                    }
                    // 需要将邮箱统一转换为小写，应该有自动转换为小写的注解，但是没有找到，所以这里手动处理一下
                    userName = userName.toLowerCase();
                    // 判断邮箱的后缀是否符合要求，如果不符合指定的邮箱格式，则返回错误码：21 email格式错误
                    String emailSuffix = maigaShareNacosConfiguration.getEmailSuffix();
                    String[] emailSuffixArray = emailSuffix.split(CommonConsts.COMMA);
                    long count = Arrays.stream(emailSuffixArray).filter(userName::endsWith).count();
                    if (count == CommonConsts.NUMBER_ZERO) {
                        return R.EMAIL_SUFFIX_ERROR();
                    }
                    // 判断邮箱是否有效
                    boolean isEmailFlag = StringUtil.isEmail(userName);
                    if (!isEmailFlag) {
                        return R.EMAIL_FORMAT_ERROR();
                    }
                    // 这里是针对gmail邮箱的特殊处理
                    userName = StringUtil.emailChangeFun(userName);

                    // 通过邮箱去查询数据库
                    account = redisService.accountSelectByEmailFromRedis(userName);

                    break;

                case CommonConsts.NUMBER_ONE_STR:
                    // 使用邮箱登录的时候，密码是必选项
                    if (StringUtil.isEmpty(password)) {
                        return R.paramError();
                    }
                    // 如果是使用ID账号登录
                    account = redisService.accountSelectByUserIdFromRedis(userName);
                    break;

                case CommonConsts.NUMBER_THREE_STR:
                    break;

                case CommonConsts.NUMBER_FOUR_STR:
                    break;
                // 后续这里如果还需要增加其他的登录方式的话，在这里处理即可
                default:
                    break;
            }

            // 此时就需要判断用户是否是存在的了,如果没有查询
            if (null == account) {
                return R.USER_NOT_EXITS_ERROR();
            }

            // 如果用户是存在的话，还需要验证密码是否正确，只有登录方式accountType为1、2、3的时候才校验密码
            switch (accountType) {
                case CommonConsts.NUMBER_TWO_STR:
                case CommonConsts.NUMBER_ONE_STR:
                case CommonConsts.NUMBER_THREE_STR:
                    if (!password.equalsIgnoreCase(account.getPassword())) {
                        return R.PASSWORD_ERROR();
                    }
                    break;
                default:
                    break;
            }

            // 如果密码也是正确的话，那么就需要组装返回参数了
            // 如何判断用户是否已经参加免费试用呢？其实可以考虑增加一个字段的，但是这里我就直接用用户的other_pwd字段是否为空
            // 来判断了，因为增加了免费试用后，是需要给这个用户生成一个明文密码的
            String isJoined = CommonConsts.FALSE_STR;
            if (!StringUtil.isEmpty(account.getOtherPwd())) {
                isJoined = CommonConsts.TRUE_STR;
            }

            // 接下来，就是生成JWT
            String userToken = JwtUtils.createToken(account);

            // 组装最终的返回结果
            LoginRsp loginResult = LoginRsp.builder().isJoined(isJoined).userToken(userToken).build();
            result.setData(loginResult);


        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }


    @Override
    public R passwordResetService(PasswordResetReq passwordResetReq) {
        R<LoginRsp> result = R.ok();
        try {
            @NotBlank String newPwd = passwordResetReq.getNewPwd();
            @NotBlank String oldPwd = passwordResetReq.getOldPwd();

            // 本方法上使用了@UserLoginToken注解，所以能走到这里说明token肯定是已经校验通过了的
            // 但是还需要从token中获取到用户的userId
            String token = request.getHeader(CommonConsts.PARAM_USER_TOKEN);
            String userId = JwtUtils.getAudience(token);

            // 如果用户的id是空的话，那么说明token异常，返回token异常的错误码
            if (StringUtil.isEmpty(userId)) {
                return R.TOKEN_ERROR();
            }

            // 通过userId去找用户的账号信息
            Account account = redisService.accountSelectByUserIdFromRedis(userId);
            if (null == account) {
                return R.USER_NOT_EXITS_ERROR();
            }

            // 如果用户的账号信息是存在的，那么就需要对比老密码是否正确
            // 如果老密码不正确，返回错误码
            if (!oldPwd.equalsIgnoreCase(account.getPassword())) {
                return R.PASSWORD_ERROR();
            }
            Account updateAccount = Account.builder().password(newPwd).userId(Integer.parseInt(userId)).build();

            // 如果密码也是正确的，那么此时根据用户的userId来更新密码
            wAccountMapper.updateByUserIdSelective(updateAccount);
            redisService.cleanAccountRedis(account);

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R<GetUserInfoRsp> getUserInfoService(GetUserInfoReq getUserInfoReq) {
        R<GetUserInfoRsp> result = R.ok();
        try {
            // 本方法上使用了@UserLoginToken注解，所以能走到这里说明token肯定是已经校验通过了的
            // 但是还需要从token中获取到用户的userId
            String token = request.getHeader(CommonConsts.PARAM_USER_TOKEN);
            String userId = JwtUtils.getAudience(token);

            // 如果用户的id是空的话，那么说明token异常，返回token异常的错误码
            if (StringUtil.isEmpty(userId)) {
                return R.TOKEN_ERROR();
            }

            // 通过userId去找用户的账号信息
            Account account = redisService.accountSelectByUserIdFromRedis(userId);
            if (null == account) {
                return R.USER_NOT_EXITS_ERROR();
            }

            GetUserInfoRsp getUserInfoRsp = GetUserInfoRsp.builder().userId(account.getUserId().toString()).email(account.getEmail())
                    .otherPwd(account.getOtherPwd()).playlistUrl(account.getPlaylistUrl()).dns(account.getDns()).parentalPwd(account.getParentalPwd()).build();

            // 如果用户是存在的，那么就需要返回结果了
            String appModel = maigaShareNacosConfiguration.getAppModel();
            if (CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                getUserInfoRsp.setAuthInvalidTime(CommonConsts.FREE_STR);
            } else {
                if (null != account.getAuthInvalidTime()) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    getUserInfoRsp.setAuthInvalidTime(df.format(account.getAuthInvalidTime()));
                } else {
                    getUserInfoRsp.setAuthInvalidTime(null);
                }

            }
            result.setData(getUserInfoRsp);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R joinService(JoinReq joinReq) {
        R result = R.ok();
        try {
            // 请求参数信息获取
            String mac = joinReq.getMac();
            String lang = joinReq.getLang();

            // 本方法上使用了@UserLoginToken注解，所以能走到这里说明token肯定是已经校验通过了的
            // 但是还需要从token中获取到用户的userId
            String token = request.getHeader(CommonConsts.PARAM_USER_TOKEN);
            String userId = JwtUtils.getAudience(token);

            // 如果用户的id是空的话，那么说明token异常，返回token异常的错误码
            if (StringUtil.isEmpty(userId)) {
                return R.TOKEN_ERROR();
            }

            // 通过userId去找用户的账号信息
            Account account = redisService.accountSelectByUserIdFromRedis(userId);
            if (null == account) {
                return R.USER_NOT_EXITS_ERROR();
            }

            // 如果用户已经存在，且已经加入了免费试用，那么返回错误码
            if (!StringUtil.isEmpty(account.getOtherPwd())) {
                return R.FREE_TRAIL_ERROR();
            }

            // 如果用户是存在的，那么需要给这个用户加入免费试用。需要给用户设置相关的参数信息
            // 这些配置信息都从配置文件中获取
            String defaultDns = maigaShareNacosConfiguration.getDefaultDns();
            String defaultOtherPwd = maigaShareNacosConfiguration.getDefaultOtherPwd();
            String defaultParentalPwd = maigaShareNacosConfiguration.getDefaultParentalPwd();
            // playListUrl需要做一下处理 playlist文件地址,生成方式：http://magia.com/md5(userId+csmagiaplaylist)
            String defaultPlaylistUrl = maigaShareNacosConfiguration.getDefaultPlaylistUrl();
            String md5UserId = MD5Parse.parseStr2md5(account.getUserId() + CommonConsts.DEFAULT_MAGIA_SECRET);
            defaultPlaylistUrl = defaultPlaylistUrl + CommonConsts.SLASH_STR + md5UserId;
            // 至于服务有效期就默认设置为当前日期吧

            // 组装更新的数据
            Account updateAccount = Account.builder().userId(Integer.parseInt(userId)).otherPwd(defaultOtherPwd).dns(defaultDns).parentalPwd(defaultParentalPwd)
                    .playlistUrl(defaultPlaylistUrl).mac(mac).authInvalidTime(new Date()).build();
            // 根据userId更新这些字段信息
            wAccountMapper.updateByUserIdSelective(updateAccount);
            redisService.cleanAccountRedis(account);

            // 调用给用户发送邮件
            // 验证码开始调用noticeCore进行发送
            String emailtempnoticeUrl = maigaShareNacosConfiguration.getEmailtempnoticeUrl();
            if (StringUtil.isEmpty(emailtempnoticeUrl)) {
                log.error("未配置properties.url.noticeCore.emailtempnotice_url的值");
                return R.error();
            }

            // 如果获取连接正常，那么组装参数发送http请求
            Map<String, String> emailParamMap = new HashMap<>();
            // 模板中展示的用户的邮箱
            emailParamMap.put("$email", account.getEmail());
            // 模板中展示的服务有效期，如果当前是免费模式，则展示free，否则直接展示时间
            String authInvalidTimeStr = CommonConsts.EMPTY_STR;
            String appModel = maigaShareNacosConfiguration.getAppModel();
            if (CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                authInvalidTimeStr = CommonConsts.FREE_STR;
            } else {
                if (null != account.getAuthInvalidTime()) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    authInvalidTimeStr = df.format(account.getAuthInvalidTime());

                }
            }
            emailParamMap.put("$authInvalidTime", authInvalidTimeStr);
            // 展示用户的userId
            emailParamMap.put("$userId", account.getUserId().toString());
            // 展示用户的明文密码
            emailParamMap.put("$otherPwd", defaultOtherPwd);
            // 展示链接
            emailParamMap.put("$playlistUrl", defaultPlaylistUrl);
            // 展示DNS
            emailParamMap.put("$dns", defaultDns);
            // 展示父母锁密码
            emailParamMap.put("$parentalPwd", defaultParentalPwd);

            // 组装
            EmailTempNoticeVo emailTempNoticeVo = new EmailTempNoticeVo();
            emailTempNoticeVo.setTemplateType(lang);
            emailTempNoticeVo.setObjJson(emailParamMap);
            emailTempNoticeVo.setCustomerEmail(account.getEmail());
            emailTempNoticeVo.setSource(CommonConsts.MAGIA_FREETRAIL);
            //根据请求参数中的语言来组装参数
            switch (lang) {
                case CommonConsts.EN:
                    emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectFreetrailEn());
                    break;

                case CommonConsts.ES:
                    emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectFreetrailEs());
                    break;

                case CommonConsts.PT:
                    emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectFreetrailPt());
                    break;
                default:
                    break;
            }

            // 发送邮件
            String data = HttpRequestUtil.getInstance().executePostByStream(emailtempnoticeUrl, JsonUtil.writeObject2JSON(emailTempNoticeVo));
            if (StringUtil.isEmpty(data)) {
                return R.error();
            }

            // 调用三方网站的api，直接下发节目单 这里只需要将mac地址保存到redis里面的一个list里面就行，让python脚本服务去处理这里面的发送逻辑
            // 如果mac地址不为空，写入到redis的一个LIST里面去
            if (!StringUtil.isEmpty(mac)) {
                String magiaMacPushList = RedisEnum.MAGIA_MAC_PUSH_LIST.getKey();
                MagiaMacPushListPo magiaMacPushListPo = MagiaMacPushListPo.builder().mac(mac).playlistUrl(defaultPlaylistUrl).build();
                redisUtil.lLeftPush(magiaMacPushList, JsonUtil.writeObject2JSON(magiaMacPushListPo));
            }

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R sendEmailService(SendEmailReq sendEmailReq) {
        R result = R.ok();
        try {
            @NotBlank String type = sendEmailReq.getType();
            @NotBlank String sendType = sendEmailReq.getSendType();
            @NotBlank String secretKey = sendEmailReq.getSecretKey();
            String lang = sendEmailReq.getLang();
            List<String> emailList = sendEmailReq.getEmailList();

            // 如果secretKey的值不对，返回错误码
            if (!CommonConsts.DEFAULT_KEY.equalsIgnoreCase(secretKey)) {
                return R.paramError();
            }

            // 调用给用户发送邮件
            // 验证码开始调用noticeCore进行发送
            String emailtempnoticeUrl = maigaShareNacosConfiguration.getEmailtempnoticeUrl();
            if (StringUtil.isEmpty(emailtempnoticeUrl)) {
                log.error("未配置properties.url.noticeCore.emailtempnotice_url的值");
                return R.error();
            }

            ExecutorService asyncService = Executors.newSingleThreadExecutor();
            asyncService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 如果秘钥正常，那么筛选出需要发送的邮箱列表
                        List<String> sendEmaiList = new ArrayList<>();
                        switch (sendType) {
                            case CommonConsts.NUMBER_ONE_STR:
                                sendEmaiList = emailList;
                                break;

                            case CommonConsts.NUMBER_TWO_STR:
                                sendEmaiList = rAccountMapper.selectEmailForFreeTrail();
                                break;

                            case CommonConsts.NUMBER_THREE_STR:
                                sendEmaiList = rAccountMapper.selectEmailForAll();
                                break;
                            default:
                                break;
                        }
                        log.info("手动触发邮件发送，需要发送的用户量为：" + sendEmaiList.size() + "后台线程方式处理...");


                        // 组装
                        EmailTempNoticeVo emailTempNoticeVo = new EmailTempNoticeVo();
                        emailTempNoticeVo.setTemplateType(lang);

                        //根据请求参数中的语言来组装参数
                        switch (type) {
                            // 内容更新通知
                            case CommonConsts.NUMBER_ONE_STR:
                                emailTempNoticeVo.setSource(CommonConsts.MAGIA_CONTENTUPDATE);
                                switch (lang) {
                                    case CommonConsts.EN:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectContentUpdateEn());
                                        break;

                                    case CommonConsts.ES:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectContentUpdateEs());
                                        break;

                                    case CommonConsts.PT:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectContentUpdatePt());
                                        break;
                                    default:
                                        break;
                                }
                                break;

                            //URL/DNS更新通知
                            case CommonConsts.NUMBER_TWO_STR:
                                emailTempNoticeVo.setSource(CommonConsts.MAGIA_LISTUPDATE);
                                //2：URL/DNS变更通知
                                switch (lang) {
                                    case CommonConsts.EN:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectListUpdateEn());
                                        break;

                                    case CommonConsts.ES:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectListUpdateEs());
                                        break;

                                    case CommonConsts.PT:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectListUpdatePt());
                                        break;
                                    default:
                                        break;
                                }
                                break;

                            //官网地址更新通知
                            case CommonConsts.NUMBER_THREE_STR:
                                emailTempNoticeVo.setSource(CommonConsts.MAGIA_WEBSITEUPDATE);
                                //3：官网地址变更通知
                                switch (lang) {
                                    case CommonConsts.EN:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectWebsiteUpdateEn());
                                        break;

                                    case CommonConsts.ES:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectWebsiteUpdateEs());
                                        break;

                                    case CommonConsts.PT:
                                        emailTempNoticeVo.setSubject(maigaShareNacosConfiguration.getSubjectWebsiteUpdatePt());
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }

                        // 到了这一步以后，还有下面两个参数是没有设置的，那就放在遍历处理里面再设置吧
                        Map<String, String> emailParamMap = new HashMap<>();
                        String websiteUrl = maigaShareNacosConfiguration.getWebsiteUrl();
                        String appModel = maigaShareNacosConfiguration.getAppModel();
                        for (String email : sendEmaiList) {
                            // 组装emailParamMap里面的参数信息
                            // type为1和2的时候，需要如下这些：$email $authInvalidTime $userId $otherPwd $playlistUrl $dns $parentalPwd
                            // type为3的时候，只需要$email $websiteurl
                            switch (type) {
                                case CommonConsts.NUMBER_ONE_STR:
                                case CommonConsts.NUMBER_TWO_STR:
                                    Account account = rAccountMapper.selectByEmail(email);
                                    String authInvalidTimeStr = null;
                                    if (null != account) {
                                        if (CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                                            authInvalidTimeStr = CommonConsts.FREE_STR;
                                        } else {
                                            if (null != account.getAuthInvalidTime()) {
                                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                authInvalidTimeStr = df.format(account.getAuthInvalidTime());

                                            }
                                        }
                                        // 模板中展示的用户的邮箱
                                        emailParamMap.put("$email", account.getEmail());
                                        emailParamMap.put("$authInvalidTime", authInvalidTimeStr);
                                        // 展示用户的userId
                                        emailParamMap.put("$userId", account.getUserId().toString());
                                        // 展示用户的明文密码
                                        emailParamMap.put("$otherPwd", account.getOtherPwd());
                                        // 展示链接
                                        emailParamMap.put("$playlistUrl", account.getPlaylistUrl());
                                        // 展示DNS
                                        emailParamMap.put("$dns", account.getDns());
                                        // 展示父母锁密码
                                        emailParamMap.put("$parentalPwd", account.getParentalPwd());

                                    }
                                    break;

                                case CommonConsts.NUMBER_THREE_STR:
                                    emailParamMap.put("$email", email);
                                    emailParamMap.put("$website", websiteUrl);
                                    emailParamMap.put("$lang", lang);
                                    break;
                                default:
                                    break;
                            }

                            emailTempNoticeVo.setObjJson(emailParamMap);
                            emailTempNoticeVo.setCustomerEmail(email);
                            // 此时已经设置好了参数，开始发送

                            // 发送邮件
                            HttpRequestUtil.getInstance().executePostByStream(emailtempnoticeUrl, JsonUtil.writeObject2JSON(emailTempNoticeVo));
                        }
                    } catch (Exception e) {
                        log.error(StringUtil.printExcetion(e));
                        asyncService.execute(this);
                    }
                }
            });
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }


}
