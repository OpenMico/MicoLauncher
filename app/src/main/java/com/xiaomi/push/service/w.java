package com.xiaomi.push.service;

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
public class w implements ServiceConnection {
    final /* synthetic */ ax a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(ax axVar) {
        this.a = axVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        List<Message> list;
        List list2;
        Messenger messenger;
        synchronized (this.a) {
            this.a.h = new Messenger(iBinder);
            this.a.g = false;
            list = this.a.f;
            for (Message message : list) {
                try {
                    messenger = this.a.h;
                    messenger.send(message);
                } catch (RemoteException e) {
                    b.a(e);
                }
            }
            list2 = this.a.f;
            list2.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.a.h = null;
        this.a.g = false;
    }
}
