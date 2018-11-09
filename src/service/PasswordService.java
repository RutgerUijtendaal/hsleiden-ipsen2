package service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Class responsible for the password hashing
 */
public class PasswordService {

    // Amount of times the algo runs. The higher the more secure vs bruteforce
    // and the longer it takes on the local machine
    private static int iterations = 1000;

    /**
     * Hash a password using PBKDF2
     *
     * @param password password to hash
     * @return String composed of hashed salt and hash: "salt:hash"
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String generatePasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64*8);
        // Specify the algo to use for creating the keys.
        // PBKDF2WithHmacSHA1 is Javas implementation of the PBKDF2 algo.
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
        return toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Check if a password is valid
     *
     * @param password password to check
     * @param storedHash hash to check against, gotten from db
     * @return true if pass is valid, false otherwise
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean isValidPassword(String password, String storedHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedHash.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = secretKeyFactory.generateSecret(spec).getEncoded();

        // Check if hashes are equal using bit operations.
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            // diff adds a bit every time the bits of hash and testhash aren't equal
            diff = diff | hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    /**
     * Gets a salt
     * @return byte array of an salt
     * @throws NoSuchAlgorithmException thrown when a wrong algorithm is asked for
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Makes a hex to an byte
     * @param hex to be converted
     * @return byte value of the inserted hex
     */
    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Make an hex from a byte
     * @param array to be converted
     * @return hex value of the inserted bytes
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
