package com.zhangyue.we.x2c.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes4.dex */
public @interface Xml {
    String[] layouts();
}
