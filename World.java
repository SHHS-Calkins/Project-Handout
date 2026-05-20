import java.util.ArrayList;
public class World {
    private String[][] area;
    private ArrayList<Agent> livingAgents = new ArrayList<Agent>();
    public World() {
        area = new int[12][12];
    }
    public World(int length, int width) {
        area = new String[length][width];
        for(String[] r : area) {
            for(String c : r) {
                c = "-";
            }
        }
    }

    public void addAgent(Agent a, int x, int y) {
        area[y][x] = a.getName();
    }

    
}
