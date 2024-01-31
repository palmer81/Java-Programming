package id3v2_2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Synchronized_id3v2_2.
 */

/**
 * @author scott
 */
public class Synchronized_id3v2_2 extends FrameDescriptors_id3v2_2 {
   
   /** The mp3. */
   File mp3 = null;
   
   /**
    * Constructor method that just stores a file to be read for ID3v2_2 tag
    * attributes.
    * 
    * @param file
    *        the file
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public Synchronized_id3v2_2(File file) throws IOException,
      FileNotFoundException {
      mp3 = file;
   }
   
   /**
    * Gets the header frame.
    * 
    * @return the header frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getHeaderFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 10, 0);
   }
   
   /**
    * Gets the non tag data.
    * 
    * @return the non tag data
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getNonTagData() throws IOException,
      FileNotFoundException {
      return readByte(mp3, this.getFileSize() - (this.getTagSize() + 10), this
         .getTagSize() + 10);
   }
   
   /**
    * Gets the file size.
    *
    * @return the file size
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public synchronized int getFileSize() throws IOException,
      FileNotFoundException {
      if (mp3.length() > Integer.MAX_VALUE) {
         throw new IOException("Read file "
            + mp3.getName() + " too big for Integer");
      }
      return (int) mp3.length();
   }
   
   /**
    * Gets the version bytes in the header frame.
    * 
    * @return the header version bytes
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getHeaderVersionBytes() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 5, 0);
   }
   
   /**
    * Gets the byte that represents the flags set in the header frame.
    * 
    * @return the header flag byte
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getHeaderFlagByte() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 1, 5);
   }
   
   /**
    * Gets the bytes that represent the size of the all frames excluding the
    * the size for the header frame.
    * 
    * @return the tag size bytes
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getTagSizeBytes() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 4, 6);
   }
   
   /**
    * Gets integer of the size of all frames excluding the size for the
    * header frame.
    * 
    * @return the tag size
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized int getTagSize() throws IOException,
      FileNotFoundException {
      byte[] sizeBytes = getTagSizeBytes();
      ArrayList<char[]> listBytes = new ArrayList<char[]>();
      for(int i = sizeBytes.length - 1; i >= 0; i--)
         listBytes.add(byteToBit(sizeBytes[i]));
      
      return bitToInteger(listBytes, 1);
   }
   
   /**
    * Gets all the frames and returns them all in one byte array.
    * 
    * @return the tag space
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    *         An array of the all the frames and their headers. Does not return
    *         the
    *         actual header frame of the tag.
    */
   public synchronized byte[] getTagSpace() throws IOException,
      FileNotFoundException {
      return readByte(mp3, getTagSize(), 10);
   }
   
   /**
    * Gets the next frame from an offset in an array of all frames. The frame
    * includes the header of the frame and the data.
    * 
    * @param frames
    *        All frames in the tag.
    * @param offset
    *        The offset of the end of a frame.
    * @return the next frame
    *         Returns the next frame from an array of all the frames of a given
    *         offset. The returning array includes the header of the frame
    *         as well as the content. Returns null if there are no more frames.
    */
   public synchronized byte[] getNextFrame(byte[] frames, int offset) {
      if(frames[offset] == 0 && frames[offset + 1] == 0) // System.out.println("From getNextFrame: no more frames or no frames");
      return null;
      byte frame[] = new byte[getNextFrameSize(frames, offset) + 6];
      for(int i = 0; i < frame.length; i++)
         frame[i] = frames[i + offset];
      // System.out.println("From getNextFrame: "+getNextFrameSize(frames,
      // offset));
      // for (int i = 0; i < frame.length; i++)
      // System.out.print((char)frame[i]+" ");
      // System.out.println();
      return frame;
   }
   
   /**
    * Gets the frame size of the frame from the start of the offset provided.
    * The size does not include the 6 bytes for the header.
    * 
    * @param frames
    *        the frames
    * @param offset
    *        the offset
    * @return the next frame size
    *         The size of the next frame as an integer. Returns -1 if no more
    *         frames.
    */
   public synchronized int getNextFrameSize(byte[] frames, int offset) {
      if(frames[offset] == 0 && frames[offset + 1] == 0) return -1;
      ArrayList<char[]> listBits = new ArrayList<char[]>();
      for(int i = offset + 5; i >= offset + 3; i--)
         listBits.add(byteToBit(frames[i]));
      
      return bitToInteger(listBits, 0);
   }
   
   /**
    * Gets the descriptor for the given frame.
    * 
    * @param frame
    *        the frame
    * @return the frame descriptor
    */
   public synchronized String getFrameDescriptor(byte[] frame) {
      if(frame.length <= 6) return null;
      String s = new String();
      for(int i = 0; i < 3; i++)
         s += (char) frame[i];
      
      return s;
   }
   
