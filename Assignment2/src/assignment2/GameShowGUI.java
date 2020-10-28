import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentListener;

/**
 * This class is the GUI to display the ZombieSimulator game
 *
 * @author Yash Raniga 19088447
 */
public class GameShowGUI extends JPanel implements ActionListener
{
    private JButton option1, option2, option3, option4, lifeline1, lifeline2;
    private JLabel question, moneyCount;
    private javax.swing.Timer timer;
    private JPanel startPanel, endPanel;
    private GamePanel gamePanel;
    private boolean lost, registered, finished;
    private Contestant contestant;

    /**
     * This constructor initialises the Human array, the buttons, and both panels
     */
    public GameShowGUI() {
        super(new BorderLayout());

        registered = false;

        startPanel = new StartPanel();
        add(startPanel);

        timer = new Timer(100,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source==timer){
            validate();
            repaint();
            if (registered){
                gamePanel = new GamePanel(contestant);
                lost = false;
                finished = false;
                remove(startPanel);
                add(gamePanel);
            }
            if (finished || lost){
                endPanel = new EndPanel();
                remove(gamePanel);
                add(endPanel);
            }
        }
    }

    private class StartPanel extends JPanel implements ActionListener {
        private JLabel info;
        private JTextField firstNameField, lastNameField, ageField;
        private String firstName, lastName;
        private int age;
        private JButton yes, no;

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

            firstNameField = new JTextField("Enter first name here");
            firstNameField.addActionListener(this::actionPerformed);
            firstNameField.setSize(400, 100);
            firstNameField.setLocation(100, 500);
            firstNameField.setBackground(Color.BLACK);
            firstNameField.setForeground(Color.WHITE);
            firstNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            firstNameField.setFont(new Font("Arial", Font.PLAIN, 20));

            lastNameField = new JTextField("Enter last name here");
            lastNameField.addActionListener(this::actionPerformed);
            lastNameField.setSize(400, 100);
            lastNameField.setLocation(600, 500);
            lastNameField.setBackground(Color.BLACK);
            lastNameField.setForeground(Color.WHITE);
            lastNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lastNameField.setFont(new Font("Arial", Font.PLAIN, 20));

            ageField = new JTextField("Enter age here");
            ageField.addActionListener(this::actionPerformed);
            ageField.setSize(400, 100);
            ageField.setLocation(1100, 500);
            ageField.setBackground(Color.BLACK);
            ageField.setForeground(Color.WHITE);
            ageField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            ageField.setFont(new Font("Arial", Font.PLAIN, 20));

            yes = new JButton("YES");
            yes.addActionListener(this::actionPerformed);
            yes.setSize(600, 100);
            yes.setLocation(100, 700);
            yes.setBackground(Color.BLACK);
            yes.setForeground(Color.WHITE);
            yes.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            yes.setFont(new Font("Arial", Font.PLAIN, 48));

            no = new JButton("NO");
            no.addActionListener(this::actionPerformed);
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

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source==firstNameField){
                firstName = firstNameField.getText();
            }

            if (source==lastNameField){
                lastName = lastNameField.getText();
            }

            if (source==ageField){
                try {
                    age = Integer.parseInt(ageField.getText());
                    if (age<0 || age>120){
                        throw new InputMismatchException();
                    }
                }
                catch (InputMismatchException o){
                    ageField.setText("Age should be an integer between 0 and 120");
                }
            }

            if (source==timer){
                startPanel.validate();
                startPanel.repaint();

            }

            if (source==yes){
                boolean correctAge = false;
                firstName = firstNameField.getText();
                lastName = lastNameField.getText();
                try {
                    age = Integer.parseInt(ageField.getText());
                    if (age<0 || age>120){
                        throw new InputMismatchException();
                    }
                    correctAge = true;
                }
                catch (InputMismatchException o){
                    ageField.setText("Age should be an integer between 0 and 120");
                }
                if (correctAge) {
                    contestant = new Contestant(firstName, lastName, age);
                    gamePanel = new GamePanel(contestant);
                    GameShowGUI.this.add(gamePanel);
                    GameShowGUI.this.remove(startPanel);
                }

            }

            if (source==no){
                System.exit(0);
            }

        }
    }

    /**
     * This class is used to draw the GUI Panel
     */
    private class GamePanel extends JPanel{

        private Image image;
        private GameShowScreens gameShowScreens;
        private int questionNumber;
        private String[] qAndA;
        private boolean lifeline2Active;


        public GamePanel(Contestant contestant){
            super();
            setPreferredSize(new Dimension(1600,900));
            setBackground(Color.WHITE);

            lifeline2Active = false;

            gameShowScreens = new GameShowScreens(contestant);
            questionNumber = 1;
            qAndA = gameShowScreens.getQAndA(questionNumber);

            lifeline1 = new JButton("50/50");
            lifeline1.addActionListener(this::actionPerformed);
            lifeline1.setLocation(50,50);
            lifeline1.setSize(250,100);
            lifeline1.setBackground(Color.BLACK);
            lifeline1.setForeground(Color.WHITE);
            lifeline1.setFont(new Font("Arial", Font.PLAIN, 48));

            lifeline2 = new JButton("Double Dip");
            lifeline2.addActionListener(this::actionPerformed);
            lifeline2.setLocation(50,150);
            lifeline2.setSize(250,100);
            lifeline2.setBackground(Color.BLACK);
            lifeline2.setForeground(Color.WHITE);
            lifeline2.setFont(new Font("Arial", Font.PLAIN, 40));

            option1 = new JButton(qAndA[1]);
            option1.addActionListener(this::actionPerformed);
            option1.setLocation(250,644);
            option1.setSize(475,75);
            option1.setBackground(Color.BLACK);
            option1.setForeground(Color.WHITE);
            option1.setBorder(null);
            option1.setFont(new Font("Arial", Font.PLAIN, 35));

            option2 = new JButton(qAndA[2]);
            option2.addActionListener(this::actionPerformed);
            option2.setLocation(920,644);
            option2.setSize(475,75);
            option2.setBackground(Color.BLACK);
            option2.setForeground(Color.WHITE);
            option2.setBorder(null);
            option2.setFont(new Font("Arial", Font.PLAIN, 35));

            option3 = new JButton(qAndA[3]);
            option3.addActionListener(this::actionPerformed);
            option3.setLocation(250,744);
            option3.setSize(475,75);
            option3.setBackground(Color.BLACK);
            option3.setForeground(Color.WHITE);
            option3.setBorder(null);
            option3.setFont(new Font("Arial", Font.PLAIN, 35));

            option4 = new JButton(qAndA[4]);
            option4.addActionListener(this::actionPerformed);
            option4.setLocation(920,744);
            option4.setSize(475,75);
            option4.setBackground(Color.BLACK);
            option4.setForeground(Color.WHITE);
            option4.setBorder(null);
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
                image = ImageIO.read(new File("images/Millionaire.png"));
            } catch (IOException e) {
            }
        }

        private void updateQAndA(){
            if (questionNumber<=10) {
                qAndA = gameShowScreens.getQAndA(questionNumber);
                question.setText(qAndA[0]);
                option1.setText(qAndA[1]);
                option2.setText(qAndA[2]);
                option3.setText(qAndA[3]);
                option4.setText(qAndA[4]);
            }
            else{
                finished = true;

            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(image, 0, 0, null);
        }

        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source==lifeline1){

                    int count = 0;
                    if(!gameShowScreens.checkAnswer(questionNumber, "A") ){
                        option1.setText("");
                        count++;
                    }
                    if(!gameShowScreens.checkAnswer(questionNumber, "B")){
                        option2.setText("");
                        count++;
                    }
                    if(!gameShowScreens.checkAnswer(questionNumber, "C")&& count<2){
                        option3.setText("");
                        count++;
                    }
                    if(!gameShowScreens.checkAnswer(questionNumber, "D")&& count<2){
                        option4.setText("");
                        count++;
                    }
                    remove(lifeline1);
            }

            if (source==lifeline2){
                lifeline2Active = true;
                remove(lifeline2);
            }

            if (source == option1){
                if (!lifeline2Active) {
                    if (gameShowScreens.checkAnswer(questionNumber, "A")) {
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else
                        lost = true;
                }
                else{
                    if (gameShowScreens.checkAnswer(questionNumber, "A")) {
                        lifeline2Active = false;
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else {
                        lifeline2Active = false;
                        option1.setText("Incorrect, have another guess");
                    }
                }
            }

            if (source == option2){
                if (!lifeline2Active) {
                    if (gameShowScreens.checkAnswer(questionNumber, "B")) {
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else
                        lost = true;
                }
                else{
                    if (gameShowScreens.checkAnswer(questionNumber, "B")) {
                        lifeline2Active = false;
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else {
                        lifeline2Active = false;
                        option2.setText("Incorrect, have another guess");
                    }
                }
            }

            if (source == option3){
                if (!lifeline2Active) {
                    if (gameShowScreens.checkAnswer(questionNumber, "C")) {
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else
                        lost = true;
                }
                else{
                    if (gameShowScreens.checkAnswer(questionNumber, "C")) {
                        lifeline2Active = false;
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else {
                        lifeline2Active = false;
                        option3.setText("Incorrect, have another guess");
                    }
                }
            }

            if (source == option4){
                if (!lifeline2Active) {
                    if (gameShowScreens.checkAnswer(questionNumber, "D")) {
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else
                        lost = true;
                }
                else{
                    if (gameShowScreens.checkAnswer(questionNumber, "D")) {
                        lifeline2Active = false;
                        moneyCount.setText("$" + gameShowScreens.getPrizeMoney()[questionNumber - 1]);
                        questionNumber++;
                        updateQAndA();
                    }
                    else {
                        lifeline2Active = false;
                        option4.setText("Incorrect, have another guess");
                    }
                }
            }

            if (source == timer){
                gamePanel.validate();
                gamePanel.repaint();

            }

        }
    }

    private class EndPanel extends JPanel implements ActionListener{
        private Image endImage;

        public EndPanel(){
            super();
            setPreferredSize(new Dimension(1600,900) );
            if (!lost) {
                try {
                    endImage = ImageIO.read(new File("images/winner.jpg"));
                } catch (IOException e) {
                }
            }
            else{
                try {
                    endImage = ImageIO.read(new File("images/loser.jpg"));
                } catch (IOException e) {
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(endImage, 0, 0, null);
        }

        public void actionPerformed(ActionEvent e){
            Object source = e.getSource();

            if (source==timer){
                endPanel.validate();
                endPanel.setSize(new Dimension(1600,901));
                endPanel.repaint();
                endPanel.setSize(new Dimension(1600,900));

            }
        }
    }

    /**
     * Main method used to run all methods to create GUI to be visible to user
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Who Wants To Be A Millionaire");

        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GameShowGUI());
        frame.pack();

        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width-frameDimension.width)/2,
                (screenDimension.height-frameDimension.height)/2);
        frame.setVisible(true);
    }
}
