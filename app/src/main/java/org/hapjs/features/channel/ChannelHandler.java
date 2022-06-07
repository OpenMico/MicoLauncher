package org.hapjs.features.channel;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.hapjs.sdk.platform.PlatformUtils;

/* loaded from: classes5.dex */
public abstract class ChannelHandler extends Handler {
    private Map<Message, Integer> a;
    private Set<Integer> b;
    private Context c;
    private int[] d;

    public abstract void onHandleMessage(Message message);

    public ChannelHandler(Context context, Looper looper, int[] iArr) {
        super(looper);
        this.c = context;
        if (Build.VERSION.SDK_INT < 21) {
            this.a = new ConcurrentHashMap();
        }
        this.b = new HashSet();
        this.d = iArr;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (!a(message)) {
            int b = b(message);
            if (b < 0) {
                Log.e("ChannelHandler", "Fail to get calling uid");
                return;
            } else if (!a(b)) {
                Log.e("ChannelHandler", "Received ungranted request");
                return;
            }
        }
        onHandleMessage(message);
    }

    private boolean a(Message message) {
        int[] iArr = this.d;
        if (iArr != null) {
            for (int i : iArr) {
                if (i == message.what) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.os.Handler
    public boolean sendMessageAtTime(Message message, long j) {
        if (Build.VERSION.SDK_INT < 21 && !a(message)) {
            this.a.put(message, Integer.valueOf(Binder.getCallingUid()));
        }
        return super.sendMessageAtTime(message, j);
    }

    private int b(Message message) {
        if (Build.VERSION.SDK_INT >= 21) {
            return message.sendingUid;
        }
        Integer remove = this.a.remove(message);
        if (remove != null) {
            return remove.intValue();
        }
        return -1;
    }

    private boolean a(int i) {
        if (this.b.contains(Integer.valueOf(i))) {
            return true;
        }
        String[] packagesForUid = this.c.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length != 1) {
            return false;
        }
        boolean isTrustedHost = PlatformUtils.isTrustedHost(this.c, packagesForUid[0], false);
        if (isTrustedHost) {
            this.b.add(Integer.valueOf(i));
        } else {
            Log.e("ChannelHandler", "not trusted host: " + packagesForUid[0]);
        }
        return isTrustedHost;
    }
}
