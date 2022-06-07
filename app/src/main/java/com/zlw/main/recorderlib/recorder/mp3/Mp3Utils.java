package com.zlw.main.recorderlib.recorder.mp3;

import android.media.MediaExtractor;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.utils.FileUtils;
import com.zlw.main.recorderlib.utils.Logger;
import java.io.IOException;

/* loaded from: classes4.dex */
public class Mp3Utils {
    private static final String a = "Mp3Utils";

    public static long getDuration(String str) {
        Throwable th;
        IOException e;
        MediaExtractor mediaExtractor;
        if (!FileUtils.isFileExists(str) || !str.endsWith(RecordConfig.RecordFormat.MP3.getExtension())) {
            return 0L;
        }
        MediaExtractor mediaExtractor2 = null;
        try {
            try {
                mediaExtractor = new MediaExtractor();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            mediaExtractor.setDataSource(str);
            long j = mediaExtractor.getTrackFormat(0).getLong("durationUs") / 1000;
            mediaExtractor.release();
            return j;
        } catch (IOException e3) {
            e = e3;
            mediaExtractor2 = mediaExtractor;
            Logger.e(e, a, e.getMessage(), new Object[0]);
            if (mediaExtractor2 != null) {
                mediaExtractor2.release();
            }
            return 0L;
        } catch (Throwable th3) {
            th = th3;
            mediaExtractor2 = mediaExtractor;
            if (mediaExtractor2 != null) {
                mediaExtractor2.release();
            }
            throw th;
        }
    }
}
