/**
 * Created by chewy on 1/15/17.
 */
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        babyNamesTotals babyData = new babyNamesTotals();
        System.out.println("Please give your name: ");
        String name = scan.nextLine();
        System.out.println("and birth year: ");
        String birth = scan.nextLine();
        int birthDate = Integer.parseInt(birth);
        System.out.println("and gender (F/M): ");
        String gender = scan.nextLine();
        System.out.println("and desired year: ");
        String nYear = scan.nextLine();
        int newYear = Integer.parseInt(nYear);
        babyData.whatIsNameInYear(name,birthDate,newYear,gender);
    }
}
