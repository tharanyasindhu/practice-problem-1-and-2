import java.util.Map;     // Fixes: cannot find symbol Map
import java.util.HashMap; // Fixes: cannot find symbol HashMap
import java.util.Set;     // Fixes: cannot find symbol Set
import java.util.HashSet; // Fixes: cannot find symbol HashSet

public class AnalyticsDashboard {
    // dimension 1: total page views
    private final Map<String, Integer> pageViews = new HashMap<>();

    // dimension 2: unique visitors per page
    private final Map<String, Set<String>> uniqueUsers = new HashMap<>();

    public void logEvent(String url, String userId) {
        // Increment total views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Add user to the set (Sets automatically handle uniqueness)
        uniqueUsers.computeIfAbsent(url, k -> new HashSet<>()).add(userId);
    }

    public void printStats() {
        System.out.println("--- Real-Time Analytics ---");
        pageViews.forEach((url, views) -> {
            int uniqueCount = uniqueUsers.get(url).size();
            System.out.println("URL: " + url + " | Total Views: " + views + " | Unique Visitors: " + uniqueCount);
        });
    }

    public static void main(String[] args) {
        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        // Simulate some traffic
        dashboard.logEvent("/home", "user_1");
        dashboard.logEvent("/home", "user_1"); // repeat view from same user
        dashboard.logEvent("/home", "user_2");
        dashboard.logEvent("/products", "user_1");

        dashboard.printStats();
    }
}
