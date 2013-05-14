package datamining;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Wayne Rijsdijk
 */
public class ChooseFile {
	private JFrame frame;
	
	public ChooseFile() {
		frame = new JFrame();
		
		frame.setVisible(true);
		this.BringToFront();
	}
	
	public File getFile() {
		FileFilter filter = new FileNameExtensionFilter("Data file", "txt", "dat", "data"); //make filter of .txt, .dat and .data files
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(filter);
		if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
			frame.setVisible(true);
			return fc.getSelectedFile();
		}
		else {
			System.out.println("No file has been selected");
			System.exit(1);
		}
		return null;
	}
	
	/*
	 * Make sure the JFrame is on top of all windows
	 */
	private void BringToFront() {
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.setExtendedState(JFrame.NORMAL);
	}
}
