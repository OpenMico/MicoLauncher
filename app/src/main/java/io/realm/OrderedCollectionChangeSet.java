package io.realm;

import java.util.Locale;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public interface OrderedCollectionChangeSet {

    /* loaded from: classes5.dex */
    public enum State {
        INITIAL,
        UPDATE,
        ERROR
    }

    Range[] getChangeRanges();

    int[] getChanges();

    Range[] getDeletionRanges();

    int[] getDeletions();

    @Nullable
    Throwable getError();

    Range[] getInsertionRanges();

    int[] getInsertions();

    State getState();

    boolean isCompleteResult();

    /* loaded from: classes5.dex */
    public static class Range {
        public final int length;
        public final int startIndex;

        public Range(int i, int i2) {
            this.startIndex = i;
            this.length = i2;
        }

        public String toString() {
            return String.format(Locale.ENGLISH, "startIndex: %d, length: %d", Integer.valueOf(this.startIndex), Integer.valueOf(this.length));
        }
    }
}
