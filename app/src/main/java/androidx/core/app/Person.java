package androidx.core.app;

import android.app.Person;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.IconCompat;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;

/* loaded from: classes.dex */
public class Person {
    @Nullable
    CharSequence a;
    @Nullable
    IconCompat b;
    @Nullable
    String c;
    @Nullable
    String d;
    boolean e;
    boolean f;

    @NonNull
    public static Person fromBundle(@NonNull Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("icon");
        return new Builder().setName(bundle.getCharSequence("name")).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString(MiSoundBoxCommandExtras.URI)).setKey(bundle.getString("key")).setBot(bundle.getBoolean("isBot")).setImportant(bundle.getBoolean("isImportant")).build();
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromPersistableBundle(@NonNull PersistableBundle persistableBundle) {
        return new Builder().setName(persistableBundle.getString("name")).setUri(persistableBundle.getString(MiSoundBoxCommandExtras.URI)).setKey(persistableBundle.getString("key")).setBot(persistableBundle.getBoolean("isBot")).setImportant(persistableBundle.getBoolean("isImportant")).build();
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromAndroidPerson(@NonNull android.app.Person person) {
        return new Builder().setName(person.getName()).setIcon(person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build();
    }

    Person(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("name", this.a);
        IconCompat iconCompat = this.b;
        bundle.putBundle("icon", iconCompat != null ? iconCompat.toBundle() : null);
        bundle.putString(MiSoundBoxCommandExtras.URI, this.c);
        bundle.putString("key", this.d);
        bundle.putBoolean("isBot", this.e);
        bundle.putBoolean("isImportant", this.f);
        return bundle;
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PersistableBundle toPersistableBundle() {
        PersistableBundle persistableBundle = new PersistableBundle();
        CharSequence charSequence = this.a;
        persistableBundle.putString("name", charSequence != null ? charSequence.toString() : null);
        persistableBundle.putString(MiSoundBoxCommandExtras.URI, this.c);
        persistableBundle.putString("key", this.d);
        persistableBundle.putBoolean("isBot", this.e);
        persistableBundle.putBoolean("isImportant", this.f);
        return persistableBundle;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public android.app.Person toAndroidPerson() {
        return new Person.Builder().setName(getName()).setIcon(getIcon() != null ? getIcon().toIcon() : null).setUri(getUri()).setKey(getKey()).setBot(isBot()).setImportant(isImportant()).build();
    }

    @Nullable
    public CharSequence getName() {
        return this.a;
    }

    @Nullable
    public IconCompat getIcon() {
        return this.b;
    }

    @Nullable
    public String getUri() {
        return this.c;
    }

    @Nullable
    public String getKey() {
        return this.d;
    }

    public boolean isBot() {
        return this.e;
    }

    public boolean isImportant() {
        return this.f;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String resolveToLegacyUri() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        if (this.a == null) {
            return "";
        }
        return "name:" + ((Object) this.a);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        @Nullable
        CharSequence a;
        @Nullable
        IconCompat b;
        @Nullable
        String c;
        @Nullable
        String d;
        boolean e;
        boolean f;

        public Builder() {
        }

        Builder(Person person) {
            this.a = person.a;
            this.b = person.b;
            this.c = person.c;
            this.d = person.d;
            this.e = person.e;
            this.f = person.f;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        @NonNull
        public Builder setIcon(@Nullable IconCompat iconCompat) {
            this.b = iconCompat;
            return this;
        }

        @NonNull
        public Builder setUri(@Nullable String str) {
            this.c = str;
            return this;
        }

        @NonNull
        public Builder setKey(@Nullable String str) {
            this.d = str;
            return this;
        }

        @NonNull
        public Builder setBot(boolean z) {
            this.e = z;
            return this;
        }

        @NonNull
        public Builder setImportant(boolean z) {
            this.f = z;
            return this;
        }

        @NonNull
        public Person build() {
            return new Person(this);
        }
    }
}
