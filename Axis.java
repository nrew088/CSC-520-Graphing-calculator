package Graph3D;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


/**
 * Axis wall
 */
public class Axis extends Pane {

        Rectangle wall;

        public Axis(double size) {

            // wall
            // first the wall, then the lines => overlapping of lines over walls
            // works
            wall = new Rectangle(size, size);
            getChildren().add(wall);

            // grid
            double zTranslate = 0;
            double lineWidth = 3.0;
//            Color gridColor = Color.WHITE;
            Color gridColor = Color.DARKCYAN;
            for (int y = 0; y <= 10; y++) {

                Line yline = new Line(0, 0, size, 0);
                yline.setStroke(gridColor);
                yline.setFill(gridColor);
                yline.setTranslateY(y*size/10);  // 畫上橫線
                yline.setTranslateZ(zTranslate);
                yline.setStrokeWidth(lineWidth);

                getChildren().addAll(yline);

            }
            

            for (int x = 1; x <= 10; x++) {

                Line xline = new Line(0, 0, 0, size);
                xline.setStroke(gridColor);
                xline.setFill(gridColor);
                xline.setTranslateX(x*size/10);
                xline.setTranslateZ(zTranslate);
                xline.setStrokeWidth(lineWidth);

                getChildren().addAll(xline);

            }

            
        }

        public void setFill(Paint paint) {
            wall.setFill(paint);
        }
}

