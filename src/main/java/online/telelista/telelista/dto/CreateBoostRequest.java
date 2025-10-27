package online.telelista.telelista.dto;

import lombok.Data;
import java.util.UUID;

@Data // lombok para criar getters e setters
public class CreateBoostRequest {

    private UUID itemId;
    private int duracaoEmDias;
}
