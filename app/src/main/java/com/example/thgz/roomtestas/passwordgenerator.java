package com.example.thgz.roomtestas;

import java.util.concurrent.ThreadLocalRandom;

public class passwordgenerator {
    private static final int MIN_CODE = 33, MAX_CODE = 126;
    public static String process (int length) {
        StringBuilder builder = new StringBuilder();

for (int i = 0; i < length;  i++){
    builder.append((char) ThreadLocalRandom.current().nextInt(MIN_CODE,MAX_CODE + 1));
}

        return builder.toString();
    }

}
