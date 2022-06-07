package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;

/* compiled from: TimeSources.kt */
@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
@ExperimentalTime
/* loaded from: classes5.dex */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long a;

    public TestTimeSource() {
        super(TimeUnit.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongTimeSource
    protected long read() {
        return this.a;
    }

    /* renamed from: plusAssign-LRDsOJo  reason: not valid java name */
    public final void m1745plusAssignLRDsOJo(long j) {
        long j2;
        long j3 = Duration.m1712toLongimpl(j, getUnit());
        if (j3 == Long.MIN_VALUE || j3 == Long.MAX_VALUE) {
            double d = this.a + Duration.m1709toDoubleimpl(j, getUnit());
            if (d > Long.MAX_VALUE || d < Long.MIN_VALUE) {
                a(j);
            }
            j2 = (long) d;
        } else {
            long j4 = this.a;
            j2 = j4 + j3;
            if ((j3 ^ j4) >= 0 && (j4 ^ j2) < 0) {
                a(j);
            }
        }
        this.a = j2;
    }

    private final void a(long j) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.a + "ns is advanced by " + Duration.m1715toStringimpl(j) + '.');
    }
}
