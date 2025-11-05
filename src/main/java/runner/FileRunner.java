package runner;

public class FileRunner {
    public static FileManager fileManager = new FileManager();

    public FileRunner(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public static void main(String[] args) {
        while (true) {
            fileManager.showMenu();
            int choice = fileManager.getMenuChoice();

            switch (choice) {
                case 1:
                    fileManager.writeDataToFile();
                    break;
                case 2:
                    fileManager.readDataFromFile();
                    break;
                case 3:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
