package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public final class ControlMessage {
    public static final int TYPE_BACK_OR_SCREEN_ON = 4;
    public static final int TYPE_COLLAPSE_NOTIFICATION_PANEL = 6;
    public static final int TYPE_EXPAND_NOTIFICATION_PANEL = 5;
    public static final int TYPE_GET_CLIPBOARD = 7;
    public static final int TYPE_INJECT_KEYCODE = 0;
    public static final int TYPE_INJECT_SCROLL_EVENT = 3;
    public static final int TYPE_INJECT_TEXT = 1;
    public static final int TYPE_INJECT_TOUCH_EVENT = 2;
    public static final int TYPE_ROTATE_DEVICE = 10;
    public static final int TYPE_SET_CLIPBOARD = 8;
    public static final int TYPE_SET_SCREEN_POWER_MODE = 9;
    private int a;
    private String b;
    private int c;
    private int d;
    private int e;
    private int f;
    private long g;
    private float h;
    private Position i;
    private int j;
    private int k;

    private ControlMessage() {
    }

    public static ControlMessage createInjectKeycode(int i, int i2, int i3) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 0;
        controlMessage.d = i;
        controlMessage.e = i2;
        controlMessage.c = i3;
        return controlMessage;
    }

    public static ControlMessage createInjectText(String str) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 1;
        controlMessage.b = str;
        return controlMessage;
    }

    public static ControlMessage createInjectTouchEvent(int i, long j, Position position, float f, int i2) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 2;
        controlMessage.d = i;
        controlMessage.g = j;
        controlMessage.h = f;
        controlMessage.i = position;
        controlMessage.f = i2;
        return controlMessage;
    }

    public static ControlMessage createInjectScrollEvent(Position position, int i, int i2) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 3;
        controlMessage.i = position;
        controlMessage.j = i;
        controlMessage.k = i2;
        return controlMessage;
    }

    public static ControlMessage createSetClipboard(String str) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 8;
        controlMessage.b = str;
        return controlMessage;
    }

    public static ControlMessage createSetScreenPowerMode(int i) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = 9;
        controlMessage.d = i;
        return controlMessage;
    }

    public static ControlMessage createEmpty(int i) {
        ControlMessage controlMessage = new ControlMessage();
        controlMessage.a = i;
        return controlMessage;
    }

    public int getType() {
        return this.a;
    }

    public String getText() {
        return this.b;
    }

    public int getMetaState() {
        return this.c;
    }

    public int getAction() {
        return this.d;
    }

    public int getKeycode() {
        return this.e;
    }

    public int getButtons() {
        return this.f;
    }

    public long getPointerId() {
        return this.g;
    }

    public float getPressure() {
        return this.h;
    }

    public Position getPosition() {
        return this.i;
    }

    public int getHScroll() {
        return this.j;
    }

    public int getVScroll() {
        return this.k;
    }
}
