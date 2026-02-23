package Program1;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class JobRepo {

    private final Stack<Job> stack = new Stack<>();
    private final int capacity;

    private final Semaphore emptySlots;
    private final Semaphore availableItems;
    private final ReentrantLock lock = new ReentrantLock();

    public JobRepo(int capacity) {
        this.capacity = capacity;
        this.emptySlots = new Semaphore(capacity);
        this.availableItems = new Semaphore(0);
    }

    public void put(Job job) throws InterruptedException {
        emptySlots.acquire();

        lock.lock();
        try {
            stack.push(job);
            System.out.println("Producer added Job#" + job.getId());
        } finally {
            lock.unlock();
        }

        availableItems.release();
    }

    public Job take() throws InterruptedException {
        availableItems.acquire();

        Job job;
        lock.lock();
        try {
            job = stack.pop();
        } finally {
            lock.unlock();
        }

        emptySlots.release();
        return job;
    }
}