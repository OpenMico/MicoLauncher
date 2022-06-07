package androidx.work;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.HashSet;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class ContentUriTriggers {
    private final Set<Trigger> a = new HashSet();

    public void add(@NonNull Uri uri, boolean z) {
        this.a.add(new Trigger(uri, z));
    }

    @NonNull
    public Set<Trigger> getTriggers() {
        return this.a;
    }

    public int size() {
        return this.a.size();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((ContentUriTriggers) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    /* loaded from: classes.dex */
    public static final class Trigger {
        @NonNull
        private final Uri a;
        private final boolean b;

        Trigger(@NonNull Uri uri, boolean z) {
            this.a = uri;
            this.b = z;
        }

        @NonNull
        public Uri getUri() {
            return this.a;
        }

        public boolean shouldTriggerForDescendants() {
            return this.b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Trigger trigger = (Trigger) obj;
            return this.b == trigger.b && this.a.equals(trigger.a);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + (this.b ? 1 : 0);
        }
    }
}
