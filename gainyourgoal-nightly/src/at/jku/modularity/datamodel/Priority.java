package at.jku.modularity.datamodel;

public enum Priority {
	ZERO("Zero", 0), TWO("Two", 2), FIVE("Five", 5), TEN("Ten", 10), TWENTY(
			"Twenty", 20), FOURTY("Fourty", 40), EIGHTY("Eighty", 80);

	private String stringValue;
	private int intValue;

	private Priority(String toString, int value) {
		stringValue = toString;
		intValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}
}
