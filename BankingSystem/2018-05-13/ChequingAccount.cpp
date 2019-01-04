#include "Account.h"
#include "ChequingAccount.h"

//empty account
ChequingAccount::ChequingAccount() : Account(){
  balance = 0;
}

//creating new account
ChequingAccount::ChequingAccount(char* newAccountId,
                                 char* newPassword,
                                 int   newPinNum,
                                 Types newType,
                                 double newBalance): Account(newAccountId,newPassword,
                                                             newPinNum,newType){
  balance = newBalance;

}

//check if the balance is not going below 0 and then do the + / -
void ChequingAccount::setBalance(double money){
  if(isValidBalance(money)){
    balance += money; 
  }
}

bool ChequingAccount::isValidBalance(double money){
  return (balance + money) >= 0;
}

//clear account to empty state 
void ChequingAccount::clearAcc(){
  Account::clearAcc();
  balance = 0;
}

// copy the other account information to this account
// going to use this function when I am creating new account
ChequingAccount& ChequingAccount::operator=(const ChequingAccount& other){

  //make sure these two accounts are not same one
  if(this != &other){
    transactionFee = other.transactionFee;  
    pinNum         = other.pinNum;    
    type           = other.type;      
    balance        = other.balance;

    delete [] accountID;
    delete [] password;  

    accountID = new char [strlen(other.AccountID + 1)];
    strcpy(accountID,other.AccountID);

    password = new char [strlen(other.password + 1)];
    strcpy(password,other.password);
  
  }
  return *this;
}
