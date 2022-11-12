import java.util.HashSet;
import java.util.Scanner;

public class ProfAndParties {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        HashSet<Integer> colorSet = new HashSet<>();
        while(n-- > 0){
            int t = sc.nextInt();
            if(colorSet.contains(t)) {
                System.out.println("BOYS");
                return;
            }
            colorSet.add(t);
        }
        System.out.println("GIRLS");
    }
}
