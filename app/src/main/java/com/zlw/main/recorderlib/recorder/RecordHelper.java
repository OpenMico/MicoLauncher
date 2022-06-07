package com.zlw.main.recorderlib.recorder;

import android.media.AudioRecord;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.listener.RecordDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.recorder.mp3.Mp3EncodeThread;
import com.zlw.main.recorderlib.recorder.wav.WavUtils;
import com.zlw.main.recorderlib.utils.ByteUtils;
import com.zlw.main.recorderlib.utils.FileUtils;
import com.zlw.main.recorderlib.utils.Logger;
import fftlib.FftFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class RecordHelper {
    private static final String a = "RecordHelper";
    private static volatile RecordHelper b;
    private RecordStateListener d;
    private RecordDataListener e;
    private RecordSoundSizeListener f;
    private RecordResultListener g;
    private RecordFftDataListener h;
    private RecordConfig i;
    private int j;
    private a k;
    private Mp3EncodeThread p;
    private volatile RecordState c = RecordState.IDLE;
    private Handler l = new Handler(Looper.getMainLooper());
    private File m = null;
    private File n = null;
    private List<File> o = new ArrayList();
    private FftFactory q = new FftFactory(FftFactory.Level.Original);

    /* loaded from: classes4.dex */
    public enum RecordState {
        IDLE,
        RECORDING,
        PAUSE,
        STOP,
        FINISH
    }

    private RecordHelper() {
    }

    public static RecordHelper getInstance() {
        if (b == null) {
            synchronized (RecordHelper.class) {
                if (b == null) {
                    b = new RecordHelper();
                }
            }
        }
        return b;
    }

    public RecordState getState() {
        return this.c;
    }

    public void a(RecordStateListener recordStateListener) {
        this.d = recordStateListener;
    }

    public void a(RecordDataListener recordDataListener) {
        this.e = recordDataListener;
    }

    public void a(RecordSoundSizeListener recordSoundSizeListener) {
        this.f = recordSoundSizeListener;
    }

    public void a(RecordResultListener recordResultListener) {
        this.g = recordResultListener;
    }

    public void setRecordFftDataListener(RecordFftDataListener recordFftDataListener) {
        this.h = recordFftDataListener;
    }

    public void setCurrentConfig(RecordConfig recordConfig) {
        this.i = recordConfig;
        this.j = AudioRecord.getMinBufferSize(recordConfig.getSampleRate(), recordConfig.getChannelConfig(), recordConfig.getEncodingConfig()) * 1;
    }

    public void start(String str) {
        if (this.i == null) {
            throw new IllegalArgumentException("RecordHelper should set RecordConfig before start record");
        } else if (this.c == RecordState.IDLE || this.c == RecordState.STOP) {
            this.m = new File(str);
            String j = j();
            Logger.d(a, "----------------开始录制 %s------------------------", this.i.getFormat().name());
            Logger.d(a, "参数： %s", this.i.toString());
            Logger.i(a, "pcm缓存 tmpFile: %s", j);
            Logger.i(a, "录音文件 resultFile: %s", str);
            this.n = new File(j);
            this.k = new a();
            this.k.start();
        } else {
            Logger.e(a, "状态异常当前状态： %s", this.c.name());
        }
    }

    public void stop() {
        if (this.c == RecordState.IDLE) {
            Logger.e(a, "状态异常当前状态： %s", this.c.name());
        } else if (this.c == RecordState.PAUSE) {
            g();
            this.c = RecordState.IDLE;
            d();
            f();
        } else {
            this.c = RecordState.STOP;
            d();
        }
    }

    public void a() {
        if (this.c != RecordState.RECORDING) {
            Logger.e(a, "状态异常当前状态： %s", this.c.name());
            return;
        }
        this.c = RecordState.PAUSE;
        d();
    }

    public void b() {
        if (this.c != RecordState.PAUSE) {
            Logger.e(a, "状态异常当前状态： %s", this.c.name());
            return;
        }
        String j = j();
        Logger.i(a, "tmpPCM File: %s", j);
        this.n = new File(j);
        this.k = new a();
        this.k.start();
    }

    public void d() {
        RecordSoundSizeListener recordSoundSizeListener;
        if (this.d != null) {
            this.l.post(new Runnable() { // from class: com.zlw.main.recorderlib.recorder.RecordHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    RecordHelper.this.d.onStateChange(RecordHelper.this.c);
                }
            });
            if ((this.c == RecordState.STOP || this.c == RecordState.PAUSE) && (recordSoundSizeListener = this.f) != null) {
                recordSoundSizeListener.onSoundSize(0);
            }
        }
    }

    public void e() {
        Logger.d(a, "录音结束 file: %s", this.m.getAbsolutePath());
        this.l.post(new Runnable() { // from class: com.zlw.main.recorderlib.recorder.RecordHelper.2
            @Override // java.lang.Runnable
            public void run() {
                if (RecordHelper.this.d != null) {
                    RecordHelper.this.d.onStateChange(RecordState.FINISH);
                }
                if (RecordHelper.this.g != null) {
                    RecordHelper.this.g.onResult(RecordHelper.this.m);
                }
            }
        });
    }

    public void a(final String str) {
        if (this.d != null) {
            this.l.post(new Runnable() { // from class: com.zlw.main.recorderlib.recorder.RecordHelper.3
                @Override // java.lang.Runnable
                public void run() {
                    RecordHelper.this.d.onError(str);
                }
            });
        }
    }

    public void a(final byte[] bArr) {
        if (this.e != null || this.f != null || this.h != null) {
            this.l.post(new Runnable() { // from class: com.zlw.main.recorderlib.recorder.RecordHelper.4
                @Override // java.lang.Runnable
                public void run() {
                    byte[] makeFftData;
                    if (RecordHelper.this.e != null) {
                        RecordHelper.this.e.onData(bArr);
                    }
                    if ((RecordHelper.this.h != null || RecordHelper.this.f != null) && (makeFftData = RecordHelper.this.q.makeFftData(bArr)) != null) {
                        if (RecordHelper.this.f != null) {
                            RecordHelper.this.f.onSoundSize(RecordHelper.this.b(makeFftData));
                        }
                        if (RecordHelper.this.h != null) {
                            RecordHelper.this.h.onFftData(makeFftData);
                        }
                    }
                }
            });
        }
    }

    public int b(byte[] bArr) {
        int i = 128;
        if (bArr.length <= 128) {
            i = bArr.length;
        }
        double d = 0.0d;
        for (int i2 = 8; i2 < i; i2++) {
            d += bArr[i2];
        }
        int log10 = (int) (Math.log10(((d / (i - 8)) * 65536.0d) / 128.0d) * 20.0d);
        if (log10 < 0) {
            return 27;
        }
        return log10;
    }

    public void a(int i) {
        try {
            this.p = new Mp3EncodeThread(this.m, i, this.i);
            this.p.start();
        } catch (Exception e) {
            Logger.e(e, a, e.getMessage(), new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends Thread {
        private AudioRecord b;

        a() {
            RecordHelper.this = r12;
            Logger.d(RecordHelper.a, "record buffer size = %s", Integer.valueOf(r12.j));
            this.b = new AudioRecord(7, r12.i.getSampleRate(), r12.i.getChannelConfig(), r12.i.getEncodingConfig(), r12.j);
            if (r12.i.getFormat() != RecordConfig.RecordFormat.MP3) {
                return;
            }
            if (r12.p == null) {
                r12.a(r12.j);
            } else {
                Logger.e(RecordHelper.a, "mp3EncodeThread != null, 请检查代码", new Object[0]);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            if (AnonymousClass6.a[RecordHelper.this.i.getFormat().ordinal()] != 1) {
                a();
            } else {
                b();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x00b4  */
        /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void a() {
            /*
                r7 = this;
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r1 = com.zlw.main.recorderlib.recorder.RecordHelper.RecordState.RECORDING
                com.zlw.main.recorderlib.recorder.RecordHelper.a(r0, r1)
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this
                com.zlw.main.recorderlib.recorder.RecordHelper.l(r0)
                java.lang.String r0 = com.zlw.main.recorderlib.recorder.RecordHelper.c()
                java.lang.String r1 = "开始录制 Pcm"
                r2 = 0
                java.lang.Object[] r3 = new java.lang.Object[r2]
                com.zlw.main.recorderlib.utils.Logger.d(r0, r1, r3)
                r0 = 0
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: Exception -> 0x0088, all -> 0x0083
                com.zlw.main.recorderlib.recorder.RecordHelper r3 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0088, all -> 0x0083
                java.io.File r3 = com.zlw.main.recorderlib.recorder.RecordHelper.m(r3)     // Catch: Exception -> 0x0088, all -> 0x0083
                r1.<init>(r3)     // Catch: Exception -> 0x0088, all -> 0x0083
                android.media.AudioRecord r0 = r7.b     // Catch: Exception -> 0x0081, all -> 0x00cc
                r0.startRecording()     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                int r0 = com.zlw.main.recorderlib.recorder.RecordHelper.i(r0)     // Catch: Exception -> 0x0081, all -> 0x00cc
                byte[] r0 = new byte[r0]     // Catch: Exception -> 0x0081, all -> 0x00cc
            L_0x0031:
                com.zlw.main.recorderlib.recorder.RecordHelper r3 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r3 = com.zlw.main.recorderlib.recorder.RecordHelper.a(r3)     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r4 = com.zlw.main.recorderlib.recorder.RecordHelper.RecordState.RECORDING     // Catch: Exception -> 0x0081, all -> 0x00cc
                if (r3 != r4) goto L_0x004e
                android.media.AudioRecord r3 = r7.b     // Catch: Exception -> 0x0081, all -> 0x00cc
                int r4 = r0.length     // Catch: Exception -> 0x0081, all -> 0x00cc
                int r3 = r3.read(r0, r2, r4)     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r4 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper.b(r4, r0)     // Catch: Exception -> 0x0081, all -> 0x00cc
                r1.write(r0, r2, r3)     // Catch: Exception -> 0x0081, all -> 0x00cc
                r1.flush()     // Catch: Exception -> 0x0081, all -> 0x00cc
                goto L_0x0031
            L_0x004e:
                android.media.AudioRecord r0 = r7.b     // Catch: Exception -> 0x0081, all -> 0x00cc
                r0.stop()     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                java.util.List r0 = com.zlw.main.recorderlib.recorder.RecordHelper.n(r0)     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r3 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                java.io.File r3 = com.zlw.main.recorderlib.recorder.RecordHelper.m(r3)     // Catch: Exception -> 0x0081, all -> 0x00cc
                r0.add(r3)     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r0 = com.zlw.main.recorderlib.recorder.RecordHelper.a(r0)     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r3 = com.zlw.main.recorderlib.recorder.RecordHelper.RecordState.STOP     // Catch: Exception -> 0x0081, all -> 0x00cc
                if (r0 != r3) goto L_0x0072
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper.o(r0)     // Catch: Exception -> 0x0081, all -> 0x00cc
                goto L_0x007d
            L_0x0072:
                java.lang.String r0 = com.zlw.main.recorderlib.recorder.RecordHelper.c()     // Catch: Exception -> 0x0081, all -> 0x00cc
                java.lang.String r3 = "暂停！"
                java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: Exception -> 0x0081, all -> 0x00cc
                com.zlw.main.recorderlib.utils.Logger.i(r0, r3, r4)     // Catch: Exception -> 0x0081, all -> 0x00cc
            L_0x007d:
                r1.close()     // Catch: IOException -> 0x00a6
                goto L_0x00aa
            L_0x0081:
                r0 = move-exception
                goto L_0x008c
            L_0x0083:
                r1 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
                goto L_0x00cd
            L_0x0088:
                r1 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
            L_0x008c:
                java.lang.String r3 = com.zlw.main.recorderlib.recorder.RecordHelper.c()     // Catch: all -> 0x00cc
                java.lang.String r4 = r0.getMessage()     // Catch: all -> 0x00cc
                java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch: all -> 0x00cc
                com.zlw.main.recorderlib.utils.Logger.e(r0, r3, r4, r5)     // Catch: all -> 0x00cc
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this     // Catch: all -> 0x00cc
                java.lang.String r3 = "录音失败"
                com.zlw.main.recorderlib.recorder.RecordHelper.a(r0, r3)     // Catch: all -> 0x00cc
                if (r1 == 0) goto L_0x00aa
                r1.close()     // Catch: IOException -> 0x00a6
                goto L_0x00aa
            L_0x00a6:
                r0 = move-exception
                r0.printStackTrace()
            L_0x00aa:
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r0 = com.zlw.main.recorderlib.recorder.RecordHelper.a(r0)
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r1 = com.zlw.main.recorderlib.recorder.RecordHelper.RecordState.PAUSE
                if (r0 == r1) goto L_0x00cb
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this
                com.zlw.main.recorderlib.recorder.RecordHelper$RecordState r1 = com.zlw.main.recorderlib.recorder.RecordHelper.RecordState.IDLE
                com.zlw.main.recorderlib.recorder.RecordHelper.a(r0, r1)
                com.zlw.main.recorderlib.recorder.RecordHelper r0 = com.zlw.main.recorderlib.recorder.RecordHelper.this
                com.zlw.main.recorderlib.recorder.RecordHelper.l(r0)
                java.lang.String r0 = com.zlw.main.recorderlib.recorder.RecordHelper.c()
                java.lang.String r1 = "录音结束"
                java.lang.Object[] r2 = new java.lang.Object[r2]
                com.zlw.main.recorderlib.utils.Logger.d(r0, r1, r2)
            L_0x00cb:
                return
            L_0x00cc:
                r0 = move-exception
            L_0x00cd:
                if (r1 == 0) goto L_0x00d7
                r1.close()     // Catch: IOException -> 0x00d3
                goto L_0x00d7
            L_0x00d3:
                r1 = move-exception
                r1.printStackTrace()
            L_0x00d7:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zlw.main.recorderlib.recorder.RecordHelper.a.a():void");
        }

        private void b() {
            RecordHelper.this.c = RecordState.RECORDING;
            RecordHelper.this.d();
            try {
                this.b.startRecording();
                short[] sArr = new short[RecordHelper.this.j];
                while (RecordHelper.this.c == RecordState.RECORDING) {
                    int read = this.b.read(sArr, 0, sArr.length);
                    if (RecordHelper.this.p != null) {
                        RecordHelper.this.p.addChangeBuffer(new Mp3EncodeThread.ChangeBuffer(sArr, read));
                    }
                    RecordHelper.this.a(ByteUtils.toBytes(sArr));
                }
                this.b.stop();
            } catch (Exception e) {
                Logger.e(e, RecordHelper.a, e.getMessage(), new Object[0]);
                RecordHelper.this.a("录音失败");
            }
            if (RecordHelper.this.c != RecordState.PAUSE) {
                RecordHelper.this.c = RecordState.IDLE;
                RecordHelper.this.d();
                RecordHelper.this.f();
                return;
            }
            Logger.d(RecordHelper.a, "暂停", new Object[0]);
        }
    }

    public void f() {
        Mp3EncodeThread mp3EncodeThread = this.p;
        if (mp3EncodeThread != null) {
            mp3EncodeThread.stopSafe(new Mp3EncodeThread.EncordFinishListener() { // from class: com.zlw.main.recorderlib.recorder.RecordHelper.5
                @Override // com.zlw.main.recorderlib.recorder.mp3.Mp3EncodeThread.EncordFinishListener
                public void onFinish() {
                    RecordHelper.this.e();
                    RecordHelper.this.p = null;
                }
            });
        } else {
            Logger.e(a, "mp3EncodeThread is null, 代码业务流程有误，请检查！！ ", new Object[0]);
        }
    }

    public void g() {
        switch (this.i.getFormat()) {
            case MP3:
                return;
            case WAV:
                i();
                h();
                break;
            case PCM:
                i();
                break;
        }
        e();
        Logger.i(a, "录音完成！ path: %s ； 大小：%s", this.m.getAbsoluteFile(), Long.valueOf(this.m.length()));
    }

    private void h() {
        if (FileUtils.isFile(this.m) && this.m.length() != 0) {
            WavUtils.writeHeader(this.m, WavUtils.generateWavFileHeader((int) this.m.length(), this.i.getSampleRate(), this.i.getChannelCount(), this.i.getEncoding()));
        }
    }

    private void i() {
        if (!a(this.m, this.o)) {
            a("合并失败");
        }
    }

    private boolean a(File file, List<File> list) {
        Throwable th;
        FileOutputStream fileOutputStream;
        Exception e;
        if (file == null || list == null || list.size() <= 0) {
            return false;
        }
        byte[] bArr = new byte[1024];
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                try {
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(list.get(i)));
                            while (true) {
                                int read = bufferedInputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                bufferedOutputStream.write(bArr, 0, read);
                            }
                            bufferedInputStream.close();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedOutputStream = bufferedOutputStream;
                            Logger.e(e, a, e.getMessage(), new Object[0]);
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    return false;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    throw th;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                    }
                    try {
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        list.get(i2).delete();
                    }
                    list.clear();
                    return true;
                } catch (Exception e6) {
                    e = e6;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e7) {
            e = e7;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
            fileOutputStream = null;
        }
    }

    private String j() {
        String format = String.format(Locale.getDefault(), "%s/Record/", Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!FileUtils.createOrExistsDir(format)) {
            Logger.e(a, "文件夹创建失败：%s", format);
        }
        return String.format(Locale.getDefault(), "%s%s.pcm", format, String.format(Locale.getDefault(), "record_tmp_%s", FileUtils.getNowString(new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.SIMPLIFIED_CHINESE))));
    }
}
