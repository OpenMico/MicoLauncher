package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class e extends i<Float> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(BaseRealm baseRealm, OsList osList, Class<Float> cls) {
        super(baseRealm, osList, cls);
    }

    @Nullable
    /* renamed from: a */
    public Float b(int i) {
        return (Float) this.b.getValue(i);
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Number)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.Number", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addFloat(((Number) obj).floatValue());
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertFloat(i, ((Number) obj).floatValue());
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setFloat(i, ((Number) obj).floatValue());
    }
}
