package club.neru.io.config.languages;

import java.util.Arrays;

/**
 * 语言枚举类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
public enum LanguagesEnum {
    /**
     * 英文。
     */
    EN,

    /**
     * 简体中文。
     */
    ZH_CN;

    /**
     * 根据枚举名称的字符串值 (忽略大小写) 返回相应的枚举值。
     *
     * <p>
     * 如果找不到匹配项，则返回默认的英文。
     * </p>
     *
     * @param value 枚举名称的字符串值
     * @return 对应的枚举值，如果找不到则返回默认值 EN
     */
    public static LanguagesEnum caseInsensitiveValueOf(String value) {
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(EN);
    }
}
