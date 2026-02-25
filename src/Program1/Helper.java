package Program1;

import java.util.Timer;


public class Helper {

    /*

    Better design pattern would be to use a single Map aka
    JobMap<String/Integer, JobInfo(object)>
    Calling Ex:
            <JobInfo.getID(), JobInfo>
    Should create a new class JobInfo for ease

    Need to override the to string, hashcode, and equals depending on implementation for HashMap

    Methods add, remove, get.
    Once you have an object use the api of JobInfo class to make changes
    Do calculations etc

    Look into duplicates, error checking, etc in hashmaps

     */



    /*private final ConcurrentHashMap<Integer, Long> startTimes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Long> endTimes   = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();

    public void startJob(int jobId) {
        startTimes.put(jobId, System.nanoTime());
    }

    public void finishJob(int jobId, int result) {
        results.put(jobId, result);
        endTimes.put(jobId, System.nanoTime());
    }

    */
}