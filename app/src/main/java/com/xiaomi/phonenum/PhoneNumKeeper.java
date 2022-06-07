package com.xiaomi.phonenum;

import android.content.Context;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.innetdate.InNetDateHelper;
import com.xiaomi.phonenum.innetdate.InNetDateResult;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import com.xiaomi.phonenum.phone.PhoneUtil;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: classes4.dex */
public class PhoneNumKeeper {
    private final int a;
    private ExecutorService[] b;
    private c c;

    /* loaded from: classes4.dex */
    public interface SetupFinishedListener {
        void onSetupFinished(Error error);
    }

    public PhoneNumKeeper(PhoneUtil phoneUtil) {
        this.a = phoneUtil.getPhoneCount();
        this.b = new ExecutorService[this.a];
    }

    public void setPhoneNumGetter(c cVar) {
        this.c = cVar;
    }

    public void setUp(SetupFinishedListener setupFinishedListener) {
        this.c.a(setupFinishedListener);
    }

    public int getSlotCount() {
        return this.a;
    }

    public synchronized Future<PhoneNum> obtainPhoneNum(int i) {
        return obtainPhoneNum(i, PhoneLevel.CACHE);
    }

    public synchronized Future<PhoneNum> obtainAndVerifyPhoneNum(int i) {
        return obtainPhoneNum(i, PhoneLevel.SMS_VERIFY);
    }

    public synchronized Future<PhoneNum> obtainPhoneNum(final int i, final PhoneLevel phoneLevel) {
        FutureTask futureTask;
        futureTask = new FutureTask(new Callable<PhoneNum>() { // from class: com.xiaomi.phonenum.PhoneNumKeeper.1
            /* renamed from: a */
            public PhoneNum call() throws ExecutionException, InterruptedException, IOException {
                return PhoneNumKeeper.this.c.b(i, phoneLevel);
            }
        });
        if (this.b[i] == null) {
            this.b[i] = Executors.newSingleThreadExecutor();
        }
        this.b[i].execute(futureTask);
        return futureTask;
    }

    public boolean invalidatePhoneNum(int i) {
        return this.c.a(i, this.c.a(i, PhoneLevel.LINE_NUMBER));
    }

    @Deprecated
    public boolean invalidateVerifiedToken(int i) {
        return invalidatePhoneNum(i);
    }

    public PhoneNum peekPhoneNum(int i) {
        return this.c.a(i, PhoneLevel.LINE_NUMBER);
    }

    public PhoneNum peekPhoneNum(int i, PhoneLevel phoneLevel) {
        return this.c.a(i, phoneLevel);
    }

    public InNetDateResult getInNetDate(Context context, int i) throws IOException {
        return new InNetDateHelper(context).getInNetDate(i);
    }

    public void dispose() {
        this.c.a();
    }
}
