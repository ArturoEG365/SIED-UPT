package com.sied.clients.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * InternationalizationConfig is a configuration class for setting up internationalization (i18n) in the application.
 * It defines beans for message source and locale resolver.
 */
@Configuration
public class InternationalizationConfig {

    /**
     * Configures the message source for loading i18n messages from resource bundles.
     *
     * @return a ReloadableResourceBundleMessageSource configured with base names and default encoding.
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MessageSourceBasenames.BASE_NAMES);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Configures the locale resolver to resolve locales based on the "Accept-Language" header.
     * Sets the default locale to US.
     *
     * @return an AcceptHeaderLocaleResolver configured with the default locale.
     */
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
