package com.fidelity.mts.servcie;

import java.util.List;
import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.dto.TransferResponse;
import com.fidelity.mts.entity.TransactionLog;

public interface TransferService {
	
	TransferResponse transferMoney(TransferRequest transferRequest);
	List<TransactionLog> getTransaction(Long id);
	List<TransactionLog> findByFromAccountId(Long fromId);
	List<TransactionLog> findByToAccountId(Long toId);

}
