package controller.pcg.bankChunk;

import java.nio.ByteBuffer;

import controller.pcg.BadDataException;
import controller.pcg.ChunkBody;

public class IniChunk extends ChunkBody {
    public int unpack(byte data[], int offset) throws BadDataException {
        ByteBuffer wrapped = ByteBuffer.wrap (data);
        _numOfItems = wrapped.getInt(offset);
        offset += 4;
        
        for (int i = 0; i < _numOfItems; ++i) {
            offset = _i.unpack(data, offset); 
        }
        
        return offset;
    }
    
    private class item {
        public int unpack (byte[] data, int offset) {
            offset += BYTE_SIZE;
            
            return offset;
        }

        private static final int BYTE_SIZE = 28;
    }
    
    private int _numOfItems; // 4 bytes
    private item _i = new item();
}
