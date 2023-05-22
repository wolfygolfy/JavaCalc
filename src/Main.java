import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Создаю объект класса Scanner для получения строки, введённой с клавиатуры
        Scanner scan = new Scanner(System.in);

        // Приглашение для ввода
        System.out.println("Введите выражение (два операнда и один оператор)");

        // Запись введённой строки в переменную
        String expression = scan.nextLine();

        // Вывод в консоль результата, возвращенного методом calc
        System.out.println(Main.calc(expression));

    }

    public static String calc(String input) {
        // Создаю переменную-флаг, чтобы переключать ее в случае, если понадобиться вывести ответ римским числом
        boolean flag = false;

        // Выделяю память для переменной, в которую запишу первый операнд выражения
        int firstDigit = 0;

        //  Выделяю память для переменной, в которую запишу второй операнд выражения
        int secondDigit = 0;

        // Список для преобразования римских цифр в арабские с пмощоью их индекса.
        String[] romanNums = {
                "I",
                "II",
                "III",
                "IV",
                "V",
                "VI",
                "VII",
                "IIX",
                "IX",
                "X",
                "XI",
                "XII",
                "XIII",
                "XIV",
                "XV",
                "XVI",
                "XVII",
                "XVIII",
                "XIX",
                "XX"
        };

        // Разделяю полученую строку на части по пробелу в качестве разделителя
        String[] parts = input.split(" ");
        System.out.println(Arrays.toString(parts));

        // Декларирую переменную для записи результата выражения
        String result;

        // Проверяю, состоит ли введенное выражение из двух операндов и одного оператора, в противном случае выбрасываю исключение
        if (Array.getLength(parts) != 3) {
            try {
                throw new IOException();
            } catch (IOException e) {
                return "Введено некорректное выражение. В выражении должно быть только два операнда и один оператор.";
            }
        }
        // Проверяю, состоит ли выражение из римских чисел и, если это так, то преобразую римские числа в арабские и переключаю флаг
        if (Arrays.asList(romanNums).contains(parts[0]) && Arrays.asList(romanNums).contains(parts[2])){
            for (int i=0;romanNums[i].equals(parts[0]); i++){
                firstDigit = i + 1;
            }
            for (int i=0;romanNums[i].equals(parts[2]); i++){
                secondDigit = i + 1;
            }
            flag = true;

            // Проверяю, состоит ли выражение только из арабских чисел
        } else if (parts[0].matches("[-+]?\\d+") && parts[2].matches("[-+]?\\d+")) {
            firstDigit = Integer.parseInt(parts[0]);
            secondDigit = Integer.parseInt(parts[2]);
        }
        // Если выражение не состоит одновременно только из арабских или только из римских чисел, выбрасываю исключение
        else {
            try {
                throw new IOException();
            } catch (IOException e) {
                return "Введено некорректное выражение. " +
                        "Выражение должно состоять либо только из римских, либо только из арабских чисел";
            }
        }

        // Проверяю, не являются ли числа в выражении меньше единицы или больше десяти
        if ((0 < firstDigit && firstDigit <= 10) && (0 < secondDigit && secondDigit <= 10)) {
            // Вылавливаю действие, которое указано в выражении и записываю результат его выполнения в переменную result
            switch (parts[1]) {
                case "+":
                case "-":
                    secondDigit = Integer.parseInt(parts[1] + (secondDigit));
                    result = Integer.toString(firstDigit + secondDigit);
                    break;
                case "*":
                    result = Integer.toString(firstDigit * secondDigit);
                    break;
                case "/":
                    // В случае деления записываю ответ в виде числа с плавающей точкой
                    result = Float.toString(Float.parseFloat(parts[0]) / Float.parseFloat(parts[2]));
                    break;

                // Если действие в выражении отличается от сложения, вычитания, деления или умножения, выбрасываю исключение
                default:
                    try {
                        throw new IOException();
                    } catch (IOException e) {
                        return "Введено некорректное выражение";
                    }
            }

            // Проверяю, были ли входные числа римскими и получилось ли в итоге целое число больше нуля.
            if (flag && Integer.parseInt(result) > 0) {
                result = romanNums[Integer.parseInt(result) - 1];

                // В противном случае выкидываю исключение
            } else if (flag && result.contains(".") || Integer.parseInt(result) <= 0) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    return "Римские числа не могут быть неполными или меньше единицы";
                }
            }

            // Возвращаю значение переменной result в основную программу
            return result;

            // Если в выражении есть числа меньше или равные нулю или больше десяти, выбрасываю исключение
        } else {
            try {
                throw new IOException();
            } catch (IOException e) {
                return "Введены некорректные числа";
            }
        }
    }
}

