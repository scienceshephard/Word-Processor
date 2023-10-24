import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class frame implements ChangeListener, ActionListener{
	File foo;
	JTextArea text;
    JFrame fram;
    Scanner scn=null;
    FileReader fileread= null;
    JTabbedPane tabPane;
    JMenuItem open, close, newp, save, ss;
    JMenu file, view;
    JPanel home, body, insert;	
    JSpinner size;
    PrintWriter prin;
    JButton color;
    JComboBox<Object> fam;
    String[] name;
    frame(){
    	//Frame
        fram= new JFrame();
        Container getContentPane=fram.getContentPane();
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fram.setSize(500, 500);
        fram.setTitle("Docx Editor");
        // Font-family name
        name= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fam= new JComboBox<Object>(name);
        fam.setPreferredSize(new Dimension(200, 25));
        fam.setSelectedIndex(5);
        fam.addActionListener(this);
        
        size= new JSpinner();
        size.setValue(16);
        size.addChangeListener(this);
        
        //Menu Items
        open= new JMenuItem("Open");
        open.addActionListener(this);
        close= new JMenuItem("Close Document");
        close.addActionListener(this);
        newp= new JMenuItem("New Document");
        save= new JMenuItem("Save");
        save.addActionListener(this);
        ss= new JMenuItem("Do LaLa");
        //file Menu
        file= new JMenu("File");
        file.add(newp);
        file.add(close);
        file.add(open);
        file.add(save);
        
        //buttonColorChooser
        color= new JButton("Text Color");
        color.setFocusable(false);
        color.addActionListener(this);
        
        //view Menu
        view= new JMenu("View");
        view.add(ss);
        
        //Home and view JPanel
        home= new JPanel();
        insert= new JPanel();
        home.add(size);
        home.add(fam);
        home.add(color);
//        home.setLayout(new BorderLayout(10, 10));
        //JTabbed Pane
        tabPane= new JTabbedPane();
        tabPane.setPreferredSize(new Dimension(20,100));
        tabPane.addTab("Home", home);
        tabPane.addTab("View", insert);
        tabPane.setTabPlacement(JTabbedPane.TOP);
        
        //TextArea
        text=new JTextArea();
        text.setLineWrap(true);
        text.setPreferredSize(new Dimension(300, 400));

        //MenuBar
        JMenuBar menu= new JMenuBar();
        menu.add(file);
        menu.add(view);
        
        //bodyPanel
        body= new JPanel();
        body.add(text);
        
        getContentPane.add(body, BorderLayout.CENTER);
        fram.setJMenuBar(menu);
        getContentPane.add(tabPane, BorderLayout.PAGE_START);
        fram.setVisible(true);
    }
	@Override
	public void stateChanged(ChangeEvent e) {
		text.setFont(new Font(text.getFont().getFamily(), text.getFont().getStyle(), (int)size.getValue()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fam) {
			text.setFont(new Font((String) fam.getSelectedItem(), text.getFont().getStyle(), text.getFont().getSize()));
		}else if(e.getSource()==color) {
			Color w=JColorChooser.showDialog(body, "Text Color", Color.black);
			text.setForeground(w);
		}else if(e.getSource()==open){
            JFileChooser fileChooser= new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter fil= new FileNameExtensionFilter("Document", "txt");
            fileChooser.setFileFilter(fil);
            int response= fileChooser.showOpenDialog(body);
            
	            if(response==JFileChooser.APPROVE_OPTION) {
		            try {
		            	foo= new File(fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath());
						scn= new Scanner(foo);
						if(foo.isFile()) {
							while(scn.hasNextLine()) {	
								String words=scn.nextLine()+"\n";
								text.append(words);
							}
						}
		            }catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					scn.close();
				}
            }
		}else if(e.getSource()==save) {
			JFileChooser filechosse= new JFileChooser();
			filechosse.setCurrentDirectory(new File("."));
			int response=filechosse.showSaveDialog(color);
			if(response==JFileChooser.APPROVE_OPTION) {
				File saveFile= new File(filechosse.getSelectedFile().getAbsoluteFile().getAbsolutePath());
				try {
					prin= new PrintWriter(saveFile);
					String word=text.getText();
					prin.print(word);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					prin.close();
				}
			}else if(response==JFileChooser.CANCEL_OPTION) {
				fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
	}
}