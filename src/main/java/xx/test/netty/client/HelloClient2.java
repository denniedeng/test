//package xx.test.netty.client;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
//public class HelloClient2 implements Runnable{
////	public static String host = "127.0.0.1";
//	public static String host = "119.3.31.88";
//    public static int port = 8080;
//    
//    public static int repeatTime = 1000;
//
//    public static Integer errorCount = 0;
//    /**
//     * @param args
//     * @throws InterruptedException 
//     * @throws IOException 
//     */
//    public static void main(String[] args) throws InterruptedException, IOException {
//        for(int i=0;i<1000;i++) {
//        	new Thread(new HelloClient2()).start();
//        	Thread.currentThread().sleep(20);
//        }
//        
//        Thread.currentThread().sleep(60000);
//        System.out.println("error thread is ="+errorCount);
//    }
//    
//    public static void method() throws InterruptedException {
//    	EventLoopGroup group = null;
//    	long st = System.currentTimeMillis();
//        try {
//        	group = new NioEventLoopGroup();
//            Bootstrap b = new Bootstrap();
//            b.group(group)
//            .channel(NioSocketChannel.class)
//            .handler(new HelloClientInitializer());
//
//            b.option(ChannelOption.SO_KEEPALIVE, true);
//            
//            // 连接服务端
//            Channel ch = b.connect(host, port).sync().channel();
//            
//            for(int i=0;i<repeatTime;i++)
//            	ch.writeAndFlush(System.currentTimeMillis()+"\r\n");
//            
////            System.out.println("total time :" +(System.currentTimeMillis()-st));
//        } finally {
//            // The connection is closed automatically on shutdown.
//        	if(group != null)
//        		group.shutdownGracefully();
//        }
//    }
//
//	@Override
//	public void run() {
//		try {
//			method();
//		} catch (Exception e) {
//			e.printStackTrace();
//			synchronized (errorCount) {
//				errorCount++;
//				System.out.println("error count is :"+errorCount);
//			}
//		}
//		
//	}
//}
