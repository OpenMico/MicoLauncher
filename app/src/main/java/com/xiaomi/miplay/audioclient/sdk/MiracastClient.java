package com.xiaomi.miplay.audioclient.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miplay.audioclient.IMiPlayClient;
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDevice;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class MiracastClient implements MiPlayClientInterface {
    private static final String a = "MiracastClient";
    private Context b;
    private IMiPlayClient c;
    private MiPlayClientCallback f;
    private String g;
    private String h;
    private volatile boolean i;
    private final Object j = new Object();
    private Handler k = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.miplay.audioclient.sdk.MiracastClient.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onInitSuccess();
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 1:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onInitError();
                            return;
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiPlayDevice miPlayDevice = (MiPlayDevice) message.obj;
                            if (MiracastClient.this.d.contains(miPlayDevice)) {
                                int indexOf = MiracastClient.this.d.indexOf(miPlayDevice);
                                MiracastClient.this.d.remove(miPlayDevice);
                                MiracastClient.this.d.add(indexOf, miPlayDevice);
                            } else {
                                MiracastClient.this.d.add(miPlayDevice);
                            }
                            MiracastClient.this.f.onDeviceFound(miPlayDevice);
                            return;
                        } catch (RemoteException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 3:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiPlayDevice miPlayDevice2 = (MiPlayDevice) message.obj;
                            if (MiracastClient.this.d.contains(miPlayDevice2)) {
                                int indexOf2 = MiracastClient.this.d.indexOf(miPlayDevice2);
                                MiracastClient.this.d.remove(miPlayDevice2);
                                MiracastClient.this.d.add(indexOf2, miPlayDevice2);
                            } else {
                                MiracastClient.this.d.add(miPlayDevice2);
                            }
                            MiracastClient.this.f.onDeviceUpdate(miPlayDevice2);
                            return;
                        } catch (RemoteException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 4:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onDeviceLost((String) message.obj);
                            return;
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 5:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onPlayStateAck((Map) message.obj);
                            return;
                        } catch (RemoteException e6) {
                            e6.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 6:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onPlayStateChange((Map) message.obj);
                            return;
                        } catch (RemoteException e7) {
                            e7.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 7:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onPositionAck((Map) message.obj);
                            return;
                        } catch (RemoteException e8) {
                            e8.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 8:
                    if (MiracastClient.this.f != null) {
                        Object[] objArr = (Object[]) message.obj;
                        try {
                            MiracastClient.this.f.onDeviceConnectStateChange((String) objArr[0], ((Integer) objArr[1]).intValue());
                            return;
                        } catch (RemoteException e9) {
                            e9.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 9:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onMediaInfoAck((Map) message.obj);
                            return;
                        } catch (RemoteException e10) {
                            e10.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 10:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onMediaInfoChange((Map) message.obj);
                            return;
                        } catch (RemoteException e11) {
                            e11.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 11:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onChannelsAck((Map) message.obj);
                            return;
                        } catch (RemoteException e12) {
                            e12.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 12:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onVolumeAck((Map) message.obj);
                            return;
                        } catch (RemoteException e13) {
                            e13.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 13:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onVolumeChange((Map) message.obj);
                            return;
                        } catch (RemoteException e14) {
                            e14.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 14:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onBufferStateChange((Map) message.obj);
                            return;
                        } catch (RemoteException e15) {
                            e15.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 15:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onMirrorModeAck((Map) message.obj);
                            return;
                        } catch (RemoteException e16) {
                            e16.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 16:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onMirrorModeNotify((Map) message.obj);
                            return;
                        } catch (RemoteException e17) {
                            e17.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 17:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onBtFrequencyACK((Map) message.obj);
                            return;
                        } catch (RemoteException e18) {
                            e18.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 18:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onDisconnectNotify((Map) message.obj);
                            return;
                        } catch (RemoteException e19) {
                            e19.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 19:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onBeSeized((String) message.obj);
                            return;
                        } catch (RemoteException e20) {
                            e20.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 20:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiPlayDeviceControlCenter miPlayDeviceControlCenter = (MiPlayDeviceControlCenter) message.obj;
                            if (MiracastClient.this.e.contains(miPlayDeviceControlCenter)) {
                                int indexOf3 = MiracastClient.this.e.indexOf(miPlayDeviceControlCenter);
                                MiracastClient.this.e.remove(miPlayDeviceControlCenter);
                                MiracastClient.this.e.add(indexOf3, miPlayDeviceControlCenter);
                            } else {
                                MiracastClient.this.e.add(miPlayDeviceControlCenter);
                            }
                            MiracastClient.this.f.onDeviceFoundControlCenter(miPlayDeviceControlCenter);
                            return;
                        } catch (RemoteException e21) {
                            e21.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 21:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiPlayDeviceControlCenter miPlayDeviceControlCenter2 = (MiPlayDeviceControlCenter) message.obj;
                            if (MiracastClient.this.e.contains(miPlayDeviceControlCenter2)) {
                                int indexOf4 = MiracastClient.this.e.indexOf(miPlayDeviceControlCenter2);
                                MiracastClient.this.e.remove(miPlayDeviceControlCenter2);
                                MiracastClient.this.e.add(indexOf4, miPlayDeviceControlCenter2);
                            } else {
                                MiracastClient.this.e.add(miPlayDeviceControlCenter2);
                            }
                            MiracastClient.this.f.onDeviceUpdateControlCenter(miPlayDeviceControlCenter2);
                            return;
                        } catch (RemoteException e22) {
                            e22.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 22:
                    if (MiracastClient.this.f != null) {
                        try {
                            MiracastClient.this.f.onLocalMediaInfoChange((MediaMetaData) message.obj);
                            return;
                        } catch (RemoteException e23) {
                            e23.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private MiPlayClientCallback l = new MiPlayClientCallback() { // from class: com.xiaomi.miplay.audioclient.sdk.MiracastClient.2
        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onInitSuccess() {
            MiracastClient.this.k.obtainMessage(0).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onInitError() throws RemoteException {
            MiracastClient.this.k.obtainMessage(1).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceFound(MiPlayDevice miPlayDevice) {
            String str = MiracastClient.a;
            Log.d(str, "onDeviceFound: " + miPlayDevice);
            MiracastClient.this.k.obtainMessage(2, miPlayDevice).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceUpdate(MiPlayDevice miPlayDevice) throws RemoteException {
            MiracastClient.this.k.obtainMessage(3, miPlayDevice).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceLost(String str) {
            MiracastClient.this.k.obtainMessage(4, str).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onPositionAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(7, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceConnectStateChange(String str, int i) throws RemoteException {
            MiracastClient.this.k.obtainMessage(8, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onPlayStateChange(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(6, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onPlayStateAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(5, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onMediaInfoAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(9, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onMediaInfoChange(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(10, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onChannelsAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(11, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onVolumeChange(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(13, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onVolumeAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(12, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onBufferStateChange(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(14, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onMirrorModeAck(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(15, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onMirrorModeNotify(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(16, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onBtFrequencyACK(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(17, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDisconnectNotify(Map map) throws RemoteException {
            MiracastClient.this.k.obtainMessage(18, map).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onBeSeized(String str) throws RemoteException {
            MiracastClient.this.k.obtainMessage(19, str).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceFoundControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
            MiracastClient.this.k.obtainMessage(20, miPlayDeviceControlCenter).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onDeviceUpdateControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
            MiracastClient.this.k.obtainMessage(21, miPlayDeviceControlCenter).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClientCallback
        public void onLocalMediaInfoChange(MediaMetaData mediaMetaData) throws RemoteException {
            MiracastClient.this.k.obtainMessage(22, mediaMetaData).sendToTarget();
        }
    };
    private ServiceConnection m = new ServiceConnection() { // from class: com.xiaomi.miplay.audioclient.sdk.MiracastClient.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiracastClient.this.c = IMiPlayClient.Stub.asInterface(iBinder);
            Log.i(MiracastClient.a, "onServiceConnected: ");
            if (MiracastClient.this.c != null) {
                try {
                    MiracastClient.this.c.init(MiracastClient.this.g, MiracastClient.this.l, MiracastClient.this.h);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(MiracastClient.a, "onServiceDisconnected: ");
        }
    };
    private List<MiPlayDevice> d = new ArrayList();
    private List<MiPlayDeviceControlCenter> e = new ArrayList();

    public MiracastClient(Context context) {
        this.b = context;
        this.g = this.b.getPackageName();
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str) {
        boolean z;
        Log.i(a, "initAsync: ");
        synchronized (this.j) {
            this.f = miPlayClientCallback;
            this.h = str;
            Intent intent = new Intent();
            intent.setPackage(Constant.PACKAGENAME_SYSTEMUI);
            intent.setAction(MiPlayAudioService.ACTION_MIPLAY_CLIENT);
            int i = 1;
            if (Build.VERSION.SDK_INT >= 29) {
                i = 4097;
            }
            this.i = this.b.bindService(intent, this.m, i);
            String str2 = a;
            Log.i(str2, "mInited:" + this.i);
            z = this.i;
        }
        return z;
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str, String str2) {
        boolean z;
        synchronized (this.j) {
            this.f = miPlayClientCallback;
            this.h = str;
            this.g = str2;
            String str3 = a;
            Log.i(str3, "initAsync: " + str2);
            Intent intent = new Intent();
            intent.setPackage(Constant.PACKAGENAME_SYSTEMUI);
            intent.setAction(MiPlayAudioService.ACTION_MIPLAY_CLIENT);
            int i = 1;
            if (Build.VERSION.SDK_INT >= 29) {
                i = 4097;
            }
            this.i = this.b.bindService(intent, this.m, i);
            String str4 = a;
            Log.i(str4, "mInited:" + this.i);
            z = this.i;
        }
        return z;
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public boolean isDiscovering() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return false;
        }
        try {
            return iMiPlayClient.isDiscovering();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public void startDiscovery(int i) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return;
        }
        if (!iMiPlayClient.asBinder().pingBinder()) {
            initAsync(this.f, this.h);
            return;
        }
        try {
            this.d.clear();
            this.e.clear();
            this.c.startDiscovery(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public void stopDiscovery() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient != null) {
            try {
                iMiPlayClient.stopDiscovery();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public void unInit() {
        synchronized (this.j) {
            if (this.c != null) {
                try {
                    this.c.unInit(this.g);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.b.unbindService(this.m);
                this.i = false;
            }
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onPlay(String[] strArr, String str) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onPlay(strArr, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int stop(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.stop(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int closeDevice(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.closeDevice(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onPause(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onPause(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onResume(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onResume(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onSeek(String[] strArr, long j) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onSeek(strArr, j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setVolume(String[] strArr, int i) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setVolume(strArr, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getVolume(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getVolume(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getPlayState(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getPlayState(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getPosition(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getPosition(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setMediaInfo(strArr, mediaMetaData);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getMediaInfo(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getMediaInfo(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setChannel(String str, int i) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setChannel(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getChannel(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getChannel(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onTimelineChange() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onTimelineChange();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public List<MiPlayDevice> getDevices() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return null;
        }
        try {
            return iMiPlayClient.getDevices();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public boolean getCollectAudio() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return false;
        }
        try {
            return iMiPlayClient.getCollectAudio();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onNext(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onNext(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onPrev(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onPrev(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onRefreshDeviceInfo() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.onRefreshDeviceInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setBtFrequency(String[] strArr, int i) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setBtFrequency(strArr, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int getBtFrequency(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return -1;
        }
        try {
            return iMiPlayClient.getBtFrequency(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int onPlay(String[] strArr, String str, int i) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.play(strArr, str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setBoxPause(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setBoxPause(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public int setBoxResume(String[] strArr) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null || strArr == null) {
            return -1;
        }
        try {
            return iMiPlayClient.setBoxResume(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public void stopUwbDiscovery() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient != null) {
            try {
                iMiPlayClient.stopUwbDiscovery();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public List<MiPlayDeviceControlCenter> getStereoDevices(String str) {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return null;
        }
        try {
            return iMiPlayClient.getStereoDevices(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.sdk.MiPlayClientInterface
    public MediaMetaData getLocalMediaInfo() {
        IMiPlayClient iMiPlayClient = this.c;
        if (iMiPlayClient == null) {
            return null;
        }
        try {
            return iMiPlayClient.getLocalMediaInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isInited() {
        return this.i;
    }
}
