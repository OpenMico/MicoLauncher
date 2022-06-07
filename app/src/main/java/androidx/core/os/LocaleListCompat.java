package androidx.core.os;

import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LocaleListCompat {
    private static final LocaleListCompat a = create(new Locale[0]);
    private b b;

    private LocaleListCompat(b bVar) {
        this.b = bVar;
    }

    @RequiresApi(24)
    @Deprecated
    public static LocaleListCompat wrap(Object obj) {
        return wrap((LocaleList) obj);
    }

    @NonNull
    @RequiresApi(24)
    public static LocaleListCompat wrap(@NonNull LocaleList localeList) {
        return new LocaleListCompat(new c(localeList));
    }

    @Nullable
    public Object unwrap() {
        return this.b.a();
    }

    @NonNull
    public static LocaleListCompat create(@NonNull Locale... localeArr) {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(new LocaleList(localeArr));
        }
        return new LocaleListCompat(new a(localeArr));
    }

    public Locale get(int i) {
        return this.b.a(i);
    }

    public boolean isEmpty() {
        return this.b.b();
    }

    @IntRange(from = 0)
    public int size() {
        return this.b.c();
    }

    @IntRange(from = -1)
    public int indexOf(Locale locale) {
        return this.b.a(locale);
    }

    @NonNull
    public String toLanguageTags() {
        return this.b.d();
    }

    @Nullable
    public Locale getFirstMatch(@NonNull String[] strArr) {
        return this.b.a(strArr);
    }

    @NonNull
    public static LocaleListCompat getEmptyLocaleList() {
        return a;
    }

    @NonNull
    public static LocaleListCompat forLanguageTags(@Nullable String str) {
        Locale locale;
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
        Locale[] localeArr = new Locale[split.length];
        for (int i = 0; i < localeArr.length; i++) {
            if (Build.VERSION.SDK_INT >= 21) {
                locale = Locale.forLanguageTag(split[i]);
            } else {
                locale = a(split[i]);
            }
            localeArr[i] = locale;
        }
        return create(localeArr);
    }

    public static Locale a(String str) {
        if (str.contains(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER, -1);
            if (split.length > 2) {
                return new Locale(split[0], split[1], split[2]);
            }
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
            if (split.length == 1) {
                return new Locale(split[0]);
            }
        } else if (!str.contains("_")) {
            return new Locale(str);
        } else {
            String[] split2 = str.split("_", -1);
            if (split2.length > 2) {
                return new Locale(split2[0], split2[1], split2[2]);
            }
            if (split2.length > 1) {
                return new Locale(split2[0], split2[1]);
            }
            if (split2.length == 1) {
                return new Locale(split2[0]);
            }
        }
        throw new IllegalArgumentException("Can not parse language tag: [" + str + "]");
    }

    @NonNull
    @Size(min = 1)
    public static LocaleListCompat getAdjustedDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getAdjustedDefault()) : create(Locale.getDefault());
    }

    @NonNull
    @Size(min = 1)
    public static LocaleListCompat getDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getDefault()) : create(Locale.getDefault());
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocaleListCompat) && this.b.equals(((LocaleListCompat) obj).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public String toString() {
        return this.b.toString();
    }
}
