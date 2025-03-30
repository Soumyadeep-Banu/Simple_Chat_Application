
package chatapplication;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Client implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame j = new JFrame();
    static DataOutputStream dout;
    
    Client(){
        
        j.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(Color.blue);
        p1.setBounds(0, 0, 400, 70);
        p1.setLayout(null);
        j.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(45,10,50,50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel phone = new JLabel(i9);
        phone.setBounds(308,20,25,25);
        p1.add(phone);
//        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel video = new JLabel(i12);
        video.setBounds(256,20,25,25);
        p1.add(video);
//        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(8,22,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dot = new JLabel(i15);
        dot.setBounds(358,20,8,22);
        p1.add(dot);
        
        JLabel name = new JLabel("Bondhu");
        name.setBounds(100, 17, 97, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("Arial",Font.BOLD,14));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(102, 40, 97, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("Arial",Font.BOLD,10));
        p1.add(status);
      
        a1 = new JPanel();
        a1.setBounds(6,75,375,512);
        j.add(a1);
        //a1.setBackground(Color.white);
        
        text = new JTextField();
        text.setBounds(6,596,275,40);
        text.setFont(new Font("Arial" , Font.PLAIN , 14));
        j.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(285,596,96,40);
        send.setBackground(Color.blue);
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("Arial" , Font.PLAIN , 16));

        j.add(send);
        
        
        j.setSize(400,700);
        j.setLocation(800,50);
        j.getContentPane().setBackground(Color.green);
        j.setVisible(true);
    }
    public static void main(String[]args){
        new Client();
        try{
            Socket s = new Socket("127.0.0.1" , 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
                while(true){ // for reading the messages
                    a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical , BorderLayout.PAGE_START);
                    j.validate();
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = text.getText();
        
        JPanel p2 = formatLabel(s);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical,BorderLayout.PAGE_START);
        
        text.setText(" ");
        
        try {
            dout.writeUTF(s);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        j.repaint();
        j.invalidate();
        j.validate();
        
        
    }
    public static JPanel formatLabel(String s){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style = \"width : 100px\">" + s + "</p></html>");
        output.setFont(new Font("Arial" , Font.PLAIN , 16));
        output.setBackground(Color.blue);
        output.setForeground(Color.white);
        output.setBorder(new EmptyBorder(15,15,15,50));
        output.setOpaque(true);
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        
        JLabel time = new JLabel();
        
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
        
        
    }
}

