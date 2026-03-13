public class ParkingSystem {
    private String[] spots;
    private int capacity;

    public ParkingSystem(int capacity) {
        this.capacity = capacity;
        this.spots = new String[capacity];
    }

    private int hash(String plate) { return Math.abs(plate.hashCode()) % capacity; }

    public int park(String plate) {
        int index = hash(plate);
        int probes = 0;
        while (spots[index] != null) {
            index = (index + 1) % capacity;
            probes++;
            if (probes == capacity) return -1; // Full
        }
        spots[index] = plate;
        System.out.println("Parked " + plate + " at " + index + " after " + probes + " probes.");
        return index;
    }

    public static void main(String[] args) {
        ParkingSystem ps = new ParkingSystem(10);
        ps.park("ABC-123");
        ps.park("XYZ-789");
    }
}