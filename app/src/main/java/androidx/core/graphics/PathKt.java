package androidx.core.graphics;

import android.graphics.Path;
import androidx.annotation.RequiresApi;
import com.umeng.analytics.pro.ai;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Path.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f¨\u0006\f"}, d2 = {"and", "Landroid/graphics/Path;", ai.av, "flatten", "", "Landroidx/core/graphics/PathSegment;", "error", "", "minus", "or", "plus", "xor", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PathKt {
    public static /* synthetic */ Iterable flatten$default(Path path, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 0.5f;
        }
        return flatten(path, f);
    }

    @RequiresApi(26)
    @NotNull
    public static final Iterable<PathSegment> flatten(@NotNull Path path, float f) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Collection<PathSegment> flatten = PathUtils.flatten(path, f);
        Intrinsics.checkNotNullExpressionValue(flatten, "flatten(this, error)");
        return flatten;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path plus(@NotNull Path path, @NotNull Path p) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        Path path2 = new Path(path);
        path2.op(p, Path.Op.UNION);
        return path2;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path minus(@NotNull Path path, @NotNull Path p) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        Path path2 = new Path(path);
        path2.op(p, Path.Op.DIFFERENCE);
        return path2;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path and(@NotNull Path path, @NotNull Path p) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        Path path2 = new Path();
        path2.op(path, p, Path.Op.INTERSECT);
        return path2;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path xor(@NotNull Path path, @NotNull Path p) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        Path path2 = new Path(path);
        path2.op(p, Path.Op.XOR);
        return path2;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path or(@NotNull Path path, @NotNull Path p) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        Path path2 = new Path(path);
        path2.op(p, Path.Op.UNION);
        return path2;
    }
}
