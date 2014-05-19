package db.entity;



import java.util.ArrayList;
import java.util.List;

public class User {
    private int userID;
    private String userName;
    private List<UserFriend> userFriendsList;
    private List<UserGroup> userGroupsList;

    public User(){
    }

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        userGroupsList = new  ArrayList<UserGroup>();
        userFriendsList = new ArrayList<UserFriend>();
    }

    public int getUserID(){
        return userID;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserFriendsList(List<UserFriend>  userFriendsList){
           this.userFriendsList = userFriendsList;
    }

    public List<UserFriend> getUserFriendsList(){
        return userFriendsList;
    }

    public void setUserGroupsList(List<UserGroup> userGroupsList){
        this.userGroupsList =  userGroupsList;
    }

    public List<UserGroup> getUserGroupsList(){
        return userGroupsList;
    }

    public void printData(){
       System.out.println("UserID: "+userID);
       System.out.println("UserName: "+userName);
       System.out.println();
       System.out.println("USER FRIENDS");
       for(int i= 0; i< userFriendsList.size();i++){
           System.out.println(userFriendsList.get(i).getFriendName());
       }

        System.out.println();
        for(int i=0;i<userGroupsList.size();i++){
            System.out.println("GROUP NAME: "+userGroupsList.get(i).getNameGroup());
            for(int j = 0; j<userGroupsList.get(i).getMembersGroupList().size();j++){
                System.out.println(userGroupsList.get(i).getMembersGroupList().get(j).getFriendName());
            }
            System.out.println();
        }
    }
}
