package io.netty.channel.oio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.RecvByteBufAllocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AbstractOioMessageChannel extends AbstractOioChannel {
    private final List<Object> d = new ArrayList();

    protected abstract int doReadMessages(List<Object> list) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractOioMessageChannel(Channel channel) {
        super(channel);
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doRead() {
        boolean z;
        if (this.c) {
            boolean z2 = false;
            this.c = false;
            ChannelConfig config = config();
            ChannelPipeline pipeline = pipeline();
            RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
            recvBufAllocHandle.reset(config);
            Throwable th = null;
            do {
                try {
                    int doReadMessages = doReadMessages(this.d);
                    if (doReadMessages == 0) {
                        break;
                    } else if (doReadMessages < 0) {
                        z = true;
                        break;
                    } else {
                        recvBufAllocHandle.incMessagesRead(doReadMessages);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z = false;
                }
            } while (recvBufAllocHandle.continueReading());
            z = false;
            int size = this.d.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.c = false;
                    pipeline.fireChannelRead(this.d.get(i));
                }
                this.d.clear();
                recvBufAllocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                z2 = true;
            }
            if (th != null) {
                if (th instanceof IOException) {
                    z = true;
                }
                pipeline.fireExceptionCaught(th);
            }
            if (z) {
                if (isOpen()) {
                    unsafe().close(unsafe().voidPromise());
                }
            } else if (this.c || config.isAutoRead() || (!z2 && isActive())) {
                read();
            }
        }
    }
}
