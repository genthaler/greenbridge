javax.swing.JOptionPane.showMessageDialog(null, "Test! ");
poss = c.model.registry.attributes.getElement('Tag').getValues();
for (i in poss) {
	javax.swing.JOptionPane.showMessageDialog(null, "Test! " + i);
}

javax.swing.JOptionPane.showMessageDialog(null, "Test! ");

String s = (String)javax.swing.JOptionPane.showInputDialog(
                    null,
                    "Complete the sentence:\n",
                    "Customized Dialog",
                    javax.swing.JOptionPane.PLAIN_MESSAGE,
                    null,
                    poss,
                    null);


javax.swing.JOptionPane.showMessageDialog(null, "6! ");


c.getController().obtainFocusForSelected();
child = c.newChild.addNew(c.getController().getView().getSelected().getModel(), c.NEW_CHILD, null)
child.createAttributeTableModel() 
if (child.getAttributes()==null || child.getAttributes().getRowCount()==0) {
	child.createAttributeTableModel();
};
child.getAttributes().insertRow(0 , 'tag', 'Alpha-2');



//javax.swing.JOptionPane.showMessageDialog(null, "Test! " + c.getController().getView().getSelected().getText());



//def i = node.childrenUnfolded(); 
//while (i.hasNext()) { d = i.next(); d.setBackgroundColor(java.awt.Color.WHITE); }; 
//c.nodeStructureChanged();
//javax.swing.JOptionPane.showMessageDialog(null, "Start! ");