package com.xiaomi.mico.base.utils;

import android.util.SparseArray;
import android.view.View;

/* loaded from: classes3.dex */
public class ViewHolder {
    public static <T extends View> T get(View view, int i) {
        SparseArray sparseArray = (SparseArray) view.getTag();
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            view.setTag(sparseArray);
        }
        T t = (T) ((View) sparseArray.get(i));
        if (t != null) {
            return t;
        }
        T t2 = (T) view.findViewById(i);
        sparseArray.put(i, t2);
        return t2;
    }
}
