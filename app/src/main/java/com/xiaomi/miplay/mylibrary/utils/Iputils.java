package com.xiaomi.miplay.mylibrary.utils;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/* loaded from: classes4.dex */
public class Iputils {
    private static final String a = "Iputils";

    public static boolean checkIpPort(String str, int i) {
        Socket socket = new Socket();
        try {
            try {
                socket.connect(new InetSocketAddress(str, i), 2000);
                Logger.i(a, "port effective", new Object[0]);
                try {
                    socket.close();
                } catch (IOException unused) {
                }
                return true;
            } catch (Exception unused2) {
                Logger.i(a, "port invalid", new Object[0]);
                try {
                    socket.close();
                } catch (IOException unused3) {
                }
                return false;
            }
        } catch (Throwable th) {
            try {
                socket.close();
            } catch (IOException unused4) {
            }
            throw th;
        }
    }

    public static boolean checkIp(String str) {
        try {
            InetAddress.getByName(str).isReachable(3000);
            Logger.i(a, "port effective", new Object[0]);
            return true;
        } catch (IOException unused) {
            Logger.i(a, "port invalid", new Object[0]);
            return false;
        }
    }
}
