public class EntryReview {
    private DiaryEntry entry;

    public EntryReview(DiaryEntry entry) {
        this.entry = entry;
    }

    public void printReview() {

        // Validate Date (Expected format: dd/MM/yyyy)
        String date = entry.getDate();

        try {
            String[] parts = date.split("/");

            if (parts.length != 3) {
                System.out.println("Invalid Date Format! Please use dd/MM/yyyy.");
                return;
            }

            int month = Integer.parseInt(parts[1]);

            if (month < 1 || month > 12) {
                System.out.println("Invalid Month! Month should be between 1 and 12.");
                return;
            }

        } catch (Exception e) {
            System.out.println("Invalid Date!");
            return;
        }

        // Calculate word count
        int wordCount = entry.getContent().trim().isEmpty()
                ? 0
                : entry.getContent().trim().split("\\s+").length;

        // Calculate character count
        int charCount = entry.getContent().length();

        // Generate excerpt
        String excerpt = entry.getContent().length() > 60
                ? entry.getContent().substring(0, 60) + "..."
                : entry.getContent();

        // Print review
        System.out.println("========================================");
        System.out.println("             ENTRY REVIEW               ");
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

        // Mark entry as reviewed
        entry.setReviewed(true);
    }
}