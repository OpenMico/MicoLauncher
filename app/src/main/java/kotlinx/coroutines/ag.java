package kotlinx.coroutines;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Interruptible.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\b\u0002\u0018\u00002#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b\u001c\u0012\b\b\u001d\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00050\u001bj\u0002`\u001eB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0096\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0011\u001a\u00020\u0005¢\u0006\u0004\b\u0011\u0010\u0007R\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00128\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0002\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u0015R\u001e\u0010\u0018\u001a\n \u0017*\u0004\u0018\u00010\u00160\u00168\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lkotlinx/coroutines/ThreadState;", "Lkotlinx/coroutines/Job;", "job", "<init>", "(Lkotlinx/coroutines/Job;)V", "", "clearInterrupt", "()V", "", XiaomiOAuthConstants.EXTRA_STATE_2, "", "invalidState", "(I)Ljava/lang/Void;", "", "cause", "invoke", "(Ljava/lang/Throwable;)V", SkillStatHelper.SKILL_STAT_SETUP, "Lkotlinx/coroutines/DisposableHandle;", "cancelHandle", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/Job;", "Ljava/lang/Thread;", "kotlin.jvm.PlatformType", "targetThread", "Ljava/lang/Thread;", "kotlinx-coroutines-core", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
final class ag implements Function1<Throwable, Unit> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(ag.class, "_state");
    @NotNull
    private final Job a;
    @Nullable
    private DisposableHandle d;
    @NotNull
    private volatile /* synthetic */ int _state = 0;
    private final Thread c = Thread.currentThread();

    public ag(@NotNull Job job) {
        this.a = job;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* synthetic */ Unit invoke(Throwable th) {
        a(th);
        return Unit.INSTANCE;
    }

    public final void a() {
        int i;
        this.d = this.a.invokeOnCompletion(true, true, this);
        do {
            i = this._state;
            if (i != 0) {
                switch (i) {
                    case 2:
                    case 3:
                        return;
                    default:
                        a(i);
                        throw new KotlinNothingValueException();
                }
            }
        } while (!b.compareAndSet(this, i, 0));
    }

    private final Void a(int i) {
        throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", Integer.valueOf(i)).toString());
    }

    public final void b() {
        while (true) {
            int i = this._state;
            if (i != 0) {
                switch (i) {
                    case 2:
                        break;
                    case 3:
                        Thread.interrupted();
                        return;
                    default:
                        a(i);
                        throw new KotlinNothingValueException();
                }
            } else if (b.compareAndSet(this, i, 1)) {
                DisposableHandle disposableHandle = this.d;
                if (disposableHandle != null) {
                    disposableHandle.dispose();
                    return;
                }
                return;
            }
        }
    }

    public void a(@Nullable Throwable th) {
        int i;
        do {
            i = this._state;
            switch (i) {
                case 0:
                    break;
                case 1:
                case 2:
                case 3:
                    return;
                default:
                    a(i);
                    throw new KotlinNothingValueException();
            }
        } while (!b.compareAndSet(this, i, 2));
        this.c.interrupt();
        this._state = 3;
    }
}
