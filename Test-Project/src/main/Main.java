package main;

import abstractTypes.*;
import exception.InvalidBirthDateException;
import exception.InvalidPhoneNumberException;
import exception.NStringDigitException;
import org.json.simple.JSONObject;
import pilha.ArrayStack;

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

    public static void consulta() {

        Scanner scanConsulta = new Scanner(System.in);
        System.out.println("---------------------CONSULTA DE BANCO---------------------");
        System.out.println("Você Entrou no modo de consulta a base de dados.");
        System.out.println("Digite o RA do aluno que deseja procurar:");
        int scanUser = scanConsulta.nextInt();
    }

    public static void changingDB() throws InterruptedException {

        int optionC = 1;

        Scanner scanC = new Scanner(System.in);

        while (true) {

            System.out.println("Você entrou na opção de mudanças na base de dados. Para voltar ao menu, digite '1'");

            System.out.println("Para criação de uma nova pilha de alunos, digite '2'");

            System.out.println("Para remoção, digite '3'");

            System.out.println("Para adição, digite '4'");

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

        else if (optionC == 4) {
            AddContent();
        }

        scanC.close();

    }

    public static void Remove() {

    }

    public static void AddContent() {

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
            JSONObject obj = new JSONObject();
            obj.put("RA", pilhaUsuario.getRA());
            obj.put("Nome", pilhaUsuario.getNome());
            obj.put("Endereço", pilhaUsuario.getAddress());
            obj.put("Data de Nascimento", pilhaUsuario.getDate());

            try {
                FileWriter file = new FileWriter("json-simple-1.1.1.jar");
                file.write(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Pilha criada!!!. Voltando ao menu principal.");
            TimeUnit.SECONDS.sleep(2);
            userInterface();

        } catch (NStringDigitException e) {

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
