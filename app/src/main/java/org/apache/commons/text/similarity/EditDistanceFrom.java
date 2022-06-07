package org.apache.commons.text.similarity;

import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class EditDistanceFrom<R> {
    private final EditDistance<R> a;
    private final CharSequence b;

    public EditDistanceFrom(EditDistance<R> editDistance, CharSequence charSequence) {
        Validate.isTrue(editDistance != null, "The edit distance may not be null.", new Object[0]);
        this.a = editDistance;
        this.b = charSequence;
    }

    public R apply(CharSequence charSequence) {
        return this.a.apply(this.b, charSequence);
    }

    public CharSequence getLeft() {
        return this.b;
    }

    public EditDistance<R> getEditDistance() {
        return this.a;
    }
}
