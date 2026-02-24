package Program1;

import java.util.function.Consumer;

public class PubSub {

    // connect to broker
    public PubSub(String brokerUrl, String clientId) {
        //
    }

    // start listening (subscribe)
    public void start() {
        // have to connect + subscribe to REQUEST + RESULT topics
    }

    // stop/cleanup
    public void stop() {
        // have to disconnect here
    }

    // Outsourcer sets this: called when a worker requests work
    public void setOnWorkRequest(Consumer<String> handler) {
        //
    }

    // Outsourcer sets this: called when a worker sends a result
    public void setOnResult(Consumer<String> handler) {
        //
    }

    // Outsourcer calls this to send a job to a specific worker
    public void publishAssignment(String workerId, String payload) {
        // have to publish to "assign/<workerId>"
    }

    // optional helper publish (workers can use too)
    public void publish(String topic, String payload) {
        //
    }
}