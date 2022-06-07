package androidx.documentfile.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.milink.base.contract.MiLinkKeys;

/* compiled from: DocumentsContractApi19.java */
@RequiresApi(19)
/* loaded from: classes.dex */
class a {
    public static boolean a(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri) && (d(context, uri) & 512) != 0;
    }

    @Nullable
    public static String b(Context context, Uri uri) {
        return a(context, uri, "_display_name", (String) null);
    }

    @Nullable
    private static String l(Context context, Uri uri) {
        return a(context, uri, "mime_type", (String) null);
    }

    @Nullable
    public static String c(Context context, Uri uri) {
        String l = l(context, uri);
        if ("vnd.android.document/directory".equals(l)) {
            return null;
        }
        return l;
    }

    public static long d(Context context, Uri uri) {
        return a(context, uri, MiLinkKeys.PARAM_FLAGS, 0L);
    }

    public static boolean e(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(l(context, uri));
    }

    public static boolean f(Context context, Uri uri) {
        String l = l(context, uri);
        return !"vnd.android.document/directory".equals(l) && !TextUtils.isEmpty(l);
    }

    public static long g(Context context, Uri uri) {
        return a(context, uri, "last_modified", 0L);
    }

    public static long h(Context context, Uri uri) {
        return a(context, uri, "_size", 0L);
    }

    public static boolean i(Context context, Uri uri) {
        return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(l(context, uri));
    }

    public static boolean j(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String l = l(context, uri);
        int a = a(context, uri, MiLinkKeys.PARAM_FLAGS, 0);
        if (TextUtils.isEmpty(l)) {
            return false;
        }
        if ((a & 4) != 0) {
            return true;
        }
        if (!"vnd.android.document/directory".equals(l) || (a & 8) == 0) {
            return !TextUtils.isEmpty(l) && (a & 2) != 0;
        }
        return true;
    }

    public static boolean k(Context context, Uri uri) {
        Cursor cursor;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            boolean z = false;
            cursor = null;
            cursor = contentResolver.query(uri, new String[]{"document_id"}, null, null, null);
            if (cursor.getCount() > 0) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            Log.w("DocumentFile", "Failed query: " + e);
            return false;
        } finally {
            a(cursor);
        }
    }

    @Nullable
    private static String a(Context context, Uri uri, String str, @Nullable String str2) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            return (!cursor.moveToFirst() || cursor.isNull(0)) ? str2 : cursor.getString(0);
        } catch (Exception e) {
            Log.w("DocumentFile", "Failed query: " + e);
            return str2;
        } finally {
            a(cursor);
        }
    }

    private static int a(Context context, Uri uri, String str, int i) {
        return (int) a(context, uri, str, i);
    }

    private static long a(Context context, Uri uri, String str, long j) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            return (!cursor.moveToFirst() || cursor.isNull(0)) ? j : cursor.getLong(0);
        } catch (Exception e) {
            Log.w("DocumentFile", "Failed query: " + e);
            return j;
        } finally {
            a(cursor);
        }
    }

    private static void a(@Nullable AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }
}
