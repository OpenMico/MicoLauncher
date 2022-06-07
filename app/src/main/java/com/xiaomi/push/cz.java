package com.xiaomi.push;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class cz {

    /* loaded from: classes4.dex */
    public static class a extends cy {
        public a() {
            super(1);
        }

        @Override // com.xiaomi.push.cy
        public String a(Context context, String str, List<as> list) {
            URL url;
            if (list == null) {
                url = new URL(str);
            } else {
                Uri.Builder buildUpon = Uri.parse(str).buildUpon();
                for (as asVar : list) {
                    buildUpon.appendQueryParameter(asVar.a(), asVar.b());
                }
                url = new URL(buildUpon.toString());
            }
            return at.a(context, url);
        }
    }

    static int a(int i, int i2) {
        return (((i2 + 243) / 1448) * 132) + 1080 + i + i2;
    }

    static int a(int i, int i2, int i3) {
        return (((i2 + 200) / 1448) * 132) + 1011 + i2 + i + i3;
    }

    private static int a(cy cyVar, String str, List<as> list, String str2) {
        if (cyVar.a() == 1) {
            return a(str.length(), a(str2));
        }
        if (cyVar.a() != 2) {
            return -1;
        }
        return a(str.length(), a(list), a(str2));
    }

    static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return 0;
        }
    }

    static int a(List<as> list) {
        int i = 0;
        for (as asVar : list) {
            if (!TextUtils.isEmpty(asVar.a())) {
                i += asVar.a().length();
            }
            if (!TextUtils.isEmpty(asVar.b())) {
                i += asVar.b().length();
            }
        }
        return i * 2;
    }

    public static String a(Context context, String str, List<as> list) {
        return a(context, str, list, new a(), true);
    }

    public static String a(Context context, String str, List<as> list, cy cyVar, boolean z) {
        cr crVar;
        String str2;
        IOException e;
        if (at.b(context)) {
            try {
                ArrayList<String> arrayList = new ArrayList<>();
                if (z) {
                    cr a2 = cv.a().a(str);
                    if (a2 != null) {
                        arrayList = a2.a(str);
                    }
                    crVar = a2;
                } else {
                    crVar = null;
                }
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                }
                Iterator<String> it = arrayList.iterator();
                String str3 = null;
                while (it.hasNext()) {
                    String next = it.next();
                    ArrayList arrayList2 = list != null ? new ArrayList(list) : null;
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                    } catch (IOException e2) {
                        e = e2;
                        str2 = str3;
                    }
                    if (!cyVar.m823a(context, next, (List<as>) arrayList2)) {
                        break;
                    }
                    str2 = cyVar.a(context, next, (List<as>) arrayList2);
                    try {
                    } catch (IOException e3) {
                        e = e3;
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        if (crVar == null) {
                            return str2;
                        }
                        try {
                            crVar.a(next, System.currentTimeMillis() - currentTimeMillis, a(cyVar, next, arrayList2, str2));
                            return str2;
                        } catch (IOException e4) {
                            e = e4;
                        }
                    } else if (crVar != null) {
                        str3 = str2;
                        try {
                            crVar.a(next, System.currentTimeMillis() - currentTimeMillis, a(cyVar, next, arrayList2, str2), null);
                        } catch (IOException e5) {
                            e = e5;
                            str2 = str3;
                        }
                    } else {
                        str3 = str2;
                    }
                    if (crVar != null) {
                        str3 = str2;
                        crVar.a(next, System.currentTimeMillis() - currentTimeMillis, a(cyVar, next, arrayList2, str2), e);
                    } else {
                        str3 = str2;
                    }
                    e.printStackTrace();
                }
                return str3;
            } catch (MalformedURLException e6) {
                e6.printStackTrace();
            }
        }
        return null;
    }
}
