package com.milink.base.contract;

import android.content.UriMatcher;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.Urn;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LockContract {
    public static final String AUTHORITY = "milink.mi.com";
    public static final String COL_LOCK_NAME = "col_lock_name";
    public static final String COL_LOCK_SCOPE = "col_lock_scope";
    public static final String COL_TAG = "col_tag";
    public static String LOCK_NID = "mi-lock";
    public static final String NONE = "";
    public static final String SCOPE_LOCAL_DEVICE = "local-device";

    private LockContract() {
    }

    public static String genIdentifyString(String str, String str2) {
        return ((String) Objects.requireNonNull(str)) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + ((String) Objects.requireNonNull(str2));
    }

    /* loaded from: classes2.dex */
    public static final class Event {
        public static final String ASK_FOR_LOCK = "ask_for_lock";
        public static final String ASK_FOR_LOCK_REJECT = "ask_for_lock_reject";
        public static final String CLIENT_REPLY = "client_reply";
        public static final String LOCK_GRANTED = "granted";
        public static final String LOCK_REVOKED = "revoked";
        public static final String LOCK_REVOKE_BEFORE = "before_revoke_lock";
        public static final String LOCK_TRANSFER = "transfer";

        private Event() {
        }
    }

    /* loaded from: classes2.dex */
    public static final class Action {
        public static final String LOCK_STATUS = "lock_status";
        public static final String TICK_INFO = "tick_info";

        private Action() {
        }
    }

    /* loaded from: classes2.dex */
    public static final class Code {
        public static final int BUSY = 3;
        public static final int ERROR = -1;
        public static final int INTERRUPTED = 4;
        public static final int LOCK_SERVER_NOT_FOUND = -2;
        public static final int LOCK_TRANSFER_FAIL = 2;
        public static final int RELEASED = 1;
        public static final int SUCC = 0;

        private Code() {
        }
    }

    /* loaded from: classes2.dex */
    public static final class Matcher {
        public static final int ACTION_LOCAL_DEVICE_LOCK = 2;
        public static final int ACTION_TICK = 1;
        public static final String LOCK = "lock";
        public static final Uri ROOT_URI = Uri.parse("content://milink.mi.com");
        private static final UriMatcher a = new UriMatcher(-1);

        static {
            a.addURI(LockContract.AUTHORITY, "lock/tick/*/*", 1);
            a.addURI(LockContract.AUTHORITY, "lock/local-device/*", 2);
        }

        private Matcher() {
        }

        public static Uri getLockUri(@NonNull String str, @NonNull String str2) {
            return ROOT_URI.buildUpon().appendPath(LOCK).appendPath((String) Objects.requireNonNull(str)).appendPath((String) Objects.requireNonNull(str2)).build();
        }

        public static Uri getLockStatusChangeUri(@NonNull String str, @NonNull String str2) {
            return ROOT_URI.buildUpon().appendPath(LOCK).appendPath((String) Objects.requireNonNull(str)).appendPath((String) Objects.requireNonNull(str2)).appendPath("status").build();
        }

        public static Uri getTickUri(@NonNull String str, @NonNull String str2) {
            return ROOT_URI.buildUpon().appendPath(LOCK).appendPath("tick").appendPath((String) Objects.requireNonNull(str)).appendPath((String) Objects.requireNonNull(str2)).build();
        }

        public static Uri getLockUriWithIdentify(String str, String str2, String str3, String str4) {
            return ROOT_URI.buildUpon().appendPath(LOCK).appendPath(str).appendPath(str2).appendPath(LockContract.genIdentifyString(str3, str4)).build();
        }

        public static int match(@NonNull Uri uri) {
            return a.match((Uri) Objects.requireNonNull(uri));
        }

        public static Uri convertToRequestUri(@NonNull Uri uri, String str, String str2) {
            String lastPathSegment = uri.getLastPathSegment();
            if (!Objects.equals(lastPathSegment, LockContract.genIdentifyString(str, str2))) {
                return uri;
            }
            String uri2 = uri.toString();
            return Uri.parse(uri2.replace("/" + lastPathSegment, ""));
        }
    }

    /* loaded from: classes2.dex */
    public static class LockUrn {
        private final Urn a;
        private int b = -1;

        private LockUrn(Urn urn) {
            this.a = urn;
        }

        @Nullable
        public static LockUrn parse(String str) {
            try {
                Urn parse = Urn.parse(str);
                if (parse.getNID().equals(LockContract.LOCK_NID)) {
                    return new LockUrn(parse);
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public String getNID() {
            return this.a.getNID();
        }

        public String getLockScope() {
            return a().substring(0, b());
        }

        public String getLockName() {
            if (a().contains(";")) {
                return a().substring(b() + 1, a().indexOf(59));
            }
            return a().substring(b() + 1);
        }

        private String a() {
            return this.a.getNSS();
        }

        private int b() {
            int i = this.b;
            if (i != -1) {
                return i;
            }
            int indexOf = a().indexOf(58);
            this.b = indexOf;
            return indexOf;
        }
    }
}
