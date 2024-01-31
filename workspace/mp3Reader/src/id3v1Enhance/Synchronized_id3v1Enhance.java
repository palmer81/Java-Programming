/**
 * 
 */
package id3v1Enhance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author scott
 *
 */
public class Synchronized_id3v1Enhance extends FrameDescriptrors_id3v1Enhance {
   /** The mp3. */
   File mp3 = null;
   
   public Synchronized_id3v1Enhance(File file) throws IOException,
      FileNotFoundException {
      mp3 = file;
   }
   
   /**
    * Checks if is id3v1+.
    *
    * @return true, if is id3v1+
    * @throws IOException Signals that an I/O exception has occurred.
    * @throws FileNotFoundException the file not found exception
    */
   public synchronized boolean isId3v1Enhance() throws IOException,
      FileNotFoundException {
      byte[] header = this.getEnhanceHeaderFrame();
      byte[] checkHeader = {(byte) '+', (byte) 'G', (byte) 'A', (byte) 'T'};
      boolean isId3v1 = false;
      for(int i = 0; i < ENHANCE_FRAME_HEADER_SIZE; i++) {
         if (header[i] == checkHeader[i])
            isId3v1 = true;
      }
      
      return isId3v1;
   }
   
   /**
    * Gets the enhanced header frame for a file set by the constructor.
    * 
    * @return the enhance header frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceHeaderFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_FRAME_HEADER_SIZE, mp3.length() - ID3V1_TAG_SIZE
         + ID3V1_TAG_ENHACNE_SIZE);
   }
   
   /**
    * Gets the enhanced title frame for a file set by the constructor.
    * 
    * @return the enhance title frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceTitleFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_TITLE_SIZE, mp3.length() - 352);
   }
   
   /**
    * Gets the enhanced artist frame for a file set by the constructor.
    * 
    * @return the enhance artist frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceArtistFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_ARTIST_SIZE, mp3.length() - 292);
   }
   
   /**
    * Gets the enhanced album frame for a file set by the constructor.
    * 
    * @return the enhance album frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceAlbumFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_ALBUM_SIZE, mp3.length() - 232);
   }
   
   /**
    * Gets the enhanced speed frame for a file set by the constructor.
    * 
    * @return the enhance speed frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceSpeedFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_SPEED_SIZE, mp3.length() - 172);
   }
   
   /**
    * Gets the enhanced genre frame for a file set by the constructor.
    * 
    * @return the enhance genre frame
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceGenreFrame() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_GENERE_SIZE, mp3.length() - 171);
   }
   
   /**
    * Gets the enhanced start-time frame for a file set by the constructor.
    * 
    * @return the enhance start time
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceStartTime() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_START_TIME_SIZE, mp3.length() - 141);
   }
   
   /**
    * Gets the enhanced end-time frame for a file set by the constructor.
    * 
    * @return the enhance end time
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized byte[] getEnhanceEndTime() throws IOException,
      FileNotFoundException {
      return readByte(mp3, ENHANCE_END_TIME_SIZE, mp3.length() - 135);
   }
   
   /**
    * Gets the enhance frames.
    * 
    * @return ArrayList<byte[]>
    * @throws IOException
    *         Signals that an I/O exception has occurred.
    * @throws FileNotFoundException
    *         the file not found exception
    */
   public synchronized ArrayList<byte[]> getEnhanceFrames() throws IOException,
      FileNotFoundException {
      ArrayList<byte[]> framesEnhance = new ArrayList<byte[]>();
      framesEnhance.add(this.getEnhanceHeaderFrame());
      framesEnhance.add(this.getEnhanceTitleFrame());
      framesEnhance.add(this.getEnhanceArtistFrame());
      framesEnhance.add(this.getEnhanceAlbumFrame());
      framesEnhance.add(this.getEnhanceSpeedFrame());
      framesEnhance.add(this.getEnhanceGenreFrame());
      framesEnhance.add(this.getEnhanceStartTime());
      framesEnhance.add(this.getEnhanceEndTime());
      
      return framesEnhance;
   }
}
