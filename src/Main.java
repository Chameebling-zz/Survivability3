

public class Main implements Runnable {
	
	private Thread thread;
	private boolean running;
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		new Main();
	}
	
	private synchronized void start() {
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		
	}

}
