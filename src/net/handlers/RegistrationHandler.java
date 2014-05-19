package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.GameChatProtocol;
import db.DBManager;
import server.WorkerHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 12.04.14
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationHandler extends IncomingMessageHandler {
   private String login;
   private String password;
    private GameChatProtocol.Registration packet;

      public RegistrationHandler(byte [] data){
          try {
              packet = GameChatProtocol.Registration.parseFrom(data);
          } catch (InvalidProtocolBufferException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
      }
    @Override
    public void handle(WorkerHelper workerHelper) {
           login = packet.getUserLogin();
           password = packet.getUserPassword();

        boolean loginIsUsing = DBManager.checkLoginIsUsing(login);

        if(loginIsUsing==true){
            GameChatProtocol.CheckLoginResult checkLoginResult = GameChatProtocol.CheckLoginResult.newBuilder()
                    .setLogin(login).build();
            workerHelper.sendPacket(checkLoginResult);

        }else {
           int userID = DBManager.insertNewUser(login,password);
            GameChatProtocol.AllUserData allUserData = GameChatProtocol.AllUserData.newBuilder()
                    .setUserID(userID)
                    .setUserLogin(login).build();
            workerHelper.sendPacket(allUserData);
        }
    }
}
