package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ExoPlaybackException extends PlaybackException {
    public static final Bundleable.Creator<ExoPlaybackException> CREATOR = $$Lambda$ExoPlaybackException$bY37w8P9dBwyEbzzDSJ5yeMpmnA.INSTANCE;
    public static final int TYPE_REMOTE = 3;
    public static final int TYPE_RENDERER = 1;
    public static final int TYPE_SOURCE = 0;
    public static final int TYPE_UNEXPECTED = 2;
    final boolean isRecoverable;
    @Nullable
    public final MediaPeriodId mediaPeriodId;
    @Nullable
    public final Format rendererFormat;
    public final int rendererFormatSupport;
    public final int rendererIndex;
    @Nullable
    public final String rendererName;
    public final int type;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Type {
    }

    public static /* synthetic */ ExoPlaybackException lambda$bY37w8P9dBwyEbzzDSJ5yeMpmnA(Bundle bundle) {
        return new ExoPlaybackException(bundle);
    }

    public static ExoPlaybackException createForSource(IOException iOException, int i) {
        return new ExoPlaybackException(0, iOException, i);
    }

    public static ExoPlaybackException createForRenderer(Throwable th, String str, int i, @Nullable Format format, int i2, boolean z, int i3) {
        return new ExoPlaybackException(1, th, null, i3, str, i, format, format == null ? 4 : i2, z);
    }

    @Deprecated
    public static ExoPlaybackException createForUnexpected(RuntimeException runtimeException) {
        return createForUnexpected(runtimeException, 1000);
    }

    public static ExoPlaybackException createForUnexpected(RuntimeException runtimeException, int i) {
        return new ExoPlaybackException(2, runtimeException, i);
    }

    public static ExoPlaybackException createForRemote(String str) {
        return new ExoPlaybackException(3, null, str, 1001, null, -1, null, 4, false);
    }

    private ExoPlaybackException(int i, Throwable th, int i2) {
        this(i, th, null, i2, null, -1, null, 4, false);
    }

    private ExoPlaybackException(int i, @Nullable Throwable th, @Nullable String str, int i2, @Nullable String str2, int i3, @Nullable Format format, int i4, boolean z) {
        this(a(i, str, str2, i3, format, i4), th, i2, i, str2, i3, format, i4, null, SystemClock.elapsedRealtime(), z);
    }

    private ExoPlaybackException(Bundle bundle) {
        super(bundle);
        this.type = bundle.getInt(keyForField(1001), 2);
        this.rendererName = bundle.getString(keyForField(1002));
        this.rendererIndex = bundle.getInt(keyForField(1003), -1);
        this.rendererFormat = (Format) bundle.getParcelable(keyForField(1004));
        this.rendererFormatSupport = bundle.getInt(keyForField(1005), 4);
        this.isRecoverable = bundle.getBoolean(keyForField(1006), false);
        this.mediaPeriodId = null;
    }

    private ExoPlaybackException(String str, @Nullable Throwable th, int i, int i2, @Nullable String str2, int i3, @Nullable Format format, int i4, @Nullable MediaPeriodId mediaPeriodId, long j, boolean z) {
        super(str, th, i, j);
        boolean z2 = false;
        Assertions.checkArgument(!z || i2 == 1);
        Assertions.checkArgument((th != null || i2 == 3) ? true : z2);
        this.type = i2;
        this.rendererName = str2;
        this.rendererIndex = i3;
        this.rendererFormat = format;
        this.rendererFormatSupport = i4;
        this.mediaPeriodId = mediaPeriodId;
        this.isRecoverable = z;
    }

    public IOException getSourceException() {
        Assertions.checkState(this.type == 0);
        return (IOException) Assertions.checkNotNull(getCause());
    }

    public Exception getRendererException() {
        boolean z = true;
        if (this.type != 1) {
            z = false;
        }
        Assertions.checkState(z);
        return (Exception) Assertions.checkNotNull(getCause());
    }

    public RuntimeException getUnexpectedException() {
        Assertions.checkState(this.type == 2);
        return (RuntimeException) Assertions.checkNotNull(getCause());
    }

    @Override // com.google.android.exoplayer2.PlaybackException
    public boolean errorInfoEquals(@Nullable PlaybackException playbackException) {
        if (!super.errorInfoEquals(playbackException)) {
            return false;
        }
        ExoPlaybackException exoPlaybackException = (ExoPlaybackException) Util.castNonNull(playbackException);
        return this.type == exoPlaybackException.type && Util.areEqual(this.rendererName, exoPlaybackException.rendererName) && this.rendererIndex == exoPlaybackException.rendererIndex && Util.areEqual(this.rendererFormat, exoPlaybackException.rendererFormat) && this.rendererFormatSupport == exoPlaybackException.rendererFormatSupport && Util.areEqual(this.mediaPeriodId, exoPlaybackException.mediaPeriodId) && this.isRecoverable == exoPlaybackException.isRecoverable;
    }

    @CheckResult
    public ExoPlaybackException a(@Nullable MediaPeriodId mediaPeriodId) {
        return new ExoPlaybackException((String) Util.castNonNull(getMessage()), getCause(), this.errorCode, this.type, this.rendererName, this.rendererIndex, this.rendererFormat, this.rendererFormatSupport, mediaPeriodId, this.timestampMs, this.isRecoverable);
    }

    private static String a(int i, @Nullable String str, @Nullable String str2, int i2, @Nullable Format format, int i3) {
        String str3;
        if (i != 3) {
            switch (i) {
                case 0:
                    str3 = "Source error";
                    break;
                case 1:
                    String valueOf = String.valueOf(format);
                    String formatSupportString = C.getFormatSupportString(i3);
                    StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 53 + String.valueOf(valueOf).length() + String.valueOf(formatSupportString).length());
                    sb.append(str2);
                    sb.append(" error, index=");
                    sb.append(i2);
                    sb.append(", format=");
                    sb.append(valueOf);
                    sb.append(", format_supported=");
                    sb.append(formatSupportString);
                    str3 = sb.toString();
                    break;
                default:
                    str3 = "Unexpected runtime error";
                    break;
            }
        } else {
            str3 = "Remote error";
        }
        if (TextUtils.isEmpty(str)) {
            return str3;
        }
        String valueOf2 = String.valueOf(str3);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 2 + String.valueOf(str).length());
        sb2.append(valueOf2);
        sb2.append(": ");
        sb2.append(str);
        return sb2.toString();
    }

    @Override // com.google.android.exoplayer2.PlaybackException, com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt(keyForField(1001), this.type);
        bundle.putString(keyForField(1002), this.rendererName);
        bundle.putInt(keyForField(1003), this.rendererIndex);
        bundle.putParcelable(keyForField(1004), this.rendererFormat);
        bundle.putInt(keyForField(1005), this.rendererFormatSupport);
        bundle.putBoolean(keyForField(1006), this.isRecoverable);
        return bundle;
    }
}
