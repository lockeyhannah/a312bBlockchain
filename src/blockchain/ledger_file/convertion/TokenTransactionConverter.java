package blockchain.ledger_file.convertion;

import blockchain.block.data_points.TokenTransaction;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class TokenTransactionConverter extends Converter<TokenTransaction> {

    public static final int USER_ID_BYTE_LEN = 16;
    private static final int GIVER_ID_BYTE_LEN = USER_ID_BYTE_LEN;
    private static final int RECIPIENT_ID_BYTE_LEN = USER_ID_BYTE_LEN;
    private static final int TRANSACTION_AMOUNT_BYTE_LEN = Double.BYTES;

    public TokenTransactionConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    @Override // Converts a TokenTransaction object to byte array
    public byte[] bytesFromInstance(TokenTransaction tokenTransaction) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(tokenTransaction.getGiverID().getBytes(), GIVER_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(tokenTransaction.getRecipientID().getBytes(), RECIPIENT_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(tokenTransaction.getTokens()), TRANSACTION_AMOUNT_BYTE_LEN));

        return ByteUtils.combineByteArrays(byteList);
    }

    @Override // Converts a TokenTransaction object to byte array
    public TokenTransaction instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        // Read bytes in the same order they were written
        String giverID = new String(byteReader.readNext(GIVER_ID_BYTE_LEN, true));
        String recipientID = new String(byteReader.readNext(RECIPIENT_ID_BYTE_LEN, true));
        double coins = ByteUtils.toDouble(byteReader.readNext(TRANSACTION_AMOUNT_BYTE_LEN, true));

        return new TokenTransaction(giverID, recipientID, coins);
    }

    @Override
    protected short getOBJECT_TYPE_UID() {
        return 7;
    }

    @Override
    public int getByteSize() {
        return GIVER_ID_BYTE_LEN + RECIPIENT_ID_BYTE_LEN + TRANSACTION_AMOUNT_BYTE_LEN;
    }
}
