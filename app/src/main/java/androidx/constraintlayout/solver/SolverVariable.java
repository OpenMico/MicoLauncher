package androidx.constraintlayout.solver;

import java.util.Arrays;
import java.util.HashSet;

/* loaded from: classes.dex */
public class SolverVariable {
    public static final int STRENGTH_BARRIER = 6;
    public static final int STRENGTH_CENTERING = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 8;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static int k = 1;
    private static int l = 1;
    private static int m = 1;
    private static int n = 1;
    private static int o = 1;
    int a;
    float[] b;
    float[] c;
    public float computedValue;
    Type d;
    ArrayRow[] e;
    int f;
    boolean g;
    int h;
    float i;
    public int id;
    public boolean inGoal;
    public boolean isFinalValue;
    HashSet<ArrayRow> j;
    private String p;
    public int strength;
    public int usageInRowCount;

    /* loaded from: classes.dex */
    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public static void a() {
        l++;
    }

    public SolverVariable(String str, Type type) {
        this.id = -1;
        this.a = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.b = new float[9];
        this.c = new float[9];
        this.e = new ArrayRow[16];
        this.f = 0;
        this.usageInRowCount = 0;
        this.g = false;
        this.h = -1;
        this.i = 0.0f;
        this.j = null;
        this.p = str;
        this.d = type;
    }

    public SolverVariable(Type type, String str) {
        this.id = -1;
        this.a = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.b = new float[9];
        this.c = new float[9];
        this.e = new ArrayRow[16];
        this.f = 0;
        this.usageInRowCount = 0;
        this.g = false;
        this.h = -1;
        this.i = 0.0f;
        this.j = null;
        this.d = type;
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i = 0;
        while (true) {
            int i2 = this.f;
            if (i >= i2) {
                ArrayRow[] arrayRowArr = this.e;
                if (i2 >= arrayRowArr.length) {
                    this.e = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.e;
                int i3 = this.f;
                arrayRowArr2[i3] = arrayRow;
                this.f = i3 + 1;
                return;
            } else if (this.e[i] != arrayRow) {
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x000d, code lost:
        if (r1 >= (r0 - 1)) goto L_0x0019;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:
        r5 = r4.e;
        r2 = r1 + 1;
        r5[r1] = r5[r2];
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
        r4.f--;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeFromRow(androidx.constraintlayout.solver.ArrayRow r5) {
        /*
            r4 = this;
            int r0 = r4.f
            r1 = 0
        L_0x0003:
            if (r1 >= r0) goto L_0x0023
            androidx.constraintlayout.solver.ArrayRow[] r2 = r4.e
            r2 = r2[r1]
            if (r2 != r5) goto L_0x0020
        L_0x000b:
            int r5 = r0 + (-1)
            if (r1 >= r5) goto L_0x0019
            androidx.constraintlayout.solver.ArrayRow[] r5 = r4.e
            int r2 = r1 + 1
            r3 = r5[r2]
            r5[r1] = r3
            r1 = r2
            goto L_0x000b
        L_0x0019:
            int r5 = r4.f
            int r5 = r5 + (-1)
            r4.f = r5
            return
        L_0x0020:
            int r1 = r1 + 1
            goto L_0x0003
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.SolverVariable.removeFromRow(androidx.constraintlayout.solver.ArrayRow):void");
    }

    public final void updateReferencesWithNewDefinition(LinearSystem linearSystem, ArrayRow arrayRow) {
        int i = this.f;
        for (int i2 = 0; i2 < i; i2++) {
            this.e[i2].updateFromRow(linearSystem, arrayRow, false);
        }
        this.f = 0;
    }

    public void setFinalValue(LinearSystem linearSystem, float f) {
        this.computedValue = f;
        this.isFinalValue = true;
        this.g = false;
        this.h = -1;
        this.i = 0.0f;
        int i = this.f;
        this.a = -1;
        for (int i2 = 0; i2 < i; i2++) {
            this.e[i2].updateFromFinalVariable(linearSystem, this, false);
        }
        this.f = 0;
    }

    public void setSynonym(LinearSystem linearSystem, SolverVariable solverVariable, float f) {
        this.g = true;
        this.h = solverVariable.id;
        this.i = f;
        int i = this.f;
        this.a = -1;
        for (int i2 = 0; i2 < i; i2++) {
            this.e[i2].updateFromSynonymVariable(linearSystem, this, false);
        }
        this.f = 0;
        linearSystem.displayReadableRows();
    }

    public void reset() {
        this.p = null;
        this.d = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.a = -1;
        this.computedValue = 0.0f;
        this.isFinalValue = false;
        this.g = false;
        this.h = -1;
        this.i = 0.0f;
        int i = this.f;
        for (int i2 = 0; i2 < i; i2++) {
            this.e[i2] = null;
        }
        this.f = 0;
        this.usageInRowCount = 0;
        this.inGoal = false;
        Arrays.fill(this.c, 0.0f);
    }

    public String getName() {
        return this.p;
    }

    public void setName(String str) {
        this.p = str;
    }

    public void setType(Type type, String str) {
        this.d = type;
    }

    public String toString() {
        if (this.p != null) {
            return "" + this.p;
        }
        return "" + this.id;
    }
}
