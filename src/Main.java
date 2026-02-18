public class Main {

    public static void main(String[] args) {

        int NCPU = Runtime.getRuntime().availableProcessors();

        JobRepo repository = new JobRepo(NCPU);

        Thread producerThread = new Thread(new Producer(repository));
        producerThread.start();

        for (int i = 0; i < NCPU; i++) {
            Thread worker = new Thread(new LocalWorker(repository, i));
            worker.start();
        }
    }
}