package com.milink.base.contract;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class ProviderContract {
    public static final String AUTHORITY = "provider.milink.mi.com";

    private ProviderContract() {
    }

    public static Uri getAuthorityUri() {
        return Uri.parse("content://provider.milink.mi.com");
    }

    public static boolean isProviderExist(@NonNull Context context) {
        return context.getContentResolver().getType(getAuthorityUri()) != null;
    }

    /* loaded from: classes2.dex */
    public static class Messenger {
        public static final String MESSENGER = "/messenger";

        public static String getPollMessageUriString() {
            return "content://provider.milink.mi.com/messenger#poll";
        }

        public static String getSendMessageUriString() {
            return "content://provider.milink.mi.com/messenger#send";
        }

        private Messenger() {
        }

        public static Uri getRegisterUri() {
            return Uri.parse("content://provider.milink.mi.com/messenger/register");
        }

        public static boolean isResponseSuccCode(Uri uri) {
            return MiLinkCodes.isSucc(uri.getQueryParameter("code"));
        }
    }
}
