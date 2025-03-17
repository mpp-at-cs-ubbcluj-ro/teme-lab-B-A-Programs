package MPP.Domain;

public enum AgeGroup {
    SIX_EIGHT("SixEight"),
    NINE_ELEVEN("NineEleven"),
    TWELVE_FIFTEEN("TwelveFifteen"),;

    private final String value;

    AgeGroup(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
