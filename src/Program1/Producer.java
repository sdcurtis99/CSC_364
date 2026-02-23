package Program1;
import java.util.Random;

public class Producer implements Runnable {

    private final JobRepo repository;
    private int jobCounter = 0;
    private final Random random = new Random();

    public Producer(JobRepo repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int operator = random.nextInt(4) + 1;
                int a = random.nextInt(10) + 1;
                int b = random.nextInt(10) + 1;

                Job job = new Job(jobCounter++, operator, a, b);
                repository.put(job);

                Thread.sleep(200 + random.nextInt(842));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}