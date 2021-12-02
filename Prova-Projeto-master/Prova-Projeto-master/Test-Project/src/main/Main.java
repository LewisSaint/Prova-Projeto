package main;

import abstractTypes.*;
import exception.InvalidBirthDateException;
import exception.InvalidPhoneNumberException;
import exception.NStringDigitException;
import mapa.HashTableMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import pilha.ArrayStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;




public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Bem-vindo ao programa!!");
        
        userInterface();
    }

    public static void userInterface() throws InterruptedException {
        int option = 1;

        Scanner scan = new Scanner(System.in); // para ler as opcoes

        while ((option >= 1 || option <= 2)) {
            System.out.println("Digite '1' para interface de consulta de dados.");

            System.out.println("Digite '2' para adição de conteúdo à base.");

            option = scan.nextInt();

            if ((option < 1 || option > 2)) {
                System.out.println("Ops, não entendi...");
                continue;
            }

            break;
        }

        if (option == 1)
            consulta();

        else if (option == 2)
            changingDB();
    }






//----------------------------------------------------------------------------------------PARTE DE CONSULTA----------------------------------------------------------------------------------------







    public static void consulta() throws InterruptedException {

        
        int option = 1;

        File file = new File("data.json");
        if (file.length() == 0) {
            System.out.println("Ops, não há nenhum dado em minha base. Por favor, volte ao menu principal, selecione a opção de mudança e crie uma pilha nova.");
            System.out.println("Redirecionando para o menu principal...");
            TimeUnit.SECONDS.sleep(2);
            userInterface();
        }

        Scanner scanConsulta = new Scanner(System.in);
        System.out.println("---------------------CONSULTA DE BANCO---------------------");
        System.out.println("Você Entrou no modo de consulta a base de dados.");
        System.out.println("Se deseja consultar pela chave padrão de RA, digite '1'. Caso contrário, digite '2' para selecionar uma chave específica desejada");
        System.out.println("Se deseja voltar ao menu principal, digite 3");

        option = scanConsulta.nextInt();


        if (option == 1) {
            TimeUnit.SECONDS.sleep(2);
            consultaRA();
        }

        else if(option == 2) {
            TimeUnit.SECONDS.sleep(2);
            consultaChave();
        }

        else if(option == 3) {
            System.out.println("Retornando ao menu principal...");
            TimeUnit.SECONDS.sleep(2);
            userInterface();
        }

        else if (option < 1 || option > 3) {
            System.out.println("Opção inválida. Por favor, tente novamente!");
            TimeUnit.SECONDS.sleep(2);
            consulta();
        }

    }


    public static void consultaRA() throws InterruptedException {
       
        Scanner scanConsultaRA = new Scanner(System.in);
        System.out.println("Digite o RA do aluno desejado: ");
        String ra = scanConsultaRA.nextLine();
        
        

        
        HashTableMap<String, String> mapa = new HashTableMap<String, String>();
    
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));

            for (int i = 0; i < a.size();i++) {           
                
                JSONObject getKey = (JSONObject) a.get(i);
                
                

                
                if (ra.equals(getKey.get("RA"))) {



                    String keyString = (String) getKey.get("RA");
                    mapa.put(keyString, keyString);
                    String nomeString = (String) getKey.get("Nome");
                    mapa.put(nomeString, "Nome");
                    String enderecoString = (String) getKey.get("Endereço");
                    mapa.put(enderecoString, "Endereço");
                    String dataString = (String) getKey.get("Data de Nascimento");
                    mapa.put(dataString, "Data de Nascimento");
                    String celphoneString = (String) getKey.get("Celular");
                    mapa.put(celphoneString, "Celular");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        try {
            if (mapa.get(ra) == null) {throw new NullPointerException();}
        } catch (NullPointerException e) {
            System.err.println("Não encontrei esse RA em meu banco. Por favor, tente novamente!");
            TimeUnit.SECONDS.sleep(1);
            consultaRA();
        }



       
        System.out.println("Tudo que tenho do aluno com RA " + ra + " é:");
        System.out.println(mapa.entrySet());
        System.out.println("Redirecionando ao menu de consulta...");
        TimeUnit.SECONDS.sleep(1);
        consulta();
       
    }
    


    public static void consultaChave() throws InterruptedException {
  
        
        int option = 1;

        Scanner scanRA = new Scanner(System.in);
        System.out.println("Você entrou no modo de consulta de chave específica.");
        System.out.println("Digite o número de RA o qual deseja buscar: ");
        
        String ra = scanRA.nextLine();
        
        HashTableMap<String, String> mapa = new HashTableMap<String, String>();
       
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));

            for (int i = 0; i < a.size();i++) {
                
                JSONObject getKey = (JSONObject) a.get(i);

                String keyString = (String) getKey.get("RA");
                mapa.put(keyString, keyString);
                
                String nomeString = (String) getKey.get("Nome");
                mapa.put(nomeString, "Nome");
                
                String enderecoString = (String) getKey.get("Endereço");
                mapa.put(enderecoString, "Endereço");
                
                String dataString = (String) getKey.get("Data de Nascimento");
                mapa.put(dataString, "Data de Nascimento");
                
                String celphoneString = (String) getKey.get("Celular");
                mapa.put(celphoneString, "Celular");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Iterator");
        System.err.println(mapa.values());

        if (mapa.get(ra) == null) {
            System.out.println("Não encontrei nenhum dado com esse RA. Por favor, tente novamente");
            TimeUnit.SECONDS.sleep(1);
            consultaChave();
        }


        Scanner scanner = new Scanner(System.in);

        System.out.println("Para consulta de Endereço, digite '1'.");
        System.out.println("Para consulta de número de celular, digite '2'.");
        System.out.println("Para consulta de nome, digite '3'.");
        System.out.println("Para consulta de data de nascimento, digite '4'.");
        option = scanner.nextInt();

        if (option < 1 || option > 4) {
            System.out.println("Opção inválida, por favor, tente novamente.");
            TimeUnit.SECONDS.sleep(1);
            consultaChave();
        }

       

        String userOption = null;

        switch (option) {
            case 1:
                userOption = "Endereço";
                break;
        
            case 2:
                userOption = "Celular";
                break;
            
            case 3:
                userOption = "Nome";
                break;

            case 4:
                userOption = "Data de Nascimento";
                break;

            default:
                break;
        }

        

        //Como as chaves são únicas, se eu atribuo o padrão {Chave,valor} como {"RA":12903} (número de exemplo), não 
        //é possível retornar a chave, através do valor, o que nos levou a recriar a árvore invertendo os valores e chaves

        HashTableMap newMap = new HashTableMap();
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
            for (Object o : a) {
                JSONObject getKey = (JSONObject) o;

                String keyString = (String) getKey.get("RA");
                mapa.put("RA", keyString);
                String nomeString = (String) getKey.get("Nome");
                mapa.put("Nome", nomeString);
                String enderecoString = (String) getKey.get("Endereço");
                mapa.put("Endereço", enderecoString);
                String dataString = (String) getKey.get("Data de Nascimento");
                mapa.put("Data de Nascimento", dataString);
                String celphoneString = (String) getKey.get("Celular");
                mapa.put("Celular", celphoneString);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        

        System.out.println(userOption + " do aluno cadastrado no RA " + ra + " é: "+ mapa.get(userOption));
        System.out.println("Retornando ao menu de consulta...");
        TimeUnit.SECONDS.sleep(1);
        consulta();


    }





















    //----------------------------------------------------------------------------------------PARTE DE MUDANÇAS----------------------------------------------------------------------------------------



    public static void changingDB() throws InterruptedException {

        int optionC = 1;

        Scanner scanC = new Scanner(System.in);

        while (true) {

            System.out.println("Você entrou na opção de mudanças na base de dados. Para voltar ao menu, digite '1'");

            System.out.println("Para criação de uma nova pilha de alunos, digite '2'");

            System.out.println("Para remoção, digite '3'");

            optionC = scanC.nextInt();

            if (optionC < 1 || optionC > 4) {
                System.out.println("Opção inválida, por favor, tente novamente.");
                continue;
            }

            break;

        }

        if (optionC == 1) {
            userInterface();
        }

        else if (optionC == 2) {
            Create();
        }

        else if (optionC == 3) {
            Remove();
        }

    
        

        scanC.close();

    }

    public static void Remove() throws InterruptedException {
        
        File checkFile =  new File("data.json");
        if (checkFile.length() == 0) {
            System.out.println("Ops, ainda não tenho essa pilha. Por favor, crie uma nova!");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
        }
        
        
        HashTableMap<String, String> mapa = new HashTableMap<String, String>();
    
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
            for (Object o : a) {
                JSONObject getKey = (JSONObject) o;
                
               


                String keyString = (String) getKey.get("RA");
                mapa.put("RA", keyString);
                String nomeString = (String) getKey.get("Nome");
                mapa.put("Nome", nomeString);
                String enderecoString = (String) getKey.get("Endereço");
                mapa.put("Endereço", enderecoString);
                String dataString = (String) getKey.get("Data de Nascimento");
                mapa.put("Data de Nascimento", dataString);
                String celphoneString = (String) getKey.get("Celular");
                mapa.put("Celular", celphoneString);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Scanner scanOption = new Scanner(System.in);
        System.out.println("Para remoção de elemento através do RA, digite '1'");
        System.out.println("Para remoção de elemento através do Celular, digite '2'");
        System.out.println("Para remoção de elemento através da data de nascimento, digite '3'");
        System.out.println("Para remoção de elemento através do Endereço, digite '4'");
        System.out.println("Para remoção de elemento através do nome, digite '5'");
        System.out.println("Para retornar ao menu principal, digite '6'");
        String userOption = scanOption.nextLine();

        System.out.println("Useroption: " + userOption);

        if (userOption.equals("6")) {
            System.out.println("Retornando ao menu principal...");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
        }

        else if ((Integer.parseInt(userOption) > 6 || Integer.parseInt(userOption) < 1)) {
            System.out.println("Opção inválida, por favor, verifique a digitação e tente novamente!");
            TimeUnit.SECONDS.sleep(1);
            Remove();
        }



        //Remoção do RA
        if (userOption.equals("1")) {
            
            try {
                JSONParser parser = new JSONParser();
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                for (int i = 0; i < a.size(); i++) {
                    JSONObject getKey = (JSONObject) a.get(i);
            
                    mapa.remove("RA");
                    getKey.remove("RA");
                    System.out.println("Removendo RA e retornando ao menu...");
                    TimeUnit.SECONDS.sleep(1);
                    changingDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRO! Retornando ao menu de mudanças...");
                TimeUnit.SECONDS.sleep(1);
                changingDB();
            }
        }

        //Remoção do Celular
        else if (userOption.equals("2")) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                for (int i = 0; i < a.size(); i++) {
                    JSONObject getKey = (JSONObject) a.get(i);
            
                    mapa.remove("Celular");
                    getKey.remove("Celular");
                    System.out.println("Removendo Celular e retornando ao menu...");
                    TimeUnit.SECONDS.sleep(1);
                    changingDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRO! Retornando ao menu de mudanças...");
                TimeUnit.SECONDS.sleep(1);
                changingDB();
            }
            
        }

        //Remoção da data de nascimento
        else if(userOption.equals("3")) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                for (int i = 0; i < a.size(); i++) {
                    JSONObject getKey = (JSONObject) a.get(i);
            
                    mapa.remove("Data de Nascimento");
                    getKey.remove("Data de Nascimento");
                    System.out.println("Removendo Data de Nascimento e retornando ao menu...");
                    TimeUnit.SECONDS.sleep(1);
                    changingDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRO! Retornando ao menu de mudanças...");
                TimeUnit.SECONDS.sleep(1);
                changingDB();
            }
        }

        //Remoção do endereço
        else if (userOption.equals("4")) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                for (int i = 0; i < a.size(); i++) {
                    JSONObject getKey = (JSONObject) a.get(i);
            
                    mapa.remove("Endereço");
                    getKey.remove("Endereço");
                    System.out.println("Removendo Endereço e retornando ao menu...");
                    TimeUnit.SECONDS.sleep(1);
                    changingDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRO! Retornando ao menu de mudanças...");
                TimeUnit.SECONDS.sleep(1);
                changingDB();
            }
            
        }

        //Remoção do nome
        else if(userOption.equals("5")) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                for (int i = 0; i < a.size(); i++) {
                    JSONObject getKey = (JSONObject) a.get(i);
            
                    mapa.remove("Nome");
                    getKey.remove("Nome");
                    System.out.println("Removendo nome e retornando ao menu...");
                    TimeUnit.SECONDS.sleep(1);
                    changingDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRO! Retornando ao menu de mudanças...");
                TimeUnit.SECONDS.sleep(1);
                changingDB();
        }

        }
        
        



    }


    public static void Create() throws InterruptedException {

        

        try {

            System.out.println("---------------------CRIAR PILHA---------------------");
            Scanner scanRA = new Scanner(System.in);
            System.out.println("Insira o RA do aluno, contendo apenas números:");
            String registro = scanRA.nextLine();
            NString registroDeAluno = new NString(registro);

            Scanner scanNome = new Scanner(System.in);
            System.out.println("Insira o nome do aluno:");
            String nomeAluno = scanNome.nextLine();

            Scanner scanEndereco = new Scanner(System.in);
            System.out.println("Insira o endereço do aluno:");
            String endereco = scanEndereco.nextLine();

            Scanner scanCelular = new Scanner(System.in);
            System.out.println("Insira o celular do aluno, contendo apenas números, sem traços, sem o '+' do DDD, sem o código internacional e com o DDD: ");
            String celularScan = scanCelular.nextLine();
            CelString nCelular = new CelString(celularScan);

            Scanner scanBirthDate = new Scanner(System.in);
            System.out.println("Insira a data do aluno no formato DD/MM/YYYY");
            String birthDateScan = scanBirthDate.nextLine();
            BirthDate dataAluno = new BirthDate(birthDateScan);

            ArrayStack pilhaUsuario = new ArrayStack(registroDeAluno, nomeAluno, endereco, dataAluno, nCelular);
            
        
        
            try {


            

                File mainFile = new File("data.json");
    
                if (mainFile.length() == 0) {
    
                    
    
                    
    
                    JSONObject atributos = new JSONObject();
                    atributos.put("RA", pilhaUsuario.getRA());
                    atributos.put("Nome", pilhaUsuario.getNome());
                    atributos.put("Endereço", pilhaUsuario.getAddress());
                    atributos.put("Data de Nascimento", pilhaUsuario.getDate());
                    atributos.put("Celular", pilhaUsuario.getCelular());
    
                    JSONArray listaUsuario = new JSONArray();
                    listaUsuario.add(atributos);
    
                    try {
    
                        FileWriter file = new FileWriter("data.json", true);
                        file.append(listaUsuario.toJSONString());
                        file.flush();
    
    
                        
    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    
    
    
                    System.out.println("Pilha criada!!!. Voltando ao menu principal.");
                    TimeUnit.SECONDS.sleep(2);
                    userInterface();
                    }
    

                else {
                    
                    JSONParser jsonParser = new JSONParser();
    
                    try {
                        Object obj = jsonParser.parse(new FileReader("data.json"));
                        JSONArray jsonArray = (JSONArray)obj;
    

    
                        JSONObject atributos = new JSONObject();
                        atributos.put("RA", pilhaUsuario.getRA());
                        atributos.put("Nome", pilhaUsuario.getNome());
                        atributos.put("Endereço", pilhaUsuario.getAddress());
                        atributos.put("Data de Nascimento", pilhaUsuario.getDate());
                        atributos.put("Celular", pilhaUsuario.getCelular());
    
                        jsonArray.add(atributos);
    
                        FileWriter file = new FileWriter("data.json");
                        file.write(jsonArray.toJSONString());
                        file.flush();

    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                System.out.println("Pilha criada!!!. Voltando ao menu principal.");
                TimeUnit.SECONDS.sleep(2);
                userInterface();
                
    
    
            }
        
        
        
        } catch (Exception e) {
            e.printStackTrace();
            Create();
        }

       



        

        }
        
        catch (NStringDigitException e) {

            System.out.println(e.getMessage());
            TimeUnit.SECONDS.sleep(2);
            Create();
        }

        catch (InvalidPhoneNumberException e) {

            System.out.println(e.getMessage());
            TimeUnit.SECONDS.sleep(2);
            Create();

        }

        catch (InvalidBirthDateException e) {
            
            System.out.println(e.getMessage());
            TimeUnit.SECONDS.sleep(2);
            Create();
        }

    }

}