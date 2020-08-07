package org.robert.core.util;

import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA加解密工具类
 *
 */
public class RsaUtils {
    /**
     * 默认"RSA"="RSA/ECB/PKCS1Padding"
     */
    private static final String CIPHER_INSTANCE = "RSA/ECB/PKCS1Padding";

    /**
     * 公钥加密
     * @param content 要加密的内容
     * @param publicKey 公钥
     */
    public static String encrypt(String content, PublicKey publicKey) {
        try{
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(content.getBytes());
            Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(output);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     * @param content 要加密的内容
     * @param publicKey 公钥
     */
    public static byte[] encrypt(byte[] content, PublicKey publicKey) {
        try{
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return  cipher.doFinal(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     * @param content 要解密的内容
     * @param privateKey 私钥
     */
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     * @param content 要解密的内容
     * @param privateKey 私钥
     */
    public static String decrypt(String content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte [] b = cipher.doFinal(content.getBytes());
            Decoder decoder = Base64.getDecoder();
            return new String(decoder.decode(b), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String转公钥PublicKey
     * @param key 公钥字符
     */
    public static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getMimeDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)keyFactory.generatePublic(keySpec);
    }

    /**
     * String转私钥PrivateKey
     * @param key 私钥字符
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getMimeDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
