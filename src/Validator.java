public class Validator implements Runnable {
    private final TSQueue<Job> genQ;
    private final TSQueue<Job> solverQ;
    private final TSQueue<Job> aggQ;
    private final int k;

    public Validator(TSQueue<Job> genQ, TSQueue<Job> solverQ, TSQueue<Job> aggQ, int k) {
        this.genQ = genQ;
        this.solverQ = solverQ;
        this.aggQ = aggQ;
        this.k = k;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Job job = genQ.take();

                if (job == Job.STOP) {
                    for (int i = 0; i < k; i++) {
                        solverQ.put(Job.STOP);
                    }
                    break;
                }

                // invalid operator
                if (job.getOperator() < 1 || job.getOperator() > 5) {
                    job.reason = "invalid operator";
                    aggQ.put(job);
                    continue;
                }

                // division or mod by zero
                if ((job.getOperator() == 4 || job.getOperator() == 5) && job.getB() == 0) {
                    job.reason = "division/mod by zero";
                    aggQ.put(job);
                    continue;
                }

                solverQ.put(job);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}