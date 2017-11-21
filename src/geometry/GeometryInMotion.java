package geometry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class GeometryInMotion extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private GridPane gridPane;
    private static final int MAX_SQUARE_SIDE_SIZE = 200;
    private static final int PANE_ROOT_HEIGHT = 600;
    private static final int PANE_ROOT_WIDTH = 800;
    private static double scene_height;
    private static double scene_width;
    Random random = new Random();


    public void buttonMultiTreads(Pane root) {
        gridPane = new GridPane ();

        gridPane.setTranslateX(10);
        gridPane.setTranslateY(10);

        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Button button = new Button("Multi Threads");
        button.setPrefWidth(110);
        gridPane.add(button, 0, 0);

        int numberOfRectangles = 3 + random.nextInt(10);
        button.setOnAction(event -> {
            for (int i = 0; i < numberOfRectangles; i++) {
                startMoving(rectangleCreation(root)).start();
            }
        });
        root.getChildren().addAll(button);
    }

    public Rectangle rectangleCreation(Pane root) {
        int height = 20 + random.nextInt(MAX_SQUARE_SIDE_SIZE);
        int width = 20 + random.nextInt(MAX_SQUARE_SIDE_SIZE);
        double setX = random.nextInt((int) scene_width - width);
        double setY = random.nextInt((int) scene_height - height);
        Rectangle rectangle = new Rectangle(width, height,
                Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        rectangle.setTranslateX(setX);
        rectangle.setTranslateY(setY);
        root.getChildren().addAll(rectangle);
        return rectangle;
    }

    public Thread startMoving(Rectangle rectangle) {
        Thread thread = new Thread(() -> {
            int stepx;
            if (random.nextBoolean() == true)
                stepx = 1;
            else stepx = -1;
            int stepy;
            if (random.nextBoolean() == true)
                stepy = 1;
            else stepy = -1;
            while (true) {
                if ((rectangle.getTranslateX() == 0) || (rectangle.getTranslateX() == scene_width - rectangle.getWidth())) {
                    stepx = stepx * -1;
                }
                if ((rectangle.getTranslateY() == 0) || (rectangle.getTranslateY() == scene_height - rectangle.getHeight())) {
                    stepy = stepy * -1;
                }
                final double y = rectangle.getTranslateY() + stepy;
                final double x = rectangle.getTranslateX() + stepx;
                Platform.runLater(() -> {
                    rectangle.setTranslateX(x);
                    rectangle.setTranslateY(y);
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }

    public Thread startMoving(Rectangle[] rectangle) {
        Thread thread = new Thread(() -> {
            Integer[] stepx = new Integer[rectangle.length];
            for (int i = 0; i < rectangle.length; i++) {

                if (random.nextBoolean() == true)
                    stepx[i] = 1;
                else stepx[i] = -1;
            }
            Integer[] stepy = new Integer[rectangle.length];
            for (int i = 0; i < rectangle.length; i++) {

                if (random.nextBoolean() == true)
                    stepy[i] = 1;
                else stepy[i] = -1;
            }
            while (true) {

                for (int i = 0; i < rectangle.length; i++) {
                    if ((rectangle[i].getTranslateX() == 0) || (rectangle[i].getTranslateX() == scene_width - rectangle[i].getWidth())) {
                        stepx[i] = stepx[i] * -1;
                    }
                    if ((rectangle[i].getTranslateY() == 0) || (rectangle[i].getTranslateY() == scene_height - rectangle[i].getHeight())) {
                        stepy[i] = stepy[i] * -1;
                    }
                    final double y = rectangle[i].getTranslateY() + stepy[i];
                    final double x = rectangle[i].getTranslateX() + stepx[i];
                    int finalI = i;
                    Platform.runLater(() -> {
                        rectangle[finalI].setTranslateX(x);
                        rectangle[finalI].setTranslateY(y);
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return thread;
    }
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(PANE_ROOT_HEIGHT);
        primaryStage.setWidth(PANE_ROOT_WIDTH);
        primaryStage.setTitle("Geometry_In_Motion");
        primaryStage.show();
        buttonMultiTreads(root);
        scene_height = scene.getHeight();
        scene_width = scene.getWidth();
    }
}

