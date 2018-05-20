package blockchain.ledger_file.convertion;

import blockchain.block.data_points.CoinTransaction;
import blockchain.block.data_points.StorageContract;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class CoinTransactionConverter extends Converter<CoinTransaction> {

    private static final int GIVER_ID_BYTE_LEN = 8;
    private static final int RECIPIENT_ID_BYTE_LEN = 8;
    private static final int TRANSACTION_AMOUNT_BYTE_LEN = Double.BYTES;

    public CoinTransactionConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    @Override
    public CoinTransaction instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        String giverID = new String(byteReader.readNext(GIVER_ID_BYTE_LEN, true));
        String recipientID = new String(byteReader.readNext(RECIPIENT_ID_BYTE_LEN, true));
        double coins = ByteUtils.toDouble(byteReader.readNext(TRANSACTION_AMOUNT_BYTE_LEN, true));

        return new CoinTransaction(giverID, recipientID, coins);
    }

    //Converts a CoinTransaction object to byte array
    @Override
    public byte[] bytesFromInstance(CoinTransaction coinTransaction) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(coinTransaction.getGiverID().getBytes(), GIVER_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(coinTransaction.getRecipientID().getBytes(), RECIPIENT_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(coinTransaction.getCoins()), TRANSACTION_AMOUNT_BYTE_LEN));

        return ByteUtils.combineByteArrays(byteList);
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
