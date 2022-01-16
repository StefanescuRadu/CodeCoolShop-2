package com.codecool.shop.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum OrderType {
    CHECKED,
    PAID,
    CONFIRMED,
    SHIPPED;

    private static final List<OrderType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static OrderType randomLetter()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

