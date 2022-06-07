package com.milink.base.utils;

import android.util.Log;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public final class Sugar {

    /* loaded from: classes2.dex */
    public interface Func0<R> {
        R apply() throws Exception;
    }

    /* loaded from: classes2.dex */
    public interface FuncV {
        void apply() throws Exception;
    }

    /* loaded from: classes2.dex */
    public interface FuncV1<P> {
        void apply(P p) throws Exception;
    }

    private Sugar() {
    }

    public static void eat(FuncV funcV) {
        try {
            funcV.apply();
        } catch (Exception e) {
            Log.e("Sugar", "eat exception", e);
        }
    }

    @Nullable
    public static <R> R eat(Func0<R> func0) {
        return (R) eat(func0, (Object) null);
    }

    public static <R> R eat(Func0<R> func0, R r) {
        try {
            return func0.apply();
        } catch (Exception e) {
            Log.e("Sugar", "eat exception", e);
            return r;
        }
    }

    public static <P> void eat(P p, FuncV1<P> funcV1) {
        try {
            funcV1.apply(p);
        } catch (Exception e) {
            Log.e("Sugar", "eat exception", e);
        }
    }
}
