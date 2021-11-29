package abstractTypes;

import exception.NStringDigitException;



public class NString {      //numberString

    private String nome;
    
    public NString (String str) throws NStringDigitException {

        for (int i = 0; i < str.length(); i++) {


            //verificacao pra checar se há uma letra na string
            if (Character.isLetter(str.charAt(i))) {
                throw new NStringDigitException("Dígito inválido encontrado no RA! Por favor, tente novamente");
            }
            
        }

        setNome(str);


        

        

        
    }

    
    public String setNome(String param) {
        return nome = param;
    }

    public String getNome() {
        return nome;
    }
    



}
