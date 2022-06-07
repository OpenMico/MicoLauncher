package androidx.lifecycle;

import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewModelProviderExtensions.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {BluetoothConstants.GET, "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/lifecycle/ViewModelProvider;", "(Landroidx/lifecycle/ViewModelProvider;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
@JvmName(name = "ViewModelProviderKt")
/* loaded from: classes.dex */
public final class ViewModelProviderKt {
    @MainThread
    public static final /* synthetic */ <VM extends ViewModel> VM get(ViewModelProvider viewModelProvider) {
        Intrinsics.checkNotNullParameter(viewModelProvider, "<this>");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return (VM) viewModelProvider.get(ViewModel.class);
    }
}
