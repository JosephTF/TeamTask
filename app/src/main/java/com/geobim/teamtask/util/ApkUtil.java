package com.geobim.teamtask.util;

import android.os.Environment;

/**
 * App的一些全局常量信息
 */
public class ApkUtil {
	/**
	 * 获取APK安装路径
	 * @return path
	 */
	public final static String getApkPath(){
		return Environment.getExternalStorageDirectory() + "/TeamTask/";
	}

	/**
	 * 获取Mob服务端的key值
	 * @return key值
	 */
	public final static String getMobAppKey(){
		return "22de5b1c8c79e";
	}

	/**
	 * 获取Mob服务端的secret值
	 * @return secret值
	 */
	public final static String getMobAppSecret(){
		return "d2901ce207c6ddd55c5bcda9efb77b72";
	}
}
