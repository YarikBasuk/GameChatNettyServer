package net.handlers;


import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import db.DBManager;
import db.entity.User;
import db.entity.UserFriend;
import db.entity.UserGroup;
import server.StartServer;
import server.WorkerHelper;

import java.util.List;

public class AuthorizationHandler extends IncomingMessageHandler {
  private   String name;
  private   String password;
     private GameChatProtocol.Authorization packet;

    public AuthorizationHandler(byte [] data){
        try {
          packet = GameChatProtocol.Authorization.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(WorkerHelper workerHelper) {
         name = packet.getUserLogin();
         password = packet.getUserPassword();
        System.out.println("Authorization name: "+name+" password: "+password);
       User currentUser = DBManager.searchUser(name,password);


         if(currentUser==null){
              workerHelper.sendPacket(sendPacketUserNotFuond());
        }else {
             GameChatProtocol.AllUserData allUserData =  createMessageUserData(currentUser);

             StartServer.onlineUser.put(currentUser.getUserID(),workerHelper);
             workerHelper.setUserID(currentUser.getUserID());

             System.out.println("oline size "+StartServer.onlineUser.size());

             workerHelper.sendPacket(allUserData);
        }
    }




    private GameChatProtocol.UserNotFound sendPacketUserNotFuond(){
      GameChatProtocol.UserNotFound userNotFoundPacket = GameChatProtocol.UserNotFound.newBuilder()
              .setLogin(name).build();
        return userNotFoundPacket;
    }




    private GameChatProtocol.AllUserData createMessageUserData(User currentUser) {

        GameChatProtocol.AllUserData.Builder userChatData = GameChatProtocol.AllUserData.newBuilder();
        userChatData.setUserID(currentUser.getUserID());
        userChatData.setUserLogin(currentUser.getUserName());

        List<UserFriend> userFriendsList = currentUser.getUserFriendsList();

        for (int i = 0; i < userFriendsList.size(); i++) {
            GameChatProtocol.UserFriend friend = GameChatProtocol.UserFriend.newBuilder()
                    .setFriendID(userFriendsList.get(i).getFrienrID())
                    .setFriendName(userFriendsList.get(i).getFriendName()).build();
            userChatData.addAllUserFriends(friend);
        }

        List<UserGroup> userGroupsList = currentUser.getUserGroupsList();

        for (int i = 0; i < userGroupsList.size(); i++) {
            GameChatProtocol.UserGroup.Builder userGroup = GameChatProtocol.UserGroup.newBuilder();
            userGroup.setGroupID(userGroupsList.get(i).getGroupID());
            userGroup.setGroupName(userGroupsList.get(i).getNameGroup());

            List<UserFriend> groupMemberList = userGroupsList.get(i).getMembersGroupList();

            for(int j = 0; j < groupMemberList.size(); j++){
                GameChatProtocol.UserFriend groupMember = GameChatProtocol.UserFriend.newBuilder()
                        .setFriendID(groupMemberList.get(j).getFrienrID())
                        .setFriendName(groupMemberList.get(j).getFriendName()).build();
                userGroup.addMembersGroup(groupMember);
            }
            userGroup.build();
            userChatData.addAllUserGroup(userGroup);

        }

        return userChatData.build();
    }


}
