package androidx.collection;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: LongSparseArray.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010(\n\u0000\u001a!\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0086\n\u001aT\u0010\n\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000326\u0010\f\u001a2\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001a.\u0010\u0011\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u0002H\u0002H\u0086\b¢\u0006\u0002\u0010\u0013\u001a7\u0010\u0014\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0019\u0010\u0017\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\b\u001a\u0016\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a-\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\u0002\u001a-\u0010\u001c\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0010\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u0010\u001d\u001a.\u0010\u001e\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0010\u001a\u0002H\u0002H\u0086\n¢\u0006\u0002\u0010\u001f\u001a\u001c\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00020!\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\"\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, d2 = {"size", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/collection/LongSparseArray;", "getSize", "(Landroidx/collection/LongSparseArray;)I", "contains", "", "key", "", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", b.p, "getOrDefault", "defaultValue", "(Landroidx/collection/LongSparseArray;JLjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroidx/collection/LongSparseArray;JLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "keyIterator", "Lkotlin/collections/LongIterator;", "plus", "other", "remove", "(Landroidx/collection/LongSparseArray;JLjava/lang/Object;)Z", BluetoothConstants.SET, "(Landroidx/collection/LongSparseArray;JLjava/lang/Object;)V", "valueIterator", "", "collection-ktx"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class LongSparseArrayKt {
    public static final <T> int getSize(@NotNull LongSparseArray<T> size) {
        Intrinsics.checkNotNullParameter(size, "$this$size");
        return size.size();
    }

    public static final <T> boolean contains(@NotNull LongSparseArray<T> contains, long j) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.containsKey(j);
    }

    public static final <T> void set(@NotNull LongSparseArray<T> set, long j, T t) {
        Intrinsics.checkNotNullParameter(set, "$this$set");
        set.put(j, t);
    }

    @NotNull
    public static final <T> LongSparseArray<T> plus(@NotNull LongSparseArray<T> plus, @NotNull LongSparseArray<T> other) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(other, "other");
        LongSparseArray<T> longSparseArray = new LongSparseArray<>(plus.size() + other.size());
        longSparseArray.putAll(plus);
        longSparseArray.putAll(other);
        return longSparseArray;
    }

    public static final <T> T getOrDefault(@NotNull LongSparseArray<T> getOrDefault, long j, T t) {
        Intrinsics.checkNotNullParameter(getOrDefault, "$this$getOrDefault");
        return getOrDefault.get(j, t);
    }

    public static final <T> T getOrElse(@NotNull LongSparseArray<T> getOrElse, long j, @NotNull Function0<? extends T> defaultValue) {
        Intrinsics.checkNotNullParameter(getOrElse, "$this$getOrElse");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        T t = getOrElse.get(j);
        return t != null ? t : (T) defaultValue.invoke();
    }

    public static final <T> boolean isNotEmpty(@NotNull LongSparseArray<T> isNotEmpty) {
        Intrinsics.checkNotNullParameter(isNotEmpty, "$this$isNotEmpty");
        return !isNotEmpty.isEmpty();
    }

    public static final <T> void forEach(@NotNull LongSparseArray<T> forEach, @NotNull Function2<? super Long, ? super T, Unit> action) {
        Intrinsics.checkNotNullParameter(forEach, "$this$forEach");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = forEach.size();
        for (int i = 0; i < size; i++) {
            action.invoke(Long.valueOf(forEach.keyAt(i)), forEach.valueAt(i));
        }
    }

    @NotNull
    public static final <T> LongIterator keyIterator(@NotNull final LongSparseArray<T> keyIterator) {
        Intrinsics.checkNotNullParameter(keyIterator, "$this$keyIterator");
        return new LongIterator() { // from class: androidx.collection.LongSparseArrayKt$keyIterator$1
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

            @Override // kotlin.collections.LongIterator
            public long nextLong() {
                LongSparseArray longSparseArray = keyIterator;
                int i = this.b;
                this.b = i + 1;
                return longSparseArray.keyAt(i);
            }
        };
    }

    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull LongSparseArray<T> valueIterator) {
        Intrinsics.checkNotNullParameter(valueIterator, "$this$valueIterator");
        return new LongSparseArrayKt$valueIterator$1(valueIterator);
    }
}
