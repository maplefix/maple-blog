package top.maplefix.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author : Maple
 * @description : AES加解密方法
 * @Date : Created in 2019/3/31 21:04
 * @editor:
 * @version: v2.1
 */
@Slf4j
public class AESUtils {

    private static String key = "zK8Gv+ycuzumesWziXH4yA==";

    /**
     * 自动生成AES128位密钥
     */
    private static String generateKey() {
        String key=null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            key = Base64.getEncoder().encodeToString(b);
        } catch (NoSuchAlgorithmException e) {
            log.warn("warn",e);
        }
        return key;

    }

    /**
     * 加密
     * @param content String
     * @return byte[]
     * @throws Exception
     */
    private static byte[] encrypt(String content) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return  encrypted;
    }

    /**
     * 解密
     *
     * @param content String
     * @return String
     * @throws Exception
     */
    private static String decrypt(byte[] content) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(content);
        return new String(original);
    }




    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @return 加密后的base 64 code
     * @throws Exception //加密传String类型，返回String类型
     */
    public static String aesEncrypt(String content) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(content));
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string   //解密传String类型，返回String类型
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr) throws Exception {
        return StringUtils.isEmpty(encryptStr)?null:decrypt(Base64.getDecoder().decode(encryptStr));
    }


    public static void main(String[] args) throws Exception {
        System.out.println("原文加密后为:" + aesEncrypt("123456"));
        System.out.println("密文解密后为:" + aesDecrypt("QNZGJtKSsOLCfwCjzRZdiQ=="));
    }

}
