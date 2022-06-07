package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JobSupport.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u0004\u0018\u00010\u0016H\u0000\u001a\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0016*\u0004\u0018\u00010\u0016H\u0000\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\"\u0016\u0010\u0006\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0003\"\u0016\u0010\b\u001a\u00020\t8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0003\"\u0016\u0010\u000b\u001a\u00020\t8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\u0003\"\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000\"\u0016\u0010\u0010\u001a\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0011\u0010\u0003\"\u0016\u0010\u0012\u001a\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0013\u0010\u0003\"\u000e\u0010\u0014\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"COMPLETING_ALREADY", "Lkotlinx/coroutines/internal/Symbol;", "getCOMPLETING_ALREADY$annotations", "()V", "COMPLETING_RETRY", "getCOMPLETING_RETRY$annotations", "COMPLETING_WAITING_CHILDREN", "getCOMPLETING_WAITING_CHILDREN$annotations", "EMPTY_ACTIVE", "Lkotlinx/coroutines/Empty;", "getEMPTY_ACTIVE$annotations", "EMPTY_NEW", "getEMPTY_NEW$annotations", "FALSE", "", "RETRY", "SEALED", "getSEALED$annotations", "TOO_LATE_TO_CANCEL", "getTOO_LATE_TO_CANCEL$annotations", "TRUE", "boxIncomplete", "", "unboxState", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class JobSupportKt {
    @NotNull
    private static final Symbol a = new Symbol("COMPLETING_ALREADY");
    @JvmField
    @NotNull
    public static final Symbol COMPLETING_WAITING_CHILDREN = new Symbol("COMPLETING_WAITING_CHILDREN");
    @NotNull
    private static final Symbol b = new Symbol("COMPLETING_RETRY");
    @NotNull
    private static final Symbol c = new Symbol("TOO_LATE_TO_CANCEL");
    @NotNull
    private static final Symbol d = new Symbol("SEALED");
    @NotNull
    private static final n e = new n(false);
    @NotNull
    private static final n f = new n(true);

    public static final /* synthetic */ Symbol access$getCOMPLETING_ALREADY$p() {
        return a;
    }

    public static final /* synthetic */ Symbol access$getCOMPLETING_RETRY$p() {
        return b;
    }

    public static final /* synthetic */ n access$getEMPTY_ACTIVE$p() {
        return f;
    }

    public static final /* synthetic */ n access$getEMPTY_NEW$p() {
        return e;
    }

    public static final /* synthetic */ Symbol access$getSEALED$p() {
        return d;
    }

    public static final /* synthetic */ Symbol access$getTOO_LATE_TO_CANCEL$p() {
        return c;
    }

    public static /* synthetic */ void getCOMPLETING_WAITING_CHILDREN$annotations() {
    }

    @Nullable
    public static final Object boxIncomplete(@Nullable Object obj) {
        return obj instanceof Incomplete ? new p((Incomplete) obj) : obj;
    }

    @Nullable
    public static final Object unboxState(@Nullable Object obj) {
        Incomplete incomplete;
        p pVar = obj instanceof p ? (p) obj : null;
        return (pVar == null || (incomplete = pVar.a) == null) ? obj : incomplete;
    }
}
