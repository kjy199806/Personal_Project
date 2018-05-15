#include "Bank.h"

void Bank::removeCAccount(char* accountId, char* password, int pinNum){
  int isFound = 0;
  for(int i = 0; i < MAX_SIZE && !isFound; i++){
    if(CAccounts[i].isThisAcc(accountId,password,pinNum)){
      isFound = 1;
      CAccounts[i].clearAcc();
    }
  }
}

void Bank::createCAccount(char* newAccountId,
                          char* newPassword,
                          int   newPinNum,
                          Types newType,
                          double newBalance){

  int emptyAccIndex = getEmptyCheAcc();

  ChequingAccount temp(newAccountId,newPassword,newPinNum,newType,newBalance);
  CAccounts[emptyAccIndex] = temp;
  
}

void Bank::removeSAccount(){
  int isFound = 0;
  for(int i = 0; i < MAX_SIZE && !isFound; i++){
    if(SAccounts[i].isThisAcc(accountId,password,pinNum)){
      isFound = 1;
      SAccounts[i].clearAcc();
    }
  }
}

void Bank::createSAccount(char* newAccountId,
                          char* newPassword,
                          int   newPinNum,
                          Types newType,
                          double newBalance){

  int emptyAccIndex = getEmptySavAcc();

  SavingAccount temp(newAccountId,newPassword,newPinNum,newType,newBalance);
  SAccounts[emptyAccIndex] = temp;
}


int Bank::getEmptyCheAcc()const{
  int isFound = 0;
  int getEmptyAccIndex = 0;
  for(int i = 0; i < MAX_SIZE && !isFound; i++){
    if(CAccounts[i].isEmpty()){
      isFound = 1;
      getEmptyAccIndex = i;
    }
  }
  return getEmptyAccIndex;
}

int Bank::getEmptySavAcc()const{
  int isFound = 0;
  int getEmptyAccIndex = 0;
  for(int i = 0; i < MAX_SIZE && !isFound; i++){
    if(SAccounts[i].isEmpty()){
      isFound = 1;
      getEmptyAccIndex = i;
    }
  }
  return getEmptyAccIndex;
}
