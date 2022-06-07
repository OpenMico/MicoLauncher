package com.xiaomi.micolauncher.common.speech.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class MultiChannelAudioHandler {
    private VpmClient l;
    private BaseSpeechEngine m;
    private boolean n;
    private Handler o;
    private byte[] r;
    private boolean u;
    private String v;
    private final int a = 96;
    private final int b = ((int) TimeUnit.SECONDS.toMillis(20)) * 96;
    private final int c = this.b / 4;
    private final String e = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vpm/multi_asr/";
    private final int f = 1;
    private final int g = 2;
    private final int h = 3;
    private final int i = 4;
    private final int j = 5;
    private final int k = 6;
    private byte[] p = null;
    private byte[] q = null;
    private int t = 0;
    private int s = 0;
    private final int d = 2;

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
    }

    public void cacheSingAsr(byte[] bArr, int i, boolean z) {
    }

    public void start() {
    }

    public MultiChannelAudioHandler(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        this.l = vpmClient;
        this.m = baseSpeechEngine;
        a();
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$MultiChannelAudioHandler$T9VyXH46CJisdPXNZp6vtxrXFq8
            @Override // java.lang.Runnable
            public final void run() {
                MultiChannelAudioHandler.this.b();
            }
        });
    }

    private void a() {
        HandlerThread handlerThread = new HandlerThread("MultiChannelAudioHandler");
        handlerThread.start();
        this.o = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$MultiChannelAudioHandler$-IjrXRx00ue8C5u3ygMuodjUnhg
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a;
                a = MultiChannelAudioHandler.this.a(message);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        L.speech.d("%s msg = %d", "[MultiAudio]: ", Integer.valueOf(message.what));
        switch (message.what) {
            case 1:
                long currentTimeMillis = System.currentTimeMillis();
                this.n = false;
                this.t = 0;
                this.p = new byte[this.b];
                do {
                    int vpmReadMultiChsAsr = this.l.vpmReadMultiChsAsr(this.p, this.t, this.c);
                    if (vpmReadMultiChsAsr > 0) {
                        this.t += vpmReadMultiChsAsr;
                    }
                    if (this.t + this.c > this.b || vpmReadMultiChsAsr <= 0) {
                        L.speech.i("%s exit multi asr read(time_use=%dms),total_size(%d)=%dms", "[MultiAudio]: ", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Integer.valueOf(this.t), Integer.valueOf(this.t / 96));
                        break;
                    }
                } while (!c());
                L.speech.i("%s exit multi asr read(time_use=%dms),total_size(%d)=%dms", "[MultiAudio]: ", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Integer.valueOf(this.t), Integer.valueOf(this.t / 96));
                break;
            case 2:
                if (message.obj instanceof String) {
                    d(a((String) message.obj));
                    break;
                }
                break;
            case 3:
                f();
                break;
            case 4:
                if (message.arg1 > 0) {
                    this.q = new byte[message.arg1];
                    L.speech.i("%s req_size=%s, get_size=%s", "[MultiAudio]: ", Integer.valueOf(message.arg1), Integer.valueOf(this.l.vpmReadMultiChsWuw(this.q, 0, message.arg1)));
                    break;
                }
                break;
            case 5:
                if (message.obj instanceof String) {
                    String b = b((String) message.obj);
                    byte[] bArr = this.q;
                    a(b, bArr, bArr.length);
                    byte[] bArr2 = this.r;
                    if (bArr2 != null && bArr2.length > 0) {
                        String c = c((String) message.obj);
                        byte[] bArr3 = this.r;
                        a(c, bArr3, bArr3.length);
                        break;
                    }
                }
                break;
            case 6:
                boolean z = 1 == message.arg1;
                byte[] bArr4 = this.q;
                if (bArr4 != null && bArr4.length > 0) {
                    L.speech.d("%s uploadWuw.multi.size=%s realWakeup=%b", "[MultiAudio]: ", Integer.valueOf(this.q.length), Boolean.valueOf(z));
                    BaseSpeechEngine baseSpeechEngine = this.m;
                    byte[] bArr5 = this.q;
                    baseSpeechEngine.uploadMultiChannelWuw(bArr5, bArr5.length, this.v, z, null);
                    this.q = null;
                }
                byte[] bArr6 = this.r;
                if (bArr6 != null && bArr6.length > 0) {
                    L.speech.d("%s uploadWuw.single.size=%s realWakeup=%b", "[MultiAudio]: ", Integer.valueOf(this.r.length), Boolean.valueOf(z));
                    BaseSpeechEngine baseSpeechEngine2 = this.m;
                    byte[] bArr7 = this.r;
                    baseSpeechEngine2.uploadSingleChannelWuw(bArr7, bArr7.length, this.v, z, null);
                    this.r = null;
                    break;
                }
                break;
        }
        return false;
    }

    private String a(String str) {
        return String.format("%s%s_%s_asr.pcm", this.e, new SimpleDateFormat("YddHHmmss", Locale.getDefault()).format(new Date()), str);
    }

    private String b(String str) {
        return String.format("%s%s_%s_wuw.pcm", this.e, new SimpleDateFormat("YddHHmmss", Locale.getDefault()).format(new Date()), str);
    }

    private String c(String str) {
        return String.format("%s%s_%s_single_wuw.pcm", this.e, new SimpleDateFormat("YddHHmmss", Locale.getDefault()).format(new Date()), str);
    }

    public void interrupt() {
        this.o.removeMessages(1);
        this.n = true;
    }

    private boolean c() {
        return this.n;
    }

    public void startRead() {
        this.n = true;
        if (this.u) {
            this.o.removeMessages(1);
            this.o.sendEmptyMessage(1);
        }
    }

    public void startReadMultiWuw(int i) {
        L.speech.d("%s startReadMultiWuw", "[MultiAudio]: ");
        this.n = true;
        this.o.removeMessages(4);
        this.o.obtainMessage(4, i, 0).sendToTarget();
    }

    public void startSave(String str) {
        L.speech.d("%s startSave %s", "[MultiAudio]: ", str);
        this.n = true;
        this.v = str;
        if (this.u) {
            this.o.removeMessages(2);
            byte[] bArr = this.p;
            if (bArr != null && bArr.length > 0) {
                this.o.obtainMessage(2, str).sendToTarget();
            }
        }
    }

    public void cacheSingleWuw(byte[] bArr, int i) {
        if (bArr != null && bArr.length > 0) {
            this.r = (byte[]) bArr.clone();
        }
    }

    public void startUploadAsr() {
        this.o.removeMessages(3);
        this.o.sendEmptyMessage(3);
    }

    public void startUploadWuw() {
        startUploadWuw(1);
    }

    public void startUploadWuw(int i) {
        L.speech.d("%s startUploadWuw realWakeup=%d", "[MultiAudio]: ", Integer.valueOf(i));
        if (this.q != null) {
            this.o.removeMessages(6);
            this.o.obtainMessage(6, i, 0).sendToTarget();
        }
    }

    public void setEnable(boolean z) {
        this.u = z;
        if (z) {
            this.l.vpmEnableMultiAsr();
        } else {
            this.l.vpmDisableMultiAsr();
        }
    }

    private int d() {
        File file = new File(this.e);
        try {
            if (!file.exists()) {
                L.speech.w("%s getFileCnt no folder: %s", "[MultiAudio]: ", this.e);
                if (!file.mkdirs()) {
                    L.speech.e("%s getFileCnt mkdir error", "[MultiAudio]: ");
                }
                return 0;
            } else if (!file.isDirectory() || file.list() == null) {
                return 0;
            } else {
                return file.list().length;
            }
        } catch (SecurityException e) {
            L.speech.e("%getFileCnt.exception=", e);
            return 0;
        }
    }

    private String e() {
        File file = new File(this.e);
        try {
            if (!file.isDirectory() || file.list() == null || file.list().length <= 0) {
                return null;
            }
            return file.list()[0];
        } catch (SecurityException e) {
            L.speech.e("getFile.exception=", e);
            return null;
        }
    }

    private void d(String str) {
        int d = d();
        L.speech.d("%s doSaveAsrFile.fileCnt=%d, .fileName = %s, size=%d", "[MultiAudio]: ", Integer.valueOf(d), str, Integer.valueOf(this.t));
        if (this.t > 0) {
            new WavFileWriter(3, 16000, str).a(this.p, 0, this.t);
            this.t = 0;
            this.p = null;
        }
        if (d + 1 >= this.d) {
            setEnable(false);
        }
    }

    private void a(String str, byte[] bArr, int i) {
        if (i > 0) {
            new WavFileWriter(3, 16000, str).a(bArr, 0, i);
        }
    }

    private void f() {
        if (d() <= 0) {
            setEnable(true);
            return;
        }
        String e = e();
        if (!TextUtils.isEmpty(e)) {
            String str = this.e + e;
            String[] split = e.split("_");
            if (split.length > 1) {
                String str2 = split[1];
                try {
                    if (Files.size(Paths.get(str, new String[0])) < this.b) {
                        Files.readAllBytes(Paths.get(str, new String[0]));
                    }
                } catch (Exception e2) {
                    L.speech.e("%s doUpload.read.exception=%s", e2);
                }
            }
            try {
                Files.delete(Paths.get(str, new String[0]));
            } catch (Exception e3) {
                L.speech.e("doUpload.delete.exception=", e3);
            }
        }
    }
}
