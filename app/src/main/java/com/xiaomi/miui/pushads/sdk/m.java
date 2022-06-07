package com.xiaomi.miui.pushads.sdk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes4.dex */
class m {
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

    public void a(String str, long j, int i) {
        StringBuilder sb = this.a;
        sb.append(str + "\t" + j + "\t" + i);
        this.a.append("\r\n");
    }
}
