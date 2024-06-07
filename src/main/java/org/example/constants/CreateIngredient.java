package org.example.constants;

import org.apache.commons.lang3.RandomUtils;

public class CreateIngredient {

    public static int createSizeListIngredient(int max) {
        return RandomUtils.nextInt(1, max);
    }
}
