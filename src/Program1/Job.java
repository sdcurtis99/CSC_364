public class Job {

    private final int id;
    private final int operator; // 1=+, 2=-, 3=*, 4=/
    private final int a;
    private final int b;

    public Job(int id, int operator, int a, int b) {
        this.id = id;
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    public int getId() { return id; }
    public int getOperator() { return operator; }
    public int getA() { return a; }
    public int getB() { return b; }

    public String getOperatorSymbol() {
        switch (operator) {
            case 1: return "+";
            case 2: return "-";
            case 3: return "*";
            case 4: return "/";
            default: return "?";
        }
    }
}