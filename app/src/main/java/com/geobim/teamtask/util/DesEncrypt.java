package com.geobim.teamtask.util;

import android.annotation.SuppressLint;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Administrator on 2017/11/28.
 */

public class DesEncrypt {
    private static final String DES_KEY = "geobim";

    /**
     * 不允许初始化
     */
    private DesEncrypt()
    {
    }

    /**
     * 数据加密，算法（DES）
     *
     * @param data 要进行加密的数据
     * @return 加密后的数据
     */
    public static String encode(String data)
    {
        return encode(DES_KEY, data);
    }

    /**
     * 数据加密，算法（DES）
     *
     * @param desKey 加密的key,8位字符串
     * @param data 要进行加密的数据
     * @return 加密后的数据
     */
    @SuppressLint("TrulyRandom")
    public static String encode(String desKey, String data)
    {
        String encryptedData = null;
        try
        {
            if (desKey == null)
            {
                desKey = DES_KEY;
            }

            while (desKey.length() < 8)
            {
                desKey = desKey + " ";
            }
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(desKey.getBytes());

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);

            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);

            // 加密，并把字节数组编码成字符串
            encryptedData = new String(Base64Utils.encode(cipher.doFinal(data.getBytes("UTF-8"))));
        } catch (Exception e)
        {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * 数据解密，算法（DES）
     *
     * @param data 加密数据
     * @return 解密后的数据
     */
    public static String decode(String data)
    {
        return decode(DES_KEY, data);
    }

    /**
     * 数据解密，算法（DES）
     *
     * @param desKey 加密的key,8位字符串
     * @param data 加密数据
     * @return 解密后的数据
     */
    public static String decode(String desKey, String data)
    {
        String decryptedData = null;
        try
        {
            if (desKey == null)
            {
                desKey = DES_KEY;
            }

            while (desKey.length() < 8)
            {
                desKey = desKey + " ";
            }
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec deskey = new DESKeySpec(desKey.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙key初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(Base64Utils.decode(data)), "UTF-8");
        } catch (Exception e)
        {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }
}
