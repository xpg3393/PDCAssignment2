package assignment2.MVC;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

public class View extends JFrame implements Observer {
    public static JButton option1 = new JButton("A");
    public static JButton option2 = new JButton("B");
    public static JButton option3 = new JButton("C");
    public static JButton option4 = new JButton("D");
    public static JButton lifeline1 = new JButton("50/50");
    public static JButton lifeline2 = new JButton("Double Dip");
    public static JLabel question = new JLabel("Question");
    public static JLabel moneyCount = new JLabel("$0");
    public static javax.swing.Timer timer;
    public static boolean lost;
    public static boolean registered = false;
    public static boolean finished;
    public static Contestant contestant;
    public static JLabel info;
    public static JTextField firstNameField = new JTextField("Enter first name here");
    public static JTextField lastNameField = new JTextField("Enter last name here");
    public static JTextField ageField = new JTextField("Enter age here");
    public static String firstName, lastName;
    public static int age;
    public static JButton yes = new JButton("YES");
    public static JButton no = new JButton("NO");
    public static Image endImage;
    public static Image gameImage;
    public static Model model;
    public static int questionNumber;
    public static String[] qAndA;
    public static boolean lifeline2Active;
    public static StartPanel startPanel = new StartPanel();
    public static EndPanel endPanel;
    public static GamePanel gamePanel = new GamePanel();

    /**
     * This constructor initialises the Human array, the buttons, and both panels
     */
    public View() {
        registered = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.add(startPanel);
        this.setVisible(true);


    }

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

    @Override
    public void update(Observable o, Object arg) {

    }

    public static class StartPanel extends JPanel{

        public StartPanel(){
            super();
            setPreferredSize(new Dimension(1600,900));
            setBackground(Color.BLACK);
            setLayout(null);

            info = new JLabel();
            info.setText("<html><p style=\"text-align:center;\"> Please enter your first name, last name and age in the " +
                    "fields given below. Please press enter after entering names and age <br/>T&C: Entry costs $500, " +
                    "Minimum winnings are $1000 and the grand prize is one million dollars ($1,000,000)<br/>Press yes to " +
                    "register yourself as a contestant or no to withdraw yourself from who wants to be a millionaire.</p><html>");
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
     * This class is used to draw the GUI Panel
     */
    public static class GamePanel extends JPanel{


        public GamePanel(){
            super();
            setPreferredSize(new Dimension(1600,900));
            setBackground(Color.WHITE);

            lifeline2Active = false;

            model = new Model();
            questionNumber = 1;
            qAndA = model.getQAndA(questionNumber);

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
            option1.setFont(new Font("Arial", Font.PLAIN, 35));

            option2 = new JButton(qAndA[2]);
            option2.setLocation(920,644);
            option2.setSize(475,75);
            option2.setBackground(Color.BLACK);
            option2.setForeground(Color.WHITE);
            option2.setOpaque(false);
            option2.setContentAreaFilled(false);
            option2.setBorderPainted(false);
            option2.setFont(new Font("Arial", Font.PLAIN, 35));

            option3 = new JButton(qAndA[3]);
            option3.setLocation(250,744);
            option3.setSize(475,75);
            option3.setBackground(Color.BLACK);
            option3.setForeground(Color.WHITE);
            option3.setOpaque(false);
            option3.setContentAreaFilled(false);
            option3.setBorderPainted(false);
            option3.setFont(new Font("Arial", Font.PLAIN, 35));

            option4 = new JButton(qAndA[4]);
            option4.setLocation(920,744);
            option4.setSize(475,75);
            option4.setBackground(Color.BLACK);
            option4.setForeground(Color.WHITE);
            option4.setOpaque(false);
            option4.setContentAreaFilled(false);
            option4.setBorderPainted(false);
            option4.setFont(new Font("Arial", Font.PLAIN, 35));

            question = new JLabel(qAndA[0]);
            question.setLocation(225,485);
            question.setSize(1150,125);
            question.setBackground(Color.BLACK);
            question.setForeground(Color.WHITE);
            question.setBorder(null);
            question.setFont(new Font("Arial", Font.PLAIN, 35));

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

        public void updateQAndA(){
            if (questionNumber<=10) {
                qAndA = model.getQAndA(questionNumber);
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

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(gameImage, 0, 0, null);
        }

    }

    public static class EndPanel extends JPanel{

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(endImage, 0, 0, null);
        }

    }

}
