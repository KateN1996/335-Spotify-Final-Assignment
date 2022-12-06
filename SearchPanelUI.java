import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchPanelUI extends Container {
	private static final long serialVersionUID = 1L;
	private SearchInterface searchInterface;
	
	private JTextField searchField;
	
	
	SearchPanelUI(){
		searchInterface = Main.searchInterface;
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		
		searchField = new JTextField();
		
		JLabel searchTitle = new JLabel("Search for Songs");
		searchTitle.setFont(BrazilBeatsUI.headerFont);
		searchTitle.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx =1;
		gbc.weighty = 1;
		this.add(searchTitle, gbc);
		gbc.gridy = 1;
		
		this.add(searchField, gbc);
		
		gbc.gridy = 2;
		JButton findButton = new JButton("Search");
		findButton.setFont(BrazilBeatsUI.captionFont);
		findButton.setForeground(BrazilBeatsUI.detailColor);
		findButton.setBackground(BrazilBeatsUI.accentColor);
		findButton.addActionListener(new SearchListener());
		this.add(findButton, gbc);
		
	}
	class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String subject = searchField.getText();
			if (subject == null) {
				return;
			}
			System.out.println(searchInterface.searchByAll(subject));
		}
	}
}
