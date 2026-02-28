import java.util.UUID;

public class Job {

    private final UUID id;
    private final int operator;
    private final int a;
    private final int b;
    private final long createdAt;
    private long startSolve;

    public static final Job STOP = new Job(UUID.fromString("00000000-0000-0000-0000-000000000000"), 0, 0, 0);

    Integer result;
    Long solvedAt;
    String reason;

    public Job(UUID id, int operator, int a, int b) {
        this.id = id;
        this.operator = operator;
        this.a = a;
        this.b = b;
        this.createdAt = System.nanoTime();
        this.result = null;
        this.reason = null;
    }

    public UUID getId() { return id; }
    public int getOperator() { return operator; }
    public int getA() { return a; }
    public int getB() { return b; }
    public long getCreatedAt() { return createdAt; }
    public void setStartSolve() { this.startSolve = System.nanoTime(); }
    public long getStartSolve() { return startSolve; }

    public String getOperatorSymbol() {
        switch (operator) {
            case 1: return "+";
            case 2: return "-";
            case 3: return "*";
            case 4: return "/";
            case 5: return "%";
            default: return "?";
        }
    }

    @Override
    public String toString() {
        return a + " " + getOperatorSymbol() + " " + b + " = " + result;
    }
}