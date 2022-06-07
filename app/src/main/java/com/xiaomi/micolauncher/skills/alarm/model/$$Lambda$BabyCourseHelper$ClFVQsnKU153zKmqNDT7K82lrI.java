package com.xiaomi.micolauncher.skills.alarm.model;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$BabyCourseHelper$ClFVQsnKU153z-KmqNDT7K82lrI  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$BabyCourseHelper$ClFVQsnKU153zKmqNDT7K82lrI implements Function {
    public static final /* synthetic */ $$Lambda$BabyCourseHelper$ClFVQsnKU153zKmqNDT7K82lrI INSTANCE = new $$Lambda$BabyCourseHelper$ClFVQsnKU153zKmqNDT7K82lrI();

    private /* synthetic */ $$Lambda$BabyCourseHelper$ClFVQsnKU153zKmqNDT7K82lrI() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = BabyCourseHelper.a((Music.StationSoundList) obj);
        return a;
    }
}
