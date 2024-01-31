/**
 * 
 */
package id3v2_2;

import id3.ByteHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class FrameDescriptors_id3v2_2.
 * 
 * @author scott
 */
public class FrameDescriptors_id3v2_2 extends ByteHelper {
   
   /**
    * Sets the buf.
    * 
    * @return the byte[]
    */
   public byte[] setBUF() {
      byte[] recommendedBufferSize = {(byte) 'B', (byte) 'U', (byte) 'F'};
      return recommendedBufferSize;
   }
   
   /**
    * Sets the cnt.
    * 
    * @return the byte[]
    */
   public byte[] setCNT() {
      byte[] playCounter = {(byte) 'C', (byte) 'N', (byte) 'T'};
      return playCounter;
   }
   
   /**
    * Sets the com.
    * 
    * @return the byte[]
    */
   public byte[] setCOM() {
      byte[] comments = {(byte) 'C', (byte) 'O', (byte) 'M'};
      return comments;
   }
   
   /**
    * Sets the cra.
    * 
    * @return the byte[]
    */
   public byte[] setCRA() {
      byte[] comments = {(byte) 'C', (byte) 'R', (byte) 'A'};
      return comments;
   }
   
   /**
    * Sets the crm.
    * 
    * @return the byte[]
    */
   public byte[] setCRM() {
      byte[] encrpytedMetaFrame = {(byte) 'C', (byte) 'R', (byte) 'M'};
      return encrpytedMetaFrame;
   }
   
   /**
    * Sets the etc.
    * 
    * @return the byte[]
    */
   public byte[] setETC() {
      byte[] eventTimingCodes = {(byte) 'E', (byte) 'T', (byte) 'C'};
      return eventTimingCodes;
   }
   
   /**
    * Sets the equ.
    * 
    * @return the byte[]
    */
   public byte[] setEQU() {
      byte[] equalization = {(byte) 'E', (byte) 'Q', (byte) 'U'};
      return equalization;
   }
   
   /**
    * Sets the geo.
    * 
    * @return the byte[]
    */
   public byte[] setGEO() {
      byte[] comments = {(byte) 'G', (byte) 'E', (byte) 'O'};
      return comments;
   }
   
   /**
    * Sets the ipl.
    * 
    * @return the byte[]
    */
   public byte[] setIPL() {
      byte[] comments = {(byte) 'I', (byte) 'P', (byte) 'L'};
      return comments;
   }
   
   /**
    * Sets the lnk.
    * 
    * @return the byte[]
    */
   public byte[] setLNK() {
      byte[] comments = {(byte) 'L', (byte) 'N', (byte) 'K'};
      return comments;
   }
   
   /**
    * Sets the mci.
    * 
    * @return the byte[]
    */
   public byte[] setMCI() {
      byte[] comments = {(byte) 'M', (byte) 'C', (byte) 'I'};
      return comments;
   }
   
   /**
    * Sets the mll.
    * 
    * @return the byte[]
    */
   public byte[] setMLL() {
      byte[] comments = {(byte) 'M', (byte) 'L', (byte) 'L'};
      return comments;
   }
   
   /**
    * Sets the pic.
    * 
    * @return the byte[]
    */
   public byte[] setPIC() {
      byte[] attchedPicture = {(byte) 'P', (byte) 'I', (byte) 'C'};
      return attchedPicture;
   }
   
   /**
    * Sets the pop.
    * 
    * @return the byte[]
    */
   public byte[] setPOP() {
      byte[] popularimeter = {(byte) 'P', (byte) 'O', (byte) 'P'};
      return popularimeter;
   }
   
   /**
    * Sets the rev.
    * 
    * @return the byte[]
    */
   public byte[] setREV() {
      byte[] reverb = {(byte) 'R', (byte) 'E', (byte) 'V'};
      return reverb;
   }
   
   /**
    * Sets the rva.
    * 
    * @return the byte[]
    */
   public byte[] setRVA() {
      byte[] comments = {(byte) 'R', (byte) 'V', (byte) 'A'};
      return comments;
   }
   
