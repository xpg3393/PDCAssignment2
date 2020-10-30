
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class represents the Database in the Who Wants To Be A Millionaire Game
 * Show
 *
 * @author Yash Raniga 19088447
 */
public class Database implements IDatabase {

    Connection conn = null;
    String url = "jdbc:derby:QuestionsAndAnswers;create=true";  //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password
    String inputTableName = "QuestionsAndAnswers";
    String responses = "Responses";

    /**
     * This method is used to create the database, and sets the questions,
     * options and answers from the database
     *
     * @param questions the array of questions for the game
     * @param options the array of options for the game
     * @param answers the array of answers for the game
     */
    @Override
    public void dbsetup(String[] questions, String[] options, String[] answers) {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();

            if (!checkTableExisting(inputTableName)) {
                statement.executeUpdate("CREATE TABLE " + inputTableName + " "
                        + "(question VARCHAR(255), option1 VARCHAR(255), "
                        + "option2 VARCHAR(255), option3 VARCHAR(255), option4 VARCHAR(255), "
                        + "answer VARCHAR(255))");

                statement.executeUpdate("INSERT INTO " + inputTableName + " VALUES ('Question 1: What is 2+2?', '2', '3', '4', '5', 'C'), "
                        + "('Question 2: What is 2x2?', '2', '3', '4', '5', 'C'), "
                        + "('Question 3: What is 9 squared divided by 3?', '27', '3', '6', '9', 'A'), "
                        + "('Question 4: What is the capital of Australia?', 'Sydney', 'Melbourne', 'Perth', 'Canberra', 'D'), "
                        + "('Question 5: Who is the president of the United States of America?', 'Donald Duck', 'Ivanka Trump', 'Donald Pump', 'Donald Trump', 'D'), "
                        + "('Question 6: What do cows drink?', 'Water', 'Milk', 'Pina Coladas', 'Gin and Tonic', 'A'), "
                        + "('Question 7: What states the wishes of the disposal of a persons property after death?', 'Will', 'Bill', 'Kill', 'Nil', 'A'), "
                        + "('Question 8: How many colours in the rainbow?', '5', '6', '7', '8', 'C'), "
                        + "('Question 9: What does OOP stand for?', 'Open Oriented Programming', 'Object Oriented Programming', 'Orange Olive Programming', 'Opal Oval Pear', 'B'), "
                        + "('Question 10: In which city is the Empire State Building?', 'Chicago', 'Las Vegas', 'New York', 'Dallas', 'C')");
            }

            ResultSet rs = statement.executeQuery("SELECT * FROM " + inputTableName);
            int i = 0;
            int j = 0;
            while (rs.next()) {
                questions[i] = rs.getString(1);
                answers[i] = rs.getString(6);
                i++;

                options[j] = rs.getString(2);
                j++;
                options[j] = rs.getString(3);
                j++;
                options[j] = rs.getString(4);
                j++;
                options[j] = rs.getString(5);
                j++;

            }

            if (!checkTableExisting(responses)) {
                statement.executeUpdate("CREATE TABLE " + responses + " (firstName VARCHAR(255), lastName VARCHAR(255), question VARCHAR(255), correctAnswer VARCHAR(255), givenAnswer VARCHAR(255))");
            }

            statement.close();

        } catch (Throwable e) {
            System.out.println(e);

        }
    }

    /**
     * this method is called after the user answers a question, and records
     * their response in the database
     *
     * @param firstName the contestants first name
     * @param lastName the contestants last name
     * @param question the question which has been answered
     * @param correctAnswer the correct answer for the question
     * @param givenAnswer the answer given by the contestant
     */
    @Override
    public void addResponse(String firstName, String lastName, String question,
            String correctAnswer, String givenAnswer) {
        try {
            Statement statement = conn.createStatement();

            statement.executeUpdate("INSERT INTO " + this.responses + " VALUES "
                    + "('" + firstName + "', '" + lastName + "', '" + question + "', "
                    + "'" + correctAnswer + "', '" + givenAnswer + "')");
        } catch (Throwable e) {
            System.out.println(e);
        }

    }

    /**
     * this private method is used to check if a table exists in the database
     *
     * @param newTableName the tables name
     * @return true if table exists
     */
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }
}
