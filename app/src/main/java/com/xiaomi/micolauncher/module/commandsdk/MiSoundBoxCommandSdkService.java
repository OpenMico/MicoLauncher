package com.xiaomi.micolauncher.module.commandsdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mi_soundbox_command_sdk.IMiCommandCallback;
import com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface;
import com.xiaomi.mi_soundbox_command_sdk.LogHelper;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class MiSoundBoxCommandSdkService extends Service {
    public static final String EMPTY_ARGS_JSON = "";
    private static MiSoundBoxCommandSdkService f;
    private volatile String d;
    private static final IMiCommandCallback a = new a();
    private static final c b = new c(a, false);
    private static MiSoundBoxCommandSdkService g = new MiSoundBoxCommandSdkService();
    private MyRemoteCallbackList c = new MyRemoteCallbackList();
    private b e = new b();

    public void setCurrentPackage(Context context, String str) {
        this.d = str;
    }

    public void pause(Context context) {
        this.e.a("pause", "");
    }

    public void resume(Context context) {
        this.e.a(Commands.RESUME, "");
    }

    public void fastForward(Context context, long j) {
        this.e.a("forward", a(MiSoundBoxCommandExtras.DURATION_MILLIS, j));
    }

    public void fastBackward(Context context, long j) {
        this.e.a(Commands.BACKWARD, a(MiSoundBoxCommandExtras.DURATION_MILLIS, j));
    }

    public void stop(Context context) {
        this.e.a(Commands.STOP_PLAY, "");
    }

    public void quit(Context context) {
        this.e.a(Commands.QUIT, "");
    }

    public void repeat(Context context) {
        this.e.a(Commands.REPEAT, "");
    }

    public void previous(Context context) {
        this.e.a(Commands.PREV, "");
    }

    public void next(Context context) {
        this.e.a("next", "");
    }

    public void select(Context context, int i) {
        this.e.a(Commands.SELECT_INDEX, a(MiSoundBoxCommandExtras.INDEX, i));
    }

    public void setResolution(Context context, String str) {
        this.e.a(Commands.SET_RESOLUTION, a("resolution", str));
    }

    public void skipStart(Context context) {
        this.e.a(Commands.SKIP_START, "");
    }

    public void seek(Context context, long j) {
        this.e.a("seek", a(MiSoundBoxCommandExtras.DURATION_MILLIS, j));
    }

    public void like(Context context) {
        this.e.a("like", "");
    }

    public boolean b() {
        if (!getInstance().isDummyService()) {
            return true;
        }
        DebugHelper.printStackTrace(this + " not started yet");
        return false;
    }

    public static MiSoundBoxCommandSdkService getInstance() {
        MiSoundBoxCommandSdkService miSoundBoxCommandSdkService = f;
        return miSoundBoxCommandSdkService == null ? g : miSoundBoxCommandSdkService;
    }

    private static String a(String str, long j) {
        MiSoundBoxCommandExtras miSoundBoxCommandExtras = new MiSoundBoxCommandExtras();
        miSoundBoxCommandExtras.put(str, String.valueOf(j));
        return Gsons.getGson().toJson(miSoundBoxCommandExtras);
    }

    private static String a(String str, String str2) {
        MiSoundBoxCommandExtras miSoundBoxCommandExtras = new MiSoundBoxCommandExtras();
        miSoundBoxCommandExtras.put(str, str2);
        return Gsons.getGson().toJson(miSoundBoxCommandExtras);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        f = this;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        f = this;
        return super.onStartCommand(intent, i, i2);
    }

    public boolean isDummyService() {
        return this == g;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        String str = intent.getPackage();
        LogHelper.i("package is " + str);
        return this.e;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public String getCurrentClientPackage() {
        return this.d;
    }

    /* loaded from: classes3.dex */
    private static class a implements IMiCommandCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandCallback
        public void onCommand(String str, String str2) {
        }

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class b extends IMiCommandInterface.Stub {
        b() {
            MiSoundBoxCommandSdkService.this = r1;
        }

        void a(String str, String str2) {
            if (!MiSoundBoxCommandSdkService.this.b()) {
                LogHelper.e("service not started yet, command ignored:(" + str + Constants.ACCEPT_TIME_SEPARATOR_SP + str2 + ")");
            } else if (MiSoundBoxCommandSdkService.this.d == null) {
                LogHelper.i("current client package not set");
            } else {
                try {
                    IMiCommandCallback iMiCommandCallback = MiSoundBoxCommandSdkService.this.c.a(MiSoundBoxCommandSdkService.this.d).a;
                    LogHelper.i("send command callback " + iMiCommandCallback + " command " + str + " with arg : " + str2);
                    iMiCommandCallback.onCommand(str, str2);
                } catch (RemoteException e) {
                    LogHelper.i("failed to send command " + str, e);
                }
            }
        }

        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
        public void registerCallback(String str, IMiCommandCallback iMiCommandCallback) {
            LogHelper.i("register callback " + iMiCommandCallback);
            MiSoundBoxCommandSdkService.this.d = str;
            LogHelper.i("set current client package to " + str);
            MiSoundBoxCommandSdkService.this.c.a(str, iMiCommandCallback);
        }

        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
        public void unRegisterCallback(String str, IMiCommandCallback iMiCommandCallback) {
            MiSoundBoxCommandSdkService.this.c.b(str, iMiCommandCallback);
        }

        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
        public void setPlaying(String str, boolean z) {
            a(str);
            MiSoundBoxCommandSdkService.this.c.a(MiSoundBoxCommandSdkService.this.d).b = z;
        }

        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
        public void setPlayInfo(String str) throws RemoteException {
            LogHelper.i("received client play info " + str);
        }

        private void a(String str) {
            if (!TextUtils.equals(str, MiSoundBoxCommandSdkService.this.d)) {
                LogHelper.i("not current package : " + str);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class c {
        IMiCommandCallback a;
        boolean b;

        c(IMiCommandCallback iMiCommandCallback, boolean z) {
            this.b = z;
            this.a = iMiCommandCallback;
        }
    }

    /* loaded from: classes3.dex */
    public static class MyRemoteCallbackList {
        private RemoteCallbackList<IMiCommandCallback> a = new RemoteCallbackList<>();
        private Map<String, c> b = new ConcurrentHashMap();

        void a(String str, IMiCommandCallback iMiCommandCallback) {
            this.b.put(str, new c(iMiCommandCallback, Configs.initialPlaying(str)));
            this.a.register(iMiCommandCallback);
        }

        void b(String str, IMiCommandCallback iMiCommandCallback) {
            this.b.remove(str);
            this.a.unregister(iMiCommandCallback);
        }

        public void onCallbackDied(IMiCommandCallback iMiCommandCallback, Object obj) {
            this.a.onCallbackDied(iMiCommandCallback, obj);
        }

        c a(String str) {
            c cVar = this.b.get(str);
            return cVar == null ? MiSoundBoxCommandSdkService.b : cVar;
        }
    }
}
