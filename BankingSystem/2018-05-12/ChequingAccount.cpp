#include "Account.h"
#include "ChequingAccount.h"

ChequingAccount::ChequingAccount() : Account(){
  balance = 0;
}

ChequingAccount::ChequingAccount(char* newAccountId,
                                 char* newPassword,
                                 int   newPinNum,
                                 Types newType,
                                 double newBalance): Account(newAccountId,newPassword,
                                                             newPinNum,newType){
  balance = newBalance;

}

void ChequingAccount::setBalance(double money){
  if(isValidBalance(money)){
    balance += money; 
  }
}

bool ChequingAccount::isValidBalance(double money){
  return (balance + money) >= 0;
}
