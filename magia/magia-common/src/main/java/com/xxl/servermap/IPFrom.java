package com.xxl.servermap;

import java.io.File;
import java.util.regex.Pattern;


public class IPFrom {
	private static File fips = new File("src/swl/market/server/servermap/gobal_ips.txt");
	
	/**
	 * Check IP is valid or not.
	 * @param str
	 * @return True or False.
	 */
	public static boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }
	
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷IP转锟斤拷为锟斤拷锟绞拷锟斤拷锟斤拷执锟斤拷锟�
	 * @param ip
	 * @return
	 */
	public static String hltoa(long ip){
		StringBuilder sb = new StringBuilder();
		for (int i = 3; i >=0; i--) {
			sb.append((ip>>>(i*8))&0x000000ff);
			if (i!=0) {
				sb.append('.');
			}
		}
		//System.out.println(sb);
		return sb.toString();
	}
	
	/**
	 * 锟斤拷锟斤拷锟绞拷锟斤拷票锟绞撅拷锟絀P锟街达拷转锟斤拷为锟斤拷锟斤拷锟轿★拷
	 * @param ip
	 * @return
	 */
	public static long atohl(String ip){
		long num = 0;
		String[] sections = ip.split("\\.");
		int i=3;
		for (String str : sections) {
			num+=(Long.parseLong(str)<<(i*8));
			i--;
		}
		//System.out.println(num);
		return num;
	}

}
