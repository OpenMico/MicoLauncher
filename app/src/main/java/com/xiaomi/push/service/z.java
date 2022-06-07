package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.push.au;
import com.xiaomi.push.g;
import com.xiaomi.push.hh;
import com.xiaomi.push.hu;
import com.xiaomi.push.id;
import com.xiaomi.push.l;
import com.xiaomi.push.service.af;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class z {
    public static long a;
    private static final LinkedList<Pair<Integer, id>> b = new LinkedList<>();
    private static ExecutorService c = Executors.newCachedThreadPool();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements Callable<Bitmap> {
        private String a;
        private Context b;
        private boolean c;

        public a(String str, Context context, boolean z) {
            this.b = context;
            this.a = str;
            this.c = z;
        }

        /* renamed from: a */
        public Bitmap call() {
            String str;
            Bitmap bitmap = null;
            if (!TextUtils.isEmpty(this.a)) {
                if (this.a.startsWith("http")) {
                    af.b a = af.a(this.b, this.a, this.c);
                    if (a != null) {
                        return a.f186a;
                    }
                } else {
                    bitmap = af.a(this.b, this.a);
                    if (bitmap != null) {
                        return bitmap;
                    }
                }
                str = "Failed get online picture/icon resource";
            } else {
                str = "Failed get online picture/icon resource cause picUrl is empty";
            }
            com.xiaomi.channel.commonutils.logger.b.m149a(str);
            return bitmap;
        }
    }

    /* loaded from: classes4.dex */
    public static class b {
        Notification a;
        long b = 0;
    }

    /* loaded from: classes4.dex */
    public static class c {
        public long a = 0;

        /* renamed from: a  reason: collision with other field name */
        public String f191a;
    }

    /* loaded from: classes4.dex */
    public static class d {

        /* renamed from: a  reason: collision with other field name */
        public boolean f192a = false;
        public HashMap<String, Integer> a = new HashMap<>();
        public HashMap<String, HashSet<Integer>> b = new HashMap<>();
    }

    private static int a(Context context, String str, String str2) {
        if (str.equals(context.getPackageName())) {
            return context.getResources().getIdentifier(str2, "drawable", str);
        }
        return 0;
    }

    private static int a(String str, String str2) {
        return (((str + str2).hashCode() / 10) * 10) + 32768;
    }

    @TargetApi(16)
    private static Notification.Builder a(Notification.Builder builder, Context context, String str, Map<String, String> map) {
        PendingIntent a2 = a(context, str, 1, map);
        if (a2 != null && !TextUtils.isEmpty(map.get("notification_style_button_left_name"))) {
            builder.addAction(0, map.get("notification_style_button_left_name"), a2);
        }
        PendingIntent a3 = a(context, str, 2, map);
        if (a3 != null && !TextUtils.isEmpty(map.get("notification_style_button_mid_name"))) {
            builder.addAction(0, map.get("notification_style_button_mid_name"), a3);
        }
        PendingIntent a4 = a(context, str, 3, map);
        if (a4 != null && !TextUtils.isEmpty(map.get("notification_style_button_right_name"))) {
            builder.addAction(0, map.get("notification_style_button_right_name"), a4);
        }
        return builder;
    }

    @TargetApi(16)
    private static Notification.Builder a(Context context, Map<String, String> map, Notification.Builder builder, String str) {
        if ("2".equals(map.get("notification_style_type"))) {
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle(builder);
            Bitmap a2 = map == null ? null : a(context, map.get("notification_bigPic_uri"), false);
            if (a2 == null) {
                return builder;
            }
            bigPictureStyle.bigPicture(a2);
            bigPictureStyle.setSummaryText(str);
            bigPictureStyle.bigLargeIcon((Bitmap) null);
            builder.setStyle(bigPictureStyle);
        } else if ("1".equals(map.get("notification_style_type"))) {
            builder.setStyle(new Notification.BigTextStyle().bigText(str));
        }
        return builder;
    }

    private static Notification a(Notification notification) {
        Object a2 = au.a(notification, "extraNotification");
        if (a2 != null) {
            au.a(a2, "setCustomizedIcon", true);
        }
        return notification;
    }

    private static Notification a(Notification notification, String str) {
        try {
            Field declaredField = Notification.class.getDeclaredField("extraNotification");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(notification);
            Method declaredMethod = obj.getClass().getDeclaredMethod("setTargetPkg", CharSequence.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, str);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
        return notification;
    }

    private static PendingIntent a(Context context, id idVar, hu huVar, byte[] bArr) {
        ComponentName componentName;
        Intent intent;
        int i = c(idVar) ? 1000 : a(idVar) ? 3000 : -1;
        String str = "";
        if (huVar != null) {
            str = huVar.m988a();
        }
        if (huVar == null || TextUtils.isEmpty(huVar.e)) {
            if (a(idVar)) {
                intent = new Intent();
                componentName = new ComponentName("com.xiaomi.xmsf", "com.xiaomi.mipush.sdk.PushMessageHandler");
            } else {
                intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
                componentName = new ComponentName(idVar.b, "com.xiaomi.mipush.sdk.PushMessageHandler");
            }
            intent.setComponent(componentName);
            intent.putExtra("mipush_payload", bArr);
            intent.putExtra("mipush_notified", true);
            intent.addCategory(String.valueOf(huVar.c()));
            intent.putExtra("messageId", str);
            intent.putExtra("eventMessageType", i);
            if (!f.b(context, idVar.b, "com.xiaomi.mipush.MESSAGE_CLICKED")) {
                return PendingIntent.getService(context, 0, intent, 134217728);
            }
            Intent intent2 = new Intent();
            intent2.setAction("com.xiaomi.mipush.MESSAGE_CLICKED");
            intent2.setClassName(idVar.b, "com.xiaomi.mipush.sdk.BridgeActivity");
            intent2.addFlags(276824064);
            intent2.putExtra("mipush_serviceIntent", intent);
            intent2.addCategory(String.valueOf(huVar.c()));
            return PendingIntent.getActivity(context, 0, intent2, 134217728);
        }
        Intent intent3 = new Intent("android.intent.action.VIEW");
        intent3.setData(Uri.parse(huVar.e));
        intent3.addFlags(268435456);
        intent3.putExtra("messageId", str);
        intent3.putExtra("eventMessageType", i);
        return PendingIntent.getActivity(context, 0, intent3, 134217728);
    }

    private static PendingIntent a(Context context, String str, int i, Map<String, String> map) {
        Intent b2;
        if (map == null || (b2 = b(context, str, i, map)) == null) {
            return null;
        }
        return PendingIntent.getActivity(context, 0, b2, 0);
    }

    private static Intent a(Context context, Intent intent) {
        try {
            if (context.getPackageManager().getPackageInfo("com.android.browser", 4) != null) {
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            }
            return intent;
        } catch (PackageManager.NameNotFoundException e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return intent;
        }
    }

    private static Bitmap a(Context context, int i) {
        return a(context.getResources().getDrawable(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.concurrent.Future] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.concurrent.Future] */
    private static Bitmap a(Context context, String str, boolean z) {
        try {
            context = c.submit(new a(str, context, z));
            try {
                Bitmap bitmap = (Bitmap) context.get(180L, TimeUnit.SECONDS);
                return bitmap == null ? bitmap : bitmap;
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                context.cancel(true);
                return null;
            }
        } finally {
            context.cancel(true);
        }
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int i = 1;
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            i = intrinsicHeight;
        }
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02d4 A[Catch: Exception -> 0x03e3, TryCatch #2 {Exception -> 0x03e3, blocks: (B:124:0x02c8, B:126:0x02d4, B:128:0x0308, B:130:0x0318, B:132:0x0329, B:133:0x0338, B:137:0x0343, B:141:0x0357, B:142:0x0360, B:143:0x036d, B:145:0x0377, B:147:0x037b, B:149:0x037f, B:152:0x0386, B:154:0x038c, B:157:0x03a8, B:159:0x03ac, B:161:0x03b0, B:163:0x03b6, B:165:0x03d0, B:167:0x03d6), top: B:201:0x02c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x038c A[Catch: Exception -> 0x03e3, TryCatch #2 {Exception -> 0x03e3, blocks: (B:124:0x02c8, B:126:0x02d4, B:128:0x0308, B:130:0x0318, B:132:0x0329, B:133:0x0338, B:137:0x0343, B:141:0x0357, B:142:0x0360, B:143:0x036d, B:145:0x0377, B:147:0x037b, B:149:0x037f, B:152:0x0386, B:154:0x038c, B:157:0x03a8, B:159:0x03ac, B:161:0x03b0, B:163:0x03b6, B:165:0x03d0, B:167:0x03d6), top: B:201:0x02c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0489  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03f5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0119 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01f6  */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.xiaomi.push.service.z.b a(android.content.Context r18, com.xiaomi.push.id r19, byte[] r20, android.widget.RemoteViews r21, android.app.PendingIntent r22) {
        /*
            Method dump skipped, instructions count: 1190
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.a(android.content.Context, com.xiaomi.push.id, byte[], android.widget.RemoteViews, android.app.PendingIntent):com.xiaomi.push.service.z$b");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0106, code lost:
        if (r5 == null) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x010a, code lost:
        if (r5 == null) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x010e, code lost:
        if (r5 == null) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0112, code lost:
        if (r5 == null) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0114, code lost:
        com.xiaomi.push.ew.a(r19.getApplicationContext()).a(r20.b(), b(r20), r5.m988a(), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x012b, code lost:
        com.xiaomi.channel.commonutils.logger.b.a(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.push.service.z.c a(android.content.Context r19, com.xiaomi.push.id r20, byte[] r21) {
        /*
            Method dump skipped, instructions count: 805
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.a(android.content.Context, com.xiaomi.push.id, byte[]):com.xiaomi.push.service.z$c");
    }

    private static d a(NotificationManager notificationManager, String str, String str2) {
        StatusBarNotification[] activeNotifications;
        Object a2;
        d dVar = new d();
        if (notificationManager != null && Build.VERSION.SDK_INT >= 23 && !TextUtils.isEmpty(str) && (activeNotifications = notificationManager.getActiveNotifications()) != null && activeNotifications.length > 0) {
            for (StatusBarNotification statusBarNotification : activeNotifications) {
                if (statusBarNotification != null) {
                    String packageName = statusBarNotification.getPackageName();
                    String str3 = "";
                    if (statusBarNotification.getNotification() != null) {
                        str3 = statusBarNotification.getNotification().getGroup();
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        String str4 = packageName + str3;
                        HashSet<Integer> hashSet = null;
                        if (!str2.equals(str4) || (a2 = au.a(statusBarNotification.getNotification(), "isGroupSummary", (Object[]) null)) == null || !(a2 instanceof Boolean) || !((Boolean) Boolean.class.cast(a2)).booleanValue()) {
                            dVar.a.put(str4, Integer.valueOf((dVar.a.containsKey(str4) ? dVar.a.get(str4).intValue() : 0) + 1));
                            if (dVar.b.containsKey(str4)) {
                                hashSet = dVar.b.get(str4);
                            }
                            if (hashSet == null) {
                                hashSet = new HashSet<>();
                                dVar.b.put(str4, hashSet);
                            }
                            hashSet.add(Integer.valueOf(statusBarNotification.getId()));
                        } else {
                            dVar.f192a = true;
                        }
                    }
                }
            }
        }
        return dVar;
    }

    private static String a(Context context, String str, Map<String, String> map) {
        return (map == null || TextUtils.isEmpty(map.get("channel_name"))) ? g.b(context, str) : map.get("channel_name");
    }

    private static void a(Notification notification, int i) {
        Object a2 = au.a(notification, "extraNotification");
        if (a2 != null) {
            au.a(a2, "setMessageCount", Integer.valueOf(i));
        }
    }

    @TargetApi(23)
    private static void a(Context context, Notification notification, int i, String str) {
        HashSet<Integer> hashSet;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        String str2 = context.getPackageName() + notification.getGroup();
        d a2 = a(notificationManager, notification.getGroup(), str2);
        boolean z = false;
        int intValue = a2.a.containsKey(str2) ? a2.a.get(str2).intValue() : 0;
        if (a2.b.containsKey(str2) && (hashSet = a2.b.get(str2)) != null && hashSet.contains(Integer.valueOf(i))) {
            z = true;
        }
        if (intValue > 1 || (intValue == 1 && !z)) {
            if (!a2.f192a) {
                a(context, str, notification, notificationManager);
            }
        } else if (intValue < 1 && a2.f192a) {
            b(notificationManager, str, notification.getGroup());
        }
    }

    public static void a(Context context, String str) {
        a(context, str, -1);
    }

    public static void a(Context context, String str, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int hashCode = ((str.hashCode() / 10) * 10) + i;
        LinkedList linkedList = new LinkedList();
        if (i >= 0) {
            notificationManager.cancel(hashCode);
        }
        synchronized (b) {
            Iterator<Pair<Integer, id>> it = b.iterator();
            while (it.hasNext()) {
                Pair<Integer, id> next = it.next();
                id idVar = (id) next.second;
                if (idVar != null) {
                    String f = f(idVar);
                    if (i >= 0) {
                        if (hashCode == ((Integer) next.first).intValue() && TextUtils.equals(f, str)) {
                            linkedList.add(next);
                        }
                    } else if (i == -1 && TextUtils.equals(f, str)) {
                        notificationManager.cancel(((Integer) next.first).intValue());
                        linkedList.add(next);
                    }
                }
            }
            if (b != null) {
                b.removeAll(linkedList);
                a(context, linkedList);
            }
        }
    }

    @TargetApi(23)
    private static void a(Context context, String str, Notification notification, NotificationManager notificationManager) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                String group = notification.getGroup();
                Notification notification2 = null;
                if (Build.VERSION.SDK_INT >= 26) {
                    if (au.a(notificationManager, "getNotificationChannel", "groupSummary") == null) {
                        au.a(notificationManager, "createNotificationChannel", Class.forName("android.app.NotificationChannel").getConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance("groupSummary", "group_summary", 3));
                    }
                    if (!TextUtils.isEmpty(group)) {
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setContentTitle("GroupSummary").setContentText("GroupSummary").setLargeIcon(notification.getLargeIcon()).setSmallIcon(notification.getSmallIcon()).setAutoCancel(true).setGroup(group).setGroupSummary(true);
                        au.a(builder, "setChannelId", "groupSummary");
                        notification2 = builder.build();
                    }
                } else if (!TextUtils.isEmpty(group)) {
                    Notification.Builder builder2 = new Notification.Builder(context);
                    builder2.setContentTitle("GroupSummary").setContentText("GroupSummary").setLargeIcon(notification.getLargeIcon()).setSmallIcon(notification.getSmallIcon()).setPriority(0).setDefaults(-1).setAutoCancel(true).setGroup(group).setGroupSummary(true);
                    notification2 = builder2.build();
                }
                if (notification2 != null && !l.c() && "com.xiaomi.xmsf".equals(context.getPackageName())) {
                    a(notification2, str);
                    if (notification2.extras != null) {
                        notification2.extras.putString("target_package", str);
                    }
                }
                int a2 = a(str, group);
                if (notification2 != null) {
                    notificationManager.notify(a2, notification2);
                    com.xiaomi.channel.commonutils.logger.b.b("GROUPSUMMARY  : showGroupSummary targetPackageName = " + str + " id = " + a2);
                }
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            LinkedList linkedList = new LinkedList();
            synchronized (b) {
                Iterator<Pair<Integer, id>> it = b.iterator();
                while (it.hasNext()) {
                    Pair<Integer, id> next = it.next();
                    id idVar = (id) next.second;
                    if (idVar != null) {
                        String f = f(idVar);
                        hu a2 = idVar.m1021a();
                        if (a2 != null && TextUtils.equals(f, str)) {
                            String c2 = a2.m996c();
                            String d2 = a2.d();
                            if (!TextUtils.isEmpty(c2) && !TextUtils.isEmpty(d2) && b(str2, c2) && b(str3, d2)) {
                                notificationManager.cancel(((Integer) next.first).intValue());
                                linkedList.add(next);
                            }
                        }
                    }
                }
                if (b != null) {
                    b.removeAll(linkedList);
                    a(context, linkedList);
                }
            }
        }
    }

    public static void a(Context context, LinkedList<? extends Object> linkedList) {
        if (linkedList != null && linkedList.size() > 0) {
            be.a(context, "category_clear_notification", "clear_notification", linkedList.size(), "");
        }
    }

    private static void a(Object obj, Map<String, String> map) {
        if (map != null && !TextUtils.isEmpty(map.get("channel_description"))) {
            au.a(obj, "setDescription", map.get("channel_description"));
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m1168a(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && Arrays.asList(runningAppProcessInfo.pkgList).contains(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(hu huVar) {
        if (huVar == null) {
            return false;
        }
        String a2 = huVar.m988a();
        return !TextUtils.isEmpty(a2) && a2.length() == 22 && "satuigm".indexOf(a2.charAt(0)) >= 0;
    }

    public static boolean a(id idVar) {
        hu a2 = idVar.m1021a();
        return a(a2) && a2.l();
    }

    public static boolean a(Map<String, String> map) {
        if (map == null || !map.containsKey("notify_foreground")) {
            return true;
        }
        return "1".equals(map.get("notify_foreground"));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004e, code lost:
        if (android.text.TextUtils.isEmpty(r3) == false) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0070, code lost:
        if (android.text.TextUtils.isEmpty(r3) == false) goto L_0x0072;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String[] a(android.content.Context r3, com.xiaomi.push.hu r4) {
        /*
            java.lang.String r0 = r4.m996c()
            java.lang.String r1 = r4.d()
            java.util.Map r4 = r4.m989a()
            if (r4 == 0) goto L_0x0073
            android.content.res.Resources r2 = r3.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.content.res.Resources r3 = r3.getResources()
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
            float r3 = r3.density
            float r2 = (float) r2
            float r2 = r2 / r3
            r3 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r3
            java.lang.Float r3 = java.lang.Float.valueOf(r2)
            int r3 = r3.intValue()
            r2 = 320(0x140, float:4.48E-43)
            if (r3 > r2) goto L_0x0051
            java.lang.String r3 = "title_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0042
            r0 = r3
        L_0x0042:
            java.lang.String r3 = "description_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x0073
            goto L_0x0072
        L_0x0051:
            r2 = 360(0x168, float:5.04E-43)
            if (r3 <= r2) goto L_0x0073
            java.lang.String r3 = "title_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0064
            r0 = r3
        L_0x0064:
            java.lang.String r3 = "description_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x0073
        L_0x0072:
            r1 = r3
        L_0x0073:
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]
            r4 = 0
            r3[r4] = r0
            r4 = 1
            r3[r4] = r1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.a(android.content.Context, com.xiaomi.push.hu):java.lang.String[]");
    }

    static int b(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).getInt(str, Integer.MAX_VALUE);
    }

    private static int b(Map<String, String> map) {
        String str = map == null ? null : map.get(RtspHeaders.Values.TIMEOUT);
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.Intent b(android.content.Context r5, java.lang.String r6, int r7, java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            Method dump skipped, instructions count: 369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.z.b(android.content.Context, java.lang.String, int, java.util.Map):android.content.Intent");
    }

    private static RemoteViews b(Context context, id idVar, byte[] bArr) {
        hu a2 = idVar.m1021a();
        String f = f(idVar);
        Map<String, String> a3 = a2.m989a();
        if (a3 == null) {
            return null;
        }
        String str = a3.get("layout_name");
        String str2 = a3.get("layout_value");
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(f);
                int identifier = resourcesForApplication.getIdentifier(str, "layout", f);
                if (identifier == 0) {
                    return null;
                }
                RemoteViews remoteViews = new RemoteViews(f, identifier);
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.has("text")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("text");
                        Iterator keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String str3 = (String) keys.next();
                            String string = jSONObject2.getString(str3);
                            int identifier2 = resourcesForApplication.getIdentifier(str3, "id", f);
                            if (identifier2 > 0) {
                                remoteViews.setTextViewText(identifier2, string);
                            }
                        }
                    }
                    if (jSONObject.has(MimeTypes.BASE_TYPE_IMAGE)) {
                        JSONObject jSONObject3 = jSONObject.getJSONObject(MimeTypes.BASE_TYPE_IMAGE);
                        Iterator keys2 = jSONObject3.keys();
                        while (keys2.hasNext()) {
                            String str4 = (String) keys2.next();
                            String string2 = jSONObject3.getString(str4);
                            int identifier3 = resourcesForApplication.getIdentifier(str4, "id", f);
                            int identifier4 = resourcesForApplication.getIdentifier(string2, "drawable", f);
                            if (identifier3 > 0) {
                                remoteViews.setImageViewResource(identifier3, identifier4);
                            }
                        }
                    }
                    if (jSONObject.has("time")) {
                        JSONObject jSONObject4 = jSONObject.getJSONObject("time");
                        Iterator keys3 = jSONObject4.keys();
                        while (keys3.hasNext()) {
                            String str5 = (String) keys3.next();
                            String string3 = jSONObject4.getString(str5);
                            if (string3.length() == 0) {
                                string3 = "yy-MM-dd hh:mm";
                            }
                            int identifier5 = resourcesForApplication.getIdentifier(str5, "id", f);
                            if (identifier5 > 0) {
                                remoteViews.setTextViewText(identifier5, new SimpleDateFormat(string3).format(new Date(System.currentTimeMillis())));
                            }
                        }
                    }
                    return remoteViews;
                } catch (JSONException e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    return null;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        return null;
    }

    public static String b(id idVar) {
        return a(idVar) ? "E100002" : c(idVar) ? "E100000" : m1169b(idVar) ? "E100001" : d(idVar) ? "E100003" : "";
    }

    private static void b(NotificationManager notificationManager, String str, String str2) {
        int a2 = a(str, str2);
        com.xiaomi.channel.commonutils.logger.b.b("GROUPSUMMARY  : cancelGroupSummarytargetPackageName = " + str + " id = " + a2);
        notificationManager.cancel(a2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(Context context, String str, int i) {
        context.getSharedPreferences("pref_notify_type", 0).edit().putInt(str, i).commit();
    }

    /* renamed from: b  reason: collision with other method in class */
    public static boolean m1169b(id idVar) {
        hu a2 = idVar.m1021a();
        return a(a2) && a2.b == 1 && !a(idVar);
    }

    private static boolean b(String str, String str2) {
        return TextUtils.isEmpty(str) || str2.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void c(Context context, String str) {
        context.getSharedPreferences("pref_notify_type", 0).edit().remove(str).commit();
    }

    public static boolean c(id idVar) {
        hu a2 = idVar.m1021a();
        return a(a2) && a2.b == 0 && !a(idVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean d(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).contains(str);
    }

    public static boolean d(id idVar) {
        return idVar.a() == hh.Registration;
    }

    private static int e(Context context, String str) {
        int a2 = a(context, str, "mipush_notification");
        int a3 = a(context, str, "mipush_small_notification");
        if (a2 <= 0) {
            a2 = a3 > 0 ? a3 : context.getApplicationInfo().icon;
        }
        return (a2 != 0 || Build.VERSION.SDK_INT < 9) ? a2 : context.getApplicationInfo().logo;
    }

    public static boolean e(id idVar) {
        return a(idVar) || c(idVar) || m1169b(idVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String f(id idVar) {
        hu a2;
        if (!(!"com.xiaomi.xmsf".equals(idVar.b) || (a2 = idVar.m1021a()) == null || a2.m989a() == null)) {
            String str = a2.m989a().get("miui_package_name");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return idVar.b;
    }
}
