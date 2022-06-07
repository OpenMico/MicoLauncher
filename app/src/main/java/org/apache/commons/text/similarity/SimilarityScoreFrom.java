package org.apache.commons.text.similarity;

import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class SimilarityScoreFrom<R> {
    private final SimilarityScore<R> a;
    private final CharSequence b;

    public SimilarityScoreFrom(SimilarityScore<R> similarityScore, CharSequence charSequence) {
        Validate.isTrue(similarityScore != null, "The edit distance may not be null.", new Object[0]);
        this.a = similarityScore;
        this.b = charSequence;
    }

    public R apply(CharSequence charSequence) {
        return this.a.apply(this.b, charSequence);
    }

    public CharSequence getLeft() {
        return this.b;
    }

    public SimilarityScore<R> getSimilarityScore() {
        return this.a;
    }
}
