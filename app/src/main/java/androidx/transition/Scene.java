package androidx.transition;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class Scene {
    private Context a;
    private int b;
    private ViewGroup c;
    private View d;
    private Runnable e;
    private Runnable f;

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup viewGroup, @LayoutRes int i, @NonNull Context context) {
        SparseArray sparseArray = (SparseArray) viewGroup.getTag(R.id.transition_scene_layoutid_cache);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            viewGroup.setTag(R.id.transition_scene_layoutid_cache, sparseArray);
        }
        Scene scene = (Scene) sparseArray.get(i);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(viewGroup, i, context);
        sparseArray.put(i, scene2);
        return scene2;
    }

    public Scene(@NonNull ViewGroup viewGroup) {
        this.b = -1;
        this.c = viewGroup;
    }

    private Scene(ViewGroup viewGroup, int i, Context context) {
        this.b = -1;
        this.a = context;
        this.c = viewGroup;
        this.b = i;
    }

    public Scene(@NonNull ViewGroup viewGroup, @NonNull View view) {
        this.b = -1;
        this.c = viewGroup;
        this.d = view;
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.c;
    }

    public void exit() {
        Runnable runnable;
        if (getCurrentScene(this.c) == this && (runnable = this.f) != null) {
            runnable.run();
        }
    }

    public void enter() {
        if (this.b > 0 || this.d != null) {
            getSceneRoot().removeAllViews();
            if (this.b > 0) {
                LayoutInflater.from(this.a).inflate(this.b, this.c);
            } else {
                this.c.addView(this.d);
            }
        }
        Runnable runnable = this.e;
        if (runnable != null) {
            runnable.run();
        }
        a(this.c, this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull ViewGroup viewGroup, @Nullable Scene scene) {
        viewGroup.setTag(R.id.transition_current_scene, scene);
    }

    @Nullable
    public static Scene getCurrentScene(@NonNull ViewGroup viewGroup) {
        return (Scene) viewGroup.getTag(R.id.transition_current_scene);
    }

    public void setEnterAction(@Nullable Runnable runnable) {
        this.e = runnable;
    }

    public void setExitAction(@Nullable Runnable runnable) {
        this.f = runnable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return this.b > 0;
    }
}
