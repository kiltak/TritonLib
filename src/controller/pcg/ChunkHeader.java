package controller.pcg;

import java.nio.ByteBuffer;

import controller.pcg.bankChunk.*;

class ChunkHeader {
    /**
     * 
     * @param data
     * @throws BadDataException 
     */
    public int unpack (byte data[], int offset) {
        assert data.length == BYTE_SIZE;
        
        if (data.length <= offset) {
            return 0;
        }
        
        // Chunck ID
        _chunkId = "";  // Make sure it's cleared out in case of reuse of object
        for (int i = 0; i < 4; ++i) {
            _chunkId += (char)data[offset++];
        }
        
        ByteBuffer wrapped = ByteBuffer.wrap (data);
        _size = wrapped.getInt(offset);
        offset += 4;
        
        return offset;
    }

    /**
     * 
     * @return
     */
    public byte[] pack () {
        ByteBuffer data = ByteBuffer.allocate(BYTE_SIZE);

        int i;
        
        // chunk ID
        byte[] byteString = _chunkId.getBytes();
        for (i = 0; i < 4; i++) {
            data.putChar(i, (char)byteString[i]);
        }
        
        data.putInt(4, _size);
        
        return data.array();
    }
    
    /**
     * Get the body's class instantiation.  If there is no body associated with this header type,
     * return null.
     * 
     * @return
     * @throws BadDataException
     */
    public ChunkBody getBodyInstantiation () throws BadDataException {
        switch (_chunkId) {
            case "PCG1":
                return null;
            case "PRG1":
                return null;
            case "PBK1":
                return new ProgramBankChunk();
            case "CMB1":
                return null;
            case "CBK1":
                return new CombinationBankChunk();
            case "DKT1":
                return null;
            case "DBK1":
                return new DrumkitBankChunk();
            case "ARP1":
                return null;
            case "ABK1":
                return new ArpeggioBankChunk();
            case "CSM1":
                return new ChecksumChunk();
            case "DIV1":
                return new DividedChunk();
            case "INI1":
                return new IniChunk();
            case "GLB1":
                return new GlobalChunk();
        }
        throw new BadDataException ("Unkown chunk type - " + _chunkId + ".");
    }
    
    public String toString () {
        return "Chunk ID: " + _chunkId + "\n"
             + "Size:     " + _size + "\n";
    }
    
    public int getSize () {
        return _size;
    }
    
    public String getChunkId () {
        return _chunkId;
    }
    
    private String _chunkId;  // [4byte]
    private int    _size;     // [4byte] (Programs/Combinations...Global)
    
    public static int BYTE_SIZE = 8;
}