package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.EmptyArrays;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public final class MqttEncoder extends MessageToMessageEncoder<MqttMessage> {
    public static final MqttEncoder INSTANCE = new MqttEncoder();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage, List list) throws Exception {
        encode2(channelHandlerContext, mqttMessage, (List<Object>) list);
    }

    private MqttEncoder() {
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage, List<Object> list) throws Exception {
        list.add(a(channelHandlerContext.alloc(), mqttMessage));
    }

    static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        switch (mqttMessage.fixedHeader().messageType()) {
            case CONNECT:
                return a(byteBufAllocator, (MqttConnectMessage) mqttMessage);
            case CONNACK:
                return a(byteBufAllocator, (MqttConnAckMessage) mqttMessage);
            case PUBLISH:
                return a(byteBufAllocator, (MqttPublishMessage) mqttMessage);
            case SUBSCRIBE:
                return a(byteBufAllocator, (MqttSubscribeMessage) mqttMessage);
            case UNSUBSCRIBE:
                return a(byteBufAllocator, (MqttUnsubscribeMessage) mqttMessage);
            case SUBACK:
                return a(byteBufAllocator, (MqttSubAckMessage) mqttMessage);
            case UNSUBACK:
            case PUBACK:
            case PUBREC:
            case PUBREL:
            case PUBCOMP:
                return b(byteBufAllocator, mqttMessage);
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                return c(byteBufAllocator, mqttMessage);
            default:
                throw new IllegalArgumentException("Unknown message type: " + mqttMessage.fixedHeader().messageType().value());
        }
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttConnectMessage mqttConnectMessage) {
        MqttFixedHeader fixedHeader = mqttConnectMessage.fixedHeader();
        MqttConnectVariableHeader variableHeader = mqttConnectMessage.variableHeader();
        MqttConnectPayload payload = mqttConnectMessage.payload();
        MqttVersion fromProtocolNameAndLevel = MqttVersion.fromProtocolNameAndLevel(variableHeader.name(), (byte) variableHeader.version());
        String clientIdentifier = payload.clientIdentifier();
        if (a.a(fromProtocolNameAndLevel, clientIdentifier)) {
            byte[] a = a(clientIdentifier);
            int length = a.length + 2 + 0;
            String willTopic = payload.willTopic();
            byte[] a2 = willTopic != null ? a(willTopic) : EmptyArrays.EMPTY_BYTES;
            String willMessage = payload.willMessage();
            byte[] a3 = willMessage != null ? a(willMessage) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.isWillFlag()) {
                length = length + a2.length + 2 + a3.length + 2;
            }
            String userName = payload.userName();
            byte[] a4 = userName != null ? a(userName) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.hasUserName()) {
                length += a4.length + 2;
            }
            String password = payload.password();
            byte[] a5 = password != null ? a(password) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.hasPassword()) {
                length += a5.length + 2;
            }
            byte[] protocolNameBytes = fromProtocolNameAndLevel.protocolNameBytes();
            int length2 = protocolNameBytes.length + 2 + 4 + length;
            ByteBuf buffer = byteBufAllocator.buffer(a(length2) + 1 + length2);
            buffer.writeByte(a(fixedHeader));
            a(buffer, length2);
            buffer.writeShort(protocolNameBytes.length);
            buffer.writeBytes(protocolNameBytes);
            buffer.writeByte(variableHeader.version());
            buffer.writeByte(a(variableHeader));
            buffer.writeShort(variableHeader.keepAliveTimeSeconds());
            buffer.writeShort(a.length);
            buffer.writeBytes(a, 0, a.length);
            if (variableHeader.isWillFlag()) {
                buffer.writeShort(a2.length);
                buffer.writeBytes(a2, 0, a2.length);
                buffer.writeShort(a3.length);
                buffer.writeBytes(a3, 0, a3.length);
            }
            if (variableHeader.hasUserName()) {
                buffer.writeShort(a4.length);
                buffer.writeBytes(a4, 0, a4.length);
            }
            if (variableHeader.hasPassword()) {
                buffer.writeShort(a5.length);
                buffer.writeBytes(a5, 0, a5.length);
            }
            return buffer;
        }
        throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + clientIdentifier);
    }

    private static int a(MqttConnectVariableHeader mqttConnectVariableHeader) {
        int i = mqttConnectVariableHeader.hasUserName() ? 128 : 0;
        if (mqttConnectVariableHeader.hasPassword()) {
            i |= 64;
        }
        if (mqttConnectVariableHeader.isWillRetain()) {
            i |= 32;
        }
        int willQos = i | ((mqttConnectVariableHeader.willQos() & 3) << 3);
        if (mqttConnectVariableHeader.isWillFlag()) {
            willQos |= 4;
        }
        return mqttConnectVariableHeader.isCleanSession() ? willQos | 2 : willQos;
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttConnAckMessage mqttConnAckMessage) {
        ByteBuf buffer = byteBufAllocator.buffer(4);
        buffer.writeByte(a(mqttConnAckMessage.fixedHeader()));
        buffer.writeByte(2);
        buffer.writeByte(mqttConnAckMessage.variableHeader().isSessionPresent() ? 1 : 0);
        buffer.writeByte(mqttConnAckMessage.variableHeader().connectReturnCode().byteValue());
        return buffer;
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttSubscribeMessage mqttSubscribeMessage) {
        MqttFixedHeader fixedHeader = mqttSubscribeMessage.fixedHeader();
        MqttMessageIdVariableHeader variableHeader = mqttSubscribeMessage.variableHeader();
        MqttSubscribePayload payload = mqttSubscribeMessage.payload();
        int i = 0;
        for (MqttTopicSubscription mqttTopicSubscription : payload.topicSubscriptions()) {
            i = i + a(mqttTopicSubscription.topicName()).length + 2 + 1;
        }
        int i2 = 2 + i;
        ByteBuf buffer = byteBufAllocator.buffer(a(i2) + 1 + i2);
        buffer.writeByte(a(fixedHeader));
        a(buffer, i2);
        buffer.writeShort(variableHeader.messageId());
        for (MqttTopicSubscription mqttTopicSubscription2 : payload.topicSubscriptions()) {
            byte[] a = a(mqttTopicSubscription2.topicName());
            buffer.writeShort(a.length);
            buffer.writeBytes(a, 0, a.length);
            buffer.writeByte(mqttTopicSubscription2.qualityOfService().value());
        }
        return buffer;
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttUnsubscribeMessage mqttUnsubscribeMessage) {
        MqttFixedHeader fixedHeader = mqttUnsubscribeMessage.fixedHeader();
        MqttMessageIdVariableHeader variableHeader = mqttUnsubscribeMessage.variableHeader();
        MqttUnsubscribePayload payload = mqttUnsubscribeMessage.payload();
        int i = 0;
        for (String str : payload.topics()) {
            i += a(str).length + 2;
        }
        int i2 = 2 + i;
        ByteBuf buffer = byteBufAllocator.buffer(a(i2) + 1 + i2);
        buffer.writeByte(a(fixedHeader));
        a(buffer, i2);
        buffer.writeShort(variableHeader.messageId());
        for (String str2 : payload.topics()) {
            byte[] a = a(str2);
            buffer.writeShort(a.length);
            buffer.writeBytes(a, 0, a.length);
        }
        return buffer;
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttSubAckMessage mqttSubAckMessage) {
        int size = mqttSubAckMessage.payload().grantedQoSLevels().size() + 2;
        ByteBuf buffer = byteBufAllocator.buffer(a(size) + 1 + size);
        buffer.writeByte(a(mqttSubAckMessage.fixedHeader()));
        a(buffer, size);
        buffer.writeShort(mqttSubAckMessage.variableHeader().messageId());
        for (Integer num : mqttSubAckMessage.payload().grantedQoSLevels()) {
            buffer.writeByte(num.intValue());
        }
        return buffer;
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, MqttPublishMessage mqttPublishMessage) {
        MqttFixedHeader fixedHeader = mqttPublishMessage.fixedHeader();
        MqttPublishVariableHeader variableHeader = mqttPublishMessage.variableHeader();
        ByteBuf duplicate = mqttPublishMessage.payload().duplicate();
        byte[] a = a(variableHeader.topicName());
        int i = 2;
        int length = a.length + 2;
        if (fixedHeader.qosLevel().value() <= 0) {
            i = 0;
        }
        int readableBytes = length + i + duplicate.readableBytes();
        ByteBuf buffer = byteBufAllocator.buffer(a(readableBytes) + 1 + readableBytes);
        buffer.writeByte(a(fixedHeader));
        a(buffer, readableBytes);
        buffer.writeShort(a.length);
        buffer.writeBytes(a);
        if (fixedHeader.qosLevel().value() > 0) {
            buffer.writeShort(variableHeader.messageId());
        }
        buffer.writeBytes(duplicate);
        return buffer;
    }

    private static ByteBuf b(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        MqttFixedHeader fixedHeader = mqttMessage.fixedHeader();
        int messageId = ((MqttMessageIdVariableHeader) mqttMessage.variableHeader()).messageId();
        ByteBuf buffer = byteBufAllocator.buffer(a(2) + 1 + 2);
        buffer.writeByte(a(fixedHeader));
        a(buffer, 2);
        buffer.writeShort(messageId);
        return buffer;
    }

    private static ByteBuf c(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        MqttFixedHeader fixedHeader = mqttMessage.fixedHeader();
        ByteBuf buffer = byteBufAllocator.buffer(2);
        buffer.writeByte(a(fixedHeader));
        buffer.writeByte(0);
        return buffer;
    }

    private static int a(MqttFixedHeader mqttFixedHeader) {
        int value = (mqttFixedHeader.messageType().value() << 4) | 0;
        if (mqttFixedHeader.isDup()) {
            value |= 8;
        }
        int value2 = value | (mqttFixedHeader.qosLevel().value() << 1);
        return mqttFixedHeader.isRetain() ? value2 | 1 : value2;
    }

    private static void a(ByteBuf byteBuf, int i) {
        do {
            int i2 = i % 128;
            i /= 128;
            if (i > 0) {
                i2 |= 128;
            }
            byteBuf.writeByte(i2);
        } while (i > 0);
    }

    private static int a(int i) {
        int i2 = 0;
        do {
            i /= 128;
            i2++;
        } while (i > 0);
        return i2;
    }

    private static byte[] a(String str) {
        return str.getBytes(CharsetUtil.UTF_8);
    }
}
