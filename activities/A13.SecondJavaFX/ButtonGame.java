import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import java.util.Random;

public class ButtonGame extends Application {
    private int score = 0;
    private Button[] buttons = new Button[9];
    private Random random = new Random();

    private void scrambleButtons(Random random, Button[] buttons) {
        for (Button b : buttons) {
            b.setLayoutX(random.nextInt(501));
            b.setLayoutY(random.nextInt(401));
        }
    }

    @Override
    public void start(final Stage stage) {
        BorderPane root = new BorderPane();
        
        Label label = new Label("Score: 0");
        root.setTop(label);
        Button button = new Button("Exit");
        root.setBottom(button);
        
        Pane pane = new Pane();
        root.setCenter(pane);

        for (int i = 0; i < buttons.length; i++) {
            if (i == 0) {
                buttons[0] = new Button("Click me!");
                buttons[0].setOnAction( event -> {
                    scrambleButtons(random, buttons);
                    score++;
                    label.setText("Score: " + score);
                    button.requestFocus();
                });
            }
            else {
                buttons[i] = new Button("Click me?");
                buttons[i].setOnAction( event -> {
                    scrambleButtons(random, buttons);
                    score--;
                    label.setText("Score: " + score);
                    button.requestFocus();
                });
            }
        }
        for (int i = 0; i < buttons.length; i++) {
            pane.getChildren().add(buttons[i]);
        }

        Scene scene = new Scene(root, 600, 500);
        
        stage.setTitle("ButtonGame");
        stage.setScene(scene);

        scrambleButtons(random, buttons);
        button.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}