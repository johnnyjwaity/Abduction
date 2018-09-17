package GameEngine;

public class Vector2 {

    private float x;
    private float y;


    public Vector2(float x, float y) {
        if(Float.isNaN(x)){
            x = 0;
        }
        if(Float.isNaN(y)){
            y = 0;
        }
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2 getNormalizedVector(){
        float mag = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Vector2(x/mag, y/mag);
    }
}
