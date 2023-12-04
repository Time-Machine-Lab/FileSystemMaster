package com.example.rvc_filesystem;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author welsir
 * @Date 2023/12/4 23:38
 */
public class JaspytTest {

    @Test
    public void generateKey(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("genius_serve_0704");  // 替换为您的密钥

        // 配置项的键和值
        String key = "private-welsir,welsir-test,welsir";

        // 加密值
        String encryptedValue = textEncryptor.encrypt(key);

        // 输出结果
        System.out.println("Encrypted Value: ENC(" + encryptedValue + ")");
    }

}
