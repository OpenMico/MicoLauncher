package com.zlw.main.recorderlib;

import android.annotation.SuppressLint;
import android.app.Application;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.RecordService;
import com.zlw.main.recorderlib.recorder.listener.RecordDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.utils.FileUtils;
import com.zlw.main.recorderlib.utils.Logger;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
public class RecordManager {
    private static final String a = "RecordManager";
    @SuppressLint({"StaticFieldLeak"})
    private static volatile RecordManager b;
    private Application c;
    private RecordConfig d;

    private RecordManager() {
    }

    public static RecordManager getInstance() {
        if (b == null) {
            synchronized (RecordManager.class) {
                if (b == null) {
                    b = new RecordManager();
                }
            }
        }
        return b;
    }

    public void init(Application application, boolean z) {
        this.c = application;
        Logger.IsDebug = z;
        Logger.LOGD = z;
        Logger.LOGI = z;
    }

    public void init(Application application, RecordConfig recordConfig, boolean z) {
        this.c = application;
        setRecordConfig(recordConfig);
        Logger.IsDebug = z;
        Logger.LOGD = z;
        Logger.LOGI = z;
    }

    public RecordConfig getRecordConfig() {
        return this.d;
    }

    public boolean setRecordConfig(RecordConfig recordConfig) {
        if (a() == RecordHelper.RecordState.IDLE) {
            this.d = recordConfig;
            RecordHelper.getInstance().setCurrentConfig(recordConfig);
            return true;
        }
        RecordConfig recordConfig2 = this.d;
        if (recordConfig2 == null || recordConfig2.getConfigVersion() != recordConfig.getConfigVersion()) {
            Logger.w(a, "setRecordConfig failed cuz record thread is not idle, will use old config when next recording", new Object[0]);
        } else {
            Logger.w(a, "setRecordConfig ignore cuz it's not changed", new Object[0]);
        }
        return false;
    }

    public boolean changeFormat(RecordConfig.RecordFormat recordFormat) {
        if (this.d == null) {
            throw new IllegalStateException("RecordManager should set RecordConfig before changeFormat");
        } else if (a() == RecordHelper.RecordState.IDLE) {
            this.d.setFormat(recordFormat);
            RecordHelper.getInstance().setCurrentConfig(this.d);
            return true;
        } else {
            Logger.w(a, "changeFormat failed cuz record thread is not idle, will use old config when next recording", new Object[0]);
            return false;
        }
    }

    public boolean changeRecordDir(String str) {
        if (this.d == null) {
            throw new IllegalStateException("RecordManager should set RecordConfig before changeRecordDir");
        } else if (a() == RecordHelper.RecordState.IDLE) {
            this.d.setRecordDir(str);
            RecordHelper.getInstance().setCurrentConfig(this.d);
            return true;
        } else {
            Logger.w(a, "changeRecordDir failed cuz record thread is not idle, will use old config when next recording", new Object[0]);
            return false;
        }
    }

    public void start() {
        if (this.c == null) {
            Logger.e(a, "未进行初始化", new Object[0]);
        } else if (this.d != null) {
            Logger.i(a, "start...", new Object[0]);
            RecordService.startRecording(this.c, b());
        } else {
            throw new IllegalStateException("RecordManager should set RecordConfig before startRecording");
        }
    }

    public void stop() {
        Application application = this.c;
        if (application != null) {
            RecordService.stopRecording(application);
        }
    }

    public void resume() {
        Application application = this.c;
        if (application != null) {
            RecordService.resumeRecording(application);
        }
    }

    public void pause() {
        Application application = this.c;
        if (application != null) {
            RecordService.pauseRecording(application);
        }
    }

    public void setRecordStateListener(RecordStateListener recordStateListener) {
        RecordService.setRecordStateListener(recordStateListener);
    }

    public void setRecordDataListener(RecordDataListener recordDataListener) {
        RecordService.setRecordDataListener(recordDataListener);
    }

    public void setRecordFftDataListener(RecordFftDataListener recordFftDataListener) {
        RecordService.setRecordFftDataListener(recordFftDataListener);
    }

    public void setRecordResultListener(RecordResultListener recordResultListener) {
        RecordService.setRecordResultListener(recordResultListener);
    }

    public void setRecordSoundSizeListener(RecordSoundSizeListener recordSoundSizeListener) {
        RecordService.setRecordSoundSizeListener(recordSoundSizeListener);
    }

    private RecordHelper.RecordState a() {
        return RecordHelper.getInstance().getState();
    }

    private String b() {
        RecordConfig recordConfig = this.d;
        if (recordConfig != null) {
            String recordDir = recordConfig.getRecordDir();
            if (!FileUtils.createOrExistsDir(recordDir)) {
                Logger.w(a, "文件夹创建失败：%s", recordDir);
                return null;
            }
            return String.format(Locale.getDefault(), "%s%s%s", recordDir, String.format(Locale.getDefault(), "record_%s", FileUtils.getNowString(new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.SIMPLIFIED_CHINESE))), this.d.getFormat().getExtension());
        }
        throw new IllegalStateException("should set RecordConfig before call getFilePath");
    }
}
