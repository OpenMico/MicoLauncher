package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class Preference {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "key")
    public String mKey;
    @Nullable
    @ColumnInfo(name = "long_value")
    public Long mValue;

    public Preference(@NonNull String str, boolean z) {
        this(str, z ? 1L : 0L);
    }

    public Preference(@NonNull String str, long j) {
        this.mKey = str;
        this.mValue = Long.valueOf(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Preference)) {
            return false;
        }
        Preference preference = (Preference) obj;
        if (!this.mKey.equals(preference.mKey)) {
            return false;
        }
        Long l = this.mValue;
        return l != null ? l.equals(preference.mValue) : preference.mValue == null;
    }

    public int hashCode() {
        int hashCode = this.mKey.hashCode() * 31;
        Long l = this.mValue;
        return hashCode + (l != null ? l.hashCode() : 0);
    }
}
