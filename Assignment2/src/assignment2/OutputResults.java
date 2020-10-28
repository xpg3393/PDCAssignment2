import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is used to output the contestants results to a file
 *
 * @author Yash Raniga 19088447
 */
public class OutputResults implements IOutputResults{

    /**
     * This is the method used to output the contestants results to a file
     * @param output the string with the output information
     */
    @Override
    public void outputResults(String output) {

        try {
            PrintWriter out = new PrintWriter(new FileWriter("results.txt"));
            out.println(output);
            out.close();
        }
        catch (IOException ioException){
            System.err.println("Error outputting results");
        }
    }
}
