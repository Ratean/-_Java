import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivilegedActionException;
import java.util.*;

class Calculator {

    private final static TreeMap<Integer, String> mapRoman = new TreeMap<Integer, String>();

    static {

        mapRoman.put(10, "X");
        mapRoman.put(9, "IX");
        mapRoman.put(5, "V");
        mapRoman.put(4, "IV");
        mapRoman.put(1, "I");

    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = "";

        while (true) {
            s = bf.readLine();
            if (s.matches("\\d{1,2}\\s{1}[+|*|\\-|/]\\s{1}\\d{1,2}")) {
                calculationArabic(s);
            } else if (s.matches("[IVX]{1,4}\\s{1}[+|*|\\-|/]\\s[IVX]{1,4}")) {
                calculationRoman(s);
            } else if (s.matches("\\w+")) {
                System.out.println("throws Exception //т.к. строка не является математической операцией");
                throw new IllegalArgumentException();
            } else if (s.matches("\\d{1,2}\\s{1}[+|*|\\-|/]\\s{1}[IVX]{1,4}") ||
                          s.matches("[IVX]{1,4}\\s{1}[+|*|\\-|/]\\s{1}\\d{1,2}")) {
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                throw new IllegalArgumentException();
            } else if (s.matches("\\d{1,2}\\s{1}[+|*|\\-|/]\\s{1}\\d{1,2}\\s{1}[+|*|\\-|/]\\s{1}\\d{1,2}") ||
                    s.matches("[IVX]{1,4}\\s{1}[+|*|\\-|/]\\s[IVX]{1,4}\\s{1}[+|*|\\-|/]\\s[IVX]{1,4}")) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                throw new IllegalArgumentException();
            }
            else {
                throw new IllegalArgumentException();
            }
        }

    }

    private static void calculationArabic (String s) {
        String[] array = s.split(" ");
        System.out.println(calculation(Integer.valueOf(array[0]), Integer.valueOf(array[2]), array[1]));

    }

    private static void calculationRoman (String s) {
        String[] array = s.split(" ");
        int result = calculation(romanToArabic(array[0]), romanToArabic(array[2]), array[1]);
        if (result < 0) {
            System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
            throw new IllegalArgumentException();
        } else if (result == 0) throw new IllegalArgumentException();
        System.out.println(arabicToRoman(result));
    }
    private static int calculation (int a, int b, String operation) {
        int result = 0;
        if (a > 10 || b >10) throw new IllegalArgumentException();
        if (operation.matches("[\\-|+]")) {
            if (operation.equals("+")) result = a+b;
            else result = a-b;
        } else {
            if (operation.equals("*")) result = a*b;
            else result = a/b;
        }
        return result;
    }


    private final static String arabicToRoman(int number) {
        int l =  mapRoman.floorKey(number);
        if ( number == l ) {
            return mapRoman.get(number);
        }
        return mapRoman.get(l) + arabicToRoman(number-l);
    }

    private static int romanToArabic(String romanNumeral) {
        int result = 0;

        TreeMap<Integer, String> mapRomanReverse = new TreeMap<>(Collections.reverseOrder());
        mapRomanReverse.putAll(mapRoman);

        while ((romanNumeral.length() > 0)) {
            for (Map.Entry<Integer, String> entry: mapRomanReverse.entrySet()) {
                String symbol = entry.getValue();
                if (romanNumeral.startsWith(symbol)) {
                    result += entry.getKey();
                    romanNumeral = romanNumeral.substring(entry.getValue().length());
                }
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException();
        }

        return result;
    }

}


