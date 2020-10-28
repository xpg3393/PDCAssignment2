import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *This class is used to display all the screens for Who Wants To Be A Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class GameShowScreens{//implements IGameShowScreens

    private boolean ongoing;
    private Contestant contestant;
    private String[] prizeMoney= new String[]{"2,000", "5,000", "10,000", "20,000", "50,000", "75,000", "150,000",
            "250,000", "500,000", "1,000,000"};
    private String[] answerCorrectness = new String[10];
    private QandA qandA;


    /**
     * constructor initialises the contestant and the game screen
     * @param contestant who is competing
     */
    public GameShowScreens(Contestant contestant){
        ongoing = true;
        this.contestant = contestant;
        qandA = new QandA();
        qandA.getQandA();
    }

    /**
     * starts the game and displays all the screens till the user loses
     * @return an int representing the number of correct questions answered
     */
//    public int startGame(){
//
//        int correctQuestions = 0;
//        boolean correct = false;
//
//        for (int i = 1; i < 11; i++) {
//
//
//
//            if (correct){
//
//                correctQuestions++;
//                answerCorrectness[i-1] = "Correct";
//            }
//            else{
//
//                answerCorrectness[i-1] = "Incorrect";
//                break;
//            }
//        }
//        return correctQuestions;
//    }

    public String[] getQAndA(int question){
        String[] qAndA = new String[5];
        qAndA[0] = this.qandA.questions[question-1];
        qAndA[1] = this.qandA.options[(question-1)*4];
        qAndA[2] = this.qandA.options[(question-1)*4 + 1];
        qAndA[3] = this.qandA.options[(question-1)*4 + 2];
        qAndA[4] = this.qandA.options[(question-1)*4 + 3];

        return qAndA;
    }

    /**
     * helper method checks whether the answer entered by the imposter is correct
     * @param question the question number
     * @return boolean variable representing the correctness of the contestants answer
     */
    public boolean checkAnswer(int question, String answer){

        boolean correct = false;

        try {
            if (!answer.equalsIgnoreCase("a") && !answer.equalsIgnoreCase("b") && !answer.equalsIgnoreCase("c") &&
                    !answer.equalsIgnoreCase("d") && !answer.equalsIgnoreCase("1") && !answer.equalsIgnoreCase("2")) {

                throw new InputMismatchException();
            }
            else if (answer.equalsIgnoreCase(qandA.answers[question-1])) {

                if (question==10){

                    System.out.println("CORRECT! CONGRATULATIONS YOU HAVE BECOME A MILLIONAIRE ");
                }
                else {

                    System.out.println("Correct! You have got yourself $" + prizeMoney[question - 1] +
                            ". Onto the next question.");

                }
                return true;
            }
//            else if (answer.equalsIgnoreCase("1")) {
//
//                if (contestant.isLifeline1()){
//
//                    System.out.println("You have activated your 50/50 lifeline");
//                    contestant.setLifeline1(false);
//                    String[] answers = new String[]{"A", "B", "C", "D"};
//                    String[] remove = new String[2];
//                    int removed = 0;
//                    for (int i = 0; i<answers.length; i++){
//
//                        if (!answers[i].equals(qandA.answers[question-1]) && removed<2){
//                            remove[removed] = answers[i];
//                            removed++;
//                        }
//                    }
//                    System.out.println("The 2 wrong answers are" + Arrays.toString(remove));
//
//                    correct = checkAnswer(gameScreen, question);
//                }
//                else{
//
//                    System.out.println("Unfortunately you have already used your 50/50 lifeline. Please enter your " +
//                            "answer");
//                    correct = checkAnswer(gameScreen, question);
//                }
//            }
//            else if (answer.equalsIgnoreCase("2")) {
//
//                if (contestant.isLifeline2()){
//
//                    System.out.println("You have activated your Double Dip. Please enter your answer");
//                    contestant.setLifeline2(false);
//                    if (checkAnswer(gameScreen, question)){
//
//                        correct = true;
//                    }
//                    else{
//
//                        System.out.println("Your first answer was wrong. Please enter your answer");
//                        correct = checkAnswer(gameScreen, question);
//                    }
//                }
//                else{
//
//                    System.out.println("Unfortunately you have already used your Double Dip lifeline. Please enter " +
//                            "your answer");
//                    correct = checkAnswer(gameScreen, question);
//                }
//            }
        }
        catch (InputMismatchException e){

            System.out.println("Please enter A, B, C or D for the answer. If you want to use a lifeline select 1 or 2");
            correct = checkAnswer(question, answer);
        }
        return correct;
    }

    public String[] getAnswerCorrectness(){
        return answerCorrectness;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public String[] getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(String[] prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public void setAnswerCorrectness(String[] answerCorrectness) {
        this.answerCorrectness = answerCorrectness;
    }

}
