package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes4.dex */
public class bj implements fz {
    public static boolean a;
    private fn c;
    private SimpleDateFormat b = new SimpleDateFormat("hh:mm:ss aaa");
    private a d = null;
    private a e = null;
    private fq f = null;
    private final String g = "[Slim] ";

    /* loaded from: classes4.dex */
    public class a implements fs, ga {
        String a;

        a(boolean z) {
            bj.this = r1;
            this.a = z ? " RCV " : " Sent ";
        }

        @Override // com.xiaomi.push.fs
        public void a(fg fgVar) {
            String str;
            StringBuilder sb;
            if (bj.a) {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bj.this.b.format(new Date()));
                sb.append(this.a);
                str = fgVar.toString();
            } else {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bj.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" Blob [");
                sb.append(fgVar.m907a());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                sb.append(fgVar.a());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                sb.append(fgVar.e());
                str = "]";
            }
            sb.append(str);
            b.c(sb.toString());
        }

        @Override // com.xiaomi.push.fs, com.xiaomi.push.ga
        /* renamed from: a */
        public void mo782a(ge geVar) {
            String str;
            StringBuilder sb;
            if (bj.a) {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bj.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" PKT ");
                str = geVar.m939a();
            } else {
                sb = new StringBuilder();
                sb.append("[Slim] ");
                sb.append(bj.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" PKT [");
                sb.append(geVar.k());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                sb.append(geVar.j());
                str = "]";
            }
            sb.append(str);
            b.c(sb.toString());
        }

        @Override // com.xiaomi.push.fs, com.xiaomi.push.ga
        /* renamed from: a */
        public boolean mo782a(ge geVar) {
            return true;
        }
    }

    static {
        boolean z = true;
        if (u.a() != 1) {
            z = false;
        }
        a = z;
    }

    public bj(fn fnVar) {
        this.c = null;
        this.c = fnVar;
        a();
    }

    private void a() {
        this.d = new a(true);
        this.e = new a(false);
        fn fnVar = this.c;
        a aVar = this.d;
        fnVar.a(aVar, aVar);
        fn fnVar2 = this.c;
        a aVar2 = this.e;
        fnVar2.b(aVar2, aVar2);
        this.f = new an(this);
    }
}
