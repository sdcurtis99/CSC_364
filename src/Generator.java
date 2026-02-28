import java.util.Random;
import java.util.UUID;

public class Generator implements Runnable {
    private final int n;
    private final TSQueue<Job> genQ;

    public Generator(int n, TSQueue<Job> genQ) {
        this.n = n;
        this.genQ = genQ;
    }

    @Override
    public void run() {
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            int a = rand.nextInt(10000);
            int operator = rand.nextInt(7) + 1;
            int b = rand.nextInt(10);
            Job job = new Job(UUID.randomUUID(), operator, a, b);

            try {
                genQ.put(job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        try {
            genQ.put(Job.STOP);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}