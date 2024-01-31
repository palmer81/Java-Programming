package id3v2_4;
// TODO: Auto-generated Javadoc

/**
 * The Class FrameDescriptors_id3v2_4.
 */

/**
 * @author scott
 */
public class FrameDescriptors_id3v2_4 {
   
   /**
    * Sets the aenc.
    * 
    * @return the byte[]
    */
   public byte[] setAENC() {
      byte[] audioEncryption = {(byte) 'A', (byte) 'E', (byte) 'N', (byte) 'C'};
      return audioEncryption;
   }
   
   /**
    * Sets the apic.
    * 
    * @return the byte[]
    */
   public byte[] setAPIC() {
      byte[] attachedPicture = {(byte) 'A', (byte) 'P', (byte) 'I', (byte) 'C'};
      return attachedPicture;
   }
   
   /**
    * Sets the aspi.
    * 
    * @return the byte[]
    */
   public byte[] setASPI() {
      byte[] audioSeekPointIndex =
         {(byte) 'A', (byte) 'S', (byte) 'P', (byte) 'I'};
      return audioSeekPointIndex;
   }
   
   /**
    * Sets the comm.
    * 
    * @return the byte[]
    */
   public byte[] setCOMM() {
      byte[] comments = {(byte) 'C', (byte) 'O', (byte) 'M', (byte) 'M'};
      return comments;
   }
   
   /**
    * Sets the comr.
    * 
    * @return the byte[]
    */
   public byte[] setCOMR() {
      byte[] commericalFrame = {(byte) 'C', (byte) 'O', (byte) 'M', (byte) 'R'};
      return commericalFrame;
   }
   
   /**
    * Sets the encr.
    * 
    * @return the byte[]
    */
   public byte[] setENCR() {
      byte[] encryptionMethodRegistration =
         {(byte) 'E', (byte) 'N', (byte) 'C', (byte) 'R'};
      return encryptionMethodRegistration;
   }
   
   /**
    * Sets the eq u2.
    * 
    * @return the byte[]
    */
   public byte[] setEQU2() {
      byte[] equalisation2 = {(byte) 'E', (byte) 'Q', (byte) 'U', (byte) '2'};
      return equalisation2;
   }
   
   /**
    * Sets the etco.
    * 
    * @return the byte[]
    */
   public byte[] setETCO() {
      byte[] eventTimingCodes =
         {(byte) 'E', (byte) 'T', (byte) 'C', (byte) 'O'};
      return eventTimingCodes;
   }
   
   /**
    * Sets the geob.
    * 
    * @return the byte[]
    */
   public byte[] setGEOB() {
      byte[] generalEncapsulationObject =
         {(byte) 'G', (byte) 'E', (byte) 'O', (byte) 'B'};
      return generalEncapsulationObject;
   }
   
   /**
    * Sets the grid.
    * 
    * @return the byte[]
    */
   public byte[] setGRID() {
      byte[] groupIdentificationRegistration =
         {(byte) 'G', (byte) 'R', (byte) 'I', (byte) 'D'};
      return groupIdentificationRegistration;
   }
   
   /**
    * Sets the link.
    * 
    * @return the byte[]
    */
   public byte[] setLINK() {
      byte[] linkedInformation =
         {(byte) 'L', (byte) 'I', (byte) 'N', (byte) 'K'};
      return linkedInformation;
   }
   
   /**
    * Sets the mcdi.
    * 
    * @return the byte[]
    */
   public byte[] setMCDI() {
      byte[] musicCdIdentifier =
         {(byte) 'M', (byte) 'C', (byte) 'D', (byte) 'I'};
      return musicCdIdentifier;
   }
   
   /**
    * Sets the mllt.
    * 
    * @return the byte[]
    */
   public byte[] setMLLT() {
      byte[] mpegLocationLookupTable =
         {(byte) 'M', (byte) 'L', (byte) 'L', (byte) 'T'};
      return mpegLocationLookupTable;
   }
   
   /**
    * Sets the owne.
    * 
    * @return the byte[]
    */
   public byte[] setOWNE() {
      byte[] ownershipFrame = {(byte) 'O', (byte) 'W', (byte) 'N', (byte) 'E'};
      return ownershipFrame;
   }
   
   /**
    * Sets the priv.
    * 
    * @return the byte[]
    */
   public byte[] setPRIV() {
      byte[] privateFrame = {(byte) 'P', (byte) 'R', (byte) 'I', (byte) 'V'};
      return privateFrame;
   }
   
