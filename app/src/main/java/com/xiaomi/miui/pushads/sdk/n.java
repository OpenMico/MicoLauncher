package com.xiaomi.miui.pushads.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.push.cf;
import java.io.File;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class n extends AsyncTask<String, Integer, Integer> {
    private Context a;
    private g b;
    private cf c;
    private String d;
    private int e;
    private SharedPreferences f;
    private String g;

    public n(Context context, SharedPreferences sharedPreferences, String str, int i, String str2, g gVar) {
        this.a = context;
        this.b = gVar;
        this.d = str;
        this.f = sharedPreferences;
        this.g = str2;
    }

    private int a(File file) {
        String str;
        h hVar = (h) this.c;
        String str2 = hVar.b;
        if (str2 == null) {
            return -1;
        }
        int a = d.a(this.a, file, str2, hVar);
        j.a("下载广告 imgUrl: " + str2 + " 结果： " + a);
        if (isCancelled() || a != 0) {
            if (isCancelled()) {
                str = "asynctask 被cancel";
            } else {
                str = "网络类型改变，中断下载: " + f.a(this.a) + StringUtils.SPACE + a;
            }
            j.a(str);
        }
        return a;
    }

    private int a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int a = a(jSONObject);
            k.a("解析参数并检查, 返回结果: " + a(a));
            if (a != 0) {
                return a;
            }
            int c = c(jSONObject);
            if (this.c != null) {
                k.a("广告获取最终结果： " + c + " 类型: " + this.c.a);
            }
            return c;
        } catch (JSONException unused) {
            return -1;
        }
    }

    private int a(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.optString("status").equals(StatConstants.BIND_SUCCESS)) {
            return -1;
        }
        int optInt = jSONObject.optInt("nonsense");
        if (optInt != 0) {
            Log.e("MIUIADSPUSH", "广告无效标志设置: " + optInt);
            j.a("广告无效");
            return -2;
        }
        long optLong = jSONObject.optLong("lastShowTime", 0L);
        l.a("expireTime: " + optLong + " currentTime: " + System.currentTimeMillis());
        if (optLong == 0 || optLong >= System.currentTimeMillis()) {
            return 0;
        }
        j.a("广告已经过期 lastShow: " + optLong + " current: " + System.currentTimeMillis());
        return -4;
    }

    private String a(int i) {
        switch (i) {
            case -5:
                return "消息不匹配";
            case -4:
                return "过期";
            case -3:
                return "到达上限";
            case -2:
                return "广告失效";
            case -1:
                return "未知原因";
            case 0:
                return "成功";
            default:
                return "";
        }
    }

    private boolean a(int i, int i2) {
        synchronized (this.f) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.f.getLong("starttime", 0L);
            if (j == 0) {
                this.f.edit().putLong("starttime", currentTimeMillis).commit();
                return true;
            } else if (currentTimeMillis - j > 86400000) {
                this.f.edit().putLong("starttime", 0L).commit();
                this.f.edit().putInt("notifycount", 0).commit();
                this.f.edit().putInt("bubblecount", 0).commit();
                return true;
            } else {
                if (i2 == 2) {
                    if (this.f.getInt("notifycount", 0) < i) {
                        return true;
                    }
                } else if (i2 == 1 && this.f.getInt("bubblecount", 0) < i * 4) {
                    return true;
                }
                j.b("超过了每天接受广告的上限");
                return false;
            }
        }
    }

    private int b(JSONObject jSONObject) {
        return jSONObject.optInt("showType");
    }

    private cf b(int i) {
        cf cfVar = new cf();
        switch (i) {
            case 1:
                cfVar = new a();
                break;
            case 2:
                cfVar = new h();
                break;
        }
        cfVar.d = this.e;
        cfVar.h = this.d;
        return cfVar;
    }

    private int c(JSONObject jSONObject) {
        boolean z;
        int b = b(jSONObject);
        try {
            Method method = Class.forName("miui.util.NotificationFilterHelper").getMethod("canSendNotifications", Context.class, String.class);
            k.a(this.g);
            z = !((Boolean) method.invoke(null, this.a, this.g)).booleanValue();
        } catch (Exception e) {
            Log.d("NotifyAdsDownloader", "reflect errors!");
            e.printStackTrace();
            z = false;
        }
        k.a("是否禁用了通知栏广告 " + z);
        int optInt = jSONObject.optInt("receiveUpperBound");
        boolean a = optInt > 0 ? true ^ a(optInt, b) : false;
        k.a("是否达到上限 " + a);
        try {
            if (a || (b == 2 && z)) {
                k.a("使用候选广告 ");
                if (jSONObject.optLong("subAdId") <= 0) {
                    k.a("没有候选广告 ");
                    return -5;
                }
                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("subAdInfo"));
                int b2 = b(jSONObject2);
                if (b2 == 2 && z) {
                    return -6;
                }
                int a2 = a(jSONObject2);
                k.a("候选广告解析参数并检查： " + a2);
                if (a2 != 0) {
                    return a2;
                }
                this.c = b(b2);
                this.c.a(jSONObject2);
            } else {
                k.a("使用主广告 ");
                this.c = b(b);
                this.c.a(jSONObject);
            }
            return 0;
        } catch (JSONException unused) {
            return -1;
        }
    }

    /* renamed from: a */
    public Integer doInBackground(String... strArr) {
        int a = a(this.d);
        if (a != 0) {
            j.a("广告解析失败 " + a);
            return Integer.valueOf(a);
        }
        if (this.c.a == 2) {
            a = a(this.a.getDir("comxiaomimiuipushadssdk", 0));
        }
        return Integer.valueOf(a);
    }

    /* renamed from: a */
    public void onPostExecute(Integer num) {
        super.onPostExecute(num);
        if (this.b != null) {
            j.a("下载 post 的结果是: " + num);
            this.b.a(num.intValue(), this.c, this);
        }
    }

    @Override // android.os.AsyncTask
    protected void onCancelled() {
        super.onCancelled();
        Log.d("ADS_DOWNLOAD", "onCancelled");
    }
}
