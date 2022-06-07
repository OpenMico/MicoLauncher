package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes2.dex */
public interface SeekMap {
    long getDurationUs();

    SeekPoints getSeekPoints(long j);

    boolean isSeekable();

    /* loaded from: classes2.dex */
    public static class Unseekable implements SeekMap {
        private final long a;
        private final SeekPoints b;

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return false;
        }

        public Unseekable(long j) {
            this(j, 0L);
        }

        public Unseekable(long j, long j2) {
            this.a = j;
            this.b = new SeekPoints(j2 == 0 ? SeekPoint.START : new SeekPoint(0L, j2));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekPoints getSeekPoints(long j) {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    public static final class SeekPoints {
        public final SeekPoint first;
        public final SeekPoint second;

        public SeekPoints(SeekPoint seekPoint) {
            this(seekPoint, seekPoint);
        }

        public SeekPoints(SeekPoint seekPoint, SeekPoint seekPoint2) {
            this.first = (SeekPoint) Assertions.checkNotNull(seekPoint);
            this.second = (SeekPoint) Assertions.checkNotNull(seekPoint2);
        }

        public String toString() {
            String str;
            String valueOf = String.valueOf(this.first);
            if (this.first.equals(this.second)) {
                str = "";
            } else {
                String valueOf2 = String.valueOf(this.second);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 2);
                sb.append(", ");
                sb.append(valueOf2);
                str = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 2 + String.valueOf(str).length());
            sb2.append("[");
            sb2.append(valueOf);
            sb2.append(str);
            sb2.append("]");
            return sb2.toString();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SeekPoints seekPoints = (SeekPoints) obj;
            return this.first.equals(seekPoints.first) && this.second.equals(seekPoints.second);
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }
    }
}
