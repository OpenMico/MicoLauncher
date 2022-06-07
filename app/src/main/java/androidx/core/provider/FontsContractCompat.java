package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class FontsContractCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static final String PARCEL_FONT_RESULTS = "font_results";

    /* loaded from: classes.dex */
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    /* loaded from: classes.dex */
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Deprecated
        public static final int RESULT_OK = 0;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        /* loaded from: classes.dex */
        public @interface FontRequestFailReason {
        }

        public void onTypefaceRequestFailed(int i) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }
    }

    private FontsContractCompat() {
    }

    @Nullable
    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fontInfoArr) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fontInfoArr, 0);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        return c.a(context, fontRequest, cancellationSignal);
    }

    public static void requestFont(@NonNull Context context, @NonNull FontRequest fontRequest, @NonNull FontRequestCallback fontRequestCallback, @NonNull Handler handler) {
        a aVar = new a(fontRequestCallback);
        d.a(context.getApplicationContext(), fontRequest, 0, e.a(handler), aVar);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static Typeface requestFont(@NonNull Context context, @NonNull FontRequest fontRequest, int i, boolean z, @IntRange(from = 0) int i2, @NonNull Handler handler, @NonNull FontRequestCallback fontRequestCallback) {
        a aVar = new a(fontRequestCallback, handler);
        if (z) {
            return d.a(context, fontRequest, aVar, i, i2);
        }
        return d.a(context, fontRequest, i, (Executor) null, aVar);
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    @VisibleForTesting
    public static void resetTypefaceCache() {
        d.a();
    }

    /* loaded from: classes.dex */
    public static class FontInfo {
        private final Uri a;
        private final int b;
        private final int c;
        private final boolean d;
        private final int e;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Deprecated
        public FontInfo(@NonNull Uri uri, @IntRange(from = 0) int i, @IntRange(from = 1, to = 1000) int i2, boolean z, int i3) {
            this.a = (Uri) Preconditions.checkNotNull(uri);
            this.b = i;
            this.c = i2;
            this.d = z;
            this.e = i3;
        }

        public static FontInfo a(@NonNull Uri uri, @IntRange(from = 0) int i, @IntRange(from = 1, to = 1000) int i2, boolean z, int i3) {
            return new FontInfo(uri, i, i2, z, i3);
        }

        @NonNull
        public Uri getUri() {
            return this.a;
        }

        @IntRange(from = 0)
        public int getTtcIndex() {
            return this.b;
        }

        @IntRange(from = 1, to = 1000)
        public int getWeight() {
            return this.c;
        }

        public boolean isItalic() {
            return this.d;
        }

        public int getResultCode() {
            return this.e;
        }
    }

    /* loaded from: classes.dex */
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final int a;
        private final FontInfo[] b;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Deprecated
        public FontFamilyResult(int i, @Nullable FontInfo[] fontInfoArr) {
            this.a = i;
            this.b = fontInfoArr;
        }

        public int getStatusCode() {
            return this.a;
        }

        public FontInfo[] getFonts() {
            return this.b;
        }

        public static FontFamilyResult a(int i, @Nullable FontInfo[] fontInfoArr) {
            return new FontFamilyResult(i, fontInfoArr);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static Typeface getFontSync(Context context, FontRequest fontRequest, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean z, int i, int i2) {
        return requestFont(context, fontRequest, i2, z, i, ResourcesCompat.FontCallback.getHandler(handler), new TypefaceCompat.ResourcesCallbackAdapter(fontCallback));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static void resetCache() {
        d.a();
    }

    @RequiresApi(19)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fontInfoArr, CancellationSignal cancellationSignal) {
        return TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fontInfoArr, cancellationSignal);
    }

    @VisibleForTesting
    @Deprecated
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest fontRequest, @Nullable Resources resources) throws PackageManager.NameNotFoundException {
        return c.a(packageManager, fontRequest, resources);
    }
}
