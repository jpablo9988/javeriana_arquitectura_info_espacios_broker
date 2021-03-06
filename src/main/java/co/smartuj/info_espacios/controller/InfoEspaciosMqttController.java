package co.smartuj.info_espacios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.smartuj.info_espacios.InfoEspaciosMqttGateway;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class InfoEspaciosMqttController {
	@Autowired
	InfoEspaciosMqttGateway mqtGateway;

	@PostMapping("/sendMessage")
	public ResponseEntity<?> publish(@RequestBody String mqttMessage) {

		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
			// mqtGateway.senToMqtt(convertObject.get("message").toString(),
			mqtGateway.sendToMqtt(convertObject.get("data").toString(), convertObject.get("topic").getAsString());
			return ResponseEntity.ok("Success");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

}