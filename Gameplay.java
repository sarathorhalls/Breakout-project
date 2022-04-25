import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.awt.Image;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private boolean running = false;
  private int score = 0;

  private int totalBricks = 28;

  private Timer timer;
  private int delay = 8;

  private int paddleX = 310;
  private int paddleY = 550;

  private int ballposX = ((int)(Math.random() * 470) + 120);
  private int ballposY = 350;
  private boolean fastMode = false;
  private int ballXdir = -1;
  private int ballYdir = -2;
  private int startingLives = 3;
  private int lives = startingLives;

  private URL heart1 = getClass().getResource(".//res/heart.png");
  private ImageIcon heart = new ImageIcon(heart1);

  private URL Ebutton1 = getClass().getResource(".//res/E-Button.png");
  private ImageIcon Ebutton = new ImageIcon(Ebutton1);

  private URL Upbutton1 = getClass().getResource(".//res/Up.png");
  private ImageIcon Upbutton = new ImageIcon(Upbutton1);

  private URL Downbutton1 = getClass().getResource(".//res/Down.png");
  private ImageIcon Downbutton = new ImageIcon(Downbutton1);

  private URL Leftbutton1 = getClass().getResource(".//res/Left.png");
  private ImageIcon Leftbutton = new ImageIcon(Leftbutton1);

  private URL Rightbutton1 = getClass().getResource(".//res/Right.png");
  private ImageIcon Rightbutton = new ImageIcon(Rightbutton1);
  
  private boolean heartCounter = false;

  private int ballSize = 20;

  private MapGenerator map;

  public Gameplay() {
    map = new MapGenerator(4, 7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  public void paint(Graphics g) {
    //Background
    g.setColor(Color.black);
    g.fillRect(1, 1, 692, 592);

    //Map
    map.draw((Graphics2D)g);

    //Borders
    g.setColor(Color.pink);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(0, 0, 692, 3);
    g.fillRect(691, 0, 3, 592);

    //Paddle
    g.setColor(Color.blue);
    g.fillRect(paddleX, paddleY, 100, 8);

    //Score
    g.setColor(Color.blue);
    g.setFont(new Font("Impact", 0, 25));
    g.drawString("Stig: " + score, 570, 30);

    //Ball
    g.setColor(Color.pink);
    g.fillOval(ballposX, ballposY, ballSize, ballSize);

    //lives
    for (int i = 0; i < lives; i++) {
      heart.paintIcon(this, g, 100 + i * 20, 15);
    }

    //Tips
    if(!running) {
      //g.drawImage(Ebutton, 30, 450, null);
      Ebutton.paintIcon(this, g, 30, 450);
      if (ballposY < 570 && ballSize == 20) {
        //g.drawImage(Downbutton, ballposX - 14, ballposY + 20, null);
        Downbutton.paintIcon(this, g, ballposX - 14, ballposY + 20);
        //g.drawImage(Upbutton, ballposX - 13, ballposY - 50, null);
        Upbutton.paintIcon(this, g, ballposX - 13, ballposY - 50);
      }
      //g.drawImage(Rightbutton, paddleX + 100, paddleY - 40, null);
      Rightbutton.paintIcon(this, g, paddleX + 100, paddleY - 40);
      //g.drawImage(Leftbutton, paddleX - 50, paddleY - 40, null);
      Leftbutton.paintIcon(this, g, paddleX - 50, paddleY - 40);
      g.setColor(Color.pink);
      if(!fastMode) {
        g.setFont(new Font("Impact", 0, 15));
        g.drawString("set Gamemode to FAST", 85, 490);
      }
      if(fastMode) {
        g.setFont(new Font("Impact", 0, 15));
        g.drawString("set Gamemode to Normal", 85, 490);
      }
    }

    //Win condition
    if (totalBricks <= 0) {
      running = false;
      ballXdir = 0;
      ballYdir = 0;
      if (!heartCounter) {
        startingLives++;
        heartCounter = true;
      } 
      g.setColor(Color.pink);
      g.setFont(new Font("Impact", 1, 30));
      g.drawString("Congratulations you have won, Stig: " + score, 95, 300);

      g.setFont(new Font("Impact", 0, 20));
      g.drawString("Press Enter To Restart", 255, 350);
    }

    //Loss Condition
    if (ballposY > 570) {
      running = false;
      ballXdir = 0;
      ballYdir = 0;
      if (!heartCounter) {
        lives--;
        heartCounter = true;
      } 
      if (lives > 0) {
        g.setColor(Color.pink);
        g.setFont(new Font("Impact", 1, 30));
        g.drawString("Continue?", 280, 300);
  
        g.setFont(new Font("Impact", 0, 20));
        g.drawString("Press Enter To Continue", 255, 350); 
      }
      if(lives <= 0) {
      g.setColor(Color.pink);
      g.setFont(new Font("Impact", 1, 30));
      g.drawString("Game Over, Stig: " + score, 225, 300);

      g.setFont(new Font("Impact", 0, 20));
      g.drawString("Press Enter To Restart", 255, 350);
      }
    } 

    //Bye
    g.dispose();

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    timer.start();
    Rectangle ballRect = new Rectangle(ballposX, ballposY, ballSize, ballSize);
    if(running) {
      if(ballRect.intersects(new Rectangle(paddleX, paddleY, 50, 8))) {
        if(ballXdir > 0) {
          ballXdir = -ballXdir;
          ballYdir = -Math.abs(ballYdir);
          
        } else {
          ballYdir = -Math.abs(ballYdir);
          
        }
      }

      if(ballRect.intersects(new Rectangle(paddleX + 50, paddleY, 50, 8))) {
        if(ballXdir < 0) {
          ballXdir = -ballXdir;
          ballYdir = -Math.abs(ballYdir);
          
        } else {
          ballYdir = -Math.abs(ballYdir);
          
        }
      }

      A: for (int i = 0; i < map.map.length; i++) {
        for (int j = 0; j < map.map[0].length; j++) {
          if (map.map[i][j] > 0) {
            int brickX = j * map.brickWidth + 80;
            int brickY = i * map.brickHeight + 50;
            int brickWidth = map.brickWidth;
            int brickHeight = map.brickHeight;

            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle brickRect = rect;

            if (ballRect.intersects(brickRect)) {
              map.setBrickValue(0, i, j);
              totalBricks--;
              if (!fastMode) {
                score += 5;
              }
              if (fastMode) {
                score += 6;
              }


              if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                ballXdir = -ballXdir;
              } else {
                ballYdir = -ballYdir;
              }

              break A;
            }
          }
        }
      }

      ballposX += ballXdir;
      ballposY += ballYdir;

      if (ballposX < 0) {
        ballXdir = -ballXdir;
      }
      if (ballposY < 0) {
        ballYdir = -ballYdir;
      }
      if (ballposX > 670) {
        ballXdir = -ballXdir;
      }
    }
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (paddleX >= 590) {
        paddleX = 590;
      } else {
        moveRight();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
      if (paddleX <= 10) {
        paddleX = 10;
      } else {
        moveLeft();
      }
    }
    if (!running) {
      if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
        if (ballSize == 30) {
          ballSize = 50;
        }
        if (ballSize == 20) {
          ballSize = 30;
        }
        if (ballSize == 10) {
          ballSize = 20;
        }
        if (ballSize == 5) {
          ballSize = 10;
        }
      } 

      if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
        if (ballSize == 10) {
          ballSize = 5;
        }
        if (ballSize == 20) {
          ballSize = 10;
        }
        if (ballSize == 30) {
          ballSize = 20;
        }
        if (ballSize == 50) {
          ballSize = 30;
        }
      } 
    }

   

    if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
      if (!running) {
        ballposX = ((int)(Math.random() * 470) + 120);
        heartCounter = false;
        ballposY = 350;
        if (fastMode) {
          ballXdir = -4;
          ballYdir = -3;
        }
        if (!fastMode) {
          ballXdir = -1;
          ballYdir = -2;
        }
        paddleX = 310;
        if(lives <= 0) {
          score = 0;
          totalBricks = 28;
          startingLives = 3;
          lives = startingLives;

          map = new MapGenerator(4, 7);
          repaint();
        }
        if(totalBricks <= 0) {
          totalBricks = 28;
          lives = startingLives;

          map = new MapGenerator(4, 7);
          repaint();
        }
      }
    } 
  }
  public void moveRight() {
    running = true;
    paddleX += 50; 
  }
  public void moveLeft() {
    running = true;
    paddleX -= 50; 
  }

  @Override
public void keyReleased(KeyEvent e) {
  B: if (e.getKeyCode() == KeyEvent.VK_E) {
    if (!running) {
      if (!fastMode) {
        fastMode = true;
        ballXdir = -4;
        ballYdir = -3;
        System.out.println("Fast");
        break B;
      }
      if (fastMode) {
        fastMode = false;
        ballXdir = -1;
        ballYdir = -2;
        System.out.println("not Fast");
        break B;
      }
    }
  } 
  }
}