package org.apache.commons.text.diff;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ReplacementsFinder<T> implements CommandVisitor<T> {
    private final List<T> a = new ArrayList();
    private final List<T> b = new ArrayList();
    private int c = 0;
    private final ReplacementsHandler<T> d;

    public ReplacementsFinder(ReplacementsHandler<T> replacementsHandler) {
        this.d = replacementsHandler;
    }

    @Override // org.apache.commons.text.diff.CommandVisitor
    public void visitInsertCommand(T t) {
        this.a.add(t);
    }

    @Override // org.apache.commons.text.diff.CommandVisitor
    public void visitKeepCommand(T t) {
        if (!this.b.isEmpty() || !this.a.isEmpty()) {
            this.d.handleReplacement(this.c, this.b, this.a);
            this.b.clear();
            this.a.clear();
            this.c = 1;
            return;
        }
        this.c++;
    }

    @Override // org.apache.commons.text.diff.CommandVisitor
    public void visitDeleteCommand(T t) {
        this.b.add(t);
    }
}
