package com.xiaomi.ai.android.track;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.track.b;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.api.Network;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.transport.a;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class c extends b {
    private int e;
    private Context f;
    private AivsConfig g;
    private String h;
    private String i;
    private OkHttpClient k;
    private int l;
    private boolean n;
    private volatile boolean m = false;
    private final ArrayNode j = APIUtils.getObjectMapper().createArrayNode();

    public c(Context context, String str, AivsConfig aivsConfig, b.c cVar) {
        super(aivsConfig.getInt(AivsConfig.GeneralTrack.CACHE_PERIOD_CHECK_INTERVAL, 10), aivsConfig.getInt(AivsConfig.GeneralTrack.DISK_PERIOD_CHECK_INTERVAL, 300), true, cVar);
        this.n = false;
        this.f = context;
        this.h = str;
        this.g = aivsConfig;
        this.e = this.g.getInt(AivsConfig.GeneralTrack.MAX_TRACK_DATA_SIZE);
        if (TextUtils.isEmpty(this.h)) {
            Logger.d("GeneralTrackStrategy", "TrackHelper:authorization is empty");
        }
        this.i = new com.xiaomi.ai.core.c(this.g).i();
        Logger.b("GeneralTrackStrategy", "mTrackUrl:" + this.i);
        this.k = new OkHttpClient.Builder().addInterceptor(new a()).connectTimeout((long) this.g.getInt(AivsConfig.Connection.CONNECT_TIMEOUT), TimeUnit.SECONDS).build();
        this.n = aivsConfig.getBoolean(AivsConfig.LimitedDiskCache.ENABLE);
        k();
    }

    private synchronized ArrayNode a(Context context, String str) {
        ArrayNode arrayNode = null;
        if (context == null) {
            Logger.d("GeneralTrackStrategy", "readLocal error : empty context");
            return null;
        } else if (f.a(str)) {
            Logger.d("GeneralTrackStrategy", "readLocal error : empty key");
            return null;
        } else {
            String c = c(str);
            if (!f.a(c)) {
                try {
                    arrayNode = APIUtils.getObjectMapper().createArrayNode();
                    ArrayNode arrayNode2 = (ArrayNode) APIUtils.getObjectMapper().readTree(c);
                    if (arrayNode2 != null && arrayNode2.size() > 0) {
                        Iterator<JsonNode> it = arrayNode2.iterator();
                        while (it.hasNext()) {
                            arrayNode.addAll((ArrayNode) APIUtils.getObjectMapper().readTree(it.next().asText()));
                        }
                    }
                    Logger.a("GeneralTrackStrategy", "readLocal  key:" + str + " ,size = " + arrayNode.size());
                } catch (IOException e) {
                    Logger.d("GeneralTrackStrategy", Log.getStackTraceString(e));
                }
            } else {
                Logger.b("GeneralTrackStrategy", "readLocal  key:" + str + " :null");
            }
            d(str);
            return arrayNode;
        }
    }

    private void a(ArrayNode arrayNode) {
        synchronized (this.j) {
            this.j.addAll(arrayNode);
        }
    }

    private void a(String str, String str2) {
        f.a().a(this.f, "aivs_track", str, str2, this.n);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0059 A[Catch: all -> 0x00de, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0008, B:9:0x0013, B:12:0x001c, B:15:0x0024, B:17:0x002a, B:20:0x0033, B:22:0x003e, B:24:0x004a, B:27:0x0059, B:28:0x0061, B:30:0x0067, B:32:0x006c, B:34:0x0072, B:35:0x0075, B:37:0x00a3, B:40:0x00bb), top: B:48:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067 A[Catch: all -> 0x00de, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0008, B:9:0x0013, B:12:0x001c, B:15:0x0024, B:17:0x002a, B:20:0x0033, B:22:0x003e, B:24:0x004a, B:27:0x0059, B:28:0x0061, B:30:0x0067, B:32:0x006c, B:34:0x0072, B:35:0x0075, B:37:0x00a3, B:40:0x00bb), top: B:48:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a3 A[Catch: all -> 0x00de, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0008, B:9:0x0013, B:12:0x001c, B:15:0x0024, B:17:0x002a, B:20:0x0033, B:22:0x003e, B:24:0x004a, B:27:0x0059, B:28:0x0061, B:30:0x0067, B:32:0x006c, B:34:0x0072, B:35:0x0075, B:37:0x00a3, B:40:0x00bb), top: B:48:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bb A[Catch: all -> 0x00de, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0008, B:9:0x0013, B:12:0x001c, B:15:0x0024, B:17:0x002a, B:20:0x0033, B:22:0x003e, B:24:0x004a, B:27:0x0059, B:28:0x0061, B:30:0x0067, B:32:0x006c, B:34:0x0072, B:35:0x0075, B:37:0x00a3, B:40:0x00bb), top: B:48:0x0001, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean a(android.content.Context r5, java.lang.String r6, java.lang.String r7, com.fasterxml.jackson.databind.node.ArrayNode r8) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = com.xiaomi.ai.b.f.a(r6)     // Catch: all -> 0x00de
            r1 = 0
            if (r0 == 0) goto L_0x0011
            java.lang.String r5 = "GeneralTrackStrategy"
            java.lang.String r6 = "saveTrackData :empty key"
            com.xiaomi.ai.log.Logger.c(r5, r6)     // Catch: all -> 0x00de
            monitor-exit(r4)
            return r1
        L_0x0011:
            if (r5 != 0) goto L_0x001c
            java.lang.String r5 = "GeneralTrackStrategy"
            java.lang.String r6 = "saveTrackData :empty mContext"
            com.xiaomi.ai.log.Logger.c(r5, r6)     // Catch: all -> 0x00de
            monitor-exit(r4)
            return r1
        L_0x001c:
            boolean r5 = com.xiaomi.ai.b.f.a(r7)     // Catch: all -> 0x00de
            if (r5 == 0) goto L_0x0033
            if (r8 == 0) goto L_0x002a
            int r5 = r8.size()     // Catch: all -> 0x00de
            if (r5 != 0) goto L_0x0033
        L_0x002a:
            java.lang.String r5 = "GeneralTrackStrategy"
            java.lang.String r6 = "saveTrackData :empty"
            com.xiaomi.ai.log.Logger.c(r5, r6)     // Catch: all -> 0x00de
            monitor-exit(r4)
            return r1
        L_0x0033:
            java.lang.String r5 = r4.c(r6)     // Catch: all -> 0x00de
            r0 = 0
            boolean r2 = com.xiaomi.ai.b.f.a(r5)     // Catch: all -> 0x00de
            if (r2 != 0) goto L_0x0056
            com.fasterxml.jackson.databind.ObjectMapper r2 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()     // Catch: IOException -> 0x0049, all -> 0x00de
            com.fasterxml.jackson.databind.JsonNode r5 = r2.readTree(r5)     // Catch: IOException -> 0x0049, all -> 0x00de
            com.fasterxml.jackson.databind.node.ArrayNode r5 = (com.fasterxml.jackson.databind.node.ArrayNode) r5     // Catch: IOException -> 0x0049, all -> 0x00de
            goto L_0x0057
        L_0x0049:
            r5 = move-exception
            r4.d(r6)     // Catch: all -> 0x00de
            java.lang.String r2 = "GeneralTrackStrategy"
            java.lang.String r5 = android.util.Log.getStackTraceString(r5)     // Catch: all -> 0x00de
            com.xiaomi.ai.log.Logger.d(r2, r5)     // Catch: all -> 0x00de
        L_0x0056:
            r5 = r0
        L_0x0057:
            if (r5 != 0) goto L_0x0061
            com.fasterxml.jackson.databind.ObjectMapper r5 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()     // Catch: all -> 0x00de
            com.fasterxml.jackson.databind.node.ArrayNode r5 = r5.createArrayNode()     // Catch: all -> 0x00de
        L_0x0061:
            boolean r0 = com.xiaomi.ai.b.f.a(r7)     // Catch: all -> 0x00de
            if (r0 != 0) goto L_0x006a
            r5.add(r7)     // Catch: all -> 0x00de
        L_0x006a:
            if (r8 == 0) goto L_0x0075
            int r7 = r8.size()     // Catch: all -> 0x00de
            if (r7 <= 0) goto L_0x0075
            r5.addAll(r8)     // Catch: all -> 0x00de
        L_0x0075:
            com.xiaomi.ai.core.AivsConfig r7 = r4.g     // Catch: all -> 0x00de
            java.lang.String r8 = "general_track.max_local_track_length"
            long r7 = r7.getLong(r8)     // Catch: all -> 0x00de
            java.lang.String r0 = "GeneralTrackStrategy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: all -> 0x00de
            r2.<init>()     // Catch: all -> 0x00de
            java.lang.String r3 = "saveTrackData: "
            r2.append(r3)     // Catch: all -> 0x00de
            r2.append(r6)     // Catch: all -> 0x00de
            java.lang.String r3 = " maxLocalTackLength: "
            r2.append(r3)     // Catch: all -> 0x00de
            r2.append(r7)     // Catch: all -> 0x00de
            java.lang.String r2 = r2.toString()     // Catch: all -> 0x00de
            com.xiaomi.ai.log.Logger.a(r0, r2)     // Catch: all -> 0x00de
            long r2 = com.xiaomi.ai.android.utils.a.a(r5)     // Catch: all -> 0x00de
            int r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x00bb
            java.lang.String r5 = "GeneralTrackStrategy"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: all -> 0x00de
            r7.<init>()     // Catch: all -> 0x00de
            r7.append(r6)     // Catch: all -> 0x00de
            java.lang.String r6 = ",save error: cache full !!! "
            r7.append(r6)     // Catch: all -> 0x00de
            java.lang.String r6 = r7.toString()     // Catch: all -> 0x00de
            com.xiaomi.ai.log.Logger.c(r5, r6)     // Catch: all -> 0x00de
            monitor-exit(r4)
            return r1
        L_0x00bb:
            java.lang.String r7 = r5.toString()     // Catch: all -> 0x00de
            r4.a(r6, r7)     // Catch: all -> 0x00de
            java.lang.String r7 = "GeneralTrackStrategy"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: all -> 0x00de
            r8.<init>()     // Catch: all -> 0x00de
            r8.append(r6)     // Catch: all -> 0x00de
            java.lang.String r6 = ",save: success  array:"
            r8.append(r6)     // Catch: all -> 0x00de
            r8.append(r5)     // Catch: all -> 0x00de
            java.lang.String r5 = r8.toString()     // Catch: all -> 0x00de
            com.xiaomi.ai.log.Logger.a(r7, r5)     // Catch: all -> 0x00de
            r5 = 1
            monitor-exit(r4)
            return r5
        L_0x00de:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.android.track.c.a(android.content.Context, java.lang.String, java.lang.String, com.fasterxml.jackson.databind.node.ArrayNode):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        a(this.f, "track_failed_info", str, null);
        this.m = true;
    }

    private boolean b(JsonNode jsonNode) {
        int i = this.g.getInt(AivsConfig.GeneralTrack.MAX_TRACK_TIMES);
        if (NetworkUtils.b(this.f) != Network.NetworkType.DATA || this.l <= i) {
            final String textValue = jsonNode.textValue();
            if (NetworkUtils.a(this.f)) {
                Logger.a("GeneralTrackStrategy", "postTrackData:" + textValue);
                if (TextUtils.isEmpty(this.h)) {
                    Logger.d("GeneralTrackStrategy", "postTrackData: mAuthorization is empty");
                    b(textValue);
                    return false;
                }
                this.k.newCall(new Request.Builder().url(this.i).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), textValue)).addHeader("Authorization", this.h).build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.track.c.1
                    @Override // okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        Logger.a("GeneralTrackStrategy", "postTrackData: onFailure " + textValue);
                        if (iOException != null) {
                            Logger.d("GeneralTrackStrategy", Log.getStackTraceString(iOException));
                        }
                        c.this.b(textValue);
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call, Response response) {
                        if (response == null) {
                            Logger.c("GeneralTrackStrategy", "postTrackData: failed");
                            return;
                        }
                        if (response.isSuccessful()) {
                            try {
                                Logger.b("GeneralTrackStrategy", "postTrackData: success");
                                if (c.this.m) {
                                    c.this.a();
                                }
                            } catch (Exception e) {
                                Logger.d("GeneralTrackStrategy", Log.getStackTraceString(e));
                            }
                            if (NetworkUtils.b(c.this.f) == Network.NetworkType.DATA) {
                                c.this.l();
                            } else {
                                Logger.a("GeneralTrackStrategy", "postTrackData: not using 4g");
                            }
                        } else {
                            Logger.d("GeneralTrackStrategy", "postTrackData: failed, code=" + response.code() + ", msg:" + response.toString());
                            if (response.code() == 401) {
                                if (c.this.c != null) {
                                    c.this.c.a(new AivsError(401, "authorization fail"));
                                } else {
                                    c.this.b(textValue);
                                }
                            }
                        }
                        try {
                            response.close();
                        } catch (Exception e2) {
                            Logger.d("GeneralTrackStrategy", Log.getStackTraceString(e2));
                        }
                    }
                });
                return true;
            }
            Logger.d("GeneralTrackStrategy", "postTrackData:network is not available");
            b(textValue);
            return true;
        }
        Logger.c("GeneralTrackStrategy", "postTrackData: reach max track time " + i + " in 4g");
        return false;
    }

    private String c(String str) {
        return f.a().a(this.f, "aivs_track", str, this.n);
    }

    private void d(String str) {
        f.a().b(this.f, "aivs_track", str, this.n);
    }

    private int j() {
        int size;
        synchronized (this.j) {
            size = this.j.size();
        }
        return size;
    }

    private synchronized void k() {
        ObjectNode objectNode;
        String format = new SimpleDateFormat("yyyyMMdd", Locale.US).format(Long.valueOf(System.currentTimeMillis()));
        String c = c("track_times");
        Logger.a("GeneralTrackStrategy", "trackRecord:" + c);
        try {
            if (!TextUtils.isEmpty(c) && (objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(c)) != null) {
                JsonNode path = objectNode.path(format);
                if (path == null || !path.isNumber()) {
                    d("track_times");
                } else {
                    this.l = path.asInt();
                    Logger.b("GeneralTrackStrategy", "load track times:" + this.l + " at " + format);
                    return;
                }
            }
        } catch (IOException e) {
            Logger.d("GeneralTrackStrategy", Log.getStackTraceString(e));
        }
        this.l = 0;
        Logger.b("GeneralTrackStrategy", "no track times recorded : at " + format);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void l() {
        String format = new SimpleDateFormat("yyyyMMdd", Locale.US).format(Long.valueOf(System.currentTimeMillis()));
        this.l++;
        ObjectNode createObjectNode = new ObjectMapper().createObjectNode();
        createObjectNode.put(format, this.l);
        a("track_times", createObjectNode.toString());
        Logger.b("GeneralTrackStrategy", "addTrackTimes:" + this.l + " at " + format);
    }

    public void a(JsonNode jsonNode) {
        synchronized (this.j) {
            this.j.add(jsonNode);
        }
    }

    public void a(String str) {
        this.h = str;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean b() {
        return j() < this.e;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean c() {
        Iterator<JsonNode> it = h().iterator();
        boolean z = true;
        while (true) {
            while (it.hasNext()) {
                boolean b = b(it.next());
                if (!z || !b) {
                    z = false;
                }
            }
            return z;
        }
    }

    @Override // com.xiaomi.ai.android.track.b
    protected void d() {
        ArrayNode a = a(this.f, "track_cached_info");
        if (a != null && a.size() > 0) {
            a(a);
        }
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean e() {
        this.m = false;
        ArrayNode a = a(this.f, "track_failed_info");
        if (a == null || a.size() <= 0) {
            return false;
        }
        a(a);
        return true;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean f() {
        boolean z;
        synchronized (this.j) {
            z = this.j.size() == 0;
        }
        return z;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean g() {
        return a(this.f, "track_cached_info", null, h());
    }

    protected ArrayNode h() {
        ArrayNode createArrayNode;
        String arrayNode;
        synchronized (this.j) {
            createArrayNode = APIUtils.getObjectMapper().createArrayNode();
            if (this.j.size() > this.e) {
                ArrayNode createArrayNode2 = APIUtils.getObjectMapper().createArrayNode();
                Iterator<JsonNode> it = this.j.iterator();
                while (it.hasNext()) {
                    createArrayNode2.add(it.next());
                    if (createArrayNode2.size() == this.e) {
                        createArrayNode.add(createArrayNode2.toString());
                        createArrayNode2.removeAll();
                    }
                }
                if (createArrayNode2.size() > 0) {
                    arrayNode = createArrayNode2.toString();
                }
                this.j.removeAll();
            } else {
                arrayNode = this.j.toString();
            }
            createArrayNode.add(arrayNode);
            this.j.removeAll();
        }
        return createArrayNode;
    }

    public synchronized void i() {
        f.a().a(this.f, "aivs_track", this.n);
    }
}
