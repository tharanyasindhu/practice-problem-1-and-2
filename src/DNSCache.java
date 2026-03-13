import java.util.HashMap; // Fixes: cannot find symbol class HashMap
import java.util.Map;     // Fixes: cannot find symbol class Map

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, int ttlSeconds) {
        this.ip = ip;
        // Current time + TTL converted to milliseconds
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000L);
    }
}

public class DNSCache {
    // Map to store domain name as Key and DNSEntry object as Value
    private final Map<String, DNSEntry> cache = new HashMap<>();

    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);

        // Check if entry exists AND hasn't expired
        if (entry != null && System.currentTimeMillis() < entry.expiryTime) {
            System.out.print("[CACHE HIT] ");
            return entry.ip;
        }

        // Simulating an upstream DNS query (e.g., Google's IP)
        String upstreamIp = "172.217.14.206";

        // Store in cache with a 300-second TTL
        cache.put(domain, new DNSEntry(upstreamIp, 300));
        System.out.print("[CACHE MISS/EXPIRED] ");
        return upstreamIp;
    }

    public static void main(String[] args) {
        DNSCache dns = new DNSCache();

        // First call: Miss
        System.out.println("Result: " + dns.resolve("google.com"));

        // Second call: Hit (happens in < 1ms)
        System.out.println("Result: " + dns.resolve("google.com"));
    }
}
