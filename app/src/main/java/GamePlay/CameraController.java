package GamePlay;

import com.threed.jpct.Camera;

public class CameraController {
    public static CameraController sharedInstance;
    private Camera camera;

    public CameraController(Camera camera) {
        this.camera = camera;
        sharedInstance = this;
    }


    public void update(){
        camera.setPosition(UFOController.ufo.getObject().getTranslation().x + 3, -6, UFOController.ufo.getObject().getTranslation().z - 3);
    }
}
