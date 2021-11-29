package pilha;

import exception.*;




import abstractTypes.BirthDate;
import abstractTypes.NString;


public class ArrayStack <InputValue> implements Stack<InputValue> {

    protected int capacity;
    protected InputValue S[];
    protected int top = -1;
    protected static NString RA;
    protected static String nomeAluno;
    protected static String address;
    protected static BirthDate date;


    public ArrayStack(NString registroAluno, String nome, String endereco, BirthDate data) {
        capacity = 100;
        
        this.RA = registroAluno;
        this.nomeAluno = nome;
        this.address = endereco;
        this.date = data;
    }

    public String getRA() {
        return RA.toString();
    }

    public String getNome() {
        return nomeAluno;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        String formatedString = date.JSONformat();
        return formatedString;
    }


    public int size() {
        return (top + 1);
    }

    public boolean isEmpty(){
        return (top < 0);
    }

    public InputValue top() {
        if (isEmpty()) throw new EmptyStackException("Pilha vazia!");
        return S[top];
    }

    public void push(InputValue element) throws FullStackException {
        if (size() == capacity) throw new FullStackException("Pilha cheia!");
        S[++top] = element;
    }

    public InputValue pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException("Pilha vazia!");
        InputValue element;
        element = S[top];
        // desreferência S[top] para o sistema de coleta de lixo
        S[top--] = null;
        return element;
    }

    public String toString() {
        String s;
        s = "[";
        if (size() > 0) s += S[0];
        if (size() > 1) {
            for (int i = 1; i <= size() - 1; i++) {
                s += ", " + S[i];
            }
        }
        return s + "]";
    }

}
