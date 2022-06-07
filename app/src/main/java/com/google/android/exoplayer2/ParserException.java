package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes.dex */
public class ParserException extends IOException {
    public final boolean contentIsMalformed;
    public final int dataType;

    public static ParserException createForMalformedDataOfUnknownType(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 0);
    }

    public static ParserException createForMalformedContainer(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 1);
    }

    public static ParserException createForMalformedManifest(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 4);
    }

    public static ParserException createForManifestWithUnsupportedFeature(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, false, 4);
    }

    public static ParserException createForUnsupportedContainerFeature(@Nullable String str) {
        return new ParserException(str, null, false, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ParserException(@Nullable String str, @Nullable Throwable th, boolean z, int i) {
        super(str, th);
        this.contentIsMalformed = z;
        this.dataType = i;
    }
}
