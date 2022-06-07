package com.xiaomi.micolauncher.common.track;

import com.xiaomi.mico.utils.ThreadUtil;

/* loaded from: classes3.dex */
public class TrackStat {
    public static void simpleCountEvent(final TrackWidget trackWidget, final EventType eventType, final ExtendJsonWrapper extendJsonWrapper, final boolean z) {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.track.-$$Lambda$TrackStat$XudyRW2iS4TkjnAa2xQ9105e2Lo
            @Override // java.lang.Runnable
            public final void run() {
                TrackStat.a(EventType.this, trackWidget, extendJsonWrapper, z);
            }
        });
    }

    public static /* synthetic */ void a(EventType eventType, TrackWidget trackWidget, ExtendJsonWrapper extendJsonWrapper, boolean z) {
        new TrackEventBuilder().setEventDataType("simple_count").setEventType(eventType).setWidget(trackWidget.getKey()).setExtendJsonWrapper(extendJsonWrapper).post(z);
    }

    public static void simpleCountEvent(TrackWidget trackWidget, EventType eventType, ExtendJsonWrapper extendJsonWrapper) {
        simpleCountEvent(trackWidget, eventType, extendJsonWrapper, true);
    }

    public static void synchSimpleCountEvent(TrackWidget trackWidget, EventType eventType, ExtendJsonWrapper extendJsonWrapper) {
        new TrackEventBuilder().setEventDataType("simple_count").setEventType(eventType).setWidget(trackWidget.getKey()).setExtendJsonWrapper(extendJsonWrapper).post();
    }

    public static void calculateCountEvent(TrackWidget trackWidget, EventType eventType, ExtendJsonWrapper extendJsonWrapper) {
        new TrackEventBuilder().setEventDataType("calculate_count").setEventType(eventType).setWidget(trackWidget.getKey()).setExtendJsonWrapper(extendJsonWrapper).post();
    }

    public static void customCountEvent(TrackWidget trackWidget, EventType eventType, ExtendJsonWrapper extendJsonWrapper) {
        new TrackEventBuilder().setEventDataType("custom").setEventType(eventType).setWidget(trackWidget.getKey()).setExtendJsonWrapper(extendJsonWrapper).post();
    }

    public static void postTransExpose(String str) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogId(str);
        simpleCountEvent(TrackWidget.TRANSLATION_CARD, EventType.EXPOSE, obtain);
    }

    public static void postTts(String str, long j, long j2, boolean z) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogId(str).setPlayStartTime(j).setDuration(j2).setInterrupted(z);
        simpleCountEvent(TrackWidget.TTS, EventType.FINISH, obtain);
    }
}
