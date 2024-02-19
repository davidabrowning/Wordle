package group.wordle.ui;

import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Style {

    public static final Font FONT_LARGE = new Font("Arial", 32);
    public static final Font FONT_MEDIUM = new Font("Arial", 24);
    public static final Font FONT_SMALL = new Font("Arial", 12);
    public static final BorderStroke BORDER_LIGHT_GRAY = new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
    public static final BorderStroke BORDER_BLACK = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);

}
