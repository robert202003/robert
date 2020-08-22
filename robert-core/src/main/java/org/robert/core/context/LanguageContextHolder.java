package org.robert.core.context;

/**
 * 多语言上下文信息类
 */
public class LanguageContextHolder {

    private static final ThreadLocal<String> languageHolder = new ThreadLocal<>();

    public static void setLanguage(String language) {
        languageHolder.set(language);
    }

    public static String getLanguage() {
        return languageHolder.get();
    }

    public static void clearLanguage() {
        languageHolder.remove();
    }
}
