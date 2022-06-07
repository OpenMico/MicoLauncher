package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class LinearSystem {
    public static long ARRAY_ROW_CREATION = 0;
    public static final boolean DEBUG = false;
    public static final boolean FULL_DEBUG = false;
    public static final boolean MEASURE = false;
    public static long OPTIMIZED_ARRAY_ROW_CREATION = 0;
    public static boolean OPTIMIZED_ENGINE = false;
    public static boolean SIMPLIFY_SYNONYMS = true;
    public static boolean SKIP_COLUMNS = true;
    public static boolean USE_BASIC_SYNONYMS = true;
    public static boolean USE_DEPENDENCY_ORDERING = false;
    public static boolean USE_SYNONYMS = true;
    private static int f = 1000;
    public static Metrics sMetrics;
    ArrayRow[] b;
    private int j;
    private boolean[] k;
    private int l;
    private a o;
    public boolean hasSimpleDefinition = false;
    int a = 0;
    private HashMap<String, SolverVariable> g = null;
    private int i = 32;
    public boolean graphOptimizer = false;
    public boolean newgraphOptimizer = false;
    int c = 1;
    int d = 0;
    private SolverVariable[] m = new SolverVariable[f];
    private int n = 0;
    final Cache e = new Cache();
    private a h = new PriorityGoalRow(this.e);

    /* loaded from: classes.dex */
    public interface a {
        void addError(SolverVariable solverVariable);

        void clear();

        SolverVariable getKey();

        SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr);

        void initFromRow(a aVar);

        boolean isEmpty();
    }

    /* loaded from: classes.dex */
    public class b extends ArrayRow {
        public b(Cache cache) {
            LinearSystem.this = r1;
            this.variables = new SolverVariableValues(this, cache);
        }
    }

    public LinearSystem() {
        int i = this.i;
        this.j = i;
        this.b = null;
        this.k = new boolean[i];
        this.l = i;
        this.b = new ArrayRow[i];
        b();
        if (OPTIMIZED_ENGINE) {
            this.o = new b(this.e);
        } else {
            this.o = new ArrayRow(this.e);
        }
    }

    public void fillMetrics(Metrics metrics) {
        sMetrics = metrics;
    }

    public static Metrics getMetrics() {
        return sMetrics;
    }

    private void a() {
        this.i *= 2;
        this.b = (ArrayRow[]) Arrays.copyOf(this.b, this.i);
        Cache cache = this.e;
        cache.d = (SolverVariable[]) Arrays.copyOf(cache.d, this.i);
        int i = this.i;
        this.k = new boolean[i];
        this.j = i;
        this.l = i;
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.tableSizeIncrease++;
            Metrics metrics2 = sMetrics;
            metrics2.maxTableSize = Math.max(metrics2.maxTableSize, this.i);
            Metrics metrics3 = sMetrics;
            metrics3.lastTableSize = metrics3.maxTableSize;
        }
    }

    private void b() {
        int i = 0;
        if (OPTIMIZED_ENGINE) {
            while (i < this.d) {
                ArrayRow arrayRow = this.b[i];
                if (arrayRow != null) {
                    this.e.a.a(arrayRow);
                }
                this.b[i] = null;
                i++;
            }
            return;
        }
        while (i < this.d) {
            ArrayRow arrayRow2 = this.b[i];
            if (arrayRow2 != null) {
                this.e.b.a(arrayRow2);
            }
            this.b[i] = null;
            i++;
        }
    }

    public void reset() {
        for (int i = 0; i < this.e.d.length; i++) {
            SolverVariable solverVariable = this.e.d[i];
            if (solverVariable != null) {
                solverVariable.reset();
            }
        }
        this.e.c.a(this.m, this.n);
        this.n = 0;
        Arrays.fill(this.e.d, (Object) null);
        HashMap<String, SolverVariable> hashMap = this.g;
        if (hashMap != null) {
            hashMap.clear();
        }
        this.a = 0;
        this.h.clear();
        this.c = 1;
        for (int i2 = 0; i2 < this.d; i2++) {
            ArrayRow[] arrayRowArr = this.b;
            if (arrayRowArr[i2] != null) {
                arrayRowArr[i2].c = false;
            }
        }
        b();
        this.d = 0;
        if (OPTIMIZED_ENGINE) {
            this.o = new b(this.e);
        } else {
            this.o = new ArrayRow(this.e);
        }
    }

    public SolverVariable createObjectVariable(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.getSolverVariable();
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable(this.e);
                solverVariable = constraintAnchor.getSolverVariable();
            }
            if (solverVariable.id == -1 || solverVariable.id > this.a || this.e.d[solverVariable.id] == null) {
                if (solverVariable.id != -1) {
                    solverVariable.reset();
                }
                this.a++;
                this.c++;
                solverVariable.id = this.a;
                solverVariable.d = SolverVariable.Type.UNRESTRICTED;
                this.e.d[this.a] = solverVariable;
            }
        }
        return solverVariable;
    }

    public ArrayRow createRow() {
        ArrayRow arrayRow;
        if (OPTIMIZED_ENGINE) {
            arrayRow = this.e.a.a();
            if (arrayRow == null) {
                arrayRow = new b(this.e);
                OPTIMIZED_ARRAY_ROW_CREATION++;
            } else {
                arrayRow.reset();
            }
        } else {
            arrayRow = this.e.b.a();
            if (arrayRow == null) {
                arrayRow = new ArrayRow(this.e);
                ARRAY_ROW_CREATION++;
            } else {
                arrayRow.reset();
            }
        }
        SolverVariable.a();
        return arrayRow;
    }

    public SolverVariable createSlackVariable() {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.slackvariables++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(SolverVariable.Type.SLACK, (String) null);
        this.a++;
        this.c++;
        a2.id = this.a;
        this.e.d[this.a] = a2;
        return a2;
    }

    public SolverVariable createExtraVariable() {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.extravariables++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(SolverVariable.Type.SLACK, (String) null);
        this.a++;
        this.c++;
        a2.id = this.a;
        this.e.d[this.a] = a2;
        return a2;
    }

    void a(ArrayRow arrayRow, int i, int i2) {
        arrayRow.b(createErrorVariable(i2, null), i);
    }

    public SolverVariable createErrorVariable(int i, String str) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.errors++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(SolverVariable.Type.ERROR, str);
        this.a++;
        this.c++;
        a2.id = this.a;
        a2.strength = i;
        this.e.d[this.a] = a2;
        this.h.addError(a2);
        return a2;
    }

    private SolverVariable a(SolverVariable.Type type, String str) {
        SolverVariable a2 = this.e.c.a();
        if (a2 == null) {
            a2 = new SolverVariable(type, str);
            a2.setType(type, str);
        } else {
            a2.reset();
            a2.setType(type, str);
        }
        int i = this.n;
        int i2 = f;
        if (i >= i2) {
            f = i2 * 2;
            this.m = (SolverVariable[]) Arrays.copyOf(this.m, f);
        }
        SolverVariable[] solverVariableArr = this.m;
        int i3 = this.n;
        this.n = i3 + 1;
        solverVariableArr[i3] = a2;
        return a2;
    }

    public int getObjectVariableValue(Object obj) {
        SolverVariable solverVariable = ((ConstraintAnchor) obj).getSolverVariable();
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    public void minimize() throws Exception {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.minimize++;
        }
        if (this.h.isEmpty()) {
            c();
        } else if (this.graphOptimizer || this.newgraphOptimizer) {
            Metrics metrics2 = sMetrics;
            if (metrics2 != null) {
                metrics2.graphOptimizer++;
            }
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= this.d) {
                    z = true;
                    break;
                } else if (!this.b[i].e) {
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                a(this.h);
                return;
            }
            Metrics metrics3 = sMetrics;
            if (metrics3 != null) {
                metrics3.fullySolved++;
            }
            c();
        } else {
            a(this.h);
        }
    }

    void a(a aVar) throws Exception {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.minimizeGoal++;
            Metrics metrics2 = sMetrics;
            metrics2.maxVariables = Math.max(metrics2.maxVariables, this.c);
            Metrics metrics3 = sMetrics;
            metrics3.maxRows = Math.max(metrics3.maxRows, this.d);
        }
        b(aVar);
        a(aVar, false);
        c();
    }

    public void addConstraint(ArrayRow arrayRow) {
        SolverVariable pickPivot;
        if (arrayRow != null) {
            Metrics metrics = sMetrics;
            if (metrics != null) {
                metrics.constraints++;
                if (arrayRow.e) {
                    sMetrics.simpleconstraints++;
                }
            }
            if (this.d + 1 >= this.l || this.c + 1 >= this.j) {
                a();
            }
            boolean z = false;
            if (!arrayRow.e) {
                arrayRow.updateFromSystem(this);
                if (!arrayRow.isEmpty()) {
                    arrayRow.d();
                    if (arrayRow.a(this)) {
                        SolverVariable createExtraVariable = createExtraVariable();
                        arrayRow.a = createExtraVariable;
                        int i = this.d;
                        a(arrayRow);
                        if (this.d == i + 1) {
                            this.o.initFromRow(arrayRow);
                            a(this.o, true);
                            if (createExtraVariable.a == -1) {
                                if (arrayRow.a == createExtraVariable && (pickPivot = arrayRow.pickPivot(createExtraVariable)) != null) {
                                    Metrics metrics2 = sMetrics;
                                    if (metrics2 != null) {
                                        metrics2.pivots++;
                                    }
                                    arrayRow.b(pickPivot);
                                }
                                if (!arrayRow.e) {
                                    arrayRow.a.updateReferencesWithNewDefinition(this, arrayRow);
                                }
                                if (OPTIMIZED_ENGINE) {
                                    this.e.a.a(arrayRow);
                                } else {
                                    this.e.b.a(arrayRow);
                                }
                                this.d--;
                            }
                            z = true;
                        }
                    }
                    if (!arrayRow.a()) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (!z) {
                a(arrayRow);
            }
        }
    }

    private final void a(ArrayRow arrayRow) {
        int i;
        if (!SIMPLIFY_SYNONYMS || !arrayRow.e) {
            this.b[this.d] = arrayRow;
            SolverVariable solverVariable = arrayRow.a;
            int i2 = this.d;
            solverVariable.a = i2;
            this.d = i2 + 1;
            arrayRow.a.updateReferencesWithNewDefinition(this, arrayRow);
        } else {
            arrayRow.a.setFinalValue(this, arrayRow.b);
        }
        if (SIMPLIFY_SYNONYMS && this.hasSimpleDefinition) {
            int i3 = 0;
            while (i3 < this.d) {
                if (this.b[i3] == null) {
                    System.out.println("WTF");
                }
                ArrayRow[] arrayRowArr = this.b;
                if (arrayRowArr[i3] != null && arrayRowArr[i3].e) {
                    ArrayRow arrayRow2 = this.b[i3];
                    arrayRow2.a.setFinalValue(this, arrayRow2.b);
                    if (OPTIMIZED_ENGINE) {
                        this.e.a.a(arrayRow2);
                    } else {
                        this.e.b.a(arrayRow2);
                    }
                    this.b[i3] = null;
                    int i4 = i3 + 1;
                    int i5 = i4;
                    while (true) {
                        i = this.d;
                        if (i4 >= i) {
                            break;
                        }
                        ArrayRow[] arrayRowArr2 = this.b;
                        int i6 = i4 - 1;
                        arrayRowArr2[i6] = arrayRowArr2[i4];
                        if (arrayRowArr2[i6].a.a == i4) {
                            this.b[i6].a.a = i6;
                        }
                        i4++;
                        i5 = i4;
                    }
                    if (i5 < i) {
                        this.b[i5] = null;
                    }
                    this.d--;
                    i3--;
                }
                i3++;
            }
            this.hasSimpleDefinition = false;
        }
    }

    public void removeRow(ArrayRow arrayRow) {
        int i;
        if (arrayRow.e && arrayRow.a != null) {
            if (arrayRow.a.a != -1) {
                int i2 = arrayRow.a.a;
                while (true) {
                    i = this.d;
                    if (i2 >= i - 1) {
                        break;
                    }
                    int i3 = i2 + 1;
                    SolverVariable solverVariable = this.b[i3].a;
                    if (solverVariable.a == i3) {
                        solverVariable.a = i2;
                    }
                    ArrayRow[] arrayRowArr = this.b;
                    arrayRowArr[i2] = arrayRowArr[i3];
                    i2 = i3;
                }
                this.d = i - 1;
            }
            if (!arrayRow.a.isFinalValue) {
                arrayRow.a.setFinalValue(this, arrayRow.b);
            }
            if (OPTIMIZED_ENGINE) {
                this.e.a.a(arrayRow);
            } else {
                this.e.b.a(arrayRow);
            }
        }
    }

    private final int a(a aVar, boolean z) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.optimize++;
        }
        for (int i = 0; i < this.c; i++) {
            this.k[i] = false;
        }
        boolean z2 = false;
        int i2 = 0;
        while (!z2) {
            Metrics metrics2 = sMetrics;
            if (metrics2 != null) {
                metrics2.iterations++;
            }
            i2++;
            if (i2 >= this.c * 2) {
                return i2;
            }
            if (aVar.getKey() != null) {
                this.k[aVar.getKey().id] = true;
            }
            SolverVariable pivotCandidate = aVar.getPivotCandidate(this, this.k);
            if (pivotCandidate != null) {
                if (this.k[pivotCandidate.id]) {
                    return i2;
                }
                this.k[pivotCandidate.id] = true;
            }
            if (pivotCandidate != null) {
                float f2 = Float.MAX_VALUE;
                int i3 = -1;
                for (int i4 = 0; i4 < this.d; i4++) {
                    ArrayRow arrayRow = this.b[i4];
                    if (arrayRow.a.d != SolverVariable.Type.UNRESTRICTED && !arrayRow.e && arrayRow.a(pivotCandidate)) {
                        float f3 = arrayRow.variables.get(pivotCandidate);
                        if (f3 < 0.0f) {
                            float f4 = (-arrayRow.b) / f3;
                            if (f4 < f2) {
                                i3 = i4;
                                f2 = f4;
                            }
                        }
                    }
                }
                if (i3 > -1) {
                    ArrayRow arrayRow2 = this.b[i3];
                    arrayRow2.a.a = -1;
                    Metrics metrics3 = sMetrics;
                    if (metrics3 != null) {
                        metrics3.pivots++;
                    }
                    arrayRow2.b(pivotCandidate);
                    arrayRow2.a.a = i3;
                    arrayRow2.a.updateReferencesWithNewDefinition(this, arrayRow2);
                }
            } else {
                z2 = true;
            }
        }
        return i2;
    }

    private int b(a aVar) throws Exception {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= this.d) {
                z = false;
                break;
            } else if (this.b[i].a.d != SolverVariable.Type.UNRESTRICTED && this.b[i].b < 0.0f) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            return 0;
        }
        boolean z2 = false;
        int i2 = 0;
        while (!z2) {
            Metrics metrics = sMetrics;
            if (metrics != null) {
                metrics.bfs++;
            }
            i2++;
            float f2 = Float.MAX_VALUE;
            int i3 = -1;
            int i4 = -1;
            int i5 = 0;
            for (int i6 = 0; i6 < this.d; i6++) {
                ArrayRow arrayRow = this.b[i6];
                if (arrayRow.a.d != SolverVariable.Type.UNRESTRICTED && !arrayRow.e && arrayRow.b < 0.0f) {
                    int i7 = 9;
                    if (SKIP_COLUMNS) {
                        int currentSize = arrayRow.variables.getCurrentSize();
                        int i8 = i5;
                        float f3 = f2;
                        int i9 = i4;
                        int i10 = i3;
                        int i11 = 0;
                        while (i11 < currentSize) {
                            SolverVariable variable = arrayRow.variables.getVariable(i11);
                            float f4 = arrayRow.variables.get(variable);
                            if (f4 > 0.0f) {
                                int i12 = i8;
                                int i13 = 0;
                                while (i13 < i7) {
                                    float f5 = variable.b[i13] / f4;
                                    if ((f5 < f3 && i13 == i12) || i13 > i12) {
                                        i10 = i6;
                                        i9 = variable.id;
                                        f3 = f5;
                                        i12 = i13;
                                    }
                                    i13++;
                                    i7 = 9;
                                }
                                i8 = i12;
                            }
                            i11++;
                            i7 = 9;
                        }
                        i3 = i10;
                        i4 = i9;
                        f2 = f3;
                        i5 = i8;
                    } else {
                        for (int i14 = 1; i14 < this.c; i14++) {
                            SolverVariable solverVariable = this.e.d[i14];
                            float f6 = arrayRow.variables.get(solverVariable);
                            if (f6 > 0.0f) {
                                for (int i15 = 0; i15 < 9; i15++) {
                                    float f7 = solverVariable.b[i15] / f6;
                                    if ((f7 < f2 && i15 == i5) || i15 > i5) {
                                        i4 = i14;
                                        i3 = i6;
                                        i5 = i15;
                                        f2 = f7;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (i3 != -1) {
                ArrayRow arrayRow2 = this.b[i3];
                arrayRow2.a.a = -1;
                Metrics metrics2 = sMetrics;
                if (metrics2 != null) {
                    metrics2.pivots++;
                }
                arrayRow2.b(this.e.d[i4]);
                arrayRow2.a.a = i3;
                arrayRow2.a.updateReferencesWithNewDefinition(this, arrayRow2);
            } else {
                z2 = true;
            }
            if (i2 > this.c / 2) {
                z2 = true;
            }
        }
        return i2;
    }

    private void c() {
        for (int i = 0; i < this.d; i++) {
            ArrayRow arrayRow = this.b[i];
            arrayRow.a.computedValue = arrayRow.b;
        }
    }

    public void displayReadableRows() {
        d();
        String str = " num vars " + this.a + "\n";
        for (int i = 0; i < this.a + 1; i++) {
            SolverVariable solverVariable = this.e.d[i];
            if (solverVariable != null && solverVariable.isFinalValue) {
                str = str + " $[" + i + "] => " + solverVariable + " = " + solverVariable.computedValue + "\n";
            }
        }
        String str2 = str + "\n";
        for (int i2 = 0; i2 < this.a + 1; i2++) {
            SolverVariable solverVariable2 = this.e.d[i2];
            if (solverVariable2 != null && solverVariable2.g) {
                str2 = str2 + " ~[" + i2 + "] => " + solverVariable2 + " = " + this.e.d[solverVariable2.h] + " + " + solverVariable2.i + "\n";
            }
        }
        String str3 = str2 + "\n\n #  ";
        for (int i3 = 0; i3 < this.d; i3++) {
            str3 = (str3 + this.b[i3].b()) + "\n #  ";
        }
        if (this.h != null) {
            str3 = str3 + "Goal: " + this.h + "\n";
        }
        System.out.println(str3);
    }

    public void displayVariablesReadableRows() {
        d();
        String str = "";
        for (int i = 0; i < this.d; i++) {
            if (this.b[i].a.d == SolverVariable.Type.UNRESTRICTED) {
                str = (str + this.b[i].b()) + "\n";
            }
        }
        System.out.println(str + this.h + "\n");
    }

    public int getMemoryUsed() {
        int i = 0;
        for (int i2 = 0; i2 < this.d; i2++) {
            ArrayRow[] arrayRowArr = this.b;
            if (arrayRowArr[i2] != null) {
                i += arrayRowArr[i2].c();
            }
        }
        return i;
    }

    public int getNumEquations() {
        return this.d;
    }

    public int getNumVariables() {
        return this.a;
    }

    private void d() {
        System.out.println("Display Rows (" + this.d + "x" + this.c + ")\n");
    }

    public Cache getCache() {
        return this.e;
    }

    public void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 8) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * (-1.0f)), i2);
        }
        addConstraint(createRow);
    }

    public void addGreaterBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int i, boolean z) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i);
        addConstraint(createRow);
    }

    public void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 8) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * (-1.0f)), i2);
        }
        addConstraint(createRow);
    }

    public void addLowerBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int i, boolean z) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i);
        addConstraint(createRow);
    }

    public void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2, int i3) {
        ArrayRow createRow = createRow();
        createRow.a(solverVariable, solverVariable2, i, f2, solverVariable3, solverVariable4, i2);
        if (i3 != 8) {
            createRow.addError(this, i3);
        }
        addConstraint(createRow);
    }

    public void addRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2, int i) {
        ArrayRow createRow = createRow();
        createRow.createRowDimensionRatio(solverVariable, solverVariable2, solverVariable3, solverVariable4, f2);
        if (i != 8) {
            createRow.addError(this, i);
        }
        addConstraint(createRow);
    }

    public void addSynonym(SolverVariable solverVariable, SolverVariable solverVariable2, int i) {
        if (solverVariable.a == -1 && i == 0) {
            if (solverVariable2.g) {
                float f2 = solverVariable2.i;
                solverVariable2 = this.e.d[solverVariable2.h];
            }
            if (solverVariable.g) {
                float f3 = solverVariable.i;
                SolverVariable solverVariable3 = this.e.d[solverVariable.h];
                return;
            }
            solverVariable.setSynonym(this, solverVariable2, 0.0f);
            return;
        }
        addEquality(solverVariable, solverVariable2, i, 8);
    }

    public ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        if (!USE_BASIC_SYNONYMS || i2 != 8 || !solverVariable2.isFinalValue || solverVariable.a != -1) {
            ArrayRow createRow = createRow();
            createRow.createRowEquals(solverVariable, solverVariable2, i);
            if (i2 != 8) {
                createRow.addError(this, i2);
            }
            addConstraint(createRow);
            return createRow;
        }
        solverVariable.setFinalValue(this, solverVariable2.computedValue + i);
        return null;
    }

    public void addEquality(SolverVariable solverVariable, int i) {
        if (!USE_BASIC_SYNONYMS || solverVariable.a != -1) {
            int i2 = solverVariable.a;
            if (solverVariable.a != -1) {
                ArrayRow arrayRow = this.b[i2];
                if (arrayRow.e) {
                    arrayRow.b = i;
                } else if (arrayRow.variables.getCurrentSize() == 0) {
                    arrayRow.e = true;
                    arrayRow.b = i;
                } else {
                    ArrayRow createRow = createRow();
                    createRow.createRowEquals(solverVariable, i);
                    addConstraint(createRow);
                }
            } else {
                ArrayRow createRow2 = createRow();
                createRow2.a(solverVariable, i);
                addConstraint(createRow2);
            }
        } else {
            float f2 = i;
            solverVariable.setFinalValue(this, f2);
            for (int i3 = 0; i3 < this.a + 1; i3++) {
                SolverVariable solverVariable2 = this.e.d[i3];
                if (solverVariable2 != null && solverVariable2.g && solverVariable2.h == solverVariable.id) {
                    solverVariable2.setFinalValue(this, solverVariable2.i + f2);
                }
            }
        }
    }

    public static ArrayRow createRowDimensionPercent(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, float f2) {
        return linearSystem.createRow().a(solverVariable, solverVariable2, f2);
    }

    public void addCenterPoint(ConstraintWidget constraintWidget, ConstraintWidget constraintWidget2, float f2, int i) {
        SolverVariable createObjectVariable = createObjectVariable(constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable createObjectVariable2 = createObjectVariable(constraintWidget.getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable createObjectVariable3 = createObjectVariable(constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT));
        SolverVariable createObjectVariable4 = createObjectVariable(constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM));
        SolverVariable createObjectVariable5 = createObjectVariable(constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable createObjectVariable6 = createObjectVariable(constraintWidget2.getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable createObjectVariable7 = createObjectVariable(constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT));
        SolverVariable createObjectVariable8 = createObjectVariable(constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM));
        ArrayRow createRow = createRow();
        double d = f2;
        double d2 = i;
        createRow.createRowWithAngle(createObjectVariable2, createObjectVariable4, createObjectVariable6, createObjectVariable8, (float) (Math.sin(d) * d2));
        addConstraint(createRow);
        ArrayRow createRow2 = createRow();
        createRow2.createRowWithAngle(createObjectVariable, createObjectVariable3, createObjectVariable5, createObjectVariable7, (float) (Math.cos(d) * d2));
        addConstraint(createRow2);
    }
}
