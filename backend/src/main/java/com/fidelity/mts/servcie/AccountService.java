package com.fidelity.mts.servcie;

import java.math.BigDecimal;
import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.enums.AccountStatus;

public interface AccountService {
	
	String addAccount(Account act);
	AccountStatus findByAccountStatus(Long id);
	Account findById(Long id);
	BigDecimal getBalance(Long id);
	void credit(Account act, BigDecimal amt);
	void debit(Account act, BigDecimal amt);
	public AccountResponse getDetails(Long id);

}
