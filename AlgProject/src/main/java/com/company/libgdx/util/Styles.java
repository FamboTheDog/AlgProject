package com.company.libgdx.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import lombok.Getter;

public class Styles {

    @Getter private static TextButton.TextButtonStyle buttonStyle;
    @Getter private static Label.LabelStyle labelStyle;

    static {
         buttonStyle = new TextButton.TextButtonStyle();
         buttonStyle.font = new BitmapFont();

         labelStyle = new Label.LabelStyle();
         labelStyle.font = new BitmapFont();
    }

}
