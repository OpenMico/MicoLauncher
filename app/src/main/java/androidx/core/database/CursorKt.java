package androidx.core.database;

import android.database.Cursor;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Cursor.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u0007\u001a\u001c\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\n\u001a\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\f\u001a\u001c\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u000f\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u0012\u001a\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¨\u0006\u0015"}, d2 = {"getBlobOrNull", "", "Landroid/database/Cursor;", MiSoundBoxCommandExtras.INDEX, "", "getDoubleOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Double;", "getFloatOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Float;", "getIntOrNull", "(Landroid/database/Cursor;I)Ljava/lang/Integer;", "getLongOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Long;", "getShortOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Short;", "getStringOrNull", "", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CursorKt {
    @Nullable
    public static final byte[] getBlobOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return cursor.getBlob(i);
    }

    @Nullable
    public static final Double getDoubleOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return Double.valueOf(cursor.getDouble(i));
    }

    @Nullable
    public static final Float getFloatOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return Float.valueOf(cursor.getFloat(i));
    }

    @Nullable
    public static final Integer getIntOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return Integer.valueOf(cursor.getInt(i));
    }

    @Nullable
    public static final Long getLongOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i));
    }

    @Nullable
    public static final Short getShortOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return Short.valueOf(cursor.getShort(i));
    }

    @Nullable
    public static final String getStringOrNull(@NotNull Cursor cursor, int i) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        if (cursor.isNull(i)) {
            return null;
        }
        return cursor.getString(i);
    }
}
