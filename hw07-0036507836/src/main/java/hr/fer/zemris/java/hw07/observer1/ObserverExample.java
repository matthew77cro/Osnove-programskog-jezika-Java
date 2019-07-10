package hr.fer.zemris.java.hw07.observer1;

public class ObserverExample {

	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		/*istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));*/
		
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
	
	private static class SquareValue implements IntegerStorageObserver {

		@Override
		public void valueChanged(IntegerStorage istorage) {
			int value = istorage.getValue();
			System.out.printf("Provided new value: %d, square is %d%n", value, value*value);
		}
		
	}
	
	private static class ChangeCounter implements IntegerStorageObserver {
		
		private int changeCounter;
		
		@Override
		public void valueChanged(IntegerStorage istorage) {
			changeCounter++;
			System.out.printf("Number of value changes since tracking: %d%n", changeCounter);
		}
		
	}
	
	private static class DoubleValue implements IntegerStorageObserver {
		
		private int changeCounter;
		private int maxChange;
		
		public DoubleValue(int maxChange) {
			this.maxChange = maxChange;
		}

		@Override
		public void valueChanged(IntegerStorage istorage) {
			System.out.printf("Double value: %d%n", istorage.getValue()*2);
			changeCounter++;
			if(changeCounter>=maxChange) {
				istorage.removeObserver(this);
			}
		}
		
	}

}
