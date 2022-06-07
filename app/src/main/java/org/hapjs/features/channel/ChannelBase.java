package org.hapjs.features.channel;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.hapjs.features.channel.appinfo.AndroidApplication;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.ChannelEventListener;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes5.dex */
public abstract class ChannelBase implements IChannel {
    private static final AtomicLong a = new AtomicLong(0);
    private final AndroidApplication b;
    private final HapApplication c;
    private HandlerThread d;
    private Handler e;
    private int f;
    private int g;
    private String h;
    private Messenger i;
    private ConcurrentHashMap<ChannelEventListener, String> j;
    private String k;
    private String l;
    protected String mType;

    protected abstract void doOpen(Message message);

    protected abstract String getIdAtReceiver();

    protected abstract boolean isSameProcess();

    /* loaded from: classes5.dex */
    private static class c {
        static HandlerThread a = new HandlerThread("ChannelBase");

        static {
            a.start();
        }
    }

    protected static String nextChannelId() {
        return String.valueOf(a.incrementAndGet());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ChannelBase(AndroidApplication androidApplication, HapApplication hapApplication, HandlerThread handlerThread, String str) {
        this.b = androidApplication;
        this.c = hapApplication;
        if (TextUtils.isEmpty(str)) {
            this.mType = "default";
        } else {
            this.mType = str;
        }
        doSetStatus(0);
        this.j = new ConcurrentHashMap<>();
        if (handlerThread != null) {
            this.d = handlerThread;
        } else {
            this.d = c.a;
        }
        this.e = new Handler(this.d.getLooper()) { // from class: org.hapjs.features.channel.ChannelBase.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        ChannelBase.this.doOpen(message);
                        return;
                    case 1:
                        d dVar = (d) message.obj;
                        EventCallBack eventCallBack = dVar.b;
                        if (ChannelBase.this.a(dVar.a)) {
                            if (eventCallBack != null) {
                                eventCallBack.onSuccess();
                                return;
                            }
                            return;
                        } else if (eventCallBack != null) {
                            eventCallBack.onFail();
                            return;
                        } else {
                            return;
                        }
                    case 2:
                        a aVar = (a) message.obj;
                        EventCallBack eventCallBack2 = aVar.d;
                        if (ChannelBase.this.doClose(aVar.a, aVar.c, aVar.b)) {
                            if (eventCallBack2 != null) {
                                eventCallBack2.onSuccess();
                                return;
                            }
                            return;
                        } else if (eventCallBack2 != null) {
                            eventCallBack2.onFail();
                            return;
                        } else {
                            return;
                        }
                    case 3:
                        ChannelBase.this.a((Bundle) message.obj);
                        return;
                    case 4:
                        ChannelBase.this.a((String) message.obj);
                        return;
                    case 5:
                        b bVar = (b) message.obj;
                        ChannelBase.this.doNotifyError(bVar.a, bVar.b);
                        return;
                    default:
                        return;
                }
            }
        };
    }

    protected boolean open(Object obj) {
        this.e.obtainMessage(0, obj).sendToTarget();
        return true;
    }

    @Override // org.hapjs.features.channel.IChannel
    public void send(ChannelMessage channelMessage, EventCallBack eventCallBack) {
        d dVar = new d();
        dVar.a = channelMessage;
        dVar.b = eventCallBack;
        this.e.obtainMessage(1, dVar).sendToTarget();
    }

    @Override // org.hapjs.features.channel.IChannel
    public void close(int i, String str, boolean z) {
        close(i, str, z, null);
    }

    public void close(int i, String str, boolean z, EventCallBack eventCallBack) {
        a aVar = new a();
        aVar.a = i;
        aVar.c = str;
        aVar.b = z;
        aVar.d = eventCallBack;
        this.e.obtainMessage(2, aVar).sendToTarget();
    }

    protected void handleReceiveMessage(Message message) {
        this.e.obtainMessage(3, message.getData().getBundle("content")).sendToTarget();
    }

    protected void handleCloseMessage(Message message) {
        this.e.obtainMessage(4, message.getData().getString(IChannel.EXTRA_CLOSE_REASON)).sendToTarget();
    }

    protected void notifyError(int i, String str) {
        b bVar = new b();
        bVar.a = i;
        bVar.b = str;
        this.e.obtainMessage(5, bVar).sendToTarget();
    }

    @Override // org.hapjs.features.channel.IChannel
    public boolean addEventListener(ChannelEventListener channelEventListener) {
        return channelEventListener != null && this.j.putIfAbsent(channelEventListener, "") == null;
    }

    @Override // org.hapjs.features.channel.IChannel
    public boolean removeEventListener(ChannelEventListener channelEventListener) {
        return this.j.remove(channelEventListener) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(ChannelMessage channelMessage) {
        if (this.f != 2) {
            doNotifyError(2, "Fail to send message, invalid status:" + this.f);
            return false;
        }
        int dataSize = channelMessage.dataSize();
        if (dataSize > 524288) {
            doNotifyError(5, "Data size must less than 524288 but " + dataSize);
            return false;
        }
        int size = channelMessage.streams != null ? channelMessage.streams.size() : 0;
        if (size > 64) {
            doNotifyError(5, "File count must less than 64 but " + size);
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putBundle("content", channelMessage.toBundle());
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.setData(bundle);
        return a(obtain);
    }

    protected boolean doClose(int i, String str, boolean z) {
        int i2 = this.f;
        if (i2 == 2 || i2 == 1) {
            if (z) {
                Bundle bundle = new Bundle();
                bundle.putString(IChannel.EXTRA_CLOSE_REASON, str);
                Message obtain = Message.obtain();
                obtain.what = 3;
                obtain.setData(bundle);
                a(obtain);
            }
            this.i = null;
            this.g = i;
            this.h = str;
            doSetStatus(3);
            Log.v("ChannelBase", "Channel closed, code:" + i + ", reason:" + str);
            return true;
        }
        doNotifyError(2, "Fail to close channel, invalid status " + this.f);
        return false;
    }

    private boolean a(Message message) {
        try {
            if (this.i == null) {
                if (!isSameProcess()) {
                    message.recycle();
                }
                doNotifyError(6, "Fail to send message, messenger is null.");
                return false;
            }
            try {
                message.getData().putString(IChannel.EXTRA_ID_AT_RECEIVER, getIdAtReceiver());
                this.i.send(message);
                if (!isSameProcess()) {
                    message.recycle();
                }
                return true;
            } catch (RemoteException e) {
                doNotifyError(4, "Remote app died.");
                Log.e("ChannelBase", "Remote app died.", e);
                if (!isSameProcess()) {
                    message.recycle();
                }
                return false;
            }
        } catch (Throwable th) {
            if (!isSameProcess()) {
                message.recycle();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        b(ChannelMessage.parse(bundle));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        doClose(1, str, false);
    }

    private void a() {
        for (ChannelEventListener channelEventListener : new HashSet(this.j.keySet())) {
            if (channelEventListener != null) {
                channelEventListener.onOpen(this);
            }
        }
    }

    private void b(ChannelMessage channelMessage) {
        for (ChannelEventListener channelEventListener : new HashSet(this.j.keySet())) {
            if (channelEventListener != null) {
                channelEventListener.onReceiveMessage(this, channelMessage);
            }
        }
    }

    private void a(int i, String str) {
        for (ChannelEventListener channelEventListener : new HashSet(this.j.keySet())) {
            if (channelEventListener != null) {
                channelEventListener.onClose(this, i, str);
            }
        }
    }

    protected void doNotifyError(int i, String str) {
        for (ChannelEventListener channelEventListener : new HashSet(this.j.keySet())) {
            if (channelEventListener != null) {
                channelEventListener.onError(this, i, str);
            }
        }
    }

    protected void doSetMessenger(Messenger messenger) {
        this.i = messenger;
    }

    protected void doSetStatus(int i) {
        int i2 = this.f;
        this.f = i;
        if (i2 == 1 && i == 2) {
            a();
        }
        if (i2 == 2 && i == 3) {
            a(this.g, this.h);
        }
    }

    @Override // org.hapjs.features.channel.IChannel
    public int getStatus() {
        return this.f;
    }

    protected void doSetIdAtServer(String str) {
        this.l = str;
    }

    protected String getIdAtServer() {
        return this.l;
    }

    protected void doSetIdAtClient(String str) {
        this.k = str;
    }

    protected String getIdAtClient() {
        return this.k;
    }

    @Override // org.hapjs.features.channel.IChannel
    public AndroidApplication getAndroidApplication() {
        return this.b;
    }

    @Override // org.hapjs.features.channel.IChannel
    public HapApplication getHapApplication() {
        return this.c;
    }

    protected HandlerThread getHandlerThread() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        int a;
        boolean b;
        String c;
        EventCallBack d;

        private a() {
        }
    }

    /* loaded from: classes5.dex */
    private static class b {
        int a;
        String b;

        private b() {
        }
    }

    /* loaded from: classes5.dex */
    private static class d {
        ChannelMessage a;
        EventCallBack b;

        private d() {
        }
    }

    @Override // org.hapjs.features.channel.IChannel
    public String getType() {
        return this.mType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Channel[type=" + getClass().getSimpleName());
        AndroidApplication androidApplication = this.b;
        if (androidApplication != null && !TextUtils.isEmpty(androidApplication.mPkgName)) {
            sb.append(", androidPkgName=" + this.b.mPkgName);
        }
        HapApplication hapApplication = this.c;
        if (hapApplication != null && !TextUtils.isEmpty(hapApplication.mPkgName)) {
            sb.append(", hapPkgName=" + this.c.mPkgName);
        }
        sb.append(", serverId=" + this.l);
        sb.append(", clientId=" + this.k + "]");
        return sb.toString();
    }
}
