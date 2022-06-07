package com.xiaomi.push;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class fy extends Exception {
    private gh a;

    /* renamed from: a */
    private gi f46a;

    /* renamed from: a */
    private Throwable f47a;

    public fy() {
        this.a = null;
        this.f46a = null;
        this.f47a = null;
    }

    public fy(gh ghVar) {
        this.a = null;
        this.f46a = null;
        this.f47a = null;
        this.a = ghVar;
    }

    public fy(String str) {
        super(str);
        this.a = null;
        this.f46a = null;
        this.f47a = null;
    }

    public fy(String str, Throwable th) {
        super(str);
        this.a = null;
        this.f46a = null;
        this.f47a = null;
        this.f47a = th;
    }

    public fy(Throwable th) {
        this.a = null;
        this.f46a = null;
        this.f47a = null;
        this.f47a = th;
    }

    public Throwable a() {
        return this.f47a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        gh ghVar;
        gi giVar;
        String message = super.getMessage();
        return (message != null || (giVar = this.f46a) == null) ? (message != null || (ghVar = this.a) == null) ? message : ghVar.toString() : giVar.toString();
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.f47a != null) {
            printStream.println("Nested Exception: ");
            this.f47a.printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.f47a != null) {
            printWriter.println("Nested Exception: ");
            this.f47a.printStackTrace(printWriter);
        }
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String message = super.getMessage();
        if (message != null) {
            sb.append(message);
            sb.append(": ");
        }
        gi giVar = this.f46a;
        if (giVar != null) {
            sb.append(giVar);
        }
        gh ghVar = this.a;
        if (ghVar != null) {
            sb.append(ghVar);
        }
        if (this.f47a != null) {
            sb.append("\n  -- caused by: ");
            sb.append(this.f47a);
        }
        return sb.toString();
    }
}
