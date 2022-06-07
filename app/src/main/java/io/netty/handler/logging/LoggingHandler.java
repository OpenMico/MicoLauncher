package io.netty.handler.logging;

import com.xiaomi.onetrack.a.a;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import org.eclipse.jetty.http.HttpMethods;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class LoggingHandler extends ChannelDuplexHandler {
    private static final LogLevel a = LogLevel.DEBUG;
    private final LogLevel b;
    protected final InternalLogLevel internalLevel;
    protected final InternalLogger logger;

    public LoggingHandler() {
        this(a);
    }

    public LoggingHandler(LogLevel logLevel) {
        if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(getClass());
            this.b = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
            return;
        }
        throw new NullPointerException(a.d);
    }

    public LoggingHandler(Class<?> cls) {
        this(cls, a);
    }

    public LoggingHandler(Class<?> cls, LogLevel logLevel) {
        if (cls == null) {
            throw new NullPointerException("clazz");
        } else if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(cls);
            this.b = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
        } else {
            throw new NullPointerException(a.d);
        }
    }

    public LoggingHandler(String str) {
        this(str, a);
    }

    public LoggingHandler(String str, LogLevel logLevel) {
        if (str == null) {
            throw new NullPointerException("name");
        } else if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(str);
            this.b = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
        } else {
            throw new NullPointerException(a.d);
        }
    }

    public LogLevel level() {
        return this.b;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "REGISTERED"));
        }
        channelHandlerContext.fireChannelRegistered();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "UNREGISTERED"));
        }
        channelHandlerContext.fireChannelUnregistered();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "ACTIVE"));
        }
        channelHandlerContext.fireChannelActive();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "INACTIVE"));
        }
        channelHandlerContext.fireChannelInactive();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "EXCEPTION", th), th);
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "USER_EVENT", obj));
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "BIND", socketAddress));
        }
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, HttpMethods.CONNECT, socketAddress, socketAddress2));
        }
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "DISCONNECT"));
        }
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "CLOSE"));
        }
        channelHandlerContext.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "DEREGISTER"));
        }
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "RECEIVED", obj));
        }
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "WRITE", obj));
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "FLUSH"));
        }
        channelHandlerContext.flush();
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String str) {
        String obj = channelHandlerContext.channel().toString();
        StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length());
        sb.append(obj);
        sb.append(' ');
        sb.append(str);
        return sb.toString();
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String str, Object obj) {
        if (obj instanceof ByteBuf) {
            return a(channelHandlerContext, str, (ByteBuf) obj);
        }
        if (obj instanceof ByteBufHolder) {
            return a(channelHandlerContext, str, (ByteBufHolder) obj);
        }
        return a(channelHandlerContext, str, obj);
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String str, Object obj, Object obj2) {
        if (obj2 == null) {
            return a(channelHandlerContext, str, obj);
        }
        String obj3 = channelHandlerContext.channel().toString();
        String valueOf = String.valueOf(obj);
        String obj4 = obj2.toString();
        return ((obj3.length() + 1) + str + 2 + valueOf.length() + 2 + obj4.length()) + obj3 + ' ' + str + ": " + valueOf + ", " + obj4;
    }

    private static String a(ChannelHandlerContext channelHandlerContext, String str, ByteBuf byteBuf) {
        String obj = channelHandlerContext.channel().toString();
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length() + 4);
            sb.append(obj);
            sb.append(' ');
            sb.append(str);
            sb.append(": 0B");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder(obj.length() + 1 + str.length() + 2 + 10 + 1 + 2 + (((readableBytes / 16) + (readableBytes % 15 == 0 ? 0 : 1) + 4) * 80));
        sb2.append(obj);
        sb2.append(' ');
        sb2.append(str);
        sb2.append(": ");
        sb2.append(readableBytes);
        sb2.append('B');
        sb2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb2, byteBuf);
        return sb2.toString();
    }

    private static String a(ChannelHandlerContext channelHandlerContext, String str, ByteBufHolder byteBufHolder) {
        String obj = channelHandlerContext.channel().toString();
        String obj2 = byteBufHolder.toString();
        ByteBuf content = byteBufHolder.content();
        int readableBytes = content.readableBytes();
        if (readableBytes == 0) {
            StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length() + 2 + obj2.length() + 4);
            sb.append(obj);
            sb.append(' ');
            sb.append(str);
            sb.append(", ");
            sb.append(obj2);
            sb.append(", 0B");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder(obj.length() + 1 + str.length() + 2 + obj2.length() + 2 + 10 + 1 + 2 + (((readableBytes / 16) + (readableBytes % 15 == 0 ? 0 : 1) + 4) * 80));
        sb2.append(obj);
        sb2.append(' ');
        sb2.append(str);
        sb2.append(": ");
        sb2.append(obj2);
        sb2.append(", ");
        sb2.append(readableBytes);
        sb2.append('B');
        sb2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb2, content);
        return sb2.toString();
    }

    private static String a(ChannelHandlerContext channelHandlerContext, String str, Object obj) {
        String obj2 = channelHandlerContext.channel().toString();
        String valueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(obj2.length() + 1 + str.length() + 2 + valueOf.length());
        sb.append(obj2);
        sb.append(' ');
        sb.append(str);
        sb.append(": ");
        sb.append(valueOf);
        return sb.toString();
    }
}
