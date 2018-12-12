import java.io.*; 
import java.rmi.*; 
import java.net.*;  
import java.util.Scanner;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server implements ActionListener {

  JFrame f;
  JTextField tx1,tx2;
  JButton send,close;
  JLabel l1,l2,l3,l4;
  JTextArea ta;
  JScrollPane sp;
  static ServerSocket ser;
  static Socket c_soc;
  static OutputStream out;

  Server() {
    f = new JFrame("Server");
    f.setLayout(null);
    Font ft = new Font("LucidaSans",Font.BOLD|Font.ITALIC,18);

    l1 = new JLabel("Enter text");
    l1.setBounds(50,100,150,30);
    l1.setFont(ft);
    l1.setForeground(Color.white);

    tx1 = new JTextField();
    tx1.setBounds(250,100,150,30);
    l2 = new JLabel("Received");
    l2.setBounds(50,170,150,30);
    l2.setFont(ft);
    l2.setForeground(Color.white);	

    tx2 = new JTextField();
    tx2.setBounds(250,170,150,30);
    l3 = new JLabel(new ImageIcon("img17.jpg"));
    l3.setBounds(0,0,700,700);

    l4 = new JLabel("Chats...");
    l4.setBounds(70,320,150,30);
    l4.setFont(ft);
    l4.setForeground(Color.green);	

    send = new JButton("send");
    send.setBounds(250,250,140,50);
    send.setFont(ft);
    send.setForeground(Color.black);
    send.setBackground(Color.white);

    close = new JButton("close");
    close.setBounds(250,550,140,50);
    close.setFont(ft);
    close.setForeground(Color.black);
    close.setBackground(Color.white);

    int v=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
    int h=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
    ta = new JTextArea("",10,10);
    sp = new JScrollPane(ta,h,v);
    sp.setBounds(70,350,300,150);
    f.getContentPane().add(sp);

    l3.add(l1);l3.add(l2);l3.add(sp);l3.add(l4);
    l3.add(tx1);l3.add(tx2);l3.add(send);l3.add(close);
    f.add(tx1);f.add(tx2);f.add(l3);

    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    send.addActionListener(this);
    close.addActionListener(this);
    f.setVisible(true);
    f.setSize(600,700);
  };

  public void actionPerformed(ActionEvent ae) {
    try {
      if(ae.getSource()==send) {		
        out.write(tx1.getText().getBytes());ta.append(">"+tx1.getText()+".."+"\n");
        if(tx1.getText()=="close") {
          c_soc.close();
        };
      };
      
      if(ae.getSource()==close) {	
        f.dispose();
      };

    }
    catch(Exception e){tx2.setText(e.toString());}
  };

  public void set(String msg) {
    tx2.setText(msg);
    ta.append(">"+msg+".."+"\n");
  };

  public static void main(String ar[]) { 
    int c=1;
    try {
      System.out.println("Server...");
      ser=new ServerSocket(10002);
      c_soc=ser.accept();
      out=c_soc.getOutputStream();
      InputStream in=c_soc.getInputStream();
      Server sobj = new Server();			
      byte data[];
      while(true) {
        data=new byte[100];
        in.read(data);
        String msg=new String(data);
        sobj.set(msg);
        c++;
        if(msg.equals("close")){
          ser.close();
          break;
        };
      };
    }
    catch(Exception e){};
  };
};
