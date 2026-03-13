import java.util.*;

public class FraudDetector {
    public static List<int[]> findTwoSum(int[] amounts, int target) {
        Map<Integer, Integer> map = new HashMap<>(); // Value -> Index
        List<int[]> pairs = new ArrayList<>();

        for (int i = 0; i < amounts.length; i++) {
            int complement = target - amounts[i];
            if (map.containsKey(complement)) {
                pairs.add(new int[]{map.get(complement), i});
            }
            map.put(amounts[i], i);
        }
        return pairs;
    }

    public static void main(String[] args) {
        int[] txns = {500, 300, 200, 100};
        List<int[]> matches = findTwoSum(txns, 500);
        for(int[] p : matches) System.out.println("Match found at indices: " + p[0] + ", " + p[1]);
    }
}