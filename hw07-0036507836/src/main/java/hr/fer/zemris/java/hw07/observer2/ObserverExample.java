package hr.fer.zemris.java.hw07.observer2;

public class ObserverExample {

	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
	
	private static class SquareValue implements IntegerStorageObserver {

		@Override
		public void valueChanged(IntegerStorageChange istorage) {
			int value = istorage.getNewValue();
			System.out.printf("Provided new value: %d, square is %d%n", value, value*value);
		}
		
	}
	
	private static class ChangeCounter implements IntegerStorageObserver {
		
		private int changeCounter;
		
		@Override
		public void valueChanged(IntegerStorageChange istorage) {
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
		public void valueChanged(IntegerStorageChange istorage) {
			System.out.printf("Double value: %d%n", istorage.getNewValue()*2);
			changeCounter++;
			if(changeCounter>=maxChange) {
				istorage.getStorage().removeObserver(this);
			}
		}
		
	}

}
