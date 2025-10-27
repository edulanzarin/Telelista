package online.telelista.telelista.controller;

import online.telelista.telelista.model.ItemTelegram;
// Importamos o nosso novo SERVIÇO
import online.telelista.telelista.service.ItemTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemTelegramController {

    @Autowired
    private ItemTelegramService itemTelegramService;

    @PostMapping
    public ResponseEntity<ItemTelegram> cadastrarItem(@RequestBody ItemTelegram itemRecebido) {
        ItemTelegram itemSalvo = itemTelegramService.cadastrarItem(itemRecebido);

        return ResponseEntity.status(201).body(itemSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ItemTelegram>> listarTodos() {
        List<ItemTelegram> lista = itemTelegramService.listarTodos();

        return ResponseEntity.ok(lista);
    }
}