package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class MultiMirrorControl {
    public static final int MULTI_DISPLAY_INFO_AUDIOCAPTURE_INITDONE = 111001;
    public static final int MULTI_DISPLAY_INFO_CONNECTED = 110002;
    public static final int MULTI_DISPLAY_INFO_DISCONNECTED = 110003;
    public static final int MULTI_DISPLAY_INFO_PREPARED = 110001;
    private static String a = "MultiMirrorControl";
    private static Context l;
    private Options b;
    private CaptureService c;
    private RemoteDisplayAdmin.Listener d;
    private AtomicBoolean e = new AtomicBoolean(false);
    private long f = 0;
    private final Object i = new Object();
    private final Object j = new Object();
    private Lock k = new ReentrantLock();
    private HandlerThread g = new HandlerThread("HandlerThread");
    private a h = new a(this.g.getLooper());

    public native int addMirror(long j, Object obj, String str, int i);

    public native int addMirror2(long j, Object obj, String str, int i, long j2);

    public native int closeMultiMirror(long j);

    public native long createMultiMirror(Object obj, String str, int i);

    public native long createMultiMirror2(Object obj, String str, int i, long j);

    public native int deleteMirror(long j, int i);

    public native int getMirrorNums(long j);

    public native boolean pauseMirror(long j, int i);

    public native boolean resumeMirror(long j, int i);

    public native void setMirrorAudioSamplerate(int i);

    public native int startMirror(long j, int i);

    public native boolean startMultiMirror(long j);

    public native int switchSource(long j, long j2);

    public native boolean writeData(long j, boolean z, byte[] bArr, int i, long j2);

    static {
        try {
            System.loadLibrary("audiomirror-jni");
        } catch (Exception e) {
            Log.e(a, "Failed to load audiomirror-jni.so while initializing exception was ", e);
        }
    }

    private MultiMirrorControl(Options options, CaptureService captureService, RemoteDisplayAdmin.Listener listener) throws IOException {
        this.b = options;
        this.c = captureService;
        this.d = listener;
        this.g.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            long j = -1;
            switch (message.what) {
                case 0:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_START_MIRROR");
                    Object[] objArr = (Object[]) message.obj;
                    Object obj = objArr[0];
                    Map map = (Map) objArr[1];
                    String str = MultiMirrorControl.a;
                    Log.d(str, "@@@@@@ lab:" + map.size());
                    String[] split = MultiMirrorControl.this.b.getMultiPort().split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                    int length = split.length;
                    for (int i = 0; i < length; i++) {
                        String str2 = split[i];
                        if (map != null) {
                            try {
                                if (map.size() > 0) {
                                    String str3 = (String) map.get(Integer.valueOf(Integer.parseInt(str2)));
                                    if (!TextUtils.isEmpty(str3)) {
                                        j = Long.parseLong(str3.replace(Constants.COLON_SEPARATOR, ""), 16);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (MultiMirrorControl.this.f != 0) {
                            MultiMirrorControl multiMirrorControl = MultiMirrorControl.this;
                            length = length;
                            split = split;
                            multiMirrorControl.addMirror2(multiMirrorControl.f, obj, MultiMirrorControl.this.b.getIp(), Integer.parseInt(str2), j);
                        } else {
                            length = length;
                            split = split;
                            MultiMirrorControl multiMirrorControl2 = MultiMirrorControl.this;
                            multiMirrorControl2.f = multiMirrorControl2.createMultiMirror2(obj, multiMirrorControl2.b.getIp(), Integer.parseInt(str2), j);
                        }
                    }
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl3 = MultiMirrorControl.this;
                        multiMirrorControl3.startMultiMirror(multiMirrorControl3.f);
                        return;
                    }
                    return;
                case 1:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_CLOSE_MIRROR");
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl4 = MultiMirrorControl.this;
                        multiMirrorControl4.closeMultiMirror(multiMirrorControl4.f);
                        MultiMirrorControl.this.f = 0L;
                    }
                    if (MultiMirrorControl.this.e.get()) {
                        Log.i(MultiMirrorControl.a, "MSG_CLOSE_MIRROR end!");
                        MultiMirrorControl.this.e.set(false);
                    }
                    MultiMirrorControl.this.h.removeCallbacksAndMessages(null);
                    MultiMirrorControl.this.g.quitSafely();
                    return;
                case 2:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_PAUSE_MIRROR");
                    int i2 = message.arg1;
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl5 = MultiMirrorControl.this;
                        multiMirrorControl5.pauseMirror(multiMirrorControl5.f, i2);
                        return;
                    }
                    return;
                case 3:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_RESUME_MIRROR");
                    int i3 = message.arg1;
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl6 = MultiMirrorControl.this;
                        multiMirrorControl6.resumeMirror(multiMirrorControl6.f, i3);
                        return;
                    }
                    return;
                case 4:
                    b bVar = (b) message.obj;
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl7 = MultiMirrorControl.this;
                        multiMirrorControl7.writeData(multiMirrorControl7.f, bVar.a(), bVar.b(), bVar.b().length, bVar.c());
                        return;
                    }
                    return;
                case 5:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_ADD_MIRROR");
                    Object[] objArr2 = (Object[]) message.obj;
                    Object obj2 = objArr2[0];
                    int i4 = message.arg1;
                    if (MultiMirrorControl.this.f != 0) {
                        try {
                            Map map2 = (Map) objArr2[1];
                            String str4 = MultiMirrorControl.a;
                            Log.d(str4, "@@@@@@ lab:" + map2.size());
                            if (map2 != null && map2.size() > 0) {
                                String str5 = (String) map2.get(Integer.valueOf(i4));
                                if (!TextUtils.isEmpty(str5)) {
                                    j = Long.parseLong(str5.replace(Constants.COLON_SEPARATOR, ""), 16);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        MultiMirrorControl multiMirrorControl8 = MultiMirrorControl.this;
                        int addMirror2 = multiMirrorControl8.addMirror2(multiMirrorControl8.f, obj2, MultiMirrorControl.this.b.getIp(), i4, j);
                        MultiMirrorControl multiMirrorControl9 = MultiMirrorControl.this;
                        multiMirrorControl9.startMirror(multiMirrorControl9.f, addMirror2);
                        return;
                    }
                    return;
                case 6:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_DELETE_MIRROR");
                    int i5 = message.arg1;
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl10 = MultiMirrorControl.this;
                        multiMirrorControl10.deleteMirror(multiMirrorControl10.f, i5);
                        return;
                    }
                    return;
                case 7:
                    Log.d(MultiMirrorControl.a, "handleMessage MSG_SWITCH_SOURCE");
                    long longValue = ((Long) message.obj).longValue();
                    if (MultiMirrorControl.this.f != 0) {
                        MultiMirrorControl multiMirrorControl11 = MultiMirrorControl.this;
                        multiMirrorControl11.switchSource(multiMirrorControl11.f, longValue);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static byte[] toUtf8String(String str) {
        byte[] bArr = new byte[str.length() + 1];
        for (int i = 0; i < str.length(); i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        bArr[str.length()] = 0;
        Log.i(a, "buf len:" + bArr.length);
        return bArr;
    }

    public boolean startMultiMirror(String str) {
        String str2 = a;
        Log.i(str2, "start multiMirror ip:" + toUtf8String(this.b.getIp()) + " port:" + str);
        this.b.setMultiPort(str);
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = new Object[]{this, -1L};
        this.h.sendMessage(obtain);
        return true;
    }

    public boolean startMultiMirror(String str, Map<Integer, String> map) {
        String str2 = a;
        Log.i(str2, "start multiMirror ip:" + toUtf8String(this.b.getIp()) + " port:" + str);
        this.b.setMultiPort(str);
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = new Object[]{this, map};
        this.h.sendMessage(obtain);
        return true;
    }

    public boolean addMirror(String str) {
        String str2 = a;
        Log.i(str2, "addMirror ip:" + toUtf8String(this.b.getIp()) + " port:" + str);
        if (this.f == 0) {
            return startMultiMirror(str);
        }
        Message obtain = Message.obtain();
        obtain.what = 5;
        obtain.obj = new Object[]{this, -1L};
        obtain.arg1 = Integer.parseInt(str);
        this.h.sendMessage(obtain);
        return true;
    }

    public boolean addMirror(String str, Map<Integer, String> map) {
        String str2 = a;
        Log.i(str2, "addMirror ip:" + toUtf8String(this.b.getIp()) + " port:" + str);
        if (this.f == 0) {
            return startMultiMirror(str, map);
        }
        Message obtain = Message.obtain();
        obtain.what = 5;
        obtain.obj = new Object[]{this, map};
        if (!TextUtils.isEmpty(str)) {
            obtain.arg1 = Integer.parseInt(str);
            this.h.sendMessage(obtain);
        }
        return true;
    }

    public boolean addMirror2(String str, Map<Integer, String> map) {
        String str2 = a;
        Log.i(str2, "addMirror2 ip:" + toUtf8String(this.b.getIp()) + " port:" + str);
        if (this.f == 0) {
            return startMultiMirror(str, map);
        }
        Message obtain = Message.obtain();
        obtain.what = 5;
        obtain.obj = new Object[]{this, map};
        if (!TextUtils.isEmpty(str)) {
            obtain.arg1 = Integer.parseInt(str);
            this.h.sendMessage(obtain);
        }
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.j) {
            try {
                this.j.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (System.currentTimeMillis() - currentTimeMillis >= 1000) {
            Log.w(a, "addMirror2 timeout...");
            return false;
        }
        Log.i(a, "addMirror2 done");
        return true;
    }

    public boolean deleteMirror(int i) {
        String str = a;
        Log.i(str, "deleteMirror index:" + i);
        Message obtain = Message.obtain();
        obtain.what = 6;
        obtain.arg1 = i;
        this.h.sendMessage(obtain);
        return true;
    }

    public static MultiMirrorControl open(Options options, CaptureService captureService, RemoteDisplayAdmin.Listener listener, Context context) throws IOException {
        MultiMirrorControl multiMirrorControl = new MultiMirrorControl(options, captureService, listener);
        multiMirrorControl.startMultiMirror(options.getMultiPort());
        l = context;
        return multiMirrorControl;
    }

    public static MultiMirrorControl open(Options options, CaptureService captureService, RemoteDisplayAdmin.Listener listener, Context context, Map<Integer, String> map) throws IOException {
        MultiMirrorControl multiMirrorControl = new MultiMirrorControl(options, captureService, listener);
        multiMirrorControl.startMultiMirror(options.getMultiPort(), map);
        l = context;
        return multiMirrorControl;
    }

    public void closeMirror() {
        Log.i(a, "close mirror");
        this.h.sendEmptyMessage(1);
        synchronized (this.j) {
            this.j.notify();
        }
    }

    public boolean switchSource(long j) {
        String str = a;
        Log.i(str, "switchSource pos:" + j);
        Message obtain = Message.obtain();
        obtain.what = 7;
        obtain.obj = Long.valueOf(j);
        this.h.sendMessage(obtain);
        return true;
    }

    public void pause(int i) {
        Log.i(a, "pause mirror");
        long j = this.f;
        if (j != 0) {
            pauseMirror(j, i);
        }
    }

    public void resume(int i) {
        Log.i(a, "resume mirror");
        long j = this.f;
        if (j != 0) {
            resumeMirror(j, i);
        }
    }

    /* loaded from: classes4.dex */
    private class b {
        private boolean a;
        private byte[] b;
        private long c;

        public boolean a() {
            return this.a;
        }

        public byte[] b() {
            return this.b;
        }

        public long c() {
            return this.c;
        }
    }

    public void WriteStream(boolean z, byte[] bArr, long j) {
        long j2 = this.f;
        if (j2 != 0) {
            writeData(j2, z, bArr, bArr.length, j);
        }
    }

    public int getMirrorNums() {
        long j = this.f;
        if (j != 0) {
            return getMirrorNums(j);
        }
        return -1;
    }

    public int initAudioCapture(int i, int i2, int i3) {
        String str = a;
        Log.i(str, "init audio capture: channel=" + i + ", bits=" + i2 + ", samplerate=" + i3);
        this.k.lock();
        this.b.setChannel(i);
        this.b.setAudioBits(i2);
        this.b.setSampleRate(i3);
        this.k.unlock();
        return 0;
    }

    public int startAudioCapture() {
        Log.i(a, "start audio capture");
        this.k.lock();
        if (!this.e.get()) {
            this.e.set(true);
            this.c.runMultiCaptureAudio(this, this.b);
        }
        this.k.unlock();
        return 0;
    }

    public int pauseAudioCapture(int i) {
        Log.i(a, "pause audio capture");
        this.k.lock();
        CaptureService captureService = this.c;
        if (captureService != null) {
            captureService.PauseCaptureAudio(i);
        }
        this.k.unlock();
        return 0;
    }

    public int resumeAudioCapture(int i) {
        Log.i(a, "resume audio capture");
        this.k.lock();
        CaptureService captureService = this.c;
        if (captureService != null) {
            captureService.ResumeCaptureAudio(i);
        }
        this.k.unlock();
        return 0;
    }

    public int stopAudioCapture() {
        Log.i(a, "stop audio capture");
        this.k.lock();
        CaptureService captureService = this.c;
        if (captureService != null) {
            captureService.stopCapture();
        }
        synchronized (this.i) {
            this.i.notify();
            Log.i(a, "mControlStop notify");
        }
        Log.i(a, "stopAudio stopCapture end");
        this.k.unlock();
        this.e.set(false);
        Log.i(a, "stopAudio end");
        return 0;
    }

    public void onMultiDisplayError(int i, int i2, int i3) {
        String str = a;
        Log.i(str, "onMultiDisplayError what:" + i + " index:" + i2);
        RemoteDisplayAdmin.Listener listener = this.d;
        if (listener != null) {
            listener.onMultiDisplayError(i, i2, i3);
        }
    }

    public static String ipInt2Str(int i) {
        int i2 = i >> 24;
        if (i2 < 0) {
            i2 = i2 + 255 + 1;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i2);
        stringBuffer.append(".");
        stringBuffer.append((i >> 16) & 255);
        stringBuffer.append(".");
        stringBuffer.append((i >> 8) & 255);
        stringBuffer.append(".");
        stringBuffer.append(i & 255);
        return stringBuffer.toString();
    }

    public void onMultiDisplayInfo(int i, int i2, int i3) {
        String str = a;
        Logger.d(str, "onMultiDisplayInfo:" + i, new Object[0]);
        if (this.d == null) {
            return;
        }
        if (i == 110001) {
            String str2 = a;
            Log.i(str2, "MULTI_DISPLAY_INFO_PREPARED index:" + i2 + " port:" + i3);
            this.d.onMultiDisplayPrepared(i2, i3);
            synchronized (this.j) {
                this.j.notify();
            }
        } else if (i == 110002) {
            String ipInt2Str = ipInt2Str(i3);
            String str3 = a;
            Log.i(str3, "MULTI_DISPLAY_INFO_CONNECTED index:" + i2 + " ip:" + ipInt2Str);
            this.d.onMultiDisplayConnected(i2, ipInt2Str);
        } else if (i == 110003) {
            String str4 = a;
            Log.i(str4, "MULTI_DISPLAY_INFO_DISCONNECTED index:" + i2);
            this.d.onMultiDisplayDisConnected(i2);
        } else if (i == 111001) {
            Log.i(a, "MULTI_DISPLAY_INFO_AUDIOCAPTURE_INITDONE");
            this.d.onMultiDisplayAudioCaptureInitDone(i2);
        } else {
            String str5 = a;
            Log.i(str5, "onMultiDisplayInfo what:" + i + " index:" + i2);
            this.d.onMultiDisplayInfo(i, i2, i3);
        }
    }
}
