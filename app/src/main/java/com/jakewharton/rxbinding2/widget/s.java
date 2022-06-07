package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_SeekBarStopChangeEvent.java */
/* loaded from: classes2.dex */
final class s extends SeekBarStopChangeEvent {
    private final SeekBar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(SeekBar seekBar) {
        if (seekBar != null) {
            this.a = seekBar;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarChangeEvent
    @NonNull
    public SeekBar view() {
        return this.a;
    }

    public String toString() {
        return "SeekBarStopChangeEvent{view=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SeekBarStopChangeEvent) {
            return this.a.equals(((SeekBarStopChangeEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
