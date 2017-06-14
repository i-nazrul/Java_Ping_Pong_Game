package pingpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.java2d.loops.DrawLine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nazrul
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {

    private boolean showTitleScreen = true;

    private boolean playing = false;
    private boolean gameOver = false;

    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean wPressed = false;
    private boolean sPressed = false;

    private int ballX = 250;//x position of ball
    private int ballY = 250;//y position of ball

    private int diameter = 20;//ball diameter

    private int ballDeltaX = -1;
    private int ballDeltaY = 5;//ball speed

//position of paddle of player one
    private int playerOneX = 25;
    private int playerOneY = 250;
    //side of the paddle of player one
    private int playerOneWidth = 15;
    private int playerOneHeight = 80;
//position of the paddle of player two
    private int playerTwoX = 465;
    private int playerTwoY = 250;
    //paddle size of player two
    private int playerTwoWidth = 15;
    private int playerTwoHeight = 80;
//paddle speed of player
    private int paddleSpeed = 10;
//score of player
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    //Construct of PongPanel class
    public PongPanel() {
        setBackground(Color.PINK);

        // listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call setp() 60 fps
        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    public void setp() {

        if (playing) {
            //move palyer 1
            if (wPressed) {
                if (playerOneY - paddleSpeed > 0) {
                    playerOneY -= paddleSpeed;
                }
            }
            if (sPressed) {
                if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
                    playerOneY += paddleSpeed;
                }
            }

            //move player 2
            if (upPressed) {
                if (playerTwoY - paddleSpeed > 0) {
                    playerTwoY -= paddleSpeed;
                }
            }
            if (downPressed) {
                if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
                    playerTwoY += paddleSpeed;
                }
            }

            //where will the ball be after it moves?
            int nextBallLeft = ballX + ballDeltaX;
            int nextBallRight = ballX + diameter + ballDeltaX;
            int nextBallTop = ballY + ballDeltaY;
            int nextBallBottom = ballY + diameter + ballDeltaY;

            int playerOneRight = playerOneX + playerOneWidth;
            int playerOneTop = playerOneY;
            int playerOneBottom = playerOneY + playerOneHeight;

            int playerTwoLeft = playerTwoX;
            int playerTwoTop = playerTwoY;
            int playerTwoBottom = playerTwoY + playerTwoHeight;

            //ball bounce off top and bottom of screen
            if (nextBallTop < 0 || nextBallBottom > getHeight()) {
                ballDeltaY *= -1;
            }

            //will the ball go off the left side?
            if (nextBallLeft < playerOneRight) {
                //is it going to miss the paddle?
                if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
                    playerTwoScore++;
                    if (playerTwoScore == 3) {
                        playing = false;
                        gameOver = true;
                    }
                    //position of ball
                    ballX = 250;
                    ballY = 250;
                } else {
                    ballDeltaX *= -1;
                }
            }
            //will the ball go off the right side?
            if (nextBallRight > playerTwoLeft) {
                //is it going to miss the paddle?
                if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
                    playerOneScore++;
                    if (playerOneScore == 3) {
                        playing = false;
                        gameOver = true;
                    }
                    //position of ball
                    ballX = 250;
                    ballY = 250;
                } else {
                    ballDeltaX *= -1;
                }
            }

            //move the ball
            ballX += ballDeltaX;
            ballY += ballDeltaY;
        }
////stuff has moved, tell this JPanel to repaint itself
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setp();

    }

//paint the game screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);

        if (showTitleScreen) {
            g.setFont(new Font(Font.SERIF, Font.BOLD, 36));
            g.setColor(Color.MAGENTA);
            g.drawString("Ping Pong", 165, 100);

            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            g.setColor(Color.BLUE);
            g.drawString("Press 'P' to play start", 50, 300);
        } else if (playing) {
            int playerOneRight = playerOneX + playerOneWidth;
            int playerTwoLeft = playerTwoX;
            setBackground(Color.decode("#006b00"));

            //draw  line and circle into center of the field
            g.setColor(Color.WHITE);
            g.drawLine(250, 5, 250, 464);
            g.drawOval(200, 180, 100, 100);
            //draw dashed line
//            for (int lineY = 0; lineY < getHeight(); lineY += 50) {
//                g.drawLine(250, lineY + 5, 250, lineY + 15);
//                g.drawOval(200, 180, 100, 100);
//            }

            //draw "goal lines" on each side
            g.setColor(Color.RED);
            g.drawLine(playerOneRight, 5, playerOneRight, getHeight() - 7);//palyer one side
            g.drawLine(playerTwoLeft, 5, playerTwoLeft, getHeight() - 7);//player two side

            //draw out line of the field           
            g.setColor(Color.white);
            g.drawRect(25, 5, 452, 460);

            //draw the scores
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.setColor(Color.YELLOW);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            //draw the ball
            g.setColor(Color.white);
            g.fillOval(ballX, ballY, diameter, diameter);

            //draw the paddle of player one
            g.setColor(Color.BLUE);
            g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
            //draw the paddle of player two
            g.setColor(Color.YELLOW);
            g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
        } else if (gameOver) {
            setBackground(Color.BLACK);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.setColor(Color.yellow);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            if (playerOneScore > playerTwoScore) {
                g.setColor(Color.BLUE);
                g.drawString("Player 1 Wins!", 160, 200);
            } else {
                g.setColor(Color.YELLOW);
                g.drawString("Player 2 Wins!", 160, 200);
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            g.drawString("Press space to restart the game.", 100, 400);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (showTitleScreen) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                showTitleScreen = false;
                playing = true;
            }
        } else if (playing) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
        } else if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                gameOver = false;
               	showTitleScreen = false;
			playing = true;                
			playerOneY = 250;
                playerTwoY = 250;
                ballX = 250;
                ballY = 250;
                playerOneScore = 0;
                playerTwoScore = 0;
                
  

            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (playing) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = false;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }

    }
}
