package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Interactor.printWelcome();
        Interactor.printMenu();
        while (true) {
            String[] userInput = Interactor.readInput();
            if (userInput == null)
                continue;
            if ("0".equals(userInput[0])) {
                Interactor.printGoodbye();
                break;
            }

            if (userInput.length == 1) {
                Interactor.singleNumber(Long.valueOf(userInput[0]));
            } else if (userInput.length == 2) {
                Interactor.twoNumbers(Long.valueOf(userInput[0]), Long.valueOf(userInput[1]));
            } else if (userInput.length == 3) {
                Interactor.threeNumbers(Long.valueOf(userInput[0]), Long.valueOf(userInput[1]), userInput[2]);
            } else if (userInput.length == 4) {
                Interactor.fourNumbers(Long.valueOf(userInput[0]), Long.valueOf(userInput[1]),
                        userInput[2], userInput[3]);
            }else if (userInput.length >= 5) {
                Interactor.moreThanFourNumbers(Long.valueOf(userInput[0]), Long.valueOf(userInput[1]),
                        Arrays.copyOfRange(userInput, 2, userInput.length));
            }

        }

    }


}

class Interactor {
    public static void printWelcome() {
        System.out.println("Welcome to Amazing Numbers!");
    }

    public static void printGoodbye() {
        System.out.println("Goodbye!");
    }

    public static void printMenu() {
        System.out.println();
        System.out.println(
                "Supported requests:\n" +
                        "- enter a natural number to know its properties;\n" +
                        "- enter two natural numbers to obtain the properties of the list:\n" +
                        "  * the first parameter represents a starting number;\n" +
                        "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                        "- two natural numbers and properties to search for;\n" +
                        "- a property preceded by minus must not be present in numbers;\n" +
                        "- separate the parameters with one space;\n" +
                        "- enter 0 to exit."
        );
        System.out.println();
    }

    public static String[] readInput() {
        System.out.print("Enter a request: ");
        Scanner sc = new Scanner(System.in);
        String[] tempInput = sc.nextLine().split(" ");

        if (tempInput.length == 0)
            return null;

        Long resultLong1 = null;
        Long resultLong2 = 0L;
        String option = "";
        String option2 = "";
        try {
            resultLong1 = Long.valueOf(tempInput[0]);
            if (tempInput.length == 2)
                resultLong2 = Long.valueOf(tempInput[1]);

            if (tempInput.length == 3)
                option = tempInput[2];

            if (tempInput.length == 4) {
                option = tempInput[2];
                option2 = tempInput[3];
            }
        } catch (Exception e) {
        }

        if (resultLong1 == null || resultLong1 < 0) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return null;
        }
        if (tempInput.length == 2 && (resultLong2 == null || resultLong2 < 0)) {
            System.out.println("\nThe second parameter should be a natural number.\n");
            return null;
        }
        if (tempInput.length == 3 && (option == null || !Properties.isValidProperty(option))) {
            System.out.printf("\nThe property [%s] is wrong.\n", option.toUpperCase());
            System.out.println("Available properties: " + Arrays.deepToString(Properties.allProperties) + "\n");
            return null;
        }
        if (tempInput.length == 4) {
            if ((option == null || !Properties.isValidProperty(option)) && (option2 == null || !Properties.isValidProperty(option2))) {
                System.out.printf("\nThe properties [%s, %s] are wrong.\n", option.toUpperCase(), option2.toUpperCase());
                System.out.println("Available properties: " + Arrays.deepToString(Properties.allProperties) + "\n");
                return null;
            } else if (option == null || !Properties.isValidProperty(option)) {
                System.out.printf("\nThe property [%s] is wrong.\n", option.toUpperCase());
                System.out.println("Available properties: " + Arrays.deepToString(Properties.allProperties) + "\n");
                return null;
            } else if (option2 == null || !Properties.isValidProperty(option2)) {
                System.out.printf("\nThe property [%s] is wrong.\n", option2.toUpperCase());
                System.out.println("Available properties: " + Arrays.deepToString(Properties.allProperties) + "\n");
                return null;
            } else if (Properties.isExclusivePair(option, option2)) {
                System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", option.toUpperCase(), option2.toUpperCase());
                System.out.println("There are no numbers with these properties.\n");
                return null;
            }
        }
        if (tempInput.length >= 5) {
            String[] differences = Properties.difference(Arrays.copyOfRange(tempInput, 2, tempInput.length));
            if (differences.length > 0) {
                if (differences.length == 1) {
                    System.out.printf("\nThe property [%s] is wrong\n", String.join(", ", differences));
                } else if (differences.length > 1) {
                    System.out.printf("\nThe properties [%s] are wrong\n", String.join(", ", differences));
                }
                System.out.println("Available properties: " + Arrays.deepToString(Properties.allProperties) + "\n");
                return null;
            }
            List<String> exclusiveOptions =
                    Properties.findExlusivePairs(Arrays.copyOfRange(tempInput, 2, tempInput.length));
            if (exclusiveOptions.size() > 0) {
                System.out.printf("\nThe request contains mutually exclusive properties: [%s]\n",
                        String.join(", ", exclusiveOptions.toArray(String[]::new)));
                System.out.println("There are no numbers with these properties.\n");
                return null;
            }
        }


