package com.xiaomi.phonenum.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;

/* loaded from: classes4.dex */
public class PhoneNumDBHelper extends SQLiteOpenHelper {
    private static final String b = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY, %s TEXT not null unique, %s TEXT not null, %s TEXT not null, %s TEXT not null, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)", "phone_number", "_id", ai.aa, "number", "number_hash", "update_time", "token", "copywriter", "operator_link", "phone_level");
    private static volatile PhoneNumDBHelper d = null;
    private Logger a = LoggerManager.getLogger();
    private Context c;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static synchronized PhoneNumDBHelper getInstance(Context context) {
        PhoneNumDBHelper phoneNumDBHelper;
        synchronized (PhoneNumDBHelper.class) {
            if (d == null) {
                d = new PhoneNumDBHelper(context.getApplicationContext());
            }
            phoneNumDBHelper = d;
        }
        return phoneNumDBHelper;
    }

    PhoneNumDBHelper(Context context) {
        super(context, "phone_num3.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.c = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(b);
    }

    public synchronized void updatePhoneNum(@NonNull PhoneNum phoneNum) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ai.aa, phoneNum.iccid);
        contentValues.put("number", phoneNum.number);
        contentValues.put("number_hash", phoneNum.numberHash);
        contentValues.put("update_time", phoneNum.updateTime);
        contentValues.put("token", phoneNum.token);
        contentValues.put("copywriter", phoneNum.copywriter);
        contentValues.put("operator_link", phoneNum.operatorLink);
        contentValues.put("phone_level", Integer.valueOf(phoneNum.phoneLevel));
        if (0 < getWritableDatabase().replace("phone_number", null, contentValues)) {
            this.a.i("PhoneNumberDBHelper", "1 entry updated in phone_number database");
        } else {
            Logger logger = this.a;
            logger.i("PhoneNumberDBHelper", "updatePhoneNum failed:" + phoneNum);
        }
    }

    public synchronized PhoneNum queryPhoneNum(@NonNull String str, int i) {
        Cursor cursor;
        Throwable th;
        String str2 = null;
        try {
            cursor = getWritableDatabase().query("phone_number", new String[]{"number", "number_hash", "token", "phone_level", "update_time", "copywriter", "operator_link"}, "iccid=\"" + str + "\"", null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        boolean z = false;
                        cursor.moveToPosition(0);
                        String string = cursor.getString(0);
                        String string2 = cursor.getString(1);
                        String string3 = cursor.getString(2);
                        int i2 = cursor.getInt(3);
                        String string4 = cursor.getString(4);
                        String string5 = cursor.isNull(5) ? null : cursor.getString(5);
                        if (!cursor.isNull(6)) {
                            str2 = cursor.getString(6);
                        }
                        this.a.i("PhoneNumberDBHelper", "phoneNum loaded from db");
                        PhoneNum.Builder operatorLink = new PhoneNum.Builder().subId(i).iccid(str).number(string).numberHash(string2).updateTime(string4).token(string3).copywriter(string5).operatorLink(str2);
                        if (i2 >= PhoneLevel.SMS_VERIFY.value) {
                            z = true;
                        }
                        PhoneNum build = operatorLink.isVerified(z).phoneLevel(i2).build();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return build;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }

    public synchronized boolean deletePhoneNum(@NonNull String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase.delete("phone_number", "iccid=\"" + str + "\"", null) > 0) {
            this.a.i("PhoneNumberDBHelper", "1 entry deletePhoneNum from phone_number database");
            return true;
        }
        Logger logger = this.a;
        logger.i("PhoneNumberDBHelper", "deletePhoneNum failed:" + str);
        return false;
    }
}
