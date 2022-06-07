package org.fourthline.cling.support.contentdirectory.ui;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public class ContentTreeExpandListener implements TreeWillExpandListener {
    protected final ContentBrowseActionCallbackCreator actionCreator;
    protected final ControlPoint controlPoint;
    protected final Service service;
    protected final DefaultTreeModel treeModel;

    public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {
    }

    public ContentTreeExpandListener(ControlPoint controlPoint, Service service, DefaultTreeModel defaultTreeModel, ContentBrowseActionCallbackCreator contentBrowseActionCallbackCreator) {
        this.controlPoint = controlPoint;
        this.service = service;
        this.treeModel = defaultTreeModel;
        this.actionCreator = contentBrowseActionCallbackCreator;
    }

    public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) treeExpansionEvent.getPath().getLastPathComponent();
        defaultMutableTreeNode.removeAllChildren();
        this.treeModel.nodeStructureChanged(defaultMutableTreeNode);
        this.controlPoint.execute(this.actionCreator.createContentBrowseActionCallback(this.service, this.treeModel, defaultMutableTreeNode));
    }
}
