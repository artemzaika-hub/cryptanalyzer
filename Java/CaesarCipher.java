public class CaesarCipher {
    public static String encrypt(String text, int shift) {
        shift = shift % 26;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                char enc = (char) ('a' + (c - 'a' + shift) % 26);
                result.append(enc);
            } else if (c >= 'A' && c <= 'Z') {
                char enc = (char) ('A' + (c - 'A' + shift) % 26);
                result.append(enc);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        int reverseShift = (26 - shift % 26) % 26;
        return encrypt(text, reverseShift);
    }
}