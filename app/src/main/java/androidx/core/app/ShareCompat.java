package androidx.core.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.IntentCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
    public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";

    private ShareCompat() {
    }

    @Nullable
    public static String getCallingPackage(@NonNull Activity activity) {
        Intent intent = activity.getIntent();
        String callingPackage = activity.getCallingPackage();
        return (callingPackage != null || intent == null) ? callingPackage : a(intent);
    }

    @Nullable
    static String a(@NonNull Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_CALLING_PACKAGE);
        return stringExtra == null ? intent.getStringExtra(EXTRA_CALLING_PACKAGE_INTEROP) : stringExtra;
    }

    @Nullable
    public static ComponentName getCallingActivity(@NonNull Activity activity) {
        Intent intent = activity.getIntent();
        ComponentName callingActivity = activity.getCallingActivity();
        return callingActivity == null ? b(intent) : callingActivity;
    }

    @Nullable
    static ComponentName b(@NonNull Intent intent) {
        ComponentName componentName = (ComponentName) intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        return componentName == null ? (ComponentName) intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY_INTEROP) : componentName;
    }

    @Deprecated
    public static void configureMenuItem(@NonNull MenuItem menuItem, @NonNull IntentBuilder intentBuilder) {
        ShareActionProvider shareActionProvider;
        ActionProvider actionProvider = menuItem.getActionProvider();
        if (!(actionProvider instanceof ShareActionProvider)) {
            shareActionProvider = new ShareActionProvider(intentBuilder.a());
        } else {
            shareActionProvider = (ShareActionProvider) actionProvider;
        }
        shareActionProvider.setShareHistoryFileName(".sharecompat_" + intentBuilder.a().getClass().getName());
        shareActionProvider.setShareIntent(intentBuilder.getIntent());
        menuItem.setActionProvider(shareActionProvider);
        if (Build.VERSION.SDK_INT < 16 && !menuItem.hasSubMenu()) {
            menuItem.setIntent(intentBuilder.createChooserIntent());
        }
    }

    @Deprecated
    public static void configureMenuItem(@NonNull Menu menu, @IdRes int i, @NonNull IntentBuilder intentBuilder) {
        MenuItem findItem = menu.findItem(i);
        if (findItem != null) {
            configureMenuItem(findItem, intentBuilder);
            return;
        }
        throw new IllegalArgumentException("Could not find menu item with id " + i + " in the supplied menu");
    }

    /* loaded from: classes.dex */
    public static class IntentBuilder {
        @NonNull
        private final Context a;
        @NonNull
        private final Intent b = new Intent().setAction("android.intent.action.SEND");
        @Nullable
        private CharSequence c;
        @Nullable
        private ArrayList<String> d;
        @Nullable
        private ArrayList<String> e;
        @Nullable
        private ArrayList<String> f;
        @Nullable
        private ArrayList<Uri> g;

        @NonNull
        @Deprecated
        public static IntentBuilder from(@NonNull Activity activity) {
            return new IntentBuilder(activity);
        }

        public IntentBuilder(@NonNull Context context) {
            Activity activity;
            this.a = (Context) Preconditions.checkNotNull(context);
            this.b.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, context.getPackageName());
            this.b.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE_INTEROP, context.getPackageName());
            this.b.addFlags(524288);
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    activity = null;
                    break;
                } else if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                } else {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (activity != null) {
                ComponentName componentName = activity.getComponentName();
                this.b.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, componentName);
                this.b.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY_INTEROP, componentName);
            }
        }

        @NonNull
        public Intent getIntent() {
            ArrayList<String> arrayList = this.d;
            if (arrayList != null) {
                a("android.intent.extra.EMAIL", arrayList);
                this.d = null;
            }
            ArrayList<String> arrayList2 = this.e;
            if (arrayList2 != null) {
                a("android.intent.extra.CC", arrayList2);
                this.e = null;
            }
            ArrayList<String> arrayList3 = this.f;
            if (arrayList3 != null) {
                a("android.intent.extra.BCC", arrayList3);
                this.f = null;
            }
            ArrayList<Uri> arrayList4 = this.g;
            boolean z = true;
            if (arrayList4 == null || arrayList4.size() <= 1) {
                z = false;
            }
            if (!z) {
                this.b.setAction("android.intent.action.SEND");
                ArrayList<Uri> arrayList5 = this.g;
                if (arrayList5 == null || arrayList5.isEmpty()) {
                    this.b.removeExtra("android.intent.extra.STREAM");
                    if (Build.VERSION.SDK_INT >= 16) {
                        a.a(this.b);
                    }
                } else {
                    this.b.putExtra("android.intent.extra.STREAM", this.g.get(0));
                    if (Build.VERSION.SDK_INT >= 16) {
                        a.a(this.b, this.g);
                    }
                }
            } else {
                this.b.setAction("android.intent.action.SEND_MULTIPLE");
                this.b.putParcelableArrayListExtra("android.intent.extra.STREAM", this.g);
                if (Build.VERSION.SDK_INT >= 16) {
                    a.a(this.b, this.g);
                }
            }
            return this.b;
        }

        @NonNull
        Context a() {
            return this.a;
        }

        private void a(String str, ArrayList<String> arrayList) {
            String[] stringArrayExtra = this.b.getStringArrayExtra(str);
            int length = stringArrayExtra != null ? stringArrayExtra.length : 0;
            String[] strArr = new String[arrayList.size() + length];
            arrayList.toArray(strArr);
            if (stringArrayExtra != null) {
                System.arraycopy(stringArrayExtra, 0, strArr, arrayList.size(), length);
            }
            this.b.putExtra(str, strArr);
        }

        private void a(@Nullable String str, @NonNull String[] strArr) {
            Intent intent = getIntent();
            String[] stringArrayExtra = intent.getStringArrayExtra(str);
            int length = stringArrayExtra != null ? stringArrayExtra.length : 0;
            String[] strArr2 = new String[strArr.length + length];
            if (stringArrayExtra != null) {
                System.arraycopy(stringArrayExtra, 0, strArr2, 0, length);
            }
            System.arraycopy(strArr, 0, strArr2, length, strArr.length);
            intent.putExtra(str, strArr2);
        }

        @NonNull
        public Intent createChooserIntent() {
            return Intent.createChooser(getIntent(), this.c);
        }

        public void startChooser() {
            this.a.startActivity(createChooserIntent());
        }

        @NonNull
        public IntentBuilder setChooserTitle(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            return this;
        }

        @NonNull
        public IntentBuilder setChooserTitle(@StringRes int i) {
            return setChooserTitle(this.a.getText(i));
        }

        @NonNull
        public IntentBuilder setType(@Nullable String str) {
            this.b.setType(str);
            return this;
        }

        @NonNull
        public IntentBuilder setText(@Nullable CharSequence charSequence) {
            this.b.putExtra("android.intent.extra.TEXT", charSequence);
            return this;
        }

        @NonNull
        public IntentBuilder setHtmlText(@Nullable String str) {
            this.b.putExtra(IntentCompat.EXTRA_HTML_TEXT, str);
            if (!this.b.hasExtra("android.intent.extra.TEXT")) {
                setText(Html.fromHtml(str));
            }
            return this;
        }

        @NonNull
        public IntentBuilder setStream(@Nullable Uri uri) {
            this.g = null;
            if (uri != null) {
                addStream(uri);
            }
            return this;
        }

        @NonNull
        public IntentBuilder addStream(@NonNull Uri uri) {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            this.g.add(uri);
            return this;
        }

        @NonNull
        public IntentBuilder setEmailTo(@Nullable String[] strArr) {
            if (this.d != null) {
                this.d = null;
            }
            this.b.putExtra("android.intent.extra.EMAIL", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailTo(@NonNull String str) {
            if (this.d == null) {
                this.d = new ArrayList<>();
            }
            this.d.add(str);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailTo(@NonNull String[] strArr) {
            a("android.intent.extra.EMAIL", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder setEmailCc(@Nullable String[] strArr) {
            this.b.putExtra("android.intent.extra.CC", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailCc(@NonNull String str) {
            if (this.e == null) {
                this.e = new ArrayList<>();
            }
            this.e.add(str);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailCc(@NonNull String[] strArr) {
            a("android.intent.extra.CC", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder setEmailBcc(@Nullable String[] strArr) {
            this.b.putExtra("android.intent.extra.BCC", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailBcc(@NonNull String str) {
            if (this.f == null) {
                this.f = new ArrayList<>();
            }
            this.f.add(str);
            return this;
        }

        @NonNull
        public IntentBuilder addEmailBcc(@NonNull String[] strArr) {
            a("android.intent.extra.BCC", strArr);
            return this;
        }

        @NonNull
        public IntentBuilder setSubject(@Nullable String str) {
            this.b.putExtra("android.intent.extra.SUBJECT", str);
            return this;
        }
    }

    /* loaded from: classes.dex */
    public static class IntentReader {
        @NonNull
        private final Context a;
        @NonNull
        private final Intent b;
        @Nullable
        private final String c;
        @Nullable
        private final ComponentName d;
        @Nullable
        private ArrayList<Uri> e;

        @NonNull
        @Deprecated
        public static IntentReader from(@NonNull Activity activity) {
            return new IntentReader(activity);
        }

        public IntentReader(@NonNull Activity activity) {
            this((Context) Preconditions.checkNotNull(activity), activity.getIntent());
        }

        public IntentReader(@NonNull Context context, @NonNull Intent intent) {
            this.a = (Context) Preconditions.checkNotNull(context);
            this.b = (Intent) Preconditions.checkNotNull(intent);
            this.c = ShareCompat.a(intent);
            this.d = ShareCompat.b(intent);
        }

        public boolean isShareIntent() {
            String action = this.b.getAction();
            return "android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action);
        }

        public boolean isSingleShare() {
            return "android.intent.action.SEND".equals(this.b.getAction());
        }

        public boolean isMultipleShare() {
            return "android.intent.action.SEND_MULTIPLE".equals(this.b.getAction());
        }

        @Nullable
        public String getType() {
            return this.b.getType();
        }

        @Nullable
        public CharSequence getText() {
            return this.b.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        @Nullable
        public String getHtmlText() {
            String stringExtra = this.b.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
            if (stringExtra != null) {
                return stringExtra;
            }
            CharSequence text = getText();
            if (text instanceof Spanned) {
                return Html.toHtml((Spanned) text);
            }
            if (text == null) {
                return stringExtra;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                return Html.escapeHtml(text);
            }
            StringBuilder sb = new StringBuilder();
            a(sb, text, 0, text.length());
            return sb.toString();
        }

        private static void a(StringBuilder sb, CharSequence charSequence, int i, int i2) {
            while (i < i2) {
                char charAt = charSequence.charAt(i);
                if (charAt == '<') {
                    sb.append("&lt;");
                } else if (charAt == '>') {
                    sb.append("&gt;");
                } else if (charAt == '&') {
                    sb.append("&amp;");
                } else if (charAt > '~' || charAt < ' ') {
                    sb.append("&#");
                    sb.append((int) charAt);
                    sb.append(";");
                } else if (charAt == ' ') {
                    while (true) {
                        int i3 = i + 1;
                        if (i3 >= i2 || charSequence.charAt(i3) != ' ') {
                            break;
                        }
                        sb.append("&nbsp;");
                        i = i3;
                    }
                    sb.append(' ');
                } else {
                    sb.append(charAt);
                }
                i++;
            }
        }

        @Nullable
        public Uri getStream() {
            return (Uri) this.b.getParcelableExtra("android.intent.extra.STREAM");
        }

        @Nullable
        public Uri getStream(int i) {
            if (this.e == null && isMultipleShare()) {
                this.e = this.b.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            ArrayList<Uri> arrayList = this.e;
            if (arrayList != null) {
                return arrayList.get(i);
            }
            if (i == 0) {
                return (Uri) this.b.getParcelableExtra("android.intent.extra.STREAM");
            }
            throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + i);
        }

        public int getStreamCount() {
            if (this.e == null && isMultipleShare()) {
                this.e = this.b.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            ArrayList<Uri> arrayList = this.e;
            if (arrayList != null) {
                return arrayList.size();
            }
            return this.b.hasExtra("android.intent.extra.STREAM") ? 1 : 0;
        }

        @Nullable
        public String[] getEmailTo() {
            return this.b.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        @Nullable
        public String[] getEmailCc() {
            return this.b.getStringArrayExtra("android.intent.extra.CC");
        }

        @Nullable
        public String[] getEmailBcc() {
            return this.b.getStringArrayExtra("android.intent.extra.BCC");
        }

        @Nullable
        public String getSubject() {
            return this.b.getStringExtra("android.intent.extra.SUBJECT");
        }

        @Nullable
        public String getCallingPackage() {
            return this.c;
        }

        @Nullable
        public ComponentName getCallingActivity() {
            return this.d;
        }

        @Nullable
        public Drawable getCallingActivityIcon() {
            if (this.d == null) {
                return null;
            }
            try {
                return this.a.getPackageManager().getActivityIcon(this.d);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("IntentReader", "Could not retrieve icon for calling activity", e);
                return null;
            }
        }

        @Nullable
        public Drawable getCallingApplicationIcon() {
            if (this.c == null) {
                return null;
            }
            try {
                return this.a.getPackageManager().getApplicationIcon(this.c);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("IntentReader", "Could not retrieve icon for calling application", e);
                return null;
            }
        }

        @Nullable
        public CharSequence getCallingApplicationLabel() {
            if (this.c == null) {
                return null;
            }
            PackageManager packageManager = this.a.getPackageManager();
            try {
                return packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.c, 0));
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("IntentReader", "Could not retrieve label for calling application", e);
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class a {
        static void a(@NonNull Intent intent, @NonNull ArrayList<Uri> arrayList) {
            ClipData clipData = new ClipData(null, new String[]{intent.getType()}, new ClipData.Item(intent.getCharSequenceExtra("android.intent.extra.TEXT"), intent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT), null, arrayList.get(0)));
            int size = arrayList.size();
            for (int i = 1; i < size; i++) {
                clipData.addItem(new ClipData.Item(arrayList.get(i)));
            }
            intent.setClipData(clipData);
            intent.addFlags(1);
        }

        static void a(@NonNull Intent intent) {
            intent.setClipData(null);
            intent.setFlags(intent.getFlags() & (-2));
        }
    }
}
