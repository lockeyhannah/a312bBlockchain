package blockchain.utility;

import java.util.Arrays;

public class ByteArrayReader {

    private final byte[] array;
    private int currentIndex = 0;

    public ByteArrayReader(byte[] array) {
        this.array = array;
    }

    //Reads the next amountOfBytes bytes
    public byte[] readNext(int amountOfBytes, boolean trimLeadingZeroes) {
        byte[] newArray = (Arrays.copyOfRange(array, currentIndex, currentIndex + amountOfBytes));

        if (trimLeadingZeroes) newArray = ByteUtils.trimLeadingZeroes(newArray);
        currentIndex += amountOfBytes;

        return newArray;
    }
}
