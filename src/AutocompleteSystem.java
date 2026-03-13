import java.util.*;

public class AutocompleteSystem {
    private final Map<String, Integer> frequencies = new HashMap<>();

    public void update(String query) {
        frequencies.put(query, frequencies.getOrDefault(query, 0) + 1);
    }

    public List<String> getSuggestions(String prefix) {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> b.getValue().equals(a.getValue()) ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue()
        );

        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                pq.offer(entry);
            }
        }

        List<String> results = new ArrayList<>();
        while (!pq.isEmpty() && results.size() < 3) results.add(pq.poll().getKey());
        return results;
    }

    public static void main(String[] args) {
        AutocompleteSystem ac = new AutocompleteSystem();
        ac.update("java tutorial");
        ac.update("java tutorial");
        ac.update("javascript");
        System.out.println("Suggestions for 'jav': " + ac.getSuggestions("jav"));
    }
}