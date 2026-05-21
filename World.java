import java.util.ArrayList;
public class World {
    private String[][] area;
    private ArrayList<String> livingAgents = new ArrayList<String>();
    public World() {
        area = new String[12][12];
    }
    public World(int length, int width) {
        area = new String[length][width];
        for(String[] r : area) {
            for(String c : r) {
                int a = (int) Math.random() * 3;
                if(a < 2) {
                    c = "food";
                }
                else if(a < 1) {
                    c = "obstacle";
                }
                else {
                    c = "-";
                }
            }
        }
    }

    public void addAgent(Agent a, int x, int y) {
        area[y][x] = a.getName();
        livingAgents.add(a.getName());
    }

    public String getAgents() {
        String s = "";
        for (String a : livingAgents) {
            s += a + "\n";
        }
        return s;
    }

    
}
