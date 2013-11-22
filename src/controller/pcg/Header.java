package controller.pcg;

import java.io.IOException;
import java.io.RandomAccessFile;

class Header {
    public Header () {
        _korgId = "";
        _padding = new byte[8];
    }
    
    /**
     * Read the data from a byte array.
     * 
     * @param data  The raw data.
     * @throws BadDataException 
     */
    public int unpack (byte[] data, int offset) throws BadDataException {
        assert data.length == HEADER_BYTE_SIZE;
        
        int j;
        
        // KORG ID
        _korgId = "";
        for (j = 0; j < 4; ++j) {
            _korgId += (char)data[offset++];
        }
        
        if (!_korgId.equals("KORG")) {
            throw new BadDataException("KORG id is wrong.  Got " + _korgId);
        }
        
        _productId = data[offset++];
        
        if (_productId != 0x50) {
            throw new BadDataException("Product ID is wrong.  Got " + String.format("%02X", _productId));
        }
        
        _fileType = data[offset++];
        _majorVersion = data[offset++];
        _minorVersion = data[offset++];
        for (j = 0; j < _padding.length; ++j) {
            _padding[j] = data[offset++];
        }
        
        return offset;
    }
    
    /**
     * Pack the data into a byte array.
     * 
     * @param data
     */
    private byte[] pack () {
        byte retArr[] = new byte[HEADER_BYTE_SIZE];
        
        System.arraycopy("KORG".getBytes(), 0, retArr, 0, 4);

        int i = 4;
        retArr[i++] = 0x50;
        retArr[i++] = 0x00;
        retArr[i++] = 0x00;
        retArr[i++] = 0x01;
        for (int j = 0; j < 8; ++j) {
            retArr[i++] = 0x00;
        }
        
        return retArr;
    }
    
    /**
     * Write this to the given file.
     * 
     * @param file
     * @throws IOException
     */
    public void write (RandomAccessFile file) throws IOException {
        byte[] data = pack();
        file.write(data);
    }
    
    /**
     * Decode the file type from a number to something meaningful.
     * 
     * @return A string representation of the file type.
     */
    private String filetypeToString () {
        switch (_fileType) {
            case 0x00:
                return "PCG";
            case 0x01:
                return "SNG";
            case 0x02:
                return "EXL";
            default:
                return "???";
        }
    }
    
    public String toString () {
        String retString = "KORG ID       " + _korgId + "\n"
                         + "Product Type  " + String.format("%02X ", _productId) + "\n"
                         + "File Type     " + filetypeToString() + "\n"
                         + "Major Version " + String.format("%02X ", _majorVersion) + "\n"
                         + "Minor Version " + String.format("%02X ", _minorVersion) + "\n"
                         + "Padding       ";
        for (int i = 0; i < _padding.length; ++i) {
            retString += String.format("%02X ", _padding[i]);
        }
        
        return retString + "\n";
    }
    
    private String _korgId;       //  'KORG'  [4byte]
    private byte   _productId;    //  0x50    [1byte]
    private byte   _fileType;     //  0x00    [1byte] 0x00 : PCG
                                  //                  0x01 : SNG
                                  //                  0x02 : EXL
    private byte   _majorVersion; //  0x00    [1byte]
    private byte   _minorVersion; //  0x01    [1byte]
    private byte   _padding[];    //  0x00    [8byte]
    
    public static int HEADER_BYTE_SIZE = 16;
}