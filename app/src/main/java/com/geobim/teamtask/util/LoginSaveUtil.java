package com.geobim.teamtask.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 保存、读取登录信息类
 *
 * @author Administrator
 */
public class LoginSaveUtil {
    private Context context;

    public LoginSaveUtil(Context context) {
        this.context = context;
    }

    /**
     * 把用户名和密码保存到ROM
     *
     * @param password 输入要保存的密码
     * @param username 要保存的用户名
     * @return
     */
    public boolean saveToRom(String password, String username, String tokenKey) throws Exception {
        //以私有的方式打开一个文件
        FileOutputStream fos = context.openFileOutput("private.txt", Context.MODE_PRIVATE);
        String result = username + ":" + password + ":" + tokenKey;
        fos.write(result.getBytes());
        fos.flush();
        fos.close();
        return true;
    }

    /**
     * 从ROM中读取用户名和密码
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public Map<String, String> getUserInfo(String filename) throws Exception {
        FileInputStream fis = context.openFileInput(filename);
        byte[] data = StreamTools.getBytes(fis);
        String result = new String(data);
        String results[] = result.split(":");
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", results[0]);
        map.put("password", results[1]);
        map.put("tokenKey", results[2]);
        return map;
    }
}
