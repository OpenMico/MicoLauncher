package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_RatingBarChangeEvent.java */
/* loaded from: classes2.dex */
final class o extends RatingBarChangeEvent {
    private final RatingBar a;
    private final float b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(RatingBar ratingBar, float f, boolean z) {
        if (ratingBar != null) {
            this.a = ratingBar;
            this.b = f;
            this.c = z;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    @NonNull
    public RatingBar view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    public float rating() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    public boolean fromUser() {
        return this.c;
    }

    public String toString() {
        return "RatingBarChangeEvent{view=" + this.a + ", rating=" + this.b + ", fromUser=" + this.c + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RatingBarChangeEvent)) {
            return false;
        }
        RatingBarChangeEvent ratingBarChangeEvent = (RatingBarChangeEvent) obj;
        return this.a.equals(ratingBarChangeEvent.view()) && Float.floatToIntBits(this.b) == Float.floatToIntBits(ratingBarChangeEvent.rating()) && this.c == ratingBarChangeEvent.fromUser();
    }

    public int hashCode() {
        return ((((this.a.hashCode() ^ 1000003) * 1000003) ^ Float.floatToIntBits(this.b)) * 1000003) ^ (this.c ? 1231 : 1237);
    }
}
