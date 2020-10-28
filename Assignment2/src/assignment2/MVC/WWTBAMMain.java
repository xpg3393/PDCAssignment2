package assignment2.MVC;

public class WWTBAMMain {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        model.addObserver(view);
    }


}
