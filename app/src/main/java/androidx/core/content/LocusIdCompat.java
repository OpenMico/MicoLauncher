package androidx.core.content;

import android.content.LocusId;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public final class LocusIdCompat {
    private final String a;
    private final LocusId b;

    public LocusIdCompat(@NonNull String str) {
        this.a = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
        if (Build.VERSION.SDK_INT >= 29) {
            this.b = a.a(str);
        } else {
            this.b = null;
        }
    }

    @NonNull
    public String getId() {
        return this.a;
    }

    public int hashCode() {
        String str = this.a;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocusIdCompat locusIdCompat = (LocusIdCompat) obj;
        String str = this.a;
        if (str == null) {
            return locusIdCompat.a == null;
        }
        return str.equals(locusIdCompat.a);
    }

    @NonNull
    public String toString() {
        return "LocusIdCompat[" + a() + "]";
    }

    @NonNull
    @RequiresApi(29)
    public LocusId toLocusId() {
        return this.b;
    }

    @NonNull
    @RequiresApi(29)
    public static LocusIdCompat toLocusIdCompat(@NonNull LocusId locusId) {
        Preconditions.checkNotNull(locusId, "locusId cannot be null");
        return new LocusIdCompat((String) Preconditions.checkStringNotEmpty(a.a(locusId), "id cannot be empty"));
    }

    @NonNull
    private String a() {
        int length = this.a.length();
        return length + "_chars";
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    private static class a {
        @NonNull
        static LocusId a(@NonNull String str) {
            return new LocusId(str);
        }

        @NonNull
        static String a(@NonNull LocusId locusId) {
            return locusId.getId();
        }
    }
}
