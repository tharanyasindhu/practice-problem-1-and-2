import java.util.*;

class MultiLevelCache {
    private final int L1_SIZE = 2;
    // L1: In-memory LRU (accessOrder = true)
    private LinkedHashMap<String, String> l1 = new LinkedHashMap<>(L1_SIZE, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry eldest) { return size() > L1_SIZE; }
    };
    private Map<String, String> l3_db = new HashMap<>(); // Database

    public MultiLevelCache() {
        l3_db.put("vid1", "Action Movie Content");
        l3_db.put("vid2", "Comedy Show Content");
        l3_db.put("vid3", "Documentary Content");
    }

    public String getVideo(String id) {
        if (l1.containsKey(id)) {
            System.out.println("L1 HIT: " + id);
            return l1.get(id);
        }

        System.out.println("L1 MISS. Fetching from DB...");
        String data = l3_db.get(id);
        if (data != null) l1.put(id, data); // Promote to L1
        return data;
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();
        cache.getVideo("vid1");
        cache.getVideo("vid2");
        cache.getVideo("vid1"); // HIT
        cache.getVideo("vid3"); // Triggers LRU eviction of vid2
    }
}