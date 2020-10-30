

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *This class represents the View in the MVC pattern for Who Wants To Be A 
 * Millionaire
 *
 * @author Yash Raniga 19088447
 */
public class View extends JFrame implements Observer, IView {
    private static JButton option1 = new JButton("A");
    private static JButton option2 = new JButton("B");
    private static JButton option3 = new JButton("C");
    private static JButton option4 = new JButton("D");
    private static JButton lifeline1 = new JButton("50/50");
    private static JButton lifeline2 = new JButton("Double Dip");
    private static JLabel question = new JLabel("Question");
    private static JLabel moneyCount = new JLabel("$0");
    private static javax.swing.Timer timer;
    private static boolean lost;
    private static boolean registered = false;
    private static boolean finished;
    private static Contestant contestant;
    private static JLabel info;
    private static JTextField firstNameField = new JTextField("Enter first name"
            + " here");
    private static JTextField lastNameField = new JTextField("Enter last name "
            + "here");
    private static JTextField ageField = new JTextField("Enter age here");
    private static String firstName, lastName;
    private static int age;
    private static JButton yes = new JButton("YES");
    private static JButton no = new JButton("NO");
    private static Image endImage;
    private static Image gameImage;
    private static Model model;
    private static int questionNumber;
    private static String[] qAndA;
    private static boolean lifeline2Active;
    private static StartPanel startPanel = new StartPanel();
    private static EndPanel endPanel;
    private static GamePanel gamePanel = new GamePanel();

    /**
     * This constructor initializes the JFrame
     */
    public View() {
        registered = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.add(startPanel);
        this.setVisible(true);


    }

    /**
     * this method is to be used by the Controller class to make all objects
     * which require an action listener linked to the Controller class
     * @param actionListener the Controller class
     */
    public void addActionListener(ActionListener actionListener){
        timer = new Timer(10, actionListener);
        timer.start();
        firstNameField.addActionListener(actionListener);
        lastNameField.addActionListener(actionListener);
        ageField.addActionListener(actionListener);
        yes.addActionListener(actionListener);
        no.addActionListener(actionListener);
        option1.addActionListener(actionListener);
        option2.addActionListener(actionListener);
        option3.addActionListener(actionListener);
        option4.addActionListener(actionListener);
        lifeline1.addActionListener(actionListener);
        lifeline2.addActionListener(actionListener);
    }

    /**
     * Update for the observer-observable pattern
     * @param o The observable object, in this case the Model class
     * @param arg the object used to update the questions and answers 
     */
    @Override
    public void update(Observable o, Object arg) {
        this.qAndA = (String[]) arg;
        this.gamePanel.updateQAndA();
        
    }

    /**
     * Start Panel used by the JFrame to register the contestant
     */
    public static class StartPanel extends JPanel{

        /**
         * Constructor initializes the JFrame, all labels, text fields and 
         * buttons
         * 
         */
        public StartPanel(){
            super();
            setPreferredSize(new Dimension(1600,900));
            setBackground(Color.BLACK);
            setLayout(null);

            info = new JLabel();
            info.setText("<html><p style=\"text-align:center;\"> Please enter "
                    + "your first name, last name and age in the fields given "
                    + "below. Please press yes after entering names and age. "
                    + "<br/>T&C: Entry costs $500, Minimum winnings are $1000 "
                    + "and the grand prize is one million dollars ($1,000,000)."
                    + "<br/>Press yes to register yourself as a contestant or "
                    + "no to withdraw yourself from who wants to be a "
                    + "millionaire.<br/>The 50/50 lifeline removes 2 incorrect"
                    + " options and can only be used once.<br/>The Double Dip "
                    + "lifeline gives you a second guess if your first one is "
                    + "wrong and can only be used once.</p><html>");
            info.setSize(1400, 400);
            info.setLocation(100, 50);
            info.setBackground(Color.BLACK);
            info.setForeground(Color.WHITE);
            info.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            info.setFont(new Font("Arial", Font.PLAIN, 25));

            firstNameField.setSize(400, 100);
            firstNameField.setLocation(100, 500);
            firstNameField.setBackground(Color.BLACK);
            firstNameField.setForeground(Color.WHITE);
            firstNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            firstNameField.setFont(new Font("Arial", Font.PLAIN, 20));

            lastNameField.setSize(400, 100);
            lastNameField.setLocation(600, 500);
            lastNameField.setBackground(Color.BLACK);
            lastNameField.setForeground(Color.WHITE);
            lastNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lastNameField.setFont(new Font("Arial", Font.PLAIN, 20));

            ageField.setSize(400, 100);
            ageField.setLocation(1100, 500);
            ageField.setBackground(Color.BLACK);
            ageField.setForeground(Color.WHITE);
            ageField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            ageField.setFont(new Font("Arial", Font.PLAIN, 20));

            yes.setSize(600, 100);
            yes.setLocation(100, 700);
            yes.setBackground(Color.BLACK);
            yes.setForeground(Color.WHITE);
            yes.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            yes.setFont(new Font("Arial", Font.PLAIN, 48));

            no.setSize(600, 100);
            no.setLocation(900, 700);
            no.setBackground(Color.BLACK);
            no.setForeground(Color.WHITE);
            no.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            no.setFont(new Font("Arial", Font.PLAIN, 48));

            add(info);
            add(firstNameField);
            add(lastNameField);
            add(ageField);
            add(yes);
            add(no);
        }

    }

    /**
     * Game Panel is used by the JFrame to play the game
     */
    public static class GamePanel extends JPanel{


        /**
         * Constructor initializes the Panel, all buttons and labels
         */
        public GamePanel(){
            super();
            setPreferredSize(new Dimension(1600,900));
            setBackground(Color.WHITE);

            lifeline2Active = false;
            
            questionNumber = 1;
            qAndA = new String[5];

            lifeline1 = new JButton("50/50");
            lifeline1.setLocation(50,50);
            lifeline1.setSize(250,100);
            lifeline1.setBackground(Color.BLACK);
            lifeline1.setForeground(Color.WHITE);
            lifeline1.setFont(new Font("Arial", Font.PLAIN, 48));

            lifeline2 = new JButton("Double Dip");
            lifeline2.setLocation(50,150);
            lifeline2.setSize(250,100);
            lifeline2.setBackground(Color.BLACK);
            lifeline2.setForeground(Color.WHITE);
            lifeline2.setFont(new Font("Arial", Font.PLAIN, 40));

            option1 = new JButton(qAndA[1]);
            option1.setLocation(250,644);
            option1.setSize(475,75);
            option1.setBackground(Color.BLACK);
            option1.setForeground(Color.WHITE);
            option1.setOpaque(false);
            option1.setContentAreaFilled(false);
            option1.setBorderPainted(false);
            option1.setFont(new Font("Arial", Font.PLAIN, 30));

            option2 = new JButton(qAndA[2]);
            option2.setLocation(920,644);
            option2.setSize(475,75);
            option2.setBackground(Color.BLACK);
            option2.setForeground(Color.WHITE);
            option2.setOpaque(false);
            option2.setContentAreaFilled(false);
            option2.setBorderPainted(false);
            option2.setFont(new Font("Arial", Font.PLAIN, 30));

            option3 = new JButton(qAndA[3]);
            option3.setLocation(250,744);
            option3.setSize(475,75);
            option3.setBackground(Color.BLACK);
            option3.setForeground(Color.WHITE);
            option3.setOpaque(false);
            option3.setContentAreaFilled(false);
            option3.setBorderPainted(false);
            option3.setFont(new Font("Arial", Font.PLAIN, 30));

            option4 = new JButton(qAndA[4]);
            option4.setLocation(920,744);
            option4.setSize(475,75);
            option4.setBackground(Color.BLACK);
            option4.setForeground(Color.WHITE);
            option4.setOpaque(false);
            option4.setContentAreaFilled(false);
            option4.setBorderPainted(false);
            option4.setFont(new Font("Arial", Font.PLAIN, 30));

            question = new JLabel(qAndA[0]);
            question.setLocation(225,485);
            question.setSize(1150,125);
            question.setBackground(Color.BLACK);
            question.setForeground(Color.WHITE);
            question.setBorder(null);
            question.setFont(new Font("Arial", Font.PLAIN, 30));

            moneyCount = new JLabel("$0");
            moneyCount.setLocation(1150,55);
            moneyCount.setSize(1150,125);
            moneyCount.setBackground(Color.BLUE);
            moneyCount.setForeground(Color.WHITE);
            moneyCount.setBorder(null);
            moneyCount.setFont(new Font("Arial", Font.PLAIN, 48));

            setLayout(null);
            add(lifeline1);
            add(lifeline2);
            add(option1);
            add(option2);
            add(option3);
            add(option4);
            add(question);
            add(moneyCount);


            try {
                gameImage = ImageIO.read(new File("images/Millionaire.png"));
            } catch (IOException ignored) {
            }
        }

        /**
         * this method refreshes the option buttons and the question label with 
         * a new set of question and answers
         */
        public void updateQAndA(){
            if (questionNumber<=10) {
                question.setText(qAndA[0]);
                question.setBackground(Color.BLACK);
                option1.setText(qAndA[1]);
                option1.setBackground(Color.BLACK);
                option1.setOpaque(false);
                option2.setText(qAndA[2]);
                option2.setBackground(Color.BLACK);
                option3.setText(qAndA[3]);
                option3.setBackground(Color.BLACK);
                option4.setText(qAndA[4]);
                option4.setBackground(Color.BLACK);
            }
            else{
                finished = true;

            }
        }

