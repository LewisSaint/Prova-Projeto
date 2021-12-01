package main;

import abstractTypes.*;
import exception.InvalidBirthDateException;
import exception.InvalidPhoneNumberException;
import exception.NStringDigitException;
import mapa.HashTableMap;
import mapa.HashTableMap.HashEntry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.UserDataHandler;

import pilha.ArrayStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Iterator;
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

        option = scanConsulta.nextInt();


        if (option == 1) {
            consultaRA();
        }

        else if(option == 2) {
            consultaChave();
        }

        else if (option < 1 || option > 2) {
            System.out.println("Opção inválida. Por favor, tente novamente!");
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


        if (mapa.get("RA").toString().equals(ra) == false) {
            System.out.println("Diferentes!");
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


        if (mapa.get("RA").toString().equals(ra) == false) {
            System.out.println("Diferentes!");
            TimeUnit.SECONDS.sleep(1);
            consultaRA();
        }

        System.out.println(userOption + "do aluno cadastrado no RA " + ra + " é: "+ mapa.get(userOption));

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

        if (userOption == "6") {
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
        if (userOption == "1") {
            mapa.remove("RA");
            System.out.println("Removendo RA e retornando ao menu...");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
        }

        //Remoção do Celular
        else if (userOption == "2") {
            mapa.remove("Celular");
            System.out.println("Removendo Celular e retornando ao menu...");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
            
        }

        //Remoção da data de nascimento
        else if(userOption == "3") {
            mapa.remove("Data de Nascimento");
            System.out.println("Removendo data de nascimento e retornando ao menu...");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
        }

        //Remoção do endereço
        else if (userOption == "4") {
            mapa.remove("Endereço");
            System.out.println("Removendo endereço e retornando ao menu...");
            TimeUnit.SECONDS.sleep(1);
            changingDB();
            
        }

        //Remoção do nome
        else if(userOption == "5") {
            mapa.remove("Nome");
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
            System.out.println(
                    "Insira o celular do aluno, contendo apenas números, sem traços, sem o '+' do DDD, sem o código internacional e com o DDD: ");
            String celularScan = scanCelular.nextLine();
            CelString nCelular = new CelString(celularScan);

            Scanner scanBirthDate = new Scanner(System.in);
            System.out.println("Insira a data do aluno no formato DD/MM/YYYY");
            String birthDateScan = scanBirthDate.nextLine();
            BirthDate dataAluno = new BirthDate(birthDateScan);

            ArrayStack pilhaUsuario = new ArrayStack(registroDeAluno, nomeAluno, endereco, dataAluno, nCelular);

            //Debug
            System.out.println("RA: " + pilhaUsuario.getRA());
            System.out.println("Nome: " + pilhaUsuario.getNome());
            System.out.println("Endereco: "+ pilhaUsuario.getAddress());
            System.out.println("Data: " + pilhaUsuario.getDate());
            System.out.println("Celular: " + pilhaUsuario.getCelular());

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