package io.netty.channel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes4.dex */
public interface ChannelHandler {

    @Target({ElementType.TYPE})
    @Inherited
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: classes4.dex */
    public @interface Sharable {
    }

    @Deprecated
    void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception;

    void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception;

    void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception;
}
