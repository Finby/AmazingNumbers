package numbers;

import java.util.ArrayList;
import java.util.List;

public enum Properties {
    BUZZ("buzz"),
    DUCK("duck"),
    PALINDROMIC("palindromic"),
    GAPFUL("gapful"),
    SPY("spy"),
    SQUARE("square"),
    SUNNY("sunny"),
    JUMPING("jumping"),
    HAPPY("happy"),
    SAD("sad"),
    EVEN("even"),
    ODD("odd");

    public String property;

    public static final Properties[] allProperties = Properties.values();

    Properties(String property) {
        this.property = property;
    }


    public static boolean getValue(String option) {
        try {
            Properties.valueOf(option.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidProperty(String property) {
        if (property.startsWith("-"))
            property = property.substring(1);

        return getValue(property);
    }

    public static boolean isExclusivePair(String option, String option2) {
        if (option.equals(option2))
            return false;
//        if (option.startsWith("-") && option2.startsWith("-")) {
//            option = option.substring(1);
//            option2 = option2.substring(1);
//        }

        option = option.toLowerCase();
        option2 = option2.toLowerCase();
        if ("even".equals(option) && "odd".equals(option2) ||
                "even".equals(option2) && "odd".equals(option) ||
                "-even".equals(option) && "-odd".equals(option2) ||
                "-even".equals(option2) && "-odd".equals(option) ||
                "duck".equals(option) && "spy".equals(option2) ||
                "duck".equals(option2) && "spy".equals(option) ||
                "sunny".equals(option) && "square".equals(option2) ||
                "sunny".equals(option2) && "square".equals(option) ||
                "sad".equals(option2) && "happy".equals(option) ||
                "sad".equals(option) && "happy".equals(option2) ||
                "-sad".equals(option2) && "-happy".equals(option) ||
                "-sad".equals(option) && "-happy".equals(option2) ||
                ("-"+option).equals(option2) || ("-"+option2).equals(option)
        ) {
            return true;
        }

        return false;
    }

    public static String[] difference(String[] copyOfRange) {
        List<String> tempRes = new ArrayList<>();
        for (int i = 0; i < copyOfRange.length ; i++) {
            if (!Properties.isValidProperty(copyOfRange[i])){
                tempRes.add(copyOfRange[i].toUpperCase());
            }
        }
        return tempRes.toArray(String[]::new);
    }

    public static List<String> findExlusivePairs(String[] copyOfRange) {
        List<String> tempRes = new ArrayList<>();
        for (int i = 0; i < copyOfRange.length ; i++) {
            for (int j = 0; j < copyOfRange.length; j++) {
                if (Properties.isExclusivePair(copyOfRange[i], copyOfRange[j])) {
                    tempRes.add(copyOfRange[i].toUpperCase());
                    tempRes.add(copyOfRange[j].toUpperCase());
                    return tempRes;
                }
            }
        }
        return tempRes;
    }
}
