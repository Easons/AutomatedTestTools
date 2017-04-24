package com.nantian.att.main.web.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.att.main.web.util.ByteAndIntUtil;

public class ATTClient {
	private static Logger logger = LoggerFactory.getLogger(ATTClient.class);
    private Selector selector;  
    static final int MESSAGE_LENGTH_HEAD = 2;
	byte[] head = new byte[2];
	int bodylen = -1;
    /** 
     * 获得一个Socket通道，并对该通道做一些初始化的工作 
     * @throws IOException 
     */  
    public void initClient() throws IOException {  
        // 获得一个Socket通道  
        SocketChannel channel = SocketChannel.open();  
        // 设置通道为非阻塞  
        channel.configureBlocking(false);  
        // 获得一个通道管理器  
        this.selector = Selector.open();  
        // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调  
        //用channel.finishConnect();才能完成连接  
        Properties prop = new Properties();     
        //读取属性文件a.properties
        String ip= null;
        int port = 0;
	    InputStream in = ATTClient.class.getClassLoader().getResourceAsStream("agent.properties");
        prop.load(in);     ///加载属性列表
        Iterator<String> it=prop.stringPropertyNames().iterator();
         while(it.hasNext()){
             String key=it.next();
             if(key.equals("ip")){
            	 ip = prop.getProperty(key);
            	 logger.info("中间代理ip:", ip);
             }else if (key.equals("port")){
            	 port = Integer.parseInt(prop.getProperty(key));
            	 logger.info("中间代理port:", port);
             }
         }
        in.close();
        channel.connect(new InetSocketAddress(ip,port));  
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。  
        channel.register(selector, SelectionKey.OP_CONNECT);  
    }  
  
    /** 
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理 
     * @throws IOException 
     */  
    public List<String> sendData(byte[] bytes,String dataFormat)  { 
    	List<String> res = new ArrayList<String>();
    	if(dataFormat.equals("outsop")){
    		res.add("此报文为输出报文，不做发送......");
    		return res;
    	}
    	try {
			initClient();
			while (true) {  
			    selector.select();  
			    // 获得selector中选中的项的迭代器  
			    Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();  
			    while (ite.hasNext()) {  
			        SelectionKey key = (SelectionKey) ite.next();  
			        // 删除已选的key,以防重复处理  
			        ite.remove();  
			        // 连接事件发生  
			        if (key.isConnectable()) {  
			            SocketChannel channel = (SocketChannel) key  
			                    .channel();  
			            // 如果正在连接，则完成连接  
			            if(channel.isConnectionPending()){  
			                try {
								channel.finishConnect();
							} catch (ConnectException e) {
								// TODO Auto-generated catch block
								res.add("没有获得连接......");
								logger.error("客户端通信无连接异常："+e.getStackTrace().toString());
							}  
			                  
			            }  
			            channel.configureBlocking(false);  
			            //给服务端发送信息
			            logger.info("客户端给服务端发送信息为："+bytes);
			            channel.write(ByteBuffer.wrap(bytes));  
			            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。  
			            channel.register(this.selector, SelectionKey.OP_READ);  
			            // 获得了可读的事件  
			        } else if (key.isReadable()) {  
			                 res = read(key,dataFormat); 
			                 if(res.size() != 0){
			                	 return res;  
			                 }
			        }  
  
			    }  
  
			}
		} catch (ClosedChannelException e) {
			logger.error("客户端通信Channel关闭异常："+e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error("客户端通信IO异常："+e.getStackTrace().toString());
		}
		return res;  
    }  
    /** 
     * 处理读取服务端发来的信息
     * @param key 
     * @throws IOException  
     */  
    public List<String> read(SelectionKey key,String dataFormat) throws IOException{
    	List<String> list = new ArrayList<String>();
    	// 服务器可读取消息:得到事件发生的Socket通道  
        SocketChannel socket = (SocketChannel) key.channel();  
        // 创建读取的缓冲区  
        ByteBuffer input = ByteBuffer.allocate(1024);
		socket.read(input);
		input.flip();
		//读取数据的原则: 要么读取一个完整的包头，要么读取一个完整包体。不满足这两种情况，不对ByteBuffer进行任何的get操作
				//但是要注意可能发生上次读取了一个完整的包头，下次读才读取一个完整包体情况。
				//所以包头部分必须用类的成员变量进行暂时的存储，当完整读取包头和包体后，在给业务处理部分。
		if(dataFormat.equals("insop")){
			while(input.remaining() > 0){
				if (bodylen < 0) //还没有生成完整的包头部分, 该变量初始值为-1，并且在拼凑一个完整的消息包以后，再将该值设置为-1
				{
					if ( input.remaining() >= MESSAGE_LENGTH_HEAD) //ByteBuffer缓冲区的字节数够拼凑一个包头
					{
						input.get(head, 0, 2);
						bodylen = ByteAndIntUtil.byte2int(head)-2;
						logger.info("客户端读取响应数据，包长度："+bodylen);
					}
					else//ByteBuffer缓冲区的字节数不够拼凑一个包头，什么操作都不做，退出这次处理，继续等待
					{
						logger.info("客户端读取响应数据，缓冲区的字节数不够拼凑一个包头，什么操作都不做，退出这次处理，继续等待");
						break;
					}
				}
				else if(bodylen > 0) //包头部分已经完整生成. 
				{
					if (input.remaining() >= bodylen) //缓冲区的内容够一个包体部分
					{
						byte[] body = new byte[bodylen];
						input.get(body, 0, bodylen);
						byte[] headandbody = new byte[MESSAGE_LENGTH_HEAD + bodylen];
						System.arraycopy(head, 0, headandbody, 0, head.length);
						System.arraycopy(body,0, headandbody, head.length, body.length);
						String bytes2HexString = ByteAndIntUtil.Bytes2HexString(headandbody);
						list.add(bytes2HexString);
						logger.info("客户端读取响应数据，缓冲区的内容够一个包体部分："+bytes2HexString);
						bodylen = -1;					}
					else  ///缓冲区的内容不够一个包体部分，继续等待，跳出循环等待下次再出发该函数
					{
						logger.info("客户端读取响应数据，缓冲区的内容不够一个包体部分，继续等待，跳出循环等待下次再出发该函数");
						break;
					}
				}
				else if(bodylen == 0) //没有包体部分，仅仅有包头的情况
				{
					byte[] headandbody = new byte[MESSAGE_LENGTH_HEAD + bodylen];
					System.arraycopy(head, 0, headandbody, 0, head.length);
					logger.info("客户端读取响应数据，没有包体部分，仅仅有包头的情况");
					bodylen = -1;
				}
			}
		}else if(dataFormat.equals("xml")){
			//有待开发
			list.add("有待开发");
			return list;
		}else if(dataFormat.equals("json")){
			//有待开发
			list.add("有待开发");
			return list;
		}
		key.interestOps(SelectionKey.OP_READ);
		return list;
    }
 }  
	
