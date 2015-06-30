import java.awt.Container; import java.awt.Insets;
import java.awt.event.WindowListener; import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements WindowListener
{
	private Insets insets;
	private GamePanel gamePanel;
	
	public GameFrame ()
	{
		super();
		insets = getInsets();
		setSize(insets.left + GamePanel.WIDTH + insets.right, insets.top + GamePanel.HEIGHT + insets.bottom);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		//layout GamePanel using absolute layout
		Container c = getContentPane();
		c.setLayout(null);
		gamePanel = new GamePanel();
		c.add(gamePanel);
		gamePanel.setBounds(insets.left, insets.top, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//finally, display the frame
		setVisible(true);
	}
	
	/**
	 * Run the GameFrame by calling run() on the GamePanel.
	 */
	public void run ()
	{
		gamePanel.run();
	}
	
	public static void main (String[] args)
	{
		GameFrame frame = new GameFrame();
		frame.run();
		frame.dispose();
		
	}
	
	//terminate gamepanel on close
	public void windowClosing(WindowEvent e) {
		if (gamePanel != null)
		{
			gamePanel.terminateGame();
		}
	}
	//unused WindowListener methods
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
}
