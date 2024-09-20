package com.sied.clients.config.i18n;

/**
 * MessageSourceBasenames is a class that holds the base names of the resource bundles
 * for internationalization (i18n) messages in the application.
 */
public class MessageSourceBasenames {

    /**
     * An array of base names for the resource bundles.
     * These base names are used to load i18n messages from the specified locations.
     */
    public static final String[] BASE_NAMES = {
            "classpath:i18n/config/exceptions/messages",
            "classpath:i18n/crud/address/messages",
            "classpath:i18n/crud/boardOfDirector/messages",
            "classpath:i18n/crud/client/messages",
            "classpath:i18n/crud/controllingEntity/messages",
            "classpath:i18n/crud/corporateClient/messages",
            "classpath:i18n/crud/corporateStructure/messages",
            "classpath:i18n/crud/guarantee/messages",
            "classpath:i18n/crud/individualClient/messages",
            "classpath:i18n/crud/jointObligor/messages",
            "classpath:i18n/crud/person/messages",
            "classpath:i18n/crud/reference/messages",
            "classpath:i18n/crud/relatedPep/messages",
            "classpath:i18n/crud/shareholder/messages",
    };
}