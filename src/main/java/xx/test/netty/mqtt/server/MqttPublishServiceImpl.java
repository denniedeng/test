//package xx.test.netty.mqtt.server;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelId;
//import io.netty.handler.codec.mqtt.MqttMessageType;
//import io.netty.handler.codec.mqtt.MqttPublishMessage;
//
//public class MqttPublishServiceImpl implements MqttPublishService {
//	private ExecutorService executor = Executors.newCachedThreadPool();
//
//	private int repeatTimes = 5;
//
//	@Override
//	public String recvMsg(String clientId, Object msg) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	// Qos = 0
//	@Override
//	public String sendMsg(String clientId, Object msg) {
//
////		ClientMapper clientMapper = (ClientMapper) MqttStaticUtil.nativeCache.get(clientId);
////		ClientMapper clientMapper = (ClientMapper) MqttServerHandler.cacheService.get(clientId);
////		ChannelId cid = clientMapper.getChannelId();
//		Channel ch = MqttServerHandler.channelGroup.find(cid);
//		ch.writeAndFlush(msg);
//		return "message sent";
//	}
//
//	// Qos = 1
//	@Override
//	public String checkAndSendMsg(String clientId, Object msg) {
//
//		ClientMapper clientMapper = (ClientMapper) MqttServerHandler.cacheService.get(clientId);
//		ChannelId cid = clientMapper.getChannelId();
//		ChannelStatusMapper channelStatusMapper = (ChannelStatusMapper) MqttServerHandler.cacheService
//				.get(cid.asLongText());
//		Channel ch = MqttServerHandler.channelGroup.find(cid);
//
//		for (int i = 0; i < repeatTimes; i++) {
//			if (channelStatusMapper.getStatus() != MqttMessageType.PUBACK) {
//				ch.writeAndFlush(msg);
//			}
//			try {
//				Thread.currentThread();
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		MqttServerHandler.cacheService.remove(cid.asLongText());
//		return "message sent!";
//	}
//
//	// Qos = 2
//	@Override
//	public String checkWaitAndSendMsg(String clientId, Object msg) {
//
//		ClientMapper clientMapper = (ClientMapper) MqttServerHandler.cacheService.get(clientId);
//		ChannelId cid = clientMapper.getChannelId();
//		ChannelStatusMapper channelStatusMapper = (ChannelStatusMapper) MqttServerHandler.cacheService
//				.get(cid.asLongText());
//		Channel ch = MqttServerHandler.channelGroup.find(cid);
//
//		for (int i = 0; i < repeatTimes; i++) {
//			if (channelStatusMapper.getStatus() != MqttMessageType.PUBREL
//					&& channelStatusMapper.getStatus() != MqttMessageType.PUBCOMP) {
//				ch.writeAndFlush(msg);
//			}
//			try {
//				Thread.currentThread();
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		MqttServerHandler.cacheService.remove(cid.asLongText());
//		return "message sent!";
//	}
//
//	@Override
//	public String publishMsgAtMostOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds) {
//
//		for (String clientId : clientIds) {
//			// 开启发送线程！
//			executor.execute(new SendTask(clientId, mqttPublishMessage));
//		}
//
//		return null;
//	}
//
//	@Override
//	public String publishMsgAtleastOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds) {
//
//		for (String clientId : clientIds) {
//			executor.execute(new CheckAndSendTask(clientId, mqttPublishMessage));
//		}
//		return null;
//	}
//
//	@Override
//	public String publishMsgExactlyOnce(MqttPublishMessage mqttPublishMessage, List<String> clientIds) {
//
//		for (String clientId : clientIds) {
//			executor.execute(new CheckWaitAndSendMsg(clientId, mqttPublishMessage));
//		}
//		return null;
//	}
//
//	class SendTask implements Runnable {
//
//		private String clientId;
//		private Object msg;
//
//		public SendTask(String clientId, Object msg) {
//			this.clientId = clientId;
//			this.msg = msg;
//		}
//
//		@Override
//		public void run() {
//			sendMsg(clientId, msg);
//		}
//	}
//
//	class CheckAndSendTask implements Runnable {
//
//		private String clientId;
//		private Object msg;
//
//		public CheckAndSendTask(String clientId, Object msg) {
//			this.clientId = clientId;
//			this.msg = msg;
//		}
//
//		@Override
//		public void run() {
//			checkAndSendMsg(clientId, msg);
//		}
//	}
//
//	class CheckWaitAndSendMsg implements Runnable {
//
//		private String clientId;
//		private Object msg;
//
//		public CheckWaitAndSendMsg(String clientId, Object msg) {
//			this.clientId = clientId;
//			this.msg = msg;
//		}
//
//		@Override
//		public void run() {
//			checkWaitAndSendMsg(clientId, msg);
//		}
//	}
//}
