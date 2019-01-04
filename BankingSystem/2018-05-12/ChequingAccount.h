#ifndef CHEQUINGACCOUNT_H
#define CHEQUINGACCOUNT_H

#include "Account.h"

class ChequingAccount : public Account{

  double balance;

  public:
  ChequingAccount();
  ChequingAccount(char* newAccountId,
                  char* newPassword,
                  int   newPinNum,
                  Types newType,
                  double newBalance);
  void setBalance(double money);
  bool isValidBalance(double money);
};
#endif
