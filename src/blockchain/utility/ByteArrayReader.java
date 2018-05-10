package blockchain.utility;

import java.io.InputStream;
import java.util.Arrays;

public class ByteArrayReader {

    byte[] array;
    int currentIndex = 0;

    public ByteArrayReader(byte[] array) {
        this.array = array;
    }

    public byte[] readNext(int amountOfBytes, boolean trimLeadingZeroes){
        byte[] newArray = (Arrays.copyOfRange(array, currentIndex, currentIndex + amountOfBytes));
        if(trimLeadingZeroes) newArray = ByteUtils.trimLeadingZeroes(newArray);
        currentIndex += amountOfBytes;

        // TODO: 29-04-2018 Add checks for length and readability
        return newArray;
    }


}
