import java.util.*; // <-- THIS FIXES THE "CANNOT FIND SYMBOL" ERROR

public class PlagiarismDetector {
    // Stores: "ngram string" -> Set of IDs [DocA, DocB]
    private final Map<String, Set<String>> index = new HashMap<>();

    public void addDocument(String docId, String content) {
        String[] words = content.toLowerCase().split("\\s+");
        // Sliding window to create 3-grams
        for (int i = 0; i <= words.length - 3; i++) {
            String ngram = words[i] + " " + words[i+1] + " " + words[i+2];
            index.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
        }
    }

    public void check(String newDoc) {
        Map<String, Integer> matchCount = new HashMap<>();
        String[] words = newDoc.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - 3; i++) {
            String ngram = words[i] + " " + words[i+1] + " " + words[i+2];
            if (index.containsKey(ngram)) {
                for (String docId : index.get(ngram)) {
                    matchCount.put(docId, matchCount.getOrDefault(docId, 0) + 1);
                }
            }
        }

        System.out.println("--- Plagiarism Report ---");
        if (matchCount.isEmpty()) {
            System.out.println("No matches found. Original content.");
        } else {
            matchCount.forEach((id, count) ->
                    System.out.println("Document: " + id + " | Shared N-Grams: " + count));
        }
    }

    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector();
        detector.addDocument("DOC_001", "Java is a popular programming language used for web apps");
        detector.addDocument("DOC_002", "Python is great for data science and machine learning");

        String studentWork = "Java is a popular programming language used for mobile apps";
        detector.check(studentWork);
    }
}