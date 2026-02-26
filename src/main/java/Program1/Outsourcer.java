package Program1;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.UUID;

public class Outsourcer {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";

    private final JobRepo repo;
    private final Helper helper;
    private final PubSub pubsub;

    public Outsourcer(JobRepo repo) throws MqttException {
        this.repo = repo;
        this.helper = new Helper();
        this.pubsub = new PubSub(BROKER, "outsourcer-" + UUID.randomUUID());

        pubsub.setOnWorkRequest(this::handleWorkRequest);
        pubsub.setOnResult(this::handleResult);
    }

    public void start() throws MqttException {
        pubsub.start();
    }

    private void handleWorkRequest(String workerId) {
        try {
            Job job = repo.take();

            helper.startJob(job.getId());

            String payload =
                    workerId + "|" +
                            job.getId() + "|" +
                            job.getOperator() + "|" +
                            job.getA() + "|" +
                            job.getB();

            pubsub.publishAssignment(workerId, payload);

            System.out.println("Assigned Job#" + job.getId() +
                    " to " + workerId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleResult(String payload) {
        String[] parts = payload.split("\\|");

        int jobId = Integer.parseInt(parts[1]);
        int result = Integer.parseInt(parts[2]);

        helper.finishJob(jobId, result);
        helper.printJob(jobId);
    }
}