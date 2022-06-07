package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Distinct.kt */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n\u001aT\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n26\u0010\f\u001a2\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\u0001\u001a6\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u000b0\n2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00120\u0007\u001au\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n2\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u0002H\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00072:\u0010\f\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\u0001H\u0002¢\u0006\u0002\b\u0014\",\u0010\u0000\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0004\u0010\u0005\"&\u0010\u0006\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00078\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0005¨\u0006\u0015"}, d2 = {"defaultAreEquivalent", "Lkotlin/Function2;", "", "", "getDefaultAreEquivalent$annotations$FlowKt__DistinctKt", "()V", "defaultKeySelector", "Lkotlin/Function1;", "getDefaultKeySelector$annotations$FlowKt__DistinctKt", "distinctUntilChanged", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "areEquivalent", "Lkotlin/ParameterName;", "name", "old", "new", "distinctUntilChangedBy", "K", "keySelector", "distinctUntilChangedBy$FlowKt__DistinctKt", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class m {
    @NotNull
    private static final Function1<Object, Object> a = b.a;
    @NotNull
    private static final Function2<Object, Object, Boolean> b = a.a;

    /* compiled from: Distinct.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001H\n"}, d2 = {"<anonymous>", "", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class b extends Lambda implements Function1<Object, Object> {
        public static final b a = new b();

        b() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final Object invoke(@Nullable Object obj) {
            return obj;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow) {
        return flow instanceof StateFlow ? flow : a(flow, a, b);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow, @NotNull Function2<? super T, ? super T, Boolean> function2) {
        return a(flow, a, function2);
    }

    @NotNull
    public static final <T, K> Flow<T> a(@NotNull Flow<? extends T> flow, @NotNull Function1<? super T, ? extends K> function1) {
        return a(flow, function1, b);
    }

    /* compiled from: Distinct.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\n"}, d2 = {"<anonymous>", "", "old", "", "new"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class a extends Lambda implements Function2<Object, Object, Boolean> {
        public static final a a = new a();

        a() {
            super(2);
        }

        public final boolean a(@Nullable Object obj, @Nullable Object obj2) {
            return Intrinsics.areEqual(obj, obj2);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Boolean invoke(Object obj, Object obj2) {
            return Boolean.valueOf(a(obj, obj2));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T> Flow<T> a(Flow<? extends T> flow, Function1<? super T, ? extends Object> function1, Function2<Object, Object, Boolean> function2) {
        if (flow instanceof e) {
            e eVar = (e) flow;
            if (eVar.a == function1 && eVar.b == function2) {
                return flow;
            }
        }
        return new e(flow, function1, function2);
    }
}
