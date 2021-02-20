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

import java.util.HashMap;
import java.util.Map;

@RestController
public class ATMRunner {
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;
    HashMap<String, Integer> availableCurrency;

    public ATMRunner(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;

        availableCurrency = new HashMap<>();
        availableCurrency.put("5",20);
        availableCurrency.put("10",30);
        availableCurrency.put("20",30);
        availableCurrency.put("50",10);
    }


    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> getBalance(@RequestBody WithdrawalDTO dto, @PathVariable Long id) throws Exception {
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
                if (accountDAO.fundsAvailable(dto.getAmount(), id)) {

                    return new ResponseEntity<>(accountDTO, HttpStatus.OK);
                }
                throw new Exception("Requested amount exceeds available funds");
            } else {
                throw new Exception("incorrect PIN Entered");
            }
        } catch (Exception e) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(accountDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
