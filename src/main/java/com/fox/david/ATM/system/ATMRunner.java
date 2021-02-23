package com.fox.david.ATM.system;

import com.fox.david.ATM.model.dao.AccountDAO;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.dto.WithdrawalDTO;
import com.fox.david.ATM.utils.CurrencyUtil;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ATMRunner {
    Logger LOGGER = Logger.getLogger(this.getClass().getName());
    AccountDAO accountDAO;
    public LinkedHashMap<String, Integer> availableCurrency;

    public ATMRunner(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;

        availableCurrency = CurrencyUtil.setUpCurrency(10, 30, 30, 20);
    }


    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> getBalance(@RequestBody WithdrawalDTO dto, @PathVariable Long id) {
        try {
            LOGGER.info("Getting balance for account number " + id);
            if (accountDAO.checkPin(id, dto.getPin())) {
                LOGGER.info("pin accepted, returning balance for account number " + id);
                AccountDTO accountDTO = accountDAO.getBalance(id,availableCurrency.get("total"));
                return new ResponseEntity<>(accountDTO, HttpStatus.OK);
            } else {
                LOGGER.error("Incorrect PIN number given for account number " + id);
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setMessage("Incorrect PIN Entered");
                return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("Balance request for account " + id + " failed for the following reason: " + e.getMessage());
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/account/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdrawFunds(@RequestBody WithdrawalDTO dto, @PathVariable long id) {
        try {
            LOGGER.info("Attempting withdrawal of " + dto.getAmount() + " from account number " + id);
            if (accountDAO.checkPin(id, dto.getPin())) {
                LOGGER.info("pin accepted, checking if account and ATM have available funds");
                if (accountDAO.fundsAvailable(dto.getAmount(), id) && cashAvailable(dto.getAmount())) {
                    LOGGER.info("funds available, making withdrawal of " + dto.getAmount() + " from account number " + id);
                    AccountDTO accountDTO = makeWithdrawal(dto.getAmount(), id);
                    return new ResponseEntity<>(accountDTO, HttpStatus.OK);
                }
                throw new Exception("Requested amount exceeds available funds");
            }
            throw new Exception("incorrect PIN Entered");
        } catch (Exception e) {
            LOGGER.error("Withdrawal request for account " + id + " failed for the following reason: " + e.getMessage());
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
        }
    }

    //Method to check if the required funds are available in the ATM
    private boolean cashAvailable(int amount) {
        LinkedHashMap<String, Integer> temp = availableCurrency;
        if (amount < temp.get("total")) {
            String requested = String.valueOf(amount);
            for (int i = 0; i < requested.length(); i++) {
                temp = parseCash(temp, i, requested.charAt(requested.length() - (i + 1)));

                if (temp == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //Method to withdraw cash from the related account
    private AccountDTO makeWithdrawal(int amount, Long id) throws Exception {
        LinkedHashMap<String, Integer> originalNotes = new LinkedHashMap<>(availableCurrency);
        String requested = String.valueOf(amount);
        for (int i = 0; i < requested.length(); i++) {
            availableCurrency = parseCash(availableCurrency, i, requested.charAt(requested.length() - (i + 1)));
        }
        return accountDAO.withdrawFunds(amount, id, calculateOutput(originalNotes, availableCurrency));
    }

    //Calculates the changes between two hash maps
    private LinkedHashMap<String, Integer> calculateOutput(LinkedHashMap<String, Integer> originalNotes, LinkedHashMap<String, Integer> availableCurrency) {
        for (Map.Entry<String, Integer> cursor : originalNotes.entrySet()) {
            cursor.setValue(cursor.getValue() - availableCurrency.get(cursor.getKey()));
        }
        return originalNotes;
    }

    //Method to parse the requested funds into their cash components
    private LinkedHashMap<String, Integer> parseCash(LinkedHashMap<String, Integer> availableCurrency, int i, char charAt) {
        double numericValue = Integer.parseInt(String.valueOf(charAt)) * (Math.pow(10, i));
        for (Map.Entry<String, Integer> cursor : availableCurrency.entrySet()) {
            //Subtracts the value of the key from the numeric value until either reach zero.
            while (numericValue > 0 && cursor.getValue() > 0) {
                //Breaks the loop if numeric value - the cursor value is less than zero
                if (numericValue - Integer.parseInt(cursor.getKey()) >= 0) {
                    numericValue = numericValue - Integer.parseInt(cursor.getKey());
                    //counts the cursor down by one
                    cursor.setValue(cursor.getValue() - 1);
                } else {
                    break;
                }
            }
        }
        if (numericValue != 0) {
            return null;
        }
        return availableCurrency;
    }
}
