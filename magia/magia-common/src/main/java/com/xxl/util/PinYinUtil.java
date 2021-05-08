package com.xxl.util;


import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.StringUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 拼音处理类
 * 
 * @author xiaohui
 * @version 2016年10月29日
 * @see PinYinUtil
 * @since
 */
public class PinYinUtil
{
    public static final int FIRST_CHAR = 0;

    public static final int QUANPIN = 1;
    protected final static Logger logger = LogManager.getLogger(PinYinUtil.class);
    /**
     * 汉字转成拼音
     * 
     * @param chn
     *            汉字
     * @param type
     *            类型0为返回首字母，其他值返回全拼
     * @return 拼音
     * @see
     */
    public static String getPinYin(String chn, int type)
    {
        HanyuPinyinOutputFormat py = new HanyuPinyinOutputFormat();
        py.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        py.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        py.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder str = new StringBuilder();
        char[] chnArray = chn.trim().toCharArray();

        try
        {
            switch (type)
            {
                case 0:
                {
                    for (char c : chnArray)
                    {
                        if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+"))
                        {
                            String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, py);
                            str.append(temp[0].toCharArray()[0]);
                        }
                        else
                        {
                            str.append(Character.toString(c).toUpperCase());
                        }
                    }
                }
                    break;
                default:
                {
                    for (char c : chnArray)
                    {
                        if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+"))
                        {
                            String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, py);
                            str.append(temp[0]);
                        }
                        else
                        {
                            str.append(Character.toString(c).toUpperCase());
                        }
                    }
                }
            }

        }
        catch (BadHanyuPinyinOutputFormatCombination e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }

        return str.toString();
    }
}
