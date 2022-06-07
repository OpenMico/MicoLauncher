package com.xiaomi.micolauncher.common.speech.utils;

import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import com.elvishew.xlog.Logger;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes3.dex */
public class SpeechLog {
    private static final String a = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vpm";
    private static final String b = a + "/wav";
    private static final String c = a + "/log";
    private static final String d = c + "/vpmclient.log";
    private static final String e = c + "/vpmclient.log.bak";
    private final boolean f;
    private FileWriter g;

    public SpeechLog() {
        this.f = SpeechConfig.isLabTest() || SpeechConfig.isAutoTest();
        File file = new File(b);
        File file2 = new File(c);
        for (int i = 3; i > 0 && !file.exists() && !file.mkdirs(); i--) {
            L.speech.e("create directory %s failed", b);
        }
        for (int i2 = 3; i2 > 0 && !file2.exists() && !file2.mkdirs(); i2--) {
            L.speech.e("create directory %s failed", c);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (this.f) {
            a("\n\nLOG START: " + simpleDateFormat.format(new Date()) + "\n\n");
        }
    }

    public void writeWakeupLog(long j, float f, float f2, long j2, int i) {
        if (this.f) {
            Date date = new Date(j);
            String format = String.format(Locale.getDefault(), "\n{\"wakeup_word\":\"小爱同学\",\"wakeup_angle\":%g,\"awaken_time\":\"%sT%s\",\"wakeup_score\":%g, \"latency\":%d, \"wakeup_cnt\":%d}\n", Float.valueOf(f), new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.getDefault()).format(date), new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(date), Float.valueOf(f2), Long.valueOf(j2), Integer.valueOf(i));
            a(format);
            Logger logger = L.speech;
            logger.d("[SpeechLog]:" + format);
        }
    }

    public void writeAsrPartialLog(Instruction instruction) {
        if (this.f) {
            String str = String.format(Locale.getDefault(), "{\"meta\":{\"type\":\"RESULT_ASR_PARTIAL\",\"request_id\":\"%s\",\"timestamp\":%d}", InstructionUtil.getDialogId(instruction), Long.valueOf(System.currentTimeMillis())) + ", \"response\":" + instruction.toString() + "}\n";
            a(str);
            L.speech.d("[SpeechLog]:" + str);
        }
    }

    public void writeAsrFinalLog(Instruction instruction) {
        if (this.f) {
            String str = String.format(Locale.getDefault(), "{\"meta\":{\"type\":\"RESULT_ASR_FINAL\",\"request_id\":\"%s\",\"timestamp\":%d}", InstructionUtil.getDialogId(instruction), Long.valueOf(System.currentTimeMillis())) + ", \"response\":" + instruction.toString() + "}\n";
            a(str);
            L.speech.d("[SpeechLog]:" + str);
        }
    }

    private synchronized void a(String str) {
        try {
            File file = new File(d);
            if (this.g == null || !file.exists()) {
                this.g = new FileWriter(d, true);
            }
            this.g.write(str);
            this.g.flush();
            if (file.exists() && file.length() >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
                this.g.close();
                if (!file.renameTo(new File(e))) {
                    L.speech.e("%s rename file %s to %s error!", "[SpeechLog]:", d, e);
                }
                this.g = null;
                L.speech.d("%s rename file %s to %s success, file.size=%d!", "[SpeechLog]:", d, e, Long.valueOf(file.length()));
            }
        } catch (IOException e2) {
            L.speech.e("[SpeechLog]:", e2);
        }
    }
}
