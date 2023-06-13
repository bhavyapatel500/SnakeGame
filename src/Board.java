import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int hieght = 400;
    int width = 400;

    int MAX_DOT = 1600;
    int DOT_SIZE = 10;

    int DOTS;
    int[] x = new int [MAX_DOT];
    int [] y = new int [MAX_DOT];
    int apple_x , apple_y;
    Image body,apple,head;
    Timer timer;
    int dealy=100;
    boolean inGame = true;

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(hieght,width));
        setBackground(Color.BLACK);
        initGame();
        loadImage();
    }

    public void initGame(){
        DOTS=3;
        x[0]=50;
        y[0]=50;
        for (int i=0;i<DOTS;i++){
            x[i] = x[0]+DOT_SIZE*i;
            y[i] = y[0];
        }
        locateApple();
        timer=new Timer(dealy,this);
        timer.start();
    }

    public void loadImage(){
        ImageIcon bodyImage = new ImageIcon("src/resource/dot.png");
        body = bodyImage.getImage();

        ImageIcon headImage = new ImageIcon("src/resource/head.png");
        head = headImage.getImage();

        ImageIcon appleImage = new ImageIcon("src/resource/apple.png");
        apple = appleImage.getImage();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<DOTS;i++){
                if(i==0) {
                    g.drawImage(head, x[0], y[0], this);
                }
                else{
                    g.drawImage(body,x[i],y[i],this);
                }
            }
        }
       else{
            timer.stop();
            gameover(g);

        }
    }

    public void locateApple(){
        apple_x = ((int) (Math.random()*39))*DOT_SIZE;
        apple_y = ((int) (Math.random()*39))*DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame==true){
            checkApple();
            checkcollision();
            move();
        }
        repaint();

    }

    public void move(){
        for(int i=DOTS-1; i>0 ;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }
    }
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            DOTS++;
            locateApple();
        }
    }

    public void checkcollision(){
        for(int i=1;i<DOTS;i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                inGame = false;
            }
        }
        if(x[0]<0)
            inGame=false;
        if(x[0]>=width)
            inGame=false;
        if(y[0]<0)
            inGame=false;
        if(y[0]>=hieght)
            inGame=false;
    }
    public void gameover(Graphics g){
        String msg = "Game Over";
        int score = (DOTS-3)*100;
        String smsg = "Score : "+ Integer.toString(score);
        Font small = new Font("Helvectica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width-fontMetrics.stringWidth(msg))/2 , hieght/4);
        g.drawString(smsg, (width-fontMetrics.stringWidth(smsg))/2 , 3*hieght/4);

    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed (KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && !downDirection){
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}

