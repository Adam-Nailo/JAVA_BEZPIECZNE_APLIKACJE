package pl.strefakursow.security.attacks.csrf;

import lombok.Data;

@Data
public class TransferCommand {
    private String fromAccount;
    private String toAccount;
    private double amount;
}
