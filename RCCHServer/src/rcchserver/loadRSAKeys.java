/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcchserver;

//Input Output Imports
import java.io.File;
import java.io.FileInputStream;


//Security Imports
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

//Utility Imports
import java.util.ArrayList;
/**
 *
 * @author Hassan Khan
 */
public class loadRSAKeys {
    
    // Loads the keys a client will need for connection
    public static ArrayList clientKey(String path) throws Exception{
    
        //Loads the server public key used for encryption
        File filePublicKey = new File(path + "/Server public.key");
        FileInputStream fIS = new FileInputStream(path + "/Server public.key");
        byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
        fIS.read(encodedPublicKey);
        fIS.close();
        
        //Loads the client private key used for decryption
        File filePrivateKey = new File(path + "/Client private.key");
        fIS = new FileInputStream(path + "/Client private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fIS.read(encodedPrivateKey);
        fIS.close();
        
        //instantiates the loaded values to PublicKey element
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        
        //instantiates the loaded values to PrivateKey element
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        
        //Prepares ArrayList for return
        ArrayList keys = new ArrayList();
        keys.add(privateKey);
        keys.add(publicKey);
        
        //Returns private and public key array for client
        return keys;
        
    }
    
        // Loads the keys a server will need for connection
    public static ArrayList serverKey(String path) throws Exception{
    
        //Loads the server public key used for encryption
        File filePublicKey = new File(path + "/Client public.key");
        FileInputStream fIS = new FileInputStream(path + "/Client public.key");
        byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
        fIS.read(encodedPublicKey);
        fIS.close();
        
        //Loads the client private key used for decryption
        File filePrivateKey = new File(path + "/Server private.key");
        fIS = new FileInputStream(path + "/Server private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fIS.read(encodedPrivateKey);
        fIS.close();
        
        //instantiates the loaded values to PublicKey element
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        
        //instantiates the loaded values to PrivateKey element
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        
        //Prepares ArrayList for return
        ArrayList keys = new ArrayList();
        keys.add(privateKey);
        keys.add(publicKey);
        
        //Returns private and public key array for client
        return keys;
        
    }
    
}
