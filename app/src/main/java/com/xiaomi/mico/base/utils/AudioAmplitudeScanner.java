package com.xiaomi.mico.base.utils;

import android.media.AudioRecord;
import android.util.Log;

/* loaded from: classes3.dex */
public class AudioAmplitudeScanner {
    private static AudioAmplitudeScanner a = new AudioAmplitudeScanner();
    private static final int b = AudioRecord.getMinBufferSize(8000, 1, 2);
    private volatile boolean c;
    private AudioAmplitudeListener d;

    /* loaded from: classes3.dex */
    public interface AudioAmplitudeListener {
        void OnAudioDetected(double d);
    }

    public static AudioAmplitudeScanner getInstance() {
        return a;
    }

    public void setAudioAmplitudeListener(AudioAmplitudeListener audioAmplitudeListener) {
        this.d = audioAmplitudeListener;
    }

    private AudioAmplitudeScanner() {
    }

    public boolean isRunning() {
        return this.c;
    }

    public synchronized boolean start() {
        if (this.c) {
            Log.e("AudioAmplitudeScanner", "AudioAmplitudeScanner running!");
            return false;
        }
        final AudioRecord audioRecord = new AudioRecord(1, 8000, 1, 2, b);
        this.c = true;
        Thread thread = new Thread(new Runnable() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$AudioAmplitudeScanner$1J2tv9fIIXMmHDZKuolNtlwrIiM
            @Override // java.lang.Runnable
            public final void run() {
                AudioAmplitudeScanner.this.a(audioRecord);
            }
        });
        thread.setName("AudioAmplitudeScanner");
        thread.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AudioRecord audioRecord) {
        audioRecord.startRecording();
        short[] sArr = new short[b];
        while (this.c) {
            int read = audioRecord.read(sArr, 0, b);
            long j = 0;
            for (short s : sArr) {
                j += s * s;
            }
            double log10 = Math.log10(j / read) * 10.0d;
            AudioAmplitudeListener audioAmplitudeListener = this.d;
            if (audioAmplitudeListener != null) {
                audioAmplitudeListener.OnAudioDetected(log10);
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        audioRecord.stop();
        audioRecord.release();
    }

    public void stop() {
        this.c = false;
    }
}
