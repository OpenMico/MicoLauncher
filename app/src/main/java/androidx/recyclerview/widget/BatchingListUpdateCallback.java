package androidx.recyclerview.widget;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class BatchingListUpdateCallback implements ListUpdateCallback {
    final ListUpdateCallback a;
    int b = 0;
    int c = -1;
    int d = -1;
    Object e = null;

    public BatchingListUpdateCallback(@NonNull ListUpdateCallback listUpdateCallback) {
        this.a = listUpdateCallback;
    }

    public void dispatchLastEvent() {
        int i = this.b;
        if (i != 0) {
            switch (i) {
                case 1:
                    this.a.onInserted(this.c, this.d);
                    break;
                case 2:
                    this.a.onRemoved(this.c, this.d);
                    break;
                case 3:
                    this.a.onChanged(this.c, this.d, this.e);
                    break;
            }
            this.e = null;
            this.b = 0;
        }
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i, int i2) {
        int i3;
        if (this.b == 1 && i >= (i3 = this.c)) {
            int i4 = this.d;
            if (i <= i3 + i4) {
                this.d = i4 + i2;
                this.c = Math.min(i, i3);
                return;
            }
        }
        dispatchLastEvent();
        this.c = i;
        this.d = i2;
        this.b = 1;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        int i3;
        if (this.b != 2 || (i3 = this.c) < i || i3 > i + i2) {
            dispatchLastEvent();
            this.c = i;
            this.d = i2;
            this.b = 2;
            return;
        }
        this.d += i2;
        this.c = i;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i, int i2) {
        dispatchLastEvent();
        this.a.onMoved(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i, int i2, Object obj) {
        int i3;
        if (this.b == 3) {
            int i4 = this.c;
            int i5 = this.d;
            if (i <= i4 + i5 && (i3 = i + i2) >= i4 && this.e == obj) {
                this.c = Math.min(i, i4);
                this.d = Math.max(i5 + i4, i3) - this.c;
                return;
            }
        }
        dispatchLastEvent();
        this.c = i;
        this.d = i2;
        this.e = obj;
        this.b = 3;
    }
}
