package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public class TypeParser implements Serializable {
    private static final long serialVersionUID = 1;
    protected final TypeFactory _factory;

    public TypeParser(TypeFactory typeFactory) {
        this._factory = typeFactory;
    }

    public TypeParser withFactory(TypeFactory typeFactory) {
        return typeFactory == this._factory ? this : new TypeParser(typeFactory);
    }

    public JavaType parse(String str) throws IllegalArgumentException {
        a aVar = new a(str.trim());
        JavaType parseType = parseType(aVar);
        if (!aVar.hasMoreTokens()) {
            return parseType;
        }
        throw _problem(aVar, "Unexpected tokens after complete type");
    }

    protected JavaType parseType(a aVar) throws IllegalArgumentException {
        if (aVar.hasMoreTokens()) {
            Class<?> findClass = findClass(aVar.nextToken(), aVar);
            if (aVar.hasMoreTokens()) {
                String nextToken = aVar.nextToken();
                if ("<".equals(nextToken)) {
                    return this._factory._fromClass(null, findClass, TypeBindings.create(findClass, parseTypes(aVar)));
                }
                aVar.a(nextToken);
            }
            return this._factory._fromClass(null, findClass, TypeBindings.emptyBindings());
        }
        throw _problem(aVar, "Unexpected end-of-string");
    }

    protected List<JavaType> parseTypes(a aVar) throws IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        while (aVar.hasMoreTokens()) {
            arrayList.add(parseType(aVar));
            if (!aVar.hasMoreTokens()) {
                break;
            }
            String nextToken = aVar.nextToken();
            if (">".equals(nextToken)) {
                return arrayList;
            }
            if (!Constants.ACCEPT_TIME_SEPARATOR_SP.equals(nextToken)) {
                throw _problem(aVar, "Unexpected token '" + nextToken + "', expected ',' or '>')");
            }
        }
        throw _problem(aVar, "Unexpected end-of-string");
    }

    protected Class<?> findClass(String str, a aVar) {
        try {
            return this._factory.findClass(str);
        } catch (Exception e) {
            ClassUtil.throwIfRTE(e);
            throw _problem(aVar, "Cannot locate class '" + str + "', problem: " + e.getMessage());
        }
    }

    protected IllegalArgumentException _problem(a aVar, String str) {
        return new IllegalArgumentException(String.format("Failed to parse type '%s' (remaining: '%s'): %s", aVar.a(), aVar.b(), str));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class a extends StringTokenizer {
        protected final String a;
        protected int b;
        protected String c;

        public a(String str) {
            super(str, "<,>", true);
            this.a = str;
        }

        @Override // java.util.StringTokenizer
        public boolean hasMoreTokens() {
            return this.c != null || super.hasMoreTokens();
        }

        @Override // java.util.StringTokenizer
        public String nextToken() {
            String str = this.c;
            if (str != null) {
                this.c = null;
                return str;
            }
            String nextToken = super.nextToken();
            this.b += nextToken.length();
            return nextToken.trim();
        }

        public void a(String str) {
            this.c = str;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.a.substring(this.b);
        }
    }
}
