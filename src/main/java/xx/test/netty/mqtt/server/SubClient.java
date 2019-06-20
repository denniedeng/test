package xx.test.netty.mqtt.server;

import java.io.Serializable;

public class SubClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String clientId;
	private Integer messageId;
	private Integer subQos;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getSubQos() {
		return subQos;
	}

	public void setSubQos(Integer subQos) {
		this.subQos = subQos;
	}
}
