package org.example.chatclientjava41;

import java.util.regex.Pattern;

public class InputValidator {
    public class UserInputValidator {
        //эти комбинации написал дипсик так что...
        private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");// Разрешает: буквы, цифры, дефисы и подчёркивания Длина: 3-20 символов
        private static final Pattern EMAIL_PATTERN= Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        private static final Pattern PHONE_PATTERN= Pattern.compile("^\\+?[0-9\\-\\s()]{10,20}$");
        private static final Pattern FORBIDEN_WORDS_PATTERN= Pattern.compile("\\b(admin|root|system|password)\\b");
        private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");// Требует: минимум 1 строчную, 1 заглавную, 1 цифру, 1 спецсимвол Минимальная длина: 8 символов
        private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");// Разрешает: буквы любых языков, пробелы, точки, апострофы, дефисы
        private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("[';\"\\\\]|(--)|(/\\*[\\s\\S]*?\\*/)|(\\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE)?|INSERT( +INTO)?|MERGE|SELECT|UPDATE|UNION( +ALL)?)\\b)", Pattern.CASE_INSENSITIVE);//sql инъекция
        private static final Pattern XSS_PATTERN = Pattern.compile("<[^>]*>|&[^;]+;|\\b(on\\w+|javascript:|alert\\(|eval\\(|expression\\()", Pattern.CASE_INSENSITIVE);//xss атака
        private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9а-яА-ЯёЁ_\\-@. ]");//обнаружение спец символов т.е можно только цифры,буквы, _ - @ .

        public static boolean isEmailValid(String str) {
            return EMAIL_PATTERN.matcher(str).matches();
        }
        public static boolean isPasswordValid(String str) {
            return PASSWORD_PATTERN.matcher(str).matches();//как же задрали эти маски хз они как то криво работают
        }
        public static boolean isLoginValid(String str) {
            return LOGIN_PATTERN.matcher(str).matches();
        }

        //хочешь чтото еще или чтоб в вашем меню была по особому проверка-добавляй)
    }
}
