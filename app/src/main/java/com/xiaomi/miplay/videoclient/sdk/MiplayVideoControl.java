package com.xiaomi.miplay.videoclient.sdk;

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
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.videoclient.IMiPlayVideoClient;
import com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MiplayVideoControl implements MiplayVideoClient {
    private static final String a = "MiplayVideoControl";
    private Context b;
    private String c;
    private String d;
    private MiplayVideoCallback g;
    private IMiPlayVideoClient h;
    private volatile boolean j;
    private final Object i = new Object();
    private List<MiPlayDeviceControlCenter> k = new ArrayList();
    private MiplayVideoCallback l = new MiplayVideoCallback() { // from class: com.xiaomi.miplay.videoclient.sdk.MiplayVideoControl.1
        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onInitSuccess() throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(0).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onInitError() throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(1).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceFoundControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(3, miPlayDeviceControlCenter).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceUpdateControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(4, miPlayDeviceControlCenter).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceLost(String str) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(2, str).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPositionAck(String str, long j) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(5, new Object[]{str, Long.valueOf(j)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPositionChange(String str, long j) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(6, new Object[]{str, Long.valueOf(j)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPlayStateChange(String str, int i) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(8, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPlayStateAck(String str, int i) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(7, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onMediaInfoAck(String str, MediaMetaData mediaMetaData) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(9, new Object[]{str, mediaMetaData}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onMediaInfoChange(String str, MediaMetaData mediaMetaData) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(10, new Object[]{str, mediaMetaData}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onVolumeChange(String str, double d) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(12, new Object[]{str, Double.valueOf(d)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onVolumeAck(String str, double d) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(11, new Object[]{str, Double.valueOf(d)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onBufferStateChange(String str, int i) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(13, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onBeSeized(String str) throws RemoteException {
            MiplayVideoControl.this.e.obtainMessage(14, str).sendToTarget();
        }
    };
    private ServiceConnection m = new ServiceConnection() { // from class: com.xiaomi.miplay.videoclient.sdk.MiplayVideoControl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiplayVideoControl.this.h = IMiPlayVideoClient.Stub.asInterface(iBinder);
            Log.i(MiplayVideoControl.a, "onServiceConnected: ");
            if (MiplayVideoControl.this.h != null) {
                try {
                    MiplayVideoControl.this.h.init(MiplayVideoControl.this.c, MiplayVideoControl.this.l, MiplayVideoControl.this.d);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(MiplayVideoControl.a, "onServiceDisconnected: ");
        }
    };
    private HandlerThread f = new HandlerThread(getClass().getName());
    private a e = new a(this.f.getLooper());

    public MiplayVideoControl(Context context) {
        this.b = context;
        this.c = context.getPackageName();
        this.f.start();
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
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiplayVideoControl.this.g.onInitSuccess();
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 1:
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiplayVideoControl.this.g.onInitError();
                            return;
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiplayVideoControl.this.g.onDeviceLost((String) message.obj);
                            return;
                        } catch (RemoteException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 3:
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiPlayDeviceControlCenter miPlayDeviceControlCenter = (MiPlayDeviceControlCenter) message.obj;
                            if (MiplayVideoControl.this.k.contains(miPlayDeviceControlCenter)) {
                                int indexOf = MiplayVideoControl.this.k.indexOf(miPlayDeviceControlCenter);
                                MiplayVideoControl.this.k.remove(miPlayDeviceControlCenter);
                                MiplayVideoControl.this.k.add(indexOf, miPlayDeviceControlCenter);
                            } else {
                                MiplayVideoControl.this.k.add(miPlayDeviceControlCenter);
                            }
                            MiplayVideoControl.this.g.onDeviceFoundControlCenter(miPlayDeviceControlCenter);
                            return;
                        } catch (RemoteException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 4:
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiPlayDeviceControlCenter miPlayDeviceControlCenter2 = (MiPlayDeviceControlCenter) message.obj;
                            if (MiplayVideoControl.this.k.contains(miPlayDeviceControlCenter2)) {
                                int indexOf2 = MiplayVideoControl.this.k.indexOf(miPlayDeviceControlCenter2);
                                MiplayVideoControl.this.k.remove(miPlayDeviceControlCenter2);
                                MiplayVideoControl.this.k.add(indexOf2, miPlayDeviceControlCenter2);
                            } else {
                                MiplayVideoControl.this.k.add(miPlayDeviceControlCenter2);
                            }
                            MiplayVideoControl.this.g.onDeviceUpdateControlCenter(miPlayDeviceControlCenter2);
                            return;
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 5:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onPositionAck((String) objArr[0], ((Long) objArr[1]).longValue());
                            return;
                        } catch (RemoteException e6) {
                            e6.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 6:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr2 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onPositionChange((String) objArr2[0], ((Long) objArr2[1]).longValue());
                            return;
                        } catch (RemoteException e7) {
                            e7.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 7:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr3 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onPlayStateAck((String) objArr3[0], ((Integer) objArr3[1]).intValue());
                            return;
                        } catch (RemoteException e8) {
                            e8.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 8:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr4 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onPlayStateChange((String) objArr4[0], ((Integer) objArr4[1]).intValue());
                            return;
                        } catch (RemoteException e9) {
                            e9.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 9:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr5 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onMediaInfoAck((String) objArr5[0], (MediaMetaData) objArr5[1]);
                            return;
                        } catch (RemoteException e10) {
                            e10.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 10:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr6 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onMediaInfoChange((String) objArr6[0], (MediaMetaData) objArr6[1]);
                            return;
                        } catch (RemoteException e11) {
                            e11.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 11:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr7 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onVolumeAck((String) objArr7[0], ((Double) objArr7[1]).doubleValue());
                            return;
                        } catch (RemoteException e12) {
                            e12.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 12:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr8 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onVolumeChange((String) objArr8[0], ((Double) objArr8[1]).doubleValue());
                            return;
                        } catch (RemoteException e13) {
                            e13.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 13:
                    if (MiplayVideoControl.this.g != null) {
                        Object[] objArr9 = (Object[]) message.obj;
                        try {
                            MiplayVideoControl.this.g.onBufferStateChange((String) objArr9[0], ((Integer) objArr9[1]).intValue());
                            return;
                        } catch (RemoteException e14) {
                            e14.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 14:
                    if (MiplayVideoControl.this.g != null) {
                        try {
                            MiplayVideoControl.this.g.onBeSeized((String) message.obj);
                            return;
                        } catch (RemoteException e15) {
                            e15.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                default:
                    Log.i(MiplayVideoControl.a, "unknown msg!");
                    return;
            }
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public boolean initAsync(MiplayVideoCallback miplayVideoCallback, String str, String str2) {
        boolean z;
        synchronized (this.i) {
            this.g = miplayVideoCallback;
            this.d = str;
            this.c = str2;
            String str3 = a;
            Log.i(str3, "initAsync: " + str2);
            Intent intent = new Intent();
            intent.setPackage(Constant.PACKAGENAME_SYSTEMUI);
            intent.setAction("COM.XIAOMI.MIPLAY.ACTION.VIDEO_CIRCULATION_CLIENT_SERVICE");
            int i = 1;
            if (Build.VERSION.SDK_INT >= 29) {
                i = 4097;
            }
            this.j = this.b.bindService(intent, this.m, i);
            String str4 = a;
            Log.i(str4, "mInited:" + this.j);
            z = this.j;
        }
        return z;
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public void unInit() {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient != null) {
            try {
                iMiPlayVideoClient.unInit(this.c);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.b.unbindService(this.m);
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public void startDiscovery(int i) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return;
        }
        if (!iMiPlayVideoClient.asBinder().pingBinder()) {
            initAsync(this.g, this.d, this.c);
            return;
        }
        try {
            this.k.clear();
            this.h.startDiscovery(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public void stopDiscovery() {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient != null) {
            try {
                iMiPlayVideoClient.stopDiscovery();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int play(String str, int i) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.play(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int stop(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.stop(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int pause(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.pause(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int resume(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.resume(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int seek(String str, long j) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.seek(str, j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int getPosition(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.getPosition(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int getPlayState(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.getPlayState(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int getMediaInfo(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.getMediaInfo(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int setVolume(String str, double d) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.setVolume(str, (int) d);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int getVolume(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.getVolume(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public boolean getCollectAudio() {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return false;
        }
        try {
            return iMiPlayVideoClient.getCollectAudio();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int next(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.next(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.videoclient.sdk.api.MiplayVideoClient
    public int prev(String str) {
        IMiPlayVideoClient iMiPlayVideoClient = this.h;
        if (iMiPlayVideoClient == null) {
            return -1;
        }
        try {
            return iMiPlayVideoClient.prev(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
