package org.hapjs.features.channel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miplay.mylibrary.DataModel;
import java.util.HashMap;
import java.util.Map;
import org.hapjs.features.channel.HapChannelManager;
import org.hapjs.features.channel.appinfo.AndroidApplication;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.EventListenerAdapter;
import org.hapjs.features.channel.transparentactivity.TransparentActivityManager;

/* loaded from: classes5.dex */
public class ChannelService extends Service {
    private Map<String, a> a = new HashMap();
    private HandlerThread b;
    private Handler c;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        TransparentActivityManager.getInstance().onServiceCreated();
        this.b = new HandlerThread("ChannelService");
        this.b.start();
        this.c = new ChannelHandler(this, this.b.getLooper(), new int[]{-1}) { // from class: org.hapjs.features.channel.ChannelService.1
            @Override // org.hapjs.features.channel.ChannelHandler
            public void onHandleMessage(Message message) {
                switch (message.what) {
                    case -1:
                        ChannelService.this.c(message);
                        return;
                    case 0:
                        ChannelService channelService = ChannelService.this;
                        channelService.a((Context) channelService, message);
                        return;
                    case 1:
                    default:
                        ChannelService.this.d(message);
                        return;
                    case 2:
                        ChannelService.this.a(message);
                        return;
                    case 3:
                        ChannelService.this.b(message);
                        return;
                }
            }
        };
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return new Messenger(this.c).getBinder();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        TransparentActivityManager.getInstance().onServiceDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, Message message) {
        Message message2;
        String string = message.getData().getString(IChannel.EXTRA_OPEN_ID_AT_CLIENT);
        String string2 = message.getData().getString(IChannel.EXTRA_OPEN_PKG_NAME);
        String string3 = message.getData().getString(IChannel.EXTRA_OPEN_SIGNATURE);
        final Messenger messenger = message.replyTo;
        int i = message.getData().getInt(IChannel.EXTRA_CLIENT_PID);
        String string4 = message.getData().getString(IChannel.EXTRA_CHANNEL_TYPE, "default");
        if (messenger == null) {
            Log.e("ChannelService", "Fail to handle open channel message, reply to is null.");
            return;
        }
        HapApplication hapApplication = new HapApplication(string2, string3);
        AndroidApplication androidApplication = new AndroidApplication(context, context.getPackageName(), new String[0]);
        a a2 = a(hapApplication, string4);
        try {
            if (a2.a) {
                final a aVar = new a(string, androidApplication, hapApplication, this.b, i == Process.myPid(), string4);
                message2 = createAckOpenMessage(a2, aVar.getIdAtServer());
                final IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: org.hapjs.features.channel.ChannelService.2
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        ChannelService.this.c.obtainMessage(-1, aVar.getIdAtServer()).sendToTarget();
                    }
                };
                messenger.getBinder().linkToDeath(deathRecipient, 0);
                aVar.addEventListener(new EventListenerAdapter() { // from class: org.hapjs.features.channel.ChannelService.3
                    @Override // org.hapjs.features.channel.listener.EventListenerAdapter, org.hapjs.features.channel.listener.ChannelEventListener
                    public void onClose(IChannel iChannel, int i2, String str) {
                        aVar.removeEventListener(this);
                        messenger.getBinder().unlinkToDeath(deathRecipient, 0);
                    }
                });
                this.a.put(aVar.getIdAtServer(), aVar);
                aVar.a(message);
            } else {
                message2 = createAckOpenMessage(a2, DataModel.CIRCULATEFAIL_NO_SUPPORT);
            }
            messenger.send(message2);
        } catch (RemoteException e) {
            Log.e("ChannelService", "Fail to ack open.", e);
        }
    }

    private a a(HapApplication hapApplication, String str) {
        a aVar = new a();
        if (!HapChannelManager.get().isInitialized()) {
            aVar.a = false;
            aVar.b = "Native app is not ready.";
        } else {
            HapChannelManager.ChannelHandler channelHandler = HapChannelManager.get().getChannelHandler(str);
            if (channelHandler == null || !channelHandler.accept(hapApplication)) {
                aVar.a = false;
                aVar.b = "Open request refused.";
            } else {
                aVar.a = true;
                aVar.b = "ok";
            }
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        String string = message.getData().getString(IChannel.EXTRA_ID_AT_RECEIVER);
        a aVar = this.a.get(string);
        if (aVar != null) {
            aVar.handleReceiveMessage(message);
            Log.v("ChannelService", aVar + " receive msg from hap app.");
            return;
        }
        Log.e("ChannelService", "Fail to handle receive message, channel " + string + " not found");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Message message) {
        String string = message.getData().getString(IChannel.EXTRA_ID_AT_RECEIVER);
        a remove = this.a.remove(string);
        if (remove != null) {
            remove.handleCloseMessage(message);
            Log.v("ChannelService", remove + " closed by hap app.");
            return;
        }
        Log.e("ChannelService", "Fail to handle close, channel " + string + " not found");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Message message) {
        String str = (String) message.obj;
        a remove = this.a.remove(str);
        if (remove != null) {
            remove.a();
            Log.v("ChannelService", remove + "'s hap app died.");
            return;
        }
        Log.e("ChannelService", "Fail to remote app death, channel " + str + " not found");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Message message) {
        String str = "Unknown msg type:" + message.what;
        if (message.replyTo != null) {
            Message obtain = Message.obtain();
            obtain.what = -1;
            obtain.getData().putString(IChannel.EXTRA_ERROR_DESC, str);
            try {
                message.replyTo.send(obtain);
            } catch (RemoteException e) {
                Log.e("ChannelService", "Fail to handle unknown msg type.", e);
            }
        }
        Log.e("ChannelService", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        boolean a;
        String b;

        private a() {
        }
    }

    public static Message createAckOpenMessage(a aVar, String str) {
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.getData().putBoolean("result", aVar.a);
        obtain.getData().putString("message", aVar.b);
        obtain.getData().putString(IChannel.EXTRA_ACK_OPEN_ID_AT_SERVER, str);
        return obtain;
    }
}
