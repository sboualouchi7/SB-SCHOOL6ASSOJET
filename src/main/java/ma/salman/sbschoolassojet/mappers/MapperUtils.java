package ma.salman.sbschoolassojet.mappers;



public class MapperUtils {
    public static byte[] stringToBytes(String value) {
        if (value == null) {
            return null;
        }
        return value.getBytes();
    }

    public static String bytesToString(byte[] value) {
        if (value == null) {
            return null;
        }
        return new String(value);
    }
}