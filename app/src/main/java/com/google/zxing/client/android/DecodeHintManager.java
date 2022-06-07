package com.google.zxing.client.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.DecodeHintType;
import com.xiaomi.mipush.sdk.Constants;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class DecodeHintManager {
    private static final String a = "DecodeHintManager";
    private static final Pattern b = Pattern.compile(Constants.ACCEPT_TIME_SEPARATOR_SP);

    private DecodeHintManager() {
    }

    public static Map<DecodeHintType, Object> parseDecodeHints(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null || extras.isEmpty()) {
            return null;
        }
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        DecodeHintType[] values = DecodeHintType.values();
        for (DecodeHintType decodeHintType : values) {
            if (!(decodeHintType == DecodeHintType.CHARACTER_SET || decodeHintType == DecodeHintType.NEED_RESULT_POINT_CALLBACK || decodeHintType == DecodeHintType.POSSIBLE_FORMATS)) {
                String name = decodeHintType.name();
                if (extras.containsKey(name)) {
                    if (decodeHintType.getValueType().equals(Void.class)) {
                        enumMap.put((EnumMap) decodeHintType, (DecodeHintType) Boolean.TRUE);
                    } else {
                        Object obj = extras.get(name);
                        if (decodeHintType.getValueType().isInstance(obj)) {
                            enumMap.put((EnumMap) decodeHintType, (DecodeHintType) obj);
                        } else {
                            Log.w(a, "Ignoring hint " + decodeHintType + " because it is not assignable from " + obj);
                        }
                    }
                }
            }
        }
        Log.i(a, "Hints from the Intent: " + enumMap);
        return enumMap;
    }
}
