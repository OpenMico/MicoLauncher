package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMutableIterator;
import org.jetbrains.annotations.NotNull;

/* compiled from: Menu.kt */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"androidx/core/view/MenuKt$iterator$1", "", "Landroid/view/MenuItem;", MiSoundBoxCommandExtras.INDEX, "", "hasNext", "", "next", "remove", "", "core-ktx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MenuKt$iterator$1 implements Iterator<MenuItem>, KMutableIterator {
    final /* synthetic */ Menu a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MenuKt$iterator$1(Menu menu) {
        this.a = menu;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.b < this.a.size();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    @NotNull
    public MenuItem next() {
        Menu menu = this.a;
        int i = this.b;
        this.b = i + 1;
        MenuItem item = menu.getItem(i);
        if (item != null) {
            return item;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // java.util.Iterator
    public void remove() {
        this.b--;
        this.a.removeItem(this.b);
    }
}
