package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.os.IInterface;
import android.view.InputEvent;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public final class InputManager {
    public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH = 2;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
    private final IInterface a;
    private Method b;

    public InputManager(IInterface iInterface) {
        this.a = iInterface;
    }

    private Method a() throws NoSuchMethodException {
        if (this.b == null) {
            this.b = this.a.getClass().getMethod("injectInputEvent", InputEvent.class, Integer.TYPE);
        }
        return this.b;
    }

    public boolean injectInputEvent(InputEvent inputEvent, int i) {
        try {
            return ((Boolean) a().invoke(this.a, inputEvent, Integer.valueOf(i))).booleanValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return false;
        }
    }
}
