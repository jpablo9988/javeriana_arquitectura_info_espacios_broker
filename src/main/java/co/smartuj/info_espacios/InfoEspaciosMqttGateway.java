package co.smartuj.info_espacios;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface InfoEspaciosMqttGateway {
	void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
	void sendToMqtt(String data);
}