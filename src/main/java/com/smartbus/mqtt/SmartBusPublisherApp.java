package com.smartbus.mqtt;

/**
 * SmartBusPublisherApp  - a class that runs the SmartBusiness MQTT Publisher App
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartBusPublisherApp {

	/**
	 * entry point to the SmartBusPublisherApp, processes user input from the SmartBusiness application menu
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {

		// initialise SmartBusPublisherOperations object, which performs the Publisher operations
		SmartBusPublisherOperations smartBusPubOps = new SmartBusPublisherOperations();

		// infinite loop, keeps displaying the app menu
		while (true) {

			// display the SmartBusiness App menu
			int intChoice = smartBusPubOps.displaySmartBusAppMenu();

			// process user input, call appropriate method from SmartBusPublisherOperations object
			switch (intChoice) {
			case 0:
				smartBusPubOps.operationConnectToBroker();
				break;
			case 1:
				smartBusPubOps.operationPublishMessages();
				break;
			case 2:
				smartBusPubOps.operationDisconnectFromBroker();
				break;
			case 3:
				smartBusPubOps.exitSmartBusApp();
				break;
			default:
				smartBusPubOps.invalidOperation();
			}
		}
	}
}
