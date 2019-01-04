#ifndef CHEQUINGACCOUNT_H
#define CHEQUINGACCOUNT_H

#include "Account.h"

class ChequingAccount : public Account{

  double balance;

  public:
  ChequingAccount();
  void setBalance(double money);
  bool isValidBalance();
}
#endif
