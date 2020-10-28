/**
 * This class is used to format the string which will be outputted in the OutputResults class
 *
 * @author Yash Raniga 19088447
 */
public class ReturnResults implements IReturnResults{

    /**
     *This method formats the string which will be outputted in the OutputResults class
     *
     * @param contestant the contestant and all his details
     * @param answerCorrectness string array representing the correctness of all answers
     * @param qanda QandA class with questions and answers
     * @return the formatted string for output
     */
    @Override
    public String returnResults(Contestant contestant, String[] answerCorrectness, QandA qanda) {
        String fileIO = "Name: "+contestant.getFirstName()+" "+contestant.getLastName()+", Age: "+contestant.getAge()
                +", Results:\n";
        for (int i = 0; i<10; i++){

            fileIO+=qanda.questions[i]+" Correct Answer: "+qanda.answers[i]+", your answer was "+answerCorrectness[i]+"\n";
            if (answerCorrectness[i].equalsIgnoreCase("Incorrect")){
                break;
            }
        }
        return fileIO;
    }


}
