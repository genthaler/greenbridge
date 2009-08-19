


tags = c.model.registry.attributes.getDefaultComboBoxModel("tag");
dialog = new com.mycompany.conversation.gui.NewTagDialog( c.frame.getJFrame(), true, tags);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);
if (dialog.tagAndDescription != null) {
    tagDetails = dialog.tagAndDescription;
    c.getController().obtainFocusForSelected();


     targetNode = c.getController().getView().getSelected().getModel();
     child = null;
     if (!targetNode.isRoot()) {
         parent = targetNode.getParentNode();
         int childPosition = parent.getChildPosition(targetNode);
         childPosition++;
         child = c.newChild.addNewNode(parent, childPosition, targetNode.isLeft());
         nodeView = c.getNodeView(child);
         c.select(nodeView);
     } else {
         parentFolded = targetNode.isFolded();
         if (parentFolded) {
            c.setFolded(targetNode,false);
         }
         position = c.getFrame().getProperty("placenewbranches").equals("last") ?
         targetNode.getChildCount() : 0;
         child = c.newChild.addNewNode(targetNode, position);
         nodeView = c.getNodeView(child);
         c.select(nodeView);
     }
    if (tagDetails.description != null && !"".equals(tagDetails.description)) {
        child.setText(tagDetails.description);
        child.createAttributeTableModel();
        if (child.getAttributes()==null || child.getAttributes().getRowCount()==0) {
            child.createAttributeTableModel();
        };
        child.getAttributes().insertRow(0 , 'tag', tagDetails.tag);
        
    } else {
        child.setText( tagDetails.tag);
    }
    history = new freemind.modes.HistoryInformation(tagDetails.tagDate, tagDetails.tagDate);
    child.setHistoryInformation(history)
    c.nodeStructureChanged(node);

}

