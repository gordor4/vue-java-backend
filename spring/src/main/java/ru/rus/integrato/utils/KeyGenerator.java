package ru.rus.integrato.utils;

import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public interface KeyGenerator {
	Key generateKey();
}
