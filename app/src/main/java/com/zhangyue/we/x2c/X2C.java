package com.zhangyue.we.x2c;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes4.dex */
public final class X2C {
    private static final SparseArray<IViewCreator> a = new SparseArray<>();

    private static int a(int i) {
        return i >> 24;
    }

    public static void setContentView(Activity activity, int i) {
        if (activity != null) {
            View view = getView(activity, i);
            if (view != null) {
                activity.setContentView(view);
            } else {
                activity.setContentView(i);
            }
        } else {
            throw new IllegalArgumentException("Activity must not be null");
        }
    }

    public static View inflate(Context context, int i, ViewGroup viewGroup) {
        return inflate(context, i, viewGroup, viewGroup != null);
    }

    public static View inflate(Context context, int i, ViewGroup viewGroup, boolean z) {
        return inflate(LayoutInflater.from(context), i, viewGroup, z);
    }

    public static View inflate(LayoutInflater layoutInflater, int i, ViewGroup viewGroup) {
        return inflate(layoutInflater, i, viewGroup, viewGroup != null);
    }

    public static View inflate(LayoutInflater layoutInflater, int i, ViewGroup viewGroup, boolean z) {
        View view = getView(layoutInflater.getContext(), i);
        if (view == null) {
            return layoutInflater.inflate(i, viewGroup, z);
        }
        if (viewGroup != null && z) {
            viewGroup.addView(view);
        }
        return view;
    }

    public static View getView(Context context, int i) {
        IViewCreator iViewCreator = a.get(i);
        if (iViewCreator == null) {
            try {
                int a2 = a(i);
                String resourceName = context.getResources().getResourceName(i);
                String substring = resourceName.substring(resourceName.lastIndexOf("/") + 1);
                iViewCreator = (IViewCreator) context.getClassLoader().loadClass("com.zhangyue.we.x2c.X2C" + a2 + "_" + substring).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (iViewCreator == null) {
                iViewCreator = new a();
            }
            a.put(i, iViewCreator);
        }
        return iViewCreator.createView(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements IViewCreator {
        @Override // com.zhangyue.we.x2c.IViewCreator
        public View createView(Context context) {
            return null;
        }

        private a() {
        }
    }
}
