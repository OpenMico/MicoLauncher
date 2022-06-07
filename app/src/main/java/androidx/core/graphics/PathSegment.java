package androidx.core.graphics;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public final class PathSegment {
    private final PointF a;
    private final float b;
    private final PointF c;
    private final float d;

    public PathSegment(@NonNull PointF pointF, float f, @NonNull PointF pointF2, float f2) {
        this.a = (PointF) Preconditions.checkNotNull(pointF, "start == null");
        this.b = f;
        this.c = (PointF) Preconditions.checkNotNull(pointF2, "end == null");
        this.d = f2;
    }

    @NonNull
    public PointF getStart() {
        return this.a;
    }

    public float getStartFraction() {
        return this.b;
    }

    @NonNull
    public PointF getEnd() {
        return this.c;
    }

    public float getEndFraction() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PathSegment)) {
            return false;
        }
        PathSegment pathSegment = (PathSegment) obj;
        return Float.compare(this.b, pathSegment.b) == 0 && Float.compare(this.d, pathSegment.d) == 0 && this.a.equals(pathSegment.a) && this.c.equals(pathSegment.c);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        float f = this.b;
        int i = 0;
        int floatToIntBits = (((hashCode + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31) + this.c.hashCode()) * 31;
        float f2 = this.d;
        if (f2 != 0.0f) {
            i = Float.floatToIntBits(f2);
        }
        return floatToIntBits + i;
    }

    public String toString() {
        return "PathSegment{start=" + this.a + ", startFraction=" + this.b + ", end=" + this.c + ", endFraction=" + this.d + '}';
    }
}
