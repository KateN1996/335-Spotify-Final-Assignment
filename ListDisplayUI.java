import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class ListDisplayUI extends Container {
	private JTable table;
	private String[] columnNames = { "#", "Song Title", "Album", "Artist" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ListDisplayUI(Playlist playlist) {
		this.setLayout(new GridBagLayout());
		// this.setMinimumSize(BrazilBeatsUI.defaultRes);
		// this.setFocusable(true);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel name = new JLabel(playlist.title);//.substring(0, playlist.title.length() - 5));
		gbc.weightx = 1f;
		gbc.weighty = 1f;
		gbc.gridy = 0;
		gbc.gridx = 0;
		name.setForeground(BrazilBeatsUI.detailColor);
		name.setFont(BrazilBeatsUI.headerFont);
		this.add(name, gbc);

		Object[][] tableData = new Object[playlist.getSize()][columnNames.length];
		for (int i = 0; i < playlist.getSize(); i++) {

			Song curSong = playlist.getSongAtIndex(i);

			// Song number
			tableData[i][0] = i;

			// tableData[i][1] = curSongLabel;

			tableData[i][2] = curSong.album;
			tableData[i][3] = curSong.artist;
		}

		table = new JTable(tableData, columnNames);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		gbc.gridy = 1;
		this.add(scrollPane, gbc);

	}

	class Renderer extends DefaultTableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Renderer(Song curSong){
			super();
			JLabel curSongLabel = new JLabel(curSong.title);
			curSongLabel.setFont(BrazilBeatsUI.mainFont);
			curSongLabel.setForeground(BrazilBeatsUI.detailColor);
			File albumImageFile = new File(curSong.coverPath);
			Image albumImage;
			try {
				albumImage = ImageIO.read(albumImageFile);
				albumImage = albumImage.getScaledInstance(BrazilBeatsUI.IMG_RES_MIN, BrazilBeatsUI.IMG_RES_MIN, Image.SCALE_DEFAULT);
				ImageIcon albumIcon = new ImageIcon(albumImage);
				curSongLabel.setIcon(albumIcon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
