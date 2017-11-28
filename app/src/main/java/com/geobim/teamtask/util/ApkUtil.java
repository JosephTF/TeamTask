package com.geobim.teamtask.util;

import android.os.Environment;

public class ApkUtil {
	/**
	 * 设置或获取APK安装路径
	 * @return
	 */
	public static String getApkPath(){
		return Environment.getExternalStorageDirectory() + "/TeamTask/";
	}

	/**
	 * 获取Mob服务端的key值
	 * @return
	 */
	public static String getMobKey(){
		return "21b75ceb709c0";
	}

	/**
	 * 获取Mob服务端的secret值
	 * @return
	 */
	public static String getMobSecret(){
		return "d12751d8470be51cd1f58bf734ef4800";
	}
}
