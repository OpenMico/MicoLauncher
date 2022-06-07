package com.xiaomi.micolauncher.common.schema;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.handler.DebugSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.NonMicoSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.QuickAppSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.SettingSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.SettingsTestSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.SkillSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.StartByUriSchemaHandler;
import com.xiaomi.micolauncher.common.schema.handler.ThirdPartySchemaHandler;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class SchemaManager {
    private static SchemaManager a;
    private ArrayList<SchemaHandler> b = new ArrayList<>();

    public static synchronized SchemaManager get() {
        SchemaManager schemaManager;
        synchronized (SchemaManager.class) {
            if (a == null) {
                a = new SchemaManager();
            }
            schemaManager = a;
        }
        return schemaManager;
    }

    private SchemaManager() {
        a(new HomepageSchemaHandler());
        a(new SkillSchemaHandler());
        a(new SettingSchemaHandler());
        a(new DebugSchemaHandler());
        a(new ThirdPartySchemaHandler());
        a(new QuickAppSchemaHandler());
        a(new SettingsTestSchemaHandler());
        a(new StartByUriSchemaHandler());
        a(new NonMicoSchemaHandler());
    }

    private void a(SchemaHandler schemaHandler) {
        this.b.add(schemaHandler);
    }

    public SchemaHandler queryHandler(Uri uri) {
        Iterator<SchemaHandler> it = this.b.iterator();
        while (it.hasNext()) {
            SchemaHandler next = it.next();
            if (next.canHandle(uri)) {
                return next;
            }
        }
        return null;
    }

    public boolean canHandle(String str) {
        Uri parse = !TextUtils.isEmpty(str) ? Uri.parse(str) : null;
        return (parse == null || queryHandler(parse) == null) ? false : true;
    }

    public static void handleSchema(Context context, String str) {
        L.base.i("SchemaManager handleSchema context name=%s", context.getClass().getSimpleName());
        if (!TextUtils.isEmpty(str)) {
            handleSchema(context, Uri.parse(str));
        }
    }

    public static void handleSchema(Context context, Uri uri) {
        if (uri != null) {
            L.base.i("SchemaManager handle schema %s", uri.toString());
            SchemaHandler queryHandler = get().queryHandler(uri);
            if (queryHandler != null) {
                SkillDataManager.getManager().addRecordByAction(uri.toString());
                queryHandler.process(context, uri, null);
                return;
            }
            Toast.makeText(context, (int) R.string.tool_open_not_support, 0).show();
        }
    }
}
