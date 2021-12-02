package abstractTypes;

import exception.InvalidBirthDateException;

public class BirthDate {

    private static String firstString;

    public BirthDate(String paramString) throws InvalidBirthDateException {


        if ((paramString.charAt(2) != '/' || (paramString.charAt(5) != '/'))) {throw new InvalidBirthDateException("Formato inválido! Por favor, tente novamente");}

        for (int i = 0; i < paramString.length();i++) {
            if ((i == 2) || (i == 5)) {continue;}

            else if (!(Character.isDigit(paramString.charAt(i)))) {
                throw new InvalidBirthDateException("Caractere inválido encontrado! Por favor, tente novamente");
            }
            



        }

        firstString = paramString;

    }

    public String toString() {
        return firstString;
    }

    public String JSONformat() {

        StringBuffer jsonString = new StringBuffer(firstString);

        for (int i = 0; i < firstString.length(); i++) {
            if(firstString.charAt(i) == '/') {
                jsonString.setCharAt(i, '-');
            }
        }

        return jsonString.toString();
    }
    
}
