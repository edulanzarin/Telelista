package online.telelista.telelista.service;

import online.telelista.telelista.dto.CreateItemRequest;
import online.telelista.telelista.dto.UpdateItemRequest;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.model.Usuario;
import online.telelista.telelista.repository.ItemTelegramRepository;
import online.telelista.telelista.repository.UsuarioRepository;
import online.telelista.telelista.exception.AccessDeniedException;
import online.telelista.telelista.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<ItemTelegram> buscarPorId(UUID id) {

        return itemTelegramRepository.findById(id);
    }

    public ItemTelegram atualizarItem(UUID id, UpdateItemRequest request, UserDetails userDetails) {

        ItemTelegram item = itemTelegramRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado com ID: " + id));

        String usernameLogado = userDetails.getUsername();
        String usernameDono = item.getDono().getUsername();

        if (!usernameLogado.equals(usernameDono)) {
            throw new AccessDeniedException("Usuário não tem permissão para atualizar item com ID: " + id);
        }

        item.setNome(request.getNome());
        item.setLink(request.getLink());
        item.setDescricao(request.getDescricao());
        item.setTipo(request.getTipo());

        return itemTelegramRepository.save(item);
    }

    public void deletarItem(UUID id, UserDetails userDetails) {

        ItemTelegram item = itemTelegramRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado com ID: " + id));

        String usernameLogado = userDetails.getUsername();
        String usernameDono = item.getDono().getUsername();

        if (!usernameLogado.equals(usernameDono)) {
            throw new AccessDeniedException("Usuário não tem permissão para deletar item com ID: " + id);
        }

        itemTelegramRepository.deleteById(id);
    }
}