package com.xiaomi.push;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class w implements Runnable {
    private Context a;
    private File b;
    private Runnable c;

    private w(Context context, File file) {
        this.a = context;
        this.b = file;
    }

    public /* synthetic */ w(Context context, File file, iu iuVar) {
        this(context, file);
    }

    public static void a(Context context, File file, Runnable runnable) {
        new iu(context, file, runnable).run();
    }

    protected abstract void a(Context context);

    @Override // java.lang.Runnable
    public final void run() {
        v vVar = null;
        try {
            try {
                if (this.b == null) {
                    this.b = new File(this.a.getFilesDir(), "default_locker");
                }
                vVar = v.a(this.a, this.b);
                if (this.c != null) {
                    this.c.run();
                }
                a(this.a);
                if (vVar == null) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (vVar == null) {
                    return;
                }
            }
            vVar.a();
        } catch (Throwable th) {
            if (vVar != null) {
                vVar.a();
            }
            throw th;
        }
    }
}
