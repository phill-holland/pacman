package com.phillholland.app;

import java.awt.*;

public class App 
{
    public static void main(String[] args)
    {        
        Frame f = new Frame();
        f.addWindowListener(new java.awt.event.WindowAdapter() 
        {
             public void windowClosing(java.awt.event.WindowEvent e) 
             {
                System.exit(0);
             };
        });
      
        GamePanel gp = new GamePanel();
            
        gp.setSize(570,600);
        f.add(gp);
        f.pack();
        gp.init();
        f.setSize(570,600 + 60);
        f.setVisible(true);        
    }
}
