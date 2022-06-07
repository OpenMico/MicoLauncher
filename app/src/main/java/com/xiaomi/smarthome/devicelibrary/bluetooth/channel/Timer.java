package com.xiaomi.smarthome.devicelibrary.bluetooth.channel;

import android.os.Handler;
import android.os.Looper;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class Timer {
    private Handler a = new Handler(Looper.getMainLooper());
    private TimerCallback b;

    private Timer() {
    }

    public static Timer newInstance() {
        return new Timer();
    }

    /* loaded from: classes4.dex */
    public static abstract class TimerCallback implements Runnable {
        private String a;

        public abstract void onTimerCallback() throws TimeoutException;

        public abstract void resetCallback();

        public TimerCallback(String str) {
            this.a = str;
        }

        public String getName() {
            return this.a;
        }

        @Override // java.lang.Runnable
        public final void run() {
            BluetoothLog.e(String.format("%s: Timer expired!!!", this.a));
            try {
                onTimerCallback();
            } catch (TimeoutException e) {
                BluetoothLog.e(e);
            }
            resetCallback();
        }
    }

    public synchronized void resetCallback() {
        this.b = null;
    }

    public synchronized void stop() {
        this.a.removeCallbacksAndMessages(null);
        this.b = null;
    }

    public synchronized boolean isRunning() {
        return this.b != null;
    }

    public synchronized String getName() {
        return isRunning() ? this.b.getName() : "";
    }

    public synchronized void start(TimerCallback timerCallback, long j) {
        this.a.removeCallbacksAndMessages(null);
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            myLooper = Looper.getMainLooper();
        }
        this.a = new Handler(myLooper);
        this.a.postDelayed(timerCallback, j);
        this.b = timerCallback;
    }
}
