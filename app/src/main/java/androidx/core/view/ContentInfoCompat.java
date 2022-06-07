package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    @NonNull
    final ClipData a;
    final int b;
    final int c;
    @Nullable
    final Uri d;
    @Nullable
    final Bundle e;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Source {
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String a(int i) {
        switch (i) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            default:
                return String.valueOf(i);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String b(int i) {
        return (i & 1) != 0 ? "FLAG_CONVERT_TO_PLAIN_TEXT" : String.valueOf(i);
    }

    ContentInfoCompat(Builder builder) {
        this.a = (ClipData) Preconditions.checkNotNull(builder.a);
        this.b = Preconditions.checkArgumentInRange(builder.b, 0, 3, "source");
        this.c = Preconditions.checkFlagsArgument(builder.c, 1);
        this.d = builder.d;
        this.e = builder.e;
    }

    @NonNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("ContentInfoCompat{clip=");
        sb.append(this.a.getDescription());
        sb.append(", source=");
        sb.append(a(this.b));
        sb.append(", flags=");
        sb.append(b(this.c));
        if (this.d == null) {
            str = "";
        } else {
            str = ", hasLinkUri(" + this.d.toString().length() + ")";
        }
        sb.append(str);
        sb.append(this.e == null ? "" : ", hasExtras");
        sb.append("}");
        return sb.toString();
    }

    @NonNull
    public ClipData getClip() {
        return this.a;
    }

    public int getSource() {
        return this.b;
    }

    public int getFlags() {
        return this.c;
    }

    @Nullable
    public Uri getLinkUri() {
        return this.d;
    }

    @Nullable
    public Bundle getExtras() {
        return this.e;
    }

    @NonNull
    public Pair<ContentInfoCompat, ContentInfoCompat> partition(@NonNull Predicate<ClipData.Item> predicate) {
        ContentInfoCompat contentInfoCompat = null;
        if (this.a.getItemCount() == 1) {
            boolean test = predicate.test(this.a.getItemAt(0));
            ContentInfoCompat contentInfoCompat2 = test ? this : null;
            if (!test) {
                contentInfoCompat = this;
            }
            return Pair.create(contentInfoCompat2, contentInfoCompat);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.a.getItemCount(); i++) {
            ClipData.Item itemAt = this.a.getItemAt(i);
            if (predicate.test(itemAt)) {
                arrayList.add(itemAt);
            } else {
                arrayList2.add(itemAt);
            }
        }
        if (arrayList.isEmpty()) {
            return Pair.create(null, this);
        }
        if (arrayList2.isEmpty()) {
            return Pair.create(this, null);
        }
        return Pair.create(new Builder(this).setClip(a(this.a.getDescription(), arrayList)).build(), new Builder(this).setClip(a(this.a.getDescription(), arrayList2)).build());
    }

    private static ClipData a(ClipDescription clipDescription, List<ClipData.Item> list) {
        ClipData clipData = new ClipData(new ClipDescription(clipDescription), list.get(0));
        for (int i = 1; i < list.size(); i++) {
            clipData.addItem(list.get(i));
        }
        return clipData;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        @NonNull
        ClipData a;
        int b;
        int c;
        @Nullable
        Uri d;
        @Nullable
        Bundle e;

        public Builder(@NonNull ContentInfoCompat contentInfoCompat) {
            this.a = contentInfoCompat.a;
            this.b = contentInfoCompat.b;
            this.c = contentInfoCompat.c;
            this.d = contentInfoCompat.d;
            this.e = contentInfoCompat.e;
        }

        public Builder(@NonNull ClipData clipData, int i) {
            this.a = clipData;
            this.b = i;
        }

        @NonNull
        public Builder setClip(@NonNull ClipData clipData) {
            this.a = clipData;
            return this;
        }

        @NonNull
        public Builder setSource(int i) {
            this.b = i;
            return this;
        }

        @NonNull
        public Builder setFlags(int i) {
            this.c = i;
            return this;
        }

        @NonNull
        public Builder setLinkUri(@Nullable Uri uri) {
            this.d = uri;
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle bundle) {
            this.e = bundle;
            return this;
        }

        @NonNull
        public ContentInfoCompat build() {
            return new ContentInfoCompat(this);
        }
    }
}
