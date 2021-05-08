package com.xxl.common.api;

import com.alibaba.fastjson.JSON;
import com.xxl.common.enums.APICodeMsgEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {
    @NotEmpty
    private String returnCode;
    @NotEmpty
    private String errorMessage;
    private T data;
//    private int total;

    public static R set(APICodeMsgEnum apiCodeMsgEnum) {
        return R.builder().returnCode(apiCodeMsgEnum.getCode()).errorMessage(apiCodeMsgEnum.getMsg()).build();
    }

    public R set(APICodeMsgEnum apiCodeMsgEnum, T data) {
        return R.builder().returnCode(apiCodeMsgEnum.getCode()).errorMessage(apiCodeMsgEnum.getMsg()).data(data).build();
    }

    /*public static R Total(int userListCount) {
        return set(APICodeMsgEnum.TOTAL);
    }*/

    public static R ok() {
        return set(APICodeMsgEnum.SUCCESS);
    }

    public  R  ok(T data) {
        return set(APICodeMsgEnum.SUCCESS, data);
    }

    public static R error() {
        return set(APICodeMsgEnum.SYSTEM_ERROR);
    }


    public static R fail() {
        return set(APICodeMsgEnum.REQUEST_FAIL);
    }


    public static R paramError() {
        return set(APICodeMsgEnum.PARAM_ERROR);
    }

    public  R  SN_REF_GROUP(T data) {
        return set(APICodeMsgEnum.SN_REF_GROUP, data);
    }
    public  R  UID_REF_GROUP(T data) {
        return set(APICodeMsgEnum.UID_REF_GROUP, data);
    }


    public static R configError() {
        return set(APICodeMsgEnum.CONFIG_ERROR);
    }

    public static R noCondition() {
        return set(APICodeMsgEnum.NO_CONDITION);
    }

    public static R notFound() {
        return set(APICodeMsgEnum.NOT_FOUND);
    }

    public static R sysBusy() {
        return set(APICodeMsgEnum.SYS_BUSY);
    }
    public static R userNameOrPwdError() {
        return set(APICodeMsgEnum.USERNAME_PWD_ERROR);
    }

    public static R notInDateRange() {
        return set(APICodeMsgEnum.NOT_IN_DATE_RANGE);
    }

    public static R USER_NOT_EXITS() {
        return set(APICodeMsgEnum.USER_NOT_EXITS);
    }

    public static R DB_ERROR() {
        return set(APICodeMsgEnum.DB_ERROR);
    }

    public static R DATA_REF_APP() {
        return set(APICodeMsgEnum.DATA_REF_APP);
    }

    public static R DATA_EXISTS() {
        return set(APICodeMsgEnum.DATA_EXISTS);
    }

    public static R DATA_DUPLICATE() {
        return set(APICodeMsgEnum.DATA_DUPLICATE);
    }

    public static R DATA_REF_NOT_REF_GROUP() { return set(APICodeMsgEnum.DATA_REF_NOT_REF_GROUP);}

    public static R TOKEN_ERROR() { return set(APICodeMsgEnum.TOKEN_ERROR);}

    public static R EMAIL_SUFFIX_ERROR() { return set(APICodeMsgEnum.EMAIL_SUFFIX_ERROR);}

    public static R EMAIL_FORMAT_ERROR() { return set(APICodeMsgEnum.EMAIL_FORMAT_ERROR);}

    public static R EMAIL_REGISTERED_ERROR() { return set(APICodeMsgEnum.EMAIL_REGISTERED_ERROR);}

    public static R SEND_EMAIL_LIMIT_ERROR() { return set(APICodeMsgEnum.SEND_EMAIL_LIMIT_ERROR);}

    public static R SEND_EMAIL_180S_LIMIT_ERROR() { return set(APICodeMsgEnum.SEND_EMAIL_180S_LIMIT_ERROR);}

    public static R VERIFICATION_CODE_ERROR() { return set(APICodeMsgEnum.VERIFICATION_CODE_ERROR);}

    public static R EMAIL_NO_REGISTERED_ERROR() { return set(APICodeMsgEnum.EMAIL_NO_REGISTERED_ERROR);}

    public static R USER_NOT_EXITS_ERROR() { return set(APICodeMsgEnum.USER_NOT_EXITS_ERROR);}

    public static R PASSWORD_ERROR() { return set(APICodeMsgEnum.PASSWORD_ERROR);}

    public static R FREE_TRAIL_ERROR() { return set(APICodeMsgEnum.FREE_TRAIL_ERROR);}





    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
