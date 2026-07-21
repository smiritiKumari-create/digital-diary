import java.util.Objects;

public class DiaryEntry implements Comparable<DiaryEntry> {
    private int id;
    private String title;
    private String content;
    private String date;   // format: YYYY-MM-DD, so plain string comparison sorts chronologically
    private String mood;
    private boolean reviewed;

    public DiaryEntry(int id, String title, String content, String date, String mood) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.mood = mood;
        this.reviewed = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public String getMood() { return mood; }
    public boolean isReviewed() { return reviewed; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setMood(String mood) { this.mood = mood; }
    public void setReviewed(boolean reviewed) { this.reviewed = reviewed; }

    @Override
    public int compareTo(DiaryEntry other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiaryEntry)) return false;
        return id == ((DiaryEntry) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d Date: %-12s Title: %-20s Mood: %-10s Reviewed: %s",
                id, date, title, mood, reviewed ? "Yes" : "No");
    }
}