package domain;

public enum AgeGroup {
    SIXEIGHT("SixEight"),
    NINEELEVEN("NineEleven"),
    TWELVEFIFTEEN("TwelveFifteen"),;

    private final String value;

    AgeGroup(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
