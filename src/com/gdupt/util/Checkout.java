package com.gdupt.util;

/**
 * 
 * 此类用来校验输入是否通过和定义信息类型
 * 
 * @author Administrator
 * 
 */

public class Checkout {
	public static final String ADD = "adduser&***&&*";
	public static final String REMOVE = "byebye*&removeme";
	public static final String ADD_ERROR = "adduser&***&&()(*((**((((";
	public static final String ADD_SUCCESS = "adduser&***&&*(*&&*&#$%";
	public static final String ADD_USER = "ADDUSER!##$#%%%%^^&*&**(";
	public static final String REMOVE_USER = "REMOVEUSER!##%^#@$#$%%$^())";
	public static final String SINGLE_CHAT_REQUEST = "singlechatquest@#%XGTYU#$!@";
	public static final String SINGLE_CHATING = "singlechatSDFA@#%$#^%S";
	public static final String SEND_FILE = "sendfile@#SDF#%XV^";
	public static final String SEPARATOR = "@!";
	public static final String NORMAL_MESSAGE = "normal_message@#$@##$^&&Fjj";
	public static final String SEND_MESSAGE = "send_message@@@##%$!!";
	public static final String DOWN = "down^&$$down!!!";
	public static final String SHIELD = "shield!#$$$#@shield";
	public static final String SHIELDREREASE = "shieldrelease!@$";
	
	
	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */

	public static boolean isEmpty(String str) {
		if ((str.length() > 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 检查字符串是否全为空格
	 */
	public static boolean isAllBlankSpace(String str) {
		if (!(str.trim().length() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查字符串首尾是否有空格
	 */
	public static boolean isEmptyStart(String str) {
		if (str.length() > str.trim().length()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否含有标点符号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCorrect(String str) {
		String strr = "~!@#$%^&*()_+|}{`:?><-=[];'/.,/*-+.~！@#￥%……&*（）——+|{}：“”？》《/*-+·-=、、。，\\";
		for (int i = 0; i < str.length(); i++) {
			if (strr.indexOf(str.substring(i, i + 1)) != -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 判断端口是否在范围1024~65535内
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPortCorrect(String str) {

		if (str.length() <= 5 && str.length() >= 3) {
			int i = Integer.parseInt(str);
			if (i < 1024 || i > 65535) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean isAddress(String str) {
		String[] ss = str.split("\\.");

		if (ss.length == 4) {
			for (String st : ss) {
				if (isNumber(st)) {
					if (Integer.parseInt(st) > 255 || Integer.parseInt(st) < 0) {
						return false;
					}
				} else {
					return false;
				}
				return true;
			}
		} else if (str.equalsIgnoreCase("localhost")) {
			return true;
		} else if (str.equals("")) {
			return true;
		}

		return true;

		/*
		 * try {
		 * 
		 * @SuppressWarnings("unused") InetAddress address =
		 * InetAddress.getByName(str); return true; } catch
		 * (UnknownHostException e) { return false; }
		 */
	}

	// 解析信息内容，返回一个字符串数组
	public static String[] analyzingMessage(String message, String str) {
		return message.substring(0, message.indexOf(str)).split(
				Checkout.SEPARATOR);
	}
}
