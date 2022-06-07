package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SharedFlow.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0000H¦@ø\u0001\u0000¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\nH'J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0010R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/flow/MutableSharedFlow;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/SharedFlow;", "Lkotlinx/coroutines/flow/FlowCollector;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "emit", "", b.p, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public interface MutableSharedFlow<T> extends FlowCollector<T>, SharedFlow<T> {
    @Override // kotlinx.coroutines.flow.FlowCollector
    @Nullable
    Object emit(T t, @NotNull Continuation<? super Unit> continuation);

    @NotNull
    StateFlow<Integer> getSubscriptionCount();

    @ExperimentalCoroutinesApi
    void resetReplayCache();

    boolean tryEmit(T t);
}
