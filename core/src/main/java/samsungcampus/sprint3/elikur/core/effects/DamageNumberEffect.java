package samsungcampus.sprint3.elikur.core.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.ui.TextView;
import samsungcampus.sprint3.elikur.core.ui.effect.TextViewEffect;

public class DamageNumberEffect {
    private Starter starter;
    private ArrayList<TextViewEffect> numbers;
    public DamageNumberEffect(Starter starter) {
        this.starter = starter;
        numbers = new ArrayList<>();
    }

    public void addNewEffect(Vector2 position, String text) {
        numbers.add(new TextViewEffect(position.x, position.y, text));
    }

    public void update(float delta) {
        int j = numbers.size();
        for (int i = 0; i < j; i++) {
            if (!numbers.get(i).isLive) {
                numbers.remove(numbers.get(i));
                j--;
            } else numbers.get(i).update(delta);
        }
    }

    public void draw(SpriteBatch batch) {
        for (TextViewEffect tve: numbers) tve.draw(batch);
    }

    public void removeAll() {
        numbers.clear();
    }
}
