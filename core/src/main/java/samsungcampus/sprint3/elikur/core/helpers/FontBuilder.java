package samsungcampus.sprint3.elikur.core.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontBuilder {

    public static BitmapFont generate(int size, Color color, String fontPath) {
        System.out.println("Проверяем существование: " + Gdx.files.internal(fontPath).exists());

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            parameter.color = color;
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose();
            return font;
        } catch (Exception e) {
            System.err.println("Ошибка при генерации шрифта: " + e.getMessage());
            return null;
        }
    }
}
