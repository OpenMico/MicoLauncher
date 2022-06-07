package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.io.PrintStream;
import java.util.HashMap;

/* loaded from: classes.dex */
public class DesignTool {
    static final HashMap<Pair<Integer, Integer>, String> a = new HashMap<>();
    static final HashMap<String, String> b = new HashMap<>();
    private final MotionLayout c;
    private MotionScene d;
    private String e = null;
    private String f = null;
    private int g = -1;
    private int h = -1;

    public DesignTool(MotionLayout motionLayout) {
        this.c = motionLayout;
    }

    static {
        a.put(Pair.create(4, 4), "layout_constraintBottom_toBottomOf");
        a.put(Pair.create(4, 3), "layout_constraintBottom_toTopOf");
        a.put(Pair.create(3, 4), "layout_constraintTop_toBottomOf");
        a.put(Pair.create(3, 3), "layout_constraintTop_toTopOf");
        a.put(Pair.create(6, 6), "layout_constraintStart_toStartOf");
        a.put(Pair.create(6, 7), "layout_constraintStart_toEndOf");
        a.put(Pair.create(7, 6), "layout_constraintEnd_toStartOf");
        a.put(Pair.create(7, 7), "layout_constraintEnd_toEndOf");
        a.put(Pair.create(1, 1), "layout_constraintLeft_toLeftOf");
        a.put(Pair.create(1, 2), "layout_constraintLeft_toRightOf");
        a.put(Pair.create(2, 2), "layout_constraintRight_toRightOf");
        a.put(Pair.create(2, 1), "layout_constraintRight_toLeftOf");
        a.put(Pair.create(5, 5), "layout_constraintBaseline_toBaselineOf");
        b.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
        b.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
        b.put("layout_constraintTop_toBottomOf", "layout_marginTop");
        b.put("layout_constraintTop_toTopOf", "layout_marginTop");
        b.put("layout_constraintStart_toStartOf", "layout_marginStart");
        b.put("layout_constraintStart_toEndOf", "layout_marginStart");
        b.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
        b.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
        b.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
        b.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
        b.put("layout_constraintRight_toRightOf", "layout_marginRight");
        b.put("layout_constraintRight_toLeftOf", "layout_marginRight");
    }

    private static int a(int i, String str) {
        int indexOf;
        if (str == null || (indexOf = str.indexOf(100)) == -1) {
            return 0;
        }
        return (int) ((Integer.valueOf(str.substring(0, indexOf)).intValue() * i) / 160.0f);
    }

    private static void a(int i, ConstraintSet constraintSet, View view, HashMap<String, String> hashMap, int i2, int i3) {
        String str = a.get(Pair.create(Integer.valueOf(i2), Integer.valueOf(i3)));
        String str2 = hashMap.get(str);
        if (str2 != null) {
            int i4 = 0;
            String str3 = b.get(str);
            if (str3 != null) {
                i4 = a(i, hashMap.get(str3));
            }
            constraintSet.connect(view.getId(), i2, Integer.parseInt(str2), i3, i4);
        }
    }

    private static void a(ConstraintSet constraintSet, View view, HashMap<String, String> hashMap, int i) {
        String str = "layout_constraintHorizontal_bias";
        if (i == 1) {
            str = "layout_constraintVertical_bias";
        }
        String str2 = hashMap.get(str);
        if (str2 == null) {
            return;
        }
        if (i == 0) {
            constraintSet.setHorizontalBias(view.getId(), Float.parseFloat(str2));
        } else if (i == 1) {
            constraintSet.setVerticalBias(view.getId(), Float.parseFloat(str2));
        }
    }

    private static void a(int i, ConstraintSet constraintSet, View view, HashMap<String, String> hashMap, int i2) {
        String str = "layout_width";
        if (i2 == 1) {
            str = "layout_height";
        }
        String str2 = hashMap.get(str);
        if (str2 != null) {
            int i3 = -2;
            if (!str2.equalsIgnoreCase("wrap_content")) {
                i3 = a(i, str2);
            }
            if (i2 == 0) {
                constraintSet.constrainWidth(view.getId(), i3);
            } else {
                constraintSet.constrainHeight(view.getId(), i3);
            }
        }
    }

    private static void a(int i, ConstraintSet constraintSet, View view, HashMap<String, String> hashMap) {
        String str = hashMap.get("layout_editor_absoluteX");
        if (str != null) {
            constraintSet.setEditorAbsoluteX(view.getId(), a(i, str));
        }
        String str2 = hashMap.get("layout_editor_absoluteY");
        if (str2 != null) {
            constraintSet.setEditorAbsoluteY(view.getId(), a(i, str2));
        }
    }

