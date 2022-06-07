package io.netty.handler.flow;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;

/* loaded from: classes4.dex */
public class FlowControlHandler extends ChannelDuplexHandler {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(FlowControlHandler.class);
    private final boolean b;
    private a c;
    private ChannelConfig d;
    private boolean e;

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public FlowControlHandler() {
        this(true);
    }

    public FlowControlHandler(boolean z) {
        this.b = z;
    }

    private void a() {
        a aVar = this.c;
        if (aVar != null) {
            if (!aVar.isEmpty()) {
                a.trace("Non-empty queue: {}", this.c);
                if (this.b) {
                    while (true) {
                        Object poll = this.c.poll();
                        if (poll == null) {
                            break;
                        }
                        ReferenceCountUtil.safeRelease(poll);
                    }
                }
            }
            this.c.b();
            this.c = null;
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.d = channelHandlerContext.channel().config();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        channelHandlerContext.fireChannelInactive();
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (a(channelHandlerContext, 1) == 0) {
            this.e = true;
            channelHandlerContext.read();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.c == null) {
            this.c = a.a();
        }
        this.c.offer(obj);
        boolean z = this.e;
        this.e = false;
        a(channelHandlerContext, z ? 1 : 0);
    }

    private int a(ChannelHandlerContext channelHandlerContext, int i) {
        int i2 = 0;
        if (this.c == null) {
            return 0;
        }
        while (true) {
            if (i2 >= i && !this.d.isAutoRead()) {
                break;
            }
            Object poll = this.c.poll();
            if (poll == null) {
                break;
            }
            i2++;
            channelHandlerContext.fireChannelRead(poll);
        }
        if (this.c.isEmpty() && i2 > 0) {
            channelHandlerContext.fireChannelReadComplete();
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a extends ArrayDeque<Object> {
        private static final Recycler<a> a = new Recycler<a>() { // from class: io.netty.handler.flow.FlowControlHandler.a.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public a newObject(Recycler.Handle<a> handle) {
                return new a(2, handle);
            }
        };
        private static final long serialVersionUID = 0;
        private final Recycler.Handle<a> handle;

        public static a a() {
            return a.get();
        }

        private a(int i, Recycler.Handle<a> handle) {
            super(i);
            this.handle = handle;
        }

        public void b() {
            clear();
            this.handle.recycle(this);
        }
    }
}
