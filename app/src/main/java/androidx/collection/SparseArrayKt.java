package androidx.collection;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseArray.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010(\n\u0000\u001a!\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\u0086\n\u001aT\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000326\u0010\u000b\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\n0\fH\u0086\bø\u0001\u0000\u001a.\u0010\u0010\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u0002H\u0002H\u0086\b¢\u0006\u0002\u0010\u0012\u001a7\u0010\u0013\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0014H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u0019\u0010\u0016\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\b\u001a\u0016\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a-\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\u0002\u001a-\u0010\u001b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a.\u0010\u001d\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u0002H\u0002H\u0086\n¢\u0006\u0002\u0010\u001e\u001a\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00020 \"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\"\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006!"}, d2 = {"size", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/collection/SparseArrayCompat;", "getSize", "(Landroidx/collection/SparseArrayCompat;)I", "contains", "", "key", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", b.p, "getOrDefault", "defaultValue", "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroidx/collection/SparseArrayCompat;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "remove", "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)Z", BluetoothConstants.SET, "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)V", "valueIterator", "", "collection-ktx"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class SparseArrayKt {
    public static final <T> int getSize(@NotNull SparseArrayCompat<T> size) {
        Intrinsics.checkNotNullParameter(size, "$this$size");
        return size.size();
    }

    public static final <T> boolean contains(@NotNull SparseArrayCompat<T> contains, int i) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.containsKey(i);
    }

    public static final <T> void set(@NotNull SparseArrayCompat<T> set, int i, T t) {
        Intrinsics.checkNotNullParameter(set, "$this$set");
        set.put(i, t);
    }

    @NotNull
    public static final <T> SparseArrayCompat<T> plus(@NotNull SparseArrayCompat<T> plus, @NotNull SparseArrayCompat<T> other) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(other, "other");
        SparseArrayCompat<T> sparseArrayCompat = new SparseArrayCompat<>(plus.size() + other.size());
        sparseArrayCompat.putAll(plus);
        sparseArrayCompat.putAll(other);
        return sparseArrayCompat;
    }

    public static final <T> T getOrDefault(@NotNull SparseArrayCompat<T> getOrDefault, int i, T t) {
        Intrinsics.checkNotNullParameter(getOrDefault, "$this$getOrDefault");
        return getOrDefault.get(i, t);
    }

    public static final <T> T getOrElse(@NotNull SparseArrayCompat<T> getOrElse, int i, @NotNull Function0<? extends T> defaultValue) {
        Intrinsics.checkNotNullParameter(getOrElse, "$this$getOrElse");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        T t = getOrElse.get(i);
        return t != null ? t : (T) defaultValue.invoke();
    }

    public static final <T> boolean isNotEmpty(@NotNull SparseArrayCompat<T> isNotEmpty) {
        Intrinsics.checkNotNullParameter(isNotEmpty, "$this$isNotEmpty");
        return !isNotEmpty.isEmpty();
    }

    public static final <T> void forEach(@NotNull SparseArrayCompat<T> forEach, @NotNull Function2<? super Integer, ? super T, Unit> action) {
        Intrinsics.checkNotNullParameter(forEach, "$this$forEach");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = forEach.size();
        for (int i = 0; i < size; i++) {
            action.invoke(Integer.valueOf(forEach.keyAt(i)), forEach.valueAt(i));
        }
    }

    @NotNull
    public static final <T> IntIterator keyIterator(@NotNull final SparseArrayCompat<T> keyIterator) {
        Intrinsics.checkNotNullParameter(keyIterator, "$this$keyIterator");
        return new IntIterator() { // from class: androidx.collection.SparseArrayKt$keyIterator$1
            private int b;

            public final int getIndex() {
                return this.b;
            }

            public final void setIndex(int i) {
                this.b = i;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b < keyIterator.size();
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                SparseArrayCompat sparseArrayCompat = keyIterator;
                int i = this.b;
                this.b = i + 1;
                return sparseArrayCompat.keyAt(i);
            }
        };
    }

    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull SparseArrayCompat<T> valueIterator) {
        Intrinsics.checkNotNullParameter(valueIterator, "$this$valueIterator");
        return new SparseArrayKt$valueIterator$1(valueIterator);
    }
}
