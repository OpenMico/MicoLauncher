package com.xiaomi.micolauncher.common.statemodel;

import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.L;
import java.util.Objects;

/* loaded from: classes3.dex */
public class StateModel {
    private StateInfo a;
    private StateInfo b;

    /* loaded from: classes3.dex */
    private static class a {
        private static final StateModel a = new StateModel();
    }

    public static StateModel getInstance() {
        return a.a;
    }

    public StateInfo getCurStateInfo() {
        return this.b;
    }

    public void start(@NonNull StateInfo stateInfo) {
        L.statusModel.i("start %s", stateInfo.a);
        if (stateInfo.equals(this.b)) {
            L.statusModel.i("ignore for already in %s", stateInfo.a);
            return;
        }
        a();
        this.b = stateInfo;
    }

    private void a() {
        StateInfo stateInfo = this.b;
        if (stateInfo != null) {
            this.a = stateInfo;
        }
    }

    public void stop(@NonNull StateInfo stateInfo) {
        if (stateInfo.equals(this.b)) {
            L.statusModel.i("stop %s", stateInfo.a);
            a();
            this.b = null;
            return;
        }
        L.statusModel.i("ignore stop %s", stateInfo.a);
    }

    public StateInfo getPreStateInfo() {
        return this.a;
    }

    /* loaded from: classes3.dex */
    public static class StateInfo {
        private String a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public StateInfo(String str) {
            this.a = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.a, ((StateInfo) obj).a);
        }

        public int hashCode() {
            return Objects.hash(this.a);
        }
    }
}
