package blockchain.ledger_file.convertion;

import blockchain.block.Data;
import blockchain.block.data_points.DataPoint;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class DataConverter extends Converter<Data> {

    private final short OBJECT_TYPE_UID = 3;
    ArrayList<Converter> converters = new ArrayList<>();


    private final int DATA_POINT_COUNT_BYTE_LEN = Integer.BYTES;
    private final int DATA_POINT_SIZE_BYTE_LEN = Short.BYTES;
    private final int OBJECT_TYPE_UID_BYTE_LEN = Short.BYTES;


    public DataConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
        converters.add(new FileOverViewConverter(CONVERTER_VERSION_UID));
        converters.add(new StorageContractConverter(CONVERTER_VERSION_UID));
    }

    @Override
    public Data instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        // Get amount of datapoints to read
        int dataPointCount = ByteUtils.toInt(byteReader.readNext(DATA_POINT_COUNT_BYTE_LEN, true));

        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for(int i = 0; i < dataPointCount; i++){
            short dataPointTypeID = ByteUtils.toShort(byteReader.readNext(OBJECT_TYPE_UID_BYTE_LEN, true));
            short dataPointByteLen = ByteUtils.toShort(byteReader.readNext(DATA_POINT_SIZE_BYTE_LEN, true));
            Converter c = getConverter(dataPointTypeID);

            if(c != null){
                dataPoints.add((DataPoint) c.instanceFromBytes(byteReader.readNext(dataPointByteLen, false)));
            }else{
                System.out.println("Could not read data point with ID : " + dataPointTypeID);
                byteReader.readNext(dataPointByteLen, true); // Skip this datapoint
            }
        }

        return new Data(dataPoints);
    }

    @Override
    public byte[] bytesFromInstance(Data data) {
        ArrayList<byte[]> bytes = new ArrayList<>();

        // Add an integer that tells the parser how many datapoints to read
        byte[] dataPointCountBytes = ByteUtils.toByteArray(data.getDatapointCount());
        bytes.add(ByteUtils.extendByteArray(dataPointCountBytes, DATA_POINT_COUNT_BYTE_LEN));

        // Iterate through every data point, transform it to bytes and add it to the byte array
        ArrayList<DataPoint> dataPoints = data.getDataPoints();
        for(DataPoint dp : dataPoints){

            // Find the converter that can transform the given DataPoint to bytes
            Converter c = getConverter(dp);

            if(c != null){
                // Add DataPoint ID to byte array
                bytes.add(ByteUtils.toByteArray(c.getOBJECT_TYPE_UID()));
                byte[] dpBytes = c.bytesFromInstance(dp);

                // Add byte length of data point to byte array
                short dpByteLen = (short) dpBytes.length;
                bytes.add(ByteUtils.toByteArray(dpByteLen));

                // Add data point
                bytes.add(dpBytes);
            }
            else{
                System.out.println("Could not find converter for datapoint");
            }// TODO: 29-04-2018 add else logic
        }

        return ByteUtils.combineByteArrays(bytes);
    }

    private Converter getConverter(short objectTypeID){
        for (Converter c : converters){
            if (c.getOBJECT_TYPE_UID() == objectTypeID) return c;
        }
        return null;
    }

    private Converter getConverter(Object o){
        for (Converter c : converters){
            if(c.canConvert(o)) return c;
        }
        return null;
    }

    @Override
    public short getOBJECT_TYPE_UID() {
        return OBJECT_TYPE_UID;
    }

    @Override
    public int getByteSize() {
        return -1;
    }
}
