package protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import net.handlers.*;




public class ProtoFactory {

    public static IncomingMessageHandler createHandler(byte data[], ProtoType type) throws InvalidProtocolBufferException {

        switch (type) {

            case AUTHORIZATION_PACKET:
                return new AuthorizationHandler(data);
            case REGISTRATION_PACKET:
                return new RegistrationHandler(data);
            case USER_MESSAGE_PACKET:
                return new ResendMessageHandler(data);
            case QUICK_SEARCH_NAMES:
                return new  QuickSearheNamesHandler(data);
            case ADD_NEW_FRIENDS_PACKET:
                return new AddNewFriendsHandler(data);
            case DETETE_FRIEND:
                return new DeleteFriendHandler(data);
            default:
                System.err.println("GET BED PACKET " + type);

        }


        return null;
    }


}
