package com.smartbus.mqtt;

/**
 * SmartBusSubscriberApp - a class that runs the SmartBusiness MQTT Subscriber App
 *
 * @author Tim Winwood - x20213638
 * @version 1.0
 */
public class SmartBusSubscriberApp {

	/**
	 * entry point to the SmartBusSubscriberApp, processes user input from the SmartBusiness application menu
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {

		// initialise SmartBusSubscriberOperations object, which performs the Subscriber operations
		SmartBusSubscriberOperations smartBusSubOps = new SmartBusSubscriberOperations();

		// infinite loop, keeps displaying the app menu
		while (true) {

			// display the SmartBusiness App menu
			int intChoice = smartBusSubOps.displaySmartBusAppMenu();

			// process user input, call appropriate method from SmartBusSubscriberOperations  object
			switch (intChoice) {
			case 0:
				smartBusSubOps.operationConnectToBroker();
				break;
			case 1:
				smartBusSubOps.operationSubSpecificSubtopic();
				break;
			case 2:
				smartBusSubOps.operationSubTopicAllSubtopics();
				break;
			case 3:
				smartBusSubOps.operationSubSubtopicCommonToTopics();
				break;
			case 4:
				smartBusSubOps.operationDisconnectFromBroker();
				break;
			case 5:
				smartBusSubOps.exitSmartBusApp();
				break;
			default:
				smartBusSubOps.invalidOperation();
			}
		}
	}
}
