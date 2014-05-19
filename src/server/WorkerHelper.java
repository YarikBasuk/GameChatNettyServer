package server;

import com.google.protobuf.AbstractMessageLite;
import net.handlers.IncomingMessageHandler;
import org.jboss.netty.channel.Channel;
import protobuf.Envelope;
import protobuf.ProtoType;



public class WorkerHelper {

    private Channel channel;
    private int userID;


    public WorkerHelper(Channel channel) {
        this.channel = channel;
    }



    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }


    public void acceptPacket(IncomingMessageHandler message) {

        message.handle(this);
    }

    public void sendPacket(AbstractMessageLite object) {

        Envelope message = new Envelope();
        byte[] data = object.toByteArray();
        message.setData(data);
        message.setLength(data.length);
        message.setType(ProtoType.fromClass(object.getClass()));
        channel.write(message);

    }



}
