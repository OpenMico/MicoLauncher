package com.xiaomi.miplay.videoswitchmirror;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror;
import com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient;

/* loaded from: classes4.dex */
public class MiplayClientControl implements IMiplayClient {
    private static final String a = "MiplayClientControl";
    private Context b;
    private String c;
    private MiplayVideoSwitchMirrorCallback f;
    private IIVideoSwitchMirror g;
    private MiplayVideoSwitchMirrorCallback h = new MiplayVideoSwitchMirrorCallback() { // from class: com.xiaomi.miplay.videoswitchmirror.MiplayClientControl.1
        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void onInitSuccess() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(0).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void onInitError() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(1).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void resumeMirror() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(2).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void startCirculate() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(3).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void endCirculate() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(4).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void closeMirror() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(5).sendToTarget();
        }
    };
    private ServiceConnection i = new ServiceConnection() { // from class: com.xiaomi.miplay.videoswitchmirror.MiplayClientControl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiplayClientControl.this.g = IIVideoSwitchMirror.Stub.asInterface(iBinder);
            Log.i(MiplayClientControl.a, "onServiceConnected: ");
            if (MiplayClientControl.this.g != null) {
                try {
                    MiplayClientControl.this.g.init(MiplayClientControl.this.c, MiplayClientControl.this.h);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(MiplayClientControl.a, "onServiceDisconnected: ");
        }
    };
    private HandlerThread e = new HandlerThread(getClass().getName());
    private a d = new a(this.e.getLooper());

    public MiplayClientControl(Context context) {
        this.b = context;
        this.c = context.getPackageName();
        this.e.start();
    }

    /* loaded from: classes4.dex */
    private class a extends Handler {
        private a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 0:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onInitSuccess();
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 1:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onInitError();
                            return;
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.resumeMirror();
                            return;
                        } catch (RemoteException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 3:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.startCirculate();
                            return;
                        } catch (RemoteException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 4:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.endCirculate();
                            return;
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 5:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.closeMirror();
                            return;
                        } catch (RemoteException e6) {
                            e6.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                default:
                    Log.i(MiplayClientControl.a, "unknown msg!");
                    return;
            }
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public boolean initAsync(MiplayVideoSwitchMirrorCallback miplayVideoSwitchMirrorCallback) {
        this.f = miplayVideoSwitchMirrorCallback;
        Log.i(a, "initAsync: ");
        Intent intent = new Intent();
        intent.setPackage(Constant.PACKAGENAME_SYSTEMUI);
        intent.setAction("COM.XIAOMI.MIPLAY.ACTION.VIDEOSWITCHMIRROR_CLIENT_SERVICE");
        return this.b.bindService(intent, this.i, Build.VERSION.SDK_INT >= 29 ? 4097 : 1);
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public void unInit() {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror != null) {
            try {
                iIVideoSwitchMirror.unInit(this.c);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.b.unbindService(this.i);
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int connectMirrorSuccess(String str) {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.connectMirrorSuccess(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int disconnectMirror(String str) {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.disconnectMirror(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int resumeMirrorSuccess() {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.resumeMirrorSuccess();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int resumeMirrorFail() {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.resumeMirrorFail();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int startDiscovery(int i) {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.startDiscovery(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int getMirrorMode(String str) {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.getMirrorMode(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.api.IMiplayClient
    public int userDisconnectMirror(String str) {
        IIVideoSwitchMirror iIVideoSwitchMirror = this.g;
        if (iIVideoSwitchMirror == null) {
            return -1;
        }
        try {
            return iIVideoSwitchMirror.userDisconnectMirror(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
