package Program1;
import java.util.Random;

public class LocalWorker implements Runnable {

    private final JobRepo repository;
    private final int workerId;
    private final Random random = new Random();   // ← add this

    public LocalWorker(JobRepo repository, int workerId) {
        this.repository = repository;
        this.workerId = workerId;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Job job = repository.take();

                int result = solve(job);

                System.out.println(
                        "Worker-" + workerId +
                                " solved Job#" + job.getId() +
                                ": " + job.getA() + " " +
                                job.getOperatorSymbol() + " " +
                                job.getB() + " = " + result
                );
                Thread.sleep(random.nextInt(1234));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int solve(Job job) {
        switch (job.getOperator()) {
            case 1: return job.getA() + job.getB();
            case 2: return job.getA() - job.getB();
            case 3: return job.getA() * job.getB();
            case 4: return job.getA() / job.getB();
            default: return 0;
        }
    }
}