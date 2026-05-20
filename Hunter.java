public class Hunter extends Agent {
    private int hp;
    private int damage;
    public Hunter(String name, String symbol, int damage) {
        super(name, symbol);
        hp = 100;
        this.damage = damage;
    }
}