        /**
         * overridden paint component to draw the image in the background 
         * @param g 
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(gameImage, 0, 0, null);
        }

    }

    /**
     * End Panel is used by the JFrame to display an image after the user has 
     * finished playing Who Wants to Be A Millionaire
     */
    public static class EndPanel extends JPanel{

        /**
         * Constructor initializes the Panel and sets the background image based
         * on the users results in the game
         */
        public EndPanel(){
            super();
            setPreferredSize(new Dimension(1600,900) );
            if (!lost) {
                try {
                    endImage = ImageIO.read(new File("images/winner.jpg"));
                } catch (IOException ignored) {
                }
            }
            else{
                try {
                    endImage = ImageIO.read(new File("images/loser.jpg"));
                } catch (IOException ignored) {
                }
            }
        }

        /**
         * paint component overridden to draw the image 
         * @param g 
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(endImage, 0, 0, null);
        }

    }

    public static JButton getOption1() {
        return option1;
    }

    public static void setOption1(JButton option1) {
        View.option1 = option1;
    }

    public static JButton getOption2() {
        return option2;
    }

    public static void setOption2(JButton option2) {
        View.option2 = option2;
    }

    public static JButton getOption3() {
        return option3;
    }

    public static void setOption3(JButton option3) {
        View.option3 = option3;
    }

    public static JButton getOption4() {
        return option4;
    }

    public static void setOption4(JButton option4) {
        View.option4 = option4;
    }

    public static JButton getLifeline1() {
        return lifeline1;
    }

    public static void setLifeline1(JButton lifeline1) {
        View.lifeline1 = lifeline1;
    }

    public static JButton getLifeline2() {
        return lifeline2;
    }

    public static void setLifeline2(JButton lifeline2) {
        View.lifeline2 = lifeline2;
    }

    public static JLabel getQuestion() {
        return question;
    }

    public static void setQuestion(JLabel question) {
        View.question = question;
    }

    public static JLabel getMoneyCount() {
        return moneyCount;
    }

    public static void setMoneyCount(JLabel moneyCount) {
        View.moneyCount = moneyCount;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void setTimer(Timer timer) {
        View.timer = timer;
    }

    public static boolean isLost() {
        return lost;
    }

    public static void setLost(boolean lost) {
        View.lost = lost;
    }

    public static boolean isRegistered() {
        return registered;
    }

    public static void setRegistered(boolean registered) {
        View.registered = registered;
    }

    public static boolean isFinished() {
        return finished;
    }

    public static void setFinished(boolean finished) {
        View.finished = finished;
    }

    public static Contestant getContestant() {
        return contestant;
    }

    public static void setContestant(Contestant contestant) {
        View.contestant = contestant;
    }

    public static JLabel getInfo() {
        return info;
    }

    public static void setInfo(JLabel info) {
        View.info = info;
    }

    public static JTextField getFirstNameField() {
        return firstNameField;
    }

    public static void setFirstNameField(JTextField firstNameField) {
        View.firstNameField = firstNameField;
    }

    public static JTextField getLastNameField() {
        return lastNameField;
    }

    public static void setLastNameField(JTextField lastNameField) {
        View.lastNameField = lastNameField;
    }

    public static JTextField getAgeField() {
        return ageField;
    }

    public static void setAgeField(JTextField ageField) {
        View.ageField = ageField;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        View.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        View.lastName = lastName;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        View.age = age;
    }

    public static JButton getYes() {
        return yes;
    }

    public static void setYes(JButton yes) {
        View.yes = yes;
    }

    public static JButton getNo() {
        return no;
    }

    public static void setNo(JButton no) {
        View.no = no;
    }

    public static Image getEndImage() {
        return endImage;
    }

    public static void setEndImage(Image endImage) {
        View.endImage = endImage;
    }

    public static Image getGameImage() {
        return gameImage;
    }

    public static void setGameImage(Image gameImage) {
        View.gameImage = gameImage;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        View.model = model;
    }

    public static int getQuestionNumber() {
        return questionNumber;
    }

    public static void setQuestionNumber(int questionNumber) {
        View.questionNumber = questionNumber;
    }

    public static String[] getqAndA() {
        return qAndA;
    }

    public static void setqAndA(String[] qAndA) {
        View.qAndA = qAndA;
    }

    public static boolean isLifeline2Active() {
        return lifeline2Active;
    }

    public static void setLifeline2Active(boolean lifeline2Active) {
        View.lifeline2Active = lifeline2Active;
    }

    public static StartPanel getStartPanel() {
        return startPanel;
    }

    public static void setStartPanel(StartPanel startPanel) {
        View.startPanel = startPanel;
    }

    public static EndPanel getEndPanel() {
        return endPanel;
    }

    public static void setEndPanel(EndPanel endPanel) {
        View.endPanel = endPanel;
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public static void setGamePanel(GamePanel gamePanel) {
        View.gamePanel = gamePanel;
    }
}
