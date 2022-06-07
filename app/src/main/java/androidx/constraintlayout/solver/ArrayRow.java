package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class ArrayRow implements LinearSystem.a {
    SolverVariable a = null;
    float b = 0.0f;
    boolean c = false;
    ArrayList<SolverVariable> d = new ArrayList<>();
    boolean e = false;
    public ArrayRowVariables variables;

    /* loaded from: classes.dex */
    public interface ArrayRowVariables {
        void add(SolverVariable solverVariable, float f, boolean z);

        void clear();

        boolean contains(SolverVariable solverVariable);

        void display();

        void divideByAmount(float f);

        float get(SolverVariable solverVariable);

        int getCurrentSize();

        SolverVariable getVariable(int i);

        float getVariableValue(int i);

        int indexOf(SolverVariable solverVariable);

        void invert();

        void put(SolverVariable solverVariable, float f);

        float remove(SolverVariable solverVariable, boolean z);

        int sizeInBytes();

        float use(ArrayRow arrayRow, boolean z);
    }

    public ArrayRow() {
    }

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    public boolean a() {
        SolverVariable solverVariable = this.a;
        return solverVariable != null && (solverVariable.d == SolverVariable.Type.UNRESTRICTED || this.b >= 0.0f);
    }

    public String toString() {
        return b();
    }

    public String b() {
        boolean z;
        float variableValue;
        int i;
        String str = (this.a == null ? "0" : "" + this.a) + " = ";
        if (this.b != 0.0f) {
            str = str + this.b;
            z = true;
        } else {
            z = false;
        }
        int currentSize = this.variables.getCurrentSize();
        for (int i2 = 0; i2 < currentSize; i2++) {
            SolverVariable variable = this.variables.getVariable(i2);
            if (!(variable == null || (variableValue = this.variables.getVariableValue(i2)) == 0.0f)) {
                String solverVariable = variable.toString();
                if (!z) {
                    if (variableValue < 0.0f) {
                        str = str + "- ";
                        variableValue *= -1.0f;
                    }
                } else if (i > 0) {
                    str = str + " + ";
                } else {
                    str = str + " - ";
                    variableValue *= -1.0f;
                }
                str = variableValue == 1.0f ? str + solverVariable : str + variableValue + StringUtils.SPACE + solverVariable;
                z = true;
            }
        }
        if (z) {
            return str;
        }
        return str + "0.0";
    }

    public void reset() {
        this.a = null;
        this.variables.clear();
        this.b = 0.0f;
        this.e = false;
    }

    public boolean a(SolverVariable solverVariable) {
        return this.variables.contains(solverVariable);
    }

    public ArrayRow a(SolverVariable solverVariable, int i) {
        this.a = solverVariable;
        float f = i;
        solverVariable.computedValue = f;
        this.b = f;
        this.e = true;
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, int i) {
        if (i < 0) {
            this.b = i * (-1);
            this.variables.put(solverVariable, 1.0f);
        } else {
            this.b = i;
            this.variables.put(solverVariable, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, SolverVariable solverVariable2, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        }
        return this;
    }

    public ArrayRow b(SolverVariable solverVariable, int i) {
        this.variables.put(solverVariable, i);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, int i, SolverVariable solverVariable2) {
        this.b = i;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
        }
        return this;
    }

    public ArrayRow createRowEqualMatchDimensions(float f, float f2, float f3, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4) {
        this.b = 0.0f;
        if (f2 == 0.0f || f == f3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else if (f == 0.0f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        } else if (f3 == 0.0f) {
            this.variables.put(solverVariable3, 1.0f);
            this.variables.put(solverVariable4, -1.0f);
        } else {
            float f4 = (f / f2) / (f3 / f2);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f4);
            this.variables.put(solverVariable3, -f4);
        }
        return this;
    }

    public ArrayRow createRowEqualDimension(float f, float f2, float f3, SolverVariable solverVariable, int i, SolverVariable solverVariable2, int i2, SolverVariable solverVariable3, int i3, SolverVariable solverVariable4, int i4) {
        if (f2 == 0.0f || f == f3) {
            this.b = ((-i) - i2) + i3 + i4;
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            float f4 = (f / f2) / (f3 / f2);
            this.b = ((-i) - i2) + (i3 * f4) + (i4 * f4);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f4);
            this.variables.put(solverVariable3, -f4);
        }
        return this;
    }

    public ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2) {
        if (solverVariable2 == solverVariable3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable2, -2.0f);
            return this;
        }
        if (f == 0.5f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            if (i > 0 || i2 > 0) {
                this.b = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.b = i;
        } else if (f >= 1.0f) {
            this.variables.put(solverVariable4, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
            this.b = -i2;
        } else {
            float f2 = 1.0f - f;
            this.variables.put(solverVariable, f2 * 1.0f);
            this.variables.put(solverVariable2, f2 * (-1.0f));
            this.variables.put(solverVariable3, (-1.0f) * f);
            this.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                this.b = ((-i) * f2) + (i2 * f);
            }
        }
        return this;
    }

    public ArrayRow addError(LinearSystem linearSystem, int i) {
        this.variables.put(linearSystem.createErrorVariable(i, "ep"), 1.0f);
        this.variables.put(linearSystem.createErrorVariable(i, "em"), -1.0f);
        return this;
    }

    public ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, f);
        return this;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f);
        this.variables.put(solverVariable3, f);
        this.variables.put(solverVariable4, -f);
        return this;
    }

    public ArrayRow createRowWithAngle(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable3, 0.5f);
        this.variables.put(solverVariable4, 0.5f);
        this.variables.put(solverVariable, -0.5f);
        this.variables.put(solverVariable2, -0.5f);
        this.b = -f;
        return this;
    }

    public int c() {
        return (this.a != null ? 4 : 0) + 4 + 4 + this.variables.sizeInBytes();
    }

    public void d() {
        float f = this.b;
        if (f < 0.0f) {
            this.b = f * (-1.0f);
            this.variables.invert();
        }
    }

    public boolean a(LinearSystem linearSystem) {
        boolean z;
        SolverVariable b = b(linearSystem);
        if (b == null) {
            z = true;
        } else {
            b(b);
            z = false;
        }
        if (this.variables.getCurrentSize() == 0) {
            this.e = true;
        }
        return z;
    }

    SolverVariable b(LinearSystem linearSystem) {
        int currentSize = this.variables.getCurrentSize();
        SolverVariable solverVariable = null;
        SolverVariable solverVariable2 = null;
        boolean z = false;
        boolean z2 = false;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < currentSize; i++) {
            float variableValue = this.variables.getVariableValue(i);
            SolverVariable variable = this.variables.getVariable(i);
            if (variable.d == SolverVariable.Type.UNRESTRICTED) {
                if (solverVariable == null) {
                    z = a(variable, linearSystem);
                    f = variableValue;
                    solverVariable = variable;
                } else if (f > variableValue) {
                    z = a(variable, linearSystem);
                    f = variableValue;
                    solverVariable = variable;
                } else if (!z && a(variable, linearSystem)) {
                    f = variableValue;
                    solverVariable = variable;
                    z = true;
                }
            } else if (solverVariable == null && variableValue < 0.0f) {
                if (solverVariable2 == null) {
                    z2 = a(variable, linearSystem);
                    f2 = variableValue;
                    solverVariable2 = variable;
                } else if (f2 > variableValue) {
                    z2 = a(variable, linearSystem);
                    f2 = variableValue;
                    solverVariable2 = variable;
                } else if (!z2 && a(variable, linearSystem)) {
                    f2 = variableValue;
                    solverVariable2 = variable;
                    z2 = true;
                }
            }
        }
        return solverVariable != null ? solverVariable : solverVariable2;
    }

    private boolean a(SolverVariable solverVariable, LinearSystem linearSystem) {
        return solverVariable.usageInRowCount <= 1;
    }

    public void b(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.a;
        if (solverVariable2 != null) {
            this.variables.put(solverVariable2, -1.0f);
            this.a.a = -1;
            this.a = null;
        }
        float remove = this.variables.remove(solverVariable, true) * (-1.0f);
        this.a = solverVariable;
        if (remove != 1.0f) {
            this.b /= remove;
            this.variables.divideByAmount(remove);
        }
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public boolean isEmpty() {
        return this.a == null && this.b == 0.0f && this.variables.getCurrentSize() == 0;
    }

    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z) {
        this.b += arrayRow.b * this.variables.use(arrayRow, z);
        if (z) {
            arrayRow.a.removeFromRow(this);
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS && this.a != null && this.variables.getCurrentSize() == 0) {
            this.e = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    public void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z) {
        if (solverVariable.isFinalValue) {
            this.b += solverVariable.computedValue * this.variables.get(solverVariable);
            this.variables.remove(solverVariable, z);
            if (z) {
                solverVariable.removeFromRow(this);
            }
            if (LinearSystem.SIMPLIFY_SYNONYMS && solverVariable != null && this.variables.getCurrentSize() == 0) {
                this.e = true;
                linearSystem.hasSimpleDefinition = true;
            }
        }
    }

    public void updateFromSynonymVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z) {
        if (solverVariable.g) {
            float f = this.variables.get(solverVariable);
            this.b += solverVariable.i * f;
            this.variables.remove(solverVariable, z);
            if (z) {
                solverVariable.removeFromRow(this);
            }
            this.variables.add(linearSystem.e.d[solverVariable.h], f, z);
            if (LinearSystem.SIMPLIFY_SYNONYMS && solverVariable != null && this.variables.getCurrentSize() == 0) {
                this.e = true;
                linearSystem.hasSimpleDefinition = true;
            }
        }
    }

    private SolverVariable a(boolean[] zArr, SolverVariable solverVariable) {
        int currentSize = this.variables.getCurrentSize();
        SolverVariable solverVariable2 = null;
        float f = 0.0f;
        for (int i = 0; i < currentSize; i++) {
            float variableValue = this.variables.getVariableValue(i);
            if (variableValue < 0.0f) {
                SolverVariable variable = this.variables.getVariable(i);
                if ((zArr == null || !zArr[variable.id]) && variable != solverVariable && ((variable.d == SolverVariable.Type.SLACK || variable.d == SolverVariable.Type.ERROR) && variableValue < f)) {
                    f = variableValue;
                    solverVariable2 = variable;
                }
            }
        }
        return solverVariable2;
    }

    public SolverVariable pickPivot(SolverVariable solverVariable) {
        return a((boolean[]) null, solverVariable);
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr) {
        return a(zArr, (SolverVariable) null);
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public void clear() {
        this.variables.clear();
        this.a = null;
        this.b = 0.0f;
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public void initFromRow(LinearSystem.a aVar) {
        if (aVar instanceof ArrayRow) {
            ArrayRow arrayRow = (ArrayRow) aVar;
            this.a = null;
            this.variables.clear();
            for (int i = 0; i < arrayRow.variables.getCurrentSize(); i++) {
                this.variables.add(arrayRow.variables.getVariable(i), arrayRow.variables.getVariableValue(i), true);
            }
        }
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public void addError(SolverVariable solverVariable) {
        float f = 1.0f;
        if (solverVariable.strength != 1) {
            if (solverVariable.strength == 2) {
                f = 1000.0f;
            } else if (solverVariable.strength == 3) {
                f = 1000000.0f;
            } else if (solverVariable.strength == 4) {
                f = 1.0E9f;
            } else if (solverVariable.strength == 5) {
                f = 1.0E12f;
            }
        }
        this.variables.put(solverVariable, f);
    }

    @Override // androidx.constraintlayout.solver.LinearSystem.a
    public SolverVariable getKey() {
        return this.a;
    }

    public void updateFromSystem(LinearSystem linearSystem) {
        if (linearSystem.b.length != 0) {
            boolean z = false;
            while (!z) {
                int currentSize = this.variables.getCurrentSize();
                for (int i = 0; i < currentSize; i++) {
                    SolverVariable variable = this.variables.getVariable(i);
                    if (variable.a != -1 || variable.isFinalValue || variable.g) {
                        this.d.add(variable);
                    }
                }
                int size = this.d.size();
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        SolverVariable solverVariable = this.d.get(i2);
                        if (solverVariable.isFinalValue) {
                            updateFromFinalVariable(linearSystem, solverVariable, true);
                        } else if (solverVariable.g) {
                            updateFromSynonymVariable(linearSystem, solverVariable, true);
                        } else {
                            updateFromRow(linearSystem, linearSystem.b[solverVariable.a], true);
                        }
                    }
                    this.d.clear();
                } else {
                    z = true;
                }
            }
            if (LinearSystem.SIMPLIFY_SYNONYMS && this.a != null && this.variables.getCurrentSize() == 0) {
                this.e = true;
                linearSystem.hasSimpleDefinition = true;
            }
        }
    }
}