        return tempInput;
    }

    public static boolean isTwoWords(String str) {
        return str.replaceAll(" ", " ").split(" ").length == 2;
    }

    public static String[] splitStringToWords(String str) {
        return str.replaceAll(" ", " ").split(" ");
    }

    public static void twoNumbers(Long numberStart, Long rangeNmber) {
        System.out.println();
        for (Long i = numberStart; i < numberStart + rangeNmber; i++) {
            MyNumber nb = new MyNumber(i);
            nb.printShortDetails();
        }
        System.out.println();
    }

    public static void singleNumber(Long valueOf) {
        MyNumber nb = new MyNumber(valueOf);
        nb.printDetailedInfo();
    }

    public static void threeNumbers(Long startNumber, Long numberOfFindings, String optionToFind) {
        System.out.println();
        while (numberOfFindings > 0) {
            MyNumber numberTest = new MyNumber(startNumber);
            if (numberTest.isOption(optionToFind.toUpperCase())) {
                numberTest.printShortDetails();
                numberOfFindings--;
            }
            startNumber++;
        }
        System.out.println();
    }

    public static void fourNumbers(Long startNumber, Long numberOfFindings, String option, String option1) {
        System.out.println();
        while (numberOfFindings > 0) {
            MyNumber numberTest = new MyNumber(startNumber);
            if (numberTest.isOption(option.toUpperCase()) && numberTest.isOption(option1.toUpperCase())) {
                numberTest.printShortDetails();
                numberOfFindings--;
            }
            startNumber++;
        }
        System.out.println();
    }

    public static void moreThanFourNumbers(Long startNumber, Long numberOfFindings, String[] copyOfRange) {
        System.out.println();
        while (numberOfFindings > 0) {
            MyNumber numberTest = new MyNumber(startNumber);
            if (Arrays.stream(copyOfRange).allMatch(option -> numberTest.isOption(option.toUpperCase()))) {
                numberTest.printShortDetails();
                numberOfFindings--;
            }
            startNumber++;
        }
        System.out.println();
    }
}

class MyNumber {
    private Long myNumber;

    public MyNumber(Long myNumber) {
        this.myNumber = myNumber;
    }

    public void printShortDetails() {
        StringBuilder strBuild = new StringBuilder();
        strBuild
                .append(this.myNumber).append(" is")
                .append(this.isEven() ? " even," : " odd,")
                .append(this.isBuzz() ? " buzz," : "")
                .append(this.isDuck() ? " duck," : "")
                .append(this.isPalindromic() ? " palindromic," : "")
                .append(this.isGapful() ? " gapful," : "")
                .append(this.isSpy() ? " spy," : "")
                .append(this.isSquare() ? " square," : "")
                .append(this.isSunny() ? " sunny," : "")
                .append(this.isJumping() ? " jumping," : "")
                .append(this.isHappy() ? " happy," : "sad,");
        strBuild.setLength(strBuild.length() - 1);
        System.out.println(strBuild);
    }

