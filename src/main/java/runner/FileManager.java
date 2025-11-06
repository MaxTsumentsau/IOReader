package runner;

import exception.FileOperationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final String FILE_NAME = "data.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n=== Файловый менеджер ===");
        System.out.println("1. Записать данные в файл");
        System.out.println("2. Прочитать данные из файла");
        System.out.println("3. Выйти");
        System.out.print("Выберите действие: ");
    }

    public int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void writeDataToFile() {
        System.out.println("\n--- Запись данных в файл ---");
        System.out.print("Введите данные для записи (пустая строка для завершения): ");

        List<String> dataLines = new ArrayList<>();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            dataLines.add(line);
        }

        try {
            writeToFile(dataLines);
            System.out.println("Данные успешно записаны в файл: " + FILE_NAME);
        } catch (FileOperationException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Причина: " + e.getCause().getMessage());
            }
        }
    }

    public void readDataFromFile() {
        System.out.println("\n--- Чтение данных из файла ---");

        try {
            List<String> data = readFromFile();
            if (data.isEmpty()) {
                System.out.println("Файл пуст.");
            } else {
                System.out.println("Содержимое файла:");
                for (int i = 0; i < data.size(); i++) {
                    System.out.println((i + 1) + ": " + data.get(i));
                }
            }
        } catch (FileOperationException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Причина: " + e.getCause().getMessage());
            }
        }
    }

    public void writeToFile(List<String> data) throws FileOperationException {
        try {
            Path path = Paths.get(FILE_NAME);
            StandardOpenOption option = Files.exists(path) ?
                    StandardOpenOption.APPEND : StandardOpenOption.CREATE;
            Files.write(path, data, StandardOpenOption.WRITE, option);
        } catch (IOException e) {
            throw new FileOperationException("Не удалось записать данные в файл", e);
        }
    }

    public List<String> readFromFile() throws FileOperationException {
        try {
            Path path = Paths.get(FILE_NAME);
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileOperationException("Ошибка при чтении файла", e);
        }
    }
}