    public int getAnimationPath(Object obj, float[] fArr, int i) {
        if (this.c.a == null) {
            return -1;
        }
        MotionController motionController = this.c.e.get(obj);
        if (motionController == null) {
            return 0;
        }
        motionController.a(fArr, i);
        return i;
    }

    public void getAnimationRectangles(Object obj, float[] fArr) {
        if (this.c.a != null) {
            int duration = this.c.a.getDuration() / 16;
            MotionController motionController = this.c.e.get(obj);
            if (motionController != null) {
                motionController.b(fArr, duration);
            }
        }
    }

    public int getAnimationKeyFrames(Object obj, float[] fArr) {
        if (this.c.a == null) {
            return -1;
        }
        int duration = this.c.a.getDuration() / 16;
        MotionController motionController = this.c.e.get(obj);
        if (motionController == null) {
            return 0;
        }
        motionController.a(fArr, (int[]) null);
        return duration;
    }

    public void setToolPosition(float f) {
        if (this.c.a == null) {
            this.c.a = this.d;
        }
        this.c.setProgress(f);
        this.c.a(true);
        this.c.requestLayout();
        this.c.invalidate();
    }

    public void setState(String str) {
        if (str == null) {
            str = "motion_base";
        }
        if (this.e != str) {
            this.e = str;
            this.f = null;
            if (this.c.a == null) {
                this.c.a = this.d;
            }
            int a2 = str != null ? this.c.a(str) : R.id.motion_base;
            this.g = a2;
            if (a2 != 0) {
                if (a2 == this.c.getStartState()) {
                    this.c.setProgress(0.0f);
                } else if (a2 == this.c.getEndState()) {
                    this.c.setProgress(1.0f);
                } else {
                    this.c.transitionToState(a2);
                    this.c.setProgress(1.0f);
                }
            }
            this.c.requestLayout();
        }
    }

    public String getStartState() {
        int startState = this.c.getStartState();
        if (this.g == startState) {
            return this.e;
        }
        String a2 = this.c.a(startState);
        if (a2 != null) {
            this.e = a2;
            this.g = startState;
        }
        return this.c.a(startState);
    }

    public String getEndState() {
        int endState = this.c.getEndState();
        if (this.h == endState) {
            return this.f;
        }
        String a2 = this.c.a(endState);
        if (a2 != null) {
            this.f = a2;
            this.h = endState;
        }
        return a2;
    }

    public float getProgress() {
        return this.c.getProgress();
    }

    public String getState() {
        if (!(this.e == null || this.f == null)) {
            float progress = getProgress();
            if (progress <= 0.01f) {
                return this.e;
            }
            if (progress >= 0.99f) {
                return this.f;
            }
        }
        return this.e;
    }

    public boolean isInTransition() {
        return (this.e == null || this.f == null) ? false : true;
    }

    public void setTransition(String str, String str2) {
        if (this.c.a == null) {
            this.c.a = this.d;
        }
        int a2 = this.c.a(str);
        int a3 = this.c.a(str2);
        this.c.setTransition(a2, a3);
        this.g = a2;
        this.h = a3;
        this.e = str;
        this.f = str2;
    }

    public void disableAutoTransition(boolean z) {
        this.c.b(z);
    }

    public long getTransitionTimeMs() {
        return this.c.getTransitionTimeMs();
    }

    public int getKeyFramePositions(Object obj, int[] iArr, float[] fArr) {
        MotionController motionController = this.c.e.get((View) obj);
        if (motionController == null) {
            return 0;
        }
        return motionController.getkeyFramePositions(iArr, fArr);
    }

    public int getKeyFrameInfo(Object obj, int i, int[] iArr) {
        MotionController motionController = this.c.e.get((View) obj);
        if (motionController == null) {
            return 0;
        }
        return motionController.getKeyFrameInfo(i, iArr);
    }

    public float getKeyFramePosition(Object obj, int i, float f, float f2) {
        return this.c.e.get((View) obj).a(i, f, f2);
    }

    public void setKeyFrame(Object obj, int i, String str, Object obj2) {
        if (this.c.a != null) {
            this.c.a.setKeyframe((View) obj, i, str, obj2);
            MotionLayout motionLayout = this.c;
            motionLayout.h = i / 100.0f;
            motionLayout.g = 0.0f;
            motionLayout.rebuildScene();
            this.c.a(true);
        }
    }

    public boolean setKeyFramePosition(Object obj, int i, int i2, float f, float f2) {
        if (this.c.a == null) {
            return false;
        }
        MotionController motionController = this.c.e.get(obj);
        int i3 = (int) (this.c.f * 100.0f);
        if (motionController == null) {
            return false;
        }
        View view = (View) obj;
        if (!this.c.a.a(view, i3)) {
            return false;
        }
        float a2 = motionController.a(2, f, f2);
        float a3 = motionController.a(5, f, f2);
        this.c.a.setKeyframe(view, i3, "motion:percentX", Float.valueOf(a2));
        this.c.a.setKeyframe(view, i3, "motion:percentY", Float.valueOf(a3));
        this.c.rebuildScene();
        this.c.a(true);
        this.c.invalidate();
        return true;
    }

    public void setViewDebug(Object obj, int i) {
        MotionController motionController = this.c.e.get(obj);
        if (motionController != null) {
            motionController.setDrawPath(i);
            this.c.invalidate();
        }
    }

    public int designAccess(int i, String str, Object obj, float[] fArr, int i2, float[] fArr2, int i3) {
        MotionController motionController;
        View view = (View) obj;
        if (i == 0) {
            motionController = null;
        } else if (this.c.a == null || view == null || (motionController = this.c.e.get(view)) == null) {
            return -1;
        }
        switch (i) {
            case 0:
                return 1;
            case 1:
                int duration = this.c.a.getDuration() / 16;
                motionController.a(fArr2, duration);
                return duration;
            case 2:
                int duration2 = this.c.a.getDuration() / 16;
                motionController.a(fArr2, (int[]) null);
                return duration2;
            case 3:
                int duration3 = this.c.a.getDuration() / 16;
                return motionController.a(str, fArr2, i3);
            default:
                return -1;
        }
    }

    public Object getKeyframe(int i, int i2, int i3) {
        if (this.c.a == null) {
            return null;
        }
        return this.c.a.a(this.c.getContext(), i, i2, i3);
    }

    public Object getKeyframeAtLocation(Object obj, float f, float f2) {
        MotionController motionController;
        View view = (View) obj;
        if (this.c.a == null) {
            return -1;
        }
        if (view == null || (motionController = this.c.e.get(view)) == null) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        return motionController.a(viewGroup.getWidth(), viewGroup.getHeight(), f, f2);
    }

    public Boolean getPositionKeyframe(Object obj, Object obj2, float f, float f2, String[] strArr, float[] fArr) {
        if (!(obj instanceof a)) {
            return false;
        }
        View view = (View) obj2;
        this.c.e.get(view).a(view, (a) obj, f, f2, strArr, fArr);
        this.c.rebuildScene();
        this.c.i = true;
        return true;
    }

    public Object getKeyframe(Object obj, int i, int i2) {
        if (this.c.a == null) {
            return null;
        }
        return this.c.a.a(this.c.getContext(), i, ((View) obj).getId(), i2);
    }

    public void setKeyframe(Object obj, String str, Object obj2) {
        if (obj instanceof Key) {
            ((Key) obj).setValue(str, obj2);
            this.c.rebuildScene();
            this.c.i = true;
        }
    }

    public void setAttributes(int i, String str, Object obj, Object obj2) {
        View view = (View) obj;
        HashMap hashMap = (HashMap) obj2;
        int a2 = this.c.a(str);
        ConstraintSet a3 = this.c.a.a(a2);
        if (a3 != null) {
            a3.clear(view.getId());
            a(i, a3, view, hashMap, 0);
            a(i, a3, view, hashMap, 1);
            a(i, a3, view, hashMap, 6, 6);
            a(i, a3, view, hashMap, 6, 7);
            a(i, a3, view, hashMap, 7, 7);
            a(i, a3, view, hashMap, 7, 6);
            a(i, a3, view, hashMap, 1, 1);
            a(i, a3, view, hashMap, 1, 2);
            a(i, a3, view, hashMap, 2, 2);
            a(i, a3, view, hashMap, 2, 1);
            a(i, a3, view, hashMap, 3, 3);
            a(i, a3, view, hashMap, 3, 4);
            a(i, a3, view, hashMap, 4, 3);
            a(i, a3, view, hashMap, 4, 4);
            a(i, a3, view, hashMap, 5, 5);
            a(a3, view, hashMap, 0);
            a(a3, view, hashMap, 1);
            a(i, a3, view, hashMap);
            this.c.updateState(a2, a3);
            this.c.requestLayout();
        }
    }

    public void dumpConstraintSet(String str) {
        if (this.c.a == null) {
            this.c.a = this.d;
        }
        int a2 = this.c.a(str);
        PrintStream printStream = System.out;
        printStream.println(" dumping  " + str + " (" + a2 + ")");
        try {
            this.c.a.a(a2).dump(this.c.a, new int[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
