package ru.rus.integrato.utils;

import org.springframework.stereotype.Component;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

@Component
public class SimpleKeyGenerator implements KeyGenerator
{
	public Key generateKey() {
		String keyString = "D>$7V~4$&h.t`c3*2oe'$V\"k9.=^K%0!^(B6q9@HFai2btcFwl^4u'{~BU?1]toFatrwekwql";

		return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA512");
	}
}
