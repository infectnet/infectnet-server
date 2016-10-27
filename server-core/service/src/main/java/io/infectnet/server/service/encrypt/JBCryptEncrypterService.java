package io.infectnet.server.service.encrypt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * {@link EncrypterService} implementation using JBCrypt as a library for hashing.
 */
public class JBCryptEncrypterService implements EncrypterService{
    @Override
    public String hash(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    @Override
    public boolean check(String str, String hashed) {
        return BCrypt.checkpw(str, hashed);
    }
}
