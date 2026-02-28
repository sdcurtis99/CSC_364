public class SolverWorker implements Runnable {
    private final TSQueue<Job> solverQ;
    private final TSQueue<Job> aggQ;

    public SolverWorker(TSQueue<Job> solverQ, TSQueue<Job> aggQ) {
        this.solverQ = solverQ;
        this.aggQ = aggQ;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Job job = solverQ.take();

                if (job == Job.STOP) {
                    aggQ.put(Job.STOP);
                    break;
                }

                job.setStartSolve();

                int a = job.getA();
                int b = job.getB();
                switch (job.getOperator()) {
                    case 1: job.result = a + b; break;
                    case 2: job.result = a - b; break;
                    case 3: job.result = a * b; break;
                    case 4: job.result = a / b; break;
                    case 5: job.result = a % b; break;
                    default:
                        job.reason = "unknown operator";
                        break;
                }

                job.solvedAt = System.nanoTime();
                aggQ.put(job);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}