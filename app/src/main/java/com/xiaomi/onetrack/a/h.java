package com.xiaomi.onetrack.a;

/* loaded from: classes4.dex */
class h implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(f fVar, String str) {
        this.b = fVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        e eVar;
        try {
            eVar = this.b.b;
            eVar.getWritableDatabase().delete(e.b, "app_id=?", new String[]{this.a});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
