package online.telelista.telelista.controller;

import online.telelista.telelista.dto.CreateBoostRequest;
import online.telelista.telelista.model.Boost;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.repository.BoostRepository;
import online.telelista.telelista.repository.ItemTelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boosts")
public class BoostController {

    @Autowired
    private BoostRepository boostRepository;

    @Autowired
    private ItemTelegramRepository itemTelegramRepository;

    private static final List<Integer> DURACOES_PERMITIDAS = List.of(1, 7, 15, 30);

    @PostMapping
    public ResponseEntity<?> criarBoost(@RequestBody CreateBoostRequest request) {

        if (!DURACOES_PERMITIDAS.contains(request.getDuracaoEmDias())) {
            return ResponseEntity
                    .badRequest()
                    .body("Duração inválida.");
        }

        Optional<ItemTelegram> itemOptional = itemTelegramRepository.findById(request.getItemId());

        if (itemOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        ItemTelegram itemParaBoostar = itemOptional.get();

        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = dataInicio.plusDays(request.getDuracaoEmDias());

        Boost novoBoost = new Boost();
        novoBoost.setItem(itemParaBoostar);
        novoBoost.setDataInicio(dataInicio);
        novoBoost.setDataFim(dataFim);

        Boost boostSalvo = boostRepository.save(novoBoost);

        return ResponseEntity.status(201).body(boostSalvo);
    }
}
