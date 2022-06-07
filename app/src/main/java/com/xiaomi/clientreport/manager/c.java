package com.xiaomi.clientreport.manager;

import android.content.Context;
import com.xiaomi.clientreport.processor.IPerfProcessor;
import com.xiaomi.push.be;
import java.util.concurrent.ExecutorService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class c implements Runnable {
    final /* synthetic */ a a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        IPerfProcessor iPerfProcessor;
        ExecutorService executorService;
        context = this.a.e;
        iPerfProcessor = this.a.h;
        be beVar = new be(context, iPerfProcessor);
        executorService = this.a.b;
        executorService.execute(beVar);
    }
}
