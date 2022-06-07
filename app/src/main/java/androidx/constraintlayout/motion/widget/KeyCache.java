package androidx.constraintlayout.motion.widget;

import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyCache {
    HashMap<Object, HashMap<String, float[]>> a = new HashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Object obj, String str, int i, float f) {
        if (!this.a.containsKey(obj)) {
            HashMap<String, float[]> hashMap = new HashMap<>();
            float[] fArr = new float[i + 1];
            fArr[i] = f;
            hashMap.put(str, fArr);
            this.a.put(obj, hashMap);
            return;
        }
        HashMap<String, float[]> hashMap2 = this.a.get(obj);
        if (!hashMap2.containsKey(str)) {
            float[] fArr2 = new float[i + 1];
            fArr2[i] = f;
            hashMap2.put(str, fArr2);
            this.a.put(obj, hashMap2);
            return;
        }
        float[] fArr3 = hashMap2.get(str);
        if (fArr3.length <= i) {
            fArr3 = Arrays.copyOf(fArr3, i + 1);
        }
        fArr3[i] = f;
        hashMap2.put(str, fArr3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float a(Object obj, String str, int i) {
        if (!this.a.containsKey(obj)) {
            return Float.NaN;
        }
        HashMap<String, float[]> hashMap = this.a.get(obj);
        if (!hashMap.containsKey(str)) {
            return Float.NaN;
        }
        float[] fArr = hashMap.get(str);
        if (fArr.length > i) {
            return fArr[i];
        }
        return Float.NaN;
    }
}
