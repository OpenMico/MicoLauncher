package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class aj implements ServiceConnection {
    final /* synthetic */ ay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aj(ay ayVar) {
        this.a = ayVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        List<Message> list;
        List list2;
        Messenger messenger;
        synchronized (this.a) {
            this.a.e = new Messenger(iBinder);
            this.a.j = false;
            list = this.a.i;
            for (Message message : list) {
                try {
                    messenger = this.a.e;
                    messenger.send(message);
                } catch (RemoteException e) {
                    b.a(e);
                }
            }
            list2 = this.a.i;
            list2.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.a.e = null;
        this.a.j = false;
    }
}
