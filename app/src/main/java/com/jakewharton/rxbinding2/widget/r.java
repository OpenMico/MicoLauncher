package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_SeekBarStartChangeEvent.java */
/* loaded from: classes2.dex */
final class r extends SeekBarStartChangeEvent {
    private final SeekBar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(SeekBar seekBar) {
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
        return "SeekBarStartChangeEvent{view=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SeekBarStartChangeEvent) {
            return this.a.equals(((SeekBarStartChangeEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
