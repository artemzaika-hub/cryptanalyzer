public class StatisticsDecryptor {
    public static int findBestShift(String encrypted) {
        int[] freq = new int[26];
        for (char c : encrypted.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']++;
            }
        }
        int maxIndex = 0;
        for (int i = 1; i < 26; i++) {
            if (freq[i] > freq[maxIndex]) {
                maxIndex = i;
            }
        }
        char mostFreqLetter = (char) ('a' + maxIndex);
        int shift = (mostFreqLetter - 'e');
        if (shift < 0) shift += 26;
        return shift;
    }

    public static String decrypt(String encrypted) {
        int shift = findBestShift(encrypted);
        return CaesarCipher.decrypt(encrypted, shift);
    }
}
