package com.fidelity.mts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fidelity.mts.dto.ErrorResponse;
 
@ControllerAdvice
public class AccGlobalExceptionHandler {
	@ExceptionHandler(value=AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> accNotFound(AccountNotFoundException ex) {
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ACC-404","Account Not Found"));
	}
 
    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleNotActive(AccountNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("ACC-403","Account Not Active"));
    }
 
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficient(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("TRX-400","Insufficient Funds."));
    }
 
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("VAL-422","Invalid Input."));
    }
 
}


