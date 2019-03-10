/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcchserver;

//Security Imports
import java.security.Key;

//Crypto Imports
import javax.crypto.Cipher;

//util Imports
import java.util.Base64;


/**
 *
 * @author Hassan Khan
 */
public class RSA {
    
    static int RSA_KEY_LEN = 1024;
    static String ALGO_NAME = "RSA";
    static String MODE_OF_OP = "ECB";
    static String PAD_SCHEME = "OAEPWITHSHA-256ANDMGF1PADDING" ;
    
    //Encrypts given message with  RSA using Public key
    public static String rsaEncrypt(String message, Key publicKey) throws Exception{
    
        Cipher ciph = Cipher.getInstance(ALGO_NAME + "/" + MODE_OF_OP + "/" + PAD_SCHEME);
        ciph.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherTextArray = ciph.doFinal(message.getBytes());
        
        return Base64.getEncoder().encodeToString(cipherTextArray);
    
    }
    
    //Decrypts given message with RSA using Private Key
    public static String rsaDecrypt(byte[] encryptedMessage, Key privateKey) throws Exception{
    
        Cipher ciph = Cipher.getInstance(ALGO_NAME + "/" + MODE_OF_OP + "/" + PAD_SCHEME);
        ciph.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = ciph.doFinal(encryptedMessage);
        
        return new String(plainText);
    }
}
