package kotlin.comparisons;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

/* compiled from: Comparisons.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, ai.at, "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}, k = 3, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class ComparisonsKt__ComparisonsKt$thenBy$1<T> implements Comparator<T> {
    final /* synthetic */ Comparator a;
    final /* synthetic */ Function1 b;

    public ComparisonsKt__ComparisonsKt$thenBy$1(Comparator comparator, Function1 function1) {
        this.a = comparator;
        this.b = function1;
    }

    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        int compare = this.a.compare(t, t2);
        return compare != 0 ? compare : ComparisonsKt.compareValues((Comparable) this.b.invoke(t), (Comparable) this.b.invoke(t2));
    }
}
