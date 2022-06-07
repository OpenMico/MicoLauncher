package org.apache.commons.text.diff;

/* loaded from: classes5.dex */
public class InsertCommand<T> extends EditCommand<T> {
    public InsertCommand(T t) {
        super(t);
    }

    @Override // org.apache.commons.text.diff.EditCommand
    public void accept(CommandVisitor<T> commandVisitor) {
        commandVisitor.visitInsertCommand(getObject());
    }
}
