package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import io.realm.internal.OsCollectionChangeSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ArrayDeque.kt */
@SinceKotlin(version = "1.4")
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\b\u0007\u0018\u0000 P*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001PB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b¢\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000¢\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000¢\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010>\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u0010?\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u0016\u0010@\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010A\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010.J\u000b\u0010B\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010C\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u000b\u0010D\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010E\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010F\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010G\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010HJ\u0017\u0010I\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0000¢\u0006\u0004\bJ\u0010KJ)\u0010I\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0000¢\u0006\u0004\bJ\u0010NJ\u0015\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0016¢\u0006\u0002\u0010KJ'\u0010O\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0016¢\u0006\u0002\u0010NR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006Q"}, d2 = {"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", MiSoundBoxCommandExtras.INDEX, "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", BluetoothConstants.GET, "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", BluetoothConstants.SET, "(ILjava/lang/Object;)Ljava/lang/Object;", "testToArray", "testToArray$kotlin_stdlib", "()[Ljava/lang/Object;", ExifInterface.GPS_DIRECTION_TRUE, "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toArray", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
@WasExperimental(markerClass = {ExperimentalStdlibApi.class})
/* loaded from: classes5.dex */
public final class ArrayDeque<E> extends AbstractMutableList<E> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final Object[] d = new Object[0];
    private int a;
    private Object[] b;
    private int c;

    @Override // kotlin.collections.AbstractMutableList
    public int getSize() {
        return this.c;
    }

    public ArrayDeque(int i) {
        Object[] objArr;
        if (i == 0) {
            objArr = d;
        } else if (i > 0) {
            objArr = new Object[i];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        this.b = objArr;
    }

    public ArrayDeque() {
        this.b = d;
    }

    public ArrayDeque(@NotNull Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        Object[] array = elements.toArray(new Object[0]);
        if (array != null) {
            this.b = array;
            Object[] objArr = this.b;
            this.c = objArr.length;
            if (objArr.length == 0 ? true : z) {
                this.b = d;
                return;
            }
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    private final void a(int i) {
        if (i >= 0) {
            Object[] objArr = this.b;
            if (i > objArr.length) {
                if (objArr == d) {
                    this.b = new Object[RangesKt.coerceAtLeast(i, 10)];
                } else {
                    b(Companion.newCapacity$kotlin_stdlib(objArr.length, i));
                }
            }
        } else {
            throw new IllegalStateException("Deque is too big.");
        }
    }

    private final void b(int i) {
        Object[] objArr = new Object[i];
        Object[] objArr2 = this.b;
        ArraysKt.copyInto(objArr2, objArr, 0, this.a, objArr2.length);
        Object[] objArr3 = this.b;
        int length = objArr3.length;
        int i2 = this.a;
        ArraysKt.copyInto(objArr3, objArr, length - i2, 0, i2);
        this.a = 0;
        this.b = objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int c(int i) {
        Object[] objArr = this.b;
        return i >= objArr.length ? i - objArr.length : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int d(int i) {
        return i < 0 ? i + this.b.length : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int e(int i) {
        if (i == ArraysKt.getLastIndex(this.b)) {
            return 0;
        }
        return i + 1;
    }

    private final int f(int i) {
        return i == 0 ? ArraysKt.getLastIndex(this.b) : i - 1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return size() == 0;
    }

    public final E first() {
        if (!isEmpty()) {
            return (E) this.b[this.a];
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Nullable
    public final E firstOrNull() {
        if (isEmpty()) {
            return null;
        }
        return (E) this.b[this.a];
    }

    public final E last() {
        if (!isEmpty()) {
            return (E) this.b[c(this.a + CollectionsKt.getLastIndex(this))];
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Nullable
    public final E lastOrNull() {
        if (isEmpty()) {
            return null;
        }
        return (E) this.b[c(this.a + CollectionsKt.getLastIndex(this))];
    }

    public final void addFirst(E e) {
        a(size() + 1);
        this.a = f(this.a);
        this.b[this.a] = e;
        this.c = size() + 1;
    }

    public final void addLast(E e) {
        a(size() + 1);
        this.b[c(this.a + size())] = e;
        this.c = size() + 1;
    }

    public final E removeFirst() {
        if (!isEmpty()) {
            E e = (E) this.b[this.a];
            Object[] objArr = this.b;
            int i = this.a;
            objArr[i] = null;
            this.a = e(i);
            this.c = size() - 1;
            return e;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Nullable
    public final E removeFirstOrNull() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    public final E removeLast() {
        if (!isEmpty()) {
            int c = c(this.a + CollectionsKt.getLastIndex(this));
            E e = (E) this.b[c];
            this.b[c] = null;
            this.c = size() - 1;
            return e;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Nullable
    public final E removeLastOrNull() {
        if (isEmpty()) {
            return null;
        }
        return removeLast();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, size());
        if (i == size()) {
            addLast(e);
        } else if (i == 0) {
            addFirst(e);
        } else {
            a(size() + 1);
            int c = c(this.a + i);
            if (i < ((size() + 1) >> 1)) {
                int f = f(c);
                int f2 = f(this.a);
                int i2 = this.a;
                if (f >= i2) {
                    Object[] objArr = this.b;
                    objArr[f2] = objArr[i2];
                    ArraysKt.copyInto(objArr, objArr, i2, i2 + 1, f + 1);
                } else {
                    Object[] objArr2 = this.b;
                    ArraysKt.copyInto(objArr2, objArr2, i2 - 1, i2, objArr2.length);
                    Object[] objArr3 = this.b;
                    objArr3[objArr3.length - 1] = objArr3[0];
                    ArraysKt.copyInto(objArr3, objArr3, 0, 1, f + 1);
                }
                this.b[f] = e;
                this.a = f2;
            } else {
                int c2 = c(this.a + size());
                if (c < c2) {
                    Object[] objArr4 = this.b;
                    ArraysKt.copyInto(objArr4, objArr4, c + 1, c, c2);
                } else {
                    Object[] objArr5 = this.b;
                    ArraysKt.copyInto(objArr5, objArr5, 1, 0, c2);
                    Object[] objArr6 = this.b;
                    objArr6[0] = objArr6[objArr6.length - 1];
                    ArraysKt.copyInto(objArr6, objArr6, c + 1, c, objArr6.length - 1);
                }
                this.b[c] = e;
            }
            this.c = size() + 1;
        }
    }

    private final void a(int i, Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
        int length = this.b.length;
        while (i < length && it.hasNext()) {
            this.b[i] = it.next();
            i++;
        }
        int i2 = this.a;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.b[i3] = it.next();
        }
        this.c = size() + collection.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(@NotNull Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (elements.isEmpty()) {
            return false;
        }
        a(size() + elements.size());
        a(c(this.a + size()), elements);
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, @NotNull Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, size());
        if (elements.isEmpty()) {
            return false;
        }
        if (i == size()) {
            return addAll(elements);
        }
        a(size() + elements.size());
        int c = c(this.a + size());
        int c2 = c(this.a + i);
        int size = elements.size();
        if (i < ((size() + 1) >> 1)) {
            int i2 = this.a;
            int i3 = i2 - size;
            if (c2 < i2) {
                Object[] objArr = this.b;
                ArraysKt.copyInto(objArr, objArr, i3, i2, objArr.length);
                if (size >= c2) {
                    Object[] objArr2 = this.b;
                    ArraysKt.copyInto(objArr2, objArr2, objArr2.length - size, 0, c2);
                } else {
                    Object[] objArr3 = this.b;
                    ArraysKt.copyInto(objArr3, objArr3, objArr3.length - size, 0, size);
                    Object[] objArr4 = this.b;
                    ArraysKt.copyInto(objArr4, objArr4, 0, size, c2);
                }
            } else if (i3 >= 0) {
                Object[] objArr5 = this.b;
                ArraysKt.copyInto(objArr5, objArr5, i3, i2, c2);
            } else {
                Object[] objArr6 = this.b;
                i3 += objArr6.length;
                int i4 = c2 - i2;
                int length = objArr6.length - i3;
                if (length >= i4) {
                    ArraysKt.copyInto(objArr6, objArr6, i3, i2, c2);
                } else {
                    ArraysKt.copyInto(objArr6, objArr6, i3, i2, i2 + length);
                    Object[] objArr7 = this.b;
                    ArraysKt.copyInto(objArr7, objArr7, 0, this.a + length, c2);
                }
            }
            this.a = i3;
            a(d(c2 - size), elements);
        } else {
            int i5 = c2 + size;
            if (c2 < c) {
                int i6 = size + c;
                Object[] objArr8 = this.b;
                if (i6 <= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i5, c2, c);
                } else if (i5 >= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i5 - objArr8.length, c2, c);
                } else {
                    int length2 = c - (i6 - objArr8.length);
                    ArraysKt.copyInto(objArr8, objArr8, 0, length2, c);
                    Object[] objArr9 = this.b;
                    ArraysKt.copyInto(objArr9, objArr9, i5, c2, length2);
                }
            } else {
                Object[] objArr10 = this.b;
                ArraysKt.copyInto(objArr10, objArr10, size, 0, c);
                Object[] objArr11 = this.b;
                if (i5 >= objArr11.length) {
                    ArraysKt.copyInto(objArr11, objArr11, i5 - objArr11.length, c2, objArr11.length);
                } else {
                    ArraysKt.copyInto(objArr11, objArr11, 0, objArr11.length - size, objArr11.length);
                    Object[] objArr12 = this.b;
                    ArraysKt.copyInto(objArr12, objArr12, i5, c2, objArr12.length - size);
                }
            }
            a(c2, elements);
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        return (E) this.b[c(this.a + i)];
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        int c = c(this.a + i);
        E e2 = (E) this.b[c];
        this.b[c] = e;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        int c = c(this.a + size());
        int i = this.a;
        if (i < c) {
            while (i < c) {
                if (Intrinsics.areEqual(obj, this.b[i])) {
                    return i - this.a;
                }
                i++;
            }
            return -1;
        } else if (i < c) {
            return -1;
        } else {
            int length = this.b.length;
            while (i < length) {
                if (Intrinsics.areEqual(obj, this.b[i])) {
                    return i - this.a;
                }
                i++;
            }
            for (int i2 = 0; i2 < c; i2++) {
                if (Intrinsics.areEqual(obj, this.b[i2])) {
                    return (i2 + this.b.length) - this.a;
                }
            }
            return -1;
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public int lastIndexOf(Object obj) {
        int c = c(this.a + size());
        int i = this.a;
        if (i < c) {
            int i2 = c - 1;
            if (i2 < i) {
                return -1;
            }
            while (!Intrinsics.areEqual(obj, this.b[i2])) {
                if (i2 == i) {
                    return -1;
                }
                i2--;
            }
            return i2 - this.a;
        } else if (i <= c) {
            return -1;
        } else {
            for (int i3 = c - 1; i3 >= 0; i3--) {
                if (Intrinsics.areEqual(obj, this.b[i3])) {
                    return (i3 + this.b.length) - this.a;
                }
            }
            int lastIndex = ArraysKt.getLastIndex(this.b);
            int i4 = this.a;
            if (lastIndex < i4) {
                return -1;
            }
            while (!Intrinsics.areEqual(obj, this.b[lastIndex])) {
                if (lastIndex == i4) {
                    return -1;
                }
                lastIndex--;
            }
            return lastIndex - this.a;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        remove(indexOf);
        return true;
    }

    @Override // kotlin.collections.AbstractMutableList
    public E removeAt(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        if (i == CollectionsKt.getLastIndex(this)) {
            return removeLast();
        }
        if (i == 0) {
            return removeFirst();
        }
        int c = c(this.a + i);
        E e = (E) this.b[c];
        if (i < (size() >> 1)) {
            int i2 = this.a;
            if (c >= i2) {
                Object[] objArr = this.b;
                ArraysKt.copyInto(objArr, objArr, i2 + 1, i2, c);
            } else {
                Object[] objArr2 = this.b;
                ArraysKt.copyInto(objArr2, objArr2, 1, 0, c);
                Object[] objArr3 = this.b;
                objArr3[0] = objArr3[objArr3.length - 1];
                int i3 = this.a;
                ArraysKt.copyInto(objArr3, objArr3, i3 + 1, i3, objArr3.length - 1);
            }
            Object[] objArr4 = this.b;
            int i4 = this.a;
            objArr4[i4] = null;
            this.a = e(i4);
        } else {
            int c2 = c(this.a + CollectionsKt.getLastIndex(this));
            if (c <= c2) {
                Object[] objArr5 = this.b;
                ArraysKt.copyInto(objArr5, objArr5, c, c + 1, c2 + 1);
            } else {
                Object[] objArr6 = this.b;
                ArraysKt.copyInto(objArr6, objArr6, c, c + 1, objArr6.length);
                Object[] objArr7 = this.b;
                objArr7[objArr7.length - 1] = objArr7[0];
                ArraysKt.copyInto(objArr7, objArr7, 0, 1, c2 + 1);
            }
            this.b[c2] = null;
        }
        this.c = size() - 1;
        return e;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        int c = c(this.a + size());
        int i = this.a;
        if (i < c) {
            ArraysKt.fill(this.b, (Object) null, i, c);
        } else if (!isEmpty()) {
            Object[] objArr = this.b;
            ArraysKt.fill(objArr, (Object) null, this.a, objArr.length);
            ArraysKt.fill(this.b, (Object) null, 0, c);
        }
        this.a = 0;
        this.c = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @NotNull
    public <T> T[] toArray(@NotNull T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (array.length < size()) {
            array = (T[]) ArraysKt.arrayOfNulls(array, size());
        }
        if (array != null) {
            int c = c(this.a + size());
            int i = this.a;
            if (i < c) {
                ArraysKt.copyInto$default(this.b, array, 0, i, c, 2, (Object) null);
            } else if (!isEmpty()) {
                Object[] objArr = this.b;
                ArraysKt.copyInto(objArr, array, 0, this.a, objArr.length);
                Object[] objArr2 = this.b;
                ArraysKt.copyInto(objArr2, array, objArr2.length - this.a, 0, c);
            }
            if (array.length > size()) {
                array[size()] = null;
            }
            if (array != null) {
                return array;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @NotNull
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    @NotNull
    public final <T> T[] testToArray$kotlin_stdlib(@NotNull T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) toArray(array);
    }

    @NotNull
    public final Object[] testToArray$kotlin_stdlib() {
        return toArray();
    }

    /* compiled from: ArrayDeque.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlin/collections/ArrayDeque$Companion;", "", "()V", "defaultMinCapacity", "", "emptyElementData", "", "[Ljava/lang/Object;", "maxArraySize", "newCapacity", "oldCapacity", "minCapacity", "newCapacity$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class Companion {
        public final int newCapacity$kotlin_stdlib(int i, int i2) {
            int i3 = i + (i >> 1);
            if (i3 - i2 < 0) {
                i3 = i2;
            }
            if (i3 - OsCollectionChangeSet.MAX_ARRAY_LENGTH <= 0) {
                return i3;
            }
            if (i2 > 2147483639) {
                return Integer.MAX_VALUE;
            }
            return OsCollectionChangeSet.MAX_ARRAY_LENGTH;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final void internalStructure$kotlin_stdlib(@NotNull Function2<? super Integer, ? super Object[], Unit> structure) {
        int i;
        Intrinsics.checkNotNullParameter(structure, "structure");
        structure.invoke(Integer.valueOf((isEmpty() || (i = this.a) < c(this.a + size())) ? this.a : i - this.b.length), toArray());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty()) {
            if ((this.b.length == 0 ? 1 : null) == null) {
                int c = c(this.a + size());
                int i = this.a;
                if (this.a < c) {
                    for (int i2 = this.a; i2 < c; i2++) {
                        Object obj = this.b[i2];
                        if (!elements.contains(obj)) {
                            i++;
                            this.b[i] = obj;
                        } else {
                            z = true;
                        }
                    }
                    ArraysKt.fill(this.b, (Object) null, i, c);
                } else {
                    int length = this.b.length;
                    boolean z2 = false;
                    for (int i3 = this.a; i3 < length; i3++) {
                        Object obj2 = this.b[i3];
                        this.b[i3] = null;
                        if (!elements.contains(obj2)) {
                            i++;
                            this.b[i] = obj2;
                        } else {
                            z2 = true;
                        }
                    }
                    i = c(i);
                    for (int i4 = 0; i4 < c; i4++) {
                        Object obj3 = this.b[i4];
                        this.b[i4] = null;
                        if (!elements.contains(obj3)) {
                            this.b[i] = obj3;
                            i = e(i);
                        } else {
                            z2 = true;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    this.c = d(i - this.a);
                }
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty()) {
            if ((this.b.length == 0 ? 1 : null) == null) {
                int c = c(this.a + size());
                int i = this.a;
                if (this.a < c) {
                    for (int i2 = this.a; i2 < c; i2++) {
                        Object obj = this.b[i2];
                        if (elements.contains(obj)) {
                            i++;
                            this.b[i] = obj;
                        } else {
                            z = true;
                        }
                    }
                    ArraysKt.fill(this.b, (Object) null, i, c);
                } else {
                    int length = this.b.length;
                    boolean z2 = false;
                    for (int i3 = this.a; i3 < length; i3++) {
                        Object obj2 = this.b[i3];
                        this.b[i3] = null;
                        if (elements.contains(obj2)) {
                            i++;
                            this.b[i] = obj2;
                        } else {
                            z2 = true;
                        }
                    }
                    i = c(i);
                    for (int i4 = 0; i4 < c; i4++) {
                        Object obj3 = this.b[i4];
                        this.b[i4] = null;
                        if (elements.contains(obj3)) {
                            this.b[i] = obj3;
                            i = e(i);
                        } else {
                            z2 = true;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    this.c = d(i - this.a);
                }
            }
        }
        return z;
    }
}
