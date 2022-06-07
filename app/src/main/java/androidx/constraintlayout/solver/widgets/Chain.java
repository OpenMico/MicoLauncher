package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Chain {
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            i3 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.J;
            i2 = 0;
        } else {
            i2 = 2;
            i3 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.I;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            ChainHead chainHead = chainHeadArr[i4];
            chainHead.define();
            if (arrayList == null || (arrayList != null && arrayList.contains(chainHead.mFirst))) {
                a(constraintWidgetContainer, linearSystem, i, i2, chainHead);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x04cf  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0504  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x052b  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x053b  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0553  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void a(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r37, androidx.constraintlayout.solver.LinearSystem r38, int r39, int r40, androidx.constraintlayout.solver.widgets.ChainHead r41) {
        /*
            Method dump skipped, instructions count: 1409
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.a(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
