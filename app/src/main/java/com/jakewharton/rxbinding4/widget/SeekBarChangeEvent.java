package com.jakewharton.rxbinding4.widget;

import android.widget.SeekBar;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: SeekBarChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SeekBarChangeEvent;", "", "()V", OneTrack.Event.VIEW, "Landroid/widget/SeekBar;", "getView", "()Landroid/widget/SeekBar;", "Lcom/jakewharton/rxbinding4/widget/SeekBarProgressChangeEvent;", "Lcom/jakewharton/rxbinding4/widget/SeekBarStartChangeEvent;", "Lcom/jakewharton/rxbinding4/widget/SeekBarStopChangeEvent;", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public abstract class SeekBarChangeEvent {
    @NotNull
    public abstract SeekBar getView();

    private SeekBarChangeEvent() {
    }

    public /* synthetic */ SeekBarChangeEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
