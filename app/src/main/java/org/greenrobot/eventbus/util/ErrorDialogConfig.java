package org.greenrobot.eventbus.util;

import android.content.res.Resources;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes5.dex */
public class ErrorDialogConfig {
    final Resources a;
    final int b;
    final int c;
    EventBus e;
    String g;
    int h;
    Class<?> i;
    boolean f = true;
    final ExceptionToResourceMapping d = new ExceptionToResourceMapping();

    public ErrorDialogConfig(Resources resources, int i, int i2) {
        this.a = resources;
        this.b = i;
        this.c = i2;
    }

    public ErrorDialogConfig addMapping(Class<? extends Throwable> cls, int i) {
        this.d.addMapping(cls, i);
        return this;
    }

    public int getMessageIdForThrowable(Throwable th) {
        Integer mapThrowable = this.d.mapThrowable(th);
        if (mapThrowable != null) {
            return mapThrowable.intValue();
        }
        String str = EventBus.TAG;
        Log.d(str, "No specific message ressource ID found for " + th);
        return this.c;
    }

    public void setDefaultDialogIconId(int i) {
        this.h = i;
    }

    public void setDefaultEventTypeOnDialogClosed(Class<?> cls) {
        this.i = cls;
    }

    public void disableExceptionLogging() {
        this.f = false;
    }

    public void setTagForLoggingExceptions(String str) {
        this.g = str;
    }

    public void setEventBus(EventBus eventBus) {
        this.e = eventBus;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EventBus a() {
        EventBus eventBus = this.e;
        return eventBus != null ? eventBus : EventBus.getDefault();
    }
}
