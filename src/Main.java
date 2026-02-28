public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int N = 10000;
        final int K = 4;

        System.out.println("Starting pipeline...");
        System.out.println("Jobs: " + N);
        System.out.println("Solver threads: " + K);

        TSQueue<Job> genQ    = new TSQueue<>();
        TSQueue<Job> solverQ = new TSQueue<>();
        TSQueue<Job> aggQ    = new TSQueue<>();

        Thread generatorThread  = new Thread(new Generator(N, genQ), "Generator");
        Thread validatorThread  = new Thread(new Validator(genQ, solverQ, aggQ, K), "Validator");
        Thread aggregatorThread = new Thread(new Aggregator(aggQ, K), "Aggregator");

        Thread[] solverThreads = new Thread[K];
        for (int i = 0; i < K; i++) {
            solverThreads[i] = new Thread(new SolverWorker(solverQ, aggQ), "Solver-" + i);
        }

        long startTime = System.currentTimeMillis();

        generatorThread.start();
        validatorThread.start();
        aggregatorThread.start();
        for (Thread t : solverThreads) t.start();

        generatorThread.join();
        validatorThread.join();
        for (Thread t : solverThreads) t.join();
        aggregatorThread.join();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("\nPipeline completed.");
        System.out.println("Total time: " + totalTime + " ms");
        System.out.printf("Throughput: %.2f jobs/sec%n", N / (totalTime / 1000.0));
    }
}