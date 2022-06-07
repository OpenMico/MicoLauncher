package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.push.aj;
import com.xiaomi.push.service.ag;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ed extends aj.a {
    private Context a;
    private SharedPreferences b;
    private ag c;

    public ed(Context context) {
        this.a = context;
        this.b = context.getSharedPreferences("mipush_extra", 0);
        this.c = ag.a(context);
    }

    private List<hp> a(File file) {
        RandomAccessFile randomAccessFile;
        FileLock fileLock;
        Throwable th;
        dl a = dm.a().m824a();
        String a2 = a == null ? "" : a.a();
        FileInputStream fileInputStream = null;
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        byte[] bArr = new byte[4];
        synchronized (dr.a) {
            try {
                File file2 = new File(this.a.getExternalFilesDir(null), "push_cdata.lock");
                z.m1176a(file2);
                randomAccessFile = new RandomAccessFile(file2, "rw");
                try {
                    fileLock = randomAccessFile.getChannel().lock();
                    try {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        while (fileInputStream2.read(bArr) == 4) {
                            try {
                                int a3 = ad.a(bArr);
                                byte[] bArr2 = new byte[a3];
                                if (fileInputStream2.read(bArr2) != a3) {
                                    break;
                                }
                                byte[] a4 = dq.a(a2, bArr2);
                                if (!(a4 == null || a4.length == 0)) {
                                    hp hpVar = new hp();
                                    ir.a(hpVar, a4);
                                    arrayList.add(hpVar);
                                }
                            } catch (Exception unused) {
                                fileInputStream = fileInputStream2;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused2) {
                                    }
                                }
                                z.a(fileInputStream);
                                z.a(randomAccessFile);
                                return arrayList;
                            } catch (Throwable th2) {
                                th = th2;
                                fileInputStream = fileInputStream2;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused3) {
                                    }
                                }
                                z.a(fileInputStream);
                                z.a(randomAccessFile);
                                throw th;
                            }
                        }
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException unused4) {
                            }
                        }
                        z.a(fileInputStream2);
                    } catch (Exception unused5) {
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception unused6) {
                    fileLock = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileLock = null;
                }
            } catch (Exception unused7) {
                fileLock = null;
                randomAccessFile = null;
            } catch (Throwable th5) {
                th = th5;
                fileLock = null;
                randomAccessFile = null;
            }
            z.a(randomAccessFile);
        }
        return arrayList;
    }

    private boolean b() {
        if (at.d(this.a)) {
            return false;
        }
        if (!at.f(this.a) || d()) {
            return (at.g(this.a) && !c()) || at.h(this.a);
        }
        return true;
    }

    private boolean c() {
        if (!this.c.a(hm.Upload3GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.b.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max((int) CacheConstants.DAY, this.c.a(hm.Upload3GFrequency.a(), 432000)));
    }

    private boolean d() {
        if (!this.c.a(hm.Upload4GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.b.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max((int) CacheConstants.DAY, this.c.a(hm.Upload4GFrequency.a(), 259200)));
    }

    private void e() {
        SharedPreferences.Editor edit = this.b.edit();
        edit.putLong("last_upload_data_timestamp", System.currentTimeMillis() / 1000);
        edit.commit();
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 1;
    }

    @Override // java.lang.Runnable
    public void run() {
        File file = new File(this.a.getExternalFilesDir(null), "push_cdata.data");
        if (!at.c(this.a)) {
            if (file.length() > 1863680) {
                file.delete();
            }
        } else if (!b() && file.exists()) {
            List<hp> a = a(file);
            if (!ae.a(a)) {
                int size = a.size();
                if (size > 4000) {
                    a = a.subList(size - 4000, size);
                }
                ia iaVar = new ia();
                iaVar.a(a);
                byte[] a2 = z.a(ir.a(iaVar));
                ig igVar = new ig(DataModel.CIRCULATEFAIL_NO_SUPPORT, false);
                igVar.c(hr.DataCollection.f67a);
                igVar.a(a2);
                dl a3 = dm.a().m824a();
                if (a3 != null) {
                    a3.a(igVar, hh.Notification, null);
                }
                e();
            }
            file.delete();
            this.b.edit().remove("ltapn").commit();
        }
    }
}
