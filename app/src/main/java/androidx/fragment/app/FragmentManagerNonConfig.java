package androidx.fragment.app;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public class FragmentManagerNonConfig {
    @Nullable
    private final Collection<Fragment> a;
    @Nullable
    private final Map<String, FragmentManagerNonConfig> b;
    @Nullable
    private final Map<String, ViewModelStore> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragmentManagerNonConfig(@Nullable Collection<Fragment> collection, @Nullable Map<String, FragmentManagerNonConfig> map, @Nullable Map<String, ViewModelStore> map2) {
        this.a = collection;
        this.b = map;
        this.c = map2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Collection<Fragment> a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Map<String, FragmentManagerNonConfig> b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Map<String, ViewModelStore> c() {
        return this.c;
    }
}
