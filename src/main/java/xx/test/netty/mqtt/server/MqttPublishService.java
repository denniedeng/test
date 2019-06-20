package xx.test.netty.mqtt.server;

import java.util.List;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

public interface MqttPublishService {
	// 接受特定会话的消息
	public String recvMsg(String clientId, Object msg);

	// 发送消息至特定会话
	public String sendMsg(String clientId, Object msg);

	public String checkAndSendMsg(String clientId, Object msg);

	public String checkWaitAndSendMsg(String clientId, Object msg);

	// 广播消息
	public String publishMsgAtMostOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds);

	public String publishMsgAtleastOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds);

	public String publishMsgExactlyOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds);
}
