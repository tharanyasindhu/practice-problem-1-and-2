import java.util.*;
import java.util.concurrent.*;

class TokenBucket {
    private final long maxTokens;
    private double tokens;
    private long lastRefillTime;
    private final double refillRatePerMs;

    public TokenBucket(long maxTokens, long tokensPerHour) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
        this.refillRatePerMs = (double) tokensPerHour / (60 * 60 * 1000);
    }

    public synchronized boolean allowRequest() {
        refill();
        if (tokens >= 1.0) {
            tokens -= 1.0;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        double delta = (now - lastRefillTime) * refillRatePerMs;
        tokens = Math.min(maxTokens, tokens + delta);
        lastRefillTime = now;
    }

    public long getTokensLeft() { return (long) tokens; }
}

public class RateLimiter {
    private final Map<String, TokenBucket> clients = new ConcurrentHashMap<>();

    public boolean checkLimit(String clientId) {
        TokenBucket bucket = clients.computeIfAbsent(clientId, k -> new TokenBucket(1000, 1000));
        return bucket.allowRequest();
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter();
        for(int i=0; i<5; i++) {
            System.out.println("Request " + i + ": " + (limiter.checkLimit("user123") ? "Allowed" : "Denied"));
        }
    }
}