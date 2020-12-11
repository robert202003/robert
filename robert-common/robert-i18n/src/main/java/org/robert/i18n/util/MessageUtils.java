package org.robert.i18n.util;

import lombok.extern.slf4j.Slf4j;
import org.robert.i18n.context.LangContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化配置读取key工具类
 */
@Component
@Slf4j
public class MessageUtils {

    private static final String DEFAULT_LANGUAGE = "en";

    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String key) {
        return get(key, null, null);
    }

    public static String get(String key, Object[] object) {
        return get(key, object, null);
    }

    public static String get(String key, String language) {
        return get(key, null, language);
    }

    public static String get(String key, Object[] objects, String language) {
        try {
            if (language == null) {
                language = LangContextHolder.getLanguage();
                language = language == null ? DEFAULT_LANGUAGE : language.replaceAll("-", "_");
            }
            return messageSource.getMessage(key, objects, new Locale(language));
        } catch (Exception e) {
            log.error("读取配置文件发生了异常");
            return key;
        }
    }

}
