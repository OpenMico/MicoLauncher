package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import com.xiaomi.idm.service.iot.InputMethodService;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ActivityResultCaller.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aQ\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004*\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\b\u001a\u0002H\u00032\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00020\n¢\u0006\u0002\u0010\u000b\u001aY\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004*\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\b\u001a\u0002H\u00032\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00020\n¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"registerForActivityResult", "Landroidx/activity/result/ActivityResultLauncher;", "", "I", "O", "Landroidx/activity/result/ActivityResultCaller;", "contract", "Landroidx/activity/result/contract/ActivityResultContract;", InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "callback", "Lkotlin/Function1;", "(Landroidx/activity/result/ActivityResultCaller;Landroidx/activity/result/contract/ActivityResultContract;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Landroidx/activity/result/ActivityResultLauncher;", "registry", "Landroidx/activity/result/ActivityResultRegistry;", "(Landroidx/activity/result/ActivityResultCaller;Landroidx/activity/result/contract/ActivityResultContract;Ljava/lang/Object;Landroidx/activity/result/ActivityResultRegistry;Lkotlin/jvm/functions/Function1;)Landroidx/activity/result/ActivityResultLauncher;", "activity-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ActivityResultCallerKt {

    /* compiled from: ActivityResultCaller.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u000e\u0010\u0004\u001a\n \u0005*\u0004\u0018\u0001H\u0003H\u0003H\n"}, d2 = {"<anonymous>", "", "I", "O", "it", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    static final class a<O> implements ActivityResultCallback {
        final /* synthetic */ Function1<O, Unit> a;

        /* JADX WARN: Multi-variable type inference failed */
        a(Function1<? super O, Unit> function1) {
            this.a = function1;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(O o) {
            this.a.invoke(o);
        }
    }

    @NotNull
    public static final <I, O> ActivityResultLauncher<Unit> registerForActivityResult(@NotNull ActivityResultCaller activityResultCaller, @NotNull ActivityResultContract<I, O> contract, I i, @NotNull ActivityResultRegistry registry, @NotNull Function1<? super O, Unit> callback) {
        Intrinsics.checkNotNullParameter(activityResultCaller, "<this>");
        Intrinsics.checkNotNullParameter(contract, "contract");
        Intrinsics.checkNotNullParameter(registry, "registry");
        Intrinsics.checkNotNullParameter(callback, "callback");
        ActivityResultLauncher<I> registerForActivityResult = activityResultCaller.registerForActivityResult(contract, registry, new a(callback));
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "callback: (O) -> Unit\n): ActivityResultLauncher<Unit> {\n    val resultLauncher = registerForActivityResult(contract, registry) { callback(it) }");
        return new ActivityResultCallerLauncher(registerForActivityResult, contract, i);
    }

    /* compiled from: ActivityResultCaller.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u000e\u0010\u0004\u001a\n \u0005*\u0004\u0018\u0001H\u0003H\u0003H\n"}, d2 = {"<anonymous>", "", "I", "O", "it", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    static final class b<O> implements ActivityResultCallback {
        final /* synthetic */ Function1<O, Unit> a;

        /* JADX WARN: Multi-variable type inference failed */
        b(Function1<? super O, Unit> function1) {
            this.a = function1;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(O o) {
            this.a.invoke(o);
        }
    }

    @NotNull
    public static final <I, O> ActivityResultLauncher<Unit> registerForActivityResult(@NotNull ActivityResultCaller activityResultCaller, @NotNull ActivityResultContract<I, O> contract, I i, @NotNull Function1<? super O, Unit> callback) {
        Intrinsics.checkNotNullParameter(activityResultCaller, "<this>");
        Intrinsics.checkNotNullParameter(contract, "contract");
        Intrinsics.checkNotNullParameter(callback, "callback");
        ActivityResultLauncher<I> registerForActivityResult = activityResultCaller.registerForActivityResult(contract, new b(callback));
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "callback: (O) -> Unit\n): ActivityResultLauncher<Unit> {\n    val resultLauncher = registerForActivityResult(contract) { callback(it) }");
        return new ActivityResultCallerLauncher(registerForActivityResult, contract, i);
    }
}
