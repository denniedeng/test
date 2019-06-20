//package xx.test.mqtt.paho;
//
//import java.util.Random;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//
//public class ThreadPublish implements Runnable{
//
//	private String userName;
//	
//	private int repeatTimes;
//	
//	public ThreadPublish(String userName,int repeatTimes) {
//		this.userName = userName;
//		this.repeatTimes = repeatTimes;
//	}
//	
//	@Override
//	public void run() {
//		MqttClient mqttClient = null;
//		try {
//			long startTime = System.currentTimeMillis();
//			String clientId = "client"+Math.random();
//			mqttClient = PubMsg.connect(clientId,userName,null);
//			for (int i=0;i<repeatTimes;i++) {
//				String data = "{'shake':'"+Math.random()+"','noise':'"+Math.random()+"'}";
//				PubMsg.publish(mqttClient,data,"v1/devices/me/telemetry");
//				Thread.currentThread().sleep(1000);
//			}
//			
//			long total = System.currentTimeMillis()-startTime;
//			System.out.println(Thread.currentThread().getName()+" finished! use :"+total+"ms");
//		} catch (MqttException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			if (mqttClient != null) {
//				try {
//					mqttClient.disconnect();
//				} catch (MqttException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}
//}