   /**
    * Sets the slt.
    * 
    * @return the byte[]
    */
   public byte[] setSLT() {
      byte[] comments = {(byte) 'S', (byte) 'L', (byte) 'T'};
      return comments;
   }
   
   /**
    * Sets the stc.
    * 
    * @return the byte[]
    */
   public byte[] setSTC() {
      byte[] comments = {(byte) 'S', (byte) 'T', (byte) 'C'};
      return comments;
   }
   
   /**
    * Sets the tal.
    * 
    * @return the byte[]
    */
   public byte[] setTAL() {
      byte[] comments = {(byte) 'T', (byte) 'A', (byte) 'L'};
      return comments;
   }
   
   /**
    * Sets the tbp.
    * 
    * @return the byte[]
    */
   public byte[] setTBP() {
      byte[] comments = {(byte) 'T', (byte) 'B', (byte) 'P'};
      return comments;
   }
   
   /**
    * Sets the tco.
    * 
    * @return the byte[]
    */
   public byte[] setTCO() {
      byte[] comments = {(byte) 'T', (byte) 'C', (byte) 'O'};
      return comments;
   }
   
   /**
    * Sets the tcr.
    * 
    * @return the byte[]
    */
   public byte[] setTCR() {
      byte[] comments = {(byte) 'T', (byte) 'C', (byte) 'R'};
      return comments;
   }
   
   /**
    * Sets the tda.
    * 
    * @return the byte[]
    */
   public byte[] setTDA() {
      byte[] comments = {(byte) 'T', (byte) 'D', (byte) 'A'};
      return comments;
   }
   
   /**
    * Sets the tdy.
    * 
    * @return the byte[]
    */
   public byte[] setTDY() {
      byte[] comments = {(byte) 'T', (byte) 'D', (byte) 'Y'};
      return comments;
   }
   
   /**
    * Sets the ten.
    * 
    * @return the byte[]
    */
   public byte[] setTEN() {
      byte[] comments = {(byte) 'T', (byte) 'E', (byte) 'N'};
      return comments;
   }
   
   /**
    * Sets the tft.
    * 
    * @return the byte[]
    */
   public byte[] setTFT() {
      byte[] comments = {(byte) 'T', (byte) 'F', (byte) 'T'};
      return comments;
   }
   
   /**
    * Sets the tim.
    * 
    * @return the byte[]
    */
   public byte[] setTIM() {
      byte[] comments = {(byte) 'T', (byte) 'I', (byte) 'M'};
      return comments;
   }
   
   /**
    * Sets the tke.
    * 
    * @return the byte[]
    */
   public byte[] setTKE() {
      byte[] comments = {(byte) 'T', (byte) 'K', (byte) 'E'};
      return comments;
   }
   
   /**
    * Sets the toa.
    * 
    * @return the byte[]
    */
   public byte[] setTOA() {
      byte[] comments = {(byte) 'T', (byte) 'O', (byte) 'A'};
      return comments;
   }
   
   /**
    * Sets the tof.
    * 
    * @return the byte[]
    */
   public byte[] setTOF() {
      byte[] comments = {(byte) 'T', (byte) 'O', (byte) 'F'};
      return comments;
   }
   
   /**
    * Sets the tol.
    * 
    * @return the byte[]
    */
   public byte[] setTOL() {
      byte[] comments = {(byte) 'C', (byte) 'O', (byte) 'M'};
      return comments;
   }
   
