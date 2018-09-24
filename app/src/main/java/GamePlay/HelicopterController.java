package GamePlay;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

import GameEngine.AttachableScript;
import GameEngine.GameObject;
import GameEngine.TimeManager;
import GameEngine.Vector2;

public class HelicopterController implements AttachableScript {

    private GameObject gameObject;

    private float speed = 2;

    private float lastRotation = 0;
    private Object3D blades;

    public HelicopterController(Object3D blades){
        this.blades = blades;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        faceObject(UFOController.ufo.getObject());

        Object3D obj = UFOController.ufo.getObject();
        if(Math.sqrt(Math.pow(obj.getTranslation().x - gameObject.getObject().getTranslation().x, 2) + Math.pow(obj.getTranslation().z - gameObject.getObject().getTranslation().z, 2)) > 2){
            Vector2 destination = new Vector2(obj.getTranslation().x, obj.getTranslation().z);


            SimpleVector direction = GameObject.subtractVectors(gameObject.getObject().getTranslation(), new SimpleVector(destination.getX(), -4, destination.getY())).normalize();
            direction.scalarMul(speed * TimeManager.deltaTime);
            gameObject.getObject().translate(direction);

        }

        blades.rotateY(0.1f);
    }

    @Override
    public void setGameObject(GameObject g) {
        gameObject = g;
    }

    private void faceObject(Object3D obj){
        float zOffset = obj.getTranslation().z - gameObject.getObject().getTranslation().z;
        float dist = (float) Math.sqrt(Math.pow(obj.getTranslation().x - gameObject.getObject().getTranslation().x, 2) + Math.pow(obj.getTranslation().z - gameObject.getObject().getTranslation().z, 2));

        float theta = (float) Math.acos(zOffset / dist);

        if(obj.getTranslation().x < gameObject.getObject().getTranslation().x){
            theta *= -1;
        }

        gameObject.getObject().rotateY(-lastRotation);
        gameObject.getObject().rotateY(theta);
        lastRotation = theta;
    }
}
