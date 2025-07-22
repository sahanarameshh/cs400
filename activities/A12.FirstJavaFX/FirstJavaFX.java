import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class FirstJavaFX extends Application {
    public void start(Stage stage) {
	Label quote = new Label("The key to making programs fast\n" + "is to make them do practically nothing.\n" + "-- Mike Haertel");
	Circle circle = new Circle(160, 120, 50);
	Polygon polygon = new Polygon(160, 120, 210, 280, 110, 280);
	
	Group group = new Group(quote, circle, polygon);
	Scene scene = new Scene(group,480,320);

	stage.setScene(scene);
	stage.setTitle("CS 400");
	stage.show();
    }
    
    public static void main(String[] args) {
	Application.launch(args);
	System.out.println("Goodbye.");
    }
    
}