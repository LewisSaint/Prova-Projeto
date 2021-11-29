package abstractTypes;

import exception.InvalidPhoneNumberException;

public class CelString {

    

    public CelString(String paramString) throws InvalidPhoneNumberException {

        if (paramString.length() != 11) {throw new InvalidPhoneNumberException("Tamnaho inválido! Por favor, tente novamente");}

        for (int i = 0; i < paramString.length();i++) {
            if ((paramString.charAt(i) == '+') || !(Character.isDigit(paramString.charAt(i)))) {
                throw new InvalidPhoneNumberException("Formato ou caractere inválido! Por favor, tente novamente");
            }
        }




    }




}