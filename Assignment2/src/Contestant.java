
/**
 * This class is used to represent a contestant used in the Who Wants To Be A Millionaire game show.
 *
 * @author Yash Raniga 19088447
 */
public class Contestant {

    private final String firstName;
    private final String lastName;
    private final Integer age;
    private boolean lifeline1;
    private boolean lifeline2;

    /**
     * Sets the contestants personal information based off input parameters
     *
     * @param firstName First Name
     * @param lastName Last Name
     * @param age Age
     */
    public Contestant(String firstName, String lastName, Integer age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        lifeline1 = true;
        lifeline2 = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public boolean isLifeline1() {
        return lifeline1;
    }

    public boolean isLifeline2() {
        return lifeline2;
    }

    public void setLifeline1(boolean lifeline1) {
        this.lifeline1 = lifeline1;
    }

    public void setLifeline2(boolean lifeline2) {
        this.lifeline2 = lifeline2;
    }
}
