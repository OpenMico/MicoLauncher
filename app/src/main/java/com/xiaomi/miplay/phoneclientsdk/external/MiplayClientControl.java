package com.xiaomi.miplay.phoneclientsdk.external;

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
import com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient;
import com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient;
import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;

/* loaded from: classes4.dex */
public class MiplayClientControl implements IMiplayClient {
    private static final String a = "MiplayClientControl";
    private Context b;
    private String c;
    private MiplayClientCallback f;
    private IMiPlayExternalClient g;
    private MiplayClientCallback h = new MiplayClientCallback() { // from class: com.xiaomi.miplay.phoneclientsdk.external.MiplayClientControl.1
        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onInitSuccess() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(0).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onInitError() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(1).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPositionChanged(long j) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(2, Long.valueOf(j)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPlayed() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(3).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onStopped(int i) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(4, Integer.valueOf(i)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onNotifyPropertiesInfo(String str) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(5, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onNext() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(6).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPrev() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(7).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateStart() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(8).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateEnd() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(9).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPaused() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(10).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onResumed() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(11).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onSeekedTo(long j) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(12, Long.valueOf(j)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateFail(String str) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(13, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onSeekDoneNotify() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(14).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onVolumeChange(double d) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(15, Double.valueOf(d)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onCirculateModeChange(int i) throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(16, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onConnectMirrorSuccess() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(17).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onBuffering() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(18).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onResumeMirrorSuccess() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(19).sendToTarget();
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onResumeMirrorFail() throws RemoteException {
            MiplayClientControl.this.d.obtainMessage(20).sendToTarget();
        }
    };
    private ServiceConnection i = new ServiceConnection() { // from class: com.xiaomi.miplay.phoneclientsdk.external.MiplayClientControl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiplayClientControl.this.g = IMiPlayExternalClient.Stub.asInterface(iBinder);
            Log.i(MiplayClientControl.a, "onServiceConnected: ");
            if (MiplayClientControl.this.g != null) {
                try {
                    MiplayClientControl.this.g.initAsync(MiplayClientControl.this.c, MiplayClientControl.this.h);
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
                            MiplayClientControl.this.f.onPositionChanged(((Long) message.obj).longValue());
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
                            MiplayClientControl.this.f.onPlayed();
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
                            MiplayClientControl.this.f.onStopped(((Integer) message.obj).intValue());
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
                            MiplayClientControl.this.f.onNotifyPropertiesInfo((String) message.obj);
                            return;
                        } catch (RemoteException e6) {
                            e6.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 6:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onNext();
                            return;
                        } catch (RemoteException e7) {
                            e7.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 7:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onPrev();
                            return;
                        } catch (RemoteException e8) {
                            e8.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 8:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onCirculateStart();
                            return;
                        } catch (RemoteException e9) {
                            e9.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 9:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onCirculateEnd();
                            return;
                        } catch (RemoteException e10) {
                            e10.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 10:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onPaused();
                            return;
                        } catch (RemoteException e11) {
                            e11.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 11:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onResumed();
                            return;
                        } catch (RemoteException e12) {
                            e12.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 12:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onSeekedTo(((Long) message.obj).longValue());
                            return;
                        } catch (RemoteException e13) {
                            e13.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 13:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onCirculateFail((String) message.obj);
                            return;
                        } catch (RemoteException e14) {
                            e14.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 14:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onSeekDoneNotify();
                            return;
                        } catch (RemoteException e15) {
                            e15.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 15:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onVolumeChange(((Double) message.obj).doubleValue());
                            return;
                        } catch (RemoteException e16) {
                            e16.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 16:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onCirculateModeChange(((Integer) message.obj).intValue());
                            return;
                        } catch (RemoteException e17) {
                            e17.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 17:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onConnectMirrorSuccess();
                            return;
                        } catch (RemoteException e18) {
                            e18.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 18:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onBuffering();
                            return;
                        } catch (RemoteException e19) {
                            e19.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 19:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onResumeMirrorSuccess();
                            return;
                        } catch (RemoteException e20) {
                            e20.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 20:
                    if (MiplayClientControl.this.f != null) {
                        try {
                            MiplayClientControl.this.f.onResumeMirrorFail();
                            return;
                        } catch (RemoteException e21) {
                            e21.printStackTrace();
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

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public boolean initAsync(MiplayClientCallback miplayClientCallback) {
        this.f = miplayClientCallback;
        Log.i(a, "initAsync: ");
        Intent intent = new Intent();
        intent.setPackage(Constant.PACKAGENAME_SYSTEMUI);
        intent.setAction("COM.XIAOMI.MIPLAY.ACTION.EXTERNAL_CIRCULATION_CLIENT_SERVICE");
        return this.b.bindService(intent, this.i, Build.VERSION.SDK_INT >= 29 ? 4097 : 1);
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public void unInit() {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient != null) {
            try {
                iMiPlayExternalClient.unInit(this.c);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.b.unbindService(this.i);
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int play(String str, MediaMetaData mediaMetaData) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.play(str, mediaMetaData);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int playStateChange(String str, int i) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.playState(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int onBufferStateChanged(String str, int i) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.onBufferState(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.sendPropertiesInfo(str, propertiesInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int stop(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.stop(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int pause(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.pause(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int resume(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.resume(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int seek(String str, long j) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.seek(str, j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int setVolume(String str, double d) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.setVolume(str, d);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int getPosition(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.getPosition(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int getVolume(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.getVolume(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public String getSourceName(String str) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return "";
        }
        try {
            return iMiPlayExternalClient.getSourceName(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int getCirculateMode() {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.getCirculateMode();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.phoneclientsdk.external.api.IMiplayClient
    public int cancelCirculate(String str, int i) {
        IMiPlayExternalClient iMiPlayExternalClient = this.g;
        if (iMiPlayExternalClient == null) {
            return -1;
        }
        try {
            return iMiPlayExternalClient.cancelCirculate(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