   /**
    * Finds a descriptor from all frames.
    * 
    * @param descriptor
    *        the descriptor
    * @return the string
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String findFrameDescriptor(String descriptor)
      throws FileNotFoundException, IOException {
      String s = new String();
      byte[] allFrames = this.getTagSpace();
      byte[] currFrame;
      int offset = 0;
      currFrame = getNextFrame(allFrames, offset);
      while(currFrame != null) {
         if(getFrameDescriptor(currFrame).toUpperCase().equals(
            descriptor.toUpperCase())) {
            s = new String();
            for(int i = 7; i < currFrame.length - 1; i++)
               s += (char) currFrame[i];
            // System.out.print(currFrame[i]+" ");
            // System.out.println();
            break;
         }
         else s = null;
         offset += currFrame.length;
         currFrame = getNextFrame(allFrames, offset);
      }
      
      return s;
   }
   
   /**
    * Packs all frames except the header frame into a ArrayList<byte[]> in the
    * order they appear in the file.
    * 
    * @return the array list
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized ArrayList<byte[]> packAllFrames()
      throws FileNotFoundException, IOException {
      ArrayList<byte[]> packedFrames = new ArrayList<byte[]>();
      byte[] allFrames = this.getTagSpace();
      byte[] currFrame;
      int offset = 0;
      currFrame = getNextFrame(allFrames, offset);
      if(currFrame == null) return null;
      while(currFrame != null) {
         packedFrames.add(currFrame);
         // System.out.println("pack: "+currFrame.length);
         offset += currFrame.length;
         currFrame = getNextFrame(allFrames, offset);
      }
      
      return packedFrames;
   }
   
   /**
    * Find packed frame.
    * 
    * @param tag
    *        The given tag to find with a packed frame ArrayList<byte[]>.
    * @param packedFrames
    *        The packed frames for an ID3 tag.
    * @return the int
    *         The reference in the ArrayList that has chosen tag to find. If the
    *         tag
    *         is not found or the tag is the improper length the return will be
    *         -1.
    */
   public int findPackedFrame(byte[] tag, ArrayList<byte[]> packedFrames) {
      boolean found = false, isTag = true;
      if(tag.length != 3) return -1;
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 3 && isTag; j++)
            if(j == 0 && packedFrames.get(i)[j] != tag[i]) isTag = false;
            else if(j == 1 && packedFrames.get(i)[j] != tag[i]) isTag = false;
            else if(j == 2 && packedFrames.get(i)[j] != tag[i]) isTag = false;
         if(isTag) {
            ref = i;
            found = true;
         }
         isTag = true;
      }
      return ref;
   }
   
   /**
    * Gets the string for the title.
    * 
    * @return the title
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String getTitle() throws FileNotFoundException,
      IOException {
      return findFrameDescriptor("TT2");
   }
   
   /**
    * Gets the string for the artist.
    * 
    * @return the artist
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String getArtist() throws FileNotFoundException,
      IOException {
      return findFrameDescriptor("TP1");
   }
   
   /**
    * Gets the string for the album.
    * 
    * @return the album
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String getAlbum() throws FileNotFoundException,
      IOException {
      return findFrameDescriptor("TAL");
   }
   
   /**
    * Gets the string for the year.
    * 
    * @return the year
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String getYear() throws FileNotFoundException,
      IOException {
      return findFrameDescriptor("TYE");
   }
   
   /**
    * Gets the string for the track.
    * 
    * @return the track
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized String getTrack() throws FileNotFoundException,
      IOException {
      return findFrameDescriptor("TRK");
   }
   
   /**
    * Creates the header frame.
    * 
    * @param synchronization
    *        the synchronization
    * @param compression
    *        the compression
    * @param sizeOfTag
    *        the size of tag
    * @return the byte[]
    */
   public synchronized byte[] createHeaderFrame(int synchronization,
      int compression, int sizeOfTag) {
      byte[] header = new byte[10];
      header[0] = (byte) 'I';
      header[1] = (byte) 'D';
      header[2] = (byte) '3';
      header[3] = (byte) '0';
      header[4] = (byte) '2';
      if(synchronization == 0 && compression == 0) header[5] = (byte) 0;
      else if(synchronization == 1 && compression == 1) header[5] = (byte) 192;
      else if(synchronization == 1 && compression == 0) header[5] = (byte) 128;
      else if(synchronization == 0 && compression == 1) header[5] = (byte) 64;
      else return null;
      ArrayList<char[]> sizeBytes = intToBit(sizeOfTag);
      header[6] = bitToByte(sizeBytes.get(0));
      header[7] = bitToByte(sizeBytes.get(1));
      header[8] = bitToByte(sizeBytes.get(2));
      header[9] = bitToByte(sizeBytes.get(3));
      
      return header;
   }
   
   /**
    * Writes all frames passed and remaining bytes in tag are set to zero.
    * 
    * @param header
    *        the header
    * @param allFrames
    *        the all frames
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public synchronized void writeFrames(byte[] header,
      ArrayList<byte[]> allFrames) throws FileNotFoundException, IOException {
      int size = this.getFileSize();
      byte[] tag = new byte[size];
      int index = 0;
      for(int i = 0; i < header.length; i++, index++)
         tag[index] = header[i];
      
      for(int i = 0; i < allFrames.size(); i++)
         for(int j = 0; j < allFrames.get(i).length; j++, index++)
            tag[index] = allFrames.get(i)[j];
      index = this.getTagSize() + 10;
      byte[] nonTagData = this.getNonTagData();
      for(int i = 0; i < nonTagData.length; i++, index++)
         tag[index] = nonTagData[i];
      System.out.println("From write: Array about to write " + tag.length + " "
         + this.getFileSize());
      
      // for (int i = 0; i < tag.length; i++) {
      // if (i%4000 == 0)
      // System.out.println();
      // System.out.print((i+1)+": "+(char)tag[i]+" -> "+tag[i]+" ");
      // }
      System.out.println();
      System.out.println("\ndone");
      File tmp = new File("/Users/scott/Music/Test.mp3");
      this.writeByte(tmp, 0, tag);
   }
}
