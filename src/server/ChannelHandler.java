package server;

import net.handlers.IncomingMessageHandler;
import org.jboss.netty.channel.*;



public class ChannelHandler extends SimpleChannelHandler {
    private WorkerHelper workerHelper;


    public ChannelHandler() {
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {

        workerHelper = new WorkerHelper(e.getChannel());
        System.err.println("connected " + e.getChannel().toString());

    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        if (e.getChannel().isOpen()) {
            workerHelper.acceptPacket((IncomingMessageHandler) e.getMessage());
        }
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
      //  workerHelper.disconnect();

     //   System.err.println("disconnected " + workerHelper.getPlayer());

        StartServer.onlineUser.remove(workerHelper.getUserID());
        System.out.println("channelDisconnected  onlineUser size: "+ StartServer.onlineUser.size());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {

        ctx.getChannel().close();
    }


}
