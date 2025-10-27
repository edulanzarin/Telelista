package online.telelista.telelista.controller;

import online.telelista.telelista.dto.CreateItemRequest;
import online.telelista.telelista.dto.ItemResponse;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.service.ItemTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<ItemResponse>> listarTodos() {
        List<ItemTelegram> listaDeItens = itemTelegramService.listarTodos();

        List<ItemResponse> responseList = listaDeItens.stream()
                .map(item -> new ItemResponse(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}