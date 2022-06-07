package com.xiaomi.push;

import com.xiaomi.mipush.sdk.Constants;
import java.net.UnknownHostException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class gt {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class a {
        fc a;
        String b;

        a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a(Exception exc) {
        e(exc);
        boolean z = exc instanceof fy;
        Exception exc2 = exc;
        if (z) {
            fy fyVar = (fy) exc;
            exc2 = exc;
            if (fyVar.a() != null) {
                exc2 = fyVar.a();
            }
        }
        a aVar = new a();
        String message = exc2.getMessage();
        if (exc2.getCause() != null) {
            message = exc2.getCause().getMessage();
        }
        String str = exc2.getClass().getSimpleName() + Constants.COLON_SEPARATOR + message;
        int a2 = fp.a(exc2);
        if (a2 != 0) {
            aVar.a = fc.a(fc.GSLB_REQUEST_SUCCESS.a() + a2);
        }
        if (aVar.a == null) {
            aVar.a = fc.GSLB_TCP_ERR_OTHER;
        }
        if (aVar.a == fc.GSLB_TCP_ERR_OTHER) {
            aVar.b = str;
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a b(Exception exc) {
        fc fcVar;
        Throwable cause;
        e(exc);
        boolean z = exc instanceof fy;
        Exception exc2 = exc;
        if (z) {
            fy fyVar = (fy) exc;
            exc2 = exc;
            if (fyVar.a() != null) {
                exc2 = fyVar.a();
            }
        }
        a aVar = new a();
        String message = exc2.getMessage();
        if (exc2.getCause() != null) {
            message = exc2.getCause().getMessage();
        }
        int a2 = fp.a(exc2);
        String str = exc2.getClass().getSimpleName() + Constants.COLON_SEPARATOR + message;
        if (a2 != 0) {
            aVar.a = fc.a(fc.CONN_SUCCESS.a() + a2);
            if (aVar.a == fc.CONN_BOSH_ERR && (cause = exc2.getCause()) != null && (cause instanceof UnknownHostException)) {
                fcVar = fc.CONN_BOSH_UNKNOWNHOST;
            }
            if (aVar.a != fc.CONN_TCP_ERR_OTHER || aVar.a == fc.CONN_XMPP_ERR || aVar.a == fc.CONN_BOSH_ERR) {
                aVar.b = str;
            }
            return aVar;
        }
        fcVar = fc.CONN_XMPP_ERR;
        aVar.a = fcVar;
        if (aVar.a != fc.CONN_TCP_ERR_OTHER) {
        }
        aVar.b = str;
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a c(Exception exc) {
        fc fcVar;
        e(exc);
        boolean z = exc instanceof fy;
        Exception exc2 = exc;
        if (z) {
            fy fyVar = (fy) exc;
            exc2 = exc;
            if (fyVar.a() != null) {
                exc2 = fyVar.a();
            }
        }
        a aVar = new a();
        String message = exc2.getMessage();
        if (exc2.getCause() != null) {
            message = exc2.getCause().getMessage();
        }
        int a2 = fp.a(exc2);
        String str = exc2.getClass().getSimpleName() + Constants.COLON_SEPARATOR + message;
        if (a2 == 105) {
            fcVar = fc.BIND_TCP_READ_TIMEOUT;
        } else if (a2 == 199) {
            fcVar = fc.BIND_TCP_ERR;
        } else if (a2 != 499) {
            switch (a2) {
                case 109:
                    fcVar = fc.BIND_TCP_CONNRESET;
                    break;
                case 110:
                    fcVar = fc.BIND_TCP_BROKEN_PIPE;
                    break;
                default:
                    fcVar = fc.BIND_XMPP_ERR;
                    break;
            }
        } else {
            aVar.a = fc.BIND_BOSH_ERR;
            if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                fcVar = fc.BIND_BOSH_ITEM_NOT_FOUND;
            }
            if (aVar.a != fc.BIND_TCP_ERR || aVar.a == fc.BIND_XMPP_ERR || aVar.a == fc.BIND_BOSH_ERR) {
                aVar.b = str;
            }
            return aVar;
        }
        aVar.a = fcVar;
        if (aVar.a != fc.BIND_TCP_ERR) {
        }
        aVar.b = str;
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a d(Exception exc) {
        fc fcVar;
        e(exc);
        boolean z = exc instanceof fy;
        Exception exc2 = exc;
        if (z) {
            fy fyVar = (fy) exc;
            exc2 = exc;
            if (fyVar.a() != null) {
                exc2 = fyVar.a();
            }
        }
        a aVar = new a();
        String message = exc2.getMessage();
        int a2 = fp.a(exc2);
        String str = exc2.getClass().getSimpleName() + Constants.COLON_SEPARATOR + message;
        if (a2 == 105) {
            fcVar = fc.CHANNEL_TCP_READTIMEOUT;
        } else if (a2 == 199) {
            fcVar = fc.CHANNEL_TCP_ERR;
        } else if (a2 != 499) {
            switch (a2) {
                case 109:
                    fcVar = fc.CHANNEL_TCP_CONNRESET;
                    break;
                case 110:
                    fcVar = fc.CHANNEL_TCP_BROKEN_PIPE;
                    break;
                default:
                    fcVar = fc.CHANNEL_XMPPEXCEPTION;
                    break;
            }
        } else {
            aVar.a = fc.CHANNEL_BOSH_EXCEPTION;
            if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                fcVar = fc.CHANNEL_BOSH_ITEMNOTFIND;
            }
            if (aVar.a != fc.CHANNEL_TCP_ERR || aVar.a == fc.CHANNEL_XMPPEXCEPTION || aVar.a == fc.CHANNEL_BOSH_EXCEPTION) {
                aVar.b = str;
            }
            return aVar;
        }
        aVar.a = fcVar;
        if (aVar.a != fc.CHANNEL_TCP_ERR) {
        }
        aVar.b = str;
        return aVar;
    }

    private static void e(Exception exc) {
        if (exc == null) {
            throw new NullPointerException();
        }
    }
}
