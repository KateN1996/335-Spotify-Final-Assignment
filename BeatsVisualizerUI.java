
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * @author Kyle Walker
 *
 */
public class BeatsVisualizerUI extends Container{
	private static final long serialVersionUID = 1L;
	private JLabel beatsDanceView;


	private SongPlayer songPlayer;


	BeatsVisualizerUI() {
		songPlayer = Main.songPlayer;
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(colLayout);
		gbc.insets = BrazilBeatsUI.INSET_GAP;

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel brazilBeatsLabel = new JLabel("Beats Visualizer");
		brazilBeatsLabel.setFont(BrazilBeatsUI.headerFont);
		brazilBeatsLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 0;
		this.add(brazilBeatsLabel, gbc);

		beatsDanceView = new JLabel("");
		gbc.gridy = 1;
		updateBeatsView();
		this.add(beatsDanceView, gbc);

		// Initialize audio compnents
		songPlayer = Main.songPlayer;

	}

	/**
	 * Set Image/GIF
	 */
	public void updateBeatsView() {
		if(songPlayer.getCurrentClip().isRunning()) {
			URL url;
			url = getClass().getResource("brazilBeatsDancer.gif");
			Icon icon = new ImageIcon(url);

			beatsDanceView.setIcon(icon);
		}
		else {
			URL url;
			url = getClass().getResource("brazilBeatsDancerIdle.gif");
			Icon icon = new ImageIcon(url);

			beatsDanceView.setIcon(icon);
		}

	}

}
