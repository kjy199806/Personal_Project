#ifndef SAVINGACCOUNT_H
#define SAVINGACCOUNT_H

#include "Account.h"

class SavingAccount : public Account{

  double balance;
  
  public:
  SavingAccount();
  void setBalance(double money);
  bool isValidBalance();
}

#endif
