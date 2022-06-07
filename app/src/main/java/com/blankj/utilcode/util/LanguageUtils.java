package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.Utils;
import java.util.Locale;

/* loaded from: classes.dex */
public class LanguageUtils {
    private LanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void applySystemLanguage() {
        applySystemLanguage(false);
    }

    public static void applySystemLanguage(boolean z) {
        a((Locale) null, z);
    }

    public static void applyLanguage(@NonNull Locale locale) {
        if (locale != null) {
            applyLanguage(locale, false);
            return;
        }
        throw new NullPointerException("Argument 'locale' of type Locale (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void applyLanguage(@NonNull Locale locale, boolean z) {
        if (locale != null) {
            a(locale, z);
            return;
        }
        throw new NullPointerException("Argument 'locale' of type Locale (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void a(Locale locale, final boolean z) {
        if (locale == null) {
            b.t().put("KEY_LOCALE", "VALUE_FOLLOW_SYSTEM", true);
        } else {
            b.t().put("KEY_LOCALE", a(locale), true);
        }
        if (locale == null) {
            locale = a(Resources.getSystem().getConfiguration());
        }
        updateAppContextLanguage(locale, new Utils.Consumer<Boolean>() { // from class: com.blankj.utilcode.util.LanguageUtils.1
            /* renamed from: a */
            public void accept(Boolean bool) {
                if (bool.booleanValue()) {
                    LanguageUtils.b(z);
                } else {
                    b.i();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(boolean z) {
        if (z) {
            b.i();
            return;
        }
        for (Activity activity : b.c()) {
            activity.recreate();
        }
    }

    public static boolean isAppliedLanguage() {
        return getAppliedLanguage() != null;
    }

    public static boolean isAppliedLanguage(@NonNull Locale locale) {
        if (locale != null) {
            Locale appliedLanguage = getAppliedLanguage();
            if (appliedLanguage == null) {
                return false;
            }
            return a(locale, appliedLanguage);
        }
        throw new NullPointerException("Argument 'locale' of type Locale (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Locale getAppliedLanguage() {
        String string = b.t().getString("KEY_LOCALE");
        if (TextUtils.isEmpty(string) || "VALUE_FOLLOW_SYSTEM".equals(string)) {
            return null;
        }
        return a(string);
    }

    public static Locale getContextLanguage(Context context) {
        return a(context.getResources().getConfiguration());
    }

    public static Locale getAppContextLanguage() {
        return getContextLanguage(Utils.getApp());
    }

    public static Locale getSystemLanguage() {
        return a(Resources.getSystem().getConfiguration());
    }

    public static void updateAppContextLanguage(@NonNull Locale locale, @Nullable Utils.Consumer<Boolean> consumer) {
        if (locale != null) {
            a(locale, 0, consumer);
            return;
        }
        throw new NullPointerException("Argument 'destLocale' of type Locale (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    static void a(final Locale locale, final int i, final Utils.Consumer<Boolean> consumer) {
        Resources resources = Utils.getApp().getResources();
        Configuration configuration = resources.getConfiguration();
        Locale a = a(configuration);
        a(configuration, locale);
        Utils.getApp().getResources().updateConfiguration(configuration, resources.getDisplayMetrics());
        if (consumer != null) {
            if (a(a, locale)) {
                consumer.accept(true);
            } else if (i < 20) {
                b.a(new Runnable() { // from class: com.blankj.utilcode.util.LanguageUtils.2
                    @Override // java.lang.Runnable
                    public void run() {
                        LanguageUtils.a(locale, i + 1, consumer);
                    }
                }, 16L);
            } else {
                Log.e("LanguageUtils", "appLocal didn't update.");
                consumer.accept(false);
            }
        }
    }

    public static Context attachBaseContext(Context context) {
        Locale a;
        String string = b.t().getString("KEY_LOCALE");
        if (TextUtils.isEmpty(string) || "VALUE_FOLLOW_SYSTEM".equals(string) || (a = a(string)) == null) {
            return context;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        a(configuration, a);
        if (Build.VERSION.SDK_INT >= 17) {
            return context.createConfigurationContext(configuration);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Activity activity) {
        Locale locale;
        String string = b.t().getString("KEY_LOCALE");
        if (!TextUtils.isEmpty(string)) {
            if ("VALUE_FOLLOW_SYSTEM".equals(string)) {
                locale = a(Resources.getSystem().getConfiguration());
            } else {
                locale = a(string);
            }
            if (locale != null) {
                a(activity, locale);
                a(Utils.getApp(), locale);
            }
        }
    }

    private static void a(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        a(configuration, locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private static String a(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return language + "$" + country;
    }

    private static Locale a(String str) {
        Locale b = b(str);
        if (b == null) {
            Log.e("LanguageUtils", "The string of " + str + " is not in the correct format.");
            b.t().remove("KEY_LOCALE");
        }
        return b;
    }

    private static Locale b(String str) {
        if (!c(str)) {
            return null;
        }
        try {
            int indexOf = str.indexOf("$");
            return new Locale(str.substring(0, indexOf), str.substring(indexOf + 1));
        } catch (Exception unused) {
            return null;
        }
    }

    private static boolean c(String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (c == '$') {
                if (i >= 1) {
                    return false;
                }
                i++;
            }
        }
        return i == 1;
    }

    private static boolean a(Locale locale, Locale locale2) {
        return b.a((CharSequence) locale2.getLanguage(), (CharSequence) locale.getLanguage()) && b.a((CharSequence) locale2.getCountry(), (CharSequence) locale.getCountry());
    }

    private static Locale a(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 24) {
            return configuration.getLocales().get(0);
        }
        return configuration.locale;
    }

    private static void a(Configuration configuration, Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
    }
}
