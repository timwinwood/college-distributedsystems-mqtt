package com.smartbus.mqtt;

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * SmartBusPublisherOperations - a class that performs the SmartBusiness MQTT Publisher App operations
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartBusPublisherOperations {

	// server uri (broker)
	private static final String SERVER_URI = "tcp://localhost:1883";

	// client id
	private static final String CLIENT_ID = "SmartBusPublisher";

	// app operations
	private static final String[] APP_OPS = { "Connect to Broker", "Publish Messages", "Disconnect from Broker", "Exit Application" };

	// declare instance variables
	private Scanner input;
	private MqttClient smartBusMqttClient;

	/**
	 * SmartBusPublisherOperations constructor
	 */
	public SmartBusPublisherOperations() {

		// initialise scanner for user input
		this.input = new Scanner(System.in);

		// smartBusMqttClient is initially null, will be initialised later
		smartBusMqttClient = null;

	}

	/**
	 * app operation - Connect to Broker
	 */
	public void operationConnectToBroker() {

		// check that client is already connected to the broker
		if (smartBusMqttClient == null) {

			try {

				// initialise the client
				smartBusMqttClient = new MqttClient(SERVER_URI, CLIENT_ID, new MemoryPersistence());

				// client options
				MqttConnectOptions options = new MqttConnectOptions();
				// clean session true, the pub will be destroyed when the app is restarted
				options.setCleanSession(true);
				// 'keep alive' - maximum wait between messages sent or received. Allows client to detect if the server is still available.
				options.setKeepAliveInterval(180);

				// connect to the broker
				System.out.println("Establishing Connection to Broker: " + SERVER_URI);
				smartBusMqttClient.connect(options);
				System.out.println("Connection to Broker has been Established: " + SERVER_URI);

			} catch (MqttException e) {
				e.printStackTrace();
			}

		} else {

			// warn the user that the client is already connected to the broker
			System.out.println("Client already connected to broker. No need to reconnect.");
		}

		// return user to menu
		returnToSmartBusMenu();

	}

	/**
	 * app operation - Publish Messages
	 */
	public void operationPublishMessages() {

		// check that client is connected to the broker
		if (smartBusMqttClient != null) {

			// publish the messages
			publishMessage("top level business information", false, 1, "/business");
			publishMessage("top level sales information", false, 1, "/business/sales");
			publishMessage("current sales information", false, 1, "/business/sales/current");
			publishMessage("future sales information", false, 1, "/business/sales/future");
			publishMessage("top level projects information", false, 1, "/business/projects");
			publishMessage("current projects information", false, 1, "/business/projects/current");
			publishMessage("future projects information", false, 1, "/business/projects/future");

		} else {

			// warn the user that the client is not connected to the broker
			System.out.println("Client not Connected to Broker. Please connect to Broker, or contact your SysAdmin.");
		}

		// return user to menu
		returnToSmartBusMenu();

	}

	/**
	 * app operation - Disconnect from Broker
	 */
	public void operationDisconnectFromBroker() {

		// check that client is connected to the broker
		if (smartBusMqttClient != null) {

			try {

				// disconnect from the broker
				smartBusMqttClient.disconnect();
				System.out.println("Client Disconnected from Broker");
				smartBusMqttClient.close();

			} catch (MqttException e) {
				e.printStackTrace();
			}

		} else {

			// warn the user that the client is not connected to the broker
			System.out.println("Client not Connected to Broker. No need to disconnect.");
		}

		// return user to menu
		returnToSmartBusMenu();

	}

	/**
	 * helper method - publishes a messages
	 * 
	 * @param payload  the contents of the message
	 * @param retained should the message be retained by the broker
	 * @param qos      the quality of service
	 * @param topic    the topic the message belongs to
	 */
	private void publishMessage(String payload, boolean retained, int qos, String topic) {

		// create the message
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setRetained(retained);
		message.setQos(qos);

		try {

			// publish the message
			smartBusMqttClient.publish(topic, message);

		} catch (MqttException e) {
			e.printStackTrace();
		}

		// print the message details
		System.out.println("Message Published. Topic: \"" + topic + "\" Payload: \"" + payload + "\"");

	}

	/**
	 * display the SmartBusiness app menu
	 * 
	 * @return the operation
	 */
	public int displaySmartBusAppMenu() {

		// display the menu
		System.out.println("-- SmartBusiness Publisher Menu --");
		for (int i = 0; i < APP_OPS.length; i++) {
			System.out.println((i) + " - " + APP_OPS[i]);
		}
		System.out.println("Enter an Operation:");

		// read operation, if out of bounds, call this method again (recursion)
		int intOperation = input.nextInt();
		if (!(intOperation >= 0 && intOperation <= APP_OPS.length - 1)) {
			System.out.println("Invalid Operation. Please enter an operation between 0 and " + APP_OPS.length + ".");
			return displaySmartBusAppMenu();
		}

		// return the operation
		return intOperation;

	}

	/**
	 * press any key to return to SmartBus menu
	 */
	public void returnToSmartBusMenu() {

		System.out.println("Press any key to return to SmartBusiness Publisher Menu...");

		try {

			// read any key
			System.in.read();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * displays invalid operation message, returns user to SmartBus menu
	 */
	public void invalidOperation() {

		System.out.println("Invalid Operation. Please enter an operation between 0 and " + APP_OPS.length + ".");

		// return user to SmartBus menu
		returnToSmartBusMenu();

	}

	/**
	 * exits the SmartBus App
	 */
	public void exitSmartBusApp() {

		System.exit(0);

	}

}
