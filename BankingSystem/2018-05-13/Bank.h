#ifndef BANK_H
#define BANK_H

#include "ChequingAccount.h"
#include "SavingAccount.h"

#define MAX_SIZE 10

class Bank{

  ChequingAccount CAccounts[MAX_SIZE];
  SavingAccount   SAccounts[MAX_SIZE];

  public:

  //controlling Chequing accounts
  void removeCAccount();
  void createCAccount();
  int getEmptyCheAcc()const;

  //controlling Saving accounts
  void removeSAccount();
  void createSAccount();
  int getEmptySavAcc()const;

};
#endif
