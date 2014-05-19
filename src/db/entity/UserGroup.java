package db.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Windows
 * Date: 02.01.14
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class UserGroup {
    private int groupID;
    private String groupName;
    private List<UserFriend> membersGroupList;


    public UserGroup(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
        membersGroupList = new ArrayList<UserFriend>();
    }

    public int getGroupID(){
        return groupID;
    }

    public String getNameGroup(){
        return groupName;
    }

    public void setMembersGroupList(List<UserFriend> membersGroupList){
          this.membersGroupList = membersGroupList;
    }

    public  List<UserFriend> getMembersGroupList(){
        return membersGroupList;
    }
}
