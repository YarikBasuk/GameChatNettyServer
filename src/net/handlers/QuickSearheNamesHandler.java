package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import db.DBManager;
import server.WorkerHelper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 15.04.14
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class QuickSearheNamesHandler extends IncomingMessageHandler {
    private GameChatProtocol.QuickSearchNames packet;
    private String partName;

    public QuickSearheNamesHandler(byte[] data) {
        try {
            packet = GameChatProtocol.QuickSearchNames.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Override
    public void handle(WorkerHelper workerHelper) {
        partName = packet.getPartName();
        System.out.println(" QuickSearheNamesHandler" + partName);

        GameChatProtocol.FoundFriends foundFriendsPacket = foundFrindsMessageBuilder();

            workerHelper.sendPacket(foundFriendsPacket);


    }

    private GameChatProtocol.FoundFriends foundFrindsMessageBuilder() {
        GameChatProtocol.FoundFriends.Builder foundFriendsBuilder = GameChatProtocol.FoundFriends.newBuilder();
        GameChatProtocol.FoundFriends foundFriendsMessage = null;

        List<String> foundFriendsList = DBManager.quickSearchUserName(partName);

        if (foundFriendsList.size() > 0) {

            for (String friendName : foundFriendsList) {
                foundFriendsBuilder.addFoundFriends(friendName);
            }

        } else {
            foundFriendsBuilder.addFoundFriends("");
        }
        foundFriendsMessage = foundFriendsBuilder.build();

        return foundFriendsMessage;
    }

}
