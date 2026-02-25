package Program1;
public class Main {

    public static void main(String[] args) {

        int NCPU = Runtime.getRuntime().availableProcessors();
        //System.out.println(NCPU);


        JobRepo repository = JobRepo.getInstance(NCPU);

        Thread producerThread = new Thread(new Producer(repository));
        producerThread.start();

        for (int i = 0; i < NCPU; i++) {
            Thread worker = new Thread(new LocalWorker(repository, i));
            worker.start();
        }
    }
}