package androidx.work;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.List;

/* loaded from: classes.dex */
public abstract class InputMerger {
    private static final String a = Logger.tagWithPrefix("InputMerger");

    @NonNull
    public abstract Data merge(@NonNull List<Data> list);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static InputMerger fromClassName(String str) {
        try {
            return (InputMerger) Class.forName(str).newInstance();
        } catch (Exception e) {
            Logger logger = Logger.get();
            String str2 = a;
            logger.error(str2, "Trouble instantiating + " + str, e);
            return null;
        }
    }
}
