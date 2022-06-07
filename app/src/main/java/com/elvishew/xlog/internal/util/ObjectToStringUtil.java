package com.elvishew.xlog.internal.util;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/util/ObjectToStringUtil.class */
public class ObjectToStringUtil {
    public static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        StringBuilder b = new StringBuilder(128);
        b.append("Bundle[{");
        bundleToShortString(bundle, b);
        b.append("}]");
        return b.toString();
    }

    public static String intentToString(Intent intent) {
        if (intent == null) {
            return "null";
        }
        StringBuilder b = new StringBuilder(128);
        b.append("Intent { ");
        intentToShortString(intent, b);
        b.append(" }");
        return b.toString();
    }

    private static void bundleToShortString(Bundle bundle, StringBuilder b) {
        boolean first = true;
        for (String key : bundle.keySet()) {
            if (!first) {
                b.append(", ");
            }
            b.append(key).append('=');
            Object value = bundle.get(key);
            if (value instanceof int[]) {
                b.append(Arrays.toString((int[]) value));
            } else if (value instanceof byte[]) {
                b.append(Arrays.toString((byte[]) value));
            } else if (value instanceof boolean[]) {
                b.append(Arrays.toString((boolean[]) value));
            } else if (value instanceof short[]) {
                b.append(Arrays.toString((short[]) value));
            } else if (value instanceof long[]) {
                b.append(Arrays.toString((long[]) value));
            } else if (value instanceof float[]) {
                b.append(Arrays.toString((float[]) value));
            } else if (value instanceof double[]) {
                b.append(Arrays.toString((double[]) value));
            } else if (value instanceof String[]) {
                b.append(Arrays.toString((String[]) value));
            } else if (value instanceof CharSequence[]) {
                b.append(Arrays.toString((CharSequence[]) value));
            } else if (value instanceof Parcelable[]) {
                b.append(Arrays.toString((Parcelable[]) value));
            } else if (value instanceof Bundle) {
                b.append(bundleToString((Bundle) value));
            } else {
                b.append(value);
            }
            first = false;
        }
    }

    private static void intentToShortString(Intent intent, StringBuilder b) {
        Intent mSelector;
        boolean first = true;
        String mAction = intent.getAction();
        if (mAction != null) {
            b.append("act=").append(mAction);
            first = false;
        }
        Set<String> mCategories = intent.getCategories();
        if (mCategories != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("cat=[");
            boolean firstCategory = true;
            for (String c : mCategories) {
                if (!firstCategory) {
                    b.append(StringUtil.COMMA);
                }
                b.append(c);
                firstCategory = false;
            }
            b.append("]");
        }
        Uri mData = intent.getData();
        if (mData != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("dat=");
            if (Build.VERSION.SDK_INT >= 14) {
                b.append(uriToSafeString(mData));
            } else {
                String scheme = mData.getScheme();
                if (scheme == null) {
                    b.append(mData);
                } else if (scheme.equalsIgnoreCase("tel")) {
                    b.append("tel:xxx-xxx-xxxx");
                } else if (scheme.equalsIgnoreCase("smsto")) {
                    b.append("smsto:xxx-xxx-xxxx");
                } else {
                    b.append(mData);
                }
            }
        }
        String mType = intent.getType();
        if (mType != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("typ=").append(mType);
        }
        int mFlags = intent.getFlags();
        if (mFlags != 0) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("flg=0x").append(Integer.toHexString(mFlags));
        }
        String mPackage = intent.getPackage();
        if (mPackage != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("pkg=").append(mPackage);
        }
        ComponentName mComponent = intent.getComponent();
        if (mComponent != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("cmp=").append(mComponent.flattenToShortString());
        }
        Rect mSourceBounds = intent.getSourceBounds();
        if (mSourceBounds != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("bnds=").append(mSourceBounds.toShortString());
        }
        if (Build.VERSION.SDK_INT >= 16 && intent.getClipData() != null) {
            if (!first) {
                b.append(' ');
            }
            first = false;
            b.append("(has clip)");
        }
        Bundle mExtras = intent.getExtras();
        if (mExtras != null) {
            if (!first) {
                b.append(' ');
            }
            b.append("extras={");
            bundleToShortString(mExtras, b);
            b.append('}');
        }
        if (Build.VERSION.SDK_INT >= 15 && (mSelector = intent.getSelector()) != null) {
            b.append(" sel=");
            intentToShortString(mSelector, b);
            b.append("}");
        }
    }

    private static String uriToSafeString(Uri uri) {
        if (Build.VERSION.SDK_INT >= 14) {
            try {
                Method toSafeString = Uri.class.getDeclaredMethod("toSafeString", new Class[0]);
                toSafeString.setAccessible(true);
                return (String) toSafeString.invoke(uri, new Object[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return uri.toString();
    }
}
