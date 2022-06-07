package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.b;
import java.util.Date;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class an implements fq {
    final /* synthetic */ bj a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public an(bj bjVar) {
        this.a = bjVar;
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar) {
        fn fnVar2;
        StringBuilder sb = new StringBuilder();
        sb.append("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection reconnected (");
        fnVar2 = this.a.c;
        sb.append(fnVar2.hashCode());
        sb.append(")");
        b.c(sb.toString());
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, int i, Exception exc) {
        fn fnVar2;
        StringBuilder sb = new StringBuilder();
        sb.append("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection closed (");
        fnVar2 = this.a.c;
        sb.append(fnVar2.hashCode());
        sb.append(")");
        b.c(sb.toString());
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, Exception exc) {
        fn fnVar2;
        StringBuilder sb = new StringBuilder();
        sb.append("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Reconnection failed due to an exception (");
        fnVar2 = this.a.c;
        sb.append(fnVar2.hashCode());
        sb.append(")");
        b.c(sb.toString());
        exc.printStackTrace();
    }

    @Override // com.xiaomi.push.fq
    public void b(fn fnVar) {
        fn fnVar2;
        StringBuilder sb = new StringBuilder();
        sb.append("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection started (");
        fnVar2 = this.a.c;
        sb.append(fnVar2.hashCode());
        sb.append(")");
        b.c(sb.toString());
    }
}
