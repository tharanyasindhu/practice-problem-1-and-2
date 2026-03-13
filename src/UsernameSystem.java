import java.util.*;

public class UsernameSystem {
    private final Set<String> registeredUsers = new HashSet<>();
    private final Map<String, Integer> searchFrequency = new HashMap<>();

    public boolean checkAvailability(String username) {
        // Track how many times this specific name was searched (Popularity)
        searchFrequency.put(username, searchFrequency.getOrDefault(username, 0) + 1);
        return !registeredUsers.contains(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int suffix = 1;
        while (suggestions.size() < 3) {
            String candidate = username + suffix++;
            if (!registeredUsers.contains(candidate)) {
                suggestions.add(candidate);
            }
        }
        return suggestions;
    }

    public void register(String username) {
        registeredUsers.add(username);
    }

    public static void main(String[] args) {
        UsernameSystem sys = new UsernameSystem();
        sys.register("john_doe");

        System.out.println("Is 'john_doe' available? " + sys.checkAvailability("john_doe"));
        System.out.println("Suggestions: " + sys.suggestAlternatives("john_doe"));
        System.out.println("Search Count for 'john_doe': " + sys.searchFrequency.get("john_doe"));
    }
}