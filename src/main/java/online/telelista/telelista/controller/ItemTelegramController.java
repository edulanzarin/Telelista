package online.telelista.telelista.controller;

import online.telelista.telelista.dto.CreateItemRequest;
import online.telelista.telelista.dto.ItemResponse;
import online.telelista.telelista.dto.UpdateItemRequest;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.service.ItemTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/itens")
public class ItemTelegramController {

    @Autowired
    private ItemTelegramService itemTelegramService;

    @PostMapping
    public ResponseEntity<ItemResponse> cadastrarItem(
            @RequestBody CreateItemRequest itemRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        ItemTelegram itemSalvo = itemTelegramService.cadastrarItem(itemRequest, userDetails);

        ItemResponse response = new ItemResponse(itemSalvo);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> listarTodos(Pageable pageable) {

        Page<ItemTelegram> paginaDeItens = itemTelegramService.listarTodos(pageable);

        Page<ItemResponse> paginaDeResponse = paginaDeItens
                .map(item -> new ItemResponse(item));

        return ResponseEntity.ok(paginaDeResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> buscarItemPorId(@PathVariable UUID id) {
        Optional<ItemTelegram> itemOptional = itemTelegramService.buscarPorId(id);

        if (itemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ItemResponse response = new ItemResponse(itemOptional.get());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> atualizarItem(
            @PathVariable UUID id,
            @RequestBody UpdateItemRequest itemRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        ItemTelegram itemAtualizado = itemTelegramService.atualizarItem(id, itemRequest, userDetails);
        ItemResponse response = new ItemResponse(itemAtualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        itemTelegramService.deletarItem(id, userDetails);

        return ResponseEntity.noContent().build();
    }
}