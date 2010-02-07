class FreeMindNodeDocumentStorage implements  com.mycompany.conversation.DocumentStorage {
	
	def formatter = new java.text.SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	def node;
	def storage;
    def converter = new com.mycompany.conversation.converter.FreemindXMLConverter();

	
	public FreeMindNodeDocumentStorage(node, storage) {
		this.node = node;
		this.storage = storage;
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
	public com.mycompany.conversation.domain.Conversation convertToConversation() {
            def fis = new java.io.FileInputStream(getFileLocation());
			try {
				converter.setStartTagOffset(Integer.parseInt(storage.loadProperty("tagStartOffset")));
				converter.setDefaultTagDuration(Integer.parseInt(storage.loadProperty("tagDuration")));
			} catch (Exception e) {}
            def c = converter.parseConversation(fis);
            return c;

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

class Player implements javazoom.jlgui.basicplayer.BasicPlayerListener, com.mycompany.conversation.AudioPlayer, freemind.modes.ModeController.NodeSelectionListener {

	def formatter = new java.text.SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	def listeners = [];
	def control;
	def secondsAmount = 0;
	def audioInfo;
	def controller;
	public Player(mm_controller) {
		this.controller = mm_controller.controller;
	}
	
	// AudioPlayer ***********************
    long play(File audioFile) {
		controller.getModeController().registerNodeSelectionListener(this);
		
		// Instantiate javazoom.jlgui.basicplayer.BasicPlayer.
		def player  =  new javazoom.jlgui.basicplayer.BasicPlayer();
		control = (javazoom.jlgui.basicplayer.BasicController) player;
		player.addBasicPlayerListener(this);
		try { 
		  // Open file, or URL or Stream (shoutcast, icecast) to play.
		  control.open(audioFile);
		  control.play();
		} catch (javazoom.jlgui.basicplayer.BasicPlayerException e) {
		  e.printStackTrace();
		}
		return 1;
	}

    void pause() {
		control.pause();
	}

    void stop() {
		control.stop();
		controller.getModeController().deregisterNodeSelectionListener(this);
	}

    /**
     * Seek to to seconds into track
     * @param percent
     */
    void seek(long seconds) {
		long totalTime = (long) Math.round(getTimeLengthEstimation(audioInfo) / 1000);
		long byteslength = -1;
		if (audioInfo.containsKey("audio.length.bytes")) {
            byteslength = ((Integer) audioInfo.get("audio.length.bytes")).intValue();
        }
		def bytesToSkip = (seconds/totalTime) * byteslength;
		
		control.seek((long)bytesToSkip);
	}

    void addListener(com.mycompany.conversation.AudioPlayerListener listener) {
		listeners.add(listener);
	}
    void removerListener(com.mycompany.conversation.AudioPlayerListener listener) {
		listeners.remove(listener);
	}

	
	//Basic Player ***********************
	public void opened(Object stream, Map properties) {
		audioInfo = properties;
		 for (def apl : listeners) {
			apl.started();
		}
    }

	public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
        int byteslength = -1;
        long total = -1;
        // try with JavaSound SPI.
        if (total <= 0) total = (long) Math.round(getTimeLengthEstimation(audioInfo) / 1000);
        if (audioInfo.containsKey("audio.length.bytes")) {
            byteslength = ((Integer) audioInfo.get("audio.length.bytes")).intValue();
        }
        float progress = -1.0f;
        if ((bytesread > 0) && ((byteslength > 0))) progress = bytesread * 1.0f / byteslength * 1.0f;
        if (audioInfo.containsKey("audio.type")) {
            String audioformat = (String) audioInfo.get("audio.type");
            if (audioformat.equalsIgnoreCase("mp3")) {                
                if (total > 0) secondsAmount = (long) (total * progress);
                else secondsAmount = -1;
            }
            else if (audioformat.equalsIgnoreCase("wave")) {
                secondsAmount = (long) (total * progress);
            }
            else {
                secondsAmount = (long) Math.round(microseconds / 1000000);
            }
        } else {
            secondsAmount = (long) Math.round(microseconds / 1000000);
        }
        if (secondsAmount < 0) secondsAmount = (long) Math.round(microseconds / 1000000);
		for (def apl : listeners) {
            apl.playing(secondsAmount, total);
        }
    }


    public void stateUpdated(javazoom.jlgui.basicplayer.BasicPlayerEvent event) {
    }
    public void setController(javazoom.jlgui.basicplayer.BasicController controller) {
		this.control = controller;
	}
	
	
	public long getTimeLengthEstimation(Map properties) {
        long milliseconds = -1;
        int byteslength = -1;
        if (properties != null) {
            if (properties.containsKey("audio.length.bytes")) {
                byteslength = ((Integer) properties.get("audio.length.bytes")).intValue();
            }
            if (properties.containsKey("duration")) {
                milliseconds = (int) (((Long) properties.get("duration")).longValue()) / 1000;
            } else {
                // Try to compute duration
                int bitspersample = -1;
                int channels = -1;
                float samplerate = -1.0f;
                int framesize = -1;
                if (properties.containsKey("audio.samplesize.bits")) {
                    bitspersample = ((Integer) properties.get("audio.samplesize.bits")).intValue();
                }
                if (properties.containsKey("audio.channels")) {
                    channels = ((Integer) properties.get("audio.channels")).intValue();
                }
                if (properties.containsKey("audio.samplerate.hz")) {
                    samplerate = ((Float) properties.get("audio.samplerate.hz")).floatValue();
                }
                if (properties.containsKey("audio.framesize.bytes")) {
                    framesize = ((Integer) properties.get("audio.framesize.bytes")).intValue();
                }
                if (bitspersample > 0) {
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * channels * (bitspersample / 8)));
                } else {
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * framesize));
                }
            }
        }
        return milliseconds;
    }
	
	
	// NodeSelectionListener
    void onSelectHook(freemind.view.mindmapview.NodeView node){
		System.out.println("Node selected!");
		def date = node.getModel().getHistoryInformation().getCreatedAt();
		def start = controller.getView().getRoot().getModel().getAttribute("startDate"); 
		//def start = root.getAttribute("startDate");
		def startDate = formatter.parse(start);
		
		
		def end = controller.getView().getRoot().getModel().getAttribute("endDate"); 
		def endDate = formatter.parse(end);
		
		// make sure between 
		if (!date.after(startDate) || !date.before(endDate)) return; 
		
		long seconds = (date.getTime() - startDate.getTime()) / 1000;
		
		System.out.println("Start: " + start);
		System.out.println("node : " + date);
		System.out.println("Seconds: " + seconds);
		seek(seconds);
		
		
	}
	void onUpdateNodeHook(freemind.modes.MindMapNode node) {}
    void onDeselectHook(freemind.view.mindmapview.NodeView node){}
	void onSaveNode(freemind.modes.MindMapNode node){}
	
	
	

}


p_storage = new FreeMindPropertiesStorage(c);
d_storage = new FreeMindNodeDocumentStorage(node, p_storage);
cu = new com.mycompany.conversation.upload.CouchTagsUploader();
//ap = new com.mycompany.conversation.AudioPlayerMock();
ap = new Player(c);
listener = new AudioListener(c);
c_controller = new com.mycompany.conversation.ConversationControllerImpl(d_storage, p_storage, cu,ap);
c_controller.addAudioRecordingListener(listener);
dialog = new com.mycompany.conversation.gui.RecordingStatusDialog(c_controller, p_storage,  null, false);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);
        
      




