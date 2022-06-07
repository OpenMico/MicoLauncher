package kotlin.collections.unsigned;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;

/* compiled from: _UArraysJvm.kt */
@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000e*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u001b\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u001e\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0006H\u0096\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J\b\u0010\u0015\u001a\u00020\nH\u0016J\u001a\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0014R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0018"}, d2 = {"kotlin/collections/unsigned/UArraysKt___UArraysJvmKt$asList$2", "Lkotlin/collections/AbstractList;", "Lkotlin/ULong;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "contains-VKZWuLQ", "(J)Z", BluetoothConstants.GET, MiSoundBoxCommandExtras.INDEX, "get-s-VKNKU", "(I)J", "indexOf", "indexOf-VKZWuLQ", "(J)I", "isEmpty", "lastIndexOf", "lastIndexOf-VKZWuLQ", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class UArraysKt___UArraysJvmKt$asList$2 extends AbstractList<ULong> implements RandomAccess {
    final /* synthetic */ long[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public UArraysKt___UArraysJvmKt$asList$2(long[] jArr) {
        this.a = jArr;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj instanceof ULong) {
            return m1353containsVKZWuLQ(((ULong) obj).m1285unboximpl());
        }
        return false;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public /* synthetic */ Object get(int i) {
        return ULong.m1279boximpl(m1354getsVKNKU(i));
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (obj instanceof ULong) {
            return m1355indexOfVKZWuLQ(((ULong) obj).m1285unboximpl());
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        if (obj instanceof ULong) {
            return m1356lastIndexOfVKZWuLQ(((ULong) obj).m1285unboximpl());
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return ULongArray.m1294getSizeimpl(this.a);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return ULongArray.m1296isEmptyimpl(this.a);
    }

    /* renamed from: contains-VKZWuLQ  reason: not valid java name */
    public boolean m1353containsVKZWuLQ(long j) {
        return ULongArray.m1289containsVKZWuLQ(this.a, j);
    }

    /* renamed from: get-s-VKNKU  reason: not valid java name */
    public long m1354getsVKNKU(int i) {
        return ULongArray.m1293getsVKNKU(this.a, i);
    }

    /* renamed from: indexOf-VKZWuLQ  reason: not valid java name */
    public int m1355indexOfVKZWuLQ(long j) {
        return ArraysKt.indexOf(this.a, j);
    }

    /* renamed from: lastIndexOf-VKZWuLQ  reason: not valid java name */
    public int m1356lastIndexOfVKZWuLQ(long j) {
        return ArraysKt.lastIndexOf(this.a, j);
    }
}
