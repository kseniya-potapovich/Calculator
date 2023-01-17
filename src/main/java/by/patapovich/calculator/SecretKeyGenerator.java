package by.patapovich.calculator;

import by.patapovich.calculator.util.EncryptUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(EncryptUtil.ALGORITHM);
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();
        FileOutputStream outputStream = new FileOutputStream(EncryptUtil.KEY_FILE);
        outputStream.write(Base64.getEncoder().encode(key.getEncoded()));
        outputStream.close();
    }
}
