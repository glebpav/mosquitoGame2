package com.mygdx.game.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class SliderView extends BaseView{

    ImageView stickImage;
    ImageView pointerImage;

    int pointerX;

    public SliderView(int x, int y, int width, int height) {
        super(x, y, width, height);

        stickImage = new ImageView(
                x, (int) (y + 1./3. * height),
                width, (int) (1./3. * height),
                "slider/sliderStick.png"
        );

        pointerImage = new ImageView(
                x,
        )

    }

    public static class SliderInputProcessor extends InputAdapter {

        OrthographicCamera camera;

        public SliderInputProcessor(OrthographicCamera camera) {
            this.camera = camera;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);
            System.out.println("Pointer: " + pointer);
            System.out.println("Button: " + button);
            System.out.println("touch down: " + vector.x + "; " + vector.y);
            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);
            System.out.println("Pointer: " + pointer);
            System.out.println("touch down: " + vector.x + "; " + vector.y);
            return super.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Vector3 vector = new Vector3(screenX, screenY, 0);
            vector = camera.unproject(vector);
            System.out.println("Pointer: " + pointer);
            System.out.println("touch down: " + vector.x + "; " + vector.y);
            return super.touchUp(screenX, screenY, pointer, button);
        }
    }

}
