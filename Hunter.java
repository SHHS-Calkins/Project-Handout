public class Hunter extends Agent {
    private int hp;
    private int damage;
    public Hunter(String name, String symbol, int hp, int damage) {
        super(name, symbol, hp);
        this.damage = damage;
    }

    public Hunter() {
        super();
        damage = 1;
    }
    public void Hunt(int range) {
        
    }
    public String toString() {
       return super.toString() + " - Damage: " + damage;
    }
}
