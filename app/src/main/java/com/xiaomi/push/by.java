package com.xiaomi.push;

import android.os.AsyncTask;

/* loaded from: classes4.dex */
class by extends AsyncTask<String, Integer, Integer> {
    cl a;
    String b;
    String c;
    bn d;

    public by(cl clVar, String str, String str2, bn bnVar) {
        this.b = str;
        this.c = str2;
        this.a = clVar;
        this.d = bnVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Integer doInBackground(String... strArr) {
        return Integer.valueOf(bz.a(this.b, this.c, this.d));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(Integer num) {
        super.onPostExecute(num);
        cl clVar = this.a;
        if (clVar != null) {
            clVar.a(num, this.d);
        }
    }

    @Override // android.os.AsyncTask
    protected void onCancelled() {
        super.onCancelled();
        cl clVar = this.a;
        if (clVar != null) {
            clVar.a(1, this.d);
        }
    }
}
