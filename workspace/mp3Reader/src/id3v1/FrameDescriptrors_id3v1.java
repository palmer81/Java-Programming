/**
 * 
 */
package id3v1;

import id3.ByteHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class FrameDescriptrors_id3v1.
 * 
 * @author scott
 */
public class FrameDescriptrors_id3v1 extends ByteHelper {
   /** The Constant ID3V1_TAG_SIZE. */
   public static final int ID3V1_TAG_SIZE = 127;
   
   /** The Constant FRAME_HEADER. */
   public static final int FRAME_HEADER_SIZE = 3;
   
   /** The Constant TITLE. */
   public static final int TITLE_SIZE = 30;
   
   /** The Constant ARTIST. */
   public static final int ARTIST_SIZE = 30;
   
   /** The Constant ALBUM. */
   public static final int ALBUM_SIZE = 30;
   
   /** The Constant YEAR. */
   public static final int YEAR_SIZE = 4;
   
   /** The Constant COMMENT_CHECK_BYTE. */
   public static final int COMMENT_CHECK_BYTE_LOCATION = 3;
   
   /** The Constant COMMENT_CHECK_BYTE. */
   public static final int COMMENT_SHORT_SIZE = 28;
   
   /** The Constant COMMENT_CHECK_BYTE. */
   public static final int COMMENT_LONG_SIZE = 30;
   
   /** The Constant GENRE. */
   public static final int GENRE_SIZE = 1;
   
   /** The Constant TRACK. */
   public static final int TRACK_SIZE = 1;
}
