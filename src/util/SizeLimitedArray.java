package util;

/**
 * Originally I was going to hold the banks in this instead of an array.  The driver for that was
 * that the Java community doesn't think arrays are fashionable.  Then I realized that computers
 * don't really care about fashions and trends.  An array makes a lot more sense, so this class
 * isn't going to be used.
 * 
 * But it was cool to write, so I'm not deleting it...
 */

import java.util.ArrayList;

public class SizeLimitedArray<T> extends ArrayList<T> {
    private static final long serialVersionUID = -3324618452436308703L;  // Added to silence Eclipse

    private int limit;
    
    public SizeLimitedArray (int limit) {
        this.limit = limit;
    }
    
    @Override
    public boolean add (T e) {
        if (this.size() < limit) {
            return super.add(e);
        }
        return false;
    }
    
}
