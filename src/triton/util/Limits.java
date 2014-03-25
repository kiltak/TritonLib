package triton.util;

public class Limits {
    public static void checkLimits (String varName, int varVal, int min, int max) {
        if ((varVal < min) || (max < varVal)) {
            throw new IllegalArgumentException(varName + " out of range - got " + varVal +
                    "(dec) 0x" + String.format("%02X", varVal) + ", valid range " + min + "-" + max + "(dec)");
        }
    }
}
