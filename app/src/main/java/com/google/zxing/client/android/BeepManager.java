package com.google.zxing.client.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class BeepManager {
    private static final String a = "BeepManager";
    private final Context b;
    private boolean c = true;
    private boolean d = false;

    public BeepManager(Activity activity) {
        activity.setVolumeControlStream(3);
        this.b = activity.getApplicationContext();
    }

    public boolean isBeepEnabled() {
        return this.c;
    }

    public void setBeepEnabled(boolean z) {
        this.c = z;
    }

    public boolean isVibrateEnabled() {
        return this.d;
    }

    public void setVibrateEnabled(boolean z) {
        this.d = z;
    }

    @SuppressLint({"MissingPermission"})
    public synchronized void playBeepSoundAndVibrate() {
        Vibrator vibrator;
        if (this.c) {
            playBeepSound();
        }
        if (this.d && (vibrator = (Vibrator) this.b.getSystemService("vibrator")) != null) {
            vibrator.vibrate(200L);
        }
    }

    public MediaPlayer playBeepSound() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.google.zxing.client.android.BeepManager.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer2) {
                mediaPlayer2.stop();
                mediaPlayer2.release();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.google.zxing.client.android.BeepManager.2
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                String str = BeepManager.a;
                Log.w(str, "Failed to beep " + i + ", " + i2);
                mediaPlayer2.stop();
                mediaPlayer2.release();
                return true;
            }
        });
        try {
            AssetFileDescriptor openRawResourceFd = this.b.getResources().openRawResourceFd(R.raw.zxing_beep);
            mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
            openRawResourceFd.close();
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.prepare();
            mediaPlayer.start();
            return mediaPlayer;
        } catch (IOException e) {
            Log.w(a, e);
            mediaPlayer.release();
            return null;
        }
    }
}
