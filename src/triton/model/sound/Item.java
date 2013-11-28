package triton.model.sound;

public abstract class Item {
    public int unpack (byte[] data, int offset) {
        System.arraycopy(data, offset, this.data, 0, BYTE_SIZE);
        offset += BYTE_SIZE;
        
        return offset;
    }
    
    public int pack (byte[] data, int offset) {
        System.arraycopy(this.data, 0, data, offset, BYTE_SIZE);
        offset += BYTE_SIZE;
        
        return offset;
    }
    
    private byte[] data;
    
    public static final int BYTE_SIZE = 0;
}
