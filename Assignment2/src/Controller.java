
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

/**
 * This class represents the Controller in the MVC pattern for Who Wants To Be A
 * Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class Controller implements ActionListener {

    private Model model;
    private View view;
    private boolean correctAge;
    private boolean correctFN;
    private boolean correctLN;

    /**
     * Constructor initializes the model, view and adds this class as an action
     * listener in the view class
     *
     * @param model
     * @param view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
        correctAge = false;
        correctFN = false;
        correctLN = false;
    }

    /**
     * This method handles all actions performed in the View Class
     *
     * @param e the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == View.getTimer()) {
            if (View.isRegistered()) {
                registered();
            }
            if (View.isFinished() || View.isLost()) {
                gameFinished();
            }
        }

        if (source == View.getFirstNameField()) {
            checkFirstName();
        }

        if (source == View.getLastNameField()) {
            checkLastName();
        }

        if (source == View.getAgeField()) {

        }

        if (source == View.getYes()) {
            checkFirstName();
            checkLastName();
            checkAge();

            if (correctAge && correctFN && correctLN) {
                startGame();
            }

        }

        if (source == View.getNo()) {
            System.exit(0);
        }

        if (source == View.getLifeline1()) {
            lifeline1Activated();
        }

        if (source == View.getLifeline2()) {
            lifeline2Activated();
        }

        if (source == View.getOption1()) {
            checkQuestion("A");
        }

        if (source == View.getOption2()) {
            checkQuestion("B");
        }

        if (source == View.getOption3()) {
            checkQuestion("C");
        }

        if (source == View.getOption4()) {
            checkQuestion("D");
        }

    }

    private void registered() {
        View.setGamePanel(new View.GamePanel());
        View.setLost(false);
        View.setFinished(false);
        view.remove(View.getStartPanel());
        view.add(View.getGamePanel());
        view.validate();
        view.repaint();
    }

    private void gameFinished() {
        view.remove(View.getGamePanel());
        View.setEndPanel(new View.EndPanel());
        view.add(View.getEndPanel());
        view.validate();
        view.repaint();
    }

    private void checkFirstName() {
        try {
            View.setFirstName(View.getFirstNameField().getText());
            char[] check = View.getFirstName().toCharArray();

            for (char c : check) {
                if (!Character.isLetter(c)) {
                    throw new InputMismatchException();
                }
            }
            this.correctFN = true;

        } catch (InputMismatchException o) {
            View.getFirstNameField().setText("First name should only contain letters");
        }
    }

    private void checkLastName() {
        try {
            View.setLastName(View.getLastNameField().getText());
            char[] check = View.getLastName().toCharArray();

            for (char c : check) {
                if (!Character.isLetter(c)) {
                    throw new InputMismatchException();
                }
            }
            this.correctLN = true;

        } catch (InputMismatchException o) {
            View.getLastNameField().setText("Last name should only contain letters");
        }
    }

    private void checkAge() {
        try {
            View.setAge(Integer.parseInt(View.getAgeField().getText()));
            if (View.getAge() < 0 || View.getAge() > 120) {
                throw new InputMismatchException();
            }
            this.correctAge = true;

        } catch (InputMismatchException o) {
            View.getAgeField().setText("Age should be between 0 and 120");
        } catch (NumberFormatException o) {
            View.getAgeField().setText("Age should be an integer");
        }
    }

    private void startGame() {
        View.setContestant(new Contestant(View.getFirstName(), View.getLastName(), View.getAge()));
        model.setContestant(View.getContestant());
        view.add(View.getGamePanel());
        view.remove(View.getStartPanel());
        view.validate();
        view.repaint();
        View.setFinished(false);
        View.setLost(false);
        model.getQAndA(1);
    }

    private void lifeline1Activated() {

        int count = 0;
        if (!model.checkAnswer(View.getQuestionNumber(), "A")) {
            View.getOption1().setText("");
            count++;
        }
        if (!model.checkAnswer(View.getQuestionNumber(), "B")) {
            View.getOption2().setText("");
            count++;
        }
        if (!model.checkAnswer(View.getQuestionNumber(), "C") && count < 2) {
            View.getOption3().setText("");
            count++;
        }
        if (!model.checkAnswer(View.getQuestionNumber(), "D") && count < 2) {
            View.getOption4().setText("");
        }
        View.getGamePanel().remove(View.getLifeline1());
        View.getGamePanel().validate();
        View.getGamePanel().repaint();
    }

    private void lifeline2Activated() {
        View.setLifeline2Active(true);
        View.getGamePanel().remove(View.getLifeline2());
        View.getGamePanel().validate();
        View.getGamePanel().repaint();
    }

    private void checkQuestion(String answer) {
        if (!View.isLifeline2Active()) {
            if (model.checkAnswer(View.getQuestionNumber(), answer)) {
                View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                View.setQuestionNumber(View.getQuestionNumber() + 1);
                model.getQAndA(View.getQuestionNumber());
                View.getGamePanel().updateQAndA();
            } else {
                View.setLost(true);
            }
        } else {
            if (model.checkAnswer(View.getQuestionNumber(), answer)) {
                View.setLifeline2Active(false);
                View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                View.setQuestionNumber(View.getQuestionNumber() + 1);
                model.getQAndA(View.getQuestionNumber());
                View.getGamePanel().updateQAndA();
            } else {
                View.setLifeline2Active(false);
                if (answer.equalsIgnoreCase("A")) {
                    View.getOption1().setText("Incorrect, have another guess");
                } else if (answer.equalsIgnoreCase("B")) {
                    View.getOption2().setText("Incorrect, have another guess");
                } else if (answer.equalsIgnoreCase("C")) {
                    View.getOption3().setText("Incorrect, have another guess");
                } else if (answer.equalsIgnoreCase("D")) {
                    View.getOption4().setText("Incorrect, have another guess");
                }
            }
        }
    }

}
