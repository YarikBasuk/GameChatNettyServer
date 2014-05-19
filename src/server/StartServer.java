package server;


import com.protocol.GameChatProtocol;
import db.ConnectionFactory;
import db.DBConfig;
import db.DBManager;
import db.entity.User;
import db.entity.UserFriend;
import db.entity.UserGroup;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import javax.swing.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public class StartServer {
    public volatile static    TreeMap<Integer,WorkerHelper> onlineUser;

   public static void  startServer()throws UnknownHostException{
       ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1,
               400000000, 2000000000, 60, TimeUnit.SECONDS);
       ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4,
               400000000, 2000000000, 60, TimeUnit.SECONDS);

       ChannelFactory factory = new NioServerSocketChannelFactory(bossExec,ioExec,4);

       ServerBootstrap networkServer = new ServerBootstrap(factory);

       networkServer.setOption("backlog", 500);
       networkServer.setOption("connectTimeoutMillis", 10000);
       networkServer.setOption("child.tcpNoDelay", true);
       networkServer.setOption("child.keepAlive", true);
       networkServer.setOption("readWriteFair", true);
       networkServer.setPipelineFactory(new ServerPipelineFactory());
       Channel channel = networkServer.bind(new InetSocketAddress("localhost",19999));


       System.err.println("Server started : " + InetAddress.getLocalHost().toString());
   }



   public static void main(String args[]) throws Exception {
       onlineUser = new TreeMap<Integer,WorkerHelper >();

       startServer();





   }

}
