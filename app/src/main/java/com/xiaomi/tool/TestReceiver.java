package com.xiaomi.tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.miot.host.lan.impl.client.MiotClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class TestReceiver extends BroadcastReceiver {
    private static String a = "TestReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            String readFile = readFile("/sdcard/otdata/rpc");
            MiotLogUtil.d(a, readFile);
            MiotClient.getInstance().sendMessageWithoutEncrypt(readFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(str);
        String str2 = a;
        MiotLogUtil.d(str2, "file是否存在" + file.exists());
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read > 0) {
                stringBuffer.append(new String(bArr, 0, read));
            } else {
                fileInputStream.close();
                return stringBuffer.toString();
            }
        }
    }
}
