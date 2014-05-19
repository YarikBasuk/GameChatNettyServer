package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import db.DBManager;
import db.entity.UserFriend;
import server.StartServer;
import server.WorkerHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 21.04.14
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class DeleteFriendHandler extends IncomingMessageHandler {
    private GameChatProtocol.DeleteFriend packet;
    private int userID;
    private int friendID;

    public DeleteFriendHandler(byte[] data){
        try {
            packet = GameChatProtocol.DeleteFriend.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Override
    public void handle(WorkerHelper workerHelper) {
         userID = packet.getUserID();
         friendID = packet.getFriendID();
        DBManager.deleteFriend(userID,friendID);
        System.out.println("DeleteFriendHandler");
        GameChatProtocol.UpdateFriendsList newUserFriendsList = userUpdateFriendsList();
         workerHelper.sendPacket(newUserFriendsList);
        System.out.println(packet.toString());



        TreeMap<Integer, WorkerHelper> onlineUser = StartServer.onlineUser;
        Set<Map.Entry<Integer, WorkerHelper>> set = onlineUser.entrySet();
        GameChatProtocol.UpdateFriendsList   newFriendsList = updateFriendsList();
        for (Map.Entry<Integer, WorkerHelper> user : set) {
            int userID = user.getKey();
            if (userID ==  friendID) {
                user.getValue().sendPacket(newFriendsList);
                break;
            }
        }


    }



    private GameChatProtocol.UpdateFriendsList userUpdateFriendsList(){
        List<UserFriend> newFriendList = DBManager.searchUserFriends(userID);
        GameChatProtocol.UpdateFriendsList.Builder updateFriendBuilder = GameChatProtocol.UpdateFriendsList.newBuilder();

        for (UserFriend friend: newFriendList){
            int friendID = friend.getFrienrID();
            String friendName = friend.getFriendName();
            GameChatProtocol.UserFriend userFriend = GameChatProtocol.UserFriend.newBuilder()
                    .setFriendID(friendID)
                    .setFriendName(friendName).build();
            updateFriendBuilder.addNewFriendsList(userFriend);
        }

        return updateFriendBuilder.build();
    }



    private GameChatProtocol.UpdateFriendsList updateFriendsList(){
        List<UserFriend> newFriendList = DBManager.searchUserFriends(friendID);
        GameChatProtocol.UpdateFriendsList.Builder updateFriendBuilder = GameChatProtocol.UpdateFriendsList.newBuilder();

        for (UserFriend friend: newFriendList){
            int friendID = friend.getFrienrID();
            String friendName = friend.getFriendName();
            GameChatProtocol.UserFriend userFriend = GameChatProtocol.UserFriend.newBuilder()
                    .setFriendID(friendID)
                    .setFriendName(friendName).build();
            updateFriendBuilder.addNewFriendsList(userFriend);
        }

        return updateFriendBuilder.build();
    }


}
