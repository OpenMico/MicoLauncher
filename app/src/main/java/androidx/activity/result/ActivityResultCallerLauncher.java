package androidx.activity.result;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import com.xiaomi.idm.service.iot.InputMethodService;
import com.xiaomi.mipush.sdk.MiPushClient;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityResultCaller.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B/\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\tJ\u0014\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00010\u0007H\u0016J!\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016¢\u0006\u0002\u0010\u001aJ\b\u0010\u001b\u001a\u00020\u0004H\u0016R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\b\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R'\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00010\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0012\u0010\u000b¨\u0006\u001c"}, d2 = {"Landroidx/activity/result/ActivityResultCallerLauncher;", "I", "O", "Landroidx/activity/result/ActivityResultLauncher;", "", "launcher", "callerContract", "Landroidx/activity/result/contract/ActivityResultContract;", InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "(Landroidx/activity/result/ActivityResultLauncher;Landroidx/activity/result/contract/ActivityResultContract;Ljava/lang/Object;)V", "getCallerContract", "()Landroidx/activity/result/contract/ActivityResultContract;", "getInput", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getLauncher", "()Landroidx/activity/result/ActivityResultLauncher;", "resultContract", "getResultContract", "resultContract$delegate", "Lkotlin/Lazy;", "getContract", "launch", "void", "options", "Landroidx/core/app/ActivityOptionsCompat;", "(Lkotlin/Unit;Landroidx/core/app/ActivityOptionsCompat;)V", MiPushClient.COMMAND_UNREGISTER, "activity-ktx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ActivityResultCallerLauncher<I, O> extends ActivityResultLauncher<Unit> {
    @NotNull
    private final ActivityResultLauncher<I> a;
    @NotNull
    private final ActivityResultContract<I, O> b;
    private final I c;
    @NotNull
    private final Lazy d = LazyKt.lazy(new a(this));

    @NotNull
    public final ActivityResultLauncher<I> getLauncher() {
        return this.a;
    }

    @NotNull
    public final ActivityResultContract<I, O> getCallerContract() {
        return this.b;
    }

    public final I getInput() {
        return this.c;
    }

    public ActivityResultCallerLauncher(@NotNull ActivityResultLauncher<I> launcher, @NotNull ActivityResultContract<I, O> callerContract, I i) {
        Intrinsics.checkNotNullParameter(launcher, "launcher");
        Intrinsics.checkNotNullParameter(callerContract, "callerContract");
        this.a = launcher;
        this.b = callerContract;
        this.c = i;
    }

    /* compiled from: ActivityResultCaller.kt */
    @Metadata(d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\n"}, d2 = {"<anonymous>", "androidx/activity/result/ActivityResultCallerLauncher$resultContract$2$1", "I", "O"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    static final class a extends Lambda implements Function0<ActivityResultCallerLauncher$resultContract$2$1> {
        final /* synthetic */ ActivityResultCallerLauncher<I, O> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(ActivityResultCallerLauncher<I, O> activityResultCallerLauncher) {
            super(0);
            this.this$0 = activityResultCallerLauncher;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.activity.result.ActivityResultCallerLauncher$resultContract$2$1] */
        @NotNull
        /* renamed from: a */
        public final ActivityResultCallerLauncher$resultContract$2$1 invoke() {
            final ActivityResultCallerLauncher<I, O> activityResultCallerLauncher = this.this$0;
            return new ActivityResultContract<Unit, O>() { // from class: androidx.activity.result.ActivityResultCallerLauncher$resultContract$2$1
                @NotNull
                public Intent createIntent(@NotNull Context context, @Nullable Unit unit) {
                    Intrinsics.checkNotNullParameter(context, "context");
                    Intent createIntent = activityResultCallerLauncher.getCallerContract().createIntent(context, activityResultCallerLauncher.getInput());
                    Intrinsics.checkNotNullExpressionValue(createIntent, "callerContract.createIntent(context, input)");
                    return createIntent;
                }

                /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.Object, O] */
                @Override // androidx.activity.result.contract.ActivityResultContract
                public O parseResult(int i, @Nullable Intent intent) {
                    return activityResultCallerLauncher.getCallerContract().parseResult(i, intent);
                }
            };
        }
    }

    @NotNull
    public final ActivityResultContract<Unit, O> getResultContract() {
        return (ActivityResultContract) this.d.getValue();
    }

    public void launch(@Nullable Unit unit, @Nullable ActivityOptionsCompat activityOptionsCompat) {
        this.a.launch(this.c, activityOptionsCompat);
    }

    @Override // androidx.activity.result.ActivityResultLauncher
    public void unregister() {
        this.a.unregister();
    }

    @Override // androidx.activity.result.ActivityResultLauncher
    @NotNull
    public ActivityResultContract<Unit, ?> getContract() {
        return (ActivityResultContract<Unit, O>) getResultContract();
    }
}
