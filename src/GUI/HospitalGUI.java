package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Created by Chaos on 8.6.2017..
 */
public class HospitalGUI extends JFrame{
    private JButton receptionBtn;
    private JButton doctorBtn;
    private JButton exitBtn;
    

    public HospitalGUI() {
        super("Hospital");
        receptionBtn = new JButton("START RECEPTION MODULE")
        {{setSize(400,100);setMaximumSize(getSize());}};
        receptionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        doctorBtn = new JButton("START DOCTOR BUTTON")
        {{setSize(400,100);setMaximumSize(getSize());}};
        doctorBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn = new JButton("EXIT")
        {{setSize(400,100);setMaximumSize(getSize());}};
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400,600));
        setMinimumSize(new Dimension(300,420));
        setMaximumSize(new Dimension(400, 600));
        setSize(400,600);

        //LOOK N FEEL
        try { 
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        e.printStackTrace();
        }
        
        
        try {
            String path = "Resources\\logo.png";
            ImageIcon ic = new ImageIcon(getClass().getClassLoader().getResource(path));
            JLabel image = new JLabel(ic);
            image.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(image);
        } catch (Exception e) {
            JLabel image = new JLabel("LOGO NOT FOUND")
            {{setSize(400,300);setMaximumSize(getSize());}};
            image.setAlignmentX(Component.CENTER_ALIGNMENT);
            image.setAlignmentY(Component.CENTER_ALIGNMENT);
            add(image);
        }

        add(receptionBtn);
        add(doctorBtn);
        add(exitBtn);

        receptionBtn.addActionListener(e -> new Reception());

        doctorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorModule();
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
