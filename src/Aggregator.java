public class Aggregator implements Runnable {
    private final TSQueue<Job> aggQ;
    private final int k;

    public Aggregator(TSQueue<Job> aggQ, int k) {
        this.aggQ = aggQ;
        this.k = k;
    }

    @Override
    public void run() {
        int totalSolved = 0;
        int totalRejected = 0;
        int stopCount = 0;
        int[] operatorCounts = new int[6];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long totalLatency = 0;

        while (true) {
            try {
                Job job = aggQ.take();

                if (job == Job.STOP) {
                    stopCount++;
                    if (stopCount == k) break;
                    continue;
                }

                if (job.reason != null) {
                    totalRejected++;
                    continue;
                }

                totalSolved++;
                operatorCounts[job.getOperator()]++;
                totalLatency += job.solvedAt - job.getStartSolve();

                if (job.result < min) min = job.result;
                if (job.result > max) max = job.result;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n===== AGGREGATOR REPORT =====");
        System.out.println("Total solved:   " + totalSolved);
        System.out.println("Total rejected: " + totalRejected);
        System.out.println("Operator counts:");
        System.out.println("  +  : " + operatorCounts[1]);
        System.out.println("  -  : " + operatorCounts[2]);
        System.out.println("  *  : " + operatorCounts[3]);
        System.out.println("  /  : " + operatorCounts[4]);
        System.out.println("  %  : " + operatorCounts[5]);
        System.out.println("Min result: " + min);
        System.out.println("Max result: " + max);
        if (totalSolved > 0) {
            long avgNs = totalLatency / totalSolved;
            System.out.println("Avg solve latency: " + avgNs + " ns");
            System.out.printf("Avg solve latency: %.4f ms%n", (double) avgNs / 1_000_000);
        }
        System.out.println("=============================");
    }
}