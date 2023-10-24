package libreria;

import libreria.port.Message;

public class Port {
	private Message m;
	
	public Port() {
		this.m = new Message();
	}

	public String getBuffer() {
		return m.getBuffer();
	}

	public void setBuffer(String m) {
		this.m.setBuffer(m);
	}
	
}
