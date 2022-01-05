
package com.smartbus.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * SmartBusSubscriber - a class that acts as the Callback for the MQTT Subscriber App
 * 
 * @implements MqttCallback
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartBusSubscriber implements MqttCallback {

	@Override
	public void connectionLost(Throwable cause) {
		
		// print the stack trace
		cause.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		// print message details
		 System.out.println("Message Arrived. Payload: \"" + new String(message.getPayload()) + "\" Topic: \"" + topic + "\" QOS: " + message.getQos() );	
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
		// print token details
		 System.out.println("Message Delivery Complete. Message ID: " +  token.getMessageId() + " IsComplete: " + token.isComplete());
	}


    
}
