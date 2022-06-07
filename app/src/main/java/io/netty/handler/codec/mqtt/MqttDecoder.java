package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class MqttDecoder extends ReplayingDecoder<a> {
    private MqttFixedHeader c;
    private Object d;
    private Object e;
    private int f;
    private final int g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        READ_FIXED_HEADER,
        READ_VARIABLE_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE
    }

    public MqttDecoder() {
        this(8092);
    }

    public MqttDecoder(int i) {
        super(a.READ_FIXED_HEADER);
        this.g = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0038 A[Catch: Exception -> 0x00e0, TRY_LEAVE, TryCatch #0 {Exception -> 0x00e0, blocks: (B:8:0x0032, B:10:0x0038, B:20:0x00c2, B:21:0x00df), top: B:25:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0073 A[Catch: Exception -> 0x00b9, TryCatch #1 {Exception -> 0x00b9, blocks: (B:11:0x0052, B:13:0x0073, B:15:0x008d, B:16:0x00b8), top: B:26:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x008d A[Catch: Exception -> 0x00b9, TryCatch #1 {Exception -> 0x00b9, blocks: (B:11:0x0052, B:13:0x0073, B:15:0x008d, B:16:0x00b8), top: B:26:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00c2 A[Catch: Exception -> 0x00e0, TRY_ENTER, TryCatch #0 {Exception -> 0x00e0, blocks: (B:8:0x0032, B:10:0x0038, B:20:0x00c2, B:21:0x00df), top: B:25:0x0032 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r3, io.netty.buffer.ByteBuf r4, java.util.List<java.lang.Object> r5) throws java.lang.Exception {
        /*
            r2 = this;
            int[] r3 = io.netty.handler.codec.mqtt.MqttDecoder.AnonymousClass1.a
            java.lang.Object r0 = r2.state()
            io.netty.handler.codec.mqtt.MqttDecoder$a r0 = (io.netty.handler.codec.mqtt.MqttDecoder.a) r0
            int r0 = r0.ordinal()
            r3 = r3[r0]
            switch(r3) {
                case 1: goto L_0x001f;
                case 2: goto L_0x0032;
                case 3: goto L_0x0052;
                case 4: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            java.lang.Error r3 = new java.lang.Error
            r3.<init>()
            throw r3
        L_0x0017:
            int r3 = r2.actualReadableBytes()
            r4.skipBytes(r3)
            goto L_0x008c
        L_0x001f:
            io.netty.handler.codec.mqtt.MqttFixedHeader r3 = a(r4)
            r2.c = r3
            io.netty.handler.codec.mqtt.MqttFixedHeader r3 = r2.c
            int r3 = r3.remainingLength()
            r2.f = r3
            io.netty.handler.codec.mqtt.MqttDecoder$a r3 = io.netty.handler.codec.mqtt.MqttDecoder.a.READ_VARIABLE_HEADER
            r2.checkpoint(r3)
        L_0x0032:
            int r3 = r2.f     // Catch: Exception -> 0x00e0
            int r0 = r2.g     // Catch: Exception -> 0x00e0
            if (r3 > r0) goto L_0x00c2
            io.netty.handler.codec.mqtt.MqttFixedHeader r3 = r2.c     // Catch: Exception -> 0x00e0
            io.netty.handler.codec.mqtt.MqttDecoder$b r3 = a(r4, r3)     // Catch: Exception -> 0x00e0
            java.lang.Object r0 = io.netty.handler.codec.mqtt.MqttDecoder.b.a(r3)     // Catch: Exception -> 0x00e0
            r2.d = r0     // Catch: Exception -> 0x00e0
            int r0 = r2.f     // Catch: Exception -> 0x00e0
            int r3 = io.netty.handler.codec.mqtt.MqttDecoder.b.b(r3)     // Catch: Exception -> 0x00e0
            int r0 = r0 - r3
            r2.f = r0     // Catch: Exception -> 0x00e0
            io.netty.handler.codec.mqtt.MqttDecoder$a r3 = io.netty.handler.codec.mqtt.MqttDecoder.a.READ_PAYLOAD     // Catch: Exception -> 0x00e0
            r2.checkpoint(r3)     // Catch: Exception -> 0x00e0
        L_0x0052:
            io.netty.handler.codec.mqtt.MqttFixedHeader r3 = r2.c     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttMessageType r3 = r3.messageType()     // Catch: Exception -> 0x00b9
            int r0 = r2.f     // Catch: Exception -> 0x00b9
            java.lang.Object r1 = r2.d     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttDecoder$b r3 = a(r4, r3, r0, r1)     // Catch: Exception -> 0x00b9
            java.lang.Object r4 = io.netty.handler.codec.mqtt.MqttDecoder.b.a(r3)     // Catch: Exception -> 0x00b9
            r2.e = r4     // Catch: Exception -> 0x00b9
            int r4 = r2.f     // Catch: Exception -> 0x00b9
            int r3 = io.netty.handler.codec.mqtt.MqttDecoder.b.b(r3)     // Catch: Exception -> 0x00b9
            int r4 = r4 - r3
            r2.f = r4     // Catch: Exception -> 0x00b9
            int r3 = r2.f     // Catch: Exception -> 0x00b9
            if (r3 != 0) goto L_0x008d
            io.netty.handler.codec.mqtt.MqttDecoder$a r3 = io.netty.handler.codec.mqtt.MqttDecoder.a.READ_FIXED_HEADER     // Catch: Exception -> 0x00b9
            r2.checkpoint(r3)     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttFixedHeader r3 = r2.c     // Catch: Exception -> 0x00b9
            java.lang.Object r4 = r2.d     // Catch: Exception -> 0x00b9
            java.lang.Object r0 = r2.e     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttMessage r3 = io.netty.handler.codec.mqtt.MqttMessageFactory.newMessage(r3, r4, r0)     // Catch: Exception -> 0x00b9
            r4 = 0
            r2.c = r4     // Catch: Exception -> 0x00b9
            r2.d = r4     // Catch: Exception -> 0x00b9
            r2.e = r4     // Catch: Exception -> 0x00b9
            r5.add(r3)     // Catch: Exception -> 0x00b9
        L_0x008c:
            return
        L_0x008d:
            io.netty.handler.codec.DecoderException r3 = new io.netty.handler.codec.DecoderException     // Catch: Exception -> 0x00b9
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: Exception -> 0x00b9
            r4.<init>()     // Catch: Exception -> 0x00b9
            java.lang.String r0 = "non-zero remaining payload bytes: "
            r4.append(r0)     // Catch: Exception -> 0x00b9
            int r0 = r2.f     // Catch: Exception -> 0x00b9
            r4.append(r0)     // Catch: Exception -> 0x00b9
            java.lang.String r0 = " ("
            r4.append(r0)     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttFixedHeader r0 = r2.c     // Catch: Exception -> 0x00b9
            io.netty.handler.codec.mqtt.MqttMessageType r0 = r0.messageType()     // Catch: Exception -> 0x00b9
            r4.append(r0)     // Catch: Exception -> 0x00b9
            r0 = 41
            r4.append(r0)     // Catch: Exception -> 0x00b9
            java.lang.String r4 = r4.toString()     // Catch: Exception -> 0x00b9
            r3.<init>(r4)     // Catch: Exception -> 0x00b9
            throw r3     // Catch: Exception -> 0x00b9
        L_0x00b9:
            r3 = move-exception
            io.netty.handler.codec.mqtt.MqttMessage r3 = r2.a(r3)
            r5.add(r3)
            return
        L_0x00c2:
            io.netty.handler.codec.DecoderException r3 = new io.netty.handler.codec.DecoderException     // Catch: Exception -> 0x00e0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: Exception -> 0x00e0
            r4.<init>()     // Catch: Exception -> 0x00e0
            java.lang.String r0 = "too large message: "
            r4.append(r0)     // Catch: Exception -> 0x00e0
            int r0 = r2.f     // Catch: Exception -> 0x00e0
            r4.append(r0)     // Catch: Exception -> 0x00e0
            java.lang.String r0 = " bytes"
            r4.append(r0)     // Catch: Exception -> 0x00e0
            java.lang.String r4 = r4.toString()     // Catch: Exception -> 0x00e0
            r3.<init>(r4)     // Catch: Exception -> 0x00e0
            throw r3     // Catch: Exception -> 0x00e0
        L_0x00e0:
            r3 = move-exception
            io.netty.handler.codec.mqtt.MqttMessage r3 = r2.a(r3)
            r5.add(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.mqtt.MqttDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private MqttMessage a(Throwable th) {
        checkpoint(a.BAD_MESSAGE);
        return MqttMessageFactory.newInvalidMessage(th);
    }

    private static MqttFixedHeader a(ByteBuf byteBuf) {
        int i;
        int i2;
        short readUnsignedByte = byteBuf.readUnsignedByte();
        MqttMessageType valueOf = MqttMessageType.valueOf(readUnsignedByte >> 4);
        int i3 = 0;
        boolean z = (readUnsignedByte & 8) == 8;
        int i4 = (readUnsignedByte & 6) >> 1;
        boolean z2 = (readUnsignedByte & 1) != 0;
        int i5 = 0;
        int i6 = 1;
        while (true) {
            short readUnsignedByte2 = byteBuf.readUnsignedByte();
            i = ((readUnsignedByte2 & 127) * i6) + i3;
            i6 *= 128;
            i5++;
            i2 = readUnsignedByte2 & 128;
            if (i2 == 0 || i5 >= 4) {
                break;
            }
            i3 = i;
        }
        if (i5 != 4 || i2 == 0) {
            return a.a(a.b(new MqttFixedHeader(valueOf, z, MqttQoS.valueOf(i4), z2, i)));
        }
        throw new DecoderException("remaining length exceeds 4 digits (" + valueOf + ')');
    }

    private static b<?> a(ByteBuf byteBuf, MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case CONNECT:
                return b(byteBuf);
            case CONNACK:
                return c(byteBuf);
            case SUBSCRIBE:
            case UNSUBSCRIBE:
            case SUBACK:
            case UNSUBACK:
            case PUBACK:
            case PUBREC:
            case PUBCOMP:
            case PUBREL:
                return d(byteBuf);
            case PUBLISH:
                return b(byteBuf, mqttFixedHeader);
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                return new b<>(null, 0);
            default:
                return new b<>(null, 0);
        }
    }

    private static b<MqttConnectVariableHeader> b(ByteBuf byteBuf) {
        b<String> f = f(byteBuf);
        int i = ((b) f).b;
        boolean z = true;
        MqttVersion fromProtocolNameAndLevel = MqttVersion.fromProtocolNameAndLevel((String) ((b) f).a, byteBuf.readByte());
        short readUnsignedByte = byteBuf.readUnsignedByte();
        b<Integer> h = h(byteBuf);
        int i2 = i + 1 + 1 + ((b) h).b;
        boolean z2 = (readUnsignedByte & 128) == 128;
        boolean z3 = (readUnsignedByte & 64) == 64;
        boolean z4 = (readUnsignedByte & 32) == 32;
        int i3 = (readUnsignedByte & 24) >> 3;
        boolean z5 = (readUnsignedByte & 4) == 4;
        boolean z6 = (readUnsignedByte & 2) == 2;
        if (fromProtocolNameAndLevel == MqttVersion.MQTT_3_1_1) {
            if ((readUnsignedByte & 1) != 0) {
                z = false;
            }
            if (!z) {
                throw new DecoderException("non-zero reserved flag");
            }
        }
        return new b<>(new MqttConnectVariableHeader(fromProtocolNameAndLevel.protocolName(), fromProtocolNameAndLevel.protocolLevel(), z2, z3, z4, i3, z5, z6, ((Integer) ((b) h).a).intValue()), i2);
    }

    private static b<MqttConnAckVariableHeader> c(ByteBuf byteBuf) {
        boolean z = true;
        if ((byteBuf.readUnsignedByte() & 1) != 1) {
            z = false;
        }
        return new b<>(new MqttConnAckVariableHeader(MqttConnectReturnCode.valueOf(byteBuf.readByte()), z), 2);
    }

    private static b<MqttMessageIdVariableHeader> d(ByteBuf byteBuf) {
        b<Integer> e = e(byteBuf);
        return new b<>(MqttMessageIdVariableHeader.from(((Integer) ((b) e).a).intValue()), ((b) e).b);
    }

    private static b<MqttPublishVariableHeader> b(ByteBuf byteBuf, MqttFixedHeader mqttFixedHeader) {
        b<String> f = f(byteBuf);
        if (a.a((String) ((b) f).a)) {
            int i = ((b) f).b;
            int i2 = -1;
            if (mqttFixedHeader.qosLevel().value() > 0) {
                b<Integer> e = e(byteBuf);
                i2 = ((Integer) ((b) e).a).intValue();
                i += ((b) e).b;
            }
            return new b<>(new MqttPublishVariableHeader((String) ((b) f).a, i2), i);
        }
        throw new DecoderException("invalid publish topic name: " + ((String) ((b) f).a) + " (contains wildcards)");
    }

    private static b<Integer> e(ByteBuf byteBuf) {
        b<Integer> h = h(byteBuf);
        if (a.a(((Integer) ((b) h).a).intValue())) {
            return h;
        }
        throw new DecoderException("invalid messageId: " + ((b) h).a);
    }

    private static b<?> a(ByteBuf byteBuf, MqttMessageType mqttMessageType, int i, Object obj) {
        int i2 = AnonymousClass1.b[mqttMessageType.ordinal()];
        if (i2 == 1) {
            return a(byteBuf, (MqttConnectVariableHeader) obj);
        }
        if (i2 == 11) {
            return d(byteBuf, i);
        }
        switch (i2) {
            case 3:
                return a(byteBuf, i);
            case 4:
                return c(byteBuf, i);
            case 5:
                return b(byteBuf, i);
            default:
                return new b<>(null, 0);
        }
    }

    private static b<MqttConnectPayload> a(ByteBuf byteBuf, MqttConnectVariableHeader mqttConnectVariableHeader) {
        b<String> bVar;
        b<String> bVar2;
        b<String> bVar3;
        b<String> bVar4;
        b<String> f = f(byteBuf);
        String str = (String) ((b) f).a;
        if (a.a(MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte) mqttConnectVariableHeader.version()), str)) {
            int i = ((b) f).b;
            String str2 = null;
            if (mqttConnectVariableHeader.isWillFlag()) {
                bVar2 = a(byteBuf, 0, 32767);
                int i2 = i + ((b) bVar2).b;
                bVar = g(byteBuf);
                i = i2 + ((b) bVar).b;
            } else {
                bVar2 = null;
                bVar = null;
            }
            if (mqttConnectVariableHeader.hasUserName()) {
                bVar3 = f(byteBuf);
                i += ((b) bVar3).b;
            } else {
                bVar3 = null;
            }
            if (mqttConnectVariableHeader.hasPassword()) {
                bVar4 = f(byteBuf);
                i += ((b) bVar4).b;
            } else {
                bVar4 = null;
            }
            String str3 = (String) ((b) f).a;
            String str4 = bVar2 != null ? (String) ((b) bVar2).a : null;
            String str5 = bVar != null ? (String) ((b) bVar).a : null;
            String str6 = bVar3 != null ? (String) ((b) bVar3).a : null;
            if (bVar4 != null) {
                str2 = (String) ((b) bVar4).a;
            }
            return new b<>(new MqttConnectPayload(str3, str4, str5, str6, str2), i);
        }
        throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + str);
    }

    private static b<MqttSubscribePayload> a(ByteBuf byteBuf, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < i) {
            b<String> f = f(byteBuf);
            i2 = i2 + ((b) f).b + 1;
            arrayList.add(new MqttTopicSubscription((String) ((b) f).a, MqttQoS.valueOf(byteBuf.readUnsignedByte() & 3)));
        }
        return new b<>(new MqttSubscribePayload(arrayList), i2);
    }

    private static b<MqttSubAckPayload> b(ByteBuf byteBuf, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < i) {
            i2++;
            arrayList.add(Integer.valueOf(byteBuf.readUnsignedByte() & 3));
        }
        return new b<>(new MqttSubAckPayload(arrayList), i2);
    }

    private static b<MqttUnsubscribePayload> c(ByteBuf byteBuf, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < i) {
            b<String> f = f(byteBuf);
            i2 += ((b) f).b;
            arrayList.add(((b) f).a);
        }
        return new b<>(new MqttUnsubscribePayload(arrayList), i2);
    }

    private static b<ByteBuf> d(ByteBuf byteBuf, int i) {
        return new b<>(byteBuf.readRetainedSlice(i), i);
    }

    private static b<String> f(ByteBuf byteBuf) {
        return a(byteBuf, 0, Integer.MAX_VALUE);
    }

    private static b<String> g(ByteBuf byteBuf) {
        b<String> a2 = a(byteBuf, 0, Integer.MAX_VALUE);
        String str = (String) ((b) a2).a;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 127) {
                return new b<>(null, ((b) a2).b);
            }
        }
        return new b<>(str, ((b) a2).b);
    }

    private static b<String> a(ByteBuf byteBuf, int i, int i2) {
        b<Integer> h = h(byteBuf);
        int intValue = ((Integer) ((b) h).a).intValue();
        int i3 = ((b) h).b;
        if (intValue < i || intValue > i2) {
            byteBuf.skipBytes(intValue);
            return new b<>(null, i3 + intValue);
        }
        String byteBuf2 = byteBuf.toString(byteBuf.readerIndex(), intValue, CharsetUtil.UTF_8);
        byteBuf.skipBytes(intValue);
        return new b<>(byteBuf2, i3 + intValue);
    }

    private static b<Integer> h(ByteBuf byteBuf) {
        return b(byteBuf, 0, 65535);
    }

    private static b<Integer> b(ByteBuf byteBuf, int i, int i2) {
        int readUnsignedByte = byteBuf.readUnsignedByte() | (byteBuf.readUnsignedByte() << 8);
        if (readUnsignedByte < i || readUnsignedByte > i2) {
            readUnsignedByte = -1;
        }
        return new b<>(Integer.valueOf(readUnsignedByte), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<T> {
        private final T a;
        private final int b;

        b(T t, int i) {
            this.a = t;
            this.b = i;
        }
    }
}
