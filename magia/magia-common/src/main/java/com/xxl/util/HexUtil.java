package com.xxl.util;


import com.xxl.common.enums.CommonConsts;

import java.io.UnsupportedEncodingException;


/**
 * 编码算法
 *
 * @author lingengming
 * @version 2016年12月8日
 * @see HexUtil
 * @since
 */
public final class HexUtil {
    private static byte[] correspondingNibble = new byte[103];

    static {
        for (int i = 0; i <= 102; i++) {
            correspondingNibble[i] = -1;
        }
        for (int i = 48; i <= 57; i++) {
            correspondingNibble[i] = ((byte) (i - 48));
        }
        for (int i = 65; i <= 70; i++) {
            correspondingNibble[i] = ((byte) (i - 65 + 10));
        }
        for (int i = 97; i <= 102; i++) {
            correspondingNibble[i] = ((byte) (i - 97 + 10));
        }
    }

    public static String fromHexString(String s)
            throws UnsupportedEncodingException {
        int stringLength = s.length();
        if ((stringLength & 0x1) != 0) {
            throw new IllegalArgumentException(
                    "fromHexString requires an even number of hex characters");
        }
        byte[] bytes = new byte[stringLength / 2];

        int i = 0;
        for (int j = 0; i < stringLength; j++) {
            int high = charToNibble(s.charAt(i));
            int low = charToNibble(s.charAt(i + 1));

            bytes[j] = ((byte) (high << 4 | low));
            i += 2;
        }
        String str = new String(bytes, CommonConsts.UTF8);
        return str;
    }

    private static int charToNibble(char c) {
        if (c > 'f') {
            throw new IllegalArgumentException("Invalid hex character: " + c);
        }
        int nibble = correspondingNibble[c];
        if (nibble < 0) {
            throw new IllegalArgumentException("Invalid hex character: " + c);
        }
        return nibble;
    }

    public static String toHexString(String s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str.append(s4);
        }
        return str.toString();
    }

    public static void main(String[] args)
            throws UnsupportedEncodingException {
        String s = "9+OGL/Hi/BY=";
        String str1 = toHexString(s);

        System.out.println(str1);

        String str2 = fromHexString(str1);

        System.out.println(str2);
    }
}
