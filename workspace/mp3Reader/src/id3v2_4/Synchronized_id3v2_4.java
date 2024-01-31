package id3v2_4;
import id3.ByteHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Synchronized_id3v2_4.
 */
public class Synchronized_id3v2_4 extends ByteHelper {
   
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
   public Synchronized_id3v2_4(File file) throws IOException,
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
   public byte[] getHeaderFrame() throws IOException, FileNotFoundException {
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
   public byte[] getNonTagData() throws IOException, FileNotFoundException {
      return readByte(mp3, this.getFileSize() - (this.getTagSize() + 10), this
         .getTagSize() + 10);
   }
   
   /**
    * Gets the file size.
    * 
    * @return the file size
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public int getFileSize() throws IOException, FileNotFoundException {
      if(mp3.length() > Integer.MAX_VALUE) {
         System.out.println("file too big");
         System.exit(1);
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
   public byte[] getHeaderVersionBytes() throws IOException,
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
   public byte getHeaderFlagByte() throws IOException, FileNotFoundException {
      byte[] flag = readByte(mp3, 1, 5);
      return flag[0];
   }
   
   /**
    * This checks the flag byte from the header for the existence of an
    * extended header.
    * 
    * @return true, if successful
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public boolean hasExtHeader() throws FileNotFoundException, IOException {
      byte flag = this.getHeaderFlagByte();
      if(flag == (byte) 64 || flag == (byte) 96 || flag == (byte) -64
         || flag == (byte) -96) return true;
      return false;
   }
   
   /**
    * This checks the flag byte from the header for the existence of an
    * footer.
    * 
    * @return true, if successful
    * @throws FileNotFoundException
    *         the file not found exception
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    */
   public boolean hasFooter() throws FileNotFoundException, IOException {
      byte flag = this.getHeaderFlagByte();
      if(flag == (byte) 16 || flag == (byte) 48 || flag == (byte) 80
         || flag == (byte) 112 || flag == (byte) -16 || flag == (byte) -48
         || flag == (byte) -80 || flag == (byte) -112) return true;
      return false;
   }
   
   /**
    * Gets the extended header size bytes.
    * 
    * @return the ext header size bytes
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public byte[] getExtHeaderSizeBytes() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 4, 10);
   }
   
   /**
    * Gets the ext header size.
    * 
    * @return the ext header size
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public int getExtHeaderSize() throws IOException, FileNotFoundException {
      byte[] extHeader = this.getExtHeaderSizeBytes();
      ArrayList<char[]> listBytes = new ArrayList<char[]>();
      for(int i = extHeader.length - 1; i >= 0; i--)
         listBytes.add(byteToBit(extHeader[i]));
      
      return bitToInteger(listBytes, 0);
   }
   
   /**
    * Gets the ext header.
    * 
    * @param size
    *        the size
    * @return the ext header
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public byte[] getExtHeader(int size) throws IOException,
      FileNotFoundException {
      return readByte(mp3, this.getExtHeaderSize(), 10);
   }
   
   /**
    * Gets the size bytes that represent the size of the all frames excluding
    * the size for the header frame.
    * 
    * @return the tag size bytes
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public byte[] getTagSizeBytes() throws IOException, FileNotFoundException {
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
   public int getTagSize() throws IOException, FileNotFoundException {
      byte[] sizeBytes = this.getTagSizeBytes();
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
   public byte[] getTagSpace() throws IOException, FileNotFoundException {
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
   public byte[] getNextFrame(byte[] frames, int offset) {
      if(frames[offset] == 0 && frames[offset + 1] == 0) // System.out.println("From getNextFrame: no more frames or no frames");
      return null;
      System.out.println("This the next frame size: "
         + getNextFrameSize(frames, offset));
      byte frame[] = new byte[getNextFrameSize(frames, offset) + 10];
      for(int i = 0; i < frame.length; i++)
         frame[i] = frames[i + offset];
      System.out.println("From getNextFrame: "
         + getNextFrameSize(frames, offset));
      for(int i = 0; i < frame.length; i++)
         System.out.print((char) frame[i] + " ");
      System.out.println();
      return frame;
   }
   
   /**
    * Gets the frame size of the frame from the start of the offset provided.
    * The size does not include the 10 bytes for the header.
    * 
    * @param frames
    *        the frames
    * @param offset
    *        the offset
    * @return the next frame size
    *         The size of the next frame as an integer. Returns -1 if no more
    *         frames.
    */
   public int getNextFrameSize(byte[] frames, int offset) {
      if(frames[offset] == 0 && frames[offset + 1] == 0) return -1;
      ArrayList<char[]> listBits = new ArrayList<char[]>();
      for(int i = offset + 7; i >= offset + 4; i--)
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
   public String getFrameDescriptor(byte[] frame) {
      if(frame.length <= 10) return null;
      String s = new String();
      for(int i = 0; i < 4; i++)
         s += (char) frame[i];
      
      return s;
   }
   
   /**
    * Gets the flag bytes for given frame specified by the parameter.
    * 
    * @param frame
    *        the frame
    * @return the frame flag bytes
    */
   public byte[] getFrameFlagBytes(byte[] frame) {
      if(frame.length <= 10) return null;
      byte[] flags = new byte[2];
      for(int i = 8; i < 10; i++)
         flags[i - 8] = frame[i];
      
      return flags;
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
   public String findFrameDescriptor(String descriptor)
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
   public ArrayList<byte[]> packAllFrames() throws FileNotFoundException,
      IOException {
      ArrayList<byte[]> packedFrames = new ArrayList<byte[]>();
      byte[] allFrames = this.getTagSpace();
      byte[] currFrame;
      int offset = 0;
      currFrame = getNextFrame(allFrames, offset);
      if(currFrame == null) return null;
      while(currFrame != null) {
         packedFrames.add(currFrame);
         System.out.println("pack: " + currFrame.length);
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
      if(tag.length != 4) return -1;
      int ref = -1;
      for(int i = 0; i < packedFrames.size() && !found; i++) {
         for(int j = 0; j < 4 && isTag; j++)
            if(j == 0 && packedFrames.get(i)[j] != tag[i]) isTag = false;
            else if(j == 1 && packedFrames.get(i)[j] != tag[i]) isTag = false;
            else if(j == 2 && packedFrames.get(i)[j] != tag[i]) isTag = false;
            else if(j == 3 && packedFrames.get(i)[j] != tag[i]) isTag = false;
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
   public String getTitle() throws FileNotFoundException, IOException {
      return findFrameDescriptor("TIT2");
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
   public String getArtist() throws FileNotFoundException, IOException {
      return findFrameDescriptor("TPE1");
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
   public String getAlbum() throws FileNotFoundException, IOException {
      return findFrameDescriptor("TALB");
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
   public String getYear() throws FileNotFoundException, IOException {
      return findFrameDescriptor("TYER");
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
   public String getTrack() throws FileNotFoundException, IOException {
      return findFrameDescriptor("TRCK");
   }
   
   /**
    * Creates the header frame.
    * 
    * @param unsynchronisation
    *        the unsynchronisation
    * @param experimental
    *        the experimental
    * @param extHeader
    *        the ext header
    * @param sizeOfTag
    *        the size of tag
    * @return the byte[]
    */
   public byte[] createHeaderFrame(int unsynchronisation, int experimental,
      int extHeader, int sizeOfTag) {
      byte[] header = new byte[10];
      header[0] = (byte) 'I';
      header[1] = (byte) 'D';
      header[2] = (byte) '3';
      header[3] = (byte) '0';
      header[4] = (byte) '3';
      if(unsynchronisation == 0 && extHeader == 0 && experimental == 0) header[5] =
         (byte) 0;
      else if(unsynchronisation == 1 && extHeader == 1 && experimental == 1) header[5] =
         (byte) 224;
      else if(unsynchronisation == 1 && extHeader == 0 && experimental == 0) header[5] =
         (byte) 128;
      else if(unsynchronisation == 1 && extHeader == 1 && experimental == 0) header[5] =
         (byte) 192;
      else if(unsynchronisation == 1 && extHeader == 0 && experimental == 1) header[5] =
         (byte) 160;
      else if(unsynchronisation == 0 && extHeader == 1 && experimental == 0) header[5] =
         (byte) 64;
      else if(unsynchronisation == 0 && extHeader == 1 && experimental == 1) header[5] =
         (byte) 96;
      else if(unsynchronisation == 0 && extHeader == 0 && experimental == 1) header[5] =
         (byte) 32;
      else return null;
      ArrayList<char[]> sizeBytes = intToBit(sizeOfTag);
      header[6] = bitToByte(sizeBytes.get(0));
      header[7] = bitToByte(sizeBytes.get(1));
      header[8] = bitToByte(sizeBytes.get(2));
      header[9] = bitToByte(sizeBytes.get(3));
      
      return header;
   }
   
   /**
    * Creates the tag frame.
    * 
    * @param des
    *        the des
    * @param tagPres
    *        the tag pres
    * @param filePres
    *        the file pres
    * @param readOnly
    *        the read only
    * @param compression
    *        the compression
    * @param encrpyt
    *        the encrpyt
    * @param group
    *        the group
    * @param content
    *        the content
    * @return the byte[]
    */
   public byte[] createTagFrame(String des, int tagPres, int filePres,
      int readOnly, int compression, int encrpyt, int group, String content) {
      byte[] frame = new byte[10 + content.length() + 2];
      char[] description = des.toCharArray();
      if(description.length != 4) return null;
      frame[0] = (byte) description[0];
      frame[1] = (byte) description[1];
      frame[2] = (byte) description[2];
      frame[3] = (byte) description[3];
      ArrayList<char[]> frameSizeBit = this.intToBit(content.length());
      if(frameSizeBit == null) return null;
      frame[4] = this.bitToByte(frameSizeBit.get(0));
      frame[5] = this.bitToByte(frameSizeBit.get(1));
      frame[6] = this.bitToByte(frameSizeBit.get(2));
      frame[7] = this.bitToByte(frameSizeBit.get(3));
      
      // Creating the first flag byte
      if(tagPres == 0 && filePres == 0 && readOnly == 0) frame[5] = (byte) 0;
      else if(tagPres == 1 && filePres == 1 && readOnly == 1) frame[5] =
         (byte) 224;
      else if(tagPres == 1 && filePres == 0 && readOnly == 0) frame[5] =
         (byte) 128;
      else if(tagPres == 1 && filePres == 1 && readOnly == 0) frame[5] =
         (byte) 192;
      else if(tagPres == 1 && filePres == 0 && readOnly == 1) frame[5] =
         (byte) 160;
      else if(tagPres == 0 && filePres == 1 && readOnly == 0) frame[5] =
         (byte) 64;
      else if(tagPres == 0 && filePres == 1 && readOnly == 1) frame[5] =
         (byte) 96;
      else if(tagPres == 0 && filePres == 0 && readOnly == 1) frame[5] =
         (byte) 32;
      else return null;
      // Creating the second flag byte
      if(compression == 0 && encrpyt == 0 && group == 0) frame[6] = (byte) 0;
      else if(compression == 1 && encrpyt == 1 && group == 1) frame[6] =
         (byte) 224;
      else if(compression == 1 && encrpyt == 0 && group == 0) frame[6] =
         (byte) 128;
      else if(compression == 1 && encrpyt == 1 && group == 0) frame[6] =
         (byte) 192;
      else if(compression == 1 && encrpyt == 0 && group == 1) frame[6] =
         (byte) 160;
      else if(compression == 0 && encrpyt == 1 && group == 0) frame[6] =
         (byte) 64;
      else if(compression == 0 && encrpyt == 1 && group == 1) frame[6] =
         (byte) 96;
      else if(compression == 0 && encrpyt == 0 && group == 1) frame[6] =
         (byte) 32;
      else return null;
      for(int i = 0; i < content.length(); i++)
         frame[i + 11] = (byte) content.charAt(i);
      return frame;
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
   public void writeFrames(byte[] header, ArrayList<byte[]> allFrames)
      throws FileNotFoundException, IOException {
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