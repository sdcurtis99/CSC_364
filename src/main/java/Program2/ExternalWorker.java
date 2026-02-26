package Program2;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.UUID;

/**
 * ExternalWorker - a remote worker that connects to the Outsourcer via MQTT.
 * Uses a pull model: requests work, receives an assignment, solves it,
 * sends the result back, then requests the next job.
 *
 * Message format (pipe-delimited):
 *   Request:  workerId
 *   Assign:   workerId|jobId|operator|a|b
 *   Result:   workerId|jobId|result
 */
public class ExternalWorker {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    // MQTT topics shared with the Outsourcer
    private static final String TOPIC_REQUEST = "csc364/request";
    private static final String TOPIC_ASSIGN = "csc364/assign";
    private static final String TOPIC_RESULT = "csc364/result";

    // Unique ID for this worker (UUID avoids collisions on the shared broker)
    private final String workerId;
    private Publisher publisher;
    private Subscriber subscriber;

    public ExternalWorker() {
        this.workerId = "worker-" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Connects to the broker, subscribes to assignments, and sends
     * an initial work request to kick off the pull cycle.
     */
    public void start() throws MqttException {
        // Use unique client IDs so multiple workers can run simultaneously
        String pubId = "ext-pub-" + UUID.randomUUID();
        String subId = "ext-sub-" + UUID.randomUUID();

        publisher = new Publisher(BROKER, pubId);
        subscriber = new Subscriber(BROKER, subId, this::handleMessage);
        subscriber.subscribe(TOPIC_ASSIGN);

        System.out.println("ExternalWorker " + workerId + " started");

        // Send initial work request (pull model)
        publisher.publish(TOPIC_REQUEST, workerId);
        System.out.println(workerId + " requesting work...");
    }

    /**
     * Callback for incoming MQTT messages on the assign topic.
     * Filters by workerId so we only process jobs meant for us.
     */
    private void handleMessage(String topic, String payload) {
        if (!topic.equals(TOPIC_ASSIGN)) return;

        // Parse the pipe-delimited assignment: workerId|jobId|operator|a|b
        String[] parts = payload.split("\\|");
        String targetWorkerId = parts[0];

        // Ignore assignments meant for other workers
        if (!targetWorkerId.equals(workerId)) return;

        int jobId = Integer.parseInt(parts[1]);
        int operator = Integer.parseInt(parts[2]);
        int a = Integer.parseInt(parts[3]);
        int b = Integer.parseInt(parts[4]);

        // Solve the math job
        int result = solve(operator, a, b);
        String symbol = operatorSymbol(operator);
        System.out.println(workerId + " solved Job#" + jobId + ": " + a + " " + symbol + " " + b + " = " + result);

        try {
            // Publish result back: workerId|jobId|result
            String resultPayload = workerId + "|" + jobId + "|" + result;
            publisher.publish(TOPIC_RESULT, resultPayload);

            // Immediately request the next job (pull model)
            publisher.publish(TOPIC_REQUEST, workerId);
            System.out.println(workerId + " requesting work...");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private String operatorSymbol(int operator) {
        switch (operator) {
            case 1: return "+";
            case 2: return "-";
            case 3: return "*";
            case 4: return "/";
            default: return "?";
        }
    }

    // Solves a math operation: 1=add, 2=sub, 3=mul, 4=div
    private int solve(int operator, int a, int b) {
        switch (operator) {
            case 1: return a + b;
            case 2: return a - b;
            case 3: return a * b;
            case 4: return a / b;
            default: return 0;
        }
    }

    // Entry point - creates a worker, starts it, then blocks forever
    public static void main(String[] args) {
        try {
            ExternalWorker worker = new ExternalWorker();
            worker.start();
            // Block main thread to keep the program alive (subscriber runs on callback threads)
            Thread.currentThread().join();
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
