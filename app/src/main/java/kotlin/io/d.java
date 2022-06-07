package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileTreeWalk.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007"}, d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/io/FilesKt")
/* loaded from: classes5.dex */
class d extends c {
    public static /* synthetic */ FileTreeWalk walk$default(File file, FileWalkDirection fileWalkDirection, int i, Object obj) {
        if ((i & 1) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        return FilesKt.walk(file, fileWalkDirection);
    }

    @NotNull
    public static final FileTreeWalk walk(@NotNull File walk, @NotNull FileWalkDirection direction) {
        Intrinsics.checkNotNullParameter(walk, "$this$walk");
        Intrinsics.checkNotNullParameter(direction, "direction");
        return new FileTreeWalk(walk, direction);
    }

    @NotNull
    public static final FileTreeWalk walkTopDown(@NotNull File walkTopDown) {
        Intrinsics.checkNotNullParameter(walkTopDown, "$this$walkTopDown");
        return FilesKt.walk(walkTopDown, FileWalkDirection.TOP_DOWN);
    }

    @NotNull
    public static final FileTreeWalk walkBottomUp(@NotNull File walkBottomUp) {
        Intrinsics.checkNotNullParameter(walkBottomUp, "$this$walkBottomUp");
        return FilesKt.walk(walkBottomUp, FileWalkDirection.BOTTOM_UP);
    }
}
