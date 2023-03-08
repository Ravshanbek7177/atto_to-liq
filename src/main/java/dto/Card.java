package dto;

import enums.CardStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Integer id;
    private String number;
    private String exp_date;
    private Double amount;
    private Integer profile_id;
    private LocalDateTime created_date;
    private LocalDateTime added_date;
    private CardStatus status;
}
