package androidx.room;

import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: CoroutinesRoom.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0007"}, d2 = {"queryDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Landroidx/room/RoomDatabase;", "getQueryDispatcher", "(Landroidx/room/RoomDatabase;)Lkotlinx/coroutines/CoroutineDispatcher;", "transactionDispatcher", "getTransactionDispatcher", "room-ktx_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes.dex */
public final class CoroutinesRoomKt {
    @NotNull
    public static final CoroutineDispatcher getQueryDispatcher(@NotNull RoomDatabase queryDispatcher) {
        Intrinsics.checkNotNullParameter(queryDispatcher, "$this$queryDispatcher");
        Map<String, Object> backingFieldMap = queryDispatcher.c();
        Intrinsics.checkNotNullExpressionValue(backingFieldMap, "backingFieldMap");
        Object obj = backingFieldMap.get("QueryDispatcher");
        if (obj == null) {
            Executor queryExecutor = queryDispatcher.getQueryExecutor();
            Intrinsics.checkNotNullExpressionValue(queryExecutor, "queryExecutor");
            obj = ExecutorsKt.from(queryExecutor);
            backingFieldMap.put("QueryDispatcher", obj);
        }
        if (obj != null) {
            return (CoroutineDispatcher) obj;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.CoroutineDispatcher");
    }

    @NotNull
    public static final CoroutineDispatcher getTransactionDispatcher(@NotNull RoomDatabase transactionDispatcher) {
        Intrinsics.checkNotNullParameter(transactionDispatcher, "$this$transactionDispatcher");
        Map<String, Object> backingFieldMap = transactionDispatcher.c();
        Intrinsics.checkNotNullExpressionValue(backingFieldMap, "backingFieldMap");
        Object obj = backingFieldMap.get("TransactionDispatcher");
        if (obj == null) {
            Executor transactionExecutor = transactionDispatcher.getTransactionExecutor();
            Intrinsics.checkNotNullExpressionValue(transactionExecutor, "transactionExecutor");
            obj = ExecutorsKt.from(transactionExecutor);
            backingFieldMap.put("TransactionDispatcher", obj);
        }
        if (obj != null) {
            return (CoroutineDispatcher) obj;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.CoroutineDispatcher");
    }
}
