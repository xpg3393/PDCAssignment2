package assignment2.MVC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class Controller implements ActionListener {
    public Model model;
    public View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source==View.timer){
            if (View.registered){
                View.gamePanel = new View.GamePanel();
                View.lost = false;
                View.finished = false;
                view.remove(View.startPanel);
                view.add(View.gamePanel);
                view.validate();
                view.repaint();
            }
            if (View.finished || View.lost){
                view.remove(View.gamePanel);
                View.endPanel = new View.EndPanel();
                view.add(View.endPanel);
                view.validate();
                view.repaint();
            }
        }

        if (source==View.firstNameField){
            View.firstName = View.firstNameField.getText();
        }

        if (source==View.lastNameField){
            View.lastName = View.lastNameField.getText();
        }

        if (source==View.ageField){
            try {
                View.age = Integer.parseInt(View.ageField.getText());
                if (View.age<0 || View.age>120){
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException o){
                View.ageField.setText("Age should be an integer between 0 and 120");
            }
            catch (NumberFormatException o){
                View.ageField.setText("Age should be an integer");
            }
        }

        if (source== View.yes){
            boolean correctAge = false;
            View.firstName = View.firstNameField.getText();
            View.lastName = View.lastNameField.getText();
            try {
                View.age = Integer.parseInt(View.ageField.getText());
                if (View.age<0 || View.age>120){
                    throw new InputMismatchException();
                }
                correctAge = true;
            }
            catch (InputMismatchException o){
                View.ageField.setText("Age should be an integer between 0 and 120");
            }
            if (correctAge) {
                View.contestant = new Contestant(View.firstName, View.lastName, View.age);
                model.setContestant(View.contestant);
                view.add(View.gamePanel);
                view.remove(View.startPanel);
                view.validate();
                view.repaint();
                View.finished = false;
                View.lost = false;
            }

        }

        if (source==View.no){
            System.exit(0);
        }

        if (source== View.lifeline1){

            int count = 0;
            if(!model.checkAnswer(View.questionNumber, "A") ){
                View.option1.setText("");
                count++;
            }
            if(!model.checkAnswer(View.questionNumber, "B")){
                View.option2.setText("");
                count++;
            }
            if(!model.checkAnswer(View.questionNumber, "C")&& count<2){
                View.option3.setText("");
                count++;
            }
            if(!model.checkAnswer(View.questionNumber, "D")&& count<2){
                View.option4.setText("");
            }
            View.gamePanel.remove(View.lifeline1);
            View.gamePanel.validate();
            View.gamePanel.repaint();
        }

        if (source==View.lifeline2){
            View.lifeline2Active = true;
            View.gamePanel.remove(View.lifeline2);
            View.gamePanel.validate();
            View.gamePanel.repaint();
        }

        if (source == View.option1){
            if (!View.lifeline2Active) {
                if (model.checkAnswer(View.questionNumber, "A")) {
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else
                    View.lost = true;
            }
            else{
                if (model.checkAnswer(View.questionNumber, "A")) {
                    View.lifeline2Active = false;
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else {
                    View.lifeline2Active = false;
                    View.option1.setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.option2){
            if (!View.lifeline2Active) {
                if (model.checkAnswer(View.questionNumber, "B")) {
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else
                    View.lost = true;
            }
            else{
                if (model.checkAnswer(View.questionNumber, "B")) {
                    View.lifeline2Active = false;
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else {
                    View.lifeline2Active = false;
                    View.option2.setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.option3){
            if (!View.lifeline2Active) {
                if (model.checkAnswer(View.questionNumber, "C")) {
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else
                    View.lost = true;
            }
            else{
                if (model.checkAnswer(View.questionNumber, "C")) {
                    View.lifeline2Active = false;
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else {
                    View.lifeline2Active = false;
                    View.option3.setText("Incorrect, have another guess");
                }
            }
        }

        if (source == View.option4){
            if (!View.lifeline2Active) {
                if (model.checkAnswer(View.questionNumber, "D")) {
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else
                    View.lost = true;
            }
            else{
                if (model.checkAnswer(View.questionNumber, "D")) {
                    View.lifeline2Active = false;
                    View.moneyCount.setText("$" + model.getPrizeMoney()[View.questionNumber - 1]);
                    View.questionNumber++;
                    View.gamePanel.updateQAndA();
                }
                else {
                    View.lifeline2Active = false;
                    View.option4.setText("Incorrect, have another guess");
                }
            }
        }

    }
}
