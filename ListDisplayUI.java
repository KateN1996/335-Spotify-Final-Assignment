
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Displays the contents of a playlist using a JTable allowing users to access
 * all songs in the playlist. The playlist order can be changed by pressing one
 * of the columns to sort by. Songs can be played individually by clicking on
 * their row in the table. The table displays the song's index in the playlist,
 * its image and title, album, and artist.
 * 
 * @author Kyle Walker
 *
 */
public class ListDisplayUI extends Container {
	// Table for view
	private JTable table;
	// Columns
	private String[] columnNames = { "#", "Song Title", "Album", "Artist", "Duration" };
	private static final long serialVersionUID = 1L;
	private SongPlayer songPlayer;
	private BrazilBeatsUI gui;
	private SongQueue songQueue;

	private Playlist curPlaylist;

	/**
	 * Creates a new playlist table display Container to display current playlist.
	 * 
	 * @param playlist Playlist that will be displayed within the table
	 */
	ListDisplayUI(Playlist playlist) {
		songPlayer = Main.songPlayer;
		gui = Main.gui;
		songQueue = Main.songQueue;
		curPlaylist = playlist;

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.CENTER;

		// gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel name = new JLabel(playlist.title);
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
			tableData[i][1] = curSong.title;

			tableData[i][2] = curSong.album;
			tableData[i][3] = curSong.artist;
			
			int songLength = (int) songPlayer.getSongLength(curSong);
			String endTime = (songLength / 60 + ":" + (new DecimalFormat("00").format((songLength % 60))));
			tableData[i][4] = endTime;
		}

		table = new JTable(tableData, columnNames);

		// Disable cell editing (Readonly table)
		DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}

		};

		table.setModel(tableModel);
		table.setRowSelectionAllowed(true);
		table.addMouseListener(new rowSelectionListener());

		table.setBackground(BrazilBeatsUI.barColor);
		table.setForeground(BrazilBeatsUI.detailColor);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		gbc.gridy = 1;
		this.add(scrollPane, gbc);

	}

	/**
	 * Listens for selctions on table and allows user to play songs by clicking row
	 * in table.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class rowSelectionListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow < 0) {
				return;
			}

			try {
				String selectedSong = (String) table.getValueAt(selectedRow, 1);
				Song curSong = SearchInterface.getSong(selectedSong);
				songPlayer.play(curSong);
				songQueue.setCurrentPlaylist(curPlaylist, curSong);

			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			gui.validateFrame();
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}

}
