package okio;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Options.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", BluetoothConstants.GET, MiSoundBoxCommandExtras.INDEX, "Companion", "okio"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ByteString[] byteStrings;
    @NotNull
    private final int[] trie;

    @JvmStatic
    @NotNull
    public static final Options of(@NotNull ByteString... byteStringArr) {
        return Companion.of(byteStringArr);
    }

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }

    @NotNull
    public final ByteString[] getByteStrings$okio() {
        return this.byteStrings;
    }

    @NotNull
    public final int[] getTrie$okio() {
        return this.trie;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    @NotNull
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    /* compiled from: Options.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:58:0x00f9, code lost:
            continue;
         */
        @kotlin.jvm.JvmStatic
        @org.jetbrains.annotations.NotNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final okio.Options of(@org.jetbrains.annotations.NotNull okio.ByteString... r18) {
            /*
                Method dump skipped, instructions count: 350
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.of(okio.ByteString[]):okio.Options");
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            companion.buildTrieRecursive((i4 & 1) != 0 ? 0L : j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long j, Buffer buffer, int i, List<? extends ByteString> list, int i2, int i3, List<Integer> list2) {
            int i4;
            ByteString byteString;
            int i5;
            int i6;
            int i7 = i;
            if (i2 < i3) {
                for (int i8 = i2; i8 < i3; i8++) {
                    if (!(((ByteString) list.get(i8)).size() >= i7)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                }
                ByteString byteString2 = (ByteString) list.get(i2);
                ByteString byteString3 = (ByteString) list.get(i3 - 1);
                if (i7 == byteString2.size()) {
                    i5 = list2.get(i2).intValue();
                    int i9 = i2 + 1;
                    byteString = (ByteString) list.get(i9);
                    i4 = i9;
                } else {
                    i4 = i2;
                    byteString = byteString2;
                    i5 = -1;
                }
                if (byteString.getByte(i7) != byteString3.getByte(i7)) {
                    int i10 = 1;
                    for (int i11 = i4 + 1; i11 < i3; i11++) {
                        if (((ByteString) list.get(i11 - 1)).getByte(i7) != ((ByteString) list.get(i11)).getByte(i7)) {
                            i10++;
                        }
                    }
                    Companion companion = this;
                    long intCount = j + companion.getIntCount(buffer) + 2 + (i10 * 2);
                    buffer.writeInt(i10);
                    buffer.writeInt(i5);
                    for (int i12 = i4; i12 < i3; i12++) {
                        byte b = ((ByteString) list.get(i12)).getByte(i7);
                        if (i12 == i4 || b != ((ByteString) list.get(i12 - 1)).getByte(i7)) {
                            buffer.writeInt(b & 255);
                        }
                    }
                    Buffer buffer2 = new Buffer();
                    while (i4 < i3) {
                        byte b2 = ((ByteString) list.get(i4)).getByte(i7);
                        int i13 = i4 + 1;
                        int i14 = i13;
                        while (true) {
                            if (i14 >= i3) {
                                i6 = i3;
                                break;
                            } else if (b2 != ((ByteString) list.get(i14)).getByte(i7)) {
                                i6 = i14;
                                break;
                            } else {
                                i14++;
                            }
                        }
                        if (i13 == i6 && i7 + 1 == ((ByteString) list.get(i4)).size()) {
                            buffer.writeInt(list2.get(i4).intValue());
                            i4 = i6;
                            buffer2 = buffer2;
                        } else {
                            buffer.writeInt(((int) (intCount + companion.getIntCount(buffer2))) * (-1));
                            i4 = i6;
                            buffer2 = buffer2;
                            companion.buildTrieRecursive(intCount, buffer2, i7 + 1, list, i4, i6, list2);
                        }
                    }
                    buffer.writeAll(buffer2);
                    return;
                }
                int min = Math.min(byteString.size(), byteString3.size());
                int i15 = 0;
                for (int i16 = i7; i16 < min && byteString.getByte(i16) == byteString3.getByte(i16); i16++) {
                    i15++;
                }
                Companion companion2 = this;
                long intCount2 = j + companion2.getIntCount(buffer) + 2 + i15 + 1;
                buffer.writeInt(-i15);
                buffer.writeInt(i5);
                int i17 = i7 + i15;
                while (i7 < i17) {
                    buffer.writeInt(byteString.getByte(i7) & 255);
                    i7++;
                }
                if (i4 + 1 == i3) {
                    if (i17 == ((ByteString) list.get(i4)).size()) {
                        buffer.writeInt(list2.get(i4).intValue());
                        return;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                Buffer buffer3 = new Buffer();
                buffer.writeInt(((int) (companion2.getIntCount(buffer3) + intCount2)) * (-1));
                companion2.buildTrieRecursive(intCount2, buffer3, i17, list, i4, i3, list2);
                buffer.writeAll(buffer3);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        private final long getIntCount(@NotNull Buffer buffer) {
            return buffer.size() / 4;
        }
    }
}
