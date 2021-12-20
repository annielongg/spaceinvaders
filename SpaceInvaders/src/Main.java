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

    private ArrayList<Sprite> alienProjectiles;

    private int direction = 1;

    private int lives = 3;

    private int score = 0;

    private boolean gameOver = false;


    public Main(int w, int h){
        setSize(w, h);

        greenGun = new Sprite(Resources.greenGun, new Point(350, 550));
//        playerProject = new Sprite(Resources.playerBullet,new Point(greenGun.getX(), greenGun.getY()));

        keys = new boolean[256];
        timer = new Timer(1000/60, e->update());
        timer.start();
        playerBullets = new ArrayList();
        alienProjectiles = new ArrayList();

        enemies = new ArrayList();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                enemies.add(new Sprite(Resources.purpleSquid, new Point(getWidth()/2-(12*60)/2+j*60,100+i*60)));
            }


        }


        setupKeys();
    }


    // called every frame.  All game state changes should happen here.
    // Thus, all movement, adding/removing enemies or lasers, etc.
    public void update(){
        if(gameOver){
            return;
        }
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


        //enemies
        boolean hitEdge = false;


        for(Sprite enemy: enemies){
            enemy.move(1*direction,0);
            if(enemy.getX()<=0 || enemy.getX()>= getWidth()-60){
                hitEdge = true;

            }

        }


        if(hitEdge){
            for(Sprite enemy: enemies){
                enemy.move(0,4);
            }
            direction *=-1;
        }


        for (int j = 0; j<playerBullets.size();j++) {
            for (int i = 0; i < enemies.size(); i++) {
                Sprite enemy = enemies.get(i);
                Sprite bullet = playerBullets.get(j);

                if(bullet.getX()+bullet.getWidth()/2>=enemy.getX() && bullet.getX()+bullet.getWidth()/2<=enemy.getX()+60 && bullet.getY()>=enemy.getY() && bullet.getY()<=enemy.getY()+60){
                    playerBullets.remove(j);
                    enemies.remove(i);
                    if(enemies.size()==0)
                        gameOver = true;
                    score+=10;
                    break;
                }
            }
            break;
        }

        //enemy aliens shoot back

        for (Sprite enemy: enemies) {
            if(Math.random()<.0002){
                alienProjectiles.add(new Sprite(Resources.playerBullet, new Point(enemy.getX() - 3, enemy.getY() - 35)));
            }
        }

        for(Sprite alien : alienProjectiles){
            alien.move(0,2);
        }

        for (int j = 0; j<alienProjectiles.size();j++) {
            Sprite bullet = alienProjectiles.get(j);
            if(bullet.getX()+bullet.getWidth()/2>=greenGun.getX() && bullet.getX()+bullet.getWidth()/2<=greenGun.getX()+ greenGun.getWidth() && bullet.getY()>=greenGun.getY() && bullet.getY()<=greenGun.getY()+ greenGun.getWidth()){
                alienProjectiles.remove(j);
                j--;
                lives--;
                if(lives ==0)
                    gameOver = true;
            }
        }


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

        for(Sprite enemy: enemies){
            enemy.draw(g2);

        }

        for(Sprite alien: alienProjectiles){
            alien.draw(g2);

        }

        //text showing lives
        g2.setFont((new Font("TimesRoman", Font.PLAIN, 24)));
        g2.drawString("Lives: " + lives + "               Score: " + score, 50, 50);

        if(gameOver){
            g2.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
            g2.drawString("GAME OVER", getWidth()/2, getHeight()/2);
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
