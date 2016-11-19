package graph;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainGraph {
	
	JFrame window;
	ChartsRegion chartsRegion;
	ControlsRegion controlsRegion;
	
	public MainGraph() {
		
		window = new JFrame("Music to Movement Patient Graph");
		chartsRegion = new ChartsRegion(); //create the charts region
		controlsRegion = new ControlsRegion(); //create the button panel
		
		
		//create the overall layout and include the charts and controls region
		window.setLayout(new BorderLayout());
		window.add(chartsRegion, BorderLayout.CENTER);
		window.add(controlsRegion, BorderLayout.SOUTH);
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setSize( (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width * 0.6), (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height * 0.6) );
		window.setLocationRelativeTo(null);
		
		window.setMinimumSize(window.getPreferredSize());
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//window.setVisible(true);

	}
	
	public void show() {
	    window.setVisible(true);
	}
}
