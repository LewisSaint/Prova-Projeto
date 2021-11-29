package pilha;

import exception.*;




import abstractTypes.BirthDate;
import abstractTypes.NString;


public class ArrayStack <InputValue> implements Stack<InputValue> {

    protected int capacity;
    protected InputValue S[];
    protected int top = -1;
    protected NString RA;
    protected String nomeAluno;
    protected String address;
    protected BirthDate date;


    public ArrayStack(NString registroAluno, String nome, String endereco, BirthDate data) {
        capacity = 100;
        
        NString RA = registroAluno;
        String nomeAluno = nome;
        String address = endereco;
        BirthDate date = data;
    }

    public NString getRA() {
        return RA;
    }

    public String getNome() {
        return nomeAluno;
    }

    public String getAddress() {
        return address;
    }

    public BirthDate getDate() {
        return date;
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
