package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class PlaybackException extends Exception implements Bundleable {
    public static final Bundleable.Creator<PlaybackException> CREATOR = $$Lambda$_TNbpzxdf4mFqqZ6N0idYO0xg.INSTANCE;
    public static final int CUSTOM_ERROR_CODE_BASE = 1000000;
    public static final int ERROR_CODE_AUDIO_TRACK_INIT_FAILED = 5001;
    public static final int ERROR_CODE_AUDIO_TRACK_WRITE_FAILED = 5002;
    public static final int ERROR_CODE_BEHIND_LIVE_WINDOW = 1002;
    public static final int ERROR_CODE_DECODER_INIT_FAILED = 4001;
    public static final int ERROR_CODE_DECODER_QUERY_FAILED = 4002;
    public static final int ERROR_CODE_DECODING_FAILED = 4003;
    public static final int ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES = 4004;
    public static final int ERROR_CODE_DECODING_FORMAT_UNSUPPORTED = 4005;
    public static final int ERROR_CODE_DRM_CONTENT_ERROR = 6003;
    public static final int ERROR_CODE_DRM_DEVICE_REVOKED = 6007;
    public static final int ERROR_CODE_DRM_DISALLOWED_OPERATION = 6005;
    public static final int ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED = 6004;
    public static final int ERROR_CODE_DRM_LICENSE_EXPIRED = 6008;
    public static final int ERROR_CODE_DRM_PROVISIONING_FAILED = 6002;
    public static final int ERROR_CODE_DRM_SCHEME_UNSUPPORTED = 6001;
    public static final int ERROR_CODE_DRM_SYSTEM_ERROR = 6006;
    public static final int ERROR_CODE_DRM_UNSPECIFIED = 6000;
    public static final int ERROR_CODE_FAILED_RUNTIME_CHECK = 1004;
    public static final int ERROR_CODE_IO_BAD_HTTP_STATUS = 2004;
    public static final int ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED = 2007;
    public static final int ERROR_CODE_IO_FILE_NOT_FOUND = 2005;
    public static final int ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE = 2003;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_FAILED = 2001;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT = 2002;
    public static final int ERROR_CODE_IO_NO_PERMISSION = 2006;
    public static final int ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE = 2008;
    public static final int ERROR_CODE_IO_UNSPECIFIED = 2000;
    public static final int ERROR_CODE_PARSING_CONTAINER_MALFORMED = 3001;
    public static final int ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED = 3003;
    public static final int ERROR_CODE_PARSING_MANIFEST_MALFORMED = 3002;
    public static final int ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED = 3004;
    public static final int ERROR_CODE_REMOTE_ERROR = 1001;
    public static final int ERROR_CODE_TIMEOUT = 1003;
    public static final int ERROR_CODE_UNSPECIFIED = 1000;
    protected static final int FIELD_CUSTOM_ID_BASE = 1000;
    public final int errorCode;
    public final long timestampMs;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ErrorCode {
    }

    public static String getErrorCodeName(int i) {
        switch (i) {
            case 1000:
                return "ERROR_CODE_UNSPECIFIED";
            case 1001:
                return "ERROR_CODE_REMOTE_ERROR";
            case 1002:
                return "ERROR_CODE_BEHIND_LIVE_WINDOW";
            case 1003:
                return "ERROR_CODE_TIMEOUT";
            case 1004:
                return "ERROR_CODE_FAILED_RUNTIME_CHECK";
            default:
                switch (i) {
                    case 2000:
                        return "ERROR_CODE_IO_UNSPECIFIED";
                    case 2001:
                        return "ERROR_CODE_IO_NETWORK_CONNECTION_FAILED";
                    case ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT /* 2002 */:
                        return "ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT";
                    case 2003:
                        return "ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE";
                    case 2004:
                        return "ERROR_CODE_IO_BAD_HTTP_STATUS";
                    case 2005:
                        return "ERROR_CODE_IO_FILE_NOT_FOUND";
                    case ERROR_CODE_IO_NO_PERMISSION /* 2006 */:
                        return "ERROR_CODE_IO_NO_PERMISSION";
                    case ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED /* 2007 */:
                        return "ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED";
                    case 2008:
                        return "ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE";
                    default:
                        switch (i) {
                            case 3001:
                                return "ERROR_CODE_PARSING_CONTAINER_MALFORMED";
                            case 3002:
                                return "ERROR_CODE_PARSING_MANIFEST_MALFORMED";
                            case 3003:
                                return "ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED";
                            case ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED /* 3004 */:
                                return "ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED";
                            default:
                                switch (i) {
                                    case ERROR_CODE_DECODER_INIT_FAILED /* 4001 */:
                                        return "ERROR_CODE_DECODER_INIT_FAILED";
                                    case 4002:
                                        return "ERROR_CODE_DECODER_QUERY_FAILED";
                                    case 4003:
                                        return "ERROR_CODE_DECODING_FAILED";
                                    case 4004:
                                        return "ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES";
                                    case ERROR_CODE_DECODING_FORMAT_UNSUPPORTED /* 4005 */:
                                        return "ERROR_CODE_DECODING_FORMAT_UNSUPPORTED";
                                    default:
                                        switch (i) {
                                            case ERROR_CODE_AUDIO_TRACK_INIT_FAILED /* 5001 */:
                                                return "ERROR_CODE_AUDIO_TRACK_INIT_FAILED";
                                            case ERROR_CODE_AUDIO_TRACK_WRITE_FAILED /* 5002 */:
                                                return "ERROR_CODE_AUDIO_TRACK_WRITE_FAILED";
                                            default:
                                                switch (i) {
                                                    case 6000:
                                                        return "ERROR_CODE_DRM_UNSPECIFIED";
                                                    case 6001:
                                                        return "ERROR_CODE_DRM_SCHEME_UNSUPPORTED";
                                                    case 6002:
                                                        return "ERROR_CODE_DRM_PROVISIONING_FAILED";
                                                    case ERROR_CODE_DRM_CONTENT_ERROR /* 6003 */:
                                                        return "ERROR_CODE_DRM_CONTENT_ERROR";
                                                    case ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED /* 6004 */:
                                                        return "ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED";
                                                    case ERROR_CODE_DRM_DISALLOWED_OPERATION /* 6005 */:
                                                        return "ERROR_CODE_DRM_DISALLOWED_OPERATION";
                                                    case ERROR_CODE_DRM_SYSTEM_ERROR /* 6006 */:
                                                        return "ERROR_CODE_DRM_SYSTEM_ERROR";
                                                    case ERROR_CODE_DRM_DEVICE_REVOKED /* 6007 */:
                                                        return "ERROR_CODE_DRM_DEVICE_REVOKED";
                                                    case ERROR_CODE_DRM_LICENSE_EXPIRED /* 6008 */:
                                                        return "ERROR_CODE_DRM_LICENSE_EXPIRED";
                                                    default:
                                                        return i >= 1000000 ? "custom error code" : "invalid error code";
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public final String getErrorCodeName() {
        return getErrorCodeName(this.errorCode);
    }

    public PlaybackException(@Nullable String str, @Nullable Throwable th, int i) {
        this(str, th, i, Clock.DEFAULT.elapsedRealtime());
    }

    public PlaybackException(Bundle bundle) {
        this(bundle.getString(keyForField(2)), a(bundle), bundle.getInt(keyForField(0), 1000), bundle.getLong(keyForField(1), SystemClock.elapsedRealtime()));
    }

    public PlaybackException(@Nullable String str, @Nullable Throwable th, int i, long j) {
        super(str, th);
        this.errorCode = i;
        this.timestampMs = j;
    }

    @CallSuper
    public boolean errorInfoEquals(@Nullable PlaybackException playbackException) {
        if (this == playbackException) {
            return true;
        }
        if (playbackException == null || getClass() != playbackException.getClass()) {
            return false;
        }
        Throwable cause = getCause();
        Throwable cause2 = playbackException.getCause();
        if (cause == null || cause2 == null) {
            if (!(cause == null && cause2 == null)) {
                return false;
            }
        } else if (!Util.areEqual(cause.getMessage(), cause2.getMessage()) || !Util.areEqual(cause.getClass(), cause2.getClass())) {
            return false;
        }
        return this.errorCode == playbackException.errorCode && Util.areEqual(getMessage(), playbackException.getMessage()) && this.timestampMs == playbackException.timestampMs;
    }

    @CallSuper
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), this.errorCode);
        bundle.putLong(keyForField(1), this.timestampMs);
        bundle.putString(keyForField(2), getMessage());
        Throwable cause = getCause();
        if (cause != null) {
            bundle.putString(keyForField(3), cause.getClass().getName());
            bundle.putString(keyForField(4), cause.getMessage());
        }
        return bundle;
    }

    protected static String keyForField(int i) {
        return Integer.toString(i, 36);
    }

    private static Throwable a(Class<?> cls, @Nullable String str) throws Exception {
        return (Throwable) cls.getConstructor(String.class).newInstance(str);
    }

    private static RemoteException a(@Nullable String str) {
        return new RemoteException(str);
    }

    @Nullable
    private static Throwable a(Bundle bundle) {
        String string = bundle.getString(keyForField(3));
        String string2 = bundle.getString(keyForField(4));
        Throwable th = null;
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            Class<?> cls = Class.forName(string, true, PlaybackException.class.getClassLoader());
            if (Throwable.class.isAssignableFrom(cls)) {
                th = a(cls, string2);
            }
            if (th != null) {
                return th;
            }
        } catch (Throwable unused) {
        }
        return a(string2);
    }
}
