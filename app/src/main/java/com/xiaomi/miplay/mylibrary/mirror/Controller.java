package com.xiaomi.miplay.mylibrary.mirror;

import android.os.SystemClock;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.io.IOException;

/* loaded from: classes4.dex */
public class Controller {
    private final Device a;
    private final MirrorControl b;
    private final DeviceMessageSender c;
    private long e;
    private final KeyCharacterMap d = KeyCharacterMap.load(-1);
    private final PointersState f = new PointersState();
    private final MotionEvent.PointerProperties[] g = new MotionEvent.PointerProperties[10];
    private final MotionEvent.PointerCoords[] h = new MotionEvent.PointerCoords[10];

    public Controller(Device device, MirrorControl mirrorControl) {
        this.a = device;
        this.b = mirrorControl;
        a();
        this.c = new DeviceMessageSender(mirrorControl);
    }

    private void a() {
        for (int i = 0; i < 10; i++) {
            MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
            pointerProperties.toolType = 1;
            MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
            pointerCoords.orientation = 0.0f;
            pointerCoords.size = 1.0f;
            this.g[i] = pointerProperties;
            this.h[i] = pointerCoords;
        }
    }

    public void control() throws IOException {
        if (!this.a.isScreenOn()) {
            a(26);
            SystemClock.sleep(500L);
        }
        while (true) {
            b();
        }
    }

    public DeviceMessageSender getSender() {
        return this.c;
    }

    private void b() throws IOException {
        ControlMessage receiveControlMessage = this.b.receiveControlMessage();
        switch (receiveControlMessage.getType()) {
            case 0:
                a(receiveControlMessage.getAction(), receiveControlMessage.getKeycode(), receiveControlMessage.getMetaState());
                return;
            case 1:
                a(receiveControlMessage.getText());
                return;
            case 2:
                a(receiveControlMessage.getAction(), receiveControlMessage.getPointerId(), receiveControlMessage.getPosition(), receiveControlMessage.getPressure(), receiveControlMessage.getButtons());
                return;
            case 3:
                a(receiveControlMessage.getPosition(), receiveControlMessage.getHScroll(), receiveControlMessage.getVScroll());
                return;
            case 4:
                c();
                return;
            case 5:
                this.a.expandNotificationPanel();
                return;
            case 6:
                this.a.collapsePanels();
                return;
            case 7:
                this.c.pushClipboardText(this.a.getClipboardText());
                return;
            case 8:
                this.a.setClipboardText(receiveControlMessage.getText());
                return;
            case 9:
                this.a.setScreenPowerMode(receiveControlMessage.getAction());
                return;
            case 10:
                this.a.rotateDevice();
                return;
            default:
                return;
        }
    }

    private boolean a(int i, int i2, int i3) {
        return a(i, i2, 0, i3);
    }

    private boolean a(char c) {
        String decompose = KeyComposition.decompose(c);
        KeyEvent[] events = this.d.getEvents(decompose != null ? decompose.toCharArray() : new char[]{c});
        if (events == null) {
            return false;
        }
        for (KeyEvent keyEvent : events) {
            if (!a(keyEvent)) {
                return false;
            }
        }
        return true;
    }

    private int a(String str) {
        char[] charArray = str.toCharArray();
        int i = 0;
        for (char c : charArray) {
            if (!a(c)) {
                Ln.w("Could not inject char u+" + String.format("%04x", Integer.valueOf(c)));
            } else {
                i++;
            }
        }
        return i;
    }

    private boolean a(int i, long j, Position position, float f, int i2) {
        int i3;
        long uptimeMillis = SystemClock.uptimeMillis();
        Point physicalPoint = this.a.getPhysicalPoint(position);
        boolean z = false;
        if (physicalPoint == null) {
            return false;
        }
        int pointerIndex = this.f.getPointerIndex(j);
        if (pointerIndex == -1) {
            Ln.w("Too many pointers for touch event");
            return false;
        }
        Pointer pointer = this.f.get(pointerIndex);
        pointer.setPoint(physicalPoint);
        pointer.setPressure(f);
        if (i == 1) {
            z = true;
        }
        pointer.setUp(z);
        int update = this.f.update(this.g, this.h);
        if (update != 1) {
            if (i == 1) {
                i3 = (pointerIndex << 8) | 6;
            } else if (i == 0) {
                i3 = (pointerIndex << 8) | 5;
            }
            return a(MotionEvent.obtain(this.e, uptimeMillis, i3, update, this.g, this.h, 0, i2, 1.0f, 1.0f, -1, 0, 4098, 0));
        } else if (i == 0) {
            this.e = uptimeMillis;
        }
        i3 = i;
        return a(MotionEvent.obtain(this.e, uptimeMillis, i3, update, this.g, this.h, 0, i2, 1.0f, 1.0f, -1, 0, 4098, 0));
    }

    private boolean a(Position position, int i, int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Point physicalPoint = this.a.getPhysicalPoint(position);
        if (physicalPoint == null) {
            return false;
        }
        this.g[0].id = 0;
        MotionEvent.PointerCoords pointerCoords = this.h[0];
        pointerCoords.x = physicalPoint.getX();
        pointerCoords.y = physicalPoint.getY();
        pointerCoords.setAxisValue(10, i);
        pointerCoords.setAxisValue(9, i2);
        return a(MotionEvent.obtain(this.e, uptimeMillis, 8, 1, this.g, this.h, 0, 0, 1.0f, 1.0f, -1, 0, 8194, 0));
    }

    private boolean a(int i, int i2, int i3, int i4) {
        long uptimeMillis = SystemClock.uptimeMillis();
        return a(new KeyEvent(uptimeMillis, uptimeMillis, i, i2, i3, i4, -1, 0, 0, 257));
    }

    private boolean a(int i) {
        return a(0, i, 0, 0) && a(1, i, 0, 0);
    }

    private boolean a(InputEvent inputEvent) {
        return this.a.injectInputEvent(inputEvent, 0);
    }

    private boolean c() {
        return a(this.a.isScreenOn() ? 4 : 26);
    }
}
