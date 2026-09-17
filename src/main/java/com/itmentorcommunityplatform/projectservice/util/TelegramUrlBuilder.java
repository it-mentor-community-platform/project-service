package com.itmentorcommunityplatform.projectservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TelegramUrlBuilder {
    private final String TELEGRAM_URL_BASE = "https://t.me/";

    public String build(String username) {
        validateBlank(username);
        return TELEGRAM_URL_BASE +
                (username.startsWith("/") || username.startsWith("@") ?
                        username.substring(1) : username);
    }

    private void validateBlank(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
    }
}
