package com.zlw.main.recorderlib.recorder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.listener.RecordDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.utils.Logger;

/* loaded from: classes4.dex */
public class RecordService extends Service {
    private static final String a = "RecordService";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return super.onStartCommand(intent, i, i2);
        }
        Bundle extras = intent.getExtras();
        if (extras == null || !extras.containsKey("action_type")) {
            return super.onStartCommand(intent, i, i2);
        }
        switch (extras.getInt("action_type", 0)) {
            case 1:
                a(extras.getString("path"));
                return 1;
            case 2:
                c();
                return 1;
            case 3:
                a();
                return 1;
            case 4:
                b();
                return 1;
            default:
                return 1;
        }
    }

    public static void startRecording(Context context, String str) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra("action_type", 1);
        intent.putExtra("path", str);
        context.startService(intent);
    }

    public static void stopRecording(Context context) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra("action_type", 2);
        context.startService(intent);
    }

    public static void resumeRecording(Context context) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra("action_type", 3);
        context.startService(intent);
    }

    public static void pauseRecording(Context context) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra("action_type", 4);
        context.startService(intent);
    }

    public static RecordHelper.RecordState getState() {
        return RecordHelper.getInstance().getState();
    }

    public static void setRecordStateListener(RecordStateListener recordStateListener) {
        RecordHelper.getInstance().a(recordStateListener);
    }

    public static void setRecordDataListener(RecordDataListener recordDataListener) {
        RecordHelper.getInstance().a(recordDataListener);
    }

    public static void setRecordSoundSizeListener(RecordSoundSizeListener recordSoundSizeListener) {
        RecordHelper.getInstance().a(recordSoundSizeListener);
    }

    public static void setRecordResultListener(RecordResultListener recordResultListener) {
        RecordHelper.getInstance().a(recordResultListener);
    }

    public static void setRecordFftDataListener(RecordFftDataListener recordFftDataListener) {
        RecordHelper.getInstance().setRecordFftDataListener(recordFftDataListener);
    }

    private void a(String str) {
        Logger.v(a, "doStartRecording path: %s", str);
        RecordHelper.getInstance().start(str);
    }

    private void a() {
        Logger.v(a, "doResumeRecording", new Object[0]);
        RecordHelper.getInstance().b();
    }

    private void b() {
        Logger.v(a, "doResumeRecording", new Object[0]);
        RecordHelper.getInstance().a();
    }

    private void c() {
        Logger.v(a, "doStopRecording", new Object[0]);
        RecordHelper.getInstance().stop();
        stopSelf();
    }
}
