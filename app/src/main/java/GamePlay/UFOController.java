package GamePlay;

import com.threed.jpct.SimpleVector;

import GameEngine.AttachableScript;
import GameEngine.GameObject;
import GameEngine.Input;
import GameEngine.TimeManager;
import GameEngine.Vector2;

public class UFOController implements AttachableScript {

    public static GameObject ufo;

    private GameObject gameObject = null;
    private final float velocity = 3f;

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
            SimpleVector v = new SimpleVector(norm.getX(), 0, -norm.getY());
            v.rotateY((float) Math.PI / 4);
            v.scalarMul(velocity * TimeManager.deltaTime);


            gameObject.getObject().translate(v);
        }
    }

    @Override
    public void setGameObject(GameObject g) {
        gameObject = g;
    }
}
