package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.text.TextUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.skills.alarm.AlarmCacheManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class AlarmMusicCacheJob implements ICacheJob {
    private String a;
    private String b;
    private String c;
    private AlarmCacheManager d = AlarmCacheManager.getInstance();
    private JobService e;
    private JobParameters f;
    private boolean g;
    private AtomicInteger h;

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public String getJobType() {
        return JobTaskUtil.JOB_TYPE_ALARM_MUSIC;
    }

    public AlarmMusicCacheJob(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public void setJobService(JobService jobService, JobParameters jobParameters) {
        this.e = jobService;
        this.f = jobParameters;
    }

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public void setJobNeedsReschedule(boolean z) {
        this.g = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.e == null || this.f == null) {
            throw new IllegalArgumentException("JobService and JobParameters should not be null");
        } else if (!TextUtils.isEmpty(this.b)) {
            L.alarm.i("AlarmMusicCacheJob will send alarmNlpRequest with ringtone=%s ", this.b);
            this.h = new AtomicInteger();
            a("alarm_" + this.b);
        } else {
            L.alarm.w("AlarmMusicCacheJob will be ignored cause of ringtone is empty.");
            this.e.jobFinished(this.f, this.g);
        }
    }

    private void a(String str) {
        SpeechEventUtil.getInstance().nlpRequest(str, new SpeechEventUtil.NLPListener() { // from class: com.xiaomi.micolauncher.common.services.job.AlarmMusicCacheJob.1
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpResult(List<Instruction> list, String str2) {
                L.alarm.i("AlarmMusicCacheJob onNlpResult");
                AlarmMusicCacheJob.this.d.setMusicCacheList(AlarmMusicCacheJob.this.a, list);
                AlarmMusicCacheJob.this.a();
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpFail(String str2, boolean z) {
                L.alarm.w("AlarmMusicCacheJob onNlpFail=%s", str2);
                AlarmMusicCacheJob.this.a();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.h.getAndDecrement();
        if (this.h.get() == 0) {
            this.e.jobFinished(this.f, this.g);
        }
    }
}
