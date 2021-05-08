package com.xxl.util;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fanjia on 2016/10/10.
 */

public class DESUtils {
    private static final String Algorithm = "DES";  //定义 加密算法,可用 DES,DESede,Blowfish
    public static final String ACTIVE_SN_KEY = "ActiceSn";  //定义 加密算法,可用 DES,DESede,Blowfish
    public static final String TV_MARKET_KEY = "tvmarket";  //定义 加密算法,可用 DES,DESede,Blowfish

    /**
     * DES加密
     *
     * @param plainData
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryption(String plainData, String encryptKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Algorithm);
            SecretKey deskey = new SecretKeySpec(encryptKey.getBytes(), Algorithm); // 加密
            cipher.init(Cipher.ENCRYPT_MODE, deskey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);
        }
        try {
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());

            return Base64Util.encode(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * DES解密
     * @param secretData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decryption(String secretData,String secretKey) throws Exception{

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Algorithm);
            SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), Algorithm); // 加密
            cipher.init(Cipher.DECRYPT_MODE, deskey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        }catch(InvalidKeyException e){
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);

        }

        try {
            if(!StringUtil.isEmpty(secretData))
                secretData = secretData.replace(' ', '+');
            byte[] buf = cipher.doFinal(Base64Util.decode(secretData.toCharArray()));

            return new String(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }
    

    public static void main(String[] args) {
    	try {
    		String encryption = encryption("a3.05-18.09-20096661", "tvmarket");
    		System.err.println(encryption);
			//System.out.println(encryption.equals("sbnN2N1ZuLzTvmfwaLU5RWyFnj%2FzeaOp"));
			System.out.println(decryption("62JhDdus5CU2fc0JqGmBip/ACq82+dEx", "tvmarket"));
			System.out.println("62JhDdus5CU2fc0JqGmBip/ACq82+dEx".equals("62JhDdus5CU2fc0JqGmBip/ACq82+dEx"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	/*String input = "a3.05-16.11-20006982";
//    	String input = "a3.05-09.18-10000042";
//		String input = "a3.03-16.03-10000060";
		String V3 = "oVBkD/PUe3ZOx+AxWx1v9dcwGWruLrgk6N9gnh2go3FnlH/KOLQCk2H8mxRxqIUPGGmPThqwp7d7pPuKr+ukTe4/5w14jwGD7j/nDXiPAYPuP+cNeI8Bg1zKL6HHGEj/";
		String V3p = "oVBkD/PUe3ZOx+AxWx1v9dcwGWruLrgk6N9gnh2go3FnlH/KOLQCk2H8mxRxqIUPGGmPThqwp7d7pPuKr+ukTe4/5w14jwGD7j/nDXiPAYPuP+cNeI8Bg1zKL6HHGEj/";
//    	String V3e ="{\"authVersion\":\"v2\",\"sn\":\"12.00-88.88-88888888\",\"authCode\":\"33333333333333333333333333333333\"}";
    	String V3e ="{\"key\":null,\"returnCode\":\"3\",\"errorMessage\":\"验证码错误\"}";
		if(V3.equals(V3p)){
			System.out.println("一样");
		}
    	
    	try {
//			System.out.println(encryption(input,"tvmarket"));
//			System.out.println(decryption(encryption(input,"tvmarket"), "tvmarket"));
//			System.out.println(encryption(V3e,"tvmarket"));
//			System.out.println(decryption(encryption(V3e,"tvmarket"), "tvmarket"));
			System.out.println(decryption("h//9u0jeRHXd+p7AOXUIkBk0boaOobL9nFZTY0u6o4FNZT8yzTSB5fAJMExzWWcbBSnBYPg65gmYRanJl0ginItkVJd/nhUXD7q7JVTBI50=", "tvmarket"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
