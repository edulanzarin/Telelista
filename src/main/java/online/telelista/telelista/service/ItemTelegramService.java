package online.telelista.telelista.service;

import online.telelista.telelista.dto.CreateItemRequest;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.model.Usuario;
import online.telelista.telelista.repository.ItemTelegramRepository;
import online.telelista.telelista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemTelegramService {

    @Autowired
    private ItemTelegramRepository itemTelegramRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ItemTelegram cadastrarItem(CreateItemRequest request, UserDetails userDetails) {

        String username = userDetails.getUsername();

        Usuario dono = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        ItemTelegram novoItem = new ItemTelegram();
        novoItem.setNome(request.getNome());
        novoItem.setLink(request.getLink());
        novoItem.setDescricao(request.getDescricao());
        novoItem.setTipo(request.getTipo());

        novoItem.setDono(dono);

        return itemTelegramRepository.save(novoItem);
    }

    public List<ItemTelegram> listarTodos() {
        LocalDateTime dataAtual = LocalDateTime.now();

        return itemTelegramRepository.findAllOrderByActiveBoost(dataAtual);
    }
}