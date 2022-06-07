package kotlin.jvm.internal;

import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: PrimitiveSpreadBuilders.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlin/jvm/internal/LongSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", b.p, "", "toArray", "getSize", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class LongSpreadBuilder extends PrimitiveSpreadBuilder<long[]> {
    private final long[] a;

    public LongSpreadBuilder(int i) {
        super(i);
        this.a = new long[i];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSize(@NotNull long[] getSize) {
        Intrinsics.checkNotNullParameter(getSize, "$this$getSize");
        return getSize.length;
    }

    public final void add(long j) {
        long[] jArr = this.a;
        int position = getPosition();
        setPosition(position + 1);
        jArr[position] = j;
    }

    @NotNull
    public final long[] toArray() {
        return toArray(this.a, new long[size()]);
    }
}
