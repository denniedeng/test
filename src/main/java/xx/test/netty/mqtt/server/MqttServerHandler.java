package xx.test.netty.mqtt.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribePayload;
import io.netty.handler.timeout.IdleStateHandler;

public class MqttServerHandler extends ChannelHandlerAdapter {
	public static ChannelGroup channelGroup = new DefaultChannelGroup(
			"mqtt-channel-group", null);
	public static Map<String, ChannelId> channelRelationship = new ConcurrentHashMap<String, ChannelId>();
/*
//	public static CacheServiceImpl cacheService = (CacheServiceImpl) 
//			SpringServerContextsUtil.getBean("cacheServiceImpl");
	public static MqttPublishServiceImpl mqttPublishService = new MqttPublishServiceImpl();
	
//	@Autowired
//	CacheService cacheService;
//	@Autowired
//	MqttPublishServiceImpl mqttPublishService;
//	@Autowired
//	MysqlAccountService mysqlAccountService;
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

//	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		MqttMessage mqttMessage = (MqttMessage) msg;
		System.out.println(mqttMessage.toString());
		MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
		MqttMessageType mqttMessageType = mqttFixedHeader.messageType();

		//消息类型判断
		switch (mqttMessageType) {
			
		case CONNECT:
			System.out.println("connect: \r\n" + mqttMessage.toString());
			//消息处理
			MqttConnAckMessage connAckMessage = connect((MqttConnectMessage)mqttMessage, ctx.channel());
            ctx.writeAndFlush(connAckMessage);
			break;
		case PUBLISH:
			System.out.println("publish: \r\n" + mqttMessage.toString());
			MqttPubAckMessage mqttPubAckMessage = publish((MqttPublishMessage)mqttMessage, ctx.channel());
			if (mqttPubAckMessage != null) {
				ctx.writeAndFlush(mqttPubAckMessage);
			}
			break;
		case PUBACK:
			System.out.println("PUBACK: \r\n" + mqttMessage.toString());
			puback(mqttMessage, ctx.channel());
			break;
		case PUBREC:
			System.out.println("PUBREC: \r\n" + mqttMessage.toString());
			MqttMessage ack = pubrec(mqttMessage, ctx.channel());
			break;
		case PUBREL:
			System.out.println("PUBREL: \r\n" + mqttMessage.toString());
			break;
		case PUBCOMP:
			System.out.println("PUBCOMP: \r\n" + mqttMessage.toString());
			break;
		case SUBSCRIBE:
			System.out.println("SUBSCRIBE: \r\n" + mqttMessage.toString());
			MqttSubAckMessage mqttSubAckMessage = subscribe((MqttSubscribeMessage)mqttMessage, ctx.channel());
			if (mqttSubAckMessage != null) {
				ctx.writeAndFlush(mqttSubAckMessage);
			}
			break;
		//case SUBACK:
		case UNSUBSCRIBE:
			System.out.println("UNSUBSCRIBE: \r\n" + mqttMessage.toString());
		//case UNSUBACK:
		case PINGREQ:
			System.out.println("PINGREQ: \r\n" + mqttMessage.toString());
			//返回心跳消息
			byte[] pingresp = {(byte) 0xd0, 0x00};
			ByteBuf buff = ctx.alloc().buffer();
			buff.writeBytes(pingresp);
            ctx.writeAndFlush(buff);
            break;
		//case PINGRESP:
		case DISCONNECT:
			System.out.println("PINGREQ: \r\n" + mqttMessage.toString());
			//返回断开连接消息
			byte[] message = {(byte) 0xe0, 0x00};
			ByteBuf buff1 = ctx.alloc().buffer();
			buff1.writeBytes(message);
            ctx.writeAndFlush(buff1);
            break;
		default:
			break;

		}
	}

//	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress()
				+ "在线");
	}

//	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress()
				+ "掉线");
		
	}
	
	*//**
	 * 
	 * @param mqttMessage
	 * @return
	 *//*
	protected MqttConnAckMessage connect(MqttConnectMessage mqttConnectMessage, Channel ch) {
		
		MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();
		//用户身份验证
		MqttConnectPayload mqttConnectPayload =  mqttConnectMessage.payload();
		if (mqttConnectVariableHeader.hasUserName()) {
			String username = mqttConnectPayload.userName();
			String password = mqttConnectPayload.password();
		}
		//保存对应关系
		channelGroup.add(ch);
//		channelRelationship.put(mqttConnectPayload.clientIdentifier(), ch.id());
//		MqttClientMapper mqttClientMapper = new MqttClientMapper();
//		mqttClientMapper.setClientId(mqttConnectPayload.clientIdentifier());
//		mqttClientMapper.setUserName(mqttConnectPayload.userName());
//		mqttClientMapper.setWillQos(mqttConnectVariableHeader.willQos());
		int keepAliveTimeSeconds = mqttConnectVariableHeader.keepAliveTimeSeconds();
		ch.pipeline().addBefore("MqttServerHandler", "MqttIdleHandler", new IdleStateHandler(keepAliveTimeSeconds, 0, 0));
//		mqttClientMapper.setKeepAliveTimeSeconds(keepAliveTimeSeconds);
//		cacheServiceImpl.saveMqttClient(mqttClientMapper);
		ClientMapper clientMapper = new ClientMapper();
		clientMapper.setClientId(mqttConnectPayload.clientIdentifier());
		clientMapper.setChannelId(ch.id());
		clientMapper.setKeepAliveTimeSeconds(keepAliveTimeSeconds);
		
//		MqttStaticUtil.nativeCache.put(mqttConnectPayload.clientIdentifier(), clientMapper);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), mqttConnectPayload.clientIdentifier());
		cacheService.set(mqttConnectPayload.clientIdentifier(), clientMapper);
		cacheService.set(ch.id().asLongText(), mqttConnectPayload.clientIdentifier());
		
//		ClientMapper clientMapperNew = 
//				(ClientMapper) cacheService.get(mqttConnectPayload.clientIdentifier());
//		ChannelId chid = clientMapperNew.getChannelId();
//		Channel chnl = channelGroup.find(chid);
//		
//		
//		ByteBuf buff = chnl.alloc().buffer();
//		buff.writeBytes("111".getBytes());
//		chnl.writeAndFlush(buff);
		
		//返回消息拼接
		MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
		MqttConnAckVariableHeader mqttConnAckVariableHeader 
			= new MqttConnAckVariableHeader(connectReturnCode,false);
		MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
				MqttMessageType.CONNACK, 
				false, 
				MqttQoS.FAILURE, 
				false, 
				0x02);
		
		MqttConnAckMessage mqttConnAckMessage = new MqttConnAckMessage(
				mqttFixedHeader, 
				mqttConnAckVariableHeader);
		
		return mqttConnAckMessage;
	}
	
	
	protected MqttPubAckMessage publish(MqttPublishMessage mqttPublishMessage, Channel ch) {
		
		MqttFixedHeader mqttFixedHeader = mqttPublishMessage.fixedHeader();
		
		String topicName = mqttPublishMessage.variableHeader().topicName();
//		SubscriberMapper subscriberMapper = (SubscriberMapper) MqttStaticUtil.nativeCache.get(topicName);
		SubscriberMapper subscriberMapper = (SubscriberMapper) cacheService.get(topicName);
		if (subscriberMapper == null) {
			return null;
		}
		List<SubClient> subscribers = subscriberMapper.getSubscribers();
		if (subscribers == null) {
			return null;
		}
		List<String> clientIds = new ArrayList<String>();
		for (SubClient subscriber : subscribers) {
			clientIds.add(subscriber.getClientId());
		}
		ChannelStatusMapper channelStatusMapper = new ChannelStatusMapper();
		channelStatusMapper.setStatus(MqttMessageType.PUBLISH);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), channelStatusMapper);
		cacheService.set(ch.id().asLongText(), channelStatusMapper);
		switch(mqttFixedHeader.qosLevel()) {
		case AT_MOST_ONCE:
			mqttPublishService.publishMsgAtMostOnce(mqttPublishMessage, clientIds);
			break;
		case AT_LEAST_ONCE:
			mqttPublishService.publishMsgAtleastOnce(mqttPublishMessage, clientIds);
			break;
		case EXACTLY_ONCE:
			mqttPublishService.publishMsgExactlyOnce(mqttPublishMessage, clientIds);
			break;
		case FAILURE:
			break;
		default:
			break;
		}
		
		return null;
	}
	
	protected MqttSubAckMessage subscribe(MqttSubscribeMessage mqttSubscribeMessage, 
			Channel ch) {
		MqttMessageIdVariableHeader mqttMessageIdVariableHeader = mqttSubscribeMessage.variableHeader();
		
		//或许可以单独抽出来！！！
		Integer messageId = mqttMessageIdVariableHeader.messageId();
//		String clientId = (String) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
//		ClientMapper clientMapper = (ClientMapper) MqttStaticUtil.nativeCache.get(clientId);
		
		String clientId = (String) cacheService.get(ch.id().asLongText());
		ClientMapper clientMapper = (ClientMapper) cacheService.get(clientId);
		//save messageId <==> clientId 
		MqttSubscribePayload mqttSubscribePayload = mqttSubscribeMessage.payload();
		List<MqttTopicSubscription> topicSubscriptions = mqttSubscribePayload.topicSubscriptions();
		for (MqttTopicSubscription topicSubscription : topicSubscriptions) {
			//遍历订阅主题
//			SubscriberMapper subscriberMapper = (SubscriberMapper) 
//					MqttStaticUtil.nativeCache.get(topicSubscription.topicName());
			SubscriberMapper subscriberMapper = 
					(SubscriberMapper) cacheService.get(topicSubscription.topicName());
			
			if (subscriberMapper == null) {
				subscriberMapper = new SubscriberMapper();
			}
			subscriberMapper.setTopicName(topicSubscription.topicName());
			List<SubClient> subscribers = subscriberMapper.getSubscribers();
			if (subscribers == null) {
				subscribers = new LinkedList<SubClient>();
			}
			SubClient subClient = new SubClient();
			subClient.setClientId(clientId);
			subClient.setMessageId(messageId);
			subClient.setSubQos(topicSubscription.qualityOfService().value());
			subscribers.add(subClient);
			subscriberMapper.setSubscribers(subscribers);
//			MqttStaticUtil.nativeCache.put(topicSubscription.topicName(), subscriberMapper);
			cacheService.set(topicSubscription.topicName(), subscriberMapper);
		}
		
		//save topic-names and Qos
		List<Integer> grantedQoSLevels = new ArrayList<Integer>();
		//service return list 
		MqttSubAckPayload mqttSubAckPayload = new MqttSubAckPayload(grantedQoSLevels);
		MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
				MqttMessageType.SUBACK, 
				false,
				MqttQoS.AT_MOST_ONCE,
				false,
				messageId);
		
		MqttSubAckMessage mqttSubAckMessage = new MqttSubAckMessage(
				mqttFixedHeader, 
				mqttMessageIdVariableHeader, 
				mqttSubAckPayload);
		
		return mqttSubAckMessage;
	}
	
	public MqttMessage unsubscribe(MqttMessage mqttMessage, 
			Channel ch) {
		
		MqttUnsubscribeMessage mqttUnsubscribeMessage = (MqttUnsubscribeMessage) mqttMessage;
		MqttMessageIdVariableHeader mqttMessageIdVariableHeader = mqttUnsubscribeMessage.variableHeader();
		MqttUnsubscribePayload mqttUnsubscribePayload = mqttUnsubscribeMessage.payload();
		
//		String clientId = (String) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
//		ClientMapper clientMapper = (ClientMapper) MqttStaticUtil.nativeCache.get(clientId);
		
		String clientId = (String) cacheService.get(ch.id().asLongText());
		ClientMapper clientMapper = (ClientMapper) cacheService.get(clientId);
		
		Integer messageId = mqttMessageIdVariableHeader.messageId();
		
		List<String> topics = mqttUnsubscribePayload.topics();
		
		for (String topic : topics) {
//			SubscriberMapper subscriberMapper = (SubscriberMapper) MqttStaticUtil.nativeCache.get(topic);
			
			SubscriberMapper subscriberMapper = (SubscriberMapper) cacheService.get(topic);
			if (subscriberMapper != null) {
				List<SubClient> subscribers = subscriberMapper.getSubscribers();
				if (subscribers != null) {
					for (SubClient subClient : subscribers) {
						if (subClient.getClientId() == clientId) {
							subscribers.remove(subClient);
							break;
						}
					}
					subscriberMapper.setSubscribers(subscribers);
					continue;
				}
			}
//			MqttStaticUtil.nativeCache.put(topic, subscriberMapper);
			cacheService.set(topic, subscriberMapper);
		}
		
		return null;
	}
	
	public MqttMessage puback(MqttMessage mqttMessage, 
			Channel ch) {
		
		MqttPubAckMessage mqttPubAckMessage = (MqttPubAckMessage) mqttMessage;
		MqttMessageIdVariableHeader mqttMessageIdVariableHeader = mqttPubAckMessage.variableHeader();
		
		Integer messageId = mqttMessageIdVariableHeader.messageId();
//		String clientId = (String) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
		String clientId = (String) cacheService.get(ch.id().asLongText());
		String publishId = clientId + messageId.toString();
		
//		ChannelStatusMapper channelStatusMapper = 
//				(ChannelStatusMapper) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
		ChannelStatusMapper channelStatusMapper = 
				(ChannelStatusMapper) cacheService.get(ch.id().asLongText());
		channelStatusMapper.setStatus(MqttMessageType.PUBACK);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), channelStatusMapper);
		cacheService.set(ch.id().asLongText(), channelStatusMapper);
//		PublishedMessageMapper publishedMessageMapper = 
//				(PublishedMessageMapper) MqttStaticUtil.nativeCache.get(publishId);
//		ClientMapper clientMapper = (ClientMapper) MqttStaticUtil.nativeCache.get(clientId);
//		
//		String topicName = publishedMessageMapper.getTopicName();
//		SubscriberMapper subscriberMapper = (SubscriberMapper) MqttStaticUtil.nativeCache.get(topicName);
//		List<SubClient> subscribers = subscriberMapper.getSubscribers();
//		for (SubClient subClient : subscribers) {
//			if (subClient.getClientId() == clientId) {
//				subscribers.remove(subClient);
//				break;
//			}
//		}
		
		return null;
	}
	
	public MqttMessage pubrec(MqttMessage mqttMessage, 
			Channel ch) {
		
//		ChannelStatusMapper channelStatusMapper = 
//				(ChannelStatusMapper) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
		ChannelStatusMapper channelStatusMapper = 
				(ChannelStatusMapper) cacheService.get(ch.id().asLongText());
		channelStatusMapper.setStatus(MqttMessageType.PUBREC);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), channelStatusMapper);
		cacheService.set(ch.id().asLongText(), channelStatusMapper);
		
		MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
		MqttFixedHeader mqttFixedHeaderPubrec = new MqttFixedHeader(
				MqttMessageType.PUBREC, 
				mqttFixedHeader.isDup(), 
				mqttFixedHeader.qosLevel(), 
				mqttFixedHeader.isRetain(), 
				mqttFixedHeader.remainingLength());
		
		MqttMessage mqttMessageack = new MqttMessage(mqttFixedHeaderPubrec, mqttMessage.variableHeader());
		return mqttMessageack;
	}
	
	public MqttMessage pubrel(MqttMessage mqttMessage, 
			Channel ch) {
		
//		ChannelStatusMapper channelStatusMapper = 
//				(ChannelStatusMapper) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
		ChannelStatusMapper channelStatusMapper = 
				(ChannelStatusMapper) cacheService.get(ch.id().asLongText());
		channelStatusMapper.setStatus(MqttMessageType.PUBREL);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), channelStatusMapper);
		cacheService.set(ch.id().asLongText(), channelStatusMapper);
				
		MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
		MqttFixedHeader mqttFixedHeaderPubrec = new MqttFixedHeader(
				MqttMessageType.PUBREL, 
				mqttFixedHeader.isDup(), 
				mqttFixedHeader.qosLevel(), 
				mqttFixedHeader.isRetain(), 
				mqttFixedHeader.remainingLength());
		
		MqttMessage mqttMessageack = new MqttMessage(mqttFixedHeaderPubrec, mqttMessage.variableHeader());
		return mqttMessageack;		
	}
	
	public MqttMessage pubcomp(MqttMessage mqttMessage, 
			Channel ch) {
//		ChannelStatusMapper channelStatusMapper = 
//				(ChannelStatusMapper) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
		ChannelStatusMapper channelStatusMapper = 
				(ChannelStatusMapper) cacheService.get(ch.id().asLongText());
		channelStatusMapper.setStatus(MqttMessageType.PUBCOMP);
//		MqttStaticUtil.nativeCache.put(ch.id().asLongText(), channelStatusMapper);
		cacheService.set(ch.id().asLongText(), channelStatusMapper);
		
		MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
		MqttFixedHeader mqttFixedHeaderPubrec = new MqttFixedHeader(
				MqttMessageType.PUBCOMP, 
				mqttFixedHeader.isDup(), 
				mqttFixedHeader.qosLevel(), 
				mqttFixedHeader.isRetain(), 
				mqttFixedHeader.remainingLength());
		
		MqttMessage mqttMessageack = new MqttMessage(mqttFixedHeaderPubrec, mqttMessage.variableHeader());
		return mqttMessageack;
	}
	
	
	public void disconnect(Channel ch) {

//		String clientId = (String) MqttStaticUtil.nativeCache.get(ch.id().asLongText());
//		ClientMapper clientMapper = (ClientMapper) MqttStaticUtil.nativeCache.get(clientId);
		String clientId = (String) cacheService.get(ch.id().asLongText());
		ClientMapper clientMapper = (ClientMapper) cacheService.get(clientId);
		
		cacheService.remove(clientId);
//		MqttStaticUtil.nativeCache.remove(clientMapper);
//		MqttStaticUtil.nativeCache.remove(ch.id().asLongText());
	}
*/
}
