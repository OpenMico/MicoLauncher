package org.fourthline.cling.support.contentdirectory.ui;

import javax.swing.JTree;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.contentdirectory.callback.Browse;
import org.fourthline.cling.support.model.container.Container;

/* loaded from: classes5.dex */
public abstract class ContentTree extends JTree implements ContentBrowseActionCallbackCreator {
    protected Container rootContainer;
    protected DefaultMutableTreeNode rootNode;

    public abstract void failure(String str);

    protected ContentTree() {
    }

    public ContentTree(ControlPoint controlPoint, Service service) {
        init(controlPoint, service);
    }

    public void init(ControlPoint controlPoint, Service service) {
        this.rootContainer = createRootContainer(service);
        this.rootNode = new DefaultMutableTreeNode(this.rootContainer) { // from class: org.fourthline.cling.support.contentdirectory.ui.ContentTree.1
            public boolean isLeaf() {
                return false;
            }
        };
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(this.rootNode);
        setModel(defaultTreeModel);
        getSelectionModel().setSelectionMode(1);
        addTreeWillExpandListener(createContainerTreeExpandListener(controlPoint, service, defaultTreeModel));
        setCellRenderer(createContainerTreeCellRenderer());
        controlPoint.execute(createContentBrowseActionCallback(service, defaultTreeModel, getRootNode()));
    }

    public Container getRootContainer() {
        return this.rootContainer;
    }

    public DefaultMutableTreeNode getRootNode() {
        return this.rootNode;
    }

    public DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) getLastSelectedPathComponent();
    }

    protected Container createRootContainer(Service service) {
        Container container = new Container();
        container.setId("0");
        container.setTitle("Content Directory on " + service.getDevice().getDisplayString());
        return container;
    }

    protected TreeWillExpandListener createContainerTreeExpandListener(ControlPoint controlPoint, Service service, DefaultTreeModel defaultTreeModel) {
        return new ContentTreeExpandListener(controlPoint, service, defaultTreeModel, this);
    }

    protected DefaultTreeCellRenderer createContainerTreeCellRenderer() {
        return new ContentTreeCellRenderer();
    }

    @Override // org.fourthline.cling.support.contentdirectory.ui.ContentBrowseActionCallbackCreator
    public ActionCallback createContentBrowseActionCallback(Service service, DefaultTreeModel defaultTreeModel, DefaultMutableTreeNode defaultMutableTreeNode) {
        return new ContentBrowseActionCallback(service, defaultTreeModel, defaultMutableTreeNode) { // from class: org.fourthline.cling.support.contentdirectory.ui.ContentTree.2
            @Override // org.fourthline.cling.support.contentdirectory.ui.ContentBrowseActionCallback
            public void updateStatusUI(Browse.Status status, DefaultMutableTreeNode defaultMutableTreeNode2, DefaultTreeModel defaultTreeModel2) {
                ContentTree.this.updateStatus(status, defaultMutableTreeNode2, defaultTreeModel2);
            }

            @Override // org.fourthline.cling.support.contentdirectory.ui.ContentBrowseActionCallback
            public void failureUI(String str) {
                ContentTree.this.failure(str);
            }
        };
    }

    public void updateStatus(Browse.Status status, DefaultMutableTreeNode defaultMutableTreeNode, DefaultTreeModel defaultTreeModel) {
        switch (status) {
            case LOADING:
            case NO_CONTENT:
                defaultMutableTreeNode.removeAllChildren();
                defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode(status.getDefaultMessage()), defaultMutableTreeNode, defaultMutableTreeNode.getChildCount() <= 0 ? 0 : defaultMutableTreeNode.getChildCount());
                defaultTreeModel.nodeStructureChanged(defaultMutableTreeNode);
                return;
            default:
                return;
        }
    }
}
