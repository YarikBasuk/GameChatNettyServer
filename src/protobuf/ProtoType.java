package protobuf;

import com.protocol.GameChatProtocol;


public enum ProtoType {

    // from client
    AUTHORIZATION_PACKET((byte) 1),
    REGISTRATION_PACKET ((byte) 2),
    USER_MESSAGE_PACKET((byte)3),
    QUICK_SEARCH_NAMES ((byte)4),
    ADD_NEW_FRIENDS_PACKET((byte)5),
    DETETE_FRIEND((byte)6),


    // to client
    ALL_USER_DATA_PACKET((byte)20, GameChatProtocol.AllUserData.class),
    USER_NOT_FOUND_PACKET((byte)21,GameChatProtocol.UserNotFound.class),
    CHECK_LOGIN_RESULT_PACKET((byte)22,GameChatProtocol.CheckLoginResult.class),
    MESSAGE_RESENT((byte)23,GameChatProtocol.UserMessage.class),
    FOUND_FRIENDS_PACKET((byte)24,GameChatProtocol.FoundFriends.class),
    UPDATE_FRIEND_LIST((byte)25,GameChatProtocol.UpdateFriendsList.class),
    UNKNOWN((byte) 0x00);

    private final byte b;
    private Class protoClass;

    private ProtoType(byte b) {
        this.b = b;
    }

    private ProtoType(byte b, Class protoClass) {
        this.b = b;
        this.protoClass = protoClass;
    }


    public static ProtoType fromByte(byte b) {
        for (ProtoType code : values()) {
            if (code.b == b) {
                return code;
            }
        }
        return UNKNOWN;
    }

    public static ProtoType fromClass(Class c) {
        for (ProtoType type : values()) {
            if (type.protoClass != null && type.protoClass.equals(c)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public byte getByteValue() {
        return b;
    }
}
