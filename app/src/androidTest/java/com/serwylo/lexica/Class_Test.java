package com.serwylo.lexica;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import androidx.test.core.app.ApplicationProvider;
import android.content.Context;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.platform.app.InstrumentationRegistry;
import com.serwylo.lexica.db.GameMode;
import com.serwylo.lexica.game.Board;
import com.serwylo.lexica.game.Game;
import com.serwylo.lexica.lang.EnglishUS;
import com.serwylo.lexica.lang.Language;

import org.junit.rules.ExpectedException;

import org.junit.*;

//GAME MODES ARE AS FOLLOWS
//enum class Type {
//    SPRINT,
//    MARATHON,
//    BEGINNER,
//    LETTER_POINTS,
//    CUSTOM,
//    LEGACY,
//}
public class Class_Test {


    private static GameMode mode;
    private static Context context;
    private static SharedPreferences prefs;
    private static Game game;

    private static Language language;
    private static String[] boardLetters;

    @BeforeClass
    public static void setup(){
        context = ApplicationProvider.getApplicationContext();
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mode = new GameMode(12,
                GameMode.Type.SPRINT,
                null,
                16,
                180,
                3,
                GameMode.SCORE_LETTERS,
                "hint_colour");
        language = new EnglishUS(); // Example language
        boardLetters = null;

        game = new Game(context, mode, language, boardLetters);
    }


    /**
     * To test validity of arguments passed and that they are present within the game
     * ~~Take note of how the game object takes in a null board and maintains a non-null board object
     * */
    @Test
    public void testGameInitialization_withValidInputs() {

        assertNotNull(game.getBoard());
        assertNotNull(prefs);
        assertNotNull(game);
        assertNotNull(game.getLanguage());

    }

    @Test
    public void testBoardInput(){

        String currentboard = game.getBoard().toString();
        System.out.println(currentboard);
        String[] boardLetterz = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "Z"};
        Game game2 = new Game(context, mode, language, boardLetterz);

        String expected = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,Z";
        String actual = game2.getBoard().toString();

        assertNotNull(game2.getBoard());
        assertEquals(expected,actual);

    }

    @Test
    public void testBoardInputUnicode() {
        String currentboard = game.getBoard().toString();
        System.out.println(currentboard);
        String[] boardLetterz = {"À", "Á", "è", "ņ", "Ǯ", "Ṍ", "Ṹ", "ỹ", "ʎ", "ή", "ϕ", "Є", "Ӊ", "Ӵ", "ڡ", "㆟"};
        Game game2 = new Game(context, mode, language, boardLetterz);

        String expected = "À,Á,è,ņ,Ǯ,Ṍ,Ṹ,ỹ,ʎ,ή,ϕ,Є,Ӊ,Ӵ,ڡ,㆟";
        String actual = game2.getBoard().toString();

        assertNotNull(game2.getBoard());
        assertEquals(expected,actual);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * This gives strange output of ArrayIndexOutOfBounds error
     * this is due to the fact that index 15 is being accessed on an array
     * that only has 14 indexes. The game does not verify whether the input of a string
     * array is actually the correct size when generating a game board. It determines
     * the size of the game based upon the gamemode parameter which can lead to errors such
     * as this if the board is not carefully instantiated
     */
    @Test
    public void testGameCreationWithShortBoard() {
        assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
            String[] boardLetterz = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
            Game game3 = new Game(context, mode, language, boardLetterz); // Replace with actual parameters
        });
    }

    /**
     * This gives output of NullPointerException Error rather than what one might expect to be
     * an IllegalArgumentException. This is due to the fact that the class that generated the board
     * based upon the input string array of letters does not ever expect to encounter a board of
     * less or more than 16 letters. Therefore when the board is instantiated, only the first 16/17
     * spots are filled. This leads to a null space when the input string array is transferred over
     * to a board object.
     */
    @Test
    public void testGameCreationWithLongBoard() {
        assertThrows(IllegalStateException.class, () -> {
            GameMode mode = new GameMode(12,
                    GameMode.Type.SPRINT,
                    null,
                    17,
                    180,
                    3,
                    GameMode.SCORE_LETTERS,
                    "hint_colour");
            String[] boardLetterz = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O","P","Q"};
            Game game3 = new Game(context, mode, language, boardLetterz);
        });
    }

    /**
     * Got a NullPointerException instead of an illegal argument exception
     * based upon input utilized. There are obviously no checks or very few checks
     * within the game class to determine if inputs to the game class are valid.
     */
    @Test
    public void testGameModeInput() {
        assertThrows(NullPointerException.class, () -> {
            Game game_null = new Game(context, null, language, boardLetters);
        });
    }

    @Test
    @Ignore("case not covered in game")
    public void testInvalidAsciiInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            String[] boardLetterz = {"{", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"};
            Game game_null = new Game(context, mode, language, boardLetters);
        });
    }

    @Test
    @Ignore("case not covered in game")
    public void testInvalidUnicodeInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            String[] boardLetterz = {"㈀", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"};
            Game game_null = new Game(context, mode, language, boardLetters);
        });
    }
}
