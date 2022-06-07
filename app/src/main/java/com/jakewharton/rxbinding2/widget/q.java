package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_SeekBarProgressChangeEvent.java */
/* loaded from: classes2.dex */
final class q extends SeekBarProgressChangeEvent {
    private final SeekBar a;
    private final int b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(SeekBar seekBar, int i, boolean z) {
        if (seekBar != null) {
            this.a = seekBar;
            this.b = i;
            this.c = z;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarChangeEvent
    @NonNull
    public SeekBar view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarProgressChangeEvent
    public int progress() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarProgressChangeEvent
    public boolean fromUser() {
        return this.c;
    }

    public String toString() {
        return "SeekBarProgressChangeEvent{view=" + this.a + ", progress=" + this.b + ", fromUser=" + this.c + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SeekBarProgressChangeEvent)) {
            return false;
        }
        SeekBarProgressChangeEvent seekBarProgressChangeEvent = (SeekBarProgressChangeEvent) obj;
        return this.a.equals(seekBarProgressChangeEvent.view()) && this.b == seekBarProgressChangeEvent.progress() && this.c == seekBarProgressChangeEvent.fromUser();
    }

    public int hashCode() {
        return ((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b) * 1000003) ^ (this.c ? 1231 : 1237);
    }
}
