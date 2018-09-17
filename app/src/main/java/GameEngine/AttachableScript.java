package GameEngine;

public interface AttachableScript {
    GameObject gameObject = null;
    public void start();
    public void update();

    public void setGameObject(GameObject g);
}
