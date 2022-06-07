package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class a extends i<byte[]> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(BaseRealm baseRealm, OsList osList, Class<byte[]> cls) {
        super(baseRealm, osList, cls);
    }

    @Nullable
    /* renamed from: a */
    public byte[] b(int i) {
        return (byte[]) this.b.getValue(i);
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof byte[])) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "byte[]", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addBinary((byte[]) obj);
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertBinary(i, (byte[]) obj);
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setBinary(i, (byte[]) obj);
    }
}
