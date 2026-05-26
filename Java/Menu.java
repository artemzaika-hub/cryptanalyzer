import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    private String getInputFile() {
        System.out.print("Введите путь к входному файлу: ");
        String path = scanner.nextLine();
        while (!Validator.fileExists(path)) {
            System.out.println("Файл не найден.");
            System.out.print("Введите путь снова: ");
            path = scanner.nextLine();
        }
        return path;
    }

    private String getOutputFile() {
        System.out.print("Введите путь к выходному файлу: ");
        return scanner.nextLine();
    }

    private int getShiftFromUser() {
        while (true) {
            System.out.print("Введите ключ сдвига (0-25): ");
            try {
                int shift = Integer.parseInt(scanner.nextLine());
                if (Validator.isShiftValid(shift)) {
                    return shift;
                } else {
                    System.out.println("Ключ должен быть от 0 до 25.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private void encryptFile() {
        try {
            String in = getInputFile();
            String out = getOutputFile();
            int shift = getShiftFromUser();
            String text = FileManager.readFile(in);
            String encrypted = CaesarCipher.encrypt(text, shift);
            FileManager.writeFile(out, encrypted);
            System.out.println("Шифрование выполнено. Результат: " + out);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void decryptWithKey() {
        try {
            String in = getInputFile();
            String out = getOutputFile();
            int shift = getShiftFromUser();
            String text = FileManager.readFile(in);
            String decrypted = CaesarCipher.decrypt(text, shift);
            FileManager.writeFile(out, decrypted);
            System.out.println("Расшифровка выполнена. Результат: " + out);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void decryptBruteForce() {
        try {
            String in = getInputFile();
            String out = getOutputFile();
            String encrypted = FileManager.readFile(in);
            System.out.println("1. Показать все варианты и выбрать ключ");
            System.out.println("2. Автоматически выбрать лучший");
            System.out.print("Режим: ");
            String mode = scanner.nextLine();
            if (mode.equals("1")) {
                Map<Integer, String> variants = BruteForce.getAllVariants(encrypted);
                System.out.println("\n=== ВСЕ ВАРИАНТЫ ===");
                for (int key = 0; key < 26; key++) {
                    String preview = variants.get(key);
                    if (preview.length() > 100) preview = preview.substring(0, 100) + "...";
                    System.out.println("Ключ " + key + ": " + preview);
                }
                System.out.print("Введите ключ (0-25) для сохранения: ");
                int chosen = Integer.parseInt(scanner.nextLine());
                if (chosen >= 0 && chosen <= 25) {
                    FileManager.writeFile(out, variants.get(chosen));
                    System.out.println("Сохранён вариант с ключом " + chosen);
                } else {
                    System.out.println("Неверный ключ.");
                }
            } else {
                String result = BruteForce.decryptAuto(encrypted);
                FileManager.writeFile(out, result);
                System.out.println("Автоматический подбор завершён. Результат: " + out);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void decryptStatistics() {
        try {
            String in = getInputFile();
            String out = getOutputFile();
            String encrypted = FileManager.readFile(in);
            String result = StatisticsDecryptor.decrypt(encrypted);
            FileManager.writeFile(out, result);
            System.out.println("Статистическая расшифровка завершена. Результат: " + out);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("\n=== КРИПТОАНАЛИЗАТОР ===");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл (известен ключ)");
        System.out.println("3. Расшифровать файл (brute force)");
        System.out.println("4. Расшифровать файл (статистический метод)");
        System.out.println("5. Выход");
        System.out.print("Ваш выбор: ");
    }

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": encryptFile(); break;
                case "2": decryptWithKey(); break;
                case "3": decryptBruteForce(); break;
                case "4": decryptStatistics(); break;
                case "5":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный пункт. Попробуйте снова.");
            }
        }
    }
}
