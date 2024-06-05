package org.example.constants;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    //генерирование случайных значений
    public static String RANDOM_EMAIL = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
    public static String RANDOM_PASSWORD = RandomStringUtils.randomNumeric(5);
    public static String RANDOM_NAME = RandomStringUtils.randomAlphabetic(10);
    public static String RANDOM_INT = RandomStringUtils.randomNumeric(5);

}
