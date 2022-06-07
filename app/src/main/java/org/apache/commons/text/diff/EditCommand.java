package org.apache.commons.text.diff;

/* loaded from: classes5.dex */
public abstract class EditCommand<T> {
    private final T a;

    public abstract void accept(CommandVisitor<T> commandVisitor);

    /* JADX INFO: Access modifiers changed from: protected */
    public EditCommand(T t) {
        this.a = t;
    }

    protected T getObject() {
        return this.a;
    }
}
