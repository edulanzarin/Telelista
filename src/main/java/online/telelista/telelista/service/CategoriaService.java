package online.telelista.telelista.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.telelista.telelista.dto.CreateCategoriaRequest;
import online.telelista.telelista.model.Categoria;
import online.telelista.telelista.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(CreateCategoriaRequest request) {

        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio.");
        }

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(request.getNome().trim());

        return categoriaRepository.save(novaCategoria);
    }

    public List<Categoria> listarCategorias() {

        return categoriaRepository.findAll();
    }
}
