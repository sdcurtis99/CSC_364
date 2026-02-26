package Program1;

public class Main {

    public static void main(String[] args) throws Exception {

        int NCPU = Runtime.getRuntime().availableProcessors();

        JobRepo repository = JobRepo.getInstance(NCPU);

        Thread producerThread = new Thread(new Producer(repository));
        producerThread.start();

        Outsourcer outsourcer = new Outsourcer(repository);
        outsourcer.start();

        System.out.println("Outsourcer system running...");
    }
}