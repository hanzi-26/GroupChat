package com.gdupt.util;

import java.net.*;
import java.util.*;
/**
 * 
 * 此类用来存储在线用户列表信息
 * @author Administrator
 *
 */

public class NameBuffer {
/*	private static List<String> name = new ArrayList<String>();
	
	public static void addName(String _name) {
		name.add(_name);
	}
	
	public static void removeName(String _name) {
		name.remove(_name);
	}

	public static String getNameList() {
		String nameList = "";
		for(String _name : getName()) {
			nameList += _name + '\n';
		}
		return nameList;
	}

	public static List<String> getName() {
		return name;
	}*/
	
	private static Map<Socket, String> map = new HashMap<Socket, String>();
	
	// 添加用户
	public static void addName(Socket socket, String username) {
		map.put(socket, username);
	}
	
	// 删除用户
	public static void remove(Socket socket) {
		map.remove(socket);
	}
	
	// 提取用户列表
	public static String getNameList() {
		Collection<String> c = map.values();
		Iterator<String> it = c.iterator();
		
		String nameList = "";
		for (; it.hasNext();) {
			nameList += it.next() + Checkout.SEPARATOR;
		}
		
		nameList = nameList.substring(0, nameList.length()-Checkout.SEPARATOR.length());
		
		return nameList;
	}
	
	
	public static Socket getKeyByValue(String value) {
		Set<Map.Entry<Socket, String>> set = map.entrySet();
		
        for (Iterator<Map.Entry<Socket, String>> it = set.iterator(); it.hasNext();) {
             Map.Entry<Socket, String> entry = (Map.Entry<Socket, String>) it.next();
              //System.out.println(entry.getKey() + "--->" + entry.getValue());
             if(entry.getValue() == value) {
            	 return (Socket)entry.getKey();
             }
        }
        return null;
	}
	
	//private static List<String> nameList;
	/*public static List<String> getNamList() {
		Collection<String> c = map.values();
		Iterator<String> it = c.iterator();
		for (; it.hasNext();) {
			nameList.add(it.next());
		}
		return nameList;
	}*/

	
	public static Map<Socket, String> getMap() {
		return map;
	}
}
