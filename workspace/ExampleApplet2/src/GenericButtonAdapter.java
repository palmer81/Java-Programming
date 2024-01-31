// This example is from the book Developing Java Beans by Robert Englander. 
// Copyright (c) 1997 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or implied.

// Chapter 3 -- The GenericButtonAdapter class



import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

public class GenericButtonAdapter 
         implements ActionListener // the adapter receives the events
{
   // the target object
   protected Object theTarget;

   // the class of the target object
   protected Class theTargetClass;

   // the class array for the parameters used for
   // the target method
   protected final static Class paramClasses[] = 
                      { java.awt.event.ActionEvent.class };

   // the mapping of source objects to callback methods
   protected Hashtable mappingTable = new Hashtable();
  
   // constructor
   public GenericButtonAdapter(Object target)
                 throws ClassNotFoundException
   {
      theTarget = target;
      theTargetClass = target.getClass();
   }

   // add an action object to listen for, along with the
   // method to call on the target when the action event
   // is received
   public void registerActionEventHandler(Button btn, String methodName)
                 throws NoSuchMethodException
   {
      Method mthd = theTargetClass.getMethod(methodName, paramClasses);
      btn.addActionListener(this);
      mappingTable.put(btn, mthd);    
   }

   // implement the listener method
   public void actionPerformed(ActionEvent evt)
   {
      try
      {
         // invoke the registered method on the target
         Method mthd = (Method)mappingTable.get(evt.getSource());
         
         Object params[] = { evt };
         mthd.invoke(theTarget, params);
      }
      catch (IllegalAccessException e)
      {
         System.out.println(e);
      }
      catch (InvocationTargetException e)
      {
         System.out.println(e);
      }
   }   
}
