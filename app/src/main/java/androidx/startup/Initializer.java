package androidx.startup;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.List;

/* loaded from: classes.dex */
public interface Initializer<T> {
    @NonNull
    T create(@NonNull Context context);

    @NonNull
    List<Class<? extends Initializer<?>>> dependencies();
}
