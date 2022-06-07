package com.jakewharton.rxbinding4.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextViewEditorActionEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/TextViewEditorActionEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/TextView;", "actionId", "", "keyEvent", "Landroid/view/KeyEvent;", "(Landroid/widget/TextView;ILandroid/view/KeyEvent;)V", "getActionId", "()I", "getKeyEvent", "()Landroid/view/KeyEvent;", "getView", "()Landroid/widget/TextView;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class TextViewEditorActionEvent {
    @NotNull
    private final TextView a;
    private final int b;
    @Nullable
    private final KeyEvent c;

    public static /* synthetic */ TextViewEditorActionEvent copy$default(TextViewEditorActionEvent textViewEditorActionEvent, TextView textView, int i, KeyEvent keyEvent, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            textView = textViewEditorActionEvent.a;
        }
        if ((i2 & 2) != 0) {
            i = textViewEditorActionEvent.b;
        }
        if ((i2 & 4) != 0) {
            keyEvent = textViewEditorActionEvent.c;
        }
        return textViewEditorActionEvent.copy(textView, i, keyEvent);
    }

    @NotNull
    public final TextView component1() {
        return this.a;
    }

    public final int component2() {
        return this.b;
    }

    @Nullable
    public final KeyEvent component3() {
        return this.c;
    }

    @NotNull
    public final TextViewEditorActionEvent copy(@NotNull TextView view, int i, @Nullable KeyEvent keyEvent) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new TextViewEditorActionEvent(view, i, keyEvent);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextViewEditorActionEvent)) {
            return false;
        }
        TextViewEditorActionEvent textViewEditorActionEvent = (TextViewEditorActionEvent) obj;
        return Intrinsics.areEqual(this.a, textViewEditorActionEvent.a) && this.b == textViewEditorActionEvent.b && Intrinsics.areEqual(this.c, textViewEditorActionEvent.c);
    }

    public int hashCode() {
        TextView textView = this.a;
        int i = 0;
        int hashCode = (((textView != null ? textView.hashCode() : 0) * 31) + Integer.hashCode(this.b)) * 31;
        KeyEvent keyEvent = this.c;
        if (keyEvent != null) {
            i = keyEvent.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "TextViewEditorActionEvent(view=" + this.a + ", actionId=" + this.b + ", keyEvent=" + this.c + ")";
    }

    public TextViewEditorActionEvent(@NotNull TextView view, int i, @Nullable KeyEvent keyEvent) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = i;
        this.c = keyEvent;
    }

    @NotNull
    public final TextView getView() {
        return this.a;
    }

    public final int getActionId() {
        return this.b;
    }

    @Nullable
    public final KeyEvent getKeyEvent() {
        return this.c;
    }
}
