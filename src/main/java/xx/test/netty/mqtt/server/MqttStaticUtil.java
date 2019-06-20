package xx.test.netty.mqtt.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

public class MqttStaticUtil {
	//
	public static Map<String, List<Map<String, MqttPublishMessage>>> pushMessage;
	
	//
	public static Map<String, MqttPublishMessage> topicMessage;
	
	//订阅主题的订阅者列表
	//1 key: topic name
	//2 key: qos value
	public static Map<String, Map<String, List<String>>> topicSubsribers;
	
	public static TopicSubsribersCache topicSubsribersCache = new TopicSubsribersCache();
	
	public static PushMessageCache pushMessageCache = new PushMessageCache();
		
	public static Map<String, Object> nativeCache = new ConcurrentHashMap<String, Object>();
}
