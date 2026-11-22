import java.util.ArrayList;
import java.util.Scanner;
class Task {
    private String description;
    private boolean isCompleted;
    private int id;
    public Task(String description, int id) {
        this.description = description;
        this.isCompleted = false;
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        String status = isCompleted ? "✓" : "✗";
        return String.format("[%d] %s %s", id, status, description);
    }
}
public class TaskManager {
    private ArrayList<Task> tasks;
    private Scanner scanner;
    private int nextId;
    public TaskManager() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextId = 1;
    }
    public void run() {
        System.out.println("=== 📋 МЕНЕДЖЕР ЗАДАЧ ===");
        System.out.println("Добро пожаловать!");
        while (true) {
            showMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    showTasks();
                    break;
                case 3:
                    completeTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    System.out.println("До свидания! 👋");
                    return;
                default:
                    System.out.println("❌ Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
    }
    private void showMenu() {
        System.out.println("\n--- МЕНЮ ---");
        System.out.println("1. ➕ Добавить задачу");
        System.out.println("2. 📋 Показать все задачи");
        System.out.println("3. ✅ Отметить задачу выполненной");
        System.out.println("4. 🗑️  Удалить задачу");
        System.out.println("5. 📊 Статистика");
        System.out.println("6. 🚪 Выход");
        System.out.print("Выберите действие (1-6): ");
    }
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private void addTask() {
        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("❌ Описание не может быть пустым!");
            return;
        }
        Task task = new Task(description, nextId++);
        tasks.add(task);
        System.out.println("✅ Задача добавлена: " + task);
    }
    private void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Список задач пуст. Добавьте новую задачу!");
            return;
        }
        System.out.println("\n=== ВАШИ ЗАДАЧИ ===");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    private void completeTask() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Нет задач для выполнения.");
            return;
        }
        showTasks();
        System.out.print("Введите ID задачи для выполнения: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Task task = findTaskById(id);
            if (task == null) {
                System.out.println("❌ Задача с идентификатором " + id + " не найдена.");
                return;
            }
            if (task.isCompleted()) {
                System.out.println("ℹ️  Задача уже выполнена!");
            } else {
                task.setCompleted(true);
                System.out.println("🎉 Задача выполнена: " + task.getDescription());
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Введите корректный номер ID.");
        }
    }
    private void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Нет задач для удаления.");
            return;
        }
        showTasks();
        System.out.print("Введите ID задачи для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Task task = findTaskById(id);
            if (task == null) {
                System.out.println("❌ Задача с идентификатором " + id + " не найдена.");
                return;
            }
            tasks.remove(task);
            System.out.println("🗑️  Задача удалена: " + task.getDescription());
        } catch (NumberFormatException e) {
            System.out.println("❌ Введите корректный номер ID.");
        }
    }
    private void showStatistics() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Нет задач для анализа.");
            return;
        }
        int total = tasks.size();
        int completed = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed++;
            }
        }
        int pending = total - completed;
        double completionRate = (double) completed / total * 100;
        System.out.println("\n=== 📊 СТАТИСТИКА ===");
        System.out.println("📋 Всего задач: " + total);
        System.out.println("✅ Выполнено: " + completed);
        System.out.println("⏳ В процессе: " + pending);
        System.out.printf("📈 Процент выполнения: %.1f%%\n", completionRate);
        if (completionRate == 100) {
            System.out.println("🎉 Поздравляем! Все задачи выполнены!");
        }
    }
    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.run();
    }
}