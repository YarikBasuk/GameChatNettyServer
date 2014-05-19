package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import db.DBManager;
import db.entity.UserFriend;
import server.WorkerHelper;

import java.sql.SQLException;
import java.util.List;


public class AddNewFriendsHandler extends IncomingMessageHandler {
    private GameChatProtocol.AddNewFriends packet;
    private int userID;

    public AddNewFriendsHandler(byte[] data ){
        try {
            packet = GameChatProtocol.AddNewFriends.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(WorkerHelper workerHelper) {
       userID = packet.getUserID();
        List<String> friendNamesList = packet.getFriendsNamesList();
        DBManager.addNewFriends(userID,friendNamesList);

       GameChatProtocol.UpdateFriendsList updateFriendsList =  createUpdateMessage();
        workerHelper.sendPacket(updateFriendsList);
    }



    private GameChatProtocol.UpdateFriendsList createUpdateMessage(){
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
}
