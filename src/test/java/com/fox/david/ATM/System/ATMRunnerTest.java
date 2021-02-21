package com.fox.david.ATM.System;

import com.fox.david.ATM.model.dao.AccountDAO;
import com.fox.david.ATM.model.dao.TransactionDAO;
import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.dto.WithdrawalDTO;
import com.fox.david.ATM.model.repository.AccountRepository;
import com.fox.david.ATM.system.ATMRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ATMRunnerTest {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionDAO transactionDAO;

    ATMRunner atmRunner;

    long id;

    WithdrawalDTO withdrawalDTO;

    @BeforeEach
    void init() {
        atmRunner = new ATMRunner(accountDAO, transactionDAO);
        id = accountRepository.save(new Account(1L, 1234, 200, 100)).getId();
    }

    @Test
    void testValidWithdrawalRequest() throws Exception {
        withdrawalDTO = new WithdrawalDTO(50, 1234);

        ResponseEntity<AccountDTO> payload = atmRunner.withdrawFunds(withdrawalDTO,id);
        AccountDTO dto = payload.getBody();
        Assertions.assertEquals(150, dto.getBalance());
        Assertions.assertEquals(100, dto.getOverdraft());
    }

    @Test
    void testInvalidWithdrawalRequest_pinDoesNotMatch() {
        withdrawalDTO = new WithdrawalDTO(150, 1236);

        ResponseEntity<AccountDTO> payload = atmRunner.withdrawFunds(withdrawalDTO, id);

        AccountDTO dto = payload.getBody();
        Assertions.assertNotNull(dto.getErrors());
    }

    @Test
    void testInvalidWithdrawalRequest_UnknownId() {
        withdrawalDTO = new WithdrawalDTO(150, 1234);

        ResponseEntity<AccountDTO> payload = atmRunner.withdrawFunds(withdrawalDTO, 234L);

        AccountDTO dto = payload.getBody();
        Assertions.assertNotNull(dto.getErrors());
    }

    @Test
    void testInvalidWithdrawalRequest_NotEnoughInAccount() {
        withdrawalDTO = new WithdrawalDTO(305, 1234);


        ResponseEntity<AccountDTO> payload = atmRunner.withdrawFunds(withdrawalDTO, id);

        AccountDTO dto = payload.getBody();
        Assertions.assertNotNull(dto.getErrors());
    }

    @Test
    void testInvalidWithdrawalRequest_NotEnoughInATM() {

    }

    @Test
    void testValidBalanceRequest() throws Exception {
        withdrawalDTO = new WithdrawalDTO(0, 1234);

        ResponseEntity<AccountDTO> payload = atmRunner.getBalance(withdrawalDTO, id);
        AccountDTO dto = payload.getBody();
        Assertions.assertEquals(200, dto.getBalance());
        Assertions.assertEquals(100, dto.getOverdraft());
    }

    @Test
    void testInvalidBalanceRequest_IncorrectPin() {
        withdrawalDTO = new WithdrawalDTO(0, 1236);

        ResponseEntity<AccountDTO> payload = atmRunner.getBalance(withdrawalDTO, id);

        AccountDTO dto = payload.getBody();
        Assertions.assertNotNull(dto.getErrors());
    }

    @Test
    void testInvalidBalanceRequest_AccountDoesNotExist() {
        withdrawalDTO = new WithdrawalDTO(0, 1234);


        ResponseEntity<AccountDTO> payload = atmRunner.getBalance(withdrawalDTO, 663613613L);
        AccountDTO dto = payload.getBody();
        Assertions.assertNotNull(dto.getErrors());
    }

}
