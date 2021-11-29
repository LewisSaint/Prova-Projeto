package main;

import abstractTypes.*;
import exception.InvalidBirthDateException;
import exception.InvalidPhoneNumberException;
import exception.NStringDigitException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pilha.ArrayStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

        int scanUser = scanConsulta.nextInt();


        if (scanUser == 1) {
            consultaRA();
        }

        else if(scanUser == 2) {
            consultaChave();
        }

    }


    public static void consultaRA() {

        Scanner scanConsultaRA = new Scanner(System.in);
        System.out.println("Digite o RA do aluno desejado: ");
        String ra = scanConsultaRA.nextLine();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("data.json"));
            JSONObject jsonObject = (JSONObject) obj;
 
			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray companyList = (JSONArray) jsonObject.get(ra);
 
			// An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
			// Iterators differ from enumerations in two ways:
			// 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
			// 2. Method names have been improved.
			Iterator<JSONObject> iterator = companyList.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
            }
        }

        catch (Exception e) {e.printStackTrace();}
        
}
    
        
        


    



    public static void consultaChave() {

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

    public static void Remove() {

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

            ArrayStack pilhaUsuario = new ArrayStack(registroDeAluno, nomeAluno, endereco, dataAluno);

            //Debug
            System.out.println("RA: " + pilhaUsuario.getRA());
            System.out.println("Nome: " + pilhaUsuario.getNome());
            System.out.println("Endereco: "+ pilhaUsuario.getAddress());
            System.out.println("Data: " + pilhaUsuario.getDate());

            JSONObject atributos = new JSONObject();
            atributos.put("RA", pilhaUsuario.getRA());
            atributos.put("Nome", pilhaUsuario.getNome());
            atributos.put("Endereço", pilhaUsuario.getAddress());
            atributos.put("Data de Nascimento", pilhaUsuario.getDate());

            JSONArray listaUsuario = new JSONArray();
            listaUsuario.add(atributos);

            


            try {
                FileWriter file = new FileWriter("data.json", true);
                file.append(listaUsuario.toJSONString());
                file.append("\n");
                file.flush();
                file.close(); 
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