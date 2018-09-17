package GamePlay;

import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;

import GameEngine.AttachableScript;
import GameEngine.GameObject;
import GameEngine.Vector2;

public class CarController implements AttachableScript {

    private Vector2[] path;
    private int currentPosition;

    private float speed = 0.1f;

    private GameObject gameObject;

    private float lastRotation = 0;

    public CarController(Vector2[] path, int start){
        this.path = path;
        this.currentPosition = start;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        Vector2 destination;
        if(currentPosition >= path.length - 1){
            destination = path[0];
        }else{
            destination = path[currentPosition + 1];
        }

        SimpleVector direction = GameObject.subtractVectors(gameObject.getObject().getTranslation(), new SimpleVector(destination.getX(), -1, destination.getY())).normalize();
        direction.scalarMul(speed);
        gameObject.getObject().translate(direction);





        float distance = (float) Math.sqrt(Math.pow(gameObject.getObject().getTranslation().x - destination.getX(), 2) + Math.pow(gameObject.getObject().getTranslation().z - destination.getY(), 2));
        if(distance < 0.05){
            currentPosition ++;
            gameObject.getObject().rotateY((float) Math.PI / 2);
            if(currentPosition >= path.length){
                currentPosition = 0;

            }
        }

    }

    @Override
    public void setGameObject(GameObject g) {
        gameObject = g;
    }
}
