package io.netty.handler.codec.memcache;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.memcache.MemcacheMessage;

/* loaded from: classes4.dex */
public abstract class AbstractMemcacheObjectAggregator<H extends MemcacheMessage> extends MessageAggregator<MemcacheObject, H, MemcacheContent, FullMemcacheMessage> {
    protected boolean isContentLengthInvalid(H h, int i) {
        return false;
    }

    protected Object newContinueResponse(H h, int i, ChannelPipeline channelPipeline) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.MessageAggregator
    protected /* bridge */ /* synthetic */ boolean isContentLengthInvalid(Object obj, int i) throws Exception {
        return isContentLengthInvalid((AbstractMemcacheObjectAggregator<H>) ((MemcacheMessage) obj), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.MessageAggregator
    protected /* bridge */ /* synthetic */ Object newContinueResponse(Object obj, int i, ChannelPipeline channelPipeline) throws Exception {
        return newContinueResponse((AbstractMemcacheObjectAggregator<H>) ((MemcacheMessage) obj), i, channelPipeline);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractMemcacheObjectAggregator(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentMessage(MemcacheObject memcacheObject) throws Exception {
        return memcacheObject instanceof MemcacheContent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLastContentMessage(MemcacheContent memcacheContent) throws Exception {
        return memcacheContent instanceof LastMemcacheContent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAggregated(MemcacheObject memcacheObject) throws Exception {
        return memcacheObject instanceof FullMemcacheMessage;
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean closeAfterContinueResponse(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean ignoreContentAfterContinueResponse(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
