package xx.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class FileChannelTest {
	private static final int SIZE = 1024; 
	
	public static void main(String[] args) throws IOException {
//		method1();
//		method2();
		selectorDemo();
	}
	
	public static void method1() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("e:/nio-data.txt", "rw");
	    FileChannel inChannel = aFile.getChannel();

	    ByteBuffer buf = ByteBuffer.allocate(48);

	    int bytesRead = inChannel.read(buf);
	    while (bytesRead != -1) {

	      System.out.println("Read " + bytesRead);
	      buf.flip();

	      while(buf.hasRemaining()){
	          System.out.print((char) buf.get());
	      }

	      buf.clear();
	      bytesRead = inChannel.read(buf);
	    }
	    aFile.close();
	}
	
	public static void method2() throws IOException {
		// 获取通道，该通道允许写操作  
        FileChannel fc = new FileOutputStream("e:/nio-data.txt").getChannel();  
        // 将字节数组包装到缓冲区中  
        fc.write(ByteBuffer.wrap("Some text".getBytes()));  
        // 关闭通道  
        fc.close();  
  
        // 随机读写文件流创建的管道  
        fc = new RandomAccessFile("e:/nio-data.txt", "rw").getChannel();  
        // fc.position()计算从文件的开始到当前位置之间的字节数  
        System.out.println("此通道的文件位置：" + fc.position());  
        // 设置此通道的文件位置,fc.size()此通道的文件的当前大小,该条语句执行后，通道位置处于文件的末尾  
        fc.position(fc.size());  
        // 在文件末尾写入字节  
        fc.write(ByteBuffer.wrap("Some more".getBytes()));  
        fc.close();  
  
        // 用通道读取文件  
        fc = new FileInputStream("e:/nio-data.txt").getChannel();  
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);  
        // 将文件内容读到指定的缓冲区中  
        fc.read(buffer);  
        buffer.flip();// 此行语句一定要有  
        while (buffer.hasRemaining()) {  
            System.out.print((char) buffer.get());  
        }  
        fc.close();  
	}
	
	public static void selectorDemo() throws IOException {

		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.socket().bind(new InetSocketAddress("localhost", 7001));
		channel.configureBlocking(false);

		
		Selector selector = Selector.open();
		SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);


		while(true) {

		  int readyChannels = selector.select();

		  if(readyChannels == 0) continue;


		  Set<SelectionKey> selectedKeys = selector.selectedKeys();

		  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

		  while(keyIterator.hasNext()) {

		    SelectionKey k = keyIterator.next();

		    if(k.isAcceptable()) {
		        // a connection was accepted by a ServerSocketChannel.
		    	System.out.println("a connection was accepted by a ServerSocketChannel.");

		    } else if (k.isConnectable()) {
		        // a connection was established with a remote server.
		    	System.out.println("a connection was established with a remote server.");

		    } else if (k.isReadable()) {
		        // a channel is ready for reading
		    	System.out.println("a channel is ready for reading");

		    } else if (k.isWritable()) {
		        // a channel is ready for writing
		    	System.out.println("a channel is ready for writing");
		    }

		    keyIterator.remove();
		  }
		}
	}
}
