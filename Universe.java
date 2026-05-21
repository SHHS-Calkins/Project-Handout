import java.util.Scanner;
public class Universe {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Clanker World! Choose how your creation starts!");
        System.out.println("C = Custom World : D = Default : S = Something Else");
        int length = 0;
        int width = 0;
        switch(scan.nextLine()) {
            case "C":
                System.out.println("Alright. Pick a Width: ");
                width = scan.nextInt();
                System.out.println("Pick a Length: ");
                length = scan.nextInt();
                System.out.println("Creating . . .");
                break;
            case "D":
                System.out.println("Creating . . .");
                break;
            case "S":
                System.out.println("I dunno what you want.");
                break;
        }
        World w = new World(length, width);
    }
}
