package androidx.core.app;

import android.app.RemoteInput;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class RemoteInput {
    public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
    public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    public static final int SOURCE_CHOICE = 1;
    public static final int SOURCE_FREE_FORM_INPUT = 0;
    private final String a;
    private final CharSequence b;
    private final CharSequence[] c;
    private final boolean d;
    private final int e;
    private final Bundle f;
    private final Set<String> g;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface EditChoicesBeforeSending {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Source {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RemoteInput(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int i, Bundle bundle, Set<String> set) {
        this.a = str;
        this.b = charSequence;
        this.c = charSequenceArr;
        this.d = z;
        this.e = i;
        this.f = bundle;
        this.g = set;
        if (getEditChoicesBeforeSending() == 2 && !getAllowFreeFormInput()) {
            throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
        }
    }

    public String getResultKey() {
        return this.a;
    }

    public CharSequence getLabel() {
        return this.b;
    }

    public CharSequence[] getChoices() {
        return this.c;
    }

    public Set<String> getAllowedDataTypes() {
        return this.g;
    }

    public boolean isDataOnly() {
        return !getAllowFreeFormInput() && (getChoices() == null || getChoices().length == 0) && getAllowedDataTypes() != null && !getAllowedDataTypes().isEmpty();
    }

    public boolean getAllowFreeFormInput() {
        return this.d;
    }

    public int getEditChoicesBeforeSending() {
        return this.e;
    }

    public Bundle getExtras() {
        return this.f;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final String a;
        private CharSequence d;
        private CharSequence[] e;
        private final Set<String> b = new HashSet();
        private final Bundle c = new Bundle();
        private boolean f = true;
        private int g = 0;

        public Builder(@NonNull String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new IllegalArgumentException("Result key can't be null");
        }

        @NonNull
        public Builder setLabel(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        @NonNull
        public Builder setChoices(@Nullable CharSequence[] charSequenceArr) {
            this.e = charSequenceArr;
            return this;
        }

        @NonNull
        public Builder setAllowDataType(@NonNull String str, boolean z) {
            if (z) {
                this.b.add(str);
            } else {
                this.b.remove(str);
            }
            return this;
        }

        @NonNull
        public Builder setAllowFreeFormInput(boolean z) {
            this.f = z;
            return this;
        }

        @NonNull
        public Builder setEditChoicesBeforeSending(int i) {
            this.g = i;
            return this;
        }

        @NonNull
        public Builder addExtras(@NonNull Bundle bundle) {
            if (bundle != null) {
                this.c.putAll(bundle);
            }
            return this;
        }

        @NonNull
        public Bundle getExtras() {
            return this.c;
        }

        @NonNull
        public RemoteInput build() {
            return new RemoteInput(this.a, this.d, this.e, this.f, this.g, this.c, this.b);
        }
    }

    public static Map<String, Uri> getDataResultsFromIntent(Intent intent, String str) {
        Intent a;
        String string;
        if (Build.VERSION.SDK_INT >= 26) {
            return android.app.RemoteInput.getDataResultsFromIntent(intent, str);
        }
        if (Build.VERSION.SDK_INT < 16 || (a = a(intent)) == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String str2 : a.getExtras().keySet()) {
            if (str2.startsWith("android.remoteinput.dataTypeResultsData")) {
                String substring = str2.substring(39);
                if (!substring.isEmpty() && (string = a.getBundleExtra(str2).getString(str)) != null && !string.isEmpty()) {
                    hashMap.put(substring, Uri.parse(string));
                }
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        return hashMap;
    }

    public static Bundle getResultsFromIntent(Intent intent) {
        Intent a;
        if (Build.VERSION.SDK_INT >= 20) {
            return android.app.RemoteInput.getResultsFromIntent(intent);
        }
        if (Build.VERSION.SDK_INT < 16 || (a = a(intent)) == null) {
            return null;
        }
        return (Bundle) a.getExtras().getParcelable(EXTRA_RESULTS_DATA);
    }

    public static void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addResultsToIntent(a(remoteInputArr), intent, bundle);
            return;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            Bundle resultsFromIntent = getResultsFromIntent(intent);
            int resultsSource = getResultsSource(intent);
            if (resultsFromIntent != null) {
                resultsFromIntent.putAll(bundle);
                bundle = resultsFromIntent;
            }
            for (RemoteInput remoteInput : remoteInputArr) {
                Map<String, Uri> dataResultsFromIntent = getDataResultsFromIntent(intent, remoteInput.getResultKey());
                android.app.RemoteInput.addResultsToIntent(a(new RemoteInput[]{remoteInput}), intent, bundle);
                if (dataResultsFromIntent != null) {
                    addDataResultToIntent(remoteInput, intent, dataResultsFromIntent);
                }
            }
            setResultsSource(intent, resultsSource);
        } else if (Build.VERSION.SDK_INT >= 16) {
            Intent a = a(intent);
            if (a == null) {
                a = new Intent();
            }
            Bundle bundleExtra = a.getBundleExtra(EXTRA_RESULTS_DATA);
            if (bundleExtra == null) {
                bundleExtra = new Bundle();
            }
            for (RemoteInput remoteInput2 : remoteInputArr) {
                Object obj = bundle.get(remoteInput2.getResultKey());
                if (obj instanceof CharSequence) {
                    bundleExtra.putCharSequence(remoteInput2.getResultKey(), (CharSequence) obj);
                }
            }
            a.putExtra(EXTRA_RESULTS_DATA, bundleExtra);
            intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, a));
        }
    }

    public static void addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map<String, Uri> map) {
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addDataResultToIntent(a(remoteInput), intent, map);
        } else if (Build.VERSION.SDK_INT >= 16) {
            Intent a = a(intent);
            if (a == null) {
                a = new Intent();
            }
            for (Map.Entry<String, Uri> entry : map.entrySet()) {
                String key = entry.getKey();
                Uri value = entry.getValue();
                if (key != null) {
                    Bundle bundleExtra = a.getBundleExtra(a(key));
                    if (bundleExtra == null) {
                        bundleExtra = new Bundle();
                    }
                    bundleExtra.putString(remoteInput.getResultKey(), value.toString());
                    a.putExtra(a(key), bundleExtra);
                }
            }
            intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, a));
        }
    }

    public static void setResultsSource(@NonNull Intent intent, int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            android.app.RemoteInput.setResultsSource(intent, i);
        } else if (Build.VERSION.SDK_INT >= 16) {
            Intent a = a(intent);
            if (a == null) {
                a = new Intent();
            }
            a.putExtra("android.remoteinput.resultsSource", i);
            intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, a));
        }
    }

    public static int getResultsSource(@NonNull Intent intent) {
        Intent a;
        if (Build.VERSION.SDK_INT >= 28) {
            return android.app.RemoteInput.getResultsSource(intent);
        }
        if (Build.VERSION.SDK_INT < 16 || (a = a(intent)) == null) {
            return 0;
        }
        return a.getExtras().getInt("android.remoteinput.resultsSource", 0);
    }

    private static String a(String str) {
        return "android.remoteinput.dataTypeResultsData" + str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(20)
    public static android.app.RemoteInput[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        android.app.RemoteInput[] remoteInputArr2 = new android.app.RemoteInput[remoteInputArr.length];
        for (int i = 0; i < remoteInputArr.length; i++) {
            remoteInputArr2[i] = a(remoteInputArr[i]);
        }
        return remoteInputArr2;
    }

    @RequiresApi(20)
    static android.app.RemoteInput a(RemoteInput remoteInput) {
        Set<String> allowedDataTypes;
        RemoteInput.Builder addExtras = new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras());
        if (Build.VERSION.SDK_INT >= 26 && (allowedDataTypes = remoteInput.getAllowedDataTypes()) != null) {
            for (String str : allowedDataTypes) {
                addExtras.setAllowDataType(str, true);
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            addExtras.setEditChoicesBeforeSending(remoteInput.getEditChoicesBeforeSending());
        }
        return addExtras.build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(20)
    public static RemoteInput a(android.app.RemoteInput remoteInput) {
        Set<String> allowedDataTypes;
        Builder addExtras = new Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras());
        if (Build.VERSION.SDK_INT >= 26 && (allowedDataTypes = remoteInput.getAllowedDataTypes()) != null) {
            for (String str : allowedDataTypes) {
                addExtras.setAllowDataType(str, true);
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            addExtras.setEditChoicesBeforeSending(remoteInput.getEditChoicesBeforeSending());
        }
        return addExtras.build();
    }

    @RequiresApi(16)
    private static Intent a(Intent intent) {
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return null;
        }
        ClipDescription description = clipData.getDescription();
        if (description.hasMimeType("text/vnd.android.intent") && description.getLabel().toString().contentEquals(RESULTS_CLIP_LABEL)) {
            return clipData.getItemAt(0).getIntent();
        }
        return null;
    }
}
