import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TSQueue<T> {

    private final LinkedList<T> items = new LinkedList<>();
    private final ReentrantLock qLock = new ReentrantLock(true);
    private final Condition notEmpty = qLock.newCondition();

    public void put(T item) throws InterruptedException {
        qLock.lock();
        try {
            items.addLast(item);
            notEmpty.signalAll();
        } finally {
            qLock.unlock();
        }
    }

    public T take() throws InterruptedException {
        qLock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            return items.removeFirst();
        } finally {
            qLock.unlock();
        }
    }
}