    private boolean isJumping() {
        List<Integer> digits = Arrays.stream(this.myNumber.toString().split("")).map(Integer::valueOf).collect(Collectors.toList());
        for (int i = 0; i < digits.size() - 1; i++) {
            if (Math.abs(digits.get(i) - digits.get(i + 1)) != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isGapful() {
        if (String.valueOf(myNumber).length() >= 3) {
            String myNumberStr = String.valueOf(myNumber);
            StringBuilder stringBuilder = new StringBuilder()
                    .append(myNumberStr.charAt(0))
                    .append(myNumberStr.charAt(myNumberStr.length() - 1));
            Long testerNumber = Long.valueOf(stringBuilder.toString());
            if (myNumber % testerNumber == 0)
                return true;
        }
        return false;
    }

    public boolean isBuzz() {
        return (myNumber % 10 == 7) || (myNumber % 7 == 0);
    }

    public boolean isEven() {
        return myNumber % 2 == 0;
    }

    public boolean isSad() {
        return !isHappy();
    }

    public boolean isHappy() {
        Long tempNumber = this.myNumber;
        List<Long> metNumbers = new ArrayList<>();
        do {
            metNumbers.add(tempNumber);
            tempNumber = sumOfDigitsSquares(tempNumber);
        }
        while (tempNumber != 1 && metNumbers.indexOf(tempNumber) == -1);

        if (tempNumber == 1)
            return true;
        else
            return false;
    }

    private Long sumOfDigitsSquares(Long tempNumber) {
        Long res = 0L;
        Long d1 = tempNumber % 10;
        res = d1 * d1;
        tempNumber = tempNumber / 10;
        while (tempNumber > 0) {
            d1 = tempNumber % 10;
            res += d1 * d1;
            tempNumber = tempNumber / 10;
        }

        return res;
    }


    public boolean isOdd() {
        return !isEven();
    }

    public boolean isDuck() {
        return String.valueOf(myNumber).substring(1).contains("0");
    }

    private boolean isPalindromic() {
        StringBuilder strBuilder = new StringBuilder(String.valueOf(myNumber));
        return strBuilder.toString().equals(strBuilder.reverse().toString());
    }

    public void printDetailedInfo() {
        System.out.printf("\nProperties of %s\n" +
                        "        buzz: %b\n" +
                        "        duck: %b\n" +
                        " palindromic: %b\n" +
                        "      gapful: %b\n" +
                        "         spy: %b\n" +
                        "      square: %b\n" +
                        "       sunny: %b\n" +
                        "     jumping: %b\n" +
                        "        even: %b\n" +
                        "         odd: %b\n" +
                        "       happy: %b\n" +
                        "         sad: %b\n",
                myNumber,
                isBuzz(),
                isDuck(),
                isPalindromic(),
                isGapful(),
                isSpy(),
                isSquare(),
                isSunny(),
                isJumping(),
                isEven(),
                isOdd(),
                isHappy(),
                isSad()
        );
        System.out.println();
    }

    private boolean isSunny() {
        return isSquare(this.myNumber + 1);
    }

    private boolean isSquare(Long number) {
        double squareDouble = Math.sqrt(number);
        long squareLong = Math.round(squareDouble);
        return squareDouble == squareLong * 1.0;
    }

    private boolean isSquare() {
        return isSquare(this.myNumber);
//        return Long.valueOf(String.valueOf(squareDouble)) == squareLong;
    }

    private boolean isSpy() {
        List<Integer> numberDigits = getDigits(this.myNumber);
        return numberDigits.stream().reduce((x, y) -> x + y).get() ==
                numberDigits.stream().reduce((x, y) -> x * y).get();
    }

    public static List<Integer> getDigits(Long number) {
        return Arrays.stream(String.valueOf(number).split(""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }


    public boolean isOption(String option) {
        if (option.startsWith("-"))
            return !isOption(option.substring(1));

        Properties prop = Properties.valueOf(option.toUpperCase());
        switch (prop) {
            case BUZZ:
                return this.isBuzz();
            case DUCK:
                return this.isDuck();
            case PALINDROMIC:
                return this.isPalindromic();
            case GAPFUL:
                return this.isGapful();
            case SPY:
                return this.isSpy();
            case SQUARE:
                return this.isSquare();
            case SUNNY:
                return this.isSunny();
            case JUMPING:
                return this.isJumping();
            case EVEN:
                return this.isEven();
            case ODD:
                return this.isOdd();
            case HAPPY:
                return this.isHappy();
            case SAD:
                return this.isSad();
        }
        return false;
    }
}

