package pilha;

import exception.EmptyStackException;

public interface Stack <InputValue> {

    public int size();

    public boolean isEmpty();

    public InputValue top() throws EmptyStackException;

    public void push(InputValue element);

    public InputValue pop() throws EmptyStackException;
    
}
