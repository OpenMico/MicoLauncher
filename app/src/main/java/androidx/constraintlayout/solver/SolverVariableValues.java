package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import java.io.PrintStream;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
    private static float i = 0.001f;
    int[] a;
    int[] b;
    int[] c;
    float[] d;
    int[] e;
    int[] f;
    private final ArrayRow m;
    protected final Cache mCache;
    private final int j = -1;
    private int k = 16;
    private int l = 16;
    int g = 0;
    int h = -1;

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int sizeInBytes() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SolverVariableValues(ArrayRow arrayRow, Cache cache) {
        int i2 = this.k;
        this.a = new int[i2];
        this.b = new int[i2];
        this.c = new int[i2];
        this.d = new float[i2];
        this.e = new int[i2];
        this.f = new int[i2];
        this.m = arrayRow;
        this.mCache = cache;
        clear();
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int getCurrentSize() {
        return this.g;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public SolverVariable getVariable(int i2) {
        int i3 = this.g;
        if (i3 == 0) {
            return null;
        }
        int i4 = this.h;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2 && i4 != -1) {
                return this.mCache.d[this.c[i4]];
            }
            i4 = this.f[i4];
            if (i4 == -1) {
                break;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float getVariableValue(int i2) {
        int i3 = this.g;
        int i4 = this.h;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2) {
                return this.d[i4];
            }
            i4 = this.f[i4];
            if (i4 == -1) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public boolean contains(SolverVariable solverVariable) {
        return indexOf(solverVariable) != -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int indexOf(SolverVariable solverVariable) {
        if (this.g == 0 || solverVariable == null) {
            return -1;
        }
        int i2 = solverVariable.id;
        int i3 = this.a[i2 % this.l];
        if (i3 == -1) {
            return -1;
        }
        if (this.c[i3] == i2) {
            return i3;
        }
        while (true) {
            int[] iArr = this.b;
            if (iArr[i3] == -1 || this.c[iArr[i3]] == i2) {
                break;
            }
            i3 = iArr[i3];
        }
        int[] iArr2 = this.b;
        if (iArr2[i3] != -1 && this.c[iArr2[i3]] == i2) {
            return iArr2[i3];
        }
        return -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float get(SolverVariable solverVariable) {
        int indexOf = indexOf(solverVariable);
        if (indexOf != -1) {
            return this.d[indexOf];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void display() {
        int i2 = this.g;
        System.out.print("{ ");
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                PrintStream printStream = System.out;
                printStream.print(variable + " = " + getVariableValue(i3) + StringUtils.SPACE);
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String str = hashCode() + " { ";
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                String str2 = str + variable + " = " + getVariableValue(i3) + StringUtils.SPACE;
                int indexOf = indexOf(variable);
                String str3 = str2 + "[p: ";
                String str4 = (this.e[indexOf] != -1 ? str3 + this.mCache.d[this.c[this.e[indexOf]]] : str3 + "none") + ", n: ";
                str = (this.f[indexOf] != -1 ? str4 + this.mCache.d[this.c[this.f[indexOf]]] : str4 + "none") + "]";
            }
        }
        return str + " }";
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void clear() {
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                variable.removeFromRow(this.m);
            }
        }
        for (int i4 = 0; i4 < this.k; i4++) {
            this.c[i4] = -1;
            this.b[i4] = -1;
        }
        for (int i5 = 0; i5 < this.l; i5++) {
            this.a[i5] = -1;
        }
        this.g = 0;
        this.h = -1;
    }

    private void a() {
        int i2 = this.k * 2;
        this.c = Arrays.copyOf(this.c, i2);
        this.d = Arrays.copyOf(this.d, i2);
        this.e = Arrays.copyOf(this.e, i2);
        this.f = Arrays.copyOf(this.f, i2);
        this.b = Arrays.copyOf(this.b, i2);
        for (int i3 = this.k; i3 < i2; i3++) {
            this.c[i3] = -1;
            this.b[i3] = -1;
        }
        this.k = i2;
    }

    private void a(SolverVariable solverVariable, int i2) {
        int[] iArr;
        int i3 = solverVariable.id % this.l;
        int[] iArr2 = this.a;
        int i4 = iArr2[i3];
        if (i4 == -1) {
            iArr2[i3] = i2;
        } else {
            while (true) {
                iArr = this.b;
                if (iArr[i4] == -1) {
                    break;
                }
                i4 = iArr[i4];
            }
            iArr[i4] = i2;
        }
        this.b[i2] = -1;
    }

    private void a(SolverVariable solverVariable) {
        int i2 = solverVariable.id % this.l;
        int i3 = this.a[i2];
        if (i3 != -1) {
            int i4 = solverVariable.id;
            if (this.c[i3] == i4) {
                int[] iArr = this.a;
                int[] iArr2 = this.b;
                iArr[i2] = iArr2[i3];
                iArr2[i3] = -1;
                return;
            }
            while (true) {
                int[] iArr3 = this.b;
                if (iArr3[i3] == -1 || this.c[iArr3[i3]] == i4) {
                    break;
                }
                i3 = iArr3[i3];
            }
            int[] iArr4 = this.b;
            int i5 = iArr4[i3];
            if (i5 != -1 && this.c[i5] == i4) {
                iArr4[i3] = iArr4[i5];
                iArr4[i5] = -1;
            }
        }
    }

    private void a(int i2, SolverVariable solverVariable, float f) {
        this.c[i2] = solverVariable.id;
        this.d[i2] = f;
        this.e[i2] = -1;
        this.f[i2] = -1;
        solverVariable.addToRow(this.m);
        solverVariable.usageInRowCount++;
        this.g++;
    }

    private int b() {
        for (int i2 = 0; i2 < this.k; i2++) {
            if (this.c[i2] == -1) {
                return i2;
            }
        }
        return -1;
    }

    private void b(int i2, SolverVariable solverVariable, float f) {
        int b = b();
        a(b, solverVariable, f);
        if (i2 != -1) {
            this.e[b] = i2;
            int[] iArr = this.f;
            iArr[b] = iArr[i2];
            iArr[i2] = b;
        } else {
            this.e[b] = -1;
            if (this.g > 0) {
                this.f[b] = this.h;
                this.h = b;
            } else {
                this.f[b] = -1;
            }
        }
        int[] iArr2 = this.f;
        if (iArr2[b] != -1) {
            this.e[iArr2[b]] = b;
        }
        a(solverVariable, b);
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void put(SolverVariable solverVariable, float f) {
        float f2 = i;
        if (f <= (-f2) || f >= f2) {
            if (this.g == 0) {
                a(0, solverVariable, f);
                a(solverVariable, 0);
                this.h = 0;
                return;
            }
            int indexOf = indexOf(solverVariable);
            if (indexOf != -1) {
                this.d[indexOf] = f;
                return;
            }
            if (this.g + 1 >= this.k) {
                a();
            }
            int i2 = this.g;
            int i3 = this.h;
            int i4 = -1;
            for (int i5 = 0; i5 < i2; i5++) {
                if (this.c[i3] == solverVariable.id) {
                    this.d[i3] = f;
                    return;
                }
                if (this.c[i3] < solverVariable.id) {
                    i4 = i3;
                }
                i3 = this.f[i3];
                if (i3 == -1) {
                    break;
                }
            }
            b(i4, solverVariable, f);
            return;
        }
        remove(solverVariable, true);
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float remove(SolverVariable solverVariable, boolean z) {
        int indexOf = indexOf(solverVariable);
        if (indexOf == -1) {
            return 0.0f;
        }
        a(solverVariable);
        float f = this.d[indexOf];
        if (this.h == indexOf) {
            this.h = this.f[indexOf];
        }
        this.c[indexOf] = -1;
        int[] iArr = this.e;
        if (iArr[indexOf] != -1) {
            int[] iArr2 = this.f;
            iArr2[iArr[indexOf]] = iArr2[indexOf];
        }
        int[] iArr3 = this.f;
        if (iArr3[indexOf] != -1) {
            int[] iArr4 = this.e;
            iArr4[iArr3[indexOf]] = iArr4[indexOf];
        }
        this.g--;
        solverVariable.usageInRowCount--;
        if (z) {
            solverVariable.removeFromRow(this.m);
        }
        return f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void add(SolverVariable solverVariable, float f, boolean z) {
        float f2 = i;
        if (f <= (-f2) || f >= f2) {
            int indexOf = indexOf(solverVariable);
            if (indexOf == -1) {
                put(solverVariable, f);
                return;
            }
            float[] fArr = this.d;
            fArr[indexOf] = fArr[indexOf] + f;
            float f3 = fArr[indexOf];
            float f4 = i;
            if (f3 > (-f4) && fArr[indexOf] < f4) {
                fArr[indexOf] = 0.0f;
                remove(solverVariable, z);
            }
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float use(ArrayRow arrayRow, boolean z) {
        float f = get(arrayRow.a);
        remove(arrayRow.a, z);
        SolverVariableValues solverVariableValues = (SolverVariableValues) arrayRow.variables;
        int currentSize = solverVariableValues.getCurrentSize();
        int i2 = solverVariableValues.h;
        int i3 = 0;
        int i4 = 0;
        while (i3 < currentSize) {
            if (solverVariableValues.c[i4] != -1) {
                add(this.mCache.d[solverVariableValues.c[i4]], solverVariableValues.d[i4] * f, z);
                i3++;
            }
            i4++;
        }
        return f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void invert() {
        int i2 = this.g;
        int i3 = this.h;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.d;
            fArr[i3] = fArr[i3] * (-1.0f);
            i3 = this.f[i3];
            if (i3 == -1) {
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void divideByAmount(float f) {
        int i2 = this.g;
        int i3 = this.h;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.d;
            fArr[i3] = fArr[i3] / f;
            i3 = this.f[i3];
            if (i3 == -1) {
                return;
            }
        }
    }
}
