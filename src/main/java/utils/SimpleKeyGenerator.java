package utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.inject.Default;

@Default
public class SimpleKeyGenerator implements KeyGenerator
{
	public Key generateKey() {
		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		return key;
	}
}
