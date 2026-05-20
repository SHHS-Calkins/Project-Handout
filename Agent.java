public class Agent {
    private String symbol;
    private String name;
    private int hp;
    public Agent(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        hp = 100;
    }
    public String getName() {
        return name;
    }
}
