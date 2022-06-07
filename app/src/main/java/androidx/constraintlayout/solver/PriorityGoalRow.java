package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class PriorityGoalRow extends ArrayRow {
    Cache g;
    private SolverVariable[] i;
    private SolverVariable[] j;
    private int h = 128;
    private int k = 0;
    a f = new a(this);

    /* loaded from: classes.dex */
    class a implements Comparable {
        SolverVariable a;
        PriorityGoalRow b;

        public a(PriorityGoalRow priorityGoalRow) {
            this.b = priorityGoalRow;
        }

        public void a(SolverVariable solverVariable) {
            this.a = solverVariable;
        }

        public boolean a(SolverVariable solverVariable, float f) {
            boolean z = true;
            if (this.a.inGoal) {
                for (int i = 0; i < 9; i++) {
                    float[] fArr = this.a.c;
                    fArr[i] = fArr[i] + (solverVariable.c[i] * f);
                    if (Math.abs(this.a.c[i]) < 1.0E-4f) {
                        this.a.c[i] = 0.0f;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    PriorityGoalRow.this.d(this.a);
                }
                return false;
            }
            for (int i2 = 0; i2 < 9; i2++) {
                float f2 = solverVariable.c[i2];
                if (f2 != 0.0f) {
                    float f3 = f2 * f;
                    if (Math.abs(f3) < 1.0E-4f) {
                        f3 = 0.0f;
                    }
                    this.a.c[i2] = f3;
                } else {
                    this.a.c[i2] = 0.0f;
                }
            }
            return true;
        }

        public final boolean a() {
            for (int i = 8; i >= 0; i--) {
                float f = this.a.c[i];
                if (f > 0.0f) {
                    return false;
                }
                if (f < 0.0f) {
                    return true;
                }
            }
            return false;
        }

        public final boolean b(SolverVariable solverVariable) {
            for (int i = 8; i >= 0; i--) {
                float f = solverVariable.c[i];
                float f2 = this.a.c[i];
                if (f2 != f) {
                    return f2 < f;
                }
            }
            return false;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return this.a.id - ((SolverVariable) obj).id;
        }

        public void b() {
            Arrays.fill(this.a.c, 0.0f);
        }

        public String toString() {
            String str = "[ ";
            if (this.a != null) {
                for (int i = 0; i < 9; i++) {
                    str = str + this.a.c[i] + StringUtils.SPACE;
                }
            }
            return str + "] " + this.a;
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow, androidx.constraintlayout.solver.LinearSystem.a
    public void clear() {
        this.k = 0;
        this.b = 0.0f;
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        int i = this.h;
        this.i = new SolverVariable[i];
        this.j = new SolverVariable[i];
        this.g = cache;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow, androidx.constraintlayout.solver.LinearSystem.a
    public boolean isEmpty() {
        return this.k == 0;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow, androidx.constraintlayout.solver.LinearSystem.a
    public SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.k; i2++) {
            SolverVariable solverVariable = this.i[i2];
            if (!zArr[solverVariable.id]) {
                this.f.a(solverVariable);
                if (i == -1) {
                    if (!this.f.a()) {
                    }
                    i = i2;
                } else {
                    if (!this.f.b(this.i[i])) {
                    }
                    i = i2;
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.i[i];
    }

    @Override // androidx.constraintlayout.solver.ArrayRow, androidx.constraintlayout.solver.LinearSystem.a
    public void addError(SolverVariable solverVariable) {
        this.f.a(solverVariable);
        this.f.b();
        solverVariable.c[solverVariable.strength] = 1.0f;
        c(solverVariable);
    }

    private final void c(SolverVariable solverVariable) {
        int i;
        int i2 = this.k + 1;
        SolverVariable[] solverVariableArr = this.i;
        if (i2 > solverVariableArr.length) {
            this.i = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            SolverVariable[] solverVariableArr2 = this.i;
            this.j = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.i;
        int i3 = this.k;
        solverVariableArr3[i3] = solverVariable;
        this.k = i3 + 1;
        int i4 = this.k;
        if (i4 > 1 && solverVariableArr3[i4 - 1].id > solverVariable.id) {
            int i5 = 0;
            while (true) {
                i = this.k;
                if (i5 >= i) {
                    break;
                }
                this.j[i5] = this.i[i5];
                i5++;
            }
            Arrays.sort(this.j, 0, i, new Comparator<SolverVariable>() { // from class: androidx.constraintlayout.solver.PriorityGoalRow.1
                /* renamed from: a */
                public int compare(SolverVariable solverVariable2, SolverVariable solverVariable3) {
                    return solverVariable2.id - solverVariable3.id;
                }
            });
            for (int i6 = 0; i6 < this.k; i6++) {
                this.i[i6] = this.j[i6];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x001c, code lost:
        r5.k = r2 - 1;
        r6.inGoal = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x000c, code lost:
        r2 = r5.k;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0010, code lost:
        if (r1 >= (r2 - 1)) goto L_0x001c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
        r2 = r5.i;
        r3 = r1 + 1;
        r2[r1] = r2[r3];
        r1 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void d(androidx.constraintlayout.solver.SolverVariable r6) {
        /*
            r5 = this;
            r0 = 0
            r1 = r0
        L_0x0002:
            int r2 = r5.k
            if (r1 >= r2) goto L_0x0026
            androidx.constraintlayout.solver.SolverVariable[] r2 = r5.i
            r2 = r2[r1]
            if (r2 != r6) goto L_0x0023
        L_0x000c:
            int r2 = r5.k
            int r3 = r2 + (-1)
            if (r1 >= r3) goto L_0x001c
            androidx.constraintlayout.solver.SolverVariable[] r2 = r5.i
            int r3 = r1 + 1
            r4 = r2[r3]
            r2[r1] = r4
            r1 = r3
            goto L_0x000c
        L_0x001c:
            int r2 = r2 + (-1)
            r5.k = r2
            r6.inGoal = r0
            return
        L_0x0023:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.PriorityGoalRow.d(androidx.constraintlayout.solver.SolverVariable):void");
    }

    @Override // androidx.constraintlayout.solver.ArrayRow
    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        SolverVariable solverVariable = arrayRow.a;
        if (solverVariable != null) {
            ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
            int currentSize = arrayRowVariables.getCurrentSize();
            for (int i = 0; i < currentSize; i++) {
                SolverVariable variable = arrayRowVariables.getVariable(i);
                float variableValue = arrayRowVariables.getVariableValue(i);
                this.f.a(variable);
                if (this.f.a(solverVariable, variableValue)) {
                    c(variable);
                }
                this.b += arrayRow.b * variableValue;
            }
            d(solverVariable);
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow
    public String toString() {
        String str = " goal -> (" + this.b + ") : ";
        for (int i = 0; i < this.k; i++) {
            this.f.a(this.i[i]);
            str = str + this.f + StringUtils.SPACE;
        }
        return str;
    }
}
