package com.xiaomi.micolauncher.skills.alarm.model;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.L;
import java.util.HashMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;

/* loaded from: classes3.dex */
public class WorkDayAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private final OkHttpClient a = new OkHttpClient();

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        if (!WorkDaySyncService.a.compareAndSet(true, false)) {
            L.base.e("IsNeedSysWorkDay is false when onPreExecuteing!");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Boolean doInBackground(Void... voidArr) {
        L.base.i("WorkDayAsyncTask called");
        try {
            Response<JsonElement> execute = ApiManager.minaService.getWorkDayConfigAuthByUser(WorkDaySyncService.getWorkDayVersion()).execute();
            int code = execute.code();
            if (code == 200) {
                JsonObject asJsonObject = execute.body().getAsJsonObject();
                String asString = asJsonObject.get("version").getAsString();
                JsonArray asJsonArray = asJsonObject.getAsJsonArray("items");
                if (!(asJsonArray == null || asJsonArray.size() == 0)) {
                    HashMap hashMap = new HashMap();
                    int size = asJsonArray.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject asJsonObject2 = asJsonArray.get(i).getAsJsonObject();
                        hashMap.put(asJsonObject2.get("filename").getAsString(), a(asJsonObject2.get("url").getAsString()));
                    }
                    WorkDaySyncService.setWorkDayConfig(hashMap, asString);
                    MicoDate.updateHolidayAndWorkday();
                }
                L.base.i("getWorkDayConfigAuthByUser failed! workDayConfig:", asJsonArray);
                return true;
            } else if (code == 304) {
                WorkDaySyncService.setLastSyncTime();
            } else {
                Logger logger = L.base;
                logger.e("GetWorkDayConfigAuthByUser Failed,response:" + execute);
            }
        } catch (Exception e) {
            L.base.e("GetWorkDayConfigAuthByUser hit an error.", e);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(Boolean bool) {
        super.onPostExecute((WorkDayAsyncTask) bool);
        WorkDaySyncService.a.set(true);
    }

    private String a(@NonNull String str) throws Exception {
        okhttp3.Response execute = this.a.newCall(new Request.Builder().url(str).build()).execute();
        Throwable th = null;
        try {
            String string = execute.body().string();
            if (execute.code() == 200) {
                if (execute != null) {
                    execute.close();
                }
                return string;
            }
            Logger logger = L.base;
            logger.e("DownloadFile Failed,response:" + execute);
            throw new Exception(string);
        } catch (Throwable th2) {
            if (execute != null) {
                if (0 != 0) {
                    try {
                        execute.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    execute.close();
                }
            }
            throw th2;
        }
    }
}
