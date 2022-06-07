package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

import android.content.Context;
import android.text.TextUtils;
import androidx.room.Room;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.cmcc.ims.model.ImsCallLog;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ImsDaoManager {
    private static ImsDaoManager a;
    private long d;
    private final int b = 50;
    private final int c = 100;
    private final String e = "ims_call_log";

    public void init(Context context) {
    }

    private ImsDaoManager() {
    }

    public static ImsDaoManager getInstance() {
        if (a == null) {
            a = new ImsDaoManager();
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ImsDataBase a() {
        if (MicoApplication.getGlobalContext() == null) {
            return null;
        }
        return (ImsDataBase) Room.databaseBuilder(MicoApplication.getGlobalContext(), ImsDataBase.class, "ims_call_log").addMigrations(ImsDataBase.b).allowMainThreadQueries().build();
    }

    public void insertImsCallLog(final ImsCallLog imsCallLog) {
        Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDaoManager.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                ImsDataBase a2 = ImsDaoManager.this.a();
                ImsDao imsDao = a2.imsDao();
                ImsCallLogEntity imsCallLogEntity = new ImsCallLogEntity();
                imsCallLogEntity.isInComing = imsCallLog.isInComing;
                imsCallLogEntity.timestamp = imsCallLog.callTime;
                imsCallLogEntity.num = imsCallLog.num;
                imsCallLogEntity.name = imsCallLog.name;
                imsCallLogEntity.cmccId = imsCallLog.cmccId;
                imsCallLogEntity.isConnected = imsCallLog.isConnected;
                imsCallLogEntity.id = imsDao.queryImsCallLogCount() + 1;
                imsCallLogEntity.type = imsCallLog.type;
                L.voip.d("insertImsCallLog, name: %s, num: %s, cmccId: %s, type: %s, callTime: %s, isInComing: %s, isConnected: %s", imsCallLog.name, imsCallLog.num, imsCallLog.cmccId, imsCallLog.type, Long.valueOf(imsCallLog.callTime), Boolean.valueOf(imsCallLog.isInComing), Boolean.valueOf(imsCallLog.isConnected));
                imsDao.insertImsCallLog(imsCallLogEntity);
                ImsDaoManager.this.a(imsDao);
                ImsDaoManager.this.a(a2);
                observableEmitter.onNext(true);
                observableEmitter.onComplete();
            }
        }).subscribe(new Consumer<Boolean>() { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDaoManager.1
            /* renamed from: a */
            public void accept(Boolean bool) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDaoManager.2
            /* renamed from: a */
            public void accept(Throwable th) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ImsDataBase imsDataBase) {
        if (imsDataBase != null && imsDataBase.isOpen()) {
            imsDataBase.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ImsDao imsDao) {
        if (imsDao != null && System.currentTimeMillis() - this.d > 600000) {
            this.d = System.currentTimeMillis();
            int queryImsCallLogCount = imsDao.queryImsCallLogCount();
            L.base.d("SessionLog count= %d", Integer.valueOf(queryImsCallLogCount));
            if (imsDao != null && queryImsCallLogCount >= 100) {
                for (ImsCallLogEntity imsCallLogEntity : imsDao.queryImsCallLogByAsc(queryImsCallLogCount - 100)) {
                    imsDao.deleteImsCallLog(imsCallLogEntity);
                }
            }
        }
    }

    public List<ImsCallLog> getImsCallLogs() {
        ArrayList arrayList = new ArrayList();
        ImsDataBase a2 = a();
        try {
            if (a2 != null) {
                try {
                    ImsDao imsDao = a2.imsDao();
                    if (imsDao != null) {
                        ImsCallLogEntity[] queryImsCallLogByDesc = imsDao.queryImsCallLogByDesc(50);
                        if (queryImsCallLogByDesc.length > 0) {
                            for (int i = 0; i < queryImsCallLogByDesc.length; i++) {
                                ImsCallLog imsCallLog = new ImsCallLog();
                                imsCallLog.num = queryImsCallLogByDesc[i].num;
                                imsCallLog.name = queryImsCallLogByDesc[i].name;
                                imsCallLog.cmccId = queryImsCallLogByDesc[i].cmccId;
                                imsCallLog.isConnected = queryImsCallLogByDesc[i].isConnected;
                                imsCallLog.isInComing = queryImsCallLogByDesc[i].isInComing;
                                imsCallLog.callTime = queryImsCallLogByDesc[i].timestamp;
                                imsCallLog.type = TextUtils.isEmpty(queryImsCallLogByDesc[i].type) ? VoipModel.CALL_LOG_TYPE_CMCC : queryImsCallLogByDesc[i].type;
                                arrayList.add(imsCallLog);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        } finally {
            a(a2);
        }
    }
}
