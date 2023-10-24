package libreria;

public class Connector {
	private Application app;
	private Port p;

	public Connector(Application app, Port p) {
		this.app = app;
		this.p = p;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public Port getP() {
		return p;
	}

	public void setP(Port p) {
		this.p = p;
	}

}