   /**
    * Sets the pcnt.
    * 
    * @return the byte[]
    */
   public byte[] setPCNT() {
      byte[] playCounter = {(byte) 'P', (byte) 'C', (byte) 'N', (byte) 'T'};
      return playCounter;
   }
   
   /**
    * Sets the popm.
    * 
    * @return the byte[]
    */
   public byte[] setPOPM() {
      byte[] popularimeter = {(byte) 'P', (byte) 'O', (byte) 'P', (byte) 'M'};
      return popularimeter;
   }
   
   /**
    * Sets the poss.
    * 
    * @return the byte[]
    */
   public byte[] setPOSS() {
      byte[] positionSynchronisationFrame =
         {(byte) 'P', (byte) 'O', (byte) 'S', (byte) 'S'};
      return positionSynchronisationFrame;
   }
   
   /**
    * Sets the rbuf.
    * 
    * @return the byte[]
    */
   public byte[] setRBUF() {
      byte[] recommendedBufferSize =
         {(byte) 'R', (byte) 'B', (byte) 'U', (byte) 'F'};
      return recommendedBufferSize;
   }
   
   /**
    * Sets the rv a2.
    * 
    * @return the byte[]
    */
   public byte[] setRVA2() {
      byte[] relativeVolumeAdjustment2 =
         {(byte) 'R', (byte) 'V', (byte) 'A', (byte) '2'};
      return relativeVolumeAdjustment2;
   }
   
   /**
    * Sets the rvrb.
    * 
    * @return the byte[]
    */
   public byte[] setRVRB() {
      byte[] reverb = {(byte) 'R', (byte) 'V', (byte) 'R', (byte) 'B'};
      return reverb;
   }
   
   /**
    * Sets the seek.
    * 
    * @return the byte[]
    */
   public byte[] setSEEK() {
      byte[] seek = {(byte) 'S', (byte) 'E', (byte) 'E', (byte) 'K'};
      return seek;
   }
   
   /**
    * Sets the sign.
    * 
    * @return the byte[]
    */
   public byte[] setSIGN() {
      byte[] signatureFrame = {(byte) 'S', (byte) 'I', (byte) 'G', (byte) 'N'};
      return signatureFrame;
   }
   
   /**
    * Sets the sylt.
    * 
    * @return the byte[]
    */
   public byte[] setSYLT() {
      byte[] synchronisedLyricText =
         {(byte) 'S', (byte) 'Y', (byte) 'L', (byte) 'T'};
      return synchronisedLyricText;
   }
   
   /**
    * Sets the sytc.
    * 
    * @return the byte[]
    */
   public byte[] setSYTC() {
      byte[] synchronisedTempoCodes =
         {(byte) 'S', (byte) 'Y', (byte) 'T', (byte) 'C'};
      return synchronisedTempoCodes;
   }
   
   /**
    * Sets the talb.
    * 
    * @return the byte[]
    */
   public byte[] setTALB() {
      byte[] albumMovieShowTitle =
         {(byte) 'T', (byte) 'A', (byte) 'L', (byte) 'B'};
      return albumMovieShowTitle;
   }
   
   /**
    * Sets the tbpm.
    * 
    * @return the byte[]
    */
   public byte[] setTBPM() {
      byte[] bpm = {(byte) 'T', (byte) 'B', (byte) 'P', (byte) 'M'};
      return bpm;
   }
   
   /**
    * Sets the tcom.
    * 
    * @return the byte[]
    */
   public byte[] setTCOM() {
      byte[] composer = {(byte) 'T', (byte) 'C', (byte) 'O', (byte) 'M'};
      return composer;
   }
   
   /**
    * Sets the tcon.
    * 
    * @return the byte[]
    */
   public byte[] setTCON() {
      byte[] contentType = {(byte) 'T', (byte) 'C', (byte) 'O', (byte) 'N'};
      return contentType;
   }
   
   /**
    * Sets the tcop.
    * 
    * @return the byte[]
    */
   public byte[] setTCOP() {
      byte[] copyrightMessage =
         {(byte) 'T', (byte) 'C', (byte) 'O', (byte) 'P'};
      return copyrightMessage;
   }
   
   /**
    * Sets the tden.
    * 
    * @return the byte[]
    */
   public byte[] setTDEN() {
      byte[] encodingTime = {(byte) 'T', (byte) 'D', (byte) 'E', (byte) 'N'};
      return encodingTime;
   }
   
   /**
    * Sets the tdly.
    * 
    * @return the byte[]
    */
   public byte[] setTDLY() {
      byte[] playlistDelay = {(byte) 'T', (byte) 'D', (byte) 'L', (byte) 'Y'};
      return playlistDelay;
   }
   
