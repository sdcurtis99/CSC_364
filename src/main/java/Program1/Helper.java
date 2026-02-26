package Program1;

import java.util.concurrent.ConcurrentHashMap;

public class Helper {

    private final ConcurrentHashMap<Integer, Long> startTimes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Long> endTimes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();

    public void startJob(int jobId) {
        startTimes.put(jobId, System.nanoTime());
    }

    public void finishJob(int jobId, int result) {
        results.put(jobId, result);
        endTimes.put(jobId, System.nanoTime());
    }

    public void printJob(int jobId) {
        Long start = startTimes.get(jobId);
        Long end = endTimes.get(jobId);
        Integer result = results.get(jobId);

        if (start != null && end != null && result != null) {
            long durationMs = (end - start) / 1_000_000;
            System.out.println("Job#" + jobId +
                    " completed. Result=" + result +
                    " Time=" + durationMs + "ms");
        }
    }
}