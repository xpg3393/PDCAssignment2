package assignment2.MVC;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *This class is used to display all the screens for Who Wants To Be A Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class Model extends Observable {//implements IGameShowScreens

    private Contestant contestant;
    private final String[] prizeMoney= new String[]{"2,000", "5,000", "10,000", "20,000", "50,000", "75,000", "150,000",
            "250,000", "500,000", "1,000,000"};
    public String[] questions;
    public String[] options;
    public String[] answers;


    /**
     * constructor initialises the contestant and the game screen
     */
    public Model(){
        questions = new String[10];
        options = new String[40];
        answers = new String[10];
        initialiseQAndA();
    }

    public void initialiseQAndA(){
        int count;
        int j;

        try {
            Scanner scanner = new Scanner(new FileReader("qanda.txt"));
            while (scanner.hasNext()) {
                j = 0;
                for (int i = 0; i < 10; i++) {
                    count = 0;

                    questions[i] = scanner.nextLine();
                    while (count < 4) {
                        options[j] = scanner.nextLine();
                        j++;
                        count++;
                    }
                    answers[i] = scanner.nextLine();
                }
            }
        }
        catch (IOException e){
            System.err.println("Error: Cannot read from file");
        }
    }

    public String[] getQAndA(int question){
        String[] qAndA = new String[5];
        qAndA[0] = this.questions[question-1];
        qAndA[1] = this.options[(question-1)*4];
        qAndA[2] = this.options[(question-1)*4 + 1];
        qAndA[3] = this.options[(question-1)*4 + 2];
        qAndA[4] = this.options[(question-1)*4 + 3];

        return qAndA;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    /**
     * helper method checks whether the answer entered by the imposter is correct
     * @param question the question number
     * @return boolean variable representing the correctness of the contestants answer
     */
    public boolean checkAnswer(int question, String answer){

        return answer.equalsIgnoreCase(this.answers[question - 1]);
    }

    public String[] getPrizeMoney() {
        return prizeMoney;
    }


}
