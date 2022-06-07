package com.xiaomi.micolauncher.module.homepage.worker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/* loaded from: classes3.dex */
public class SelectAlarmWorker extends Worker {
    @Override // androidx.work.Worker
    @NonNull
    public ListenableWorker.Result doWork() {
        return null;
    }

    public SelectAlarmWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }
}
