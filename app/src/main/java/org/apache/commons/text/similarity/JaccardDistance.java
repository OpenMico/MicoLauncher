package org.apache.commons.text.similarity;

/* loaded from: classes5.dex */
public class JaccardDistance implements EditDistance<Double> {
    private final JaccardSimilarity a = new JaccardSimilarity();

    @Override // org.apache.commons.text.similarity.EditDistance, org.apache.commons.text.similarity.SimilarityScore
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != null && charSequence2 != null) {
            return Double.valueOf(Math.round((1.0d - this.a.apply(charSequence, charSequence2).doubleValue()) * 100.0d) / 100.0d);
        }
        throw new IllegalArgumentException("Input cannot be null");
    }
}
