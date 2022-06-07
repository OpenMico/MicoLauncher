package org.apache.commons.text.similarity;

import java.util.Objects;

/* loaded from: classes5.dex */
public class LevenshteinResults {
    private final Integer a;
    private final Integer b;
    private final Integer c;
    private final Integer d;

    public LevenshteinResults(Integer num, Integer num2, Integer num3, Integer num4) {
        this.a = num;
        this.b = num2;
        this.c = num3;
        this.d = num4;
    }

    public Integer getDistance() {
        return this.a;
    }

    public Integer getInsertCount() {
        return this.b;
    }

    public Integer getDeleteCount() {
        return this.c;
    }

    public Integer getSubstituteCount() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LevenshteinResults levenshteinResults = (LevenshteinResults) obj;
        return Objects.equals(this.a, levenshteinResults.a) && Objects.equals(this.b, levenshteinResults.b) && Objects.equals(this.c, levenshteinResults.c) && Objects.equals(this.d, levenshteinResults.d);
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    public String toString() {
        return "Distance: " + this.a + ", Insert: " + this.b + ", Delete: " + this.c + ", Substitute: " + this.d;
    }
}
