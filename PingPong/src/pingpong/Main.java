package pingpong;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 *
 * @author Nazrul
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong @Nazrul");//create object of JFrame
        PongPanel pongPanel = new PongPanel(); // create object of PongPanle clas
//        JButton play = new JButton("Play");
//        
//        JButton close = new JButton("Close");
//        JButton help = new JButton("Help");
//        JButton restart = new JButton("Restart");
//         play.setBounds(150, 250, 80, 50);

        frame.setLayout(new BorderLayout());//set layout
        frame.add(pongPanel, BorderLayout.CENTER);//add object of PongPanel with frame

        frame.setSize(500, 500);//frame size

        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
