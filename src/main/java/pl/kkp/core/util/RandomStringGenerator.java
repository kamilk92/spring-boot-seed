package pl.kkp.core.util;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomStringGenerator implements StringGenerator {
    private static final int FROM_CHAR_INCLUSIVE = 97;
    private static final int TO_CHAR_EXCLUSIVE = 123;

    private int strLength;

    public RandomStringGenerator(int strLength) {
        this.strLength = strLength;
    }

    public String generate() {
        return new Random().ints(strLength, FROM_CHAR_INCLUSIVE, TO_CHAR_EXCLUSIVE)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

    public void setStrLength(int strLength) {
        this.strLength = strLength;
    }
}
