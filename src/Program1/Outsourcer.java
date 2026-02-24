package Program1;

public class Outsourcer {
    /*private final JobRepo repo;
    private final Helper helper;
    private final PubSub pubsub;

    public Outsourcer(JobRepo repo, Helper helper, PubSub pubsub) {
        this.repo = repo;
        this.helper = helper;
        this.pubsub = pubsub;
    }

    public void start() {
        pubsub.subscribeWorkRequests(this::handleWorkRequest);
        pubsub.subscribeResults(this::handleResult);
    }

    private void handleWorkRequest(WorkRequest req) {
        Job job = repo.tryPoll();
        if (job == null) {
            pubsub.publishWorkAssignment(req.getWorkerId(), null); 
            return;
        }
        helper.startJob(job.getId());
        pubsub.publishWorkAssignment(req.getWorkerId(), job);
    }

    private void handleResult(Result r) {
        helper.finishJob(r.getJobId(), r.getValue());
        helper.printJob(r.getJobId());
    }

     */
}