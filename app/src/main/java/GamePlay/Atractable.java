package GamePlay;

import com.johnnywaity.abduction.GameActivity;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;

import GameEngine.AttachableScript;
import GameEngine.GameObject;

public class Atractable implements AttachableScript {
    private GameObject gameObject;
    private float speed = 0.02f;
    @Override
    public void start() {

    }

    @Override
    public void update() {
        float distance = (float) Math.sqrt(Math.pow(gameObject.getObject().getTranslation().x - UFOController.ufo.getObject().getTranslation().x, 2) + Math.pow(gameObject.getObject().getTranslation().z - UFOController.ufo.getObject().getTranslation().z, 2));
        if(gameObject.getObject().getParents().length > 0){
            float worldX = gameObject.getObject().getWorldTransformation().getTranslation().x;
            float worldZ = gameObject.getObject().getWorldTransformation().getTranslation().z;
            distance = (float) Math.sqrt(Math.pow(worldX - UFOController.ufo.getObject().getTranslation().x, 2) + Math.pow(worldZ - UFOController.ufo.getObject().getTranslation().z, 2));
        }
        if(distance < 1){
            gameObject.disableOtherScripts(this);
            gameObject.getObject().rotateY(0.01f);
            gameObject.getObject().rotateX(0.01f);
            gameObject.getObject().rotateZ(0.01f);

            if(gameObject.getObject().getParents().length > 0){
                Matrix world = gameObject.getObject().getWorldTransformation().cloneMatrix();
                gameObject.getObject().removeParent(gameObject.getObject().getParents()[0]);
                gameObject.getObject().setTranslationMatrix(world);
            }

            SimpleVector direction = GameObject.subtractVectors(gameObject.getObject().getTranslation(), UFOController.ufo.getObject().getTranslation()).normalize();
            direction.scalarMul(speed);
            gameObject.getObject().translate(direction);
            float yDistance = Math.abs(gameObject.getObject().getTranslation().y - UFOController.ufo.getObject().getTranslation().y);
            if(yDistance < 0.5f){
                float newScale = gameObject.getObject().getScale() - 0.1f;
                if(newScale > 0){
                    gameObject.getObject().setScale(newScale);
                }else{
                    GameActivity.scoreNum += 5;
                    gameObject.remove();
                }
            }




        }
        else if(gameObject.getObject().getTranslation().y < -1.2f){
            gameObject.getObject().translate(0, 0.1f, 0);
        }
    }

    @Override
    public void setGameObject(GameObject g) {
        gameObject = g;
    }


}
