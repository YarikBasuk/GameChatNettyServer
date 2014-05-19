package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import server.StartServer;
import server.WorkerHelper;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 13.04.14
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class ResendMessageHandler extends IncomingMessageHandler {
    private GameChatProtocol.UserMessage packet;

    public ResendMessageHandler(byte[] data) {
        try {
            packet = GameChatProtocol.UserMessage.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void handle(WorkerHelper workerHelper) {
        System.out.println(packet.toString());
        
        int userReceverID = packet.getReceiverID();

        TreeMap<Integer, WorkerHelper> onlineUser = StartServer.onlineUser;
        Set<Map.Entry<Integer, WorkerHelper>> set = onlineUser.entrySet();

        for (Map.Entry<Integer, WorkerHelper> user : set) {
            int userID = user.getKey();
            if (userID == userReceverID) {
                user.getValue().sendPacket(packet);
                break;
            }
        }

    }
}
