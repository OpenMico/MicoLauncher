package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher a;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageToMessageEncoder() {
        this.a = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> cls) {
        this.a = TypeParameterMatcher.get(cls);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.a.match(obj);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [io.netty.handler.codec.a, boolean] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(io.netty.channel.ChannelHandlerContext r8, java.lang.Object r9, io.netty.channel.ChannelPromise r10) throws java.lang.Exception {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            r2 = 1
            boolean r3 = r7.acceptOutboundMessage(r9)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            if (r3 == 0) goto L_0x0047
            io.netty.handler.codec.a r3 = io.netty.handler.codec.a.a()     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            r7.encode(r8, r9, r3)     // Catch: all -> 0x0042
            io.netty.util.ReferenceCountUtil.release(r9)     // Catch: EncoderException -> 0x003f, Throwable -> 0x003c, all -> 0x0039
            boolean r9 = r3.isEmpty()     // Catch: EncoderException -> 0x003f, Throwable -> 0x003c, all -> 0x0039
            if (r9 != 0) goto L_0x001b
            r0 = r3
            goto L_0x004a
        L_0x001b:
            r3.c()     // Catch: EncoderException -> 0x003f, Throwable -> 0x003c, all -> 0x0039
            io.netty.handler.codec.EncoderException r9 = new io.netty.handler.codec.EncoderException     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            r3.<init>()     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            java.lang.String r4 = io.netty.util.internal.StringUtil.simpleClassName(r7)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            r3.append(r4)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            java.lang.String r4 = " must produce at least one message."
            r3.append(r4)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            java.lang.String r3 = r3.toString()     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            r9.<init>(r3)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
            throw r9     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
        L_0x0039:
            r9 = move-exception
            r0 = r3
            goto L_0x008f
        L_0x003c:
            r9 = move-exception
            r0 = r3
            goto L_0x0087
        L_0x003f:
            r9 = move-exception
            r0 = r3
            goto L_0x008e
        L_0x0042:
            r0 = move-exception
            io.netty.util.ReferenceCountUtil.release(r9)     // Catch: EncoderException -> 0x003f, Throwable -> 0x003c, all -> 0x0039
            throw r0     // Catch: EncoderException -> 0x003f, Throwable -> 0x003c, all -> 0x0039
        L_0x0047:
            r8.write(r9, r10)     // Catch: EncoderException -> 0x008d, Throwable -> 0x0086, all -> 0x0084
        L_0x004a:
            if (r0 == 0) goto L_0x0083
            int r9 = r0.size()
            int r9 = r9 - r2
            if (r9 != 0) goto L_0x005b
            java.lang.Object r9 = r0.get(r1)
            r8.write(r9, r10)
            goto L_0x0080
        L_0x005b:
            if (r9 <= 0) goto L_0x0080
            io.netty.channel.ChannelPromise r3 = r8.voidPromise()
            if (r10 != r3) goto L_0x0064
            goto L_0x0065
        L_0x0064:
            r2 = r1
        L_0x0065:
            if (r1 >= r9) goto L_0x0079
            if (r2 == 0) goto L_0x006b
            r4 = r3
            goto L_0x006f
        L_0x006b:
            io.netty.channel.ChannelPromise r4 = r8.newPromise()
        L_0x006f:
            java.lang.Object r5 = r0.a(r1)
            r8.write(r5, r4)
            int r1 = r1 + 1
            goto L_0x0065
        L_0x0079:
            java.lang.Object r9 = r0.a(r9)
            r8.write(r9, r10)
        L_0x0080:
            r0.c()
        L_0x0083:
            return
        L_0x0084:
            r9 = move-exception
            goto L_0x008f
        L_0x0086:
            r9 = move-exception
        L_0x0087:
            io.netty.handler.codec.EncoderException r3 = new io.netty.handler.codec.EncoderException     // Catch: all -> 0x0084
            r3.<init>(r9)     // Catch: all -> 0x0084
            throw r3     // Catch: all -> 0x0084
        L_0x008d:
            r9 = move-exception
        L_0x008e:
            throw r9     // Catch: all -> 0x0084
        L_0x008f:
            if (r0 == 0) goto L_0x00c8
            int r3 = r0.size()
            int r3 = r3 - r2
            if (r3 == 0) goto L_0x00be
            if (r3 <= 0) goto L_0x00c5
            io.netty.channel.ChannelPromise r4 = r8.voidPromise()
            if (r10 != r4) goto L_0x00a1
            goto L_0x00a2
        L_0x00a1:
            r2 = r1
        L_0x00a2:
            if (r1 >= r3) goto L_0x00b6
            if (r2 == 0) goto L_0x00a8
            r5 = r4
            goto L_0x00ac
        L_0x00a8:
            io.netty.channel.ChannelPromise r5 = r8.newPromise()
        L_0x00ac:
            java.lang.Object r6 = r0.a(r1)
            r8.write(r6, r5)
            int r1 = r1 + 1
            goto L_0x00a2
        L_0x00b6:
            java.lang.Object r1 = r0.a(r3)
            r8.write(r1, r10)
            goto L_0x00c5
        L_0x00be:
            java.lang.Object r1 = r0.get(r1)
            r8.write(r1, r10)
        L_0x00c5:
            r0.c()
        L_0x00c8:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.MessageToMessageEncoder.write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise):void");
    }
}
