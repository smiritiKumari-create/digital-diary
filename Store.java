import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Store {
    private DiaryBook diaryBook = new DiaryBook();
    private List<Admin> admins = new ArrayList<>();
    private Queue<DiaryEntry> reviewQueue = new LinkedList<>();
    private List<DiaryEntry> reviewedEntries = new ArrayList<>();
    public Store(List<Admin> initialAdmins) {
        admins.addAll(initialAdmins);
    }

    public DiaryBook getDiaryBook() { return diaryBook; }
    public List<Admin> getAdmins() { return admins; }

    public void enqueueForReview(DiaryEntry e) { reviewQueue.add(e); }

    public DiaryEntry processNextReview() {
        DiaryEntry e = reviewQueue.poll();
        if (e != null) reviewedEntries.add(e);
        return e;
    }
}