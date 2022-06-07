package com.xiaomi.analytics;

import android.text.TextUtils;
import com.xiaomi.analytics.LogEvent;

/* loaded from: classes3.dex */
public class Tracker extends a {
    @Override // com.xiaomi.analytics.a
    public /* bridge */ /* synthetic */ void endSession() {
        super.endSession();
    }

    @Override // com.xiaomi.analytics.a
    public /* bridge */ /* synthetic */ void startSession() {
        super.startSession();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Tracker(String str) {
        super(str);
    }

    public void track(Action action) {
        if (action == null) {
            return;
        }
        if (action instanceof AdAction) {
            log(LogEvent.create(LogEvent.LogType.TYPE_AD).b(action.a()).a(action.b()));
        } else {
            log(LogEvent.create().b(action.a()).a(action.b()));
        }
    }

    public void track(Action action, LogEvent.IdType idType) {
        if (action == null) {
            return;
        }
        if (action instanceof AdAction) {
            log(LogEvent.create(LogEvent.LogType.TYPE_AD, idType).b(action.a()).a(action.b()));
        } else {
            log(LogEvent.create(idType).b(action.a()).a(action.b()));
        }
    }

    public void track(String str, Action action) {
        if (action != null && !TextUtils.isEmpty(str)) {
            if (action instanceof AdAction) {
                log(str, LogEvent.create(LogEvent.LogType.TYPE_AD).b(action.a()).a(action.b()));
            } else {
                log(str, LogEvent.create().b(action.a()).a(action.b()));
            }
        }
    }

    public void track(String str, Action action, LogEvent.IdType idType) {
        if (action != null && !TextUtils.isEmpty(str)) {
            if (action instanceof AdAction) {
                log(str, LogEvent.create(LogEvent.LogType.TYPE_AD, idType).b(action.a()).a(action.b()));
            } else {
                log(str, LogEvent.create(idType).b(action.a()).a(action.b()));
            }
        }
    }
}
