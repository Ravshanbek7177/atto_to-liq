package dto;

import enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class Transaction {
    private Integer id;
    private Integer profile_id;
    private Integer card_id;
    private Integer terminal_id;
    private Double amount;
    private LocalDateTime created_date;
    private TransactionType transactionType;

}

