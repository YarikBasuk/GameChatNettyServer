package db.entity;



public class UserFriend {
    private int frienrID;
    private String friendName;

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public void setFrienrID(int frienrID) {
        this.frienrID = frienrID;
    }


    public UserFriend(int friendID, String friendName) {
        this.frienrID = friendID;
        this.friendName = friendName;
    }

    public int getFrienrID(){
        return frienrID;
    }

    public String getFriendName(){
        return friendName;
    }
}
