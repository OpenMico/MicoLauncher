package com.zlw.main.recorderlib.recorder.mp3;

import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.utils.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class Mp3EncodeThread extends Thread {
    private static final String a = "Mp3EncodeThread";
    private File c;
    private FileOutputStream d;
    private byte[] e;
    private EncordFinishListener f;
    private List<ChangeBuffer> b = Collections.synchronizedList(new LinkedList());
    private volatile boolean g = false;
    private volatile boolean h = true;

    /* loaded from: classes4.dex */
    public interface EncordFinishListener {
        void onFinish();
    }

    public Mp3EncodeThread(File file, int i, RecordConfig recordConfig) {
        this.c = file;
        this.e = new byte[(int) ((i * 2 * 1.25d) + 7200.0d)];
        int sampleRate = recordConfig.getSampleRate();
        Logger.w(a, "in_sampleRate:%s，getChannelCount:%s ，out_sampleRate：%s 位宽： %s,", Integer.valueOf(sampleRate), Integer.valueOf(recordConfig.getChannelCount()), Integer.valueOf(sampleRate), Integer.valueOf(recordConfig.getRealEncoding()));
        Mp3Encoder.init(sampleRate, recordConfig.getChannelCount(), sampleRate, recordConfig.getRealEncoding());
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.d = new FileOutputStream(this.c);
            while (this.h) {
                ChangeBuffer a2 = a();
                String str = a;
                Object[] objArr = new Object[1];
                objArr[0] = a2 == null ? "null" : Integer.valueOf(a2.b());
                Logger.v(str, "处理数据：%s", objArr);
                a(a2);
            }
        } catch (FileNotFoundException e) {
            Logger.e(e, a, e.getMessage(), new Object[0]);
        }
    }

    public void addChangeBuffer(ChangeBuffer changeBuffer) {
        if (changeBuffer != null) {
            this.b.add(changeBuffer);
            synchronized (this) {
                notify();
            }
        }
    }

    public void stopSafe(EncordFinishListener encordFinishListener) {
        this.f = encordFinishListener;
        this.g = true;
        synchronized (this) {
            notify();
        }
    }

    private ChangeBuffer a() {
        while (true) {
            List<ChangeBuffer> list = this.b;
            if (list != null && list.size() != 0) {
                return this.b.remove(0);
            }
            try {
                if (this.g) {
                    b();
                }
                synchronized (this) {
                    wait();
                }
            } catch (Exception e) {
                Logger.e(e, a, e.getMessage(), new Object[0]);
            }
        }
    }

    private void a(ChangeBuffer changeBuffer) {
        if (changeBuffer != null) {
            short[] a2 = changeBuffer.a();
            int b = changeBuffer.b();
            if (b > 0) {
                int encode = Mp3Encoder.encode(a2, a2, b, this.e);
                if (encode < 0) {
                    String str = a;
                    Logger.e(str, "Lame encoded size: " + encode, new Object[0]);
                }
                try {
                    this.d.write(this.e, 0, encode);
                } catch (IOException e) {
                    Logger.e(e, a, "Unable to write to file", new Object[0]);
                }
            }
        }
    }

    private void b() {
        this.h = false;
        int flush = Mp3Encoder.flush(this.e);
        if (flush > 0) {
            try {
                this.d.write(this.e, 0, flush);
                this.d.close();
            } catch (IOException e) {
                Logger.e(a, e.getMessage(), new Object[0]);
            }
        }
        Logger.d(a, "转换结束 :%s", Long.valueOf(this.c.length()));
        EncordFinishListener encordFinishListener = this.f;
        if (encordFinishListener != null) {
            encordFinishListener.onFinish();
        }
    }

    /* loaded from: classes4.dex */
    public static class ChangeBuffer {
        private short[] a;
        private int b;

        public ChangeBuffer(short[] sArr, int i) {
            this.a = (short[]) sArr.clone();
            this.b = i;
        }

        short[] a() {
            return this.a;
        }

        int b() {
            return this.b;
        }
    }
}
