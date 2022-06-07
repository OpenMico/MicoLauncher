package androidx.sqlite.db;

import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class SupportSQLiteQueryBuilder {
    private static final Pattern a = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
    private final String c;
    private String e;
    private Object[] f;
    private boolean b = false;
    private String[] d = null;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;

    public static SupportSQLiteQueryBuilder builder(String str) {
        return new SupportSQLiteQueryBuilder(str);
    }

    private SupportSQLiteQueryBuilder(String str) {
        this.c = str;
    }

    public SupportSQLiteQueryBuilder distinct() {
        this.b = true;
        return this;
    }

    public SupportSQLiteQueryBuilder columns(String[] strArr) {
        this.d = strArr;
        return this;
    }

    public SupportSQLiteQueryBuilder selection(String str, Object[] objArr) {
        this.e = str;
        this.f = objArr;
        return this;
    }

    public SupportSQLiteQueryBuilder groupBy(String str) {
        this.g = str;
        return this;
    }

    public SupportSQLiteQueryBuilder having(String str) {
        this.h = str;
        return this;
    }

    public SupportSQLiteQueryBuilder orderBy(String str) {
        this.i = str;
        return this;
    }

    public SupportSQLiteQueryBuilder limit(String str) {
        if (a(str) || a.matcher(str).matches()) {
            this.j = str;
            return this;
        }
        throw new IllegalArgumentException("invalid LIMIT clauses:" + str);
    }

    public SupportSQLiteQuery create() {
        if (!a(this.g) || a(this.h)) {
            StringBuilder sb = new StringBuilder(120);
            sb.append("SELECT ");
            if (this.b) {
                sb.append("DISTINCT ");
            }
            String[] strArr = this.d;
            if (strArr == null || strArr.length == 0) {
                sb.append(" * ");
            } else {
                a(sb, strArr);
            }
            sb.append(" FROM ");
            sb.append(this.c);
            a(sb, " WHERE ", this.e);
            a(sb, " GROUP BY ", this.g);
            a(sb, " HAVING ", this.h);
            a(sb, " ORDER BY ", this.i);
            a(sb, " LIMIT ", this.j);
            return new SimpleSQLiteQuery(sb.toString(), this.f);
        }
        throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
    }

    private static void a(StringBuilder sb, String str, String str2) {
        if (!a(str2)) {
            sb.append(str);
            sb.append(str2);
        }
    }

    private static void a(StringBuilder sb, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(str);
        }
        sb.append(' ');
    }

    private static boolean a(String str) {
        return str == null || str.length() == 0;
    }
}
