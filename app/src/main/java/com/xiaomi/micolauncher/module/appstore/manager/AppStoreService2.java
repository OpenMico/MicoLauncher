package com.xiaomi.micolauncher.module.appstore.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.xiaomi.mico.appstore.IAppStoreService2;
import com.xiaomi.mico.appstore.IAppStoreServiceCallback;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AppStoreService2 extends Service {
    private final a a = new a();
    private NotificationManager b = null;
    private boolean c;

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        startForeground(3, a());
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        L.storage.i("AppStoreService2 onBind %s", Integer.valueOf(Process.myPid()));
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a extends IAppStoreService2.Stub {
        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ List b(Throwable th) throws Exception {
            return null;
        }

        private a() {
        }

        @Override // com.xiaomi.mico.appstore.IAppStoreService2
        public void getAppInfoListData(IAppStoreServiceCallback iAppStoreServiceCallback) {
            List<AppInfo> blockingSingle = AppStoreManager.getManager().getAppInfoList().onErrorReturn($$Lambda$AppStoreService2$a$l2mjCxmntZ8uLmi8BtT5IzJLiU.INSTANCE).blockingSingle();
            if (blockingSingle != null) {
                String json = Gsons.getGson().toJson(blockingSingle);
                if (iAppStoreServiceCallback != null) {
                    try {
                        iAppStoreServiceCallback.callback(0, json);
                    } catch (RemoteException e) {
                        L.storage.e("AppStoreService2 getAppInfoListData", e);
                    }
                }
            } else if (iAppStoreServiceCallback != null) {
                try {
                    iAppStoreServiceCallback.callback(1, null);
                } catch (RemoteException e2) {
                    L.storage.e("AppStoreService2 getAppInfoListData", e2);
                }
            }
        }

        @Override // com.xiaomi.mico.appstore.IAppStoreService2
        public void getAppInfoListDataFromServer(IAppStoreServiceCallback iAppStoreServiceCallback) {
            List<AppInfo> list;
            AppStoreOtaUtils.getInstance().reportMeshGatewayOtaStatistic(MicoApplication.getApp().getApplicationContext(), "null");
            try {
                list = AppStoreManager.getManager().getAppInfoListFromServer() != null ? AppStoreManager.getManager().getAppInfoListFromServer().onErrorReturn($$Lambda$AppStoreService2$a$iAzKjTvV9uIf92axXjhHodYCBVQ.INSTANCE).blockingSingle() : null;
            } catch (Exception e) {
                L.storage.e("AppStoreService2 getAppInfoListDataFromServer 1", e);
                list = null;
            }
            if (list != null) {
                String json = Gsons.getGson().toJson(list);
                if (iAppStoreServiceCallback != null) {
                    try {
                        iAppStoreServiceCallback.callback(0, json);
                    } catch (RemoteException e2) {
                        L.storage.e("AppStoreService2 getAppInfoListDataFromServer 3", e2);
                    }
                }
            } else if (iAppStoreServiceCallback != null) {
                try {
                    iAppStoreServiceCallback.callback(1, null);
                } catch (RemoteException e3) {
                    L.storage.e("AppStoreService2 getAppInfoListDataFromServer 2 ", e3);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ List a(Throwable th) throws Exception {
            L.storage.e("AppStoreService2 getAppInfoListDataFromServer 0", th);
            return new ArrayList();
        }
    }

    private Notification a() {
        if (this.b == null) {
            this.b = (NotificationManager) MicoApplication.getApp().getSystemService("notification");
        }
        String packageName = MicoApplication.getApp().getPackageName();
        if (!this.c) {
            NotificationChannel notificationChannel = new NotificationChannel(packageName, "MICO_APP_STORE_NOTIFICATION", 3);
            notificationChannel.setLightColor(-16776961);
            this.b.createNotificationChannel(notificationChannel);
            this.c = true;
        }
        Notification.Builder builder = new Notification.Builder(MicoApplication.getApp().getApplicationContext(), packageName);
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentText("").setContentIntent(PendingIntent.getActivity(this, 0, getPackageManager().getLaunchIntentForPackage(getPackageName()), 134217728)).setWhen(System.currentTimeMillis());
        return builder.build();
    }
}
