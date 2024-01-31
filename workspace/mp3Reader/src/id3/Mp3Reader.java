package id3;
import id3v1.FrameDescriptrors_id3v1;
import id3v1.Synchronized_id3v1;
import id3v2_3.Synchronized_id3v2_3;
import id3v2_4.Synchronized_id3v2_4;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import view.Mp3View;

// TODO: Auto-generated Javadoc
/**
 * The Class mp3Reader.
 */
public class Mp3Reader {
   public static Mp3View mp3view;
   /**
    * The main method.
    * 
    * @param args
    *        the arguments
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public static void main(String args[]) throws IOException {
	   mp3view = new Mp3View();
//      String testFile =
//         "/Users/scott/Music/Keller Williams/2008 Live With Moseley, Droll & Sipe/1-05 Breathe.mp3";
//      // Make test Window
      
   }
   
   /**
    * Test i d3v1.
    * 
    * @param file
    *        the file
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public void testID3v1(String file) throws FileNotFoundException, IOException {
      boolean done = false;
      // String testFile =
      // "/Users/scott/Downloads/Synchronized_id3v1 3/id3v1_006_basic.mp3";
      String testFile = file;
      // Printing Mp3 with internet method from below
      // byte[] stream = null;
      // try {
      // stream = getBytesFromFile(new File(testFile));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // System.out.println("Length of the stream: "+stream.length);
      // for (int i = 0; i < 2500; i++) {
      // char c = (char) stream[i];
      // System.out.print((i+1)+": "+c+" -> ");
      // System.out.println(stream[i]);
      // }
      // System.out.println();
      // System.out.println("done");
      // Testing myMp3 methods
      Synchronized_id3v1 myMp3 = new Synchronized_id3v1(new File(testFile));
      byte[] allFrames = myMp3.getTagSpace();
      System.out.println("Array of all frames:" + allFrames.length);
      for(int i = 0; i < allFrames.length; i++)
         System.out.println((char) allFrames[i] + " -> " + allFrames[i] + ": "
            + (i + 1));
      System.out.println("\n" + allFrames.length + "\ndone");
      ArrayList<byte[]> packedFrames = myMp3.getFrames();
      System.out.println("Pack of all frames:");
      for(int i = 0; i < packedFrames.size(); i++) {
         for(int j = 0; j < packedFrames.get(i).length; j++)
            System.out.print((char) packedFrames.get(i)[j] + " -> "
               + packedFrames.get(i)[j] + " ");
         System.out.println("done");
      }
      System.out.println("\n" + packedFrames.size() + "\ndone");
      // Testing Frame building and writing
      // boolean done = false;
      // while(!done) {
      String entered =
         JOptionPane.showInputDialog("Enter something: \n"
            + FrameDescriptrors_id3v1.ID3V1_TAG_SIZE + " # of all Frames\n"
            + myMp3.getNonTagData().length + " Length of non tag data\n"
            + (FrameDescriptrors_id3v1.ID3V1_TAG_SIZE + 10 + myMp3.getNonTagData().length)
            + "size of mp3 file calculated\n" + myMp3.getFileSize()
            + " size of mp3 file");
      char[] array = entered.toCharArray();
      boolean isArtist = true, found = false;
      
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 3 && isArtist; j++)
            if(j == 0 && (char) packedFrames.get(i)[j] != 'T') isArtist = false;
            else if(j == 1 && (char) packedFrames.get(i)[j] != 'P') isArtist =
               false;
            else if(j == 2 && (char) packedFrames.get(i)[j] != '1') isArtist =
               false;
         if(isArtist) {
            ref = i;
            found = true;
         }
         isArtist = true;
      }
      // creating a new frame to be inserted into the packed frames
      if(found) {
         packedFrames.remove(ref);
         byte newFrame[] = new byte[array.length + 8];
         newFrame[0] = (byte) 'C';
         newFrame[1] = (byte) 'O';
         newFrame[2] = (byte) 'M';
         newFrame[3] = (byte) 'M';
         int size = array.length + 1;
         if(size > 384) {
            System.out.println("too big of input");
            System.exit(1);
         }
         else if(size == 384) {
            newFrame[3] = -128;
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 256) {
            newFrame[3] = (byte) (size - 256);
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 128) {
            newFrame[3] = 0;
            newFrame[4] = (byte) (size - 128);
            newFrame[5] = -128;
         }
         else {
            newFrame[3] = 0;
            newFrame[4] = 0;
            newFrame[5] = (byte) size;
         }
         for(int i = 7; i < newFrame.length - 1; i++)
            newFrame[i] = (byte) array[i - 7];
         System.out.println("new frame:");
         for(int i = 0; i < newFrame.length; i++)
            System.out.print(newFrame[i] + " ");
         System.out.println();
         for(int i = 0; i < newFrame.length; i++)
            System.out.print((char) newFrame[i]);
         System.out.println("\ndone");
         packedFrames.add(newFrame);
         System.out.println("Pack of all frames (not re-read):");
         for(int i = 0; i < packedFrames.size(); i++) {
            for(int j = 0; j < packedFrames.get(i).length; j++)
               System.out.print((char) packedFrames.get(i)[j] + " ");
            System.out.println("done");
         }
         System.out.println("\n" + packedFrames.size() + "\ndone");
         // myMp3.writeFrames(myMp3.getHeaderFrame(), packedFrames);
         // packedFrames = myMp3.packAllFrames();
         // System.out.println("Pack of all frames (re-read):");
         // for (int i = 0; i < packedFrames.size(); i++) {
         // for (int j = 0; j < packedFrames.get(i).length; j++)
         // System.out.print((char)packedFrames.get(i)[j]+" ");
         // System.out.println("done");
         // }
         // System.out.println("\n"+packedFrames.size()+"\ndone");
      }
      else System.out.println("Oh no!!!");
      String question = JOptionPane.showInputDialog("Done?");
      if(question.equalsIgnoreCase("yes")) done = true;
   }
   
   /**
    * Test i d3v2.
    * 
    * @param file
    *        the file
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public static void testID3v2(File file) throws FileNotFoundException,
      IOException {
      boolean done = false;
      // String testFile =
      // "/Users/scott/Downloads/Synchronized_id3v1 3/id3v1_006_basic.mp3";
      String testFile =
         "/Users/scott/Music/Keller Williams/2008 Live With Moseley, Droll & Sipe/1-05 Breathe.mp3";
      // Printing Mp3 with internet method from below
      // byte[] stream = null;
      // try {
      // stream = getBytesFromFile(new File(testFile));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // System.out.println("Length of the stream: "+stream.length);
      // for (int i = 0; i < 2500; i++) {
      // char c = (char) stream[i];
      // System.out.print((i+1)+": "+c+" -> ");
      // System.out.println(stream[i]);
      // }
      // System.out.println();
      // System.out.println("done");
      // Testing myMp3 methods
      Synchronized_id3v2_3 myMp3 = new Synchronized_id3v2_3(new File(testFile));
      byte[] allFrames = myMp3.getTagSpace();
      System.out.println("Header size bytes: " + myMp3.getTagSizeBytes()[0]
         + " " + myMp3.getTagSizeBytes()[1] + " " + myMp3.getTagSizeBytes()[2]
         + " " + myMp3.getTagSizeBytes()[3]);
      System.out.println("Array of all frames:" + allFrames.length);
      for(int i = 0; i < allFrames.length; i++)
         System.out.println((char) allFrames[i] + " -> " + allFrames[i] + ": "
            + (i + 1));
      System.out.println("\n" + allFrames.length + "\ndone");
      ArrayList<byte[]> packedFrames = myMp3.packAllFrames();
      System.out.println("Pack of all frames:");
      for(int i = 0; i < packedFrames.size(); i++) {
         for(int j = 0; j < packedFrames.get(i).length; j++)
            System.out.print((char) packedFrames.get(i)[j] + " -> "
               + packedFrames.get(i)[j] + " ");
         System.out.println("done");
      }
      System.out.println("\n" + packedFrames.size() + "\ndone");
      // Testing Frame building and writing
      // boolean done = false;
      // while(!done) {
      String entered =
         JOptionPane.showInputDialog("Enter something: \n" + myMp3.getTagSize()
            + " # of all Frames\n" + myMp3.getNonTagData().length
            + " Length of non tag data\n"
            + (myMp3.getTagSize() + 10 + myMp3.getNonTagData().length)
            + "size of mp3 file calculated\n" + myMp3.getFileSize()
            + " size of mp3 file");
      char[] array = entered.toCharArray();
      boolean isArtist = true, found = false;
      
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 3 && isArtist; j++)
            if(j == 0 && (char) packedFrames.get(i)[j] != 'T') isArtist = false;
            else if(j == 1 && (char) packedFrames.get(i)[j] != 'P') isArtist =
               false;
            else if(j == 2 && (char) packedFrames.get(i)[j] != '1') isArtist =
               false;
         if(isArtist) {
            ref = i;
            found = true;
         }
         isArtist = true;
      }
      // creating a new frame to be inserted into the packed frames
      if(found) {
         packedFrames.remove(ref);
         byte newFrame[] = new byte[array.length + 8];
         newFrame[0] = (byte) 'C';
         newFrame[1] = (byte) 'O';
         newFrame[2] = (byte) 'M';
         newFrame[3] = (byte) 'M';
         int size = array.length + 1;
         if(size > 384) {
            System.out.println("too big of input");
            System.exit(1);
         }
         else if(size == 384) {
            newFrame[3] = -128;
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 256) {
            newFrame[3] = (byte) (size - 256);
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 128) {
            newFrame[3] = 0;
            newFrame[4] = (byte) (size - 128);
            newFrame[5] = -128;
         }
         else {
            newFrame[3] = 0;
            newFrame[4] = 0;
            newFrame[5] = (byte) size;
         }
         for(int i = 7; i < newFrame.length - 1; i++)
            newFrame[i] = (byte) array[i - 7];
         System.out.println("new frame:");
         for(int i = 0; i < newFrame.length; i++)
            System.out.print(newFrame[i] + " ");
         System.out.println();
         for(int i = 0; i < newFrame.length; i++)
            System.out.print((char) newFrame[i]);
         System.out.println("\ndone");
         packedFrames.add(newFrame);
         System.out.println("Pack of all frames (not re-read):");
         for(int i = 0; i < packedFrames.size(); i++) {
            for(int j = 0; j < packedFrames.get(i).length; j++)
               System.out.print((char) packedFrames.get(i)[j] + " ");
            System.out.println("done");
         }
         System.out.println("\n" + packedFrames.size() + "\ndone");
         // myMp3.writeFrames(myMp3.getHeaderFrame(), packedFrames);
         // packedFrames = myMp3.packAllFrames();
         // System.out.println("Pack of all frames (re-read):");
         // for (int i = 0; i < packedFrames.size(); i++) {
         // for (int j = 0; j < packedFrames.get(i).length; j++)
         // System.out.print((char)packedFrames.get(i)[j]+" ");
         // System.out.println("done");
         // }
         // System.out.println("\n"+packedFrames.size()+"\ndone");
      }
      else System.out.println("Oh no!!!");
      String question = JOptionPane.showInputDialog("Done?");
      if(question.equalsIgnoreCase("yes")) done = true;
   }
   
   /**
    * Test i d3v2_3.
    * 
    * @param file
    *        the file
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public static void testID3v2_3(File file) throws FileNotFoundException,
      IOException {
      boolean done = false;
      // String testFile =
      // "/Users/scott/Downloads/Synchronized_id3v1 3/id3v1_006_basic.mp3";
      String testFile =
         "/Users/scott/Music/Keller Williams/2008 Live With Moseley, Droll & Sipe/1-05 Breathe.mp3";
      // Printing Mp3 with internet method from below
      // byte[] stream = null;
      // try {
      // stream = getBytesFromFile(new File(testFile));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // System.out.println("Length of the stream: "+stream.length);
      // for (int i = 0; i < 2500; i++) {
      // char c = (char) stream[i];
      // System.out.print((i+1)+": "+c+" -> ");
      // System.out.println(stream[i]);
      // }
      // System.out.println();
      // System.out.println("done");
      // Testing myMp3 methods
      Synchronized_id3v2_3 myMp3 = new Synchronized_id3v2_3(new File(testFile));
      byte[] allFrames = myMp3.getTagSpace();
      System.out.println("Header size bytes: " + myMp3.getTagSizeBytes()[0]
         + " " + myMp3.getTagSizeBytes()[1] + " " + myMp3.getTagSizeBytes()[2]
         + " " + myMp3.getTagSizeBytes()[3]);
      System.out.println("Array of all frames:" + allFrames.length);
      for(int i = 0; i < allFrames.length; i++)
         System.out.println((char) allFrames[i] + " -> " + allFrames[i] + ": "
            + (i + 1));
      System.out.println("\n" + allFrames.length + "\ndone");
      ArrayList<byte[]> packedFrames = myMp3.packAllFrames();
      System.out.println("Pack of all frames:");
      for(int i = 0; i < packedFrames.size(); i++) {
         for(int j = 0; j < packedFrames.get(i).length; j++)
            System.out.print((char) packedFrames.get(i)[j] + " -> "
               + packedFrames.get(i)[j] + " ");
         System.out.println("done");
      }
      System.out.println("\n" + packedFrames.size() + "\ndone");
      // Testing Frame building and writing
      // boolean done = false;
      // while(!done) {
      String entered =
         JOptionPane.showInputDialog("Enter something: \n" + myMp3.getTagSize()
            + " # of all Frames\n" + myMp3.getNonTagData().length
            + " Length of non tag data\n"
            + (myMp3.getTagSize() + 10 + myMp3.getNonTagData().length)
            + "size of mp3 file calculated\n" + myMp3.getFileSize()
            + " size of mp3 file");
      char[] array = entered.toCharArray();
      boolean isArtist = true, found = false;
      
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 3 && isArtist; j++)
            if(j == 0 && (char) packedFrames.get(i)[j] != 'T') isArtist = false;
            else if(j == 1 && (char) packedFrames.get(i)[j] != 'P') isArtist =
               false;
            else if(j == 2 && (char) packedFrames.get(i)[j] != '1') isArtist =
               false;
         if(isArtist) {
            ref = i;
            found = true;
         }
         isArtist = true;
      }
      // creating a new frame to be inserted into the packed frames
      if(found) {
         packedFrames.remove(ref);
         byte newFrame[] = new byte[array.length + 8];
         newFrame[0] = (byte) 'C';
         newFrame[1] = (byte) 'O';
         newFrame[2] = (byte) 'M';
         newFrame[3] = (byte) 'M';
         int size = array.length + 1;
         if(size > 384) {
            System.out.println("too big of input");
            System.exit(1);
         }
         else if(size == 384) {
            newFrame[3] = -128;
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 256) {
            newFrame[3] = (byte) (size - 256);
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 128) {
            newFrame[3] = 0;
            newFrame[4] = (byte) (size - 128);
            newFrame[5] = -128;
         }
         else {
            newFrame[3] = 0;
            newFrame[4] = 0;
            newFrame[5] = (byte) size;
         }
         for(int i = 7; i < newFrame.length - 1; i++)
            newFrame[i] = (byte) array[i - 7];
         System.out.println("new frame:");
         for(int i = 0; i < newFrame.length; i++)
            System.out.print(newFrame[i] + " ");
         System.out.println();
         for(int i = 0; i < newFrame.length; i++)
            System.out.print((char) newFrame[i]);
         System.out.println("\ndone");
         packedFrames.add(newFrame);
         System.out.println("Pack of all frames (not re-read):");
         for(int i = 0; i < packedFrames.size(); i++) {
            for(int j = 0; j < packedFrames.get(i).length; j++)
               System.out.print((char) packedFrames.get(i)[j] + " ");
            System.out.println("done");
         }
         System.out.println("\n" + packedFrames.size() + "\ndone");
         // myMp3.writeFrames(myMp3.getHeaderFrame(), packedFrames);
         // packedFrames = myMp3.packAllFrames();
         // System.out.println("Pack of all frames (re-read):");
         // for (int i = 0; i < packedFrames.size(); i++) {
         // for (int j = 0; j < packedFrames.get(i).length; j++)
         // System.out.print((char)packedFrames.get(i)[j]+" ");
         // System.out.println("done");
         // }
         // System.out.println("\n"+packedFrames.size()+"\ndone");
      }
      else System.out.println("Oh no!!!");
      String question = JOptionPane.showInputDialog("Done?");
      if(question.equalsIgnoreCase("yes")) done = true;
   }
   
   /**
    * Test i d3v2_4.
    * 
    * @param file
    *        the file
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public static void testID3v2_4(File file) throws FileNotFoundException,
      IOException {
      boolean done = false;
      // String testFile =
      // "/Users/scott/Downloads/Synchronized_id3v1 3/id3v1_006_basic.mp3";
      String testFile =
         "/Users/scott/Music/Keller Williams/2008 Live With Moseley, Droll & Sipe/1-05 Breathe.mp3";
      // Printing Mp3 with internet method from below
      // byte[] stream = null;
      // try {
      // stream = getBytesFromFile(new File(testFile));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // System.out.println("Length of the stream: "+stream.length);
      // for (int i = 0; i < 2500; i++) {
      // char c = (char) stream[i];
      // System.out.print((i+1)+": "+c+" -> ");
      // System.out.println(stream[i]);
      // }
      // System.out.println();
      // System.out.println("done");
      // Testing myMp3 methods
      Synchronized_id3v2_4 myMp3 = new Synchronized_id3v2_4(new File(testFile));
      byte[] allFrames = myMp3.getTagSpace();
      System.out.println("Header size bytes: " + myMp3.getTagSizeBytes()[0]
         + " " + myMp3.getTagSizeBytes()[1] + " " + myMp3.getTagSizeBytes()[2]
         + " " + myMp3.getTagSizeBytes()[3]);
      System.out.println("Array of all frames:" + allFrames.length);
      for(int i = 0; i < allFrames.length; i++)
         System.out.println((char) allFrames[i] + " -> " + allFrames[i] + ": "
            + (i + 1));
      System.out.println("\n" + allFrames.length + "\ndone");
      ArrayList<byte[]> packedFrames = myMp3.packAllFrames();
      System.out.println("Pack of all frames:");
      for(int i = 0; i < packedFrames.size(); i++) {
         for(int j = 0; j < packedFrames.get(i).length; j++)
            System.out.print((char) packedFrames.get(i)[j] + " -> "
               + packedFrames.get(i)[j] + " ");
         System.out.println("done");
      }
      System.out.println("\n" + packedFrames.size() + "\ndone");
      // Testing Frame building and writing
      // boolean done = false;
      // while(!done) {
      String entered =
         JOptionPane.showInputDialog("Enter something: \n" + myMp3.getTagSize()
            + " # of all Frames\n" + myMp3.getNonTagData().length
            + " Length of non tag data\n"
            + (myMp3.getTagSize() + 10 + myMp3.getNonTagData().length)
            + "size of mp3 file calculated\n" + myMp3.getFileSize()
            + " size of mp3 file");
      char[] array = entered.toCharArray();
      boolean isArtist = true, found = false;
      
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 3 && isArtist; j++)
            if(j == 0 && (char) packedFrames.get(i)[j] != 'T') isArtist = false;
            else if(j == 1 && (char) packedFrames.get(i)[j] != 'P') isArtist =
               false;
            else if(j == 2 && (char) packedFrames.get(i)[j] != '1') isArtist =
               false;
         if(isArtist) {
            ref = i;
            found = true;
         }
         isArtist = true;
      }
      // creating a new frame to be inserted into the packed frames
      if(found) {
         packedFrames.remove(ref);
         byte newFrame[] = new byte[array.length + 8];
         newFrame[0] = (byte) 'C';
         newFrame[1] = (byte) 'O';
         newFrame[2] = (byte) 'M';
         newFrame[3] = (byte) 'M';
         int size = array.length + 1;
         if(size > 384) {
            System.out.println("too big of input");
            System.exit(1);
         }
         else if(size == 384) {
            newFrame[3] = -128;
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 256) {
            newFrame[3] = (byte) (size - 256);
            newFrame[4] = -128;
            newFrame[5] = -128;
         }
         else if(size > 128) {
            newFrame[3] = 0;
            newFrame[4] = (byte) (size - 128);
            newFrame[5] = -128;
         }
         else {
            newFrame[3] = 0;
            newFrame[4] = 0;
            newFrame[5] = (byte) size;
         }
         for(int i = 7; i < newFrame.length - 1; i++)
            newFrame[i] = (byte) array[i - 7];
         System.out.println("new frame:");
         for(int i = 0; i < newFrame.length; i++)
            System.out.print(newFrame[i] + " ");
         System.out.println();
         for(int i = 0; i < newFrame.length; i++)
            System.out.print((char) newFrame[i]);
         System.out.println("\ndone");
         packedFrames.add(newFrame);
         System.out.println("Pack of all frames (not re-read):");
         for(int i = 0; i < packedFrames.size(); i++) {
            for(int j = 0; j < packedFrames.get(i).length; j++)
               System.out.print((char) packedFrames.get(i)[j] + " ");
            System.out.println("done");
         }
         System.out.println("\n" + packedFrames.size() + "\ndone");
         // myMp3.writeFrames(myMp3.getHeaderFrame(), packedFrames);
         // packedFrames = myMp3.packAllFrames();
         // System.out.println("Pack of all frames (re-read):");
         // for (int i = 0; i < packedFrames.size(); i++) {
         // for (int j = 0; j < packedFrames.get(i).length; j++)
         // System.out.print((char)packedFrames.get(i)[j]+" ");
         // System.out.println("done");
         // }
         // System.out.println("\n"+packedFrames.size()+"\ndone");
      }
      else System.out.println("Oh no!!!");
      String question = JOptionPane.showInputDialog("Done?");
      if(question.equalsIgnoreCase("yes")) done = true;
   }
   
   // Returns the contents of the file in a byte array.
   /**
    * Gets the bytes from file.
    * 
    * @param file
    *        the file
    * @return the bytes from file
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public static byte[] getBytesFromFile(File file) throws IOException {
      InputStream is = new FileInputStream(file);
      
      // Get the size of the file
      long length = file.length();
      
      // You cannot create an array using a long type.
      // It needs to be an int type.
      // Before converting to an int type, check
      // to ensure that file is not larger than Integer.MAX_VALUE.
      if(length > Integer.MAX_VALUE) {
         // File is too large
         System.out.println("File too big: getBytesFromFile(File file)");
         System.exit(1);
      }
      
      // Create the byte array to hold the data
      byte[] bytes = new byte[(int) length];
      
      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while(offset < bytes.length
         && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
         offset += numRead;
      
      // Ensure all the bytes have been read in
      if(offset < bytes.length) throw new IOException(
         "Could not completely read file " + file.getName());
      
      // Close the input stream and return bytes
      is.close();
      return bytes;
   }
   
}