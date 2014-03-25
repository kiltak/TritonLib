/**
 * Build the commands to send to the hardware.  This does not handle the requests, but rather the data
 * (programs/combis/whatever) that is being loaded into the hardware.
 */

package controller.utils.midi;

public class MidiBuilder {
//
//    /**
//     * Convert from the internal memory bytes to midi bytes. MIDI bytes don't
//     * use the MSB.
//     * 
//     * MIDI Internal 0ABCDEFG Aaaaaaaa 0aaaaaaa Bbbbbbbb 0bbbbbbb Cccccccc
//     * 0ccccccc Dddddddd
//     * 
//     * @param internalData
//     *            The data in internal format (uses MSB in the byte)
//     * @return The data in midi format (doesn't use MSB in byte)
//     */
//    private byte[] internalToMidi (byte[] internalData) {
//        double size = internalData.length * 8.0 / 7.0; // every 7 bytes will be expanded to 8 bytes
//        byte[] tmpData = new byte[(int)Math.ceil(size)];
//
//        int j = 0;
//        int byteNo = 0;
//        byte header = 0x00;
//        byte msb;
//        byte[] sevenBytes = new byte[7]; // temp storage for the seven bytes being worked on
//        for (int i = 0; i < internalData.length; ++i) {
//            byteNo = i % 7;
//            if (byteNo == 0) { // first byte
//                header = 0x00;
//            }
//            
//            msb = (byte)(internalData[i] & 0x80);
//            header |= (byte)(msb >> byteNo);
//            sevenBytes[byteNo] = (byte)(internalData[i] & 0x7F);
//            
//            if (byteNo == 6) { // last byte
//                tmpData[j++] = header;
//                for (byte b : sevenBytes) {
//                    tmpData[j++] = b;
//                }
//            }
//        }
//        
//        if (byteNo != 6) { // need to write out these bytes
//            tmpData[j++] = header;
//            for (int i = 0; i <= byteNo; ++i) {
//                tmpData[j++] = sevenBytes[i];
//            }
//        }
//        
//        byte[] midiData = new byte[j];
//        System.arraycopy(tmpData, 0, midiData, 0, j);
//        
//        return midiData;
//    }
}