   /**
    * Sets the tdor.
    * 
    * @return the byte[]
    */
   public byte[] setTDOR() {
      byte[] originalReleaseTime =
         {(byte) 'T', (byte) 'D', (byte) 'O', (byte) 'R'};
      return originalReleaseTime;
   }
   
   /**
    * Sets the tdrc.
    * 
    * @return the byte[]
    */
   public byte[] setTDRC() {
      byte[] recordingTime = {(byte) 'T', (byte) 'D', (byte) 'R', (byte) 'C'};
      return recordingTime;
   }
   
   /**
    * Sets the tdtg.
    * 
    * @return the byte[]
    */
   public byte[] setTDTG() {
      byte[] taggingTime = {(byte) 'T', (byte) 'D', (byte) 'T', (byte) 'G'};
      return taggingTime;
   }
   
   /**
    * Sets the tenc.
    * 
    * @return the byte[]
    */
   public byte[] setTENC() {
      byte[] encodedBy = {(byte) 'T', (byte) 'E', (byte) 'N', (byte) 'C'};
      return encodedBy;
   }
   
   /**
    * Sets the text.
    * 
    * @return the byte[]
    */
   public byte[] setTEXT() {
      byte[] lyricistTextWriter =
         {(byte) 'T', (byte) 'E', (byte) 'X', (byte) 'T'};
      return lyricistTextWriter;
   }
   
   /**
    * Sets the tflt.
    * 
    * @return the byte[]
    */
   public byte[] setTFLT() {
      byte[] fileType = {(byte) 'T', (byte) 'F', (byte) 'L', (byte) 'T'};
      return fileType;
   }
   
   /**
    * Sets the tipl.
    * 
    * @return the byte[]
    */
   public byte[] setTIPL() {
      byte[] involvedPeopleList =
         {(byte) 'T', (byte) 'I', (byte) 'P', (byte) 'L'};
      return involvedPeopleList;
   }
   
   /**
    * Sets the ti t1.
    * 
    * @return the byte[]
    */
   public byte[] setTIT1() {
      byte[] contentGroupDescription =
         {(byte) 'T', (byte) 'I', (byte) 'T', (byte) '1'};
      return contentGroupDescription;
   }
   
   /**
    * Sets the ti t2.
    * 
    * @return the byte[]
    */
   public byte[] setTIT2() {
      byte[] titleSongnameContentDescription =
         {(byte) 'T', (byte) 'I', (byte) 'T', (byte) '2'};
      return titleSongnameContentDescription;
   }
   
   /**
    * Sets the ti t3.
    * 
    * @return the byte[]
    */
   public byte[] setTIT3() {
      byte[] subtitleDescriptionRefinement =
         {(byte) 'T', (byte) 'I', (byte) 'T', (byte) '3'};
      return subtitleDescriptionRefinement;
   }
   
   /**
    * Sets the tkey.
    * 
    * @return the byte[]
    */
   public byte[] setTKEY() {
      byte[] initialKey = {(byte) 'T', (byte) 'K', (byte) 'E', (byte) 'Y'};
      return initialKey;
   }
   
   /**
    * Sets the tlan.
    * 
    * @return the byte[]
    */
   public byte[] setTLAN() {
      byte[] languages = {(byte) 'T', (byte) 'L', (byte) 'A', (byte) 'N'};
      return languages;
   }
   
   /**
    * Sets the tmcl.
    * 
    * @return the byte[]
    */
   public byte[] setTMCL() {
      byte[] musicanCreditsList =
         {(byte) 'T', (byte) 'M', (byte) 'C', (byte) 'L'};
      return musicanCreditsList;
   }
   
   /**
    * Sets the tmed.
    * 
    * @return the byte[]
    */
   public byte[] setTMED() {
      byte[] mediaType = {(byte) 'T', (byte) 'M', (byte) 'E', (byte) 'D'};
      return mediaType;
   }
   
   /**
    * Sets the tmoo.
    * 
    * @return the byte[]
    */
   public byte[] setTMOO() {
      byte[] mood = {(byte) 'T', (byte) 'M', (byte) 'O', (byte) 'O'};
      return mood;
   }
   
   /**
    * Sets the toal.
    * 
    * @return the byte[]
    */
   public byte[] setTOAL() {
      byte[] originalAlbumMovieShowTitle =
         {(byte) 'T', (byte) 'O', (byte) 'A', (byte) 'L'};
      return originalAlbumMovieShowTitle;
   }
   
