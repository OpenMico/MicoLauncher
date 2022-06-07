package androidx.core.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    @GuardedBy("sEnabledNotificationListenersLock")
    private static String b;
    @GuardedBy("sLock")
    private static d g;
    private final Context d;
    private final NotificationManager e;
    private static final Object a = new Object();
    @GuardedBy("sEnabledNotificationListenersLock")
    private static Set<String> c = new HashSet();
    private static final Object f = new Object();

    /* loaded from: classes.dex */
    public interface e {
        void a(INotificationSideChannel iNotificationSideChannel) throws RemoteException;
    }

    @NonNull
    public static NotificationManagerCompat from(@NonNull Context context) {
        return new NotificationManagerCompat(context);
    }

    private NotificationManagerCompat(Context context) {
        this.d = context;
        this.e = (NotificationManager) this.d.getSystemService("notification");
    }

    public void cancel(int i) {
        cancel(null, i);
    }

    public void cancel(@Nullable String str, int i) {
        this.e.cancel(str, i);
        if (Build.VERSION.SDK_INT <= 19) {
            a(new a(this.d.getPackageName(), i, str));
        }
    }

    public void cancelAll() {
        this.e.cancelAll();
        if (Build.VERSION.SDK_INT <= 19) {
            a(new a(this.d.getPackageName()));
        }
    }

    public void notify(int i, @NonNull Notification notification) {
        notify(null, i, notification);
    }

    public void notify(@Nullable String str, int i, @NonNull Notification notification) {
        if (a(notification)) {
            a(new b(this.d.getPackageName(), i, str, notification));
            this.e.cancel(str, i);
            return;
        }
        this.e.notify(str, i, notification);
    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.e.areNotificationsEnabled();
        }
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.d.getSystemService("appops");
        ApplicationInfo applicationInfo = this.d.getApplicationInfo();
        String packageName = this.d.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            return ((Integer) cls.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName)).intValue() == 0;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
            return true;
        }
    }

    public int getImportance() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.e.getImportance();
        }
        return -1000;
    }

    public void createNotificationChannel(@NonNull NotificationChannel notificationChannel) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.createNotificationChannel(notificationChannel);
        }
    }

    public void createNotificationChannel(@NonNull NotificationChannelCompat notificationChannelCompat) {
        createNotificationChannel(notificationChannelCompat.a());
    }

    public void createNotificationChannelGroup(@NonNull NotificationChannelGroup notificationChannelGroup) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.createNotificationChannelGroup(notificationChannelGroup);
        }
    }

    public void createNotificationChannelGroup(@NonNull NotificationChannelGroupCompat notificationChannelGroupCompat) {
        createNotificationChannelGroup(notificationChannelGroupCompat.a());
    }

    public void createNotificationChannels(@NonNull List<NotificationChannel> list) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.createNotificationChannels(list);
        }
    }

    public void createNotificationChannelsCompat(@NonNull List<NotificationChannelCompat> list) {
        if (Build.VERSION.SDK_INT >= 26 && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList(list.size());
            for (NotificationChannelCompat notificationChannelCompat : list) {
                arrayList.add(notificationChannelCompat.a());
            }
            this.e.createNotificationChannels(arrayList);
        }
    }

    public void createNotificationChannelGroups(@NonNull List<NotificationChannelGroup> list) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.createNotificationChannelGroups(list);
        }
    }

    public void createNotificationChannelGroupsCompat(@NonNull List<NotificationChannelGroupCompat> list) {
        if (Build.VERSION.SDK_INT >= 26 && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList(list.size());
            for (NotificationChannelGroupCompat notificationChannelGroupCompat : list) {
                arrayList.add(notificationChannelGroupCompat.a());
            }
            this.e.createNotificationChannelGroups(arrayList);
        }
    }

    public void deleteNotificationChannel(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.deleteNotificationChannel(str);
        }
    }

    public void deleteNotificationChannelGroup(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.e.deleteNotificationChannelGroup(str);
        }
    }

    public void deleteUnlistedNotificationChannels(@NonNull Collection<String> collection) {
        if (Build.VERSION.SDK_INT >= 26) {
            for (NotificationChannel notificationChannel : this.e.getNotificationChannels()) {
                if (!collection.contains(notificationChannel.getId()) && (Build.VERSION.SDK_INT < 30 || !collection.contains(notificationChannel.getParentChannelId()))) {
                    this.e.deleteNotificationChannel(notificationChannel.getId());
                }
            }
        }
    }

    @Nullable
    public NotificationChannel getNotificationChannel(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.e.getNotificationChannel(str);
        }
        return null;
    }

    @Nullable
    public NotificationChannelCompat getNotificationChannelCompat(@NonNull String str) {
        NotificationChannel notificationChannel;
        if (Build.VERSION.SDK_INT < 26 || (notificationChannel = getNotificationChannel(str)) == null) {
            return null;
        }
        return new NotificationChannelCompat(notificationChannel);
    }

    @Nullable
    public NotificationChannel getNotificationChannel(@NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 30) {
            return this.e.getNotificationChannel(str, str2);
        }
        return getNotificationChannel(str);
    }

    @Nullable
    public NotificationChannelCompat getNotificationChannelCompat(@NonNull String str, @NonNull String str2) {
        NotificationChannel notificationChannel;
        if (Build.VERSION.SDK_INT < 26 || (notificationChannel = getNotificationChannel(str, str2)) == null) {
            return null;
        }
        return new NotificationChannelCompat(notificationChannel);
    }

    @Nullable
    public NotificationChannelGroup getNotificationChannelGroup(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.e.getNotificationChannelGroup(str);
        }
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        for (NotificationChannelGroup notificationChannelGroup : getNotificationChannelGroups()) {
            if (notificationChannelGroup.getId().equals(str)) {
                return notificationChannelGroup;
            }
        }
        return null;
    }

    @Nullable
    public NotificationChannelGroupCompat getNotificationChannelGroupCompat(@NonNull String str) {
        NotificationChannelGroup notificationChannelGroup;
        if (Build.VERSION.SDK_INT >= 28) {
            NotificationChannelGroup notificationChannelGroup2 = getNotificationChannelGroup(str);
            if (notificationChannelGroup2 != null) {
                return new NotificationChannelGroupCompat(notificationChannelGroup2);
            }
            return null;
        } else if (Build.VERSION.SDK_INT < 26 || (notificationChannelGroup = getNotificationChannelGroup(str)) == null) {
            return null;
        } else {
            return new NotificationChannelGroupCompat(notificationChannelGroup, getNotificationChannels());
        }
    }

    @NonNull
    public List<NotificationChannel> getNotificationChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.e.getNotificationChannels();
        }
        return Collections.emptyList();
    }

    @NonNull
    public List<NotificationChannelCompat> getNotificationChannelsCompat() {
        if (Build.VERSION.SDK_INT >= 26) {
            List<NotificationChannel> notificationChannels = getNotificationChannels();
            if (!notificationChannels.isEmpty()) {
                ArrayList arrayList = new ArrayList(notificationChannels.size());
                for (NotificationChannel notificationChannel : notificationChannels) {
                    arrayList.add(new NotificationChannelCompat(notificationChannel));
                }
                return arrayList;
            }
        }
        return Collections.emptyList();
    }

    @NonNull
    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.e.getNotificationChannelGroups();
        }
        return Collections.emptyList();
    }

    @NonNull
    public List<NotificationChannelGroupCompat> getNotificationChannelGroupsCompat() {
        List<NotificationChannel> list;
        if (Build.VERSION.SDK_INT >= 26) {
            List<NotificationChannelGroup> notificationChannelGroups = getNotificationChannelGroups();
            if (!notificationChannelGroups.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 28) {
                    list = Collections.emptyList();
                } else {
                    list = getNotificationChannels();
                }
                ArrayList arrayList = new ArrayList(notificationChannelGroups.size());
                for (NotificationChannelGroup notificationChannelGroup : notificationChannelGroups) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        arrayList.add(new NotificationChannelGroupCompat(notificationChannelGroup));
                    } else {
                        arrayList.add(new NotificationChannelGroupCompat(notificationChannelGroup, list));
                    }
                }
                return arrayList;
            }
        }
        return Collections.emptyList();
    }

    @NonNull
    public static Set<String> getEnabledListenerPackages(@NonNull Context context) {
        Set<String> set;
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        synchronized (a) {
            if (string != null) {
                if (!string.equals(b)) {
                    String[] split = string.split(Constants.COLON_SEPARATOR, -1);
                    HashSet hashSet = new HashSet(split.length);
                    for (String str : split) {
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                        if (unflattenFromString != null) {
                            hashSet.add(unflattenFromString.getPackageName());
                        }
                    }
                    c = hashSet;
                    b = string;
                }
            }
            set = c;
        }
        return set;
    }

    private static boolean a(Notification notification) {
        Bundle extras = NotificationCompat.getExtras(notification);
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void a(e eVar) {
        synchronized (f) {
            if (g == null) {
                g = new d(this.d.getApplicationContext());
            }
            g.a(eVar);
        }
    }

    /* loaded from: classes.dex */
    public static class d implements ServiceConnection, Handler.Callback {
        private final Context a;
        private final Map<ComponentName, a> d = new HashMap();
        private Set<String> e = new HashSet();
        private final HandlerThread b = new HandlerThread("NotificationManagerCompat");
        private final Handler c = new Handler(this.b.getLooper(), this);

        d(Context context) {
            this.a = context;
            this.b.start();
        }

        public void a(e eVar) {
            this.c.obtainMessage(0, eVar).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    b((e) message.obj);
                    return true;
                case 1:
                    c cVar = (c) message.obj;
                    a(cVar.a, cVar.b);
                    return true;
                case 2:
                    a((ComponentName) message.obj);
                    return true;
                case 3:
                    b((ComponentName) message.obj);
                    return true;
                default:
                    return false;
            }
        }

        private void b(e eVar) {
            a();
            for (a aVar : this.d.values()) {
                aVar.d.add(eVar);
                d(aVar);
            }
        }

        private void a(ComponentName componentName, IBinder iBinder) {
            a aVar = this.d.get(componentName);
            if (aVar != null) {
                aVar.c = INotificationSideChannel.Stub.asInterface(iBinder);
                aVar.e = 0;
                d(aVar);
            }
        }

        private void a(ComponentName componentName) {
            a aVar = this.d.get(componentName);
            if (aVar != null) {
                b(aVar);
            }
        }

        private void b(ComponentName componentName) {
            a aVar = this.d.get(componentName);
            if (aVar != null) {
                d(aVar);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Connected to service " + componentName);
            }
            this.c.obtainMessage(1, new c(componentName, iBinder)).sendToTarget();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Disconnected from service " + componentName);
            }
            this.c.obtainMessage(2, componentName).sendToTarget();
        }

        private void a() {
            Set<String> enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.a);
            if (!enabledListenerPackages.equals(this.e)) {
                this.e = enabledListenerPackages;
                List<ResolveInfo> queryIntentServices = this.a.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
                HashSet<ComponentName> hashSet = new HashSet();
                for (ResolveInfo resolveInfo : queryIntentServices) {
                    if (enabledListenerPackages.contains(resolveInfo.serviceInfo.packageName)) {
                        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission != null) {
                            Log.w("NotifManCompat", "Permission present on component " + componentName + ", not adding listener record.");
                        } else {
                            hashSet.add(componentName);
                        }
                    }
                }
                for (ComponentName componentName2 : hashSet) {
                    if (!this.d.containsKey(componentName2)) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Adding listener record for " + componentName2);
                        }
                        this.d.put(componentName2, new a(componentName2));
                    }
                }
                Iterator<Map.Entry<ComponentName, a>> it = this.d.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<ComponentName, a> next = it.next();
                    if (!hashSet.contains(next.getKey())) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Removing listener record for " + next.getKey());
                        }
                        b(next.getValue());
                        it.remove();
                    }
                }
            }
        }

        private boolean a(a aVar) {
            if (aVar.b) {
                return true;
            }
            aVar.b = this.a.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(aVar.a), this, 33);
            if (aVar.b) {
                aVar.e = 0;
            } else {
                Log.w("NotifManCompat", "Unable to bind to listener " + aVar.a);
                this.a.unbindService(this);
            }
            return aVar.b;
        }

        private void b(a aVar) {
            if (aVar.b) {
                this.a.unbindService(this);
                aVar.b = false;
            }
            aVar.c = null;
        }

        private void c(a aVar) {
            if (!this.c.hasMessages(3, aVar.a)) {
                aVar.e++;
                if (aVar.e > 6) {
                    Log.w("NotifManCompat", "Giving up on delivering " + aVar.d.size() + " tasks to " + aVar.a + " after " + aVar.e + " retries");
                    aVar.d.clear();
                    return;
                }
                int i = (1 << (aVar.e - 1)) * 1000;
                if (Log.isLoggable("NotifManCompat", 3)) {
                    Log.d("NotifManCompat", "Scheduling retry for " + i + " ms");
                }
                this.c.sendMessageDelayed(this.c.obtainMessage(3, aVar.a), i);
            }
        }

        private void d(a aVar) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Processing component " + aVar.a + ", " + aVar.d.size() + " queued tasks");
            }
            if (!aVar.d.isEmpty()) {
                if (!a(aVar) || aVar.c == null) {
                    c(aVar);
                    return;
                }
                while (true) {
                    e peek = aVar.d.peek();
                    if (peek == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Sending task " + peek);
                        }
                        peek.a(aVar.c);
                        aVar.d.remove();
                    } catch (DeadObjectException unused) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Remote service has died: " + aVar.a);
                        }
                    } catch (RemoteException e) {
                        Log.w("NotifManCompat", "RemoteException communicating with " + aVar.a, e);
                    }
                }
                if (!aVar.d.isEmpty()) {
                    c(aVar);
                }
            }
        }

        /* loaded from: classes.dex */
        public static class a {
            final ComponentName a;
            INotificationSideChannel c;
            boolean b = false;
            ArrayDeque<e> d = new ArrayDeque<>();
            int e = 0;

            a(ComponentName componentName) {
                this.a = componentName;
            }
        }
    }

    /* loaded from: classes.dex */
    private static class c {
        final ComponentName a;
        final IBinder b;

        c(ComponentName componentName, IBinder iBinder) {
            this.a = componentName;
            this.b = iBinder;
        }
    }

    /* loaded from: classes.dex */
    public static class b implements e {
        final String a;
        final int b;
        final String c;
        final Notification d;

        b(String str, int i, String str2, Notification notification) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = notification;
        }

        @Override // androidx.core.app.NotificationManagerCompat.e
        public void a(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.a, this.b, this.c, this.d);
        }

        @NonNull
        public String toString() {
            return "NotifyTask[packageName:" + this.a + ", id:" + this.b + ", tag:" + this.c + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class a implements e {
        final String a;
        final int b;
        final String c;
        final boolean d;

        a(String str) {
            this.a = str;
            this.b = 0;
            this.c = null;
            this.d = true;
        }

        a(String str, int i, String str2) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = false;
        }

        @Override // androidx.core.app.NotificationManagerCompat.e
        public void a(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.d) {
                iNotificationSideChannel.cancelAll(this.a);
            } else {
                iNotificationSideChannel.cancel(this.a, this.b, this.c);
            }
        }

        @NonNull
        public String toString() {
            return "CancelTask[packageName:" + this.a + ", id:" + this.b + ", tag:" + this.c + ", all:" + this.d + "]";
        }
    }
}
