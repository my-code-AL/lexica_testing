package com.serwylo.lexica;


import com.serwylo.lexica.game.Board;
import com.serwylo.lexica.game.FiveByFiveBoard;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.graphics.Canvas;

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
}
