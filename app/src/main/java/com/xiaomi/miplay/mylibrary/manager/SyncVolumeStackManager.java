package com.xiaomi.miplay.mylibrary.manager;

import java.util.Stack;

/* loaded from: classes4.dex */
public class SyncVolumeStackManager {
    private Stack<String> a;

    /* loaded from: classes4.dex */
    private static class a {
        public static SyncVolumeStackManager a = new SyncVolumeStackManager();
    }

    public static SyncVolumeStackManager getInstance() {
        return a.a;
    }

    public Stack<String> getGroupIdStack() {
        return this.a;
    }

    private SyncVolumeStackManager() {
        this.a = new Stack<>();
    }

    public void addGroupId(String str) {
        this.a.add(str);
    }

    public void deleteGroupId(String str) {
        this.a.remove(str);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }
}
