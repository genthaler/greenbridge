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

class AudioListener implements com.mycompany.conversation.AudioRecordingListener {
    def c;

    public AudioListener(c) {
		this.c = c;
	}

    public void recordingChanged(com.mycompany.conversation.AudioRecordingState state) {
        if (state.recordingStarted == null) {
            
        }
        if (state.recordingStarted && !state.recordingStopped) {
            //def icon = new javax.swing.ImageIcon("plugins/script/record.png")
            //def started = new javax.swing.JLabel(icon);
            //started.setText("REC");
            //started.setForeground(java.awt.Color.RED)
            //def glass = (javax.swing.JPanel) c.frame.getJFrame().getGlassPane();
            //glass.setVisible(true);
            //glass.setLayout(null);
            //glass.add(started);
            //started.setBounds(c.frame.getJFrame().getWidth() - 62, 40 , 50, 50);
        }
        if (state.recordingStarted && state.recordingStopped) {
            //def glass = (javax.swing.JPanel) c.frame.getJFrame().getGlassPane();
            //glass.setVisible(false);
            //glass.removeAll();
        }
    }
}




p_storage = new FreeMindPropertiesStorage(c);
d_storage = new FreeMindNodeDocumentStorage(node);
listener = new AudioListener(c);
c_controller = new com.mycompany.conversation.ConversationControllerImpl(d_storage, p_storage);
c_controller.addAudioRecordingListener(listener);
dialog = new com.mycompany.conversation.gui.RecordingStatusDialog(c_controller, p_storage,  null, false);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);
        
      




