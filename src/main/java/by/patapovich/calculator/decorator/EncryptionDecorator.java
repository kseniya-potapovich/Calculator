package by.patapovich.calculator.decorator;

import by.patapovich.calculator.exception.CustomException;
import by.patapovich.calculator.persistence.DataSource;
import by.patapovich.calculator.util.EncryptUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    private static final SecretKey key;

    static {
        try (FileInputStream inputStream = new FileInputStream(EncryptUtil.KEY_FILE)) {
            byte[] keyBytes = new byte[32];
            inputStream.read(keyBytes);
            key = new SecretKeySpec(Base64.getDecoder().decode(keyBytes), EncryptUtil.ALGORITHM);
        } catch (IOException e) {
            throw new CustomException("Error while reading key");
        }
    }

    private byte[] encrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(EncryptUtil.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encode(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("Wrong encrypt algorithm");
        } catch (IllegalBlockSizeException e) {
            throw new CustomException("Wrong block size");
        } catch (InvalidKeyException | BadPaddingException | NoSuchPaddingException e) {
            throw new CustomException("Invalid key");
        }

    }

    private byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(EncryptUtil.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("Wrong encrypt algorithm");
        } catch (IllegalBlockSizeException e) {
            throw new CustomException("Wrong block size");
        } catch (InvalidKeyException | BadPaddingException | NoSuchPaddingException e) {
            throw new CustomException("Invalid key");
        }
    }

    @Override
    public byte[] read() {
        return decrypt(super.read());
    }

    @Override
    public void write(byte[] data) {
        super.write(encrypt(data));
    }
}
