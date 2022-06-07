package com.jakewharton.rxbinding4.widget;

import android.text.Editable;
import android.widget.TextView;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextViewAfterTextChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/TextViewAfterTextChangeEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/TextView;", "editable", "Landroid/text/Editable;", "(Landroid/widget/TextView;Landroid/text/Editable;)V", "getEditable", "()Landroid/text/Editable;", "getView", "()Landroid/widget/TextView;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class TextViewAfterTextChangeEvent {
    @NotNull
    private final TextView a;
    @Nullable
    private final Editable b;

    public static /* synthetic */ TextViewAfterTextChangeEvent copy$default(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent, TextView textView, Editable editable, int i, Object obj) {
        if ((i & 1) != 0) {
            textView = textViewAfterTextChangeEvent.a;
        }
        if ((i & 2) != 0) {
            editable = textViewAfterTextChangeEvent.b;
        }
        return textViewAfterTextChangeEvent.copy(textView, editable);
    }

    @NotNull
    public final TextView component1() {
        return this.a;
    }

    @Nullable
    public final Editable component2() {
        return this.b;
    }

    @NotNull
    public final TextViewAfterTextChangeEvent copy(@NotNull TextView view, @Nullable Editable editable) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new TextViewAfterTextChangeEvent(view, editable);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextViewAfterTextChangeEvent)) {
            return false;
        }
        TextViewAfterTextChangeEvent textViewAfterTextChangeEvent = (TextViewAfterTextChangeEvent) obj;
        return Intrinsics.areEqual(this.a, textViewAfterTextChangeEvent.a) && Intrinsics.areEqual(this.b, textViewAfterTextChangeEvent.b);
    }

    public int hashCode() {
        TextView textView = this.a;
        int i = 0;
        int hashCode = (textView != null ? textView.hashCode() : 0) * 31;
        Editable editable = this.b;
        if (editable != null) {
            i = editable.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "TextViewAfterTextChangeEvent(view=" + this.a + ", editable=" + ((Object) this.b) + ")";
    }

    public TextViewAfterTextChangeEvent(@NotNull TextView view, @Nullable Editable editable) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = editable;
    }

    @NotNull
    public final TextView getView() {
        return this.a;
    }

    @Nullable
    public final Editable getEditable() {
        return this.b;
    }
}
