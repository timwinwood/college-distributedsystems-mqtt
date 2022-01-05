package com.smartbus.mqtt;

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * SmartBusSubscriberOperations  - a class that performs the SmartBusiness MQTT Subscriber App operations
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartBusSubscriberOperations {

	// initialise constants
	
	// server uri (broker)
	private static final String SERVER_URI = "tcp://localhost:1883";

	// client id
	private static final String CLIENT_ID = "SmartBusSubscriber";

	// application operations
	private static final String[] APP_OPS = { "Connect to Broker","Q1.A - Sub to Specific Subtopic", "Q1.B - Sub to Topic and all its Subtopics", "Q1.C - Sub to Subtopic Common to all Topics","Disconnect from Broker","Exit Application" };

	// declare instance variables
	private Scanner input;
	private MqttClient smartBusMqttClient;

	/**
	 * SmartBusPublisherOperations constructor
	 */
	public SmartBusSubscriberOperations() {

		// initialise scanner for user input
		this.input = new Scanner(System.in);
		
		// mqttClient is initially null, will be initialised later
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
				// clean session true, the subs will be destroyed when the app is restarted
				options.setCleanSession(true);
				// set the callback to our SmartBusSubscriber
				smartBusMqttClient.setCallback(new SmartBusSubscriber());
				
				// Connect to the broker
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
	 * app operation - Q1.A - Sub to Specific Subtopic
	 */
	public void operationSubSpecificSubtopic() {
				
		// check that client is connected to the broker
		if (smartBusMqttClient != null) {
			
			try {
				
				// Sub to Specific Subtopic
				String topicFilter = "/business/sales";
				smartBusMqttClient.subscribe(topicFilter);
				System.out.println("Subscribed to Specific Subtopic using Topic Filter: \"" + topicFilter + "\"");
				
			} catch (MqttException e) {
				e.printStackTrace();
			}

		} else {

			// warn the user that the client is not connected to the broker
			System.out.println("Client not Connected to Broker. Please connect to Broker, or contact your SysAdmin." + "\"");
		}

		// return user to menu
		returnToSmartBusMenu();

	}

	/**
	 * app operation - Q1.B - Sub to Topic and all its Subtopics
	 */
	public void operationSubTopicAllSubtopics() {
		
		// check that client is connected to the broker
		if (smartBusMqttClient != null) {

			try {
				
				// Sub to Topic and all its Subtopics
				String topicFilter = "/business/projects/#";
				smartBusMqttClient.subscribe(topicFilter);
				System.out.println("Subscribed to Topic and all its Subtopics using Topic Filter: \"" + topicFilter + "\"");
				
			} catch (MqttException e) {
				e.printStackTrace();
			}

		} else {

			// warn the user that the client is not connected to the broker
			System.out.println("Client not Connected to Broker. Please connect to Broker, or contact your SysAdmin.");
		}

		// return user to menu
		returnToSmartBusMenu();


	}

	/**
	 * app operation - Q1.C - Sub to Subtopic Common to all Topics
	 */
	public void operationSubSubtopicCommonToTopics() {
		
		// check that client is connected to the broker
		if (smartBusMqttClient != null) {
			
			try {
				
				//Sub to Subtopic Common to all Topics
				String topicFilter = "/business/+/current";
				smartBusMqttClient.subscribe(topicFilter);
				System.out.println("Subscribed to Subtopic Common to all Topics using Topic Filter: \"" + topicFilter + "\"");
				
			} catch (MqttException e) {
				e.printStackTrace();
			}
			
		} else {

			// warn the user that the client is not connected to the broker
			System.out.println("Client not Connected to Broker. Please connect to Broker, or contact your SysAdmin.");
		}

		// return user to menu
		returnToSmartBusMenu();

	}

	/**
	 * app operation - Q1.D - Disconnect from Broker
	 */
	public void operationDisconnectFromBroker() {

		// check that client is connected to the broker
		if (smartBusMqttClient != null) {
			
			try {

				// disconnect from broker
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
	 * display the SmartBusiness app menu
	 * 
	 * @return the operation
	 */
	public int displaySmartBusAppMenu() {

		// display the menu
		System.out.println("-- SmartBusiness Subscriber Menu --");
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

		System.out.println("Press any key to return to SmartBusiness Subscriber Menu...");

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
	public void exitSmartBusApp(){

		System.exit(0);

	}

}
