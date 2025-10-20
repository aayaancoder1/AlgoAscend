import java.awt.*;
import java.awt.event.*;

/*
create new button
add them to the frame
add action listeners to the button
return the label
comapre the label
do the operation
*/

public class buttonEvent extends Frame implements ActionListener
{
    Button b1, b2, b3;
    Label l;
    buttonEvent(){
        setLayout(new FlowLayout());
        Button b1 = new Button("Java");
        Button b2 = new Button("OS");
        Button b3 = new Button("ADS");
        Label l = new Label("Options");
        
        add(l);
        add(b1);
        add(b2);
        add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setSize(500, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();

        if(s.equals("Java"))
            setBackground(Color.blue);
        if(s.equals("OS"))
            setBackground(Color.red);
        if(s.equals("ADS"))
            setBackground(Color.green);
    }

    public static void main (String arg[])
    {
        new buttonEvent();
    }
}