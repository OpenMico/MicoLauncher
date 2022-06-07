package com.xiaomi.miplay.audioclient.tv;

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
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient;
import com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService;
import com.xiaomi.miplay.mylibrary.utils.Constant;

/* loaded from: classes3.dex */
public class MiPlayTVClientImpl implements MiPlayTVClientInterface {
    private static final String a = "MiPlayTVClientImpl";
    private Context b;
    private IMiPlayTVClient c;
    private MiPlayTVClientCallback d;
    private String e;
    private Handler f = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.miplay.audioclient.tv.MiPlayTVClientImpl.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onInitSuccess();
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 1:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onInitError();
                            return;
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onPlayStateAck(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 3:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onPlayStateChange(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 4:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onPositionAck(((Long) message.obj).longValue());
                            return;
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 5:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onMediaInfoAck((TVMediaMetaData) message.obj);
                            return;
                        } catch (RemoteException e6) {
                            e6.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 6:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onMediaInfoChange((TVMediaMetaData) message.obj);
                            return;
                        } catch (RemoteException e7) {
                            e7.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 7:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onVolumeAck(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e8) {
                            e8.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 8:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onVolumeChange(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e9) {
                            e9.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 9:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onCmdSessionError();
                            return;
                        } catch (RemoteException e10) {
                            e10.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 10:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onCmdSessionSuccess();
                            return;
                        } catch (RemoteException e11) {
                            e11.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 11:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onSourceNameChange((String) message.obj);
                            return;
                        } catch (RemoteException e12) {
                            e12.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 12:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onDurationUpdated(((Long) message.obj).longValue());
                            return;
                        } catch (RemoteException e13) {
                            e13.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 13:
                    if (MiPlayTVClientImpl.this.d != null) {
                        try {
                            MiPlayTVClientImpl.this.d.onMirrorModeNotify(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e14) {
                            e14.printStackTrace();
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
    private MiPlayTVClientCallback g = new MiPlayTVClientCallback() { // from class: com.xiaomi.miplay.audioclient.tv.MiPlayTVClientImpl.2
        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onInitSuccess() {
            MiPlayTVClientImpl.this.f.obtainMessage(0).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onInitError() throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(1).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onCmdSessionError() throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(9).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onCmdSessionSuccess() throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(10).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPositionAck(long j) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(4, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPlayStateChange(int i) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(3, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPlayStateAck(int i) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(2, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(5, tVMediaMetaData).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(6, tVMediaMetaData).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onVolumeChange(int i) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(8, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onVolumeAck(int i) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(7, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onSourceNameChange(String str) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(11, str).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onDurationUpdated(long j) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(12, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMirrorModeNotify(int i) throws RemoteException {
            MiPlayTVClientImpl.this.f.obtainMessage(13, Integer.valueOf(i)).sendToTarget();
        }
    };
    private ServiceConnection h = new ServiceConnection() { // from class: com.xiaomi.miplay.audioclient.tv.MiPlayTVClientImpl.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiPlayTVClientImpl.this.c = IMiPlayTVClient.Stub.asInterface(iBinder);
            Log.i(MiPlayTVClientImpl.a, "onServiceConnected: ");
            if (MiPlayTVClientImpl.this.c != null) {
                try {
                    MiPlayTVClientImpl.this.c.init(MiPlayTVClientImpl.this.e, MiPlayTVClientImpl.this.g);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(MiPlayTVClientImpl.a, "onServiceDisconnected: ");
        }
    };

    public MiPlayTVClientImpl(Context context) {
        this.b = context;
        this.e = this.b.getPackageName();
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback) {
        this.d = miPlayTVClientCallback;
        Log.i(a, "initAsync: ");
        Intent intent = new Intent();
        intent.setPackage(Constant.PACKAGENAME_SMARTSHARE);
        intent.setAction(MiplayAudioTVService.ACTION_MIPLAY_TV_CLIENT);
        return this.b.bindService(intent, this.h, Build.VERSION.SDK_INT >= 29 ? 4097 : 1);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback, String str) {
        this.d = miPlayTVClientCallback;
        Log.i(a, "initAsync: ");
        Intent intent = new Intent();
        if (TextUtils.isEmpty(str)) {
            intent.setPackage(Constant.PACKAGENAME_SMARTSHARE);
        } else {
            intent.setPackage(str);
        }
        intent.setAction(MiplayAudioTVService.ACTION_MIPLAY_TV_CLIENT);
        int i = 1;
        if (Build.VERSION.SDK_INT >= 29) {
            i = 4097;
        }
        return this.b.bindService(intent, this.h, i);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public void unInit() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient != null) {
            try {
                iMiPlayTVClient.unInit(this.e);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.b.unbindService(this.h);
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int stop() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int onPause() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.onPause();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int onResume() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.onResume();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int onSeek(long j) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.onSeek(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setVolume(int i) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setVolume(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int getVolume() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.getVolume();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int getPlayState() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.getPlayState();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int getPosition() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int getMediaInfo() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.getMediaInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int onNext() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.onNext();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int onPrev() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.onPrev();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public String getSourceName() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return "";
        }
        try {
            return iMiPlayTVClient.getSourceName();
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int musicRelay(String str, int i) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.musicRelay(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setLocalDeviceInfo(String str) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setLocalDeviceInfo(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setLocalMediaInfo(tVMediaMetaData);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setLocalMediaState(int i) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setLocalMediaState(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int volumeControl() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.volumeControl();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setMiplayVolumeControl(int i) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setMiplayVolumeControl(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int audioFcControl() {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.audioFcControl();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.MiPlayTVClientInterface
    public int setMiplayAudioFcControl(int i) {
        IMiPlayTVClient iMiPlayTVClient = this.c;
        if (iMiPlayTVClient == null) {
            return -1;
        }
        try {
            return iMiPlayTVClient.setMiplayAudioFcControl(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
