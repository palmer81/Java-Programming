// This example is from the book Developing Java Beans by Robert Englander. 
// Copyright (c) 1997 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or implied.

// Chapter 2 -- The first version of class UpdatingButton

import java.awt.*;
import java.awt.event.*;

class UpdatingButton1 extends Button
   implements ActionListener
{
   // number of times pressed
   protected int count = 0;

   // constructors
   UpdatingButton1()
   {
      this("");
   }

   UpdatingButton1(String str)
   {
      super(str);

      // register as an action listener on myself
      addActionListener(this);
   }

   // called when the button is pressed
   public void actionPerformed(ActionEvent e)
   {
      // bump the count and update the label
      count++;
      setLabel(new Integer(count).toString());
   }
}
