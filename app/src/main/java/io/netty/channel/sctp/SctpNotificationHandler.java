package io.netty.channel.sctp;

import com.sun.nio.sctp.AbstractNotificationHandler;
import com.sun.nio.sctp.AssociationChangeNotification;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.PeerAddressChangeNotification;
import com.sun.nio.sctp.SendFailedNotification;
import com.sun.nio.sctp.ShutdownNotification;

/* loaded from: classes4.dex */
public final class SctpNotificationHandler extends AbstractNotificationHandler<Object> {
    private final SctpChannel a;

    public SctpNotificationHandler(SctpChannel sctpChannel) {
        if (sctpChannel != null) {
            this.a = sctpChannel;
            return;
        }
        throw new NullPointerException("sctpChannel");
    }

    public HandlerResult handleNotification(AssociationChangeNotification associationChangeNotification, Object obj) {
        a(associationChangeNotification);
        return HandlerResult.CONTINUE;
    }

    public HandlerResult handleNotification(PeerAddressChangeNotification peerAddressChangeNotification, Object obj) {
        a(peerAddressChangeNotification);
        return HandlerResult.CONTINUE;
    }

    public HandlerResult handleNotification(SendFailedNotification sendFailedNotification, Object obj) {
        a(sendFailedNotification);
        return HandlerResult.CONTINUE;
    }

    public HandlerResult handleNotification(ShutdownNotification shutdownNotification, Object obj) {
        a(shutdownNotification);
        this.a.close();
        return HandlerResult.RETURN;
    }

    private void a(Notification notification) {
        this.a.pipeline().fireUserEventTriggered((Object) notification);
    }
}
