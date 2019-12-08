///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package myUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * Teste dos algoritmos de encriptacao simetricos
 *
 * @author ZULU
 */
public class Crypt {

    public static Key generateKey(String algorithm) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static KeyPair generateKeyPair(String algorithm) {
        try {
            //criar um par de chaves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (Exception ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * encrypt data with simetric key
     *
     * @param data data to encrypt
     * @param key key of encriptation
     * @return encripted data
     */
    public static byte[] encrypt(byte[] data, Key key) {
        try {
            // chiper object with algorithm of the key
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            //configure to encrypt
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //encrypt data
            return cipher.doFinal(data);
        } catch (Exception ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
            // error in encryptation - return plain data
            return data;
        }

    }
    /**
     * decrypt data
     *
     * @param data encrypted data
     * @param key key of encryptation
     * @return decrypted data
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            //cipher object
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            //configure to decrypt
            cipher.init(Cipher.DECRYPT_MODE, key);
            //decrypt message
            return cipher.doFinal(data);
        } catch (Exception ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
            // error in encryptation - return plain data
            return data;
        }

    }
}
