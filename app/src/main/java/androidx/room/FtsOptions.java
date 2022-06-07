package androidx.room;

import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public class FtsOptions {
    public static final String TOKENIZER_ICU = "icu";
    public static final String TOKENIZER_PORTER = "porter";
    public static final String TOKENIZER_SIMPLE = "simple";
    @RequiresApi(21)
    public static final String TOKENIZER_UNICODE61 = "unicode61";

    /* loaded from: classes.dex */
    public enum MatchInfo {
        FTS3,
        FTS4
    }

    /* loaded from: classes.dex */
    public enum Order {
        ASC,
        DESC
    }

    private FtsOptions() {
    }
}
