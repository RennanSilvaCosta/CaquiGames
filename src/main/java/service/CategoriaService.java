package service;

import dao.CategoriaDAO;
import model.Categoria;

import java.util.List;

public class CategoriaService {

    CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void cadastrarCategoria(String cat) {
        categoriaDAO.criarCategoria(new Categoria(cat));
    }

    public List<Categoria> buscaTodasCategoiras() {
        return categoriaDAO.buscaCategorias();
    }

}
