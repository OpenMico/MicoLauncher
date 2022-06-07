package androidx.fragment.app;

import android.view.View;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"findFragment", HomePageRecordHelper.AREA_F, "Landroidx/fragment/app/Fragment;", "Landroid/view/View;", "(Landroid/view/View;)Landroidx/fragment/app/Fragment;", "fragment-ktx_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class ViewKt {
    @NotNull
    public static final <F extends Fragment> F findFragment(@NotNull View findFragment) {
        Intrinsics.checkNotNullParameter(findFragment, "$this$findFragment");
        F f = (F) FragmentManager.findFragment(findFragment);
        Intrinsics.checkNotNullExpressionValue(f, "FragmentManager.findFragment(this)");
        return f;
    }
}
