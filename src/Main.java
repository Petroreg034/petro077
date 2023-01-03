import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String output;
        String input;

        input = "1 + 2";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "1 - 2";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "1 * 2";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "1 / 2";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I + II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I - II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I * II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I / II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "100 + 200";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "3 + II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I - V";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I - V - II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "I - V-II";
        output = calc(input);
        System.out.println(input + ": " + output);

        input = "X * X";
        output = calc(input);
        System.out.println(input + ": " + output);

        System.out.print("Enter your expression: ");
        Scanner in = new Scanner(System.in);
        output = calc(in.nextLine());
        System.out.println(output);
    }

    public static String calc(String input){
        // input
        // "1 + 2"
        // "1 - 2"
        // "1 * 2"
        // "1 / 2"
        // "I + II"
        // "I - II"
        // "I * II"
        // "I / II"
        // "100 + 200" - error
        // "3 + II" - error
        // "I - V" - error
        // "I - V - II" - error
        // "I - V-II" - error
        // "X * X"
        String[] input_3 = input.split(" ");
        // System.out.println( String.join( " - ", input_3) );
        // input3[0] = "I"
        // input3[1] = "-"
        // input3[2] = "V-II"
        if (input_3.length != 3) {
            return "Error input data!";
        } else {
            HashSet<String> operations = new HashSet<>();
            operations.add("+");
            operations.add("-");
            operations.add("*");
            operations.add("/");
            if (!operations.contains(input_3[1])) {
                return "Error operation!";
            } else {
                int number_1 = -1;
                try{
                    number_1 = Integer.parseInt(input_3[0]);
                } catch (NumberFormatException ex){}
                int number_2 = -1;
                try{
                    number_2 = Integer.parseInt(input_3[2]);
                } catch (NumberFormatException ex){}
                if(number_1 < 0 || number_2 < 0 || number_1 > 10 || number_2 > 10){
                    String[] romeNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
                    int number_1_index = Arrays.asList(romeNumbers).indexOf(input_3[0]);
                    int number_2_index = Arrays.asList(romeNumbers).indexOf(input_3[2]);
                    if (number_1_index != -1 && number_2_index != -1) {
                        number_1 = number_1_index + 1;
                        number_2 = number_2_index + 1;
                        int output = -1;
                        switch(input_3[1]){
                            case "+":
                                output = number_1 + number_2;
                                break;
                            case "-":
                                output = number_1 - number_2;
                                break;
                            case "*":
                                output = number_1 * number_2;
                                break;
                            case "/":
                                output = number_1 / number_2;
                                break;
                        }
                        if(output <= 0) {
                            return "Error Rome numbers operation!";
                        } else {
                            // output to Rome numbers
                            // return Integer.toString(output);
                            return arabicToRoman(output);
                        }
                    } else {
                        return "Error in numbers!";
                    }
                } else {
                    int output = -1;
                    switch(input_3[1]){
                        case "+":
                            output = number_1 + number_2;
                            break;
                        case "-":
                            output = number_1 - number_2;
                            break;
                        case "*":
                            output = number_1 * number_2;
                            break;
                        case "/":
                            output = number_1 / number_2;
                            break;
                    }
                    return Integer.toString(output);
                }
            }
        }
    }

    // https://www.baeldung.com/java-convert-roman-arabic
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}