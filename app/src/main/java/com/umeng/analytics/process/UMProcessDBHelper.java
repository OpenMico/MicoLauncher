package com.umeng.analytics.process;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.umeng.analytics.process.DBFileTraversalUtil;
import com.umeng.analytics.process.a;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.utils.FileLockCallback;
import com.umeng.commonsdk.utils.FileLockUtil;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UMProcessDBHelper {
    private static UMProcessDBHelper mInstance;
    private Context mContext;
    private FileLockUtil mFileLock = new FileLockUtil();
    private InsertEventCallback ekvCallBack = new InsertEventCallback();

    private int getDataSource() {
        return 0;
    }

    /* loaded from: classes2.dex */
    public class InsertEventCallback implements FileLockCallback {
        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) {
            return false;
        }

        private InsertEventCallback() {
            UMProcessDBHelper.this = r1;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            if (str.startsWith(a.c)) {
                str = str.replaceFirst(a.c, "");
            }
            UMProcessDBHelper.this.insertEvents(str.replace(a.d, ""), (JSONArray) obj);
            return true;
        }
    }

    private UMProcessDBHelper() {
    }

    private UMProcessDBHelper(Context context) {
        com.umeng.common.a.a().a(context);
    }

    public static UMProcessDBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UMProcessDBHelper.class) {
                if (mInstance == null) {
                    mInstance = new UMProcessDBHelper(context);
                }
            }
        }
        UMProcessDBHelper uMProcessDBHelper = mInstance;
        uMProcessDBHelper.mContext = context;
        return uMProcessDBHelper;
    }

    public void createDBByProcess(String str) {
        try {
            c.a(this.mContext).a(str);
            c.a(this.mContext).b(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertEventsInSubProcess(String str, JSONArray jSONArray) {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            File file = new File(b.b(this.mContext, str));
            if (file.exists()) {
                this.mFileLock.doFileOperateion(file, this.ekvCallBack, jSONArray);
            } else {
                insertEvents(str, jSONArray);
            }
        }
    }

    public void insertEvents(String str, JSONArray jSONArray) {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            insertEvents_(str, datasAdapter(str, jSONArray));
        }
    }

    public void processToMain(String str) {
        if (dbIsExists(str)) {
            List<a> readEventByProcess = readEventByProcess(str);
            if (!readEventByProcess.isEmpty() && insertEvents_(a.h, readEventByProcess)) {
                deleteEventDatas(str, null, readEventByProcess);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0181 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject readMainEvents(long r17, java.util.List<java.lang.Integer> r19) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readMainEvents(long, java.util.List):org.json.JSONObject");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004a, code lost:
        if (r0 != null) goto L_0x004c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004f, code lost:
        com.umeng.analytics.process.c.a(r6.mContext).b(com.umeng.analytics.process.a.h);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005a, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0035, code lost:
        if (r0 != null) goto L_0x004c;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void deleteMainProcessEventDatasByIds(java.util.List<java.lang.Integer> r7) {
        /*
            r6 = this;
            r0 = 0
            android.content.Context r1 = r6.mContext     // Catch: Exception -> 0x004a, all -> 0x0038
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)     // Catch: Exception -> 0x004a, all -> 0x0038
            java.lang.String r2 = "_main_"
            android.database.sqlite.SQLiteDatabase r0 = r1.a(r2)     // Catch: Exception -> 0x004a, all -> 0x0038
            r0.beginTransaction()     // Catch: Exception -> 0x004a, all -> 0x0038
            java.util.Iterator r7 = r7.iterator()     // Catch: Exception -> 0x004a, all -> 0x0038
        L_0x0014:
            boolean r1 = r7.hasNext()     // Catch: Exception -> 0x004a, all -> 0x0038
            if (r1 == 0) goto L_0x0032
            java.lang.Object r1 = r7.next()     // Catch: Exception -> 0x004a, all -> 0x0038
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch: Exception -> 0x004a, all -> 0x0038
            java.lang.String r2 = "__et_p"
            java.lang.String r3 = "id=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch: Exception -> 0x004a, all -> 0x0038
            r5 = 0
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: Exception -> 0x004a, all -> 0x0038
            r4[r5] = r1     // Catch: Exception -> 0x004a, all -> 0x0038
            r0.delete(r2, r3, r4)     // Catch: Exception -> 0x004a, all -> 0x0038
            goto L_0x0014
        L_0x0032:
            r0.setTransactionSuccessful()     // Catch: Exception -> 0x004a, all -> 0x0038
            if (r0 == 0) goto L_0x004f
            goto L_0x004c
        L_0x0038:
            r7 = move-exception
            if (r0 == 0) goto L_0x003e
            r0.endTransaction()
        L_0x003e:
            android.content.Context r0 = r6.mContext
            com.umeng.analytics.process.c r0 = com.umeng.analytics.process.c.a(r0)
            java.lang.String r1 = "_main_"
            r0.b(r1)
            throw r7
        L_0x004a:
            if (r0 == 0) goto L_0x004f
        L_0x004c:
            r0.endTransaction()
        L_0x004f:
            android.content.Context r7 = r6.mContext
            com.umeng.analytics.process.c r7 = com.umeng.analytics.process.c.a(r7)
            java.lang.String r0 = "_main_"
            r7.b(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.deleteMainProcessEventDatasByIds(java.util.List):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
        if (r0 != null) goto L_0x0061;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
        if (r0 == null) goto L_0x0064;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0061, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0064, code lost:
        com.umeng.analytics.process.c.a(r4.mContext).b(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006d, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void deleteEventDatas(java.lang.String r5, java.lang.String r6, java.util.List<com.umeng.analytics.process.UMProcessDBHelper.a> r7) {
        /*
            r4 = this;
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 == 0) goto L_0x0007
            return
        L_0x0007:
            r6 = 0
            android.content.Context r0 = r4.mContext     // Catch: Exception -> 0x005e, all -> 0x004c
            com.umeng.analytics.process.c r0 = com.umeng.analytics.process.c.a(r0)     // Catch: Exception -> 0x005e, all -> 0x004c
            android.database.sqlite.SQLiteDatabase r0 = r0.a(r5)     // Catch: Exception -> 0x005e, all -> 0x004c
            r0.beginTransaction()     // Catch: Exception -> 0x005f, all -> 0x004a
            int r1 = r7.size()     // Catch: Exception -> 0x005f, all -> 0x004a
            if (r7 == 0) goto L_0x003f
            if (r1 <= 0) goto L_0x003f
            r6 = 0
        L_0x001e:
            if (r6 >= r1) goto L_0x0044
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: Exception -> 0x005f, all -> 0x004a
            r2.<init>()     // Catch: Exception -> 0x005f, all -> 0x004a
            java.lang.String r3 = "delete from __et_p where rowid="
            r2.append(r3)     // Catch: Exception -> 0x005f, all -> 0x004a
            java.lang.Object r3 = r7.get(r6)     // Catch: Exception -> 0x005f, all -> 0x004a
            com.umeng.analytics.process.UMProcessDBHelper$a r3 = (com.umeng.analytics.process.UMProcessDBHelper.a) r3     // Catch: Exception -> 0x005f, all -> 0x004a
            int r3 = r3.a     // Catch: Exception -> 0x005f, all -> 0x004a
            r2.append(r3)     // Catch: Exception -> 0x005f, all -> 0x004a
            java.lang.String r2 = r2.toString()     // Catch: Exception -> 0x005f, all -> 0x004a
            r0.execSQL(r2)     // Catch: Exception -> 0x005f, all -> 0x004a
            int r6 = r6 + 1
            goto L_0x001e
        L_0x003f:
            java.lang.String r7 = "__et_p"
            r0.delete(r7, r6, r6)     // Catch: Exception -> 0x005f, all -> 0x004a
        L_0x0044:
            r0.setTransactionSuccessful()     // Catch: Exception -> 0x005f, all -> 0x004a
            if (r0 == 0) goto L_0x0064
            goto L_0x0061
        L_0x004a:
            r6 = move-exception
            goto L_0x004f
        L_0x004c:
            r7 = move-exception
            r0 = r6
            r6 = r7
        L_0x004f:
            if (r0 == 0) goto L_0x0054
            r0.endTransaction()
        L_0x0054:
            android.content.Context r7 = r4.mContext
            com.umeng.analytics.process.c r7 = com.umeng.analytics.process.c.a(r7)
            r7.b(r5)
            throw r6
        L_0x005e:
            r0 = r6
        L_0x005f:
            if (r0 == 0) goto L_0x0064
        L_0x0061:
            r0.endTransaction()
        L_0x0064:
            android.content.Context r6 = r4.mContext
            com.umeng.analytics.process.c r6 = com.umeng.analytics.process.c.a(r6)
            r6.b(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.deleteEventDatas(java.lang.String, java.lang.String, java.util.List):void");
    }

    private boolean insertEvents_(String str, List<a> list) {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty()) {
            return true;
        }
        try {
            sQLiteDatabase = c.a(this.mContext).a(str);
            try {
                try {
                    sQLiteDatabase.beginTransaction();
                    for (a aVar : list) {
                        try {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("__i", aVar.b);
                            contentValues.put("__e", aVar.c);
                            contentValues.put("__t", Integer.valueOf(aVar.e));
                            contentValues.put(a.AbstractC0139a.f, aVar.f);
                            contentValues.put("__av", aVar.g);
                            contentValues.put("__vc", aVar.h);
                            contentValues.put("__s", aVar.d);
                            sQLiteDatabase.insert(a.AbstractC0139a.a, null, contentValues);
                        } catch (Exception unused) {
                        }
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Throwable unused2) {
                        }
                    }
                    c.a(this.mContext).b(str);
                    return true;
                } catch (Throwable th2) {
                    th = th2;
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Throwable unused3) {
                        }
                    }
                    c.a(this.mContext).b(str);
                    throw th;
                }
            } catch (Exception unused4) {
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.endTransaction();
                    } catch (Throwable unused5) {
                    }
                }
                c.a(this.mContext).b(str);
                return false;
            }
        } catch (Exception unused6) {
            sQLiteDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = null;
        }
    }

    private List<a> datasAdapter(String str, JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                a aVar = new a();
                aVar.c = jSONObject.optString("id");
                aVar.g = UMUtils.getAppVersionName(this.mContext);
                aVar.h = UMUtils.getAppVersionCode(this.mContext);
                aVar.b = jSONObject.optString("__i");
                aVar.e = jSONObject.optInt("__t");
                aVar.f = str;
                if (jSONObject.has("ds")) {
                    jSONObject.remove("ds");
                }
                jSONObject.put("ds", getDataSource());
                jSONObject.remove("__i");
                jSONObject.remove("__t");
                aVar.d = com.umeng.common.a.a().a(jSONObject.toString());
                jSONObject.remove("ds");
                arrayList.add(aVar);
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0097 A[Catch: Exception -> 0x009a, TRY_LEAVE, TryCatch #5 {Exception -> 0x009a, blocks: (B:35:0x0092, B:37:0x0097), top: B:56:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00af A[Catch: Exception -> 0x00b2, TRY_LEAVE, TryCatch #9 {Exception -> 0x00b2, blocks: (B:42:0x00aa, B:44:0x00af), top: B:59:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject readVersionInfoFromColumId(java.lang.Integer r7) {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "select *  from __et_p where rowid="
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r0 = 0
            android.content.Context r1 = r6.mContext     // Catch: Exception -> 0x0088, all -> 0x0083
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)     // Catch: Exception -> 0x0088, all -> 0x0083
            java.lang.String r2 = "_main_"
            android.database.sqlite.SQLiteDatabase r1 = r1.a(r2)     // Catch: Exception -> 0x0088, all -> 0x0083
            r1.beginTransaction()     // Catch: Exception -> 0x0080, all -> 0x007b
            android.database.Cursor r7 = r1.rawQuery(r7, r0)     // Catch: Exception -> 0x0080, all -> 0x007b
            if (r7 == 0) goto L_0x0065
            boolean r2 = r7.moveToNext()     // Catch: Exception -> 0x0060, all -> 0x00a7
            if (r2 == 0) goto L_0x0065
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: Exception -> 0x0060, all -> 0x00a7
            r2.<init>()     // Catch: Exception -> 0x0060, all -> 0x00a7
            java.lang.String r0 = "__av"
            int r0 = r7.getColumnIndex(r0)     // Catch: Exception -> 0x005e, all -> 0x00a7
            java.lang.String r0 = r7.getString(r0)     // Catch: Exception -> 0x005e, all -> 0x00a7
            java.lang.String r3 = "__vc"
            int r3 = r7.getColumnIndex(r3)     // Catch: Exception -> 0x005e, all -> 0x00a7
            java.lang.String r3 = r7.getString(r3)     // Catch: Exception -> 0x005e, all -> 0x00a7
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch: Exception -> 0x005e, all -> 0x00a7
            if (r4 != 0) goto L_0x0051
            java.lang.String r4 = "__av"
            r2.put(r4, r0)     // Catch: Exception -> 0x005e, all -> 0x00a7
        L_0x0051:
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch: Exception -> 0x005e, all -> 0x00a7
            if (r0 != 0) goto L_0x005c
            java.lang.String r0 = "__vc"
            r2.put(r0, r3)     // Catch: Exception -> 0x005e, all -> 0x00a7
        L_0x005c:
            r0 = r2
            goto L_0x0065
        L_0x005e:
            r0 = move-exception
            goto L_0x008d
        L_0x0060:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x008d
        L_0x0065:
            if (r7 == 0) goto L_0x006a
            r7.close()     // Catch: Exception -> 0x006f
        L_0x006a:
            if (r1 == 0) goto L_0x006f
            r1.endTransaction()     // Catch: Exception -> 0x006f
        L_0x006f:
            android.content.Context r7 = r6.mContext
            com.umeng.analytics.process.c r7 = com.umeng.analytics.process.c.a(r7)
            java.lang.String r1 = "_main_"
            r7.b(r1)
            goto L_0x00a6
        L_0x007b:
            r7 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x00a8
        L_0x0080:
            r7 = move-exception
            r2 = r0
            goto L_0x008b
        L_0x0083:
            r7 = move-exception
            r1 = r0
            r0 = r7
            r7 = r1
            goto L_0x00a8
        L_0x0088:
            r7 = move-exception
            r1 = r0
            r2 = r1
        L_0x008b:
            r0 = r7
            r7 = r2
        L_0x008d:
            r0.printStackTrace()     // Catch: all -> 0x00a7
            if (r7 == 0) goto L_0x0095
            r7.close()     // Catch: Exception -> 0x009a
        L_0x0095:
            if (r1 == 0) goto L_0x009a
            r1.endTransaction()     // Catch: Exception -> 0x009a
        L_0x009a:
            android.content.Context r7 = r6.mContext
            com.umeng.analytics.process.c r7 = com.umeng.analytics.process.c.a(r7)
            java.lang.String r0 = "_main_"
            r7.b(r0)
            r0 = r2
        L_0x00a6:
            return r0
        L_0x00a7:
            r0 = move-exception
        L_0x00a8:
            if (r7 == 0) goto L_0x00ad
            r7.close()     // Catch: Exception -> 0x00b2
        L_0x00ad:
            if (r1 == 0) goto L_0x00b2
            r1.endTransaction()     // Catch: Exception -> 0x00b2
        L_0x00b2:
            android.content.Context r7 = r6.mContext
            com.umeng.analytics.process.c r7 = com.umeng.analytics.process.c.a(r7)
            java.lang.String r1 = "_main_"
            r7.b(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readVersionInfoFromColumId(java.lang.Integer):org.json.JSONObject");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x008c, code lost:
        if (r3 != null) goto L_0x00a9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a7, code lost:
        if (r3 != null) goto L_0x00a9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a9, code lost:
        r3.endTransaction();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<com.umeng.analytics.process.UMProcessDBHelper.a> readEventByProcess(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "select *  from __et_p"
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            android.content.Context r3 = r7.mContext     // Catch: Exception -> 0x009b, all -> 0x0097
            com.umeng.analytics.process.c r3 = com.umeng.analytics.process.c.a(r3)     // Catch: Exception -> 0x009b, all -> 0x0097
            android.database.sqlite.SQLiteDatabase r3 = r3.a(r8)     // Catch: Exception -> 0x009b, all -> 0x0097
            r3.beginTransaction()     // Catch: Exception -> 0x0092, all -> 0x008f
            android.database.Cursor r0 = r3.rawQuery(r0, r2)     // Catch: Exception -> 0x0092, all -> 0x008f
            if (r0 == 0) goto L_0x0087
        L_0x001b:
            boolean r4 = r0.moveToNext()     // Catch: Exception -> 0x0085, all -> 0x00b6
            if (r4 == 0) goto L_0x0087
            com.umeng.analytics.process.UMProcessDBHelper$a r4 = new com.umeng.analytics.process.UMProcessDBHelper$a     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.<init>()     // Catch: Exception -> 0x0085, all -> 0x00b6
            r5 = 0
            int r5 = r0.getInt(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.a = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__i"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.b = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__e"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.c = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__s"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.d = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__t"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            int r5 = r0.getInt(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.e = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__pn"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.f = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__av"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.g = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = "__vc"
            int r5 = r0.getColumnIndex(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            java.lang.String r5 = r0.getString(r5)     // Catch: Exception -> 0x0085, all -> 0x00b6
            r4.h = r5     // Catch: Exception -> 0x0085, all -> 0x00b6
            r1.add(r4)     // Catch: Exception -> 0x0085, all -> 0x00b6
            goto L_0x001b
        L_0x0085:
            r2 = move-exception
            goto L_0x009f
        L_0x0087:
            if (r0 == 0) goto L_0x008c
            r0.close()     // Catch: Exception -> 0x00ac
        L_0x008c:
            if (r3 == 0) goto L_0x00ac
            goto L_0x00a9
        L_0x008f:
            r1 = move-exception
            r0 = r2
            goto L_0x00b7
        L_0x0092:
            r0 = move-exception
            r6 = r2
            r2 = r0
            r0 = r6
            goto L_0x009f
        L_0x0097:
            r1 = move-exception
            r0 = r2
            r3 = r0
            goto L_0x00b7
        L_0x009b:
            r0 = move-exception
            r3 = r2
            r2 = r0
            r0 = r3
        L_0x009f:
            r2.printStackTrace()     // Catch: all -> 0x00b6
            if (r0 == 0) goto L_0x00a7
            r0.close()     // Catch: Exception -> 0x00ac
        L_0x00a7:
            if (r3 == 0) goto L_0x00ac
        L_0x00a9:
            r3.endTransaction()     // Catch: Exception -> 0x00ac
        L_0x00ac:
            android.content.Context r0 = r7.mContext
            com.umeng.analytics.process.c r0 = com.umeng.analytics.process.c.a(r0)
            r0.b(r8)
            return r1
        L_0x00b6:
            r1 = move-exception
        L_0x00b7:
            if (r0 == 0) goto L_0x00bc
            r0.close()     // Catch: Exception -> 0x00c1
        L_0x00bc:
            if (r3 == 0) goto L_0x00c1
            r3.endTransaction()     // Catch: Exception -> 0x00c1
        L_0x00c1:
            android.content.Context r0 = r7.mContext
            com.umeng.analytics.process.c r0 = com.umeng.analytics.process.c.a(r0)
            r0.b(r8)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readEventByProcess(java.lang.String):java.util.List");
    }

    private boolean dbIsExists(String str) {
        try {
            return new File(b.b(this.mContext, str)).exists();
        } catch (Throwable unused) {
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public class a implements Serializable {
        int a;
        String b;
        String c;
        String d;
        int e;
        String f;
        String g;
        String h;

        private a() {
            UMProcessDBHelper.this = r1;
        }
    }

    /* loaded from: classes2.dex */
    public class ProcessToMainCallback implements FileLockCallback {
        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) {
            return false;
        }

        private ProcessToMainCallback() {
            UMProcessDBHelper.this = r1;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            if (str.startsWith(a.c)) {
                str = str.replaceFirst(a.c, "");
            }
            UMProcessDBHelper.this.processToMain(str.replace(a.d, ""));
            return true;
        }
    }

    public void processDBToMain() {
        try {
            DBFileTraversalUtil.traverseDBFiles(b.a(this.mContext), new ProcessToMainCallback(), new DBFileTraversalUtil.a() { // from class: com.umeng.analytics.process.UMProcessDBHelper.1
                @Override // com.umeng.analytics.process.DBFileTraversalUtil.a
                public void a() {
                    if (AnalyticsConstants.SUB_PROCESS_EVENT) {
                        UMWorkDispatch.sendEvent(UMProcessDBHelper.this.mContext, UMProcessDBDatasSender.UM_PROCESS_CONSTRUCTMESSAGE, UMProcessDBDatasSender.getInstance(UMProcessDBHelper.this.mContext), null);
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    private boolean processIsService(Context context) {
        return context.getPackageManager().getServiceInfo(new ComponentName(context, this.mContext.getClass()), 0) != null;
    }
}
