package androidx.work;

import androidx.annotation.NonNull;
import androidx.work.Data;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public final class OverwritingInputMerger extends InputMerger {
    @Override // androidx.work.InputMerger
    @NonNull
    public Data merge(@NonNull List<Data> list) {
        Data.Builder builder = new Data.Builder();
        HashMap hashMap = new HashMap();
        for (Data data : list) {
            hashMap.putAll(data.getKeyValueMap());
        }
        builder.putAll(hashMap);
        return builder.build();
    }
}
