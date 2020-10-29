package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

/**
 *This class represents the Controller in the MVC pattern for Who Wants To Be A 
 * Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class Controller implements ActionListener {
    private Model model;
    private View view;

    /**
     * Constructor initializes the model, view and adds this class as an 
     * action listener in the view class
     * @param model
     * @param view 
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
    }

    /**
     * This method handles all actions performed in the View Class
     * @param e the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source==View.getTimer()){
            if (View.isRegistered()){
                View.setGamePanel(new View.GamePanel());
                View.setLost(false);
                View.setFinished(false);
                view.remove(View.getStartPanel());
                view.add(View.getGamePanel());
                view.validate();
                view.repaint();
            }
            if (View.isFinished() || View.isLost()){
                view.remove(View.getGamePanel());
                View.setEndPanel(new View.EndPanel());
                view.add(View.getEndPanel());
                view.validate();
                view.repaint();
            }
        }

        if (source==View.getFirstNameField()){
            try{
                View.setFirstName(View.getFirstNameField().getText());
                char[] check = View.getFirstName().toCharArray();

                for(char c: check){
                    if(!Character.isLetter(c)){
                        throw new InputMismatchException();
                    }
                }
            }
            catch (InputMismatchException o){
                View.getFirstNameField().setText("First name should only contain letters");
            }
        }

        if (source==View.getLastNameField()){
            try{
                View.setLastName(View.getLastNameField().getText());
                char[] check = View.getLastName().toCharArray();

                for(char c: check){
                    if(!Character.isLetter(c)){
                        throw new InputMismatchException();
                    }
                }
            }
            catch (InputMismatchException o){
                View.getLastNameField().setText("Last name should only contain letters");
            }
        }

        if (source==View.getAgeField()){
            try {
                View.setAge(Integer.parseInt(View.getAgeField().getText()));
                if (View.getAge()<0 || View.getAge()>120){
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException o){
                View.getAgeField().setText("Age should be between 0 and 120");
            }
            catch (NumberFormatException o){
                View.getAgeField().setText("Age should be an integer");
            }
        }

        if (source== View.getYes()){
            boolean correctAge = false;
            boolean correctFN = false;
            boolean correctLN = false;
            try{
                View.setFirstName(View.getFirstNameField().getText());
                char[] check = View.getFirstName().toCharArray();

                for(char c: check){
                    if(!Character.isLetter(c)){
                        throw new InputMismatchException();
                    }
                }
                correctFN = true;
            }
            catch (InputMismatchException o){
                View.getFirstNameField().setText("First name should only contain letters");
            }
            try{
                View.setLastName(View.getLastNameField().getText());
                char[] check = View.getLastName().toCharArray();

                for(char c: check){
                    if(!Character.isLetter(c)){
                        throw new InputMismatchException();
                    }
                }
                correctLN = true;
                
            }
            catch (InputMismatchException o){
                View.getLastNameField().setText("Last name should only contain letters");
            }
            try {
                View.setAge(Integer.parseInt(View.getAgeField().getText()));
                if (View.getAge()<0 || View.getAge()>120){
                    throw new InputMismatchException();
                }
                correctAge = true;

            }
            catch (InputMismatchException o){
                View.getAgeField().setText("Age should be between 0 and 120");
            }
            catch (NumberFormatException o){
                View.getAgeField().setText("Age should be an integer");
            }
            if (correctAge && correctFN && correctLN) {
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

        }

        if (source==View.getNo()){
            System.exit(0);
        }

        if (source== View.getLifeline1()){

            int count = 0;
            if(!model.checkAnswer(View.getQuestionNumber(), "A") ){
                View.getOption1().setText("");
                count++;
            }
            if(!model.checkAnswer(View.getQuestionNumber(), "B")){
                View.getOption2().setText("");
                count++;
            }
            if(!model.checkAnswer(View.getQuestionNumber(), "C")&& count<2){
                View.getOption3().setText("");
                count++;
            }
            if(!model.checkAnswer(View.getQuestionNumber(), "D")&& count<2){
                View.getOption4().setText("");
            }
            View.getGamePanel().remove(View.getLifeline1());
            View.getGamePanel().validate();
            View.getGamePanel().repaint();
        }

        if (source==View.getLifeline2()){
            View.setLifeline2Active(true);
            View.getGamePanel().remove(View.getLifeline2());
            View.getGamePanel().validate();
            View.getGamePanel().repaint();
        }

        if (source == View.getOption1()){
            if (!View.isLifeline2Active()) {
                if (model.checkAnswer(View.getQuestionNumber(), "A")) {
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else
                    View.setLost(true);
            }
            else{
                if (model.checkAnswer(View.getQuestionNumber(), "A")) {
                    View.setLifeline2Active(false);
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else {
                    View.setLifeline2Active(false);
                    View.getOption1().setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.getOption2()){
            if (!View.isLifeline2Active()) {
                if (model.checkAnswer(View.getQuestionNumber(), "B")) {
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else
                    View.setLost(true);
            }
            else{
                if (model.checkAnswer(View.getQuestionNumber(), "B")) {
                    View.setLifeline2Active(false);
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else {
                    View.setLifeline2Active(false);
                    View.getOption2().setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.getOption3()){
            if (!View.isLifeline2Active()) {
                if (model.checkAnswer(View.getQuestionNumber(), "C")) {
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else
                    View.setLost(true);
            }
            else{
                if (model.checkAnswer(View.getQuestionNumber(), "C")) {
                    View.setLifeline2Active(false);
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else {
                    View.setLifeline2Active(false);
                    View.getOption3().setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.getOption4()){
            if (!View.isLifeline2Active()) {
                if (model.checkAnswer(View.getQuestionNumber(), "D")) {
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else
                    View.setLost(true);
            }
            else{
                if (model.checkAnswer(View.getQuestionNumber(), "D")) {
                    View.setLifeline2Active(false);
                    View.getMoneyCount().setText("$" + model.getPrizeMoney()[View.getQuestionNumber() - 1]);
                    View.setQuestionNumber(View.getQuestionNumber()+1);
                    model.getQAndA(View.getQuestionNumber());
                    View.getGamePanel().updateQAndA();
                }
                else {
                    View.setLifeline2Active(false);
                    View.getOption4().setText("Incorrect, have another guess");
                }
            }
        }

    }
}
