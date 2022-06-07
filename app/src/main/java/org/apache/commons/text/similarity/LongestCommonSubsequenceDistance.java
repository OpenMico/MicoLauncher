package org.apache.commons.text.similarity;

/* loaded from: classes5.dex */
public class LongestCommonSubsequenceDistance implements EditDistance<Integer> {
    private final LongestCommonSubsequence a = new LongestCommonSubsequence();

    @Override // org.apache.commons.text.similarity.EditDistance, org.apache.commons.text.similarity.SimilarityScore
    public Integer apply(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != null && charSequence2 != null) {
            return Integer.valueOf((charSequence.length() + charSequence2.length()) - (this.a.apply(charSequence, charSequence2).intValue() * 2));
        }
        throw new IllegalArgumentException("Inputs must not be null");
    }
}
