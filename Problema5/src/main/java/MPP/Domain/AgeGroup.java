package MPP.Domain;

public enum AgeGroup {
    SIX_EIGHT("6-8"),
    NINE_ELEVEN("9-11"),
    TWELVE_FIFTEEN("12-15");

    private final String value;

    AgeGroup(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
