package online.telelista.telelista.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.telelista.telelista.dto.CategoriaResponse;
import online.telelista.telelista.dto.CreateCategoriaRequest;
import online.telelista.telelista.model.Categoria;
import online.telelista.telelista.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CreateCategoriaRequest request) {

        try {
            Categoria categoriaSalva = categoriaService.criarCategoria(request);
            CategoriaResponse response = new CategoriaResponse(categoriaSalva);
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {

        List<Categoria> categorias = categoriaService.listarCategorias();
        List<CategoriaResponse> response = categorias.stream()
                .map(CategoriaResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
