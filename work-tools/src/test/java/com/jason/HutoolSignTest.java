package com.jason;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Jason
 * @Date 2023/04/03
 */
public class HutoolSignTest {

    String privateKey = null;
    String publicKey = null;
    String data = "hello world";
    String encryptData = null;
    String decryptData = null;


    @Before
    public void initKey() {
        RSA rsa = new RSA();
        privateKey = rsa.getPrivateKeyBase64();
        System.out.println("privateKey: " + privateKey);

        publicKey = rsa.getPublicKeyBase64();
        System.out.println("publicKey: " + publicKey);
    }

    @Test
    public void encrypt() {
        RSA rsa = new RSA(null, publicKey);
        byte[] encryptDataArr = rsa.encrypt(data, KeyType.PublicKey);

        encryptData = StrUtil.str(encryptDataArr, CharsetUtil.CHARSET_UTF_8);
        System.out.println("encryptData: " + encryptData);
    }

    @After
    public void decrypt() {
        RSA rsa = new RSA(privateKey, null);

        byte[] encryptByte = HexUtil.decodeHex(encryptData);
        byte[] decryptDataArr = rsa.decrypt(encryptByte, KeyType.PrivateKey);

        decryptData = StrUtil.str(decryptDataArr, CharsetUtil.CHARSET_UTF_8);
        System.out.println("decryptData: " + decryptData);
    }

}
