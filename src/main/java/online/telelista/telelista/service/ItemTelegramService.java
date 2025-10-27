package online.telelista.telelista.service;

import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.repository.ItemTelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemTelegramService {

    @Autowired
    private ItemTelegramRepository itemTelegramRepository;

    public ItemTelegram cadastrarItem(ItemTelegram item) {
        return itemTelegramRepository.save(item);
    }

    public List<ItemTelegram> listarTodos() {
        LocalDateTime dataAtual = LocalDateTime.now();

        return itemTelegramRepository.findAllOrderByActiveBoost(dataAtual);
    }
}