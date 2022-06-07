package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class MessengerUtils {
    private static ConcurrentHashMap<String, MessageCallback> a = new ConcurrentHashMap<>();
    private static Map<String, a> b = new HashMap();
    private static a c;

    /* loaded from: classes.dex */
    public interface MessageCallback {
        void messageCall(Bundle bundle);
    }

    public static void register() {
        if (b.n()) {
            if (b.n(ServerService.class.getName())) {
                Log.i("MessengerUtils", "Server service is running.");
            } else {
                a(new Intent(Utils.getApp(), ServerService.class));
            }
        } else if (c == null) {
            a aVar = new a(null);
            if (aVar.a()) {
                c = aVar;
            } else {
                Log.e("MessengerUtils", "Bind service failed.");
            }
        } else {
            Log.i("MessengerUtils", "The client have been bind.");
        }
    }

    public static void unregister() {
        if (b.n()) {
            if (!b.n(ServerService.class.getName())) {
                Log.i("MessengerUtils", "Server service isn't running.");
                return;
            } else {
                Utils.getApp().stopService(new Intent(Utils.getApp(), ServerService.class));
            }
        }
        a aVar = c;
        if (aVar != null) {
            aVar.b();
        }
    }

    public static void register(String str) {
        if (b.containsKey(str)) {
            Log.i("MessengerUtils", "register: client registered: " + str);
            return;
        }
        a aVar = new a(str);
        if (aVar.a()) {
            b.put(str, aVar);
            return;
        }
        Log.e("MessengerUtils", "register: client bind failed: " + str);
    }

    public static void unregister(String str) {
        if (!b.containsKey(str)) {
            Log.i("MessengerUtils", "unregister: client didn't register: " + str);
            return;
        }
        a aVar = b.get(str);
        b.remove(str);
        if (aVar != null) {
            aVar.b();
        }
    }

    public static void subscribe(@NonNull String str, @NonNull MessageCallback messageCallback) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (messageCallback != null) {
            a.put(str, messageCallback);
        } else {
            throw new NullPointerException("Argument 'callback' of type MessageCallback (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void unsubscribe(@NonNull String str) {
        if (str != null) {
            a.remove(str);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void post(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (bundle != null) {
            bundle.putString("MESSENGER_UTILS", str);
            a aVar = c;
            if (aVar != null) {
                aVar.a(bundle);
            } else {
                Intent intent = new Intent(Utils.getApp(), ServerService.class);
                intent.putExtras(bundle);
                a(intent);
            }
            for (a aVar2 : b.values()) {
                aVar2.a(bundle);
            }
        } else {
            throw new NullPointerException("Argument 'data' of type Bundle (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    private static void a(Intent intent) {
        try {
            intent.setFlags(32);
            if (Build.VERSION.SDK_INT >= 26) {
                Utils.getApp().startForegroundService(intent);
            } else {
                Utils.getApp().startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes.dex */
    static class a {
        String a;
        Messenger b;
        LinkedList<Bundle> c = new LinkedList<>();
        @SuppressLint({"HandlerLeak"})
        Handler d = new Handler() { // from class: com.blankj.utilcode.util.MessengerUtils.a.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MessageCallback messageCallback;
                Bundle data = message.getData();
                data.setClassLoader(MessengerUtils.class.getClassLoader());
                String string = data.getString("MESSENGER_UTILS");
                if (string != null && (messageCallback = (MessageCallback) MessengerUtils.a.get(string)) != null) {
                    messageCallback.messageCall(data);
                }
            }
        };
        Messenger e = new Messenger(this.d);
        ServiceConnection f = new ServiceConnection() { // from class: com.blankj.utilcode.util.MessengerUtils.a.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("MessengerUtils", "client service connected " + componentName);
                a.this.b = new Messenger(iBinder);
                Message obtain = Message.obtain(a.this.d, 0, b.p().hashCode(), 0);
                obtain.getData().setClassLoader(MessengerUtils.class.getClassLoader());
                obtain.replyTo = a.this.e;
                try {
                    a.this.b.send(obtain);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                a.this.c();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.w("MessengerUtils", "client service disconnected:" + componentName);
                a aVar = a.this;
                aVar.b = null;
                if (!aVar.a()) {
                    Log.e("MessengerUtils", "client service rebind failed: " + componentName);
                }
            }
        };

        a(String str) {
            this.a = str;
        }

        boolean a() {
            if (TextUtils.isEmpty(this.a)) {
                return Utils.getApp().bindService(new Intent(Utils.getApp(), ServerService.class), this.f, 1);
            } else if (!b.c(this.a)) {
                Log.e("MessengerUtils", "bind: the app is not installed -> " + this.a);
                return false;
            } else if (b.b(this.a)) {
                Intent intent = new Intent(this.a + ".messenger");
                intent.setPackage(this.a);
                return Utils.getApp().bindService(intent, this.f, 1);
            } else {
                Log.e("MessengerUtils", "bind: the app is not running -> " + this.a);
                return false;
            }
        }

        void b() {
            Message obtain = Message.obtain(this.d, 1, b.p().hashCode(), 0);
            obtain.replyTo = this.e;
            try {
                this.b.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                Utils.getApp().unbindService(this.f);
            } catch (Exception unused) {
            }
        }

        void a(Bundle bundle) {
            if (this.b == null) {
                this.c.addFirst(bundle);
                Log.i("MessengerUtils", "save the bundle " + bundle);
                return;
            }
            c();
            if (!b(bundle)) {
                this.c.addFirst(bundle);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            if (!this.c.isEmpty()) {
                for (int size = this.c.size() - 1; size >= 0; size--) {
                    if (b(this.c.get(size))) {
                        this.c.remove(size);
                    }
                }
            }
        }

        private boolean b(Bundle bundle) {
            Message obtain = Message.obtain(this.d, 2);
            bundle.setClassLoader(MessengerUtils.class.getClassLoader());
            obtain.setData(bundle);
            obtain.replyTo = this.e;
            try {
                this.b.send(obtain);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ServerService extends Service {
        private final ConcurrentHashMap<Integer, Messenger> a = new ConcurrentHashMap<>();
        @SuppressLint({"HandlerLeak"})
        private final Handler b = new Handler() { // from class: com.blankj.utilcode.util.MessengerUtils.ServerService.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        ServerService.this.a.put(Integer.valueOf(message.arg1), message.replyTo);
                        return;
                    case 1:
                        ServerService.this.a.remove(Integer.valueOf(message.arg1));
                        return;
                    case 2:
                        ServerService.this.a(message);
                        ServerService.this.b(message);
                        return;
                    default:
                        super.handleMessage(message);
                        return;
                }
            }
        };
        private final Messenger c = new Messenger(this.b);

        @Override // android.app.Service
        @Nullable
        public IBinder onBind(Intent intent) {
            return this.c.getBinder();
        }

        @Override // android.app.Service
        public int onStartCommand(Intent intent, int i, int i2) {
            Bundle extras;
            if (Build.VERSION.SDK_INT >= 26) {
                startForeground(1, b.a(NotificationUtils.ChannelConfig.DEFAULT_CHANNEL_CONFIG, (Utils.Consumer<NotificationCompat.Builder>) null));
            }
            if (!(intent == null || (extras = intent.getExtras()) == null)) {
                Message obtain = Message.obtain(this.b, 2);
                obtain.replyTo = this.c;
                obtain.setData(extras);
                a(obtain);
                b(obtain);
            }
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Message message) {
            for (Messenger messenger : this.a.values()) {
                if (messenger != null) {
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Message message) {
            String string;
            MessageCallback messageCallback;
            Bundle data = message.getData();
            if (data != null && (string = data.getString("MESSENGER_UTILS")) != null && (messageCallback = (MessageCallback) MessengerUtils.a.get(string)) != null) {
                messageCallback.messageCall(data);
            }
        }
    }
}
