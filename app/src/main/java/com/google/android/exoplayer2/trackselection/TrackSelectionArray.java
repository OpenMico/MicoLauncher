package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class TrackSelectionArray {
    private final TrackSelection[] a;
    private int b;
    public final int length;

    public TrackSelectionArray(TrackSelection... trackSelectionArr) {
        this.a = trackSelectionArr;
        this.length = trackSelectionArr.length;
    }

    @Nullable
    public TrackSelection get(int i) {
        return this.a[i];
    }

    public TrackSelection[] getAll() {
        return (TrackSelection[]) this.a.clone();
    }

    public int hashCode() {
        if (this.b == 0) {
            this.b = 527 + Arrays.hashCode(this.a);
        }
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.a, ((TrackSelectionArray) obj).a);
    }
}
