import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is used to register a contestant in the Who Wants To Be A Millionaire game show. It ensures the user knows
 * the risks and rewards before entering
 *
 * @author Yash Raniga 19088447
 */
public class EnterContestant implements IEnterContestant{

    /**
     * Method is used to register the contestant.
     * @return Contestants details if they agree to the details. Returns null if contestant doesn't agree to
     */
    @Override
    public Contestant registerContestant() {

        String firstName = firstName();

        String lastName = lastName();

        Integer age = age();

        Contestant contestant = new Contestant(firstName, lastName, age);

        System.out.println("Are you sure you want to compete? \nEntry costs $500, Minimum winnings are $1000 and " +
                "the grand prize is one million dollars ($1,000,000)");
        System.out.println("Press y to register yourself as a contestant or n to withdraw yourself from who wants" +
                "to be a millionaire.");

        if (register()){
            return contestant;
        }
        else {
            return null;
        }

    }

    /**
     * Helper method for registerContestant to get the first name from user
     * @return String with the users first name
     */
    private String firstName(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your first name?");
        String firstName = scanner.nextLine();

        return firstName;
    }

    /**
     * Helper method for registerContestant to get the last name from user
     * @return String with the users last name
     */
    private String lastName(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your last name?");
        String lastName = scanner.nextLine();

        return lastName;
    }

    /**
     * Helper method for registerContestant to get the user's age. It uses recursion to ensure there is no invalid input
     * @return Integer representing users age
     */
    private Integer age(){

        Integer age;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What is your age?");
            age = scanner.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Please enter a number");
            age = age();
        }

        return age;
    }

    /**
     * Helper method for registerContestant to get the users decision on entering the game show
     * @return boolean object representing the users decision
     */
    private boolean register(){

        Scanner scanner = new Scanner(System.in);
        String decision;
        boolean register;

        try {
            decision = scanner.nextLine();

            if (decision.equalsIgnoreCase("y")){
                register = true;
            }
            else if (decision.equalsIgnoreCase("n")){
                register = false;
            }
            else{
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Please enter either y or n.");
            register = register();
        }

        return register;
    }
}
