package com.fox.david.ATM.system;

import com.fox.david.ATM.model.dao.AccountDAO;
import com.fox.david.ATM.model.dao.TransactionDAO;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.dto.WithdrawalDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ATMRunner {
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;
    LinkedHashMap<String, Integer> availableCurrency;

    public ATMRunner(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;

        availableCurrency = new LinkedHashMap<>();
        availableCurrency.put("50", 10);
        availableCurrency.put("20", 30);
        availableCurrency.put("10", 30);
        availableCurrency.put("5", 20);
        availableCurrency.put("total", 1500);
    }


    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> getBalance(@RequestBody WithdrawalDTO dto, @PathVariable Long id) {
        try {
            if (accountDAO.checkPin(id, dto.getPin())) {
                AccountDTO accountDTO = accountDAO.getBalance(id);
                return new ResponseEntity<>(accountDTO, HttpStatus.OK);
            } else {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setErrors("Incorrect PIN Entered");
                return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<AccountDTO> withdrawFunds(WithdrawalDTO dto, long id) {
        try {
            if (accountDAO.checkPin(id, dto.getPin())) {
                if (accountDAO.fundsAvailable(dto.getAmount(), id) && cashAvailable(dto.getAmount())) {
                    AccountDTO accountDTO = makeWithdrawal(dto.getAmount(), id);
                    return new ResponseEntity<>(accountDTO, HttpStatus.OK);
                }
                throw new Exception("Requested amount exceeds available funds");
            }
            throw new Exception("incorrect PIN Entered");
        } catch (Exception e) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
        }
    }

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

    private AccountDTO makeWithdrawal(int amount, Long id) throws Exception {
        String requested = String.valueOf(amount);
        for (int i = 0; i < requested.length(); i++) {
            availableCurrency = parseCash(availableCurrency, i, requested.charAt(requested.length() - (i+1)));
        }
//        transactionDAO.createTransaction(amount, id);
        return accountDAO.withdrawFunds(amount, id);
    }

    private LinkedHashMap<String, Integer> parseCash(LinkedHashMap<String, Integer> availableCurrency, int i, char charAt) {
        double numericValue = Integer.parseInt(String.valueOf(charAt)) * (Math.pow(10, i));
        for (Map.Entry<String, Integer> cursor : availableCurrency.entrySet()) {
            while (numericValue > 0 && cursor.getValue() > 0) {
                if (numericValue - Integer.parseInt(cursor.getKey()) >= 0) {
                    numericValue = numericValue - Integer.parseInt(cursor.getKey());
                    cursor.setValue(cursor.getValue() - 1);
                }
            }

            if (numericValue > 0) {
                return null;
            }
        }
        return availableCurrency;
    }
}
