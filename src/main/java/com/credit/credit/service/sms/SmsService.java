package com.credit.credit.service.sms;

import com.credit.credit.enums.LoanStatus;

public interface SmsService{
    String sendSmsMessageByLoanStatus(LoanStatus status);
}
