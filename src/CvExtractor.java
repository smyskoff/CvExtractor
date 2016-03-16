import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 
 */

/**
 * @author Sergii Myskov
 *
 */
public class CvExtractor {
    private static final String NAME_PATTERN  = "\\b[A-ZЄ-ЯҐ]{1}[a-z -'а-їґ]{1,35}\\b( +(van|der|von|de))* +\\b(Mc|d')?[A-ZЄ-ЯҐ]{1}[a-z -'а-їґ]{1,35}( ?- ?[A-ZЄ-ЯҐ]{1}[a-z -'а-їґ]{1,35})?\\b";
    private static final String PHONE_PATTERN = "\\+?\\b ?\\(?[0-9]+\\)? ?\\(?[0-9]+\\)? ?[0-9 -]+";
    private static final String EMAIL_PATTERN = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
    
    private static final Pattern namePattern = Pattern.compile(NAME_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    
    public static class UserInfo {
        private String name;
        private String phone;
        private String email;
        
        private UserInfo(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
        
        public String getName() {
            return name;
        }
        
        public String getPhones() {
            return phone;
        }
        
        public String getEmails() {
            return email;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("User name: ");
            sb.append(name);
            sb.append("\nPhone: ");
            sb.append(phone);
            sb.append("\nE-mails: ");
            sb.append(email);
            sb.append("\n");
            
            return sb.toString();
        }
    }
    
    private static Optional<String> match(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        
        if (matcher.find()) {
            return Optional.of(matcher.group(0));
        }
        
        return Optional.empty();
    }
    
    public static Optional<UserInfo> getUserInfo(String parsedCv) {
        // There's no smart features for the moment. Just try to match the name
        // and guess that the first name in the CV is the candidate name.
        // Either phones list or emails list or both can be empty, we don't care.
        UserInfo info = new UserInfo(match(parsedCv, namePattern).orElse(""),
                match(parsedCv, phonePattern).orElse(""),
                match(parsedCv, emailPattern).orElse(""));
        
        return Optional.of(info);
    }
    
    public static List<UserInfo> getUsersInfo(String[] files) throws IOException {
        return Arrays.stream(files)
                .map(DocumentFactory::getDocument)
                .map(IDocument::getText)
                .map(CvExtractor::getUserInfo)
                .filter(ui -> ui.isPresent())
                .map(ui -> ui.get())
                .collect(Collectors.toList());
    }
    
    private static void checkSuccessMatch(String str, Pattern pattern) {
        Optional<String> m = match(str, pattern);
        
        if (!m.isPresent() || m.get() != str) {
            System.out.println("Invalid match of string \"" + str + "\", " +
                    "result: \"" + m.orElse("") + "\"\n");
        }
    }
    
    private static void checkUnsuccessMatch(String str, Pattern pattern) {
        Optional<String> m = match(str, pattern);

        if (m.isPresent()) {
            System.out.println("Invalid unmatch of string \"" + str + "\", " +
                    "result: \"" + m.orElse("") + "\"\n");
        }
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        getUsersInfo(args).stream().forEach(System.out::println);
        
        String[] nameListSuccess = {
                "Jeanne d'Arc",
                "Ashly van der Pedrick",
                "Magdalena McBallenger",
                "Cristina von Hertzler",
                "Григорій Квітка-Основ'яненко",
                "Marty Blunt",
                "Soon Hiney",
                "Rickie Tatom",
                "Patti Wickman",
                "Hyon Wilcoxen",
                "Sharita Moshier",
                "Miranda Hymel",
                "Lorretta Delmont",
                "Elissa Spells",
                "Rena Oberle",
                "Jannie Etienne",
                "Silvia Haider",
                "Trinh Cantu",
                "Britta Landreneau",
                "Chloe Stroman",
        };
        
        String[] nameListUnsuccess = {};
        String[] phoneList = {
                "000-123-45-67",
                "+123 45 67 890 12",
                "+1 (234) 567-8912",
                "12 345 6789012",
        };
        
        System.out.println("Checking names match:\n");
        
        Arrays.asList(nameListSuccess).stream()
                .forEach(s -> checkSuccessMatch(s, namePattern));
        
        System.out.println("\nChecking names unmatch:\n");
        
        Arrays.asList(nameListUnsuccess).stream()
                .forEach(s -> checkUnsuccessMatch(s, namePattern));

        System.out.println("\nChecking phones unmatch:\n");
        
        Arrays.asList(phoneList).stream()
                .forEach(s -> checkSuccessMatch(s, phonePattern));
    }

}
