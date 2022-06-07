package org.apache.commons.text.diff;

/* loaded from: classes5.dex */
public class KeepCommand<T> extends EditCommand<T> {
    public KeepCommand(T t) {
        super(t);
    }

    @Override // org.apache.commons.text.diff.EditCommand
    public void accept(CommandVisitor<T> commandVisitor) {
        commandVisitor.visitKeepCommand(getObject());
    }
}
