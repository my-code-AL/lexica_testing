package com.serwylo.lexica;


import com.serwylo.lexica.db.GameMode;
import com.serwylo.lexica.game.Board;
import com.serwylo.lexica.game.CharProbGenerator;
import com.serwylo.lexica.game.FiveByFiveBoard;
import com.serwylo.lexica.game.Game;
import com.serwylo.lexica.game.SixBySixBoard;
import com.serwylo.lexica.lang.English;
import com.serwylo.lexica.lang.EnglishUS;
import com.serwylo.lexica.lang.Language;
import com.serwylo.lexica.trie.util.LetterFrequency;

import net.healeys.trie.Solution;
import net.healeys.trie.StringTrie;
import net.healeys.trie.WordFilter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;

import androidx.test.core.app.ApplicationProvider;

public class Test_Extenstion {
    private static Language language;
    private static LetterFrequency letterFreqs;

    @Test
    public void sixBysix(){
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        String actual = board.toString();
        String expected = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,A,B,C,D,E,F,G,H,I,J,K";
        assertEquals(expected,actual);
    }
    @Test
    public void testRotate(){
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        board.rotate();
        String actual = board.toString();
        String expected = "F,Y,S,M,G,A,G,A,T,N,H,B,H,B,U,O,I,C,I,C,V,P,J,D,J,D,W,Q,K,E,K,E,X,R,L,F";
        assertEquals(expected,actual);
    }

    @BeforeClass
    public static void Setup(){
        language = new EnglishUS();
        letterFreqs = new LetterFrequency(language);
    }

    @Test
    public void LetterFrequencyTest(){
//        Canvas canvas = new Canvas();

        letterFreqs.addWord("word");
        Set value = letterFreqs.getLetters();


        assertTrue(value.contains("w"));
        assertTrue(value.contains("o"));
        assertTrue(value.contains("r"));
        assertTrue(value.contains("d"));
        assertFalse(value.contains("z"));

    }
    @Test
    public void LetterFrequencyTest2(){

        HashMap<String, Integer> testMap = letterFreqs.getLetterCountsForWord("apple");
        assertTrue(testMap.get("p")==2);
        assertTrue(testMap.get("a")==1);


    }
    @Test
    public void LetterFrequencyTest3(){
        boolean val = letterFreqs.shouldInclude("word",1);
        assertEquals(true,val);
    }

    @Test
    public void testBoardElementAt() {
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        board.rotate();
        String actual = board.toString();
        String expected = "F,Y,S,M,G,A,G,A,T,N,H,B,H,B,U,O,I,C,I,C,V,P,J,D,J,D,W,Q,K,E,K,E,X,R,L,F";
        assertEquals(expected,actual);
        assertEquals("S", board.elementAt(2));
        assertEquals("S", board.elementAt(2, 0));
        assertEquals("S", board.valueAt(2));
    }

    @Test
    public void testRotatedPosition() {
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        String actual = board.toString();
        String expected = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,A,B,C,D,E,F,G,H,I,J,K";
        assertEquals(expected,actual);
        assertEquals(2, board.getRotatedPosition(2));
    }

    @Test
    public void testGetLetters() {
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        String expected = "F,Y,S,M,G,A,G,A,T,N,H,B,H,B,U,O,I,C,I,C,V,P,J,D,J,D,W,Q,K,E,K,E,X,R,L,F";
        Object[] exp = Arrays.stream(expected.split(",")).sorted().toArray();
        Object[] actual = Arrays.stream(board.getLetters()).sorted().toArray();
        assertArrayEquals(exp, actual);
    }

    @Test
    public void testCanRevisit() {
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        board.rotate();
        String actual = board.toString();
        String expected = "F,Y,S,M,G,A,G,A,T,N,H,B,H,B,U,O,I,C,I,C,V,P,J,D,J,D,W,Q,K,E,K,E,X,R,L,F";
        assertEquals(expected,actual);
        assertFalse(board.canRevisit());
    }

    @Test
    public void testCanTransition() {
        Board board = new SixBySixBoard(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",});
        board.rotate();
        String actual = board.toString();
        String expected = "F,Y,S,M,G,A,G,A,T,N,H,B,H,B,U,O,I,C,I,C,V,P,J,D,J,D,W,Q,K,E,K,E,X,R,L,F";
        assertEquals(expected,actual);
        assertFalse(board.canTransition(7,6,6,6));
        assertFalse(board.canTransition(6,7,6,6));
        assertFalse(board.canTransition(6,6,7,6));
        assertFalse(board.canTransition(6,6,6,7));
        assertTrue(board.canTransition(3,3,2,2));
        assertTrue(board.canTransition(3,3,3,2));
        assertTrue(board.canTransition(3,3,2,3));
        assertFalse(board.canTransition(6,6,3,3));
    }

    @Test
    public void testCharPobGenerator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 26; j++) {
                sb.append(1);
                sb.append(" ");
            }
            sb.append('\n');
        }

        InputStream istream = new ByteArrayInputStream(sb.toString().getBytes());
        Language language = new EnglishUS();
        CharProbGenerator probGenerator = new CharProbGenerator(istream, language);
        assertNotNull(probGenerator);
        assertTrue(new CharProbGenerator(probGenerator).getAlphabet().size() > 0);
        assertEquals(25, probGenerator.generateFiveByFiveBoard().getSize());
        assertEquals(5, probGenerator.generateFiveByFiveBoard().getWidth());

        istream = new ByteArrayInputStream(sb.toString().getBytes());
        language = new EnglishUS();
        probGenerator = new CharProbGenerator(istream, language);
        assertEquals(16, probGenerator.generateFourByFourBoard().getSize());
        assertEquals(4, probGenerator.generateFourByFourBoard().getWidth());
    }
}
