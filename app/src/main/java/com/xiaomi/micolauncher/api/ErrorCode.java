package com.xiaomi.micolauncher.api;

import android.content.Context;
import androidx.annotation.StringRes;
import com.xiaomi.micolauncher.R;
import java.util.HashMap;

/* loaded from: classes3.dex */
public enum ErrorCode {
    SUCCESS(0, 0),
    UNKNOWN_ERROR(1, 0),
    HTTP_ERROR(2, R.string.error_http_error),
    CONNECT_ERROR(3, 0),
    INVALIDATE_DATA_FORMAT(4, 0),
    CONNECT_TIMEOUT(5, 0),
    PARSE_ERROR(6, 0),
    APP_SONG_LRC_URL_EMPTY(10000, 0);
    
    private static HashMap<Integer, ErrorCode> a;
    private final int mCode;
    @StringRes
    private final int mMsgRes;

    ErrorCode(int i, int i2) {
        this.mCode = i;
        this.mMsgRes = i2;
    }

    public int getCode() {
        return this.mCode;
    }

    public static ErrorCode valueOf(int i) {
        if (a == null) {
            a = new HashMap<>();
            ErrorCode[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                a.put(Integer.valueOf(values[i2].getCode()), values[i2]);
            }
        }
        if (a.containsKey(Integer.valueOf(i))) {
            return a.get(Integer.valueOf(i));
        }
        return UNKNOWN_ERROR;
    }

    @StringRes
    public int getMsgRes() {
        return this.mMsgRes;
    }

    public String getMsgString(Context context) {
        int i = this.mMsgRes;
        return i > 0 ? context.getString(i) : context.getString(R.string.error_msg_unknow, Integer.valueOf(getCode()));
    }

    public ApiError toApiError(String str) {
        return new ApiError(getCode(), str);
    }
}
