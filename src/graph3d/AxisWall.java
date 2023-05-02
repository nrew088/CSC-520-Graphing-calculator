package graph3d;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class AxisWall extends Pane {
	Rectangle wallRect;
	double minY;
	
	public AxisWall(double size) {
		// size *= 0.0001;
		wallRect = new Rectangle(size, size);
		getChildren().add(wallRect);
		
		double zTranslate = 10, lineWidth = 1;
		Color gridColor = Color.DARKCYAN;
		for(int y = 0; y <= 10; y++) {
			Line line = new Line(0,0,size,0);
			line.setStroke(gridColor);
			line.setFill(gridColor);
			line.setTranslateY(y*size);
			line.setTranslateZ(zTranslate);
			line.setStrokeWidth(lineWidth);
			
			getChildren().add(line);
			
			if(y < minY) minY = y;
		}
		
		for(int x = 0; x <= 10; x++) {
			Line line = new Line(0,0,0,size);
			line.setStroke(gridColor);
			line.setFill(gridColor);
			line.setTranslateX(x*size);
			line.setStrokeWidth(lineWidth);
			
			getChildren().add(line);
		}
	}
	
	public void setFill(Paint paint) {
		wallRect.setFill(paint);
	}
}
