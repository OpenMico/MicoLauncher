package androidx.core.util;

import android.util.SparseIntArray;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseIntArray.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\u0086\n\u001a\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\u0086\b\u001a\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\n\u001a\u00020\u0001H\u0086\b\u001aH\u0010\u000b\u001a\u00020\f*\u00020\u000226\u0010\r\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\f0\u000eH\u0086\bø\u0001\u0000\u001a\u001d\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0086\b\u001a&\u0010\u0013\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\u0086\bø\u0001\u0000\u001a\r\u0010\u0015\u001a\u00020\u0006*\u00020\u0002H\u0086\b\u001a\r\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\u0086\b\u001a\n\u0010\u0017\u001a\u00020\u0018*\u00020\u0002\u001a\u0015\u0010\u0019\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002H\u0086\u0002\u001a\u0012\u0010\u001b\u001a\u00020\f*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u001a\u0010\u001c\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001\u001a\u001d\u0010\u001d\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001H\u0086\n\u001a\n\u0010\u001e\u001a\u00020\u0018*\u00020\u0002\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001f"}, d2 = {"size", "", "Landroid/util/SparseIntArray;", "getSize", "(Landroid/util/SparseIntArray;)I", "contains", "", "key", "containsKey", "containsValue", b.p, "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", BluetoothConstants.SET, "valueIterator", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SparseIntArrayKt {
    public static final int getSize(@NotNull SparseIntArray sparseIntArray) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.size();
    }

    public static final boolean contains(@NotNull SparseIntArray sparseIntArray, int i) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.indexOfKey(i) >= 0;
    }

    public static final void set(@NotNull SparseIntArray sparseIntArray, int i, int i2) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        sparseIntArray.put(i, i2);
    }

    @NotNull
    public static final SparseIntArray plus(@NotNull SparseIntArray sparseIntArray, @NotNull SparseIntArray other) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        SparseIntArray sparseIntArray2 = new SparseIntArray(sparseIntArray.size() + other.size());
        putAll(sparseIntArray2, sparseIntArray);
        putAll(sparseIntArray2, other);
        return sparseIntArray2;
    }

    public static final boolean containsKey(@NotNull SparseIntArray sparseIntArray, int i) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.indexOfKey(i) >= 0;
    }

    public static final boolean containsValue(@NotNull SparseIntArray sparseIntArray, int i) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.indexOfValue(i) >= 0;
    }

    public static final int getOrDefault(@NotNull SparseIntArray sparseIntArray, int i, int i2) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.get(i, i2);
    }

    public static final int getOrElse(@NotNull SparseIntArray sparseIntArray, int i, @NotNull Function0<Integer> defaultValue) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        int indexOfKey = sparseIntArray.indexOfKey(i);
        return indexOfKey >= 0 ? sparseIntArray.valueAt(indexOfKey) : defaultValue.invoke().intValue();
    }

    public static final boolean isEmpty(@NotNull SparseIntArray sparseIntArray) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.size() == 0;
    }

    public static final boolean isNotEmpty(@NotNull SparseIntArray sparseIntArray) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return sparseIntArray.size() != 0;
    }

    public static final boolean remove(@NotNull SparseIntArray sparseIntArray, int i, int i2) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        int indexOfKey = sparseIntArray.indexOfKey(i);
        if (indexOfKey < 0 || i2 != sparseIntArray.valueAt(indexOfKey)) {
            return false;
        }
        sparseIntArray.removeAt(indexOfKey);
        return true;
    }

    public static final void forEach(@NotNull SparseIntArray sparseIntArray, @NotNull Function2<? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = sparseIntArray.size();
        if (size > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                action.invoke(Integer.valueOf(sparseIntArray.keyAt(i)), Integer.valueOf(sparseIntArray.valueAt(i)));
                if (i2 < size) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }

    @NotNull
    public static final IntIterator keyIterator(@NotNull final SparseIntArray sparseIntArray) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return new IntIterator() { // from class: androidx.core.util.SparseIntArrayKt$keyIterator$1
            private int b;

            public final int getIndex() {
                return this.b;
            }

            public final void setIndex(int i) {
                this.b = i;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b < sparseIntArray.size();
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                SparseIntArray sparseIntArray2 = sparseIntArray;
                int i = this.b;
                this.b = i + 1;
                return sparseIntArray2.keyAt(i);
            }
        };
    }

    @NotNull
    public static final IntIterator valueIterator(@NotNull final SparseIntArray sparseIntArray) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        return new IntIterator() { // from class: androidx.core.util.SparseIntArrayKt$valueIterator$1
            private int b;

            public final int getIndex() {
                return this.b;
            }

            public final void setIndex(int i) {
                this.b = i;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b < sparseIntArray.size();
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                SparseIntArray sparseIntArray2 = sparseIntArray;
                int i = this.b;
                this.b = i + 1;
                return sparseIntArray2.valueAt(i);
            }
        };
    }

    public static final void putAll(@NotNull SparseIntArray sparseIntArray, @NotNull SparseIntArray other) {
        Intrinsics.checkNotNullParameter(sparseIntArray, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int size = other.size();
        if (size > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                sparseIntArray.put(other.keyAt(i), other.valueAt(i));
                if (i2 < size) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }
}
