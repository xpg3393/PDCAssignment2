package MVC;

/**
 * main method used to run the who wants to be a millionaire game GUI
 * @author xpg3393
 */
public class WWTBAMMain {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        model.addObserver(view);
        
    }


}
