package co.smartuj.info_espacios;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class InfoEspaciosMqttBeans {
	// private String topicEvents = "data/events";
	// private String topicLocationChanges = "data/locationChanges";
	private String topicIots = "data/iots";

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();

		options.setServerURIs(new String[] { "tcp://10.43.102.29:1883" });
		options.setUserName("admin");
		String pass = "1234";
		options.setPassword(pass.toCharArray());
		options.setCleanSession(false);

		factory.setConnectionOptions(options);

		return factory;
	}

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverInUbicacion",
				mqttClientFactory(), topicIots);

		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler handler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				// String topic =
				// message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
				System.out.println("Se recibio informacion desde un dispositivo iot");
				System.out.println(message.getPayload());
			}
		};
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		// clientId is generated using a random number
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOutUbicacion", mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("data/#");
		messageHandler.setDefaultRetained(false);
		return messageHandler;
	}

}