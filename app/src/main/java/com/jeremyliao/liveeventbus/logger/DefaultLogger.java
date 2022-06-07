package com.jeremyliao.liveeventbus.logger;

import android.util.Log;
import java.util.logging.Level;

/* loaded from: classes2.dex */
public class DefaultLogger implements Logger {
    private static final String TAG = "[LiveEventBus]";

    @Override // com.jeremyliao.liveeventbus.logger.Logger
    public void log(Level level, String str) {
        if (level == Level.SEVERE) {
            Log.e(TAG, str);
        } else if (level == Level.WARNING) {
            Log.w(TAG, str);
        } else if (level == Level.INFO) {
            Log.i(TAG, str);
        } else if (level == Level.CONFIG) {
            Log.d(TAG, str);
        } else if (level != Level.OFF) {
            Log.v(TAG, str);
        }
    }

    @Override // com.jeremyliao.liveeventbus.logger.Logger
    public void log(Level level, String str, Throwable th) {
        if (level == Level.SEVERE) {
            Log.e(TAG, str, th);
        } else if (level == Level.WARNING) {
            Log.w(TAG, str, th);
        } else if (level == Level.INFO) {
            Log.i(TAG, str, th);
        } else if (level == Level.CONFIG) {
            Log.d(TAG, str, th);
        } else if (level != Level.OFF) {
            Log.v(TAG, str, th);
        }
    }
}
