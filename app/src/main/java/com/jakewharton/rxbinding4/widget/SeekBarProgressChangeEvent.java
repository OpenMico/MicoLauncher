package com.jakewharton.rxbinding4.widget;

import android.widget.SeekBar;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SeekBarChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SeekBarProgressChangeEvent;", "Lcom/jakewharton/rxbinding4/widget/SeekBarChangeEvent;", OneTrack.Event.VIEW, "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "(Landroid/widget/SeekBar;IZ)V", "getFromUser", "()Z", "getProgress", "()I", "getView", "()Landroid/widget/SeekBar;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class SeekBarProgressChangeEvent extends SeekBarChangeEvent {
    @NotNull
    private final SeekBar a;
    private final int b;
    private final boolean c;

    public static /* synthetic */ SeekBarProgressChangeEvent copy$default(SeekBarProgressChangeEvent seekBarProgressChangeEvent, SeekBar seekBar, int i, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            seekBar = seekBarProgressChangeEvent.getView();
        }
        if ((i2 & 2) != 0) {
            i = seekBarProgressChangeEvent.b;
        }
        if ((i2 & 4) != 0) {
            z = seekBarProgressChangeEvent.c;
        }
        return seekBarProgressChangeEvent.copy(seekBar, i, z);
    }

    @NotNull
    public final SeekBar component1() {
        return getView();
    }

    public final int component2() {
        return this.b;
    }

    public final boolean component3() {
        return this.c;
    }

    @NotNull
    public final SeekBarProgressChangeEvent copy(@NotNull SeekBar view, int i, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new SeekBarProgressChangeEvent(view, i, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeekBarProgressChangeEvent)) {
            return false;
        }
        SeekBarProgressChangeEvent seekBarProgressChangeEvent = (SeekBarProgressChangeEvent) obj;
        return Intrinsics.areEqual(getView(), seekBarProgressChangeEvent.getView()) && this.b == seekBarProgressChangeEvent.b && this.c == seekBarProgressChangeEvent.c;
    }

    public int hashCode() {
        SeekBar view = getView();
        int hashCode = (((view != null ? view.hashCode() : 0) * 31) + Integer.hashCode(this.b)) * 31;
        boolean z = this.c;
        if (z) {
            z = true;
        }
        int i = z ? 1 : 0;
        int i2 = z ? 1 : 0;
        int i3 = z ? 1 : 0;
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "SeekBarProgressChangeEvent(view=" + getView() + ", progress=" + this.b + ", fromUser=" + this.c + ")";
    }

    @Override // com.jakewharton.rxbinding4.widget.SeekBarChangeEvent
    @NotNull
    public SeekBar getView() {
        return this.a;
    }

    public final int getProgress() {
        return this.b;
    }

    public final boolean getFromUser() {
        return this.c;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekBarProgressChangeEvent(@NotNull SeekBar view, int i, boolean z) {
        super(null);
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = i;
        this.c = z;
    }
}
