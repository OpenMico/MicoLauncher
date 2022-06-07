package okio;

import com.xiaomi.micolauncher.api.model.Remote;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Segment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0000\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0000J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0000J\u0006\u0010\u0013\u001a\u00020\u0000J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0000J\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u0004\u0018\u00010\u00008\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lokio/Segment;", "", "()V", "data", "", "pos", "", "limit", "shared", "", "owner", "([BIIZZ)V", "next", Remote.Response.ControlAction.ACTION_PREV, "compact", "", "pop", "push", "segment", "sharedCopy", "split", "byteCount", "unsharedCopy", "writeTo", "sink", "Companion", "okio"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class Segment {
    public static final Companion Companion = new Companion(null);
    public static final int SHARE_MINIMUM = 1024;
    public static final int SIZE = 8192;
    @JvmField
    @NotNull
    public final byte[] data;
    @JvmField
    public int limit;
    @JvmField
    @Nullable
    public Segment next;
    @JvmField
    public boolean owner;
    @JvmField
    public int pos;
    @JvmField
    @Nullable
    public Segment prev;
    @JvmField
    public boolean shared;

    public Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    public Segment(@NotNull byte[] data, int i, int i2, boolean z, boolean z2) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        this.data = data;
        this.pos = i;
        this.limit = i2;
        this.shared = z;
        this.owner = z2;
    }

    @NotNull
    public final Segment sharedCopy() {
        this.shared = true;
        return new Segment(this.data, this.pos, this.limit, true, false);
    }

    @NotNull
    public final Segment unsharedCopy() {
        byte[] bArr = this.data;
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return new Segment(copyOf, this.pos, this.limit, false, true);
    }

    @Nullable
    public final Segment pop() {
        Segment segment = this.next;
        if (segment == this) {
            segment = null;
        }
        Segment segment2 = this.prev;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment2.next = this.next;
        Segment segment3 = this.next;
        if (segment3 == null) {
            Intrinsics.throwNpe();
        }
        segment3.prev = this.prev;
        Segment segment4 = null;
        this.next = segment4;
        this.prev = segment4;
        return segment;
    }

    @NotNull
    public final Segment push(@NotNull Segment segment) {
        Intrinsics.checkParameterIsNotNull(segment, "segment");
        segment.prev = this;
        segment.next = this.next;
        Segment segment2 = this.next;
        if (segment2 == null) {
            Intrinsics.throwNpe();
        }
        segment2.prev = segment;
        this.next = segment;
        return segment;
    }

    @NotNull
    public final Segment split(int i) {
        Segment segment;
        if (i > 0 && i <= this.limit - this.pos) {
            if (i >= 1024) {
                segment = sharedCopy();
            } else {
                segment = SegmentPool.INSTANCE.take();
                byte[] bArr = this.data;
                byte[] bArr2 = segment.data;
                int i2 = this.pos;
                ArraysKt.copyInto$default(bArr, bArr2, 0, i2, i2 + i, 2, (Object) null);
            }
            segment.limit = segment.pos + i;
            this.pos += i;
            Segment segment2 = this.prev;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            segment2.push(segment);
            return segment;
        }
        throw new IllegalArgumentException("byteCount out of range".toString());
    }

    public final void compact() {
        int i = 0;
        if (this.prev != this) {
            Segment segment = this.prev;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            if (segment.owner) {
                int i2 = this.limit - this.pos;
                Segment segment2 = this.prev;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                int i3 = 8192 - segment2.limit;
                Segment segment3 = this.prev;
                if (segment3 == null) {
                    Intrinsics.throwNpe();
                }
                if (!segment3.shared) {
                    Segment segment4 = this.prev;
                    if (segment4 == null) {
                        Intrinsics.throwNpe();
                    }
                    i = segment4.pos;
                }
                if (i2 <= i3 + i) {
                    Segment segment5 = this.prev;
                    if (segment5 == null) {
                        Intrinsics.throwNpe();
                    }
                    writeTo(segment5, i2);
                    pop();
                    SegmentPool.INSTANCE.recycle(this);
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("cannot compact".toString());
    }

    public final void writeTo(@NotNull Segment sink, int i) {
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        if (sink.owner) {
            int i2 = sink.limit;
            if (i2 + i > 8192) {
                if (!sink.shared) {
                    int i3 = sink.pos;
                    if ((i2 + i) - i3 <= 8192) {
                        byte[] bArr = sink.data;
                        ArraysKt.copyInto$default(bArr, bArr, 0, i3, i2, 2, (Object) null);
                        sink.limit -= sink.pos;
                        sink.pos = 0;
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            byte[] bArr2 = this.data;
            byte[] bArr3 = sink.data;
            int i4 = sink.limit;
            int i5 = this.pos;
            ArraysKt.copyInto(bArr2, bArr3, i4, i5, i5 + i);
            sink.limit += i;
            this.pos += i;
            return;
        }
        throw new IllegalStateException("only owner can write".toString());
    }

    /* compiled from: Segment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lokio/Segment$Companion;", "", "()V", "SHARE_MINIMUM", "", "SIZE", "okio"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
