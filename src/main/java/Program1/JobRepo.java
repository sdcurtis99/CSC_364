package Program1;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class JobRepo {

    private static JobRepo instance;

    private static final Queue<Job> jobQueue = new ArrayDeque<>();
    int capacity;
    private final Semaphore emptySlots;
    private final Semaphore availableItems;
    private final ReentrantLock qLock = new ReentrantLock();

    JobRepo(int capacity) {
        this.capacity = capacity;
        this.emptySlots = new Semaphore(capacity);
        this.availableItems = new Semaphore(0);
    }

    public static synchronized JobRepo getInstance(int getCapacity) {
        if (instance == null) {
            instance = new JobRepo(getCapacity);
        }
        return instance;
    }

    public void put(Job job) throws InterruptedException {
        emptySlots.acquire();
        qLock.lock();
        try {
            jobQueue.add(job);
            System.out.println("Producer added Job#" + job.getId());
        } finally {
            qLock.unlock();
        }
        availableItems.release();
    }


    public Job take() throws InterruptedException {
        availableItems.acquire();
        qLock.lock();
        Job job;
        try {
            job = jobQueue.remove();
        } finally {
            qLock.unlock();
        }
        emptySlots.release();
        return job;
    }
}