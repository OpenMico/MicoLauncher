package com.xiaomi.micolauncher.common.track;

/* loaded from: classes3.dex */
public enum EventType {
    EXPOSE(0),
    CLICK(1),
    FINISH(2),
    PLAY(3),
    FAVORITE(4),
    ENTER(5),
    EXIT(6),
    CANCEL_FAVORITE(7),
    DURATION(8);
    
    private int id;

    EventType(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
