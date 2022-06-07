package io.realm.internal;

import com.xiaomi.mipush.sdk.Constants;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;

/* loaded from: classes5.dex */
public final class ColumnIndices {
    private final Map<Class<? extends RealmModel>, ColumnInfo> a = new HashMap();
    private final Map<String, ColumnInfo> b = new HashMap();
    private final RealmProxyMediator c;
    private final OsSchemaInfo d;

    public ColumnIndices(RealmProxyMediator realmProxyMediator, OsSchemaInfo osSchemaInfo) {
        this.c = realmProxyMediator;
        this.d = osSchemaInfo;
    }

    @Nonnull
    public ColumnInfo getColumnInfo(Class<? extends RealmModel> cls) {
        ColumnInfo columnInfo = this.a.get(cls);
        if (columnInfo != null) {
            return columnInfo;
        }
        ColumnInfo createColumnInfo = this.c.createColumnInfo(cls, this.d);
        this.a.put(cls, createColumnInfo);
        return createColumnInfo;
    }

    @Nonnull
    public ColumnInfo getColumnInfo(String str) {
        ColumnInfo columnInfo = this.b.get(str);
        if (columnInfo == null) {
            Iterator<Class<? extends RealmModel>> it = this.c.getModelClasses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Class<? extends RealmModel> next = it.next();
                if (this.c.getSimpleClassName(next).equals(str)) {
                    columnInfo = getColumnInfo(next);
                    this.b.put(str, columnInfo);
                    break;
                }
            }
        }
        if (columnInfo != null) {
            return columnInfo;
        }
        throw new RealmException(String.format(Locale.US, "'%s' doesn't exist in current schema.", str));
    }

    public void refresh() {
        for (Map.Entry<Class<? extends RealmModel>, ColumnInfo> entry : this.a.entrySet()) {
            entry.getValue().copyFrom(this.c.createColumnInfo(entry.getKey(), this.d));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnIndices[");
        boolean z = false;
        for (Map.Entry<Class<? extends RealmModel>, ColumnInfo> entry : this.a.entrySet()) {
            if (z) {
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            sb.append(entry.getKey().getSimpleName());
            sb.append("->");
            sb.append(entry.getValue());
            z = true;
        }
        sb.append("]");
        return sb.toString();
    }
}
