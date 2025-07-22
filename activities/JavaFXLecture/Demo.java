import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
// new imports
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class Demo extends Application {
    @Override
    public void start(Stage stage) {
        // top label
        Label topLabel = new Label("0");
        // margins
        Insets margins = new Insents(12);

        // bottom buttons
        Button buttonx2 = new Button("x2");
        Button buttonQuit = new Button("Quit");
        HBox bottomButtons = new HBox(10, buttonx2, buttonQuit);

        // center buttons
        GridPane centerButtons = new GridPane();
        // aligned center
        centerButtons.setAlignment(Pos.CENTER);
        
        Button[] buttons = new Button[9];
        for (int i = 0 ; i < buttons.length; i++) {
            Label buttonLabel = i++ + "";
            Button button = new Button(buttonLabel);
            centerButtons.getChildren.add(button);
            GridPane.setColumnIndex(button, i / 3);
            GridPane.setColumnIndex(button, i % 3);
            button.setOnAction( event -> {
                if(output.getText().equals("0")) {
                    output.setText("");
                }
                output.setText( output.getText() + buttonLabel );
            });
        }

        // layout
        BorderPane root = new BorderPane();
        root.setTop(topLabel);
        root.setCenter(centerButtons);
        root.setBottom(bottomButtons);

        // scene setup
        stage.setScene(new Scene(root, 640, 480));
        stage.setTitle("Calculator Demo");
        stage.show();
    }

    public static void main(String args[]) {
        Application.launch();
    }
}
