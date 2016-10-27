package io.infectnet.server.service.encrypt;

/**
 * Interface for services providing hashed data.
 */
public interface EncrypterService {

    /**
     * Hashes a given String.
     * @param str the String to be hashed
     * @return the hashed string
     */
    String hash(String str);

    /**
     * Checks if the given String is equal to the given hashed string, converting the former with the same hash.
     * @param str the given string to check
     * @param hashed the given hash to compare to
     * @return true if the two Strings are equal, false otherwise
     */
    boolean check(String str, String hashed);
}
