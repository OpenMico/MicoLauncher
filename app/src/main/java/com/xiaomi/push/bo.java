package com.xiaomi.push;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes4.dex */
class bo {
    private StringBuilder a;
    private File b;

    public void a() {
        try {
            FileWriter fileWriter = new FileWriter(this.b, true);
            fileWriter.write(this.a.toString());
            fileWriter.flush();
            fileWriter.close();
            this.a.delete(0, this.a.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void a(bn bnVar) {
        StringBuilder sb = this.a;
        sb.append(bnVar.a + "\t");
        StringBuilder sb2 = this.a;
        sb2.append(bnVar.b + "\t" + bnVar.c);
        this.a.append("\r\n");
    }
}
