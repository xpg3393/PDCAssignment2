
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class represents the Model in the MVC pattern for Who Wants To Be A
 * Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class Model extends Observable implements IModel {

    private Contestant contestant;
    private final String[] prizeMoney = new String[]{"2,000", "5,000", "10,000",
        "20,000", "50,000", "75,000", "150,000", "250,000", "500,000",
        "1,000,000"};
    public String[] questions;
    public String[] options;
    public String[] answers;
    public String[] currentQandO;
    public Database database;

    /**
     * constructor initializes the questions, options, answers, and the database
     */
    public Model() {
        questions = new String[10];
        options = new String[40];
        answers = new String[10];
        database = new Database();
        currentQandO = new String[5];
        database.dbsetup(questions, options, answers);
    }

    /**
     * sets the current questions and options for the question specified
     *
     * @param question the number of the question
     */
    public void getQAndA(int question) {
        if (question < 11) {

            currentQandO[0] = this.questions[question - 1];
            currentQandO[1] = this.options[(question - 1) * 4];
            currentQandO[2] = this.options[(question - 1) * 4 + 1];
            currentQandO[3] = this.options[(question - 1) * 4 + 2];
            currentQandO[4] = this.options[(question - 1) * 4 + 3];

            this.setChanged();
            this.notifyObservers(this.currentQandO);
        }
    }

    /**
     * checks the correctness of the contestants answer and stores the response
     * in the database
     *
     * @param question the question number
     * @param answer the answer given
     * @return
     */
    public boolean checkAnswer(int question, String answer) {

        database.addResponse(this.contestant.getFirstName(),
                this.contestant.getLastName(), this.questions[question - 1],
                this.answers[question - 1], answer);

        return answer.equalsIgnoreCase(this.answers[question - 1]);
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Contestant getContestant() {
        return this.contestant;
    }

    public String[] getPrizeMoney() {
        return prizeMoney;
    }

}
