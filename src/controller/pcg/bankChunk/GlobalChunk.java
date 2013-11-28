package controller.pcg.bankChunk;

import controller.pcg.BadDataException;
import controller.pcg.ChunkBody;
import triton.model.global.Global;

public class GlobalChunk extends ChunkBody {
    public int unpack(byte data[], int offset) throws BadDataException {
        offset = _global.unpack(data, offset);
        
        return offset;
    }
    
    public int pack(byte data[], int offset) {
        offset = _global.pack(data, offset);
        
        return offset;
    }
    
    public Global getGlobal() {
        return _global;
    }
    
    public String toString() {
        return _global.toString();
    }
    
    private Global _global = new Global();
}
