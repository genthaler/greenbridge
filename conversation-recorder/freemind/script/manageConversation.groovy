class FreeMindNodeDocumentStorage implements  com.mycompany.conversation.DocumentStorage {
	
	def formatter = new java.text.SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	def node;

	
	public FreeMindNodeDocumentStorage(node) {
		this.node = node;
		node.createAttributeTableModel();
	}
	public void setStartDate(Date date) {
		def att = new freemind.modes.attributes.Attribute("startDate", formatter.format(date))
		node.attributes.addRowNoUndo(att)
	}
	public Date getStartDate() {
		def start  = node.getAttribute("startDate");
		if (start != null) return formatter.parse(start);	
		else return null;
	}
	public void setEndDate(Date date) {
		def att = new freemind.modes.attributes.Attribute("endDate", formatter.format(date))
		node.attributes.addRowNoUndo(att)
	}

	public Date getEndDate(){
		def end  = node.getAttribute("endDate");
		if (end != null) return formatter.parse(end);	
		else return null;
	}
	public File getFileLocation(){
		return node.map.file
	}


	public boolean saveCurrentDocument(){
		node.map.modeController.save()
	}

	public void setUploadURL(String url){
		def att = new freemind.modes.attributes.Attribute("uploadURL", url)
		node.attributes.addRowNoUndo(att)
	}
	public String getUploadURL(){
		return node.getAttribute("uploadURL");
	}
	


}

class FreeMindPropertiesStorage implements  com.mycompany.conversation.PropertiesStorage {
	def controller;
	public FreeMindPropertiesStorage(mm_controller) {
		this.controller = mm_controller.controller;
	}
	public void storeProperty(String property, String value) {
			controller.setProperty(property,value);
	}
	public String loadProperty(String property){
		return controller.getProperty(property);
	}
	public void showURL(String url) {
		controller.getFrame().openDocument(new java.net.URL(url));
	}
}

		p_storage = new FreeMindPropertiesStorage(c);
		d_storage = new FreeMindNodeDocumentStorage(node);
		c_controller = new com.mycompany.conversation.ConversationControllerImpl(d_storage, p_storage);
		dialog = new com.mycompany.conversation.gui.RecordingStatusDialog(c_controller, p_storage,  null, false);
		dialog.setVisible(true);
      




