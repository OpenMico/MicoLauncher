package kotlin.collections.builders;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MapBuilder.kt */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010&\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000 v*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0006vwxyz{B\u0007\b\u0016¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007BE\b\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0006¢\u0006\u0002\u0010\u0010J\u0017\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00028\u0000H\u0000¢\u0006\u0004\b0\u00101J\u0013\u00102\u001a\b\u0012\u0004\u0012\u00028\u00010\tH\u0002¢\u0006\u0002\u00103J\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105J\r\u00106\u001a\u000207H\u0000¢\u0006\u0002\b8J\b\u00109\u001a\u000207H\u0016J\b\u0010:\u001a\u000207H\u0002J\u0019\u0010;\u001a\u00020\u001f2\n\u0010<\u001a\u0006\u0012\u0002\b\u00030=H\u0000¢\u0006\u0002\b>J!\u0010?\u001a\u00020\u001f2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010AH\u0000¢\u0006\u0002\bBJ\u0015\u0010C\u001a\u00020\u001f2\u0006\u0010/\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010DJ\u0015\u0010E\u001a\u00020\u001f2\u0006\u0010F\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010DJ\u0018\u0010G\u001a\u00020\u001f2\u000e\u0010H\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u000305H\u0002J\u0010\u0010I\u001a\u0002072\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0010\u0010J\u001a\u0002072\u0006\u0010K\u001a\u00020\u0006H\u0002J\u0019\u0010L\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010MH\u0000¢\u0006\u0002\bNJ\u0013\u0010O\u001a\u00020\u001f2\b\u0010H\u001a\u0004\u0018\u00010PH\u0096\u0002J\u0015\u0010Q\u001a\u00020\u00062\u0006\u0010/\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00101J\u0015\u0010R\u001a\u00020\u00062\u0006\u0010F\u001a\u00028\u0001H\u0002¢\u0006\u0002\u00101J\u0018\u0010S\u001a\u0004\u0018\u00018\u00012\u0006\u0010/\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010TJ\u0015\u0010U\u001a\u00020\u00062\u0006\u0010/\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00101J\b\u0010V\u001a\u00020\u0006H\u0016J\b\u0010W\u001a\u00020\u001fH\u0016J\u0019\u0010X\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010YH\u0000¢\u0006\u0002\bZJ\u001f\u0010[\u001a\u0004\u0018\u00018\u00012\u0006\u0010/\u001a\u00028\u00002\u0006\u0010F\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\\J\u001e\u0010]\u001a\u0002072\u0014\u0010^\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105H\u0016J\"\u0010_\u001a\u00020\u001f2\u0018\u0010^\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010A0=H\u0002J\u001c\u0010`\u001a\u00020\u001f2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010AH\u0002J\u0010\u0010a\u001a\u00020\u001f2\u0006\u0010b\u001a\u00020\u0006H\u0002J\u0010\u0010c\u001a\u0002072\u0006\u0010d\u001a\u00020\u0006H\u0002J\u0017\u0010e\u001a\u0004\u0018\u00018\u00012\u0006\u0010/\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010TJ!\u0010f\u001a\u00020\u001f2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010AH\u0000¢\u0006\u0002\bgJ\u0010\u0010h\u001a\u0002072\u0006\u0010i\u001a\u00020\u0006H\u0002J\u0017\u0010j\u001a\u00020\u00062\u0006\u0010/\u001a\u00028\u0000H\u0000¢\u0006\u0004\bk\u00101J\u0010\u0010l\u001a\u0002072\u0006\u0010m\u001a\u00020\u0006H\u0002J\u0017\u0010n\u001a\u00020\u001f2\u0006\u0010o\u001a\u00028\u0001H\u0000¢\u0006\u0004\bp\u0010DJ\b\u0010q\u001a\u00020rH\u0016J\u0019\u0010s\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010tH\u0000¢\u0006\u0002\buR\u0014\u0010\u0011\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R&\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00160\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0013R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0018R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\"R\u0016\u0010#\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010&\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0013R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00028\u00010)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0018\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\"R\u0016\u0010,\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006|"}, d2 = {"Lkotlin/collections/builders/MapBuilder;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "initialCapacity", "", "(I)V", "keysArray", "", "valuesArray", "presenceArray", "", "hashArray", "maxProbeDistance", "length", "([Ljava/lang/Object;[Ljava/lang/Object;[I[III)V", "capacity", "getCapacity", "()I", "entries", "", "", "getEntries", "()Ljava/util/Set;", "entriesView", "Lkotlin/collections/builders/MapBuilderEntries;", "hashShift", "hashSize", "getHashSize", "isReadOnly", "", "keys", "getKeys", "[Ljava/lang/Object;", "keysView", "Lkotlin/collections/builders/MapBuilderKeys;", "<set-?>", "size", "getSize", "values", "", "getValues", "()Ljava/util/Collection;", "valuesView", "Lkotlin/collections/builders/MapBuilderValues;", "addKey", "key", "addKey$kotlin_stdlib", "(Ljava/lang/Object;)I", "allocateValuesArray", "()[Ljava/lang/Object;", "build", "", "checkIsMutable", "", "checkIsMutable$kotlin_stdlib", "clear", "compact", "containsAllEntries", MessageElement.XPATH_PREFIX, "", "containsAllEntries$kotlin_stdlib", "containsEntry", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "(Ljava/lang/Object;)Z", "containsValue", b.p, "contentEquals", "other", "ensureCapacity", "ensureExtraCapacity", "n", "entriesIterator", "Lkotlin/collections/builders/MapBuilder$EntriesItr;", "entriesIterator$kotlin_stdlib", "equals", "", "findKey", "findValue", BluetoothConstants.GET, "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "hashCode", "isEmpty", "keysIterator", "Lkotlin/collections/builders/MapBuilder$KeysItr;", "keysIterator$kotlin_stdlib", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "putAllEntries", "putEntry", "putRehash", ai.aA, "rehash", "newHashSize", "remove", "removeEntry", "removeEntry$kotlin_stdlib", "removeHashAt", "removedHash", "removeKey", "removeKey$kotlin_stdlib", "removeKeyAt", MiSoundBoxCommandExtras.INDEX, "removeValue", "element", "removeValue$kotlin_stdlib", "toString", "", "valuesIterator", "Lkotlin/collections/builders/MapBuilder$ValuesItr;", "valuesIterator$kotlin_stdlib", "Companion", "EntriesItr", "EntryRef", "Itr", "KeysItr", "ValuesItr", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class MapBuilder<K, V> implements Map<K, V>, KMutableMap {
    @NotNull
    private static final a m = new a(null);
    private int a;
    private int b;
    private MapBuilderKeys<K> c;
    private MapBuilderValues<V> d;
    private MapBuilderEntries<K, V> e;
    private boolean f;
    private K[] g;
    private V[] h;
    private int[] i;
    private int[] j;
    private int k;
    private int l;

    private MapBuilder(K[] kArr, V[] vArr, int[] iArr, int[] iArr2, int i, int i2) {
        this.g = kArr;
        this.h = vArr;
        this.i = iArr;
        this.j = iArr2;
        this.k = i;
        this.l = i2;
        this.a = m.b(b());
    }

    @Override // java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final Set<K> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final Collection<V> values() {
        return getValues();
    }

    public int getSize() {
        return this.b;
    }

    public MapBuilder() {
        this(8);
    }

    public MapBuilder(int i) {
        this(ListBuilderKt.arrayOfUninitializedElements(i), null, new int[i], new int[m.a(i)], 2, 0);
    }

    @NotNull
    public final Map<K, V> build() {
        checkIsMutable$kotlin_stdlib();
        this.f = true;
        return this;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return b((MapBuilder<K, V>) obj) >= 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return c((MapBuilder<K, V>) obj) >= 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Nullable
    public V get(Object obj) {
        int b = b((MapBuilder<K, V>) obj);
        if (b < 0) {
            return null;
        }
        V[] vArr = this.h;
        Intrinsics.checkNotNull(vArr);
        return vArr[b];
    }

    @Override // java.util.Map
    @Nullable
    public V put(K k, V v) {
        checkIsMutable$kotlin_stdlib();
        int addKey$kotlin_stdlib = addKey$kotlin_stdlib(k);
        V[] c = c();
        if (addKey$kotlin_stdlib < 0) {
            int i = (-addKey$kotlin_stdlib) - 1;
            V v2 = c[i];
            c[i] = v;
            return v2;
        }
        c[addKey$kotlin_stdlib] = v;
        return null;
    }

    @Override // java.util.Map
    public void putAll(@NotNull Map<? extends K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        checkIsMutable$kotlin_stdlib();
        a((Collection) from.entrySet());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Nullable
    public V remove(Object obj) {
        int removeKey$kotlin_stdlib = removeKey$kotlin_stdlib(obj);
        if (removeKey$kotlin_stdlib < 0) {
            return null;
        }
        V[] vArr = this.h;
        Intrinsics.checkNotNull(vArr);
        V v = vArr[removeKey$kotlin_stdlib];
        ListBuilderKt.resetAt(vArr, removeKey$kotlin_stdlib);
        return v;
    }

    @Override // java.util.Map
    public void clear() {
        checkIsMutable$kotlin_stdlib();
        int i = this.l - 1;
        if (i >= 0) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.i;
                int i3 = iArr[i2];
                if (i3 >= 0) {
                    this.j[i3] = 0;
                    iArr[i2] = -1;
                }
                if (i2 == i) {
                    break;
                }
                i2++;
            }
        }
        ListBuilderKt.resetRange(this.g, 0, this.l);
        V[] vArr = this.h;
        if (vArr != null) {
            ListBuilderKt.resetRange(vArr, 0, this.l);
        }
        this.b = 0;
        this.l = 0;
    }

    @NotNull
    public Set<K> getKeys() {
        MapBuilderKeys<K> mapBuilderKeys = this.c;
        if (mapBuilderKeys != null) {
            return mapBuilderKeys;
        }
        MapBuilderKeys<K> mapBuilderKeys2 = new MapBuilderKeys<>(this);
        this.c = mapBuilderKeys2;
        return mapBuilderKeys2;
    }

    @NotNull
    public Collection<V> getValues() {
        MapBuilderValues<V> mapBuilderValues = this.d;
        if (mapBuilderValues != null) {
            return mapBuilderValues;
        }
        MapBuilderValues<V> mapBuilderValues2 = new MapBuilderValues<>(this);
        this.d = mapBuilderValues2;
        return mapBuilderValues2;
    }

    @NotNull
    public Set<Map.Entry<K, V>> getEntries() {
        MapBuilderEntries<K, V> mapBuilderEntries = this.e;
        if (mapBuilderEntries != null) {
            return mapBuilderEntries;
        }
        MapBuilderEntries<K, V> mapBuilderEntries2 = new MapBuilderEntries<>(this);
        this.e = mapBuilderEntries2;
        return mapBuilderEntries2;
    }

    @Override // java.util.Map
    public boolean equals(@Nullable Object obj) {
        return obj == this || ((obj instanceof Map) && a((Map) obj));
    }

    @Override // java.util.Map
    public int hashCode() {
        EntriesItr<K, V> entriesIterator$kotlin_stdlib = entriesIterator$kotlin_stdlib();
        int i = 0;
        while (entriesIterator$kotlin_stdlib.hasNext()) {
            i += entriesIterator$kotlin_stdlib.nextHashCode$kotlin_stdlib();
        }
        return i;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder((size() * 3) + 2);
        sb.append("{");
        EntriesItr<K, V> entriesIterator$kotlin_stdlib = entriesIterator$kotlin_stdlib();
        int i = 0;
        while (entriesIterator$kotlin_stdlib.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            entriesIterator$kotlin_stdlib.nextAppendString(sb);
            i++;
        }
        sb.append("}");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "sb.toString()");
        return sb2;
    }

    private final int a() {
        return this.g.length;
    }

    private final int b() {
        return this.j.length;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.f) {
            throw new UnsupportedOperationException();
        }
    }

    private final void a(int i) {
        b(this.l + i);
    }

    private final void b(int i) {
        if (i > a()) {
            int a2 = (a() * 3) / 2;
            if (i <= a2) {
                i = a2;
            }
            this.g = (K[]) ListBuilderKt.copyOfUninitializedElements(this.g, i);
            V[] vArr = this.h;
            this.h = vArr != null ? (V[]) ListBuilderKt.copyOfUninitializedElements(vArr, i) : null;
            int[] copyOf = Arrays.copyOf(this.i, i);
            Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, newSize)");
            this.i = copyOf;
            int a3 = m.a(i);
            if (a3 > b()) {
                c(a3);
            }
        } else if ((this.l + i) - size() > a()) {
            c(b());
        }
    }

    public final V[] c() {
        V[] vArr = this.h;
        if (vArr != null) {
            return vArr;
        }
        V[] vArr2 = (V[]) ListBuilderKt.arrayOfUninitializedElements(a());
        this.h = vArr2;
        return vArr2;
    }

    private final int a(K k) {
        return ((k != null ? k.hashCode() : 0) * (-1640531527)) >>> this.a;
    }

    private final void d() {
        int i;
        V[] vArr = this.h;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.l;
            if (i2 >= i) {
                break;
            }
            if (this.i[i2] >= 0) {
                K[] kArr = this.g;
                kArr[i3] = kArr[i2];
                if (vArr != null) {
                    vArr[i3] = vArr[i2];
                }
                i3++;
            }
            i2++;
        }
        ListBuilderKt.resetRange(this.g, i3, i);
        if (vArr != null) {
            ListBuilderKt.resetRange(vArr, i3, this.l);
        }
        this.l = i3;
    }

    private final void c(int i) {
        if (this.l > size()) {
            d();
        }
        int i2 = 0;
        if (i != b()) {
            this.j = new int[i];
            this.a = m.b(i);
        } else {
            ArraysKt.fill(this.j, 0, 0, b());
        }
        while (i2 < this.l) {
            i2++;
            if (!d(i2)) {
                throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
            }
        }
    }

    private final boolean d(int i) {
        int a2 = a((MapBuilder<K, V>) this.g[i]);
        int i2 = this.k;
        while (true) {
            int[] iArr = this.j;
            if (iArr[a2] == 0) {
                iArr[a2] = i + 1;
                this.i[i] = a2;
                return true;
            }
            i2--;
            if (i2 < 0) {
                return false;
            }
            a2--;
            if (a2 == 0) {
                a2 = b() - 1;
            }
        }
    }

    private final int b(K k) {
        int a2 = a((MapBuilder<K, V>) k);
        int i = this.k;
        while (true) {
            int i2 = this.j[a2];
            if (i2 == 0) {
                return -1;
            }
            if (i2 > 0) {
                int i3 = i2 - 1;
                if (Intrinsics.areEqual(this.g[i3], k)) {
                    return i3;
                }
            }
            i--;
            if (i < 0) {
                return -1;
            }
            a2--;
            if (a2 == 0) {
                a2 = b() - 1;
            }
        }
    }

    private final int c(V v) {
        int i = this.l;
        while (true) {
            i--;
            if (i < 0) {
                return -1;
            }
            if (this.i[i] >= 0) {
                V[] vArr = this.h;
                Intrinsics.checkNotNull(vArr);
                if (Intrinsics.areEqual(vArr[i], v)) {
                    return i;
                }
            }
        }
    }

    public final int addKey$kotlin_stdlib(K k) {
        checkIsMutable$kotlin_stdlib();
        while (true) {
            int a2 = a((MapBuilder<K, V>) k);
            int coerceAtMost = RangesKt.coerceAtMost(this.k * 2, b() / 2);
            int i = 0;
            while (true) {
                int i2 = this.j[a2];
                if (i2 <= 0) {
                    if (this.l >= a()) {
                        a(1);
                    } else {
                        int i3 = this.l;
                        int i4 = i3 + 1;
                        this.l = i4;
                        this.g[i3] = k;
                        this.i[i3] = a2;
                        this.j[a2] = i4;
                        this.b = size() + 1;
                        if (i > this.k) {
                            this.k = i;
                        }
                        return i3;
                    }
                } else if (Intrinsics.areEqual(this.g[i2 - 1], k)) {
                    return -i2;
                } else {
                    i++;
                    if (i > coerceAtMost) {
                        c(b() * 2);
                        break;
                    }
                    a2--;
                    if (a2 == 0) {
                        a2 = b() - 1;
                    }
                }
            }
        }
    }

    public final int removeKey$kotlin_stdlib(K k) {
        checkIsMutable$kotlin_stdlib();
        int b = b((MapBuilder<K, V>) k);
        if (b < 0) {
            return -1;
        }
        e(b);
        return b;
    }

    public final void e(int i) {
        ListBuilderKt.resetAt(this.g, i);
        f(this.i[i]);
        this.i[i] = -1;
        this.b = size() - 1;
    }

    private final void f(int i) {
        int coerceAtMost = RangesKt.coerceAtMost(this.k * 2, b() / 2);
        int i2 = i;
        int i3 = 0;
        do {
            i--;
            if (i == 0) {
                i = b() - 1;
            }
            i3++;
            if (i3 > this.k) {
                this.j[i2] = 0;
                return;
            }
            int[] iArr = this.j;
            int i4 = iArr[i];
            if (i4 == 0) {
                iArr[i2] = 0;
                return;
            }
            if (i4 < 0) {
                iArr[i2] = -1;
                i2 = i;
                i3 = 0;
            } else {
                int i5 = i4 - 1;
                if (((a((MapBuilder<K, V>) this.g[i5]) - i) & (b() - 1)) >= i3) {
                    this.j[i2] = i4;
                    this.i[i5] = i2;
                    i2 = i;
                    i3 = 0;
                }
            }
            coerceAtMost--;
        } while (coerceAtMost >= 0);
        this.j[i2] = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean containsEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        int b = b((MapBuilder<K, V>) entry.getKey());
        if (b < 0) {
            return false;
        }
        V[] vArr = this.h;
        Intrinsics.checkNotNull(vArr);
        return Intrinsics.areEqual(vArr[b], entry.getValue());
    }

    private final boolean a(Map<?, ?> map) {
        return size() == map.size() && containsAllEntries$kotlin_stdlib(map.entrySet());
    }

    public final boolean containsAllEntries$kotlin_stdlib(@NotNull Collection<?> m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        for (Object obj : m2) {
            if (obj != null) {
                try {
                    if (!containsEntry$kotlin_stdlib((Map.Entry) obj)) {
                    }
                } catch (ClassCastException unused) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean a(Map.Entry<? extends K, ? extends V> entry) {
        int addKey$kotlin_stdlib = addKey$kotlin_stdlib(entry.getKey());
        Object[] c = c();
        if (addKey$kotlin_stdlib >= 0) {
            c[addKey$kotlin_stdlib] = entry.getValue();
            return true;
        }
        int i = (-addKey$kotlin_stdlib) - 1;
        if (!(!Intrinsics.areEqual(entry.getValue(), c[i]))) {
            return false;
        }
        c[i] = entry.getValue();
        return true;
    }

    private final boolean a(Collection<? extends Map.Entry<? extends K, ? extends V>> collection) {
        boolean z = false;
        if (collection.isEmpty()) {
            return false;
        }
        a(collection.size());
        for (Map.Entry<? extends K, ? extends V> entry : collection) {
            if (a((Map.Entry) entry)) {
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean removeEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        checkIsMutable$kotlin_stdlib();
        int b = b((MapBuilder<K, V>) entry.getKey());
        if (b < 0) {
            return false;
        }
        V[] vArr = this.h;
        Intrinsics.checkNotNull(vArr);
        if (!Intrinsics.areEqual(vArr[b], entry.getValue())) {
            return false;
        }
        e(b);
        return true;
    }

    public final boolean removeValue$kotlin_stdlib(V v) {
        checkIsMutable$kotlin_stdlib();
        int c = c((MapBuilder<K, V>) v);
        if (c < 0) {
            return false;
        }
        e(c);
        return true;
    }

    @NotNull
    public final KeysItr<K, V> keysIterator$kotlin_stdlib() {
        return new KeysItr<>(this);
    }

    @NotNull
    public final ValuesItr<K, V> valuesIterator$kotlin_stdlib() {
        return new ValuesItr<>(this);
    }

    @NotNull
    public final EntriesItr<K, V> entriesIterator$kotlin_stdlib() {
        return new EntriesItr<>(this);
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlin/collections/builders/MapBuilder$Companion;", "", "()V", "INITIAL_CAPACITY", "", "INITIAL_MAX_PROBE_DISTANCE", "MAGIC", "TOMBSTONE", "computeHashSize", "capacity", "computeShift", "hashSize", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int a(int i) {
            return Integer.highestOneBit(RangesKt.coerceAtLeast(i, 1) * 3);
        }

        public final int b(int i) {
            return Integer.numberOfLeadingZeros(i) + 1;
        }
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\r\u0010\u0014\u001a\u00020\u0015H\u0000¢\u0006\u0002\b\u0016J\u0006\u0010\u0017\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Lkotlin/collections/builders/MapBuilder$Itr;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", MiSoundBoxCommandExtras.INDEX, "", "getIndex$kotlin_stdlib", "()I", "setIndex$kotlin_stdlib", "(I)V", "lastIndex", "getLastIndex$kotlin_stdlib", "setLastIndex$kotlin_stdlib", "getMap$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "hasNext", "", "initNext", "", "initNext$kotlin_stdlib", "remove", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static class Itr<K, V> {
        private int a;
        private int b = -1;
        @NotNull
        private final MapBuilder<K, V> c;

        public Itr(@NotNull MapBuilder<K, V> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            this.c = map;
            initNext$kotlin_stdlib();
        }

        @NotNull
        public final MapBuilder<K, V> getMap$kotlin_stdlib() {
            return this.c;
        }

        public final int getIndex$kotlin_stdlib() {
            return this.a;
        }

        public final void setIndex$kotlin_stdlib(int i) {
            this.a = i;
        }

        public final int getLastIndex$kotlin_stdlib() {
            return this.b;
        }

        public final void setLastIndex$kotlin_stdlib(int i) {
            this.b = i;
        }

        public final void initNext$kotlin_stdlib() {
            while (this.a < ((MapBuilder) this.c).l) {
                int[] iArr = ((MapBuilder) this.c).i;
                int i = this.a;
                if (iArr[i] < 0) {
                    this.a = i + 1;
                } else {
                    return;
                }
            }
        }

        public final boolean hasNext() {
            return this.a < ((MapBuilder) this.c).l;
        }

        public final void remove() {
            this.c.checkIsMutable$kotlin_stdlib();
            this.c.e(this.b);
            this.b = -1;
        }
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/collections/builders/MapBuilder$KeysItr;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class KeysItr<K, V> extends Itr<K, V> implements Iterator<K>, KMutableIterator {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeysItr(@NotNull MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        @Override // java.util.Iterator
        public K next() {
            if (getIndex$kotlin_stdlib() < ((MapBuilder) getMap$kotlin_stdlib()).l) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                K k = (K) ((MapBuilder) getMap$kotlin_stdlib()).g[getLastIndex$kotlin_stdlib()];
                initNext$kotlin_stdlib();
                return k;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0003H\u0096\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/collections/builders/MapBuilder$ValuesItr;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class ValuesItr<K, V> extends Itr<K, V> implements Iterator<V>, KMutableIterator {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValuesItr(@NotNull MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        @Override // java.util.Iterator
        public V next() {
            if (getIndex$kotlin_stdlib() < ((MapBuilder) getMap$kotlin_stdlib()).l) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object[] objArr = ((MapBuilder) getMap$kotlin_stdlib()).h;
                Intrinsics.checkNotNull(objArr);
                V v = (V) objArr[getLastIndex$kotlin_stdlib()];
                initNext$kotlin_stdlib();
                return v;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\nH\u0096\u0002J\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fJ\r\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lkotlin/collections/builders/MapBuilder$EntriesItr;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/builders/MapBuilder$Itr;", "", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "Lkotlin/collections/builders/MapBuilder$EntryRef;", "nextAppendString", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "nextHashCode", "", "nextHashCode$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class EntriesItr<K, V> extends Itr<K, V> implements Iterator<Map.Entry<K, V>>, KMutableIterator {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntriesItr(@NotNull MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        @Override // java.util.Iterator
        @NotNull
        public EntryRef<K, V> next() {
            if (getIndex$kotlin_stdlib() < ((MapBuilder) getMap$kotlin_stdlib()).l) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                EntryRef<K, V> entryRef = new EntryRef<>(getMap$kotlin_stdlib(), getLastIndex$kotlin_stdlib());
                initNext$kotlin_stdlib();
                return entryRef;
            }
            throw new NoSuchElementException();
        }

        public final int nextHashCode$kotlin_stdlib() {
            if (getIndex$kotlin_stdlib() < ((MapBuilder) getMap$kotlin_stdlib()).l) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object obj = ((MapBuilder) getMap$kotlin_stdlib()).g[getLastIndex$kotlin_stdlib()];
                int i = 0;
                int hashCode = obj != null ? obj.hashCode() : 0;
                Object[] objArr = ((MapBuilder) getMap$kotlin_stdlib()).h;
                Intrinsics.checkNotNull(objArr);
                Object obj2 = objArr[getLastIndex$kotlin_stdlib()];
                if (obj2 != null) {
                    i = obj2.hashCode();
                }
                int i2 = hashCode ^ i;
                initNext$kotlin_stdlib();
                return i2;
            }
            throw new NoSuchElementException();
        }

        public final void nextAppendString(@NotNull StringBuilder sb) {
            Intrinsics.checkNotNullParameter(sb, "sb");
            if (getIndex$kotlin_stdlib() < ((MapBuilder) getMap$kotlin_stdlib()).l) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object obj = ((MapBuilder) getMap$kotlin_stdlib()).g[getLastIndex$kotlin_stdlib()];
                if (Intrinsics.areEqual(obj, getMap$kotlin_stdlib())) {
                    sb.append("(this Map)");
                } else {
                    sb.append(obj);
                }
                sb.append('=');
                Object[] objArr = ((MapBuilder) getMap$kotlin_stdlib()).h;
                Intrinsics.checkNotNull(objArr);
                Object obj2 = objArr[getLastIndex$kotlin_stdlib()];
                if (Intrinsics.areEqual(obj2, getMap$kotlin_stdlib())) {
                    sb.append("(this Map)");
                } else {
                    sb.append(obj2);
                }
                initNext$kotlin_stdlib();
                return;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapBuilder.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0007H\u0016J\u0015\u0010\u0013\u001a\u00028\u00032\u0006\u0010\u0014\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00028\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u0018"}, d2 = {"Lkotlin/collections/builders/MapBuilder$EntryRef;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "map", "Lkotlin/collections/builders/MapBuilder;", MiSoundBoxCommandExtras.INDEX, "", "(Lkotlin/collections/builders/MapBuilder;I)V", "key", "getKey", "()Ljava/lang/Object;", b.p, "getValue", "equals", "", "other", "", "hashCode", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class EntryRef<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final MapBuilder<K, V> a;
        private final int b;

        public EntryRef(@NotNull MapBuilder<K, V> map, int i) {
            Intrinsics.checkNotNullParameter(map, "map");
            this.a = map;
            this.b = i;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return (K) ((MapBuilder) this.a).g[this.b];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            Object[] objArr = ((MapBuilder) this.a).h;
            Intrinsics.checkNotNull(objArr);
            return (V) objArr[this.b];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            this.a.checkIsMutable$kotlin_stdlib();
            Object[] c = this.a.c();
            int i = this.b;
            V v2 = (V) c[i];
            c[i] = v;
            return v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                if (Intrinsics.areEqual(entry.getKey(), getKey()) && Intrinsics.areEqual(entry.getValue(), getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K key = getKey();
            int i = 0;
            int hashCode = key != null ? key.hashCode() : 0;
            V value = getValue();
            if (value != null) {
                i = value.hashCode();
            }
            return hashCode ^ i;
        }

        @NotNull
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append('=');
            sb.append(getValue());
            return sb.toString();
        }
    }
}
