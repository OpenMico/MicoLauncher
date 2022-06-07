package androidx.databinding;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.Collections;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public abstract class DataBinderMapper {
    public abstract String convertBrIdToString(int i);

    public abstract ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i);

    public abstract ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i);

    public abstract int getLayoutId(String str);

    @NonNull
    public List<DataBinderMapper> collectDependencies() {
        return Collections.emptyList();
    }
}
