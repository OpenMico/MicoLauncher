package com.xiaomi.clientreport.manager;

import android.content.Context;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.push.be;
import java.util.concurrent.ExecutorService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class b implements Runnable {
    final /* synthetic */ a a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        IEventProcessor iEventProcessor;
        ExecutorService executorService;
        context = this.a.e;
        iEventProcessor = this.a.g;
        be beVar = new be(context, iEventProcessor);
        executorService = this.a.b;
        executorService.execute(beVar);
    }
}
