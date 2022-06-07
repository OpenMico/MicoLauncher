package com.xiaomi.micolauncher.skills.alarm.model;

import androidx.annotation.NonNull;
import com.umeng.umcrash.UMCrash;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.DataBaseLogHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.FileSizeUtil;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmSchema;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy;
import io.realm.exceptions.RealmError;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class AlarmRealmHelper {
    public static final String DB_NAME = "calendar.realm";
    public static final String ID = "id";
    private final RealmConfiguration a;

    private AlarmRealmHelper() {
        this.a = new RealmConfiguration.Builder().name(DB_NAME).schemaVersion(1L).migration($$Lambda$AlarmRealmHelper$lity016q5flSZ6IGH7gO8uTKko.INSTANCE).modules(new AlarmModule(), new Object[0]).compactOnLaunch().build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DynamicRealm dynamicRealm, long j, long j2) {
        RealmSchema schema = dynamicRealm.getSchema();
        if (j == 0) {
            schema.get(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("ringtoneType", String.class, new FieldAttribute[0]).addField("displayTxt", String.class, new FieldAttribute[0]).addField("ringtoneVideo", String.class, new FieldAttribute[0]).addField("ringtoneVideoImage", String.class, new FieldAttribute[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final AlarmRealmHelper a = new AlarmRealmHelper();
    }

    public static AlarmRealmHelper getInstance() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(AlarmRealmObjectBean alarmRealmObjectBean) {
        DataBaseLogHelper.printProcess();
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            instance.beginTransaction();
            Number max = instance.where(AlarmRealmObject.class).max("id");
            int i = 1;
            if (max != null) {
                i = 1 + max.intValue();
            }
            alarmRealmObjectBean.setId(i);
            instance.copyToRealm((Realm) new AlarmRealmObject(alarmRealmObjectBean), new ImportFlag[0]);
            instance.commitTransaction();
            if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        DataBaseLogHelper.printProcess();
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            AlarmRealmObject alarmRealmObject = (AlarmRealmObject) instance.where(AlarmRealmObject.class).equalTo("id", Integer.valueOf(i)).findFirst();
            if (alarmRealmObject != null) {
                instance.beginTransaction();
                alarmRealmObject.deleteFromRealm();
                instance.commitTransaction();
                if (instance != null) {
                    instance.close();
                }
            } else if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull AlarmRealmObjectBean alarmRealmObjectBean) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.a);
            instance.beginTransaction();
            instance.copyToRealmOrUpdate((Realm) new AlarmRealmObject(alarmRealmObjectBean), new ImportFlag[0]);
            instance.commitTransaction();
            if (instance != null) {
                instance.close();
            }
        } catch (RealmError e) {
            L.alarm.e("AlarmRealmHelper#updateAlarm catches exceptions %s", e);
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmRealmHelper$9dRJBF4NEINBn48mwrXpw08BFpI
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmRealmHelper.b(RealmError.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(RealmError realmError) {
        String printSDCardSpaceInfo = FileSizeUtil.printSDCardSpaceInfo();
        String printSystemSpaceInfo = FileSizeUtil.printSystemSpaceInfo();
        UMCrash.generateCustomLog(printSDCardSpaceInfo + "\n--------\n" + printSystemSpaceInfo, "AlarmRealmHelper");
        UMCrash.generateCustomLog(realmError, "AlarmRealmHelper");
    }

    public ArrayList<AlarmRealmObjectBean> queryAllAlarm() {
        DataBaseLogHelper.printProcess();
        ArrayList<AlarmRealmObjectBean> arrayList = new ArrayList<>();
        try {
            Realm instance = Realm.getInstance(this.a);
            Iterator it = instance.where(AlarmRealmObject.class).findAll().sort("id").iterator();
            while (it.hasNext()) {
                arrayList.add(new AlarmRealmObjectBean((AlarmRealmObject) it.next()));
            }
            if (instance != null) {
                instance.close();
            }
        } catch (RealmError e) {
            L.alarm.e("AlarmRealmHelper#queryAllAlarm catches exceptions %s", e);
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmRealmHelper$YBgJjr9ubm38sk-Oz7XENCeXEkk
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmRealmHelper.a(RealmError.this);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(RealmError realmError) {
        String printSDCardSpaceInfo = FileSizeUtil.printSDCardSpaceInfo();
        String printSystemSpaceInfo = FileSizeUtil.printSystemSpaceInfo();
        UMCrash.generateCustomLog(printSDCardSpaceInfo + "\n--------\n" + printSystemSpaceInfo, "AlarmRealmHelper");
        UMCrash.generateCustomLog(realmError, "AlarmRealmHelper");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x003a: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:20:0x004a, block:B:14:0x003a
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public boolean isAlarmExist(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x003a: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:20:0x004a, block:B:14:0x003a
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */
}
