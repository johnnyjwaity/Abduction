package GamePlay;

import com.johnnywaity.abduction.GameActivity;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

import java.sql.Time;

import GameEngine.AttachableScript;
import GameEngine.GameObject;
import GameEngine.TimeManager;

public class MissileController implements AttachableScript {

    private GameObject gameObject;
    private SimpleVector direction;
    private float speed = 1f;

    private float deathTimer = 20;


    @Override
    public void start() {
        direction = GameObject.subtractVectors(gameObject.getObject().getTranslation(), UFOController.ufo.getObject().getTranslation()).normalize();
        faceObject(UFOController.ufo.getObject());
    }

    @Override
    public void update() {
        SimpleVector movement = new SimpleVector(direction.x, direction.y, direction.z);
        movement.scalarMul(speed);
        movement.scalarMul(TimeManager.deltaTime);
        gameObject.getObject().translate(movement);

        deathTimer -= TimeManager.deltaTime;
        if(deathTimer <= 0){
            gameObject.remove();
        }

        if(gameObject.getObject().getTranslation().distance(UFOController.ufo.getObject().getTranslation()) < 0.5f){
            GameActivity.health -= 5;
            gameObject.remove();
        }

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

        gameObject.getObject().rotateY(theta);
    }
}
