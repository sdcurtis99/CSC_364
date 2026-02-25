package Program2;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Reusable MQTT publisher wrapper.
 * Connects to a broker on construction and provides a simple
 * publish method that sends messages with QoS 2 (exactly once).
 */
public class Publisher {

    private final MqttClient client;

    // Connects to the MQTT broker immediately
    public Publisher(String brokerUrl, String clientId) throws MqttException {
        client = new MqttClient(brokerUrl, clientId);
        client.connect();
        System.out.println("Publisher connected to broker: " + brokerUrl);
    }

    // Publishes a message to the given topic at QoS 2
    public void publish(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(2);
        if (client.isConnected()) {
            client.publish(topic, message);
        }
    }

    // Disconnects from the broker
    public void disconnect() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }
}
