package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import java.io.PrintStream;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class ArrayLinkedVariables implements ArrayRow.ArrayRowVariables {
    private static float k = 0.001f;
    private final ArrayRow b;
    private int[] e;
    private int[] f;
    private float[] g;
    protected final Cache mCache;
    int a = 0;
    private int c = 8;
    private SolverVariable d = null;
    private int h = -1;
    private int i = -1;
    private boolean j = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        int i = this.c;
        this.e = new int[i];
        this.f = new int[i];
        this.g = new float[i];
        this.b = arrayRow;
        this.mCache = cache;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public final void put(SolverVariable solverVariable, float f) {
        if (f == 0.0f) {
            remove(solverVariable, true);
            return;
        }
        int i = this.h;
        if (i == -1) {
            this.h = 0;
            float[] fArr = this.g;
            int i2 = this.h;
            fArr[i2] = f;
            this.e[i2] = solverVariable.id;
            this.f[this.h] = -1;
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.b);
            this.a++;
            if (!this.j) {
                this.i++;
                int i3 = this.i;
                int[] iArr = this.e;
                if (i3 >= iArr.length) {
                    this.j = true;
                    this.i = iArr.length - 1;
                    return;
                }
                return;
            }
            return;
        }
        int i4 = -1;
        for (int i5 = 0; i != -1 && i5 < this.a; i5++) {
            if (this.e[i] == solverVariable.id) {
                this.g[i] = f;
                return;
            }
            if (this.e[i] < solverVariable.id) {
                i4 = i;
            }
            i = this.f[i];
        }
        int i6 = this.i;
        i6++;
        if (this.j) {
            int[] iArr2 = this.e;
            if (iArr2[i6] != -1) {
                i6 = iArr2.length;
            }
        }
        int[] iArr3 = this.e;
        if (i6 >= iArr3.length && this.a < iArr3.length) {
            int i7 = 0;
            while (true) {
                int[] iArr4 = this.e;
                if (i7 >= iArr4.length) {
                    break;
                } else if (iArr4[i7] == -1) {
                    i6 = i7;
                    break;
                } else {
                    i7++;
                }
            }
        }
        int[] iArr5 = this.e;
        if (i6 >= iArr5.length) {
            i6 = iArr5.length;
            this.c *= 2;
            this.j = false;
            this.i = i6 - 1;
            this.g = Arrays.copyOf(this.g, this.c);
            this.e = Arrays.copyOf(this.e, this.c);
            this.f = Arrays.copyOf(this.f, this.c);
        }
        this.e[i6] = solverVariable.id;
        this.g[i6] = f;
        if (i4 != -1) {
            int[] iArr6 = this.f;
            iArr6[i6] = iArr6[i4];
            iArr6[i4] = i6;
        } else {
            this.f[i6] = this.h;
            this.h = i6;
        }
        solverVariable.usageInRowCount++;
        solverVariable.addToRow(this.b);
        this.a++;
        if (!this.j) {
            this.i++;
        }
        if (this.a >= this.e.length) {
            this.j = true;
        }
        int i8 = this.i;
        int[] iArr7 = this.e;
        if (i8 >= iArr7.length) {
            this.j = true;
            this.i = iArr7.length - 1;
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void add(SolverVariable solverVariable, float f, boolean z) {
        float f2 = k;
        if (f <= (-f2) || f >= f2) {
            int i = this.h;
            if (i == -1) {
                this.h = 0;
                float[] fArr = this.g;
                int i2 = this.h;
                fArr[i2] = f;
                this.e[i2] = solverVariable.id;
                this.f[this.h] = -1;
                solverVariable.usageInRowCount++;
                solverVariable.addToRow(this.b);
                this.a++;
                if (!this.j) {
                    this.i++;
                    int i3 = this.i;
                    int[] iArr = this.e;
                    if (i3 >= iArr.length) {
                        this.j = true;
                        this.i = iArr.length - 1;
                        return;
                    }
                    return;
                }
                return;
            }
            int i4 = -1;
            for (int i5 = 0; i != -1 && i5 < this.a; i5++) {
                if (this.e[i] == solverVariable.id) {
                    float f3 = this.g[i] + f;
                    float f4 = k;
                    if (f3 > (-f4) && f3 < f4) {
                        f3 = 0.0f;
                    }
                    this.g[i] = f3;
                    if (f3 == 0.0f) {
                        if (i == this.h) {
                            this.h = this.f[i];
                        } else {
                            int[] iArr2 = this.f;
                            iArr2[i4] = iArr2[i];
                        }
                        if (z) {
                            solverVariable.removeFromRow(this.b);
                        }
                        if (this.j) {
                            this.i = i;
                        }
                        solverVariable.usageInRowCount--;
                        this.a--;
                        return;
                    }
                    return;
                }
                if (this.e[i] < solverVariable.id) {
                    i4 = i;
                }
                i = this.f[i];
            }
            int i6 = this.i;
            i6++;
            if (this.j) {
                int[] iArr3 = this.e;
                if (iArr3[i6] != -1) {
                    i6 = iArr3.length;
                }
            }
            int[] iArr4 = this.e;
            if (i6 >= iArr4.length && this.a < iArr4.length) {
                int i7 = 0;
                while (true) {
                    int[] iArr5 = this.e;
                    if (i7 >= iArr5.length) {
                        break;
                    } else if (iArr5[i7] == -1) {
                        i6 = i7;
                        break;
                    } else {
                        i7++;
                    }
                }
            }
            int[] iArr6 = this.e;
            if (i6 >= iArr6.length) {
                i6 = iArr6.length;
                this.c *= 2;
                this.j = false;
                this.i = i6 - 1;
                this.g = Arrays.copyOf(this.g, this.c);
                this.e = Arrays.copyOf(this.e, this.c);
                this.f = Arrays.copyOf(this.f, this.c);
            }
            this.e[i6] = solverVariable.id;
            this.g[i6] = f;
            if (i4 != -1) {
                int[] iArr7 = this.f;
                iArr7[i6] = iArr7[i4];
                iArr7[i4] = i6;
            } else {
                this.f[i6] = this.h;
                this.h = i6;
            }
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.b);
            this.a++;
            if (!this.j) {
                this.i++;
            }
            int i8 = this.i;
            int[] iArr8 = this.e;
            if (i8 >= iArr8.length) {
                this.j = true;
                this.i = iArr8.length - 1;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float use(ArrayRow arrayRow, boolean z) {
        float f = get(arrayRow.a);
        remove(arrayRow.a, z);
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayRowVariables.getVariable(i);
            add(variable, arrayRowVariables.get(variable) * f, z);
        }
        return f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public final float remove(SolverVariable solverVariable, boolean z) {
        if (this.d == solverVariable) {
            this.d = null;
        }
        int i = this.h;
        if (i == -1) {
            return 0.0f;
        }
        int i2 = 0;
        int i3 = -1;
        while (i != -1 && i2 < this.a) {
            if (this.e[i] == solverVariable.id) {
                if (i == this.h) {
                    this.h = this.f[i];
                } else {
                    int[] iArr = this.f;
                    iArr[i3] = iArr[i];
                }
                if (z) {
                    solverVariable.removeFromRow(this.b);
                }
                solverVariable.usageInRowCount--;
                this.a--;
                this.e[i] = -1;
                if (this.j) {
                    this.i = i;
                }
                return this.g[i];
            }
            i = this.f[i];
            i2++;
            i3 = i;
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public final void clear() {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            SolverVariable solverVariable = this.mCache.d[this.e[i]];
            if (solverVariable != null) {
                solverVariable.removeFromRow(this.b);
            }
            i = this.f[i];
        }
        this.h = -1;
        this.i = -1;
        this.j = false;
        this.a = 0;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public boolean contains(SolverVariable solverVariable) {
        int i = this.h;
        if (i == -1) {
            return false;
        }
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            if (this.e[i] == solverVariable.id) {
                return true;
            }
            i = this.f[i];
        }
        return false;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int indexOf(SolverVariable solverVariable) {
        int i = this.h;
        if (i == -1) {
            return -1;
        }
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            if (this.e[i] == solverVariable.id) {
                return i;
            }
            i = this.f[i];
        }
        return -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void invert() {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            float[] fArr = this.g;
            fArr[i] = fArr[i] * (-1.0f);
            i = this.f[i];
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void divideByAmount(float f) {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            float[] fArr = this.g;
            fArr[i] = fArr[i] / f;
            i = this.f[i];
        }
    }

    public int getHead() {
        return this.h;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int getCurrentSize() {
        return this.a;
    }

    public final int getId(int i) {
        return this.e[i];
    }

    public final float getValue(int i) {
        return this.g[i];
    }

    public final int getNextIndice(int i) {
        return this.f[i];
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public SolverVariable getVariable(int i) {
        int i2 = this.h;
        for (int i3 = 0; i2 != -1 && i3 < this.a; i3++) {
            if (i3 == i) {
                return this.mCache.d[this.e[i2]];
            }
            i2 = this.f[i2];
        }
        return null;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float getVariableValue(int i) {
        int i2 = this.h;
        for (int i3 = 0; i2 != -1 && i3 < this.a; i3++) {
            if (i3 == i) {
                return this.g[i2];
            }
            i2 = this.f[i2];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public final float get(SolverVariable solverVariable) {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            if (this.e[i] == solverVariable.id) {
                return this.g[i];
            }
            i = this.f[i];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int sizeInBytes() {
        return (this.e.length * 4 * 3) + 0 + 36;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void display() {
        int i = this.a;
        System.out.print("{ ");
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable variable = getVariable(i2);
            if (variable != null) {
                PrintStream printStream = System.out;
                printStream.print(variable + " = " + getVariableValue(i2) + StringUtils.SPACE);
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String str = "";
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            str = ((str + " -> ") + this.g[i] + " : ") + this.mCache.d[this.e[i]];
            i = this.f[i];
        }
        return str;
    }
}
