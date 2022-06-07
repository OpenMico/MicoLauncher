package kotlin.io;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FilePathComponents.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0013HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013J\t\u0010\u001f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "}, d2 = {"Lkotlin/io/FilePathComponents;", "", "root", "Ljava/io/File;", "segments", "", "(Ljava/io/File;Ljava/util/List;)V", "isRooted", "", "()Z", "getRoot", "()Ljava/io/File;", "rootName", "", "getRootName", "()Ljava/lang/String;", "getSegments", "()Ljava/util/List;", "size", "", "getSize", "()I", "component1", "component2", "copy", "equals", "other", "hashCode", "subPath", "beginIndex", "endIndex", "toString", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class FilePathComponents {
    @NotNull
    private final File a;
    @NotNull
    private final List<File> b;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FilePathComponents copy$default(FilePathComponents filePathComponents, File file, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            file = filePathComponents.a;
        }
        if ((i & 2) != 0) {
            list = filePathComponents.b;
        }
        return filePathComponents.copy(file, list);
    }

    @NotNull
    public final File component1() {
        return this.a;
    }

    @NotNull
    public final List<File> component2() {
        return this.b;
    }

    @NotNull
    public final FilePathComponents copy(@NotNull File root, @NotNull List<? extends File> segments) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(segments, "segments");
        return new FilePathComponents(root, segments);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilePathComponents)) {
            return false;
        }
        FilePathComponents filePathComponents = (FilePathComponents) obj;
        return Intrinsics.areEqual(this.a, filePathComponents.a) && Intrinsics.areEqual(this.b, filePathComponents.b);
    }

    public int hashCode() {
        File file = this.a;
        int i = 0;
        int hashCode = (file != null ? file.hashCode() : 0) * 31;
        List<File> list = this.b;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "FilePathComponents(root=" + this.a + ", segments=" + this.b + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FilePathComponents(@NotNull File root, @NotNull List<? extends File> segments) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(segments, "segments");
        this.a = root;
        this.b = segments;
    }

    @NotNull
    public final File getRoot() {
        return this.a;
    }

    @NotNull
    public final List<File> getSegments() {
        return this.b;
    }

    @NotNull
    public final String getRootName() {
        String path = this.a.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "root.path");
        return path;
    }

    public final boolean isRooted() {
        String path = this.a.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "root.path");
        return path.length() > 0;
    }

    public final int getSize() {
        return this.b.size();
    }

    @NotNull
    public final File subPath(int i, int i2) {
        if (i < 0 || i > i2 || i2 > getSize()) {
            throw new IllegalArgumentException();
        }
        List<File> subList = this.b.subList(i, i2);
        String str = File.separator;
        Intrinsics.checkNotNullExpressionValue(str, "File.separator");
        return new File(CollectionsKt.joinToString$default(subList, str, null, null, 0, null, null, 62, null));
    }
}
