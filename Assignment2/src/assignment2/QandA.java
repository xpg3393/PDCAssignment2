import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * this class is used to find out the questions, options, answers using a file reader
 *
 * @author Yash Raniga 19088447
 */
public class QandA implements IGetQandA{

    public String[] questions;
    public String[] options;
    public String[] answers;

    /**
     * constructor initialises the length of the questions, options, answers string arrays
     */
    public QandA(){
        questions = new String[10];
        options = new String[40];
        answers = new String[10];

    }

    /**
     * this method uses a file reader to get the questions, options, answers from the qanda.txt file
     */
    @Override
    public void getQandA(){
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

}
