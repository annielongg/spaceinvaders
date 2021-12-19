// the main problem here is my player projectiles (player bullets) - their behavior)


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main extends JPanel {

    private Timer timer;  // fires an event to trigger updating the animation.

    private Sprite greenGun;

    private boolean[] keys;

    private ArrayList<Sprite> playerBullets;

    private ArrayList<Sprite> enemies;

    public Main(int w, int h){
        setSize(w, h);

        greenGun = new Sprite(Resources.greenGun, new Point(350, 550));
//        playerProject = new Sprite(Resources.playerBullet,new Point(greenGun.getX(), greenGun.getY()));

        keys = new boolean[256];
        timer = new Timer(1000/60, e->update());
        timer.start();
        playerBullets = new ArrayList();

        enemies = new ArrayList();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                enemies.add(new Sprite(Resources.purpleSquid, new Point(300,300)));
            }


        }


        setupKeys();
    }


    // called every frame.  All game state changes should happen here.
    // Thus, all movement, adding/removing enemies or lasers, etc.
    public void update(){

        // moving a sprite is a movement so we do it here:

        //KEY MOVEMENTS based on A = left, D = right, W = up, S = down
        if(greenGun.getX()>-30 && greenGun.getX()<710) {
            if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
                //move test left
                greenGun.move(-3, 0);
            } else if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
                //move test right
                greenGun.move(3, 0);
            } else if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
                //move test up
                //            greenGun.move(0,-3);
            } else if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
                //move test down
                //            greenGun.move(0,3);
            }
        }

        if(greenGun.getX()>=710){
            if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
                //move test left
                greenGun.move(-3, 0); }

        }

        if(greenGun.getX()<=-30){
            if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
                //move test right
                greenGun.move(3, 0);
            }
        }

        //add on a new player projectile

        if(keys[KeyEvent.VK_SPACE] && playerBullets.size()<1) {
            playerBullets.add(new Sprite(Resources.playerBullet, new Point(greenGun.getX() - 3, greenGun.getY() - 35)));
        }

        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).move(0, -10);
            if ((playerBullets.get(i)).getY() <= -10) {
                playerBullets.remove(i);
            }
        }







        //loop over projectiles and move them
        //            playerBullets.get(i).move(0,10);


//        for (int i = 0; i < playerBullets.size(); i++) {
//            playerBullets.get(i).move(0,-10);
//                if((playerBullets.get(i)).getY()>0){
//                    playerBullets.remove(i);
//                    if(keys[KeyEvent.VK_SPACE]){
//                        if(playerBullets.size()<1)
//                            playerBullets.add(new Sprite(Resources.playerBullet,new Point(greenGun.getX()-3, greenGun.getY()-35)));
//                    }
//                }
//        }


        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        /*
            ALL drawing happens here.
            Note that ONLY drawing should happen here - any game state
            changes should happen elsewhere.
         */

        greenGun.draw(g2);

        for (Sprite laser: playerBullets) {
            laser.draw(g2);
        }

        for(Sprite enemies: enemies){
            enemies.draw(g2);
        }

    }

    public void setupKeys(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
    }


    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 800;
        int height = 800;
        window.setBounds(0, 0, width, height + 22); //(x, y, w, h) 22 due to title bar.

        JPanel panel = new Main(width, height);
        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(true);
    }
}
