package com.sied.clients.util.security;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageSource messageSource;
    public String getMessage(String code) {
        return getMessage(code, null);
    }
    public String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }
}
