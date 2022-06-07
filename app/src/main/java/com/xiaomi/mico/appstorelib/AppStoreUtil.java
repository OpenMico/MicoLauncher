package com.xiaomi.mico.appstorelib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.appstore.IAppStoreService2;
import com.xiaomi.mico.appstore.IAppStoreServiceCallback;
import com.xiaomi.mico.appstore.IAppstoreService;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class AppStoreUtil {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a {
        ServiceConnection a;
        Context b;
        Handler c;

        public a(Context context) {
            this.c = null;
            if (context != null) {
                this.b = context;
                this.c = new Handler(context.getMainLooper());
            }
        }

        void a(ServiceConnection serviceConnection) {
            this.a = serviceConnection;
        }

        void a() {
            Handler handler = this.c;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$a$-iNYm4zqbvbd5p2YvAXABCwN2HQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreUtil.a.this.b();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b() {
            Log.i("APPSTORELIB.storage", "diconnect " + this.a);
            ServiceConnection serviceConnection = this.a;
            if (serviceConnection != null) {
                this.b.unbindService(serviceConnection);
                this.a = null;
            }
        }
    }

    public static void initAppStore(Context context) {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.1
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                Log.i("APPSTORELIB.storage", "initAppStore");
                try {
                    this.a.initAppStore();
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "initAppStore " + e.toString());
                }
                a.this.a();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i("APPSTORELIB.storage", "onServiceDisconnected initAppStore");
                this.a = null;
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static Observable<List<AppInfo>> getAppInfoList(final Context context) {
        Log.i("APPSTORELIB.storage", "getAppInfoList");
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$yBPJyQjbd-F7R6Tb_mA4AIOmW-I
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreUtil.b(context, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Context context, final ObservableEmitter observableEmitter) throws Exception {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.3
            IAppStoreService2 a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppStoreService2.Stub.asInterface(iBinder);
                try {
                    this.a.getAppInfoListData(new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.3.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str) {
                            Log.i("APPSTORELIB.storage", "getAppInfoList " + str);
                            if (ContainerUtil.hasData(str)) {
                                List list = (List) Gsons.getGson().fromJson(str, new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.3.1.1
                                }.getType());
                                Log.i("APPSTORELIB.storage", "getAppInfoList size " + list.size());
                                ObservableEmitter.this.onNext(list);
                                ObservableEmitter.this.onComplete();
                            } else {
                                ObservableEmitter.this.onNext(new ArrayList());
                                ObservableEmitter.this.onComplete();
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "getAppInfoList " + e.toString());
                    ObservableEmitter.this.onNext(new ArrayList());
                    ObservableEmitter.this.onComplete();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected getAppInfoList");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage("com.xiaomi.micolauncher");
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static Observable<List<AppInfo>> getAppInfoListFromServer(final Context context) {
        Log.i("APPSTORELIB.storage", "getAppInfoListFromServer");
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$wT-pPt7bK2qR7IoZ8wKz9Eyvxu4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreUtil.a(context, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, final ObservableEmitter observableEmitter) throws Exception {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.4
            IAppStoreService2 a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppStoreService2.Stub.asInterface(iBinder);
                try {
                    this.a.getAppInfoListDataFromServer(new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.4.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str) {
                            Log.i("APPSTORELIB.storage", "getAppInfoListFromServer " + str);
                            if (ContainerUtil.hasData(str)) {
                                List list = (List) Gsons.getGson().fromJson(str, new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.4.1.1
                                }.getType());
                                Log.i("APPSTORELIB.storage", "getAppInfoListFromServer size " + list.size());
                                ObservableEmitter.this.onNext(list);
                                ObservableEmitter.this.onComplete();
                            } else {
                                ObservableEmitter.this.onNext(new ArrayList());
                                ObservableEmitter.this.onComplete();
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "getAppInfoListFromServer " + e.toString());
                    ObservableEmitter.this.onNext(new ArrayList());
                    ObservableEmitter.this.onComplete();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected getAppInfoListFromServer");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage("com.xiaomi.micolauncher");
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static void installApp(Context context, final AppInfo appInfo, final Runnable runnable) {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.5
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.installApp(AppInfo.this.toJson(), new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.5.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str) {
                            if (!(i == 2 || i != 0 || runnable == null)) {
                                runnable.run();
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "installApp failed" + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected installApp");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static Observable<List<AppInfo>> updateAppSize(final Context context, final String str) {
        Log.i("APPSTORELIB.storage", "updateAppSize");
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$AdR5GF4JfQA_cvZLqDPvGYsXRLI
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreUtil.c(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context, final String str, final ObservableEmitter observableEmitter) throws Exception {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.6
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                Log.i("APPSTORELIB.storage", String.format(" IAppstoreService %s %s", componentName, str));
                try {
                    this.a.updateAppSize(str, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.6.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str2) {
                            Log.i("APPSTORELIB.storage", String.format(" updateAppSize returned %s", Integer.valueOf(i)));
                            if (i == 0) {
                                if (ContainerUtil.hasData(str2)) {
                                    observableEmitter.onNext((List) Gsons.getGson().fromJson(str2, new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.6.1.1
                                    }.getType()));
                                    observableEmitter.onComplete();
                                } else {
                                    observableEmitter.onNext(new ArrayList());
                                    observableEmitter.onComplete();
                                }
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "updateAppSize " + e.toString());
                    observableEmitter.onNext(new ArrayList());
                    observableEmitter.onComplete();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected updateAppSize");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static Observable<String> clearAppCaches(final Context context, final String str) {
        Log.i("APPSTORELIB.storage", "clearAppCaches");
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$5B0Nl-O0AN70bakwxrYY7ssGHPg
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreUtil.b(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Context context, final String str, final ObservableEmitter observableEmitter) throws Exception {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.7
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.clearAppCache(str, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.7.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str2) {
                            if (i == 0) {
                                observableEmitter.onNext(str2);
                                observableEmitter.onComplete();
                            } else {
                                observableEmitter.onNext("");
                                observableEmitter.onComplete();
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "clearAppCache " + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected clearAppCaches");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static Observable<String> deepClearApps(final Context context, final String str) {
        Log.i("APPSTORELIB.storage", "deepClearApps");
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.appstorelib.-$$Lambda$AppStoreUtil$6TO1_4_vLeXVrmRIbXLVRoKl_KI
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreUtil.a(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, final String str, final ObservableEmitter observableEmitter) throws Exception {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.8
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.silentUninstallApp(str, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.8.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str2) {
                            if (i == 0) {
                                observableEmitter.onNext(str2);
                                observableEmitter.onComplete();
                            } else {
                                observableEmitter.onNext("");
                                observableEmitter.onComplete();
                            }
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "deepClearApps " + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected deepClearApps");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 16384);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isSystemApp(Context context, String str) {
        try {
            return (context.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void runAppStoreTimer(Context context, final boolean z) {
        Log.i("APPSTORELIB.storage", "runAppStoreTimer");
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.9
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.runAppStoreTimer(z, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.9.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str) {
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "runAppStoreTimer " + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected runAppStoreTimer");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static void silentUpgradeApps(Context context, final String str) {
        Log.i("APPSTORELIB.storage", "silentUpgradeApps");
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.10
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.silentUpgradeApps(str, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.10.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str2) {
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "silentUpgradeApps " + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected silentUpgradeApps");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }

    public static void setPreference(Context context, final String str) {
        Log.i("APPSTORELIB.storage", "setPreference");
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.2
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    this.a.setPreference(str, new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.mico.appstorelib.AppStoreUtil.2.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str2) {
                            aVar.a();
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("APPSTORELIB.storage", "setPreference " + e.toString());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                Log.i("APPSTORELIB.storage", "onServiceDisconnected setPreference");
            }
        };
        Intent intent = new Intent();
        intent.setAction(AppStoreApi.APPSTORE_BIND_ACTION);
        intent.setPackage(AppStoreApi.APPSTORE_PKG_NAME);
        if (context.bindService(intent, serviceConnection, 1)) {
            aVar.a(serviceConnection);
        }
    }
}
