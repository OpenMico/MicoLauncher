package org.fourthline.cling.support.contentdirectory.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public interface ContentBrowseActionCallbackCreator {
    ActionCallback createContentBrowseActionCallback(Service service, DefaultTreeModel defaultTreeModel, DefaultMutableTreeNode defaultMutableTreeNode);
}
