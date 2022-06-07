package org.apache.commons.text.diff;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class EditScript<T> {
    private final List<EditCommand<T>> a = new ArrayList();
    private int b = 0;
    private int c = 0;

    public void append(KeepCommand<T> keepCommand) {
        this.a.add(keepCommand);
        this.b++;
    }

    public void append(InsertCommand<T> insertCommand) {
        this.a.add(insertCommand);
        this.c++;
    }

    public void append(DeleteCommand<T> deleteCommand) {
        this.a.add(deleteCommand);
        this.c++;
    }

    public void visit(CommandVisitor<T> commandVisitor) {
        for (EditCommand<T> editCommand : this.a) {
            editCommand.accept(commandVisitor);
        }
    }

    public int getLCSLength() {
        return this.b;
    }

    public int getModifications() {
        return this.c;
    }
}
