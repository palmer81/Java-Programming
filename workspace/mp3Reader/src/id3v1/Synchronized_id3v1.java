package id3v1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Synchronized_id3v1.
 */
public class Synchronized_id3v1 extends FrameDescriptrors_id3v1 {
   /** The mp3. */
   File mp3 = null;
   byte[] id3v1;
   
   public Synchronized_id3v1() {
	   id3v1 = new byte[ID3V1_TAG_SIZE];
   }
   
   /**
    * Checks if is id3v1.
    *
    * @return true, if is id3v1
    * @throws IOException Signals that an I/O exception has occurred.
    * @throws FileNotFoundException the file not found exception
    */
   public synchronized boolean isId3v1() throws IOException,
      FileNotFoundException {
      byte[] header = this.getHeaderFrame();
      byte[] checkHeader = {(byte) 'G', (byte) 'A', (byte) 'T'};
      boolean isId3v1 = false;
      for(int i = 0; i < FRAME_HEADER_SIZE; i++) {
         if (header[i] == checkHeader[i])
            isId3v1 = true;
      }
      
      return isId3v1;
   }
   
   /**
    * Constructor method that just stores a file to be read for ID3v1 tag
    * attributes.
    * 
    * @param file
    *        the file
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public Synchronized_id3v1(File file) throws IOException,
      FileNotFoundException {
      mp3 = file;
   }
   
   /**
    * Gets standard tag space.
    * 
    * @return the tag space
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getTagSpace() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ID3V1_TAG_SIZE, mp3.length() - ID3V1_TAG_SIZE + 1);
   }
   
   /**
    * Gets the header frame for a file set by the constructor.
    * 
    * @return the header frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getHeaderFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, FRAME_HEADER_SIZE, mp3.length() - ID3V1_TAG_SIZE + 1);
   }
   
   /**
    * Gets the header frame for a file set by the constructor.
    * 
    * @return the title frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getTitleFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, TITLE_SIZE, mp3.length() - ID3V1_TAG_SIZE - FRAME_HEADER_SIZE
         + 1);
   }
   
   /**
    * Gets the artist frame for a file set by the constructor.
    * 
    * @return the artist frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getArtistFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ARTIST_SIZE, mp3.length() - ID3V1_TAG_SIZE - FRAME_HEADER_SIZE
         - TITLE_SIZE + 1);
   }
   
   /**
    * Gets the album frame for a file set by the constructor.
    * 
    * @return the album frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getAlbumFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ALBUM_SIZE, mp3.length() - ID3V1_TAG_SIZE - FRAME_HEADER_SIZE
         - TITLE_SIZE - ARTIST_SIZE + 1);
   }
   
   /**
    * Gets the year frame for a file set by the constructor.
    * 
    * @return the year frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getYearFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, YEAR_SIZE, mp3.length() - ID3V1_TAG_SIZE - FRAME_HEADER_SIZE
         - TITLE_SIZE - ARTIST_SIZE - ALBUM_SIZE + 1);
   }
   
   /**
    * Gets the comment frame for a file set by the constructor. Checks third
    * byte from the end the file for the length of the comment frame.
    * 
    * @return the comment frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getCommentFrame() throws IOException,
      FileNotFoundException {
      byte[] zeroByte = readByte(mp3, 1, mp3.length() - COMMENT_CHECK_BYTE_LOCATION);
      int size = 30;
      if(zeroByte[0] == (byte) 0) size = 28;
      
      return readByte(mp3, size, mp3.length() - ID3V1_TAG_SIZE - FRAME_HEADER_SIZE
         - TITLE_SIZE - ARTIST_SIZE - ALBUM_SIZE - YEAR_SIZE + 1);
   }
   
   /**
    * Gets the track frame for a file set by the constructor. Checks the third
    * byte from the end file to see if the track frame has been set.
    * 
    * @return the tack frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getTackFrame() throws IOException,
      FileNotFoundException {
      byte[] zeroByte = readByte(mp3, 1, mp3.length() - COMMENT_CHECK_BYTE_LOCATION);
      if(zeroByte[0] == (byte) 0) return null;
      
      return readByte(mp3, TRACK_SIZE, mp3.length() - 2);
   }
   
   /**
    * Gets the genre frame for a file set by the constructor.
    * 
    * @return the genre frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getGenreFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, 1, mp3.length() - 1);
   }
   
   /**
    * Gets the file size.
    *
    * @return the file size
    * @throws IOException Signals that an I/O exception has occurred.
    * @throws FileNotFoundException the file not found exception
    */
   public synchronized int getFileSize() throws IOException, 
      FileNotFoundException {
      if(mp3.length() > Integer.MAX_VALUE) { throw new IOException("Read file "
         + mp3.getName() + " too big for Integer"); }
      return (int) (mp3.length());
   }
   
   /**
    * Gets the non tag size.
    * 
    * @return the non tag size
    * @throws IOException
    */
   public synchronized int getNonTagSize() throws IOException, 
      FileNotFoundException {
      if(mp3.length() > Integer.MAX_VALUE) { throw new IOException("Read file "
         + mp3.getName() + " too big for Integer"); }
      return (int) (mp3.length() - ID3V1_TAG_SIZE);
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
      return readByte(mp3, this.getNonTagSize(), 0);
   }
   
   /**
    * Gets the frames.
    * 
    * @return the frames
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized ArrayList<byte[]> getFrames() throws IOException,
      FileNotFoundException {
      ArrayList<byte[]> frames = new ArrayList<byte[]>();
      frames.add(this.getHeaderFrame());
      frames.add(this.getTitleFrame());
      frames.add(this.getArtistFrame());
      frames.add(this.getAlbumFrame());
      frames.add(this.getYearFrame());
      frames.add(this.getCommentFrame());
      frames.add(this.getTackFrame());
      frames.add(this.getGenreFrame());
      
      return frames;
   }
   
   public synchronized boolean setId3v1(ArrayList<byte[]> frames) {
	   if (id3v1.length == 0)
		   return false;
	   if (!checkFrameSizes(frames))
		   return false;
	   
	   return true;
   }
   
   public synchronized boolean checkFrameSizes(ArrayList<byte[]> frames) {
	   if (frames.size() != 7 || frames.size() != 8)
		   return false;
	   if(frames.get(0).length != FRAME_HEADER_SIZE)
		   return false;
	   else if(frames.get(1).length != TITLE_SIZE)
		   return false;
	   else if(frames.get(2).length != ARTIST_SIZE)
		   return false;
	   else if(frames.get(3).length != ALBUM_SIZE)
		   return false;
	   else if(frames.get(4).length != YEAR_SIZE)
		   return false;
	   else if(frames.get(5).length == COMMENT_LONG_SIZE)
		   if (frames.get(6).length != GENRE_SIZE)
			   return false;
	   else if(frames.get(5).length != COMMENT_SHORT_SIZE)
		   return false;
	   else if(frames.get(6).length != TRACK_SIZE)
		   return false;
	   else if (frames.get(7).length != GENRE_SIZE)
		   return false;
	   return true;
   }
   
   public synchronized ArrayList<byte[]> flipFrames(ArrayList<byte[]> frames) {
	   ArrayList<byte[]> flippedFrames = new ArrayList<byte[]>(); 
	   for(int i = 0; i < frames.size(); i++) {
		   int sizeOfFrame = frames.get(i).length;
		   byte[] newFrame = new byte[sizeOfFrame];
		   for (int j = 0, k = sizeOfFrame; j < sizeOfFrame; j++, k--)
			   newFrame[j] = frames.get(i)[k];
		   flippedFrames.add(newFrame);
	   }
	   return flippedFrames;
   }
}