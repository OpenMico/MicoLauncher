package androidx.work;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public abstract class InputMergerFactory {
    @Nullable
    public abstract InputMerger createInputMerger(@NonNull String str);

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final InputMerger createInputMergerWithDefaultFallback(@NonNull String str) {
        InputMerger createInputMerger = createInputMerger(str);
        return createInputMerger == null ? InputMerger.fromClassName(str) : createInputMerger;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static InputMergerFactory getDefaultInputMergerFactory() {
        return new InputMergerFactory() { // from class: androidx.work.InputMergerFactory.1
            @Override // androidx.work.InputMergerFactory
            @Nullable
            public InputMerger createInputMerger(@NonNull String str) {
                return null;
            }
        };
    }
}
