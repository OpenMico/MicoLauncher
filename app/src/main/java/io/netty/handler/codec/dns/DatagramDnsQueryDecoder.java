package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class DatagramDnsQueryDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final DnsRecordDecoder a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List list) throws Exception {
        decode2(channelHandlerContext, datagramPacket, (List<Object>) list);
    }

    public DatagramDnsQueryDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    public DatagramDnsQueryDecoder(DnsRecordDecoder dnsRecordDecoder) {
        this.a = (DnsRecordDecoder) ObjectUtil.checkNotNull(dnsRecordDecoder, "recordDecoder");
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
        DnsQuery a = a(datagramPacket, byteBuf);
        try {
            int readUnsignedShort = byteBuf.readUnsignedShort();
            int readUnsignedShort2 = byteBuf.readUnsignedShort();
            int readUnsignedShort3 = byteBuf.readUnsignedShort();
            int readUnsignedShort4 = byteBuf.readUnsignedShort();
            a(a, byteBuf, readUnsignedShort);
            a(a, DnsSection.ANSWER, byteBuf, readUnsignedShort2);
            a(a, DnsSection.AUTHORITY, byteBuf, readUnsignedShort3);
            a(a, DnsSection.ADDITIONAL, byteBuf, readUnsignedShort4);
            list.add(a);
        } catch (Throwable th) {
            a.release();
            throw th;
        }
    }

    private static DnsQuery a(DatagramPacket datagramPacket, ByteBuf byteBuf) {
        int readUnsignedShort = byteBuf.readUnsignedShort();
        int readUnsignedShort2 = byteBuf.readUnsignedShort();
        boolean z = true;
        if ((readUnsignedShort2 >> 15) != 1) {
            DatagramDnsQuery datagramDnsQuery = new DatagramDnsQuery(datagramPacket.sender(), datagramPacket.recipient(), readUnsignedShort, DnsOpCode.valueOf((byte) ((readUnsignedShort2 >> 11) & 15)));
            if (((readUnsignedShort2 >> 8) & 1) != 1) {
                z = false;
            }
            datagramDnsQuery.setRecursionDesired(z);
            datagramDnsQuery.setZ((readUnsignedShort2 >> 4) & 7);
            return datagramDnsQuery;
        }
        throw new CorruptedFrameException("not a query");
    }

    private void a(DnsQuery dnsQuery, ByteBuf byteBuf, int i) throws Exception {
        while (i > 0) {
            dnsQuery.addRecord(DnsSection.QUESTION, (DnsRecord) this.a.decodeQuestion(byteBuf));
            i--;
        }
    }

    private void a(DnsQuery dnsQuery, DnsSection dnsSection, ByteBuf byteBuf, int i) throws Exception {
        while (i > 0) {
            DnsRecord decodeRecord = this.a.decodeRecord(byteBuf);
            if (decodeRecord != null) {
                dnsQuery.addRecord(dnsSection, decodeRecord);
                i--;
            } else {
                return;
            }
        }
    }
}
