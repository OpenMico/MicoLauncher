package org.apache.commons.text.similarity;

/* loaded from: classes5.dex */
public class CosineDistance implements EditDistance<Double> {
    private final c<CharSequence> a = new b();
    private final CosineSimilarity b = new CosineSimilarity();

    @Override // org.apache.commons.text.similarity.EditDistance, org.apache.commons.text.similarity.SimilarityScore
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        return Double.valueOf(1.0d - this.b.cosineSimilarity(a.a(this.a.b(charSequence)), a.a(this.a.b(charSequence2))).doubleValue());
    }
}
