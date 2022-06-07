package com.jakewharton.rxbinding4.widget;

import android.widget.RatingBar;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RatingBarRatingChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/RatingBarChangeEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/RatingBar;", SkillStore.SkillDetailSection.TYPE_RATING, "", "fromUser", "", "(Landroid/widget/RatingBar;FZ)V", "getFromUser", "()Z", "getRating", "()F", "getView", "()Landroid/widget/RatingBar;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RatingBarChangeEvent {
    @NotNull
    private final RatingBar a;
    private final float b;
    private final boolean c;

    public static /* synthetic */ RatingBarChangeEvent copy$default(RatingBarChangeEvent ratingBarChangeEvent, RatingBar ratingBar, float f, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            ratingBar = ratingBarChangeEvent.a;
        }
        if ((i & 2) != 0) {
            f = ratingBarChangeEvent.b;
        }
        if ((i & 4) != 0) {
            z = ratingBarChangeEvent.c;
        }
        return ratingBarChangeEvent.copy(ratingBar, f, z);
    }

    @NotNull
    public final RatingBar component1() {
        return this.a;
    }

    public final float component2() {
        return this.b;
    }

    public final boolean component3() {
        return this.c;
    }

    @NotNull
    public final RatingBarChangeEvent copy(@NotNull RatingBar view, float f, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new RatingBarChangeEvent(view, f, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RatingBarChangeEvent)) {
            return false;
        }
        RatingBarChangeEvent ratingBarChangeEvent = (RatingBarChangeEvent) obj;
        return Intrinsics.areEqual(this.a, ratingBarChangeEvent.a) && Float.compare(this.b, ratingBarChangeEvent.b) == 0 && this.c == ratingBarChangeEvent.c;
    }

    public int hashCode() {
        RatingBar ratingBar = this.a;
        int hashCode = (((ratingBar != null ? ratingBar.hashCode() : 0) * 31) + Float.hashCode(this.b)) * 31;
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
        return "RatingBarChangeEvent(view=" + this.a + ", rating=" + this.b + ", fromUser=" + this.c + ")";
    }

    public RatingBarChangeEvent(@NotNull RatingBar view, float f, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = f;
        this.c = z;
    }

    @NotNull
    public final RatingBar getView() {
        return this.a;
    }

    public final float getRating() {
        return this.b;
    }

    public final boolean getFromUser() {
        return this.c;
    }
}
