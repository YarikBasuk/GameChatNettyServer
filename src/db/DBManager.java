package db;


import db.entity.User;
import db.entity.UserFriend;
import db.entity.UserGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String TABLE_USERS = "game_chat_database.users";
	
	private static final String zzz = "ddf";


    public static int insertNewUser(String login, String password) {
        String query = "call sp_insertNewUser('" + login + "','" + password + "');";
        int id = -1;
        try {
            ResultSet result = DBExecutor.executeSelect(query);

            while (result.next()) {
                id = result.getInt("idUsers");
                return id;
            }
        } catch (SQLException e) {
            return id;
        }
        return id;
    }


    public static boolean checkLoginIsUsing(String login) {
        String query = "SELECT idUsers, login FROM users WHERE login = '" + login + "'";
        boolean loginIsUsing = false;
        try {
            ResultSet result = DBExecutor.executeSelect(query);
            while (result.next()) {
                int id = result.getInt("idUsers");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginIsUsing;
    }


    public static List<UserFriend> searchUserFriends(int userID)  {
        String query = "SELECT idUsers as friendsID, login as friendsNames FROM Users" +
                "\tINNER JOIN (SELECT idFriends FROM friends WHERE idUsers =" + userID + ") as res\n" +
                "\tON Users.idUsers   = res.idFriends;";
        ResultSet result = null;
        List<UserFriend> userFriendsList = new ArrayList<UserFriend>();
        try {
            result = DBExecutor.executeSelect(query);

            while (result.next()) {
                int friendID = result.getInt("friendsID");
                String friendName = result.getString("friendsNames");

                UserFriend userFriend = new UserFriend(friendID, friendName);
                userFriendsList.add(userFriend);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return userFriendsList;
    }



    public static List<UserFriend> searchGroupMembers(int groupID) throws SQLException {
        String query = "SELECT Users.idUsers as memberGroupID, login as memberGroupName FROM Users\n" +
                "\tINNER JOIN (SELECT idUsers FROM content_group WHERE idgroups = " + groupID + ") as resul\n" +
                "\tON Users.idUsers = resul.idUsers;";
        ResultSet result = DBExecutor.executeSelect(query);
        List<UserFriend> membersGroupList = new ArrayList<UserFriend>();

        while (result.next()) {
            int memberGroupID = result.getInt("memberGroupID");
            String memberGroupName = result.getString("memberGroupName");

            UserFriend userFriend = new UserFriend(memberGroupID, memberGroupName);
            membersGroupList.add(userFriend);
        }
        return membersGroupList;
    }


    public static List<UserGroup> searchUserGoups(int userID) throws SQLException {

        String query = "SELECT idgroups as groupID, name_group as groupName FROM groups WHERE idUser = " + userID + ";";
        ResultSet result = DBExecutor.executeSelect(query);
        List<UserGroup> userGroupsList = new ArrayList<UserGroup>();

        while (result.next()) {
            int groupId = result.getInt("groupID");
            String groupName = result.getString("groupName");

            UserGroup currentGroup = new UserGroup(groupId, groupName);
            currentGroup.setMembersGroupList(searchGroupMembers(groupId));
            userGroupsList.add(currentGroup);
        }
        return userGroupsList;
    }


    public static User searchUser(String login, String password) {
        String query = "SELECT idUsers, login as name FROM Users WHERE login = '" + login + "' AND password = '" + password + "';";
        User currentUser = null;

        try {
            ResultSet result = DBExecutor.executeSelect(query);

            while (result.next()) {
                int userID = result.getInt("idUsers");
                String userName = result.getString("name");
                currentUser = new User(userID, userName);
                currentUser.setUserFriendsList(searchUserFriends(userID));
                currentUser.setUserGroupsList(searchUserGoups(userID));
                return currentUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return currentUser;
    }

    public static List<String> quickSearchUserName(String partName) {
        String query = "call sp_quickSearchUserName('" + partName + "');";
        List<String> partNameList = new ArrayList<String>();

        try {
            ResultSet result = DBExecutor.executeSelect(query);

            while (result.next()) {
                String userName = result.getString("login");
                partNameList.add(userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partNameList;
    }

    public static void addNewFriends(int userID, List<String> friendsName) {

        if (friendsName.size() > 0) {
            for(String name: friendsName){
            String query = "call sp_addNewFriend("+userID+",'" + name + "');";
            DBExecutor.executeInsert(query);
            }
        }
    }


    public static void deleteFriend(int userID, int friendsID) {
        String query = "call sp_deleteFriend("+userID+",'" +friendsID + "');";
        DBExecutor.executeInsert(query);
        }

     public static void saveMessage(int senderID, int receiverID, String message){
         String query = "call sp_saveMessage("+senderID+","+receiverID+",'"+message+"');";
         DBExecutor.executeInsert(query);
     }
}
