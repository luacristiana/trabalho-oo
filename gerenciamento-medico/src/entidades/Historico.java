package entidades;

import java.util.ArrayList;
import java.util.List;

public class Historico<T> { // Classe genérica que pode armazenar qualquer tipo de objeto (ex.: Consulta ou Exame)

    private List<T> itens;

    public Historico() {
        itens = new ArrayList<>();
    }

    public void adicionarItem(T item) {
        itens.add(item);
    }

    public List<T> getItens() {
        return itens;
    }
}
