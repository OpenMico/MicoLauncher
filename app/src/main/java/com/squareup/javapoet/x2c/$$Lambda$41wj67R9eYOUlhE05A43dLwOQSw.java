package com.squareup.javapoet.x2c;

import java.lang.reflect.Method;
import java.util.function.Function;

/* compiled from: lambda */
/* renamed from: com.squareup.javapoet.x2c.-$$Lambda$41wj67R9eYOUlhE05A43dLwOQSw  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$41wj67R9eYOUlhE05A43dLwOQSw implements Function {
    public static final /* synthetic */ $$Lambda$41wj67R9eYOUlhE05A43dLwOQSw INSTANCE = new $$Lambda$41wj67R9eYOUlhE05A43dLwOQSw();

    private /* synthetic */ $$Lambda$41wj67R9eYOUlhE05A43dLwOQSw() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Method) obj).getName();
    }
}
