package cfwos.util;

public class Util {
    public static class FrequencyTable {
        private char[] characters;
        private int[] frequencies;
        private int size;

        public FrequencyTable(int capacity) {
            characters = new char[capacity];
            frequencies = new int[capacity];
            size = 0;
        }

        public void add(char character) {
            for (int i = 0; i < size; i++) {
                if (characters[i] == character) {
                    frequencies[i]++;
                    return;
                }
            }
            characters[size] = character;
            frequencies[size] = 1;
            size++;
        }

        public char[] getCharacters() {
            char[] result = new char[size];
            System.arraycopy(characters, 0, result, 0, size);
            return result;
        }

        public int[] getFrequencies() {
            int[] result = new int[size];
            System.arraycopy(frequencies, 0, result, 0, size);
            return result;
        }

        public int getSize() {
            return size;
        }

    }

}