


tags = c.model.registry.attributes.getDefaultComboBoxModel("tag");
dialog = new com.mycompany.conversation.gui.NewTagDialog( c.frame.getJFrame(), true, tags);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);
if (dialog.tagAndDescription != null) {
    tagDetails = dialog.tagAndDescription;
    c.getController().obtainFocusForSelected();

    action = c.NEW_SIBLING_BEHIND
    if (node == c.getController().getView().getSelected()) {
        action = c.NEW_CHILD
    }

    //child = c.newChild.addNew(c.getController().getView().getSelected().getModel(), action, null)
    child = c.newChild.addNewNode()
    if (tagDetails.description != null && !"".equals(tagDetails.description)) {
        child.setText(tagDetails.description);
        child.createAttributeTableModel();
        if (child.getAttributes()==null || child.getAttributes().getRowCount()==0) {
            child.createAttributeTableModel();
        };
        child.getAttributes().insertRow(0 , 'tag', tagDetails.tag);
        
    } else {
        child.setText( tagDetails.tag);
        println('child text: ' + child.getText())
    }
    history = new freemind.modes.HistoryInformation(tagDetails.tagDate, tagDetails.tagDate);
    child.setHistoryInformation(history)
    c.nodeStructureChanged(node);

}

