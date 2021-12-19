import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resources  { //holding spot for our buffered image objects, based on our computer files
    // to add an image to the environment:
    // 1. put the file into the res folder.
    // 2. Declare a variable before the static block.
    // 3. Initialize the variable by copying and pasting and modifying the
    //    ImageIO line.


    public static BufferedImage test, charlie, greenAlienBall, purpleSquid, pinkShield,greenGun,playerBullet;

    static{
        try{
            test = ImageIO.read(new File("./res/test.png"));
            charlie = ImageIO.read(new File("./res/charlie_弟弟.png"));
            greenAlienBall = ImageIO.read(new File("./res/green-alien-ball.png"));
            purpleSquid = ImageIO.read(new File("./res/purple-squid.png"));
            pinkShield = ImageIO.read(new File("./res/pink-shield.png"));
            greenGun = ImageIO.read(new File("./res/green-gun.png"));
            playerBullet = ImageIO.read(new File("./res/player-projectile.png"));


        }catch(Exception e){e.printStackTrace();}
    }
}
