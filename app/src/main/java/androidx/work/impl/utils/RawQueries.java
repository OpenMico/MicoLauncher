package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.work.WorkInfo;
import androidx.work.WorkQuery;
import androidx.work.impl.model.WorkTypeConverters;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class RawQueries {
    private RawQueries() {
    }

    @NonNull
    public static SupportSQLiteQuery workQueryToRawQuery(@NonNull WorkQuery workQuery) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("SELECT * FROM workspec");
        String str = " WHERE";
        List<WorkInfo.State> states = workQuery.getStates();
        if (!states.isEmpty()) {
            ArrayList arrayList2 = new ArrayList(states.size());
            for (WorkInfo.State state : states) {
                arrayList2.add(Integer.valueOf(WorkTypeConverters.stateToInt(state)));
            }
            sb.append(str);
            sb.append(" state IN (");
            a(sb, arrayList2.size());
            sb.append(")");
            arrayList.addAll(arrayList2);
            str = " AND";
        }
        List<UUID> ids = workQuery.getIds();
        if (!ids.isEmpty()) {
            ArrayList arrayList3 = new ArrayList(ids.size());
            for (UUID uuid : ids) {
                arrayList3.add(uuid.toString());
            }
            sb.append(str);
            sb.append(" id IN (");
            a(sb, ids.size());
            sb.append(")");
            arrayList.addAll(arrayList3);
            str = " AND";
        }
        List<String> tags = workQuery.getTags();
        if (!tags.isEmpty()) {
            sb.append(str);
            sb.append(" id IN (SELECT work_spec_id FROM worktag WHERE tag IN (");
            a(sb, tags.size());
            sb.append("))");
            arrayList.addAll(tags);
            str = " AND";
        }
        List<String> uniqueWorkNames = workQuery.getUniqueWorkNames();
        if (!uniqueWorkNames.isEmpty()) {
            sb.append(str);
            sb.append(" id IN (SELECT work_spec_id FROM workname WHERE name IN (");
            a(sb, uniqueWorkNames.size());
            sb.append("))");
            arrayList.addAll(uniqueWorkNames);
        }
        sb.append(";");
        return new SimpleSQLiteQuery(sb.toString(), arrayList.toArray());
    }

    private static void a(@NonNull StringBuilder sb, int i) {
        if (i > 0) {
            sb.append("?");
            for (int i2 = 1; i2 < i; i2++) {
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                sb.append("?");
            }
        }
    }
}
