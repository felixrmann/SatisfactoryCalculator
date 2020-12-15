package SatisfactoryCalculator.Model;

public enum Result {
    SUCCESS(0),
    NOACTION(1),
    DUPLICATE(2),
    ERROR(3);

    private int code;

    Result(int code) {
        this.setCode(code);
    }

    public void setCode(int code) {
        this.code = code;
    }
}