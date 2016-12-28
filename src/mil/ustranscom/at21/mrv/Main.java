package mil.ustranscom.at21.mrv;

public class Main implements Runnable {
	public static void main(String[] args) {
		Main main = new Main();
		main.run();		
	}
	
	
	@Override
	public void run() {
		try(RulesRunner runner = new RulesRunner()) {
			
			
		} catch (Exception e) {
			throw new RuntimeException("Exception closing RulesSession", e);
		}		
	}
}
