package Program1;

import org.eclipse.paho.client.mqttv3.*;

import java.util.function.Consumer;

public class PubSub implements MqttCallback {

    private final MqttClient client;

    private Consumer<String> workRequestHandler;
    private Consumer<String> resultHandler;

    private static final String TOPIC_REQUEST = "csc364/request";
    private static final String TOPIC_RESULT = "csc364/result";

    public PubSub(String brokerUrl, String clientId) throws MqttException {
        client = new MqttClient(brokerUrl, clientId);
        client.setCallback(this);
        client.connect();
        System.out.println("Outsourcer connected to broker.");
    }

    public void start() throws MqttException {
        client.subscribe(TOPIC_REQUEST);
        client.subscribe(TOPIC_RESULT);
        System.out.println("Outsourcer subscribed to request/result topics.");
    }

    public void stop() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    public void setOnWorkRequest(Consumer<String> handler) {
        this.workRequestHandler = handler;
    }

    public void setOnResult(Consumer<String> handler) {
        this.resultHandler = handler;
    }

    public void publishAssignment(String workerId, String payload) throws MqttException {
        String topic = "csc364/assign";
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(2);
        client.publish(topic, message);
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());

        if (topic.equals(TOPIC_REQUEST) && workRequestHandler != null) {
            workRequestHandler.accept(payload);
        }

        if (topic.equals(TOPIC_RESULT) && resultHandler != null) {
            resultHandler.accept(payload);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}