package block;

public class Header {

    private byte[] target; // Target value that hash values must be under
    private byte[] nonce; // TODO: 18-04-2018 : Disse skal nok ændres til BIG_NUM?? nonces / target kan blive meget store når difficulty er høj

    private byte[] hash;
    private byte[] prevHash;
    private long timeStamp;



}
