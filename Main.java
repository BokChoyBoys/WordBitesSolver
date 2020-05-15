import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** Main class for running solver.
 * @author Pierre Le
 */
public class Main {
    public static void main(String[] args) throws Exception {
        File words = new File("dictionary.json");
        BufferedReader br = new BufferedReader(new FileReader(words));
        HashSet<String> wordset = new HashSet<String>();
        String st;
        while ((st = br.readLine()) != null) {
            st = st.replaceAll("\\s*[\",]+", "");
            if (st.length() > 2 && st.length() < 10) {
                wordset.add(st);
            }
        }
        Scanner kb = new Scanner(System.in);
        System.out.println("What are your horizontal tiles?");
        String horz = kb.nextLine();
        System.out.println("What are your vertical tiles?");
        String vert = kb.nextLine();
        System.out.println("What are your single tiles?");
        String single = kb.nextLine();
        String[] horzlets = horz.split("\\s+");
        String[] vertlets = vert.split("\\s+");
        String[] singlelets = single.split("\\s+");
        List<Piece> pieces = new ArrayList<Piece>();
        for (String s: horzlets) {
            if (!s.equals("") && s.length() == 2) {
                pieces.add(new Piece(s.charAt(0), s.charAt(1), true));
            }
        }
        for (String s: vertlets) {
            if (!s.equals("") && s.length() == 2) {
                pieces.add(new Piece(s.charAt(0), s.charAt(1), false));
            }
        }
        for (String s: singlelets) {
            if (!s.equals("") && s.length() == 1) {
                pieces.add(new Piece(s.charAt(0)));
            }
        }
        List<String> goodwords = new ArrayList<String>();
        moveHorz(new HashSet<Piece>(), goodwords, "", wordset, pieces);
        moveVert(new HashSet<Piece>(), goodwords, "", wordset, pieces);
        Collections.sort(goodwords, new StringLengthComparator(""));
        System.out.println(goodwords);
    }

    /** Finds the longest words that can be made horizontally using full horizontal pieces with half vertical pieces.
     *  and single tiles. Max length word is 8 due to 9x8 board ingame.
     *
     *
     * @param piecesTraversed Set for checking if a piece is already used in the word
     * @param words List of words that can be made with a set of pieces
     * @param word Current word being made with pieces
     * @param wordset Set of all words in dictionary
     * @param pieces List of pieces given by input
     */
    private static void moveHorz(HashSet<Piece> piecesTraversed,
                                 List<String> words, String word, HashSet<String> wordset, List<Piece> pieces) {
        if (word.length() < 9 && wordset.contains(word) && !words.contains(word)) {
            words.add(word);
        }
        if (wordset.isEmpty() || word.length() > 8) {
            return;
        }
        for (Piece q: pieces) {
            if (!piecesTraversed.contains(q)) {
                HashSet<Piece> newpiecesTraversed = (HashSet<Piece>) piecesTraversed.clone();
                newpiecesTraversed.add(q);
                wordset = updatewordSet(wordset, word, word.length());
                if (q.getpieceType() == 2) {
                    moveHorz(newpiecesTraversed, words, word + q.getChar(0), wordset, pieces);
                    moveHorz(newpiecesTraversed, words, word + q.getChar(1), wordset, pieces);
                } else if (q.getpieceType() == 0) {
                    moveHorz(newpiecesTraversed, words, word + q.toString(), wordset, pieces);
                } else {
                    moveHorz(newpiecesTraversed, words, word + q.toString(), wordset, pieces);
                }
            }
        }
    }

    /** Moves vertically to find longest possible words. Max length is 9.
     * Same parameters as moveHorz.
     *
     * @param piecesTraversed Set for checking if a piece is already used in the word
     * @param words List of words that can be made with a set of pieces
     * @param word Current word being made with pieces
     * @param wordset Set of all words in dictionary
     * @param pieces List of pieces given by input
     */
    private static void moveVert(HashSet<Piece> piecesTraversed,
                                 List<String> words, String word, HashSet<String> wordset, List<Piece> pieces) {
        if (word.length() < 10 && wordset.contains(word) && !words.contains(word)) {
            words.add(word);
        }
        if (wordset.isEmpty() || word.length() > 9) {
            return;
        }
        for (Piece q: pieces) {
            if (!piecesTraversed.contains(q)) {
                HashSet<Piece> newpiecesTraversed = (HashSet<Piece>) piecesTraversed.clone();
                newpiecesTraversed.add(q);
                wordset = updatewordSet(wordset, word, word.length());
                if (q.getpieceType() == 1) {
                    moveVert(newpiecesTraversed, words, word + q.getChar(0), wordset, pieces);
                    moveVert(newpiecesTraversed, words, word + q.getChar(1), wordset, pieces);
                } else if (q.getpieceType() == 0) {
                    moveVert(newpiecesTraversed, words, word + q.toString(), wordset, pieces);
                } else {
                    moveVert(newpiecesTraversed, words, word + q.toString(), wordset, pieces);
                }
            }
        }
    }

    /** Helper function for reducing size of wordset to prune impossible words made with pieces.
     *
     * @param wordset Set of possible words
     * @param word Current word
     * @param len length of word
     * @return pruned HashSet with only possible words with word
     */
    private static HashSet<String> updatewordSet(HashSet<String> wordset, String word, int len) {
        HashSet<String> newset = new HashSet<String>();
        for (String s: wordset) {
            if (s.length() >= len && s.substring(0, len).equals(word)) {
                newset.add(s);
            }
        }
        return newset;
    }
}
