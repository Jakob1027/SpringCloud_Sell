package com.jakob.order.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 时间+随机数
     *
     * @return 唯一id
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
