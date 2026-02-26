package Program1;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        int NCPU = Runtime.getRuntime().availableProcessors();
        JobRepo repository = JobRepo.getInstance(NCPU);

        Thread producerThread = new Thread(new Producer(repository));
        producerThread.start();

        Thread localWorkerThread = new Thread(new LocalWorker(repository, UUID.randomUUID()));
        localWorkerThread.start();

        Outsourcer outsourcer = new Outsourcer(repository);
        outsourcer.start();
    }
}