import java.awt.*;
import java.awt.image.BufferedImage;

/* This class represents a Sprite in a game.
   Sprites have an image and location, and can
   detect collision/intersection with another Sprite.
   This class is set up to have rectangular collision
   detection - rectangles intersecting rectangles.
 */

public class Sprite{ // i.e., a 2D thing in a video game . we will extend this class to make multiple sprites (ex,, bush, flower, dog)

    private BufferedImage image;
    private Point location;

    public Sprite(BufferedImage image, Point location) {
        this.image = image;
        this.location = location;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, location.x, location.y, null);
    }

    public boolean intersects(Sprite other){ // when u try to kill X, u need to know when u touch X (or when a bullet would intersect with X)
        Rectangle hitBox = new Rectangle(location.x, location.y, image.getWidth(), image.getHeight());
        Rectangle otherHitBox = new Rectangle(other.location.x, other.location.y, other.image.getWidth(), other.image.getHeight());
        return hitBox.intersects(otherHitBox);
    }

    public void move(int dx, int dy){

        location.translate(dx, dy);
    }

    public int getX(){
        return location.x;
    }

    public int getY(){
        return location.y;
    }

    public int getWidth(){ // get width of image
        return image.getWidth();
    }

    public void setLocation(int x, int y){
        location = new Point(x,y);
    }

    public Point getLocation() {
        return location;
    }
}
