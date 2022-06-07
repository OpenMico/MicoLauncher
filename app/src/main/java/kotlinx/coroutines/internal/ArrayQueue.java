package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ArrayQueue.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0010\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010J\u0006\u0010\u0011\u001a\u00020\u000eJ\b\u0010\u0012\u001a\u00020\u000eH\u0002J\r\u0010\u0013\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0014R\u0018\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0005X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/internal/ArrayQueue;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "elements", "", "[Ljava/lang/Object;", "head", "", "isEmpty", "", "()Z", "tail", "addLast", "", "element", "(Ljava/lang/Object;)V", "clear", "ensureCapacity", "removeFirstOrNull", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public class ArrayQueue<T> {
    @NotNull
    private Object[] a = new Object[16];
    private int b;
    private int c;

    public final boolean isEmpty() {
        return this.b == this.c;
    }

    public final void addLast(@NotNull T t) {
        Object[] objArr = this.a;
        int i = this.c;
        objArr[i] = t;
        this.c = (objArr.length - 1) & (i + 1);
        if (this.c == this.b) {
            a();
        }
    }

    @Nullable
    public final T removeFirstOrNull() {
        int i = this.b;
        if (i == this.c) {
            return null;
        }
        Object[] objArr = this.a;
        T t = (T) objArr[i];
        objArr[i] = null;
        this.b = (i + 1) & (objArr.length - 1);
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null cannot be cast to non-null type T of kotlinx.coroutines.internal.ArrayQueue");
    }

    public final void clear() {
        this.b = 0;
        this.c = 0;
        this.a = new Object[this.a.length];
    }

    private final void a() {
        Object[] objArr = this.a;
        int length = objArr.length;
        Object[] objArr2 = new Object[length << 1];
        ArraysKt.copyInto$default(objArr, objArr2, 0, this.b, 0, 10, (Object) null);
        Object[] objArr3 = this.a;
        int length2 = objArr3.length;
        int i = this.b;
        ArraysKt.copyInto$default(objArr3, objArr2, length2 - i, 0, i, 4, (Object) null);
        this.a = objArr2;
        this.b = 0;
        this.c = length;
    }
}
