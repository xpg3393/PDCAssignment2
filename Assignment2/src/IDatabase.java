
/**
 * Interface for Database class
 *
 * @author xpg3393
 */
public interface IDatabase {

    public void dbsetup(String[] questions, String[] options, String[] answers);

    public void addResponse(String firstName, String lastName, String question,
            String correctAnswer, String givenAnswer);

}
