package com.xiaomi.miplay.mylibrary.mirror;

import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PointersState {
    public static final int MAX_POINTERS = 10;
    private final List<Pointer> a = new ArrayList();

    private int a(long j) {
        for (int i = 0; i < this.a.size(); i++) {
            if (this.a.get(i).getId() == j) {
                return i;
            }
        }
        return -1;
    }

    private boolean a(int i) {
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            if (this.a.get(i2).getLocalId() == i) {
                return false;
            }
        }
        return true;
    }

    private int a() {
        for (int i = 0; i < 10; i++) {
            if (a(i)) {
                return i;
            }
        }
        return -1;
    }

    public Pointer get(int i) {
        return this.a.get(i);
    }

    public int getPointerIndex(long j) {
        int a = a(j);
        if (a != -1) {
            return a;
        }
        if (this.a.size() >= 10) {
            return -1;
        }
        int a2 = a();
        if (a2 != -1) {
            this.a.add(new Pointer(j, a2));
            return this.a.size() - 1;
        }
        throw new AssertionError("pointers.size() < maxFingers implies that a local id is available");
    }

    public int update(MotionEvent.PointerProperties[] pointerPropertiesArr, MotionEvent.PointerCoords[] pointerCoordsArr) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            Pointer pointer = this.a.get(i);
            pointerPropertiesArr[i].id = pointer.getLocalId();
            Point point = pointer.getPoint();
            pointerCoordsArr[i].x = point.getX();
            pointerCoordsArr[i].y = point.getY();
            pointerCoordsArr[i].pressure = pointer.getPressure();
        }
        b();
        return size;
    }

    private void b() {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            if (this.a.get(size).isUp()) {
                this.a.remove(size);
            }
        }
    }
}
