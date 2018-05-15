#include "Account.h"
#include "SavingAccount.h"

SavingAccount::SavingAccount(): Account(){
  balance = 0;
}

SavingAccount::SavingAccount(char*  newAccountId,
                             char*  newPassword,
                             int    newPinNum,
                             Types  newType,
                             double newBalance): Account(newAccountId,newPassword,
                                                          newPinNum,newType){

  balance = newBalance;

}
void SavingAccount::setBalance(double money){
  if(isValidBalance(money)){
    balance += money;
  }
}

bool SavingAccount::isValidBalance(double money){
  return (balance + money) >= 0; 
}
