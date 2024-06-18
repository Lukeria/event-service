package com.eventmanager.eventservice.service;

import java.util.UUID;

public class UuidUtil {
    public static String generate32Uuid() {
        return UUID.randomUUID().toString();
    }

    public static String generateUuid(int length) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, length);
    }
}
