package server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import protobuf.Envelope;
import protobuf.ProtoType;


public class PacketFrameEncoder extends OneToOneEncoder {


    private ChannelBuffer encodeMessage(Envelope message) throws IllegalAccessException{
       if (message.getType()== null  || message.getType()== ProtoType.UNKNOWN  ) {
           throw  new IllegalAccessException("Message type cannot be null or UNKNOWN\"");}
        if ((message.getData() == null) || (message.getData().length == 0)) {
            throw new IllegalArgumentException("Message payload cannot be null or empty");
        }
        int size = 5 + message.getData().length;
        ChannelBuffer buffer = ChannelBuffers.buffer(size);
        buffer.writeByte(message.getType().getByteValue());
        buffer.writeInt(message.getData().length);
        buffer.writeBytes(message.getData());
        return buffer;
    }

    @Override
    protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {

        if (o instanceof Envelope) {

            return encodeMessage((Envelope) o);

        } else return o;

    }

}
