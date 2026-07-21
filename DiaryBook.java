import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryBook {
    // Single source of truth for all diary entries.
    private Map<Integer, DiaryEntry> entryMap = new HashMap<>();

    public void addEntry(DiaryEntry e) {
        if (entryMap.containsKey(e.getId())) {
            throw new InvalidInputException("Diary entry ID " + e.getId() + " already exists.");
        }
        entryMap.put(e.getId(), e);
    }

    public DiaryEntry getEntryById(int id) throws DiaryNotFoundException {
        DiaryEntry e = entryMap.get(id);
        if (e == null) {
            throw new DiaryNotFoundException("Diary entry with ID " + id + " not found.");
        }
        return e;
    }

    public void deleteEntry(int id) throws DiaryNotFoundException {
        if (!entryMap.containsKey(id)) {
            throw new DiaryNotFoundException("Diary entry with ID " + id + " not found.");
        }
        entryMap.remove(id);
    }

    public List<DiaryEntry> viewAll() {
        return new ArrayList<>(entryMap.values());
    }

    // HashMap-backed title search — linear scan over values since the map is keyed by ID.
    public List<DiaryEntry> searchByTitle(String title) {
        List<DiaryEntry> results = new ArrayList<>();
        for (DiaryEntry e : entryMap.values()) {
            if (e.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(e);
            }
        }
        return results;
    }

    // Manual bubble sort, generalized over any Comparator (OCP: new sort criteria
    // don't require modifying this method — see Main's use of Comparator.comparing(...)).
    public List<DiaryEntry> bubbleSort(Comparator<DiaryEntry> comparator) {
        List<DiaryEntry> list = new ArrayList<>(entryMap.values());
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    DiaryEntry temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    // Collections.sort, generalized over any Comparator.
    public List<DiaryEntry> collectionsSort(Comparator<DiaryEntry> comparator) {
        List<DiaryEntry> list = new ArrayList<>(entryMap.values());
        Collections.sort(list, comparator);
        return list;
    }

    // Binary search by ID. Builds a sorted list from the map on demand,
    // so the HashMap stays the only stored state (no second structure to desync).
    public DiaryEntry binarySearchById(int id) throws DiaryNotFoundException {
        List<DiaryEntry> sorted = collectionsSort(Comparator.comparingInt(DiaryEntry::getId));
        int low = 0;
        int high = sorted.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midId = sorted.get(mid).getId();
            if (midId == id) {
                return sorted.get(mid);
            } else if (midId < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        throw new DiaryNotFoundException("Diary entry with ID " + id + " not found (binary search).");
    }
}