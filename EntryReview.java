// Analogous to a "Bill" in a billing system — summarizes and finalizes an entry.
public class EntryReview {
    private DiaryEntry entry;

    public EntryReview(DiaryEntry entry) {
        this.entry = entry;
    }

    public void printReview() {
        int wordCount = entry.getContent().trim().isEmpty()
                ? 0
                : entry.getContent().trim().split("\\s+").length;
        int charCount = entry.getContent().length();
        String excerpt = entry.getContent().length() > 60
                ? entry.getContent().substring(0, 60) + "..."
                : entry.getContent();

        System.out.println("========================================");
        System.out.println("             ENTRY REVIEW                ");
        System.out.println("========================================");
        System.out.println("Entry ID   : " + entry.getId());
        System.out.println("Title      : " + entry.getTitle());
        System.out.println("Date       : " + entry.getDate());
        System.out.println("Mood       : " + entry.getMood());
        System.out.println("----------------------------------------");
        System.out.println("Excerpt    : " + excerpt);
        System.out.println("Word count : " + wordCount);
        System.out.println("Char count : " + charCount);
        System.out.println("========================================");
        entry.setReviewed(true);
    }
}