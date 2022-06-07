package org.apache.commons.text.diff;

/* loaded from: classes5.dex */
public class DeleteCommand<T> extends EditCommand<T> {
    public DeleteCommand(T t) {
        super(t);
    }

    @Override // org.apache.commons.text.diff.EditCommand
    public void accept(CommandVisitor<T> commandVisitor) {
        commandVisitor.visitDeleteCommand(getObject());
    }
}