   /**
    * Sets the t p1.
    * 
    * @return the byte[]
    */
   public byte[] setTP1() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) '1'};
      return comments;
   }
   
   /**
    * Sets the t p2.
    * 
    * @return the byte[]
    */
   public byte[] setTP2() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) '2'};
      return comments;
   }
   
   /**
    * Sets the t p3.
    * 
    * @return the byte[]
    */
   public byte[] setTP3() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) '3'};
      return comments;
   }
   
   /**
    * Sets the t p4.
    * 
    * @return the byte[]
    */
   public byte[] setTP4() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) '4'};
      return comments;
   }
   
   /**
    * Sets the tpa.
    * 
    * @return the byte[]
    */
   public byte[] setTPA() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) 'A'};
      return comments;
   }
   
   /**
    * Sets the tpb.
    * 
    * @return the byte[]
    */
   public byte[] setTPB() {
      byte[] comments = {(byte) 'T', (byte) 'P', (byte) 'B'};
      return comments;
   }
   
   /**
    * Sets the trc.
    * 
    * @return the byte[]
    */
   public byte[] setTRC() {
      byte[] comments = {(byte) 'T', (byte) 'R', (byte) 'C'};
      return comments;
   }
   
   /**
    * Sets the trd.
    * 
    * @return the byte[]
    */
   public byte[] setTRD() {
      byte[] comments = {(byte) 'T', (byte) 'R', (byte) 'D'};
      return comments;
   }
   
   /**
    * Sets the tsi.
    * 
    * @return the byte[]
    */
   public byte[] setTSI() {
      byte[] comments = {(byte) 'T', (byte) 'S', (byte) 'I'};
      return comments;
   }
   
   /**
    * Sets the tss.
    * 
    * @return the byte[]
    */
   public byte[] setTSS() {
      byte[] comments = {(byte) 'T', (byte) 'S', (byte) 'S'};
      return comments;
   }
   
   /**
    * Sets the t t1.
    * 
    * @return the byte[]
    */
   public byte[] setTT1() {
      byte[] comments = {(byte) 'T', (byte) 'T', (byte) '1'};
      return comments;
   }
   
   /**
    * Sets the t t2.
    * 
    * @return the byte[]
    */
   public byte[] setTT2() {
      byte[] comments = {(byte) 'T', (byte) 'T', (byte) '2'};
      return comments;
   }
   
   /**
    * Sets the t t3.
    * 
    * @return the byte[]
    */
   public byte[] setTT3() {
      byte[] comments = {(byte) 'T', (byte) 'T', (byte) '3'};
      return comments;
   }
   
   /**
    * Sets the txt.
    * 
    * @return the byte[]
    */
   public byte[] setTXT() {
      byte[] comments = {(byte) 'T', (byte) 'X', (byte) 'T'};
      return comments;
   }
   
   /**
    * Sets the tye.
    * 
    * @return the byte[]
    */
   public byte[] setTYE() {
      byte[] comments = {(byte) 'T', (byte) 'Y', (byte) 'E'};
      return comments;
   }
   
   /**
    * Sets the ufi.
    * 
    * @return the byte[]
    */
   public byte[] setUFI() {
      byte[] comments = {(byte) 'U', (byte) 'F', (byte) 'I'};
      return comments;
   }
   
   /**
    * Sets the ult.
    * 
    * @return the byte[]
    */
   public byte[] setULT() {
      byte[] comments = {(byte) 'U', (byte) 'L', (byte) 'T'};
      return comments;
   }
   
   /**
    * Sets the waf.
    * 
    * @return the byte[]
    */
   public byte[] setWAF() {
      byte[] comments = {(byte) 'W', (byte) 'A', (byte) 'F'};
      return comments;
   }
   
   /**
    * Sets the war.
    * 
    * @return the byte[]
    */
   public byte[] setWAR() {
      byte[] comments = {(byte) 'W', (byte) 'A', (byte) 'R'};
      return comments;
   }
   
   /**
    * Sets the wcm.
    * 
    * @return the byte[]
    */
   public byte[] setWCM() {
      byte[] comments = {(byte) 'W', (byte) 'C', (byte) 'M'};
      return comments;
   }
   
   /**
    * Sets the wcp.
    * 
    * @return the byte[]
    */
   public byte[] setWCP() {
      byte[] comments = {(byte) 'W', (byte) 'C', (byte) 'P'};
      return comments;
   }
   
   /**
    * Sets the wpb.
    * 
    * @return the byte[]
    */
   public byte[] setWPB() {
      byte[] comments = {(byte) 'W', (byte) 'P', (byte) 'B'};
      return comments;
   }
}
