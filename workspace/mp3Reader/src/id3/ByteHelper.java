package id3;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteHelper.
 */
public class ByteHelper {
   
   /** The Constant ZERO_BYTE. */
   public static final byte ZERO_BYTE = 0;
   
   /**
    * Reads a number of bytes set by num parameter from a file given by the
    * file parameter.
    * 
    * @param file
    *        The file to read bytes from
    * @param num
    *        The of bytes to read from the file
    * @param skip
    *        The number bytes to skip in the file to start reading bytes from
    * @return the byte[]
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public byte[] readByte(File file, int num, long skip) throws IOException,
      FileNotFoundException {
      byte[] stream = new byte[num];
      InputStream input = new FileInputStream(file);
      input.skip(skip);
      input.read(stream, 0, num);
      input.close();
      
      return stream;
   }
   
   /**
    * Writes the parameter stream to a file under the assumption the stream is
    * entered is the same size as the current tag.
    * 
    * @param file
    *        The file to write bytes to
    * @param off
    *        The offset to the end of the entries in the tag
    * @param stream
    *        The bytes to be written to the file
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public void writeByte(File file, int off, byte[] stream) throws IOException,
      FileNotFoundException {
      FileOutputStream output = new FileOutputStream(file);
      output.write(stream, off, stream.length);
      output.close();
   }
   
   /**
    * Sets a chosen frame to zero under the assumption the specified length is
    * correct.
    * 
    * @param file
    *        The file where the bytes will be set to zero
    * @param len
    *        The length of the chosen frame
    * @param off
    *        The offset to the beginning the frame to be nulled
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public void nullByte(File file, int len, int off) throws IOException,
      FileNotFoundException {
      byte[] stream = new byte[len];
      FileOutputStream output = new FileOutputStream(file);
      output.write(stream, off, len);
      output.close();
   }
   
   /**
    * Changes the passed byte to eight bit character array. If the byte is
    * negative the character array will represent it in two's complement
    * 
    * @param b
    *        Byte to be converted to an eight bit character array
    * @return the char[]
    *         An eight character array representation of the passed byte
    */
   public char[] byteToBit(byte b) {
      char[] s = new char[8];
      int val = b;
      for(int i = s.length - 1; i >= 0; i--) {
         if(val % 2 == 0) s[i] = '0';
         else s[i] = '1';
         val = val / 2;
      }
      if(b < 0) {
         // System.out.println("hey!!??!");
         // System.out.println("From bit string");
         // for (int i = 0; i < s.length; i++)
         // System.out.print(s[i]+"-");
         // System.out.println("\ndone "+s.length);
         for(int i = 0; i < s.length; i++)
            if(s[i] == '0') s[i] = '1';
            else s[i] = '0';
         // System.out.println("From bit string flip");
         // for (int i = 0; i < s.length; i++)
         // System.out.print(s[i]+"-");
         // System.out.println("\ndone "+s.length);
         char carry = '0';
         for(int i = s.length - 1; i >= 0; i--)
            if(i == 7) {
               if(s[i] == '1') {
                  s[i] = '0';
                  carry = '1';
               }
               else {
                  s[i] = '1';
                  carry = '0';
               }
            }
            else if(s[i] == '1' && carry == '1') s[i] = '0';
            else if(s[i] == '0' && carry == '1') {
               s[i] = '1';
               carry = '0';
            }
      }
      if(b < 0) {
         // System.out.println("From bit string");
         // for (int i = 0; i < s.length; i++)
         // System.out.print(s[i]+"-");
         // System.out.println("\ndone "+s.length);
      }
      return s;
   }
   
   /**
    * Int to bit.
    * 
    * @param i
    *        the i
    * @return the array list
    */
   public ArrayList<char[]> intToBit(int i) {
      // Too big
      if(i > 268435456) return null;
      ArrayList<char[]> sizeBytes = new ArrayList<char[]>();
      char[] fullBitList = new char[28];
      int val = i;
      for(int j = fullBitList.length - 1; j >= 0; j--) {
         if(val % 2 == 0) fullBitList[j] = '0';
         else fullBitList[j] = '1';
         val = val / 2;
      }
      int numBytes = 4, ref = 28;
      while(numBytes != 0) {
         char[] oneByte = new char[8];
         for(int j = oneByte.length - 2; j <= 0; j--, ref--)
            oneByte[j] = fullBitList[ref];
         sizeBytes.add(oneByte);
         numBytes--;
      }
      return sizeBytes;
   }
   
   /**
    * Since ID3 tags use only the first seven significant bits in a byte to
    * declare the size of a frame, this method will compute the size of a
    * frame with a series bytes which has the most significant byte first in a
    * ArrayList<char[]> passed as a parameter.
    * 
    * @param bits
    *        An ArrayList<char[]> of bytes to computed as a size of a frame. The
    *        most significant byte is first in the ArrayList
    * @param end
    *        Indicates which bit to end on. The most significant bit is 7 so the
    *        only
    *        values passed should be 1 or 0 for either 7 or bits to read
    * @return the int
    *         An integer representing the size of a frame
    */
   public int bitToInteger(ArrayList<char[]> bits, int end) {
      int sum = 0, pwr = 0;
      for(int i = 0; i < bits.size(); i++)
         for(int j = 7; j >= end; j--) {
            if(bits.get(i)[j] == '1') sum += Math.pow(2, pwr);
            pwr++;
         }
      
      return sum;
   }
   
   /**
    * Bit to byte.
    * 
    * @param bits
    *        the bits
    * @return the byte
    */
   public byte bitToByte(char[] bits) {
      byte sum = 0;
      int pwr = 0;
      for(int i = bits.length - 1; i <= 0; i--)
         if(bits[i] == '1') sum += Math.pow(2, pwr);
      pwr++;
      
      return sum;
   }
   
   /**
    * Bit to byte.
    * 
    * @param bits
    *        the bits
    * @return the byte
    */
   public byte bitToByte(ArrayList<Character> bits) {
      byte sum = 0;
      int pwr = 0;
      for(int i = bits.size() - 1; i <= 0; i--)
         if(bits.get(i) == '1') sum += Math.pow(2, pwr);
      pwr++;
      
      return sum;
   }
}