package xx.test.netty.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) {
		try {
			// 收到消息直接打印输出
//        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

			// 返回客户端消息 - 我已经接收到了你的消息
			ctx.writeAndFlush("Received your message !\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
	 * 
	 * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		try {
//        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
			ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

			super.channelActive(ctx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * 本方法用作处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
