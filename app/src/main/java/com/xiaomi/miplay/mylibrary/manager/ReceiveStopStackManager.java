package com.xiaomi.miplay.mylibrary.manager;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.util.Stack;

/* loaded from: classes4.dex */
public class ReceiveStopStackManager {
    private static final String a = "ReceiveStopStackManager";
    private Stack<String> b;

    /* loaded from: classes4.dex */
    private static class a {
        public static ReceiveStopStackManager a = new ReceiveStopStackManager();
    }

    public static ReceiveStopStackManager getInstance() {
        return a.a;
    }

    public Stack<String> getStack() {
        return this.b;
    }

    private ReceiveStopStackManager() {
        this.b = new Stack<>();
    }

    public void addDevice(String str) {
        Logger.d(a, "addDevice.", new Object[0]);
        this.b.add(str);
    }

    public void deleteDevice(String str) {
        Logger.d(a, "deleteDevice.", new Object[0]);
        this.b.remove(str);
    }

    public boolean isEmpty() {
        return this.b.isEmpty();
    }
}
