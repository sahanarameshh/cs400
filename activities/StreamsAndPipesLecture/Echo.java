import java.util.Scanner;

public class Echo {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            System.out.println(in.nextLine());
        }
        in.close();
    }
}