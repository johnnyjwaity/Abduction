package GamePlay;

import GameEngine.AttachableScript;
import GameEngine.GameObject;
import GameEngine.Input;
import GameEngine.TimeManager;
import GameEngine.Vector2;

public class UFOController implements AttachableScript {

    public static GameObject ufo;

    private GameObject gameObject = null;
    private final float velocity = 0.1f;

    @Override
    public void start() {
        ufo = gameObject;
    }

    @Override
    public void update() {
        if(Input.IS_MOVING){
            float deltaX = Input.X - Input.ORIGIN_X;
            float deltaY = Input.Y - Input.ORIGIN_Y;
            if(Float.isNaN(deltaX)){
                deltaX = 0;
            }
            if(Float.isNaN(deltaY)){
                deltaY = 0;
            }
            Vector2 norm = new Vector2(deltaX, deltaY).getNormalizedVector();




            gameObject.getObject().translate(norm.getX() * velocity, 0, -norm.getY() * velocity);
        }
    }

    @Override
    public void setGameObject(GameObject g) {
        gameObject = g;
    }
}
