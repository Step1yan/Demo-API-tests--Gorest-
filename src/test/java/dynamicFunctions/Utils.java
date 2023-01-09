package dynamicFunctions;

import java.util.Random;

public class Utils {

    public static String getRandomEmail() {
        return "mazda" + new Random().nextInt(10000) + "@gmail.com";
    }

}
