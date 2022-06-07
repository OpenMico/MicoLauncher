package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContext.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\nH\u0014J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\nR\u0010\u0010\b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/UndispatchedCoroutine;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/internal/ScopeCoroutine;", c.R, "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "savedContext", "savedOldValue", "", "afterResume", "", XiaomiOAuthConstants.EXTRA_STATE_2, "clearThreadContext", "", "saveThreadContext", "oldValue", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class UndispatchedCoroutine<T> extends ScopeCoroutine<T> {
    @Nullable
    private CoroutineContext a;
    @Nullable
    private Object b;

    public UndispatchedCoroutine(@NotNull CoroutineContext coroutineContext, @NotNull Continuation<? super T> continuation) {
        super(coroutineContext.get(ai.a) == null ? coroutineContext.plus(ai.a) : coroutineContext, continuation);
    }

    public final void saveThreadContext(@NotNull CoroutineContext coroutineContext, @Nullable Object obj) {
        this.a = coroutineContext;
        this.b = obj;
    }

    public final boolean clearThreadContext() {
        if (this.a == null) {
            return false;
        }
        this.a = null;
        this.b = null;
        return true;
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.AbstractCoroutine
    protected void afterResume(@Nullable Object obj) {
        UndispatchedCoroutine<?> undispatchedCoroutine;
        CoroutineContext coroutineContext = this.a;
        if (coroutineContext != null) {
            ThreadContextKt.restoreThreadContext(coroutineContext, this.b);
            this.a = null;
            this.b = null;
        }
        Object recoverResult = CompletionStateKt.recoverResult(obj, this.uCont);
        Continuation continuation = this.uCont;
        CoroutineContext context = continuation.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, null);
        if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
        } else {
            undispatchedCoroutine = null;
        }
        try {
            this.uCont.resumeWith(recoverResult);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
        }
    }
}
