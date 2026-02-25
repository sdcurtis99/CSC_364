package Program2;

import org.eclipse.paho.client.mqttv3.*;

import java.util.function.BiConsumer;

/**
 * Reusable MQTT subscriber wrapper.
 * Implements MqttCallback so incoming messages are forwarded
 * to a BiConsumer handler that receives (topic, payload).
 */
public class Subscriber implements MqttCallback {

    private final MqttClient client;
    // Handler called whenever a message arrives: (topic, payload) -> void
    private final BiConsumer<String, String> messageHandler;

    // Connects to the broker and registers the callback
    public Subscriber(String brokerUrl, String clientId, BiConsumer<String, String> messageHandler) throws MqttException {
        this.messageHandler = messageHandler;
        client = new MqttClient(brokerUrl, clientId);
        client.setCallback(this);
        client.connect();
        System.out.println("Subscriber connected to broker: " + brokerUrl);
    }

    // Subscribe to a topic (can be called multiple times for different topics)
    public void subscribe(String topic) throws MqttException {
        client.subscribe(topic);
        System.out.println("Subscribed to topic: " + topic);
    }

    // Disconnects from the broker
    public void disconnect() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost: " + throwable.getMessage());
    }

    // Forward incoming messages to the handler
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        messageHandler.accept(topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // delivery confirmed
    }
}
