package pl.strefakursow.security.simple.attacks.csrf;

import lombok.Data;

@Data
public class TransferCommand {
    private String fromAccount;
    private String toAccount;
    private double amount;
}
