#ifndef SAVINGACCOUNT_H
#define SAVINGACCOUNT_H

#include "Account.h"

class SavingAccount : public Account{

  double balance;
  
  public:
  SavingAccount();
  SavingAccount(char* newAccountId,
                char* newPassword,
                int newPinNum,
                Types newType,
                double newBalance);
  void setBalance(double money);
  bool isValidBalance(double money);
  void clearAcc();
  SavingAccount& operator=(const SavingAccount& other);
};

#endif
