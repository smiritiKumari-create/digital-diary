import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Store store = new Store(buildDefaultAdmins());

    // Composition root: this is the one place that knows what the default
    // login account is. Store itself stays ignorant of it.
    private static List<Admin> buildDefaultAdmins() {
        List<Admin> defaults = new ArrayList<>();
        defaults.add(new Admin(1, "Admin", "9999999999", "admin@diary.com",
                "admin", "admin123", "Moderator"));
        return defaults;
    }

    public static void main(String[] args) {
        System.out.println("=== DIGITAL DIARY SYSTEM ===");
        if (!login()) {
            System.out.println("Login failed 3 times. Exiting.");
            return;
        }
        mainMenu();
        System.out.println("Goodbye!");
    }

    private static boolean login() {
        Login loginSystem = new Login(store.getAdmins());
        int attempts = 0;
        while (attempts < 3) {
            String username = Utils.readNonEmptyString(sc, "Username: ");
            String password = Utils.readNonEmptyString(sc, "Password: ");
            if (loginSystem.authenticate(username, password)) {
                System.out.println("Login successful. Welcome, " + loginSystem.getLoggedInAdmin().getName() + "!");
                return true;
            }
            attempts++;
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
        }
        return false;
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n----- MAIN MENU -----");
            System.out.println("1. Diary Entry Management");
            System.out.println("2. Review Queue");
            System.out.println("3. Search & Sort Demo");
            System.out.println("4. Exit");
            int choice = Utils.readInt(sc, "Choice: ");
            switch (choice) {
                case 1: diaryMenu(); break;
                case 2: reviewMenu(); break;
                case 3: searchSortMenu(); break;
                case 4: running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void diaryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Diary Entry Management --");
            System.out.println("1. Add Entry");
            System.out.println("2. View All Entries");
            System.out.println("3. Update Entry");
            System.out.println("4. Delete Entry");
            System.out.println("5. Back");
            int choice = Utils.readInt(sc, "Choice: ");
            try {
                switch (choice) {
                    case 1: addEntry(); break;
                    case 2: viewAllEntries(); break;
                    case 3: updateEntry(); break;
                    case 4: deleteEntry(); break;
                    case 5: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (DiaryNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addEntry() {
        int id = Utils.readInt(sc, "ID: ");
        String title = Utils.readNonEmptyString(sc, "Title: ");
        String content = Utils.readNonEmptyString(sc, "Content: ");
        String date = Utils.readValidDate(sc, "Date (YYYY-MM-DD): ");
        String mood = Utils.readNonEmptyString(sc, "Mood: ");
        store.getDiaryBook().addEntry(new DiaryEntry(id, title, content, date, mood));
        System.out.println("Entry added.");
    }

    private static void viewAllEntries() {
        List<DiaryEntry> all = store.getDiaryBook().viewAll();
        if (all.isEmpty()) {
            System.out.println("No diary entries yet.");
            return;
        }
        for (DiaryEntry e : all) System.out.println(e);
    }

    private static void updateEntry() throws DiaryNotFoundException {
        int id = Utils.readInt(sc, "ID to update: ");
        DiaryEntry e = store.getDiaryBook().getEntryById(id);
        System.out.println("Current: " + e);

        boolean updating = true;
        while (updating) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Title");
            System.out.println("2. Content");
            System.out.println("3. Date");
            System.out.println("4. Mood");
            System.out.println("5. Done");
            int choice = Utils.readInt(sc, "Choice: ");
            switch (choice) {
                case 1:
                    e.setTitle(Utils.readNonEmptyString(sc, "New title: "));
                    System.out.println("Title updated.");
                    break;
                case 2:
                    e.setContent(Utils.readNonEmptyString(sc, "New content: "));
                    System.out.println("Content updated.");
                    break;
                case 3:
                    e.setDate(Utils.readValidDate(sc, "New date (YYYY-MM-DD): "));
                    System.out.println("Date updated.");
                    break;
                case 4:
                    e.setMood(Utils.readNonEmptyString(sc, "New mood: "));
                    System.out.println("Mood updated.");
                    break;
                case 5:
                    updating = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        System.out.println("Updated: " + e);
    }

    private static void deleteEntry() throws DiaryNotFoundException {
        int id = Utils.readInt(sc, "ID to delete: ");
        store.getDiaryBook().deleteEntry(id);
        System.out.println("Deleted.");
    }

    private static void reviewMenu() {
        System.out.println("\n-- Review Queue --");
        System.out.println("1. Enqueue Entry for Review");
        System.out.println("2. Process Next Review");
        System.out.println("3. Back");
        int choice = Utils.readInt(sc, "Choice: ");
        try {
            switch (choice) {
                case 1: enqueueReview(); break;
                case 2: processReview(); break;
                case 3: break;
                default: System.out.println("Invalid choice.");
            }
        } catch (DiaryNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void enqueueReview() throws DiaryNotFoundException {
        int id = Utils.readInt(sc, "Entry ID to enqueue: ");
        DiaryEntry e = store.getDiaryBook().getEntryById(id);
        store.enqueueForReview(e);
        System.out.println("Entry #" + e.getId() + " added to review queue.");
    }

    private static void processReview() {
        DiaryEntry e = store.processNextReview();
        if (e == null) {
            System.out.println("Review queue is empty.");
            return;
        }
        new EntryReview(e).printReview();
    }

    private static void searchSortMenu() {
        System.out.println("\n-- Search & Sort Demo --");
        System.out.println("1. Search by Title (HashMap-backed)");
        System.out.println("2. Binary Search by ID");
        System.out.println("3. Bubble Sort by Date");
        System.out.println("4. Collections.sort by ID");
        System.out.println("5. Back");
        int choice = Utils.readInt(sc, "Choice: ");
        try {
            switch (choice) {
                case 1:
                    String title = Utils.readNonEmptyString(sc, "Title to search: ");
                    List<DiaryEntry> results = store.getDiaryBook().searchByTitle(title);
                    if (results.isEmpty()) System.out.println("No matches.");
                    for (DiaryEntry e : results) System.out.println(e);
                    break;
                case 2:
                    int id = Utils.readInt(sc, "ID to search: ");
                    DiaryEntry found = store.getDiaryBook().binarySearchById(id);
                    System.out.println("Found: " + found);
                    break;
                case 3:
                    for (DiaryEntry e2 : store.getDiaryBook().bubbleSort(Comparator.comparing(DiaryEntry::getDate))) {
                        System.out.println(e2);
                    }
                    break;
                case 4:
                    for (DiaryEntry e3 : store.getDiaryBook().collectionsSort(Comparator.comparingInt(DiaryEntry::getId))) {
                        System.out.println(e3);
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (DiaryNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}