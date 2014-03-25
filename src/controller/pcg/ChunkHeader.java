package controller.pcg;

import java.nio.ByteBuffer;

import controller.pcg.bankChunk.*;

class ChunkHeader {
    /**
     * 
     * @param data
     * @throws BadDataException
     */
    public int unpack(byte data[], int offset) {
        assert data.length == BYTE_SIZE;
        
        if (data.length <= offset) {
            return 0;
        }
        
        // Chunck ID
        _chunkId = ""; // Make sure it's cleared out in case of reuse of object
        for (int i = 0; i < 4; ++i) {
            _chunkId += (char) data[offset++];
        }
        
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        _size = wrapped.getInt(offset);
        offset += 4;
        
        return offset;
    }
    
    /**
     * 
     * @return
     */
    public byte[] pack() {
        ByteBuffer data = ByteBuffer.allocate(BYTE_SIZE);
        
        int i;
        
        // chunk ID
        byte[] byteString = _chunkId.getBytes();
        for (i = 0; i < 4; i++) {
            data.putChar(i, (char) byteString[i]);
        }
        
        data.putInt(4, _size);
        
        return data.array();
    }
    
    /**
     * Get the body's class instantiation. If there is no body associated with
     * this header type, return null.
     * 
     * @return
     * @throws BadDataException
     */
    public ChunkBody getBodyInstantiation() throws BadDataException {
        if ("PCG1".equalsIgnoreCase(_chunkId)) {
            return null;
        }
        if ("PRG1".equalsIgnoreCase(_chunkId)) {
            return null;
        }
        if ("PBK1".equalsIgnoreCase(_chunkId)) {
            return new ProgramBankChunk();
        }
        if ("CMB1".equalsIgnoreCase(_chunkId)) {
            return null;
        }
        if ("CBK1".equalsIgnoreCase(_chunkId)) {
            return new CombinationBankChunk();
        }
        if ("DKT1".equalsIgnoreCase(_chunkId)) {
            return null;
        }
        if ("DBK1".equalsIgnoreCase(_chunkId)) {
            return new DrumkitBankChunk();
        }
        if ("ARP1".equalsIgnoreCase(_chunkId)) {
            return null;
        }
        if ("ABK1".equalsIgnoreCase(_chunkId)) {
            return new ArpeggioBankChunk();
        }
        if ("CSM1".equalsIgnoreCase(_chunkId)) {
            return new ChecksumChunk();
        }
        if ("DIV1".equalsIgnoreCase(_chunkId)) {
            return new DividedChunk();
        }
        if ("INI1".equalsIgnoreCase(_chunkId)) {
            return new IniChunk();
        }
        if ("GLB1".equalsIgnoreCase(_chunkId)) {
            return new GlobalChunk();
        }
        throw new BadDataException("Unkown chunk type - " + _chunkId + ".");
    }
    
    public String toString() {
        return "Chunk ID: " + _chunkId + "\n" + "Size:     " + _size + "\n";
    }
    
    public int getSize() {
        return _size;
    }
    
    public String getChunkId() {
        return _chunkId;
    }
    
    private String _chunkId; // [4byte]
    private int _size; // [4byte] (Programs/Combinations...Global)
    
    public static int BYTE_SIZE = 8;
}