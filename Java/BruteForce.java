import java.util.*;

public class BruteForce {
    private static final String[] COMMON_WORDS = {
            "и", "в", "на", "с", "по", "к", "у", "за", "из", "от",
            "а", "но", "что", "как", "был", "его", "для"
    };

    private static int countRussianWords(String text) {
        String[] words = text.toLowerCase().split("[^а-яё]+");
        int cnt = 0;
        for (String w : words) {
            if (w.isEmpty()) continue;
            for (String common : COMMON_WORDS) {
                if (w.equals(common)) {
                    cnt++;
                    break;
                }
            }
        }
        return cnt;
    }

    public static int findBestShift(String encrypted) {
        int bestShift = 0;
        int maxWords = 0;
        for (int shift = 0; shift < 26; shift++) {
            String decrypted = CaesarCipher.decrypt(encrypted, shift);
            int count = countRussianWords(decrypted);
            if (count > maxWords) {
                maxWords = count;
                bestShift = shift;
            }
        }
        return bestShift;
    }

    public static String decryptAuto(String encrypted) {
        int shift = findBestShift(encrypted);
        return CaesarCipher.decrypt(encrypted, shift);
    }

    public static Map<Integer, String> getAllVariants(String encrypted) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            map.put(i, CaesarCipher.decrypt(encrypted, i));
        }
        return map;
    }
}
