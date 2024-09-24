import java.util.Random;

//backend
public class PasswordGenerator {
    public final static String UPPERCASE_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static String LOWERCASE_CHARACTER = "abcdefghijklmnopqrstuvwxyz";
    public final static String NUMBERS_CHARACTER = "0123456789";
    public final static String SYMBOLS_CHARACTER = "`~!@#$%^&*()_+-=[]{};',:\"\\./<>?|";

    //random to generate password at random
    private final Random random;

    //initialize random obj
    public PasswordGenerator(){random = new Random();}

    //it generate password
    public String generatePassWord(int length, boolean isUppercase, boolean isLowercase, boolean isNumbers, boolean isSymbols){
        StringBuilder passwordBuilder = new StringBuilder();

        //store valid characters
        StringBuilder validCharacters = new StringBuilder();
        if (isUppercase) validCharacters.append(UPPERCASE_CHARACTER);
        if (isLowercase) validCharacters.append(LOWERCASE_CHARACTER);
        if (isNumbers) validCharacters.append(NUMBERS_CHARACTER);
        if (isSymbols) validCharacters.append(SYMBOLS_CHARACTER);

        for (int i = 0; i < length; i++) {
            //generate random index
            int randomIndex = random.nextInt(validCharacters.length());

            //get char at random index
            char randomChar = validCharacters.charAt(randomIndex);

            //store random char to password
            passwordBuilder.append(randomChar);

        }
        return passwordBuilder.toString();
    }
}








