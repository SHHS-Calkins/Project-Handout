public class Agent {
    private String symbol;
    private String name;
    private int hp;

    public Agent(String name, String symbol, int hp) {
        this.name = name;
        this.symbol = symbol;
        this.hp = hp;
    }

    public Agent() {
        name = "Billy";
        symbol = "robotFace";
        hp = 100;
    }
    public String getName() {
        return name;
    }
    
    public String getSymbol() {
        return symbol;
    }

    public int getHp() {
        return hp;
    }
    public String toString() {
        return name + " - Symbol: " + symbol + " - HP: " + hp;
    }
}