   /**
    * Sets the tofn.
    * 
    * @return the byte[]
    */
   public byte[] setTOFN() {
      byte[] originalFilename =
         {(byte) 'T', (byte) 'O', (byte) 'F', (byte) 'N'};
      return originalFilename;
   }
   
   /**
    * Sets the toly.
    * 
    * @return the byte[]
    */
   public byte[] setTOLY() {
      byte[] originalLyricistTextWriters =
         {(byte) 'T', (byte) 'O', (byte) 'L', (byte) 'Y'};
      return originalLyricistTextWriters;
   }
   
   /**
    * Sets the tope.
    * 
    * @return the byte[]
    */
   public byte[] setTOPE() {
      byte[] oringinalArtistsPerformers =
         {(byte) 'T', (byte) 'O', (byte) 'P', (byte) 'E'};
      return oringinalArtistsPerformers;
   }
   
   /**
    * Sets the town.
    * 
    * @return the byte[]
    */
   public byte[] setTOWN() {
      byte[] fileOwnerLicensee =
         {(byte) 'T', (byte) 'O', (byte) 'W', (byte) 'N'};
      return fileOwnerLicensee;
   }
   
   /**
    * Sets the tp e1.
    * 
    * @return the byte[]
    */
   public byte[] setTPE1() {
      byte[] leadPerformerSoloist =
         {(byte) 'T', (byte) 'P', (byte) 'E', (byte) '1'};
      return leadPerformerSoloist;
   }
   
   /**
    * Sets the tp e2.
    * 
    * @return the byte[]
    */
   public byte[] setTPE2() {
      byte[] bandOrchestraAccompaniment =
         {(byte) 'T', (byte) 'P', (byte) 'E', (byte) '2'};
      return bandOrchestraAccompaniment;
   }
   
   /**
    * Sets the tp e3.
    * 
    * @return the byte[]
    */
   public byte[] setTPE3() {
      byte[] conductorPerformerRefinement =
         {(byte) 'T', (byte) 'P', (byte) 'E', (byte) '3'};
      return conductorPerformerRefinement;
   }
   
   /**
    * Sets the tp e4.
    * 
    * @return the byte[]
    */
   public byte[] setTPE4() {
      byte[] interpetedRemixedModifiedBy =
         {(byte) 'T', (byte) 'P', (byte) 'E', (byte) '4'};
      return interpetedRemixedModifiedBy;
   }
   
   /**
    * Sets the tpos.
    * 
    * @return the byte[]
    */
   public byte[] setTPOS() {
      byte[] partOfSet = {(byte) 'T', (byte) 'P', (byte) 'O', (byte) 'S'};
      return partOfSet;
   }
   
   /**
    * Sets the tpro.
    * 
    * @return the byte[]
    */
   public byte[] setTPRO() {
      byte[] producedNotice = {(byte) 'T', (byte) 'P', (byte) 'R', (byte) 'O'};
      return producedNotice;
   }
   
   /**
    * Sets the tpub.
    * 
    * @return the byte[]
    */
   public byte[] setTPUB() {
      byte[] publisher = {(byte) 'T', (byte) 'P', (byte) 'U', (byte) 'B'};
      return publisher;
   }
   
   /**
    * Sets the trck.
    * 
    * @return the byte[]
    */
   public byte[] setTRCK() {
      byte[] trackNumberPositionInSet =
         {(byte) 'T', (byte) 'R', (byte) 'C', (byte) 'K'};
      return trackNumberPositionInSet;
   }
   
   /**
    * Sets the trsn.
    * 
    * @return the byte[]
    */
   public byte[] setTRSN() {
      byte[] internetRadionStationName =
         {(byte) 'T', (byte) 'R', (byte) 'S', (byte) 'N'};
      return internetRadionStationName;
   }
   
   /**
    * Sets the trso.
    * 
    * @return the byte[]
    */
   public byte[] setTRSO() {
      byte[] internetRadioStationName =
         {(byte) 'T', (byte) 'R', (byte) 'S', (byte) 'O'};
      return internetRadioStationName;
   }
   
   /**
    * Sets the tsoa.
    * 
    * @return the byte[]
    */
   public byte[] setTSOA() {
      byte[] albumSortOrder = {(byte) 'T', (byte) 'S', (byte) 'O', (byte) 'A'};
      return albumSortOrder;
   }
   
   /**
    * Sets the tsop.
    * 
    * @return the byte[]
    */
   public byte[] setTSOP() {
      byte[] partOfSet = {(byte) 'T', (byte) 'S', (byte) 'O', (byte) 'P'};
      return partOfSet;
   }
   
   /**
    * Sets the tsot.
    * 
    * @return the byte[]
    */
   public byte[] setTSOT() {
      byte[] titleSortOrder = {(byte) 'T', (byte) 'S', (byte) 'O', (byte) 'T'};
      return titleSortOrder;
   }
   
   /**
    * Sets the tsrc.
    * 
    * @return the byte[]
    */
   public byte[] setTSRC() {
      byte[] internationalStanardRecordingCode =
         {(byte) 'T', (byte) 'S', (byte) 'R', (byte) 'C'};
      return internationalStanardRecordingCode;
   }
   
   /**
    * Sets the tsse.
    * 
    * @return the byte[]
    */
   public byte[] setTSSE() {
      byte[] softwareHardwareSettingUsedForEncoding =
         {(byte) 'T', (byte) 'S', (byte) 'S', (byte) 'E'};
      return softwareHardwareSettingUsedForEncoding;
   }
   
   /**
    * Sets the tsst.
    * 
    * @return the byte[]
    */
   public byte[] setTSST() {
      byte[] setSubtitle = {(byte) 'T', (byte) 'S', (byte) 'S', (byte) 'T'};
      return setSubtitle;
   }
   
   /**
    * Sets the tyer.
    * 
    * @return the byte[]
    */
   public byte[] setTYER() {
      byte[] year = {(byte) 'T', (byte) 'Y', (byte) 'E', (byte) 'R'};
      return year;
   }
   
   /**
    * Sets the ufid.
    * 
    * @return the byte[]
    */
   public byte[] setUFID() {
      byte[] uniqueFileIdentifier =
         {(byte) 'U', (byte) 'F', (byte) 'I', (byte) 'D'};
      return uniqueFileIdentifier;
   }
   
   /**
    * Sets the user.
    * 
    * @return the byte[]
    */
   public byte[] setUSER() {
      byte[] termsOfUse = {(byte) 'U', (byte) 'S', (byte) 'E', (byte) 'R'};
      return termsOfUse;
   }
   
   /**
    * Sets the uslt.
    * 
    * @return the byte[]
    */
   public byte[] setUSLT() {
      byte[] unsynchronisedLyricTextTranscription =
         {(byte) 'U', (byte) 'S', (byte) 'L', (byte) 'T'};
      return unsynchronisedLyricTextTranscription;
   }
   
   /**
    * Sets the wcom.
    * 
    * @return the byte[]
    */
   public byte[] setWCOM() {
      byte[] commercialInformation =
         {(byte) 'W', (byte) 'C', (byte) 'O', (byte) 'M'};
      return commercialInformation;
   }
   
   /**
    * Sets the wcop.
    * 
    * @return the byte[]
    */
   public byte[] setWCOP() {
      byte[] copyrightLegalInformation =
         {(byte) 'W', (byte) 'C', (byte) 'O', (byte) 'P'};
      return copyrightLegalInformation;
   }
   
   /**
    * Sets the woaf.
    * 
    * @return the byte[]
    */
   public byte[] setWOAF() {
      byte[] officialAudioFileWebpage =
         {(byte) 'W', (byte) 'O', (byte) 'A', (byte) 'F'};
      return officialAudioFileWebpage;
   }
   
   /**
    * Sets the woar.
    * 
    * @return the byte[]
    */
   public byte[] setWOAR() {
      byte[] officialArtistPerformerWebpage =
         {(byte) 'W', (byte) 'O', (byte) 'A', (byte) 'R'};
      return officialArtistPerformerWebpage;
   }
   
   /**
    * Sets the woas.
    * 
    * @return the byte[]
    */
   public byte[] setWOAS() {
      byte[] officialAudioSourceWebpage =
         {(byte) 'W', (byte) 'O', (byte) 'A', (byte) 'S'};
      return officialAudioSourceWebpage;
   }
   
   /**
    * Sets the wors.
    * 
    * @return the byte[]
    */
   public byte[] setWORS() {
      byte[] officialInternetRadioStationHomepage =
         {(byte) 'W', (byte) 'O', (byte) 'R', (byte) 'S'};
      return officialInternetRadioStationHomepage;
   }
   
   /**
    * Sets the wpay.
    * 
    * @return the byte[]
    */
   public byte[] setWPAY() {
      byte[] payment = {(byte) 'W', (byte) 'P', (byte) 'A', (byte) 'Y'};
      return payment;
   }
   
   /**
    * Sets the wpub.
    * 
    * @return the byte[]
    */
   public byte[] setWPUB() {
      byte[] publishersOfficialWebpage =
         {(byte) 'W', (byte) 'P', (byte) 'U', (byte) 'B'};
      return publishersOfficialWebpage;
   }
}
