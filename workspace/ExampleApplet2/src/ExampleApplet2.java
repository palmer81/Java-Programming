// This example is from the book Developing Java Beans by Robert Englander. 
// Copyright (c) 1997 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or implied.

// Chapter 3 -- The ExampleApplet2 class

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class ExampleApplet2 extends Applet
{
   // the buttons
   protected Button b1 = new Button("Button 1");
   protected Button b2 = new Button("Button 2");
   protected Button b3 = new Button("Button 3");

   // the adapter
   protected GenericButtonAdapter adapter;

   // the applet initialization
   public void init()
   {
      // add the buttons to the applet
      add(b1);
      add(b2);
      add(b3);

      try
      {
         adapter = new GenericButtonAdapter(this);
         adapter.registerActionEventHandler(b1, "handleB1");
         adapter.registerActionEventHandler(b2, "handleB2");
         adapter.registerActionEventHandler(b3, "handleB3");
      }
      catch (ClassNotFoundException e)
      {
         System.out.println(e);
      }
      catch (NoSuchMethodException e)
      {
         System.out.println(e);
      }
   }

   public void handleB1(ActionEvent e)
   {
      Button b = (Button)e.getSource();
      System.out.println("handleB1 called for button " + b.getLabel());
   }

   public void handleB2(ActionEvent e)
   {
      Button b = (Button)e.getSource();
      System.out.println("handleB2 called for button " + b.getLabel());
   }

   public void handleB3(ActionEvent e)
   {
      Button b = (Button)e.getSource();
      System.out.println("handleB3 called for button " + b.getLabel());
   }
}
