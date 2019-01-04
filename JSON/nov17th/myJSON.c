#include <stdio.h>
#include <string.h>
#define readJSONformat a
#define len strlen
void readJSONformat(char* array, int size, int * isValidJSON);
void checkString(char* array, int firstIndex, int secondIndex, int* isvalidJSON);
void checkNum(char* array,int colonIndex, int size, int* isValidJSON);
void findQuote(char* array, int size, int* quoteIndex, int* secondquoteIndex, int* checkQuote);
void checkStruct(char* array, int* isValidJSON, int size, int checkQuote);
void removeSpace(char* array);

int main(void){
  int size = 0;
  int iV= 1;
  char ar [] = "{\"hello\":123}"  ;
  char ar1[] = "{\"he\"llo\":392}";
  char ar2[] = "{\"hello\":3j3r}" ;
  char ar3[] = "{\"hello\":33" ;
  char ar4[] = "{\"hello\":##}" ;
  char ar5[] = "{\"hello\":10}" ;
  char ar6[] = "{\"a\":{\"b\":123}}";
  char ar7[] = "{\"a\" :  123}";
  char ar8[] = "{}";
  //printf("%s", array);
  size = len(ar) ; a(ar ,size,&iV); printf("%s: %s\n", ar, iV==1?"Pass":"Fail"); 
  size = len(ar1); a(ar1,size,&iV); printf("%s: %s\n", ar1,iV==0?"Pass":"Fail");
  size = len(ar2); a(ar2,size,&iV); printf("%s: %s\n", ar2,iV==0?"Pass":"Fail");
  size = len(ar3); a(ar3,size,&iV); printf("%s: %s\n", ar3,iV==0?"Pass":"Fail");
  size = len(ar4); a(ar4,size,&iV); printf("%s: %s\n", ar4,iV==0?"Pass":"Fail");
  size = len(ar5); a(ar5,size,&iV); printf("%s: %s\n", ar5,iV==1?"Pass":"Fail");
  size = len(ar6); a(ar6,size,&iV); printf("%s: %s\n", ar6,iV==1?"Pass":"Fail");
  size = len(ar7); a(ar7,size,&iV); printf("%s: %s\n", ar7,iV==1?"Pass":"Fail");
  size = len(ar8); a(ar8,size,&iV); printf("%s: %s\n", ar8,iV==1?"Pass":"Fail");

  //isValidJSON == 1 ? printf("valid\n"):printf("Invalid\n");



}
  
void readJSONformat(char* array, int size, int* isValidJSON){
  int quoteIndex = 0;
  int secondquoteIndex = 0;
  int checkQuote = 0;
  *isValidJSON = 1;
  // remove space in the JSON
  removeSpace(array);

  //find quote index
  findQuote(array,size,&quoteIndex,&secondquoteIndex,&checkQuote);

  // checking if JSON string is valid
  checkString(array,quoteIndex,secondquoteIndex,isValidJSON);

   
  // check JSON format

  int semicolonIndex = -1;
  // checking if there is space after the quote
  if(array[secondquoteIndex] == ' '){
    for(int i = 0; i<size; i++){
      if(secondquoteIndex+1+i == ':'){
        semicolonIndex =secondquoteIndex+1+i;
        break;
      }
    }
  }
  else{
    semicolonIndex = secondquoteIndex+1;
  }

  if(semicolonIndex == -1){
    *isValidJSON = 0;
    return;
  }

  //checking if JSON value has only number
  checkNum(array, semicolonIndex,size,isValidJSON);

  // checking JSON structure ex) quotes , brackets
  checkStruct(array,isValidJSON,size,checkQuote);
}

void checkString(char* array,int firstIndex, int secondIndex, int* isValidJSON){
  for(int i = firstIndex+1; i<secondIndex; i++){
    //printf("%c ", array[i]);
    if(array[i] == '"'){
      *isValidJSON = 0;
      return;
    }
  }
}

void checkNum(char* array,int colonIndex, int size, int* isValidJSON){
  for(int i =colonIndex+1; i<size-1; i++){
    if((array[i] >= '0' && array[i] <= '9')){
    }
    else{
      *isValidJSON = 0;
      return;
    }
  }
}

void findQuote(char* array, int size, int* quoteIndex, int* secondquoteIndex,int * checkQuote){

  // finding first quote index in JSON
  if(*checkQuote == 0){
    for(int i = 0 ; i < size; i++){
      if(array[i] == '"'){
        (*checkQuote)++;
        *quoteIndex = i;
        break;
      }
    }
  }

  // checking if JSON has second quote
  if(*checkQuote == 1){
    for(int i = (*quoteIndex)+1 ; i < size; i++){
      if(array[i] == '"'){
        (*checkQuote)++;
        *secondquoteIndex = i;
      }
    }
  }
}

void checkStruct(char* array, int* isValidJSON, int size, int checkQuote){
  int countBracket = 0;

  // checking if JSON structure has 2 quote
  if(checkQuote != 2){  
    *isValidJSON = 0;
  }
  for(int i = 0 ; i<size; i++){
    if(array[i] == '{' || array[i] == '}'){
      countBracket++;
    }
  }
  if(countBracket != 2){
    *isValidJSON = 0;
  }
}

void removeSpace(char* array){
  int size = sizeof(array);
  int isSpace =0;
  int countSpace = 0;
  for(int i = 0; i < size; i++){
    if(array[i] == ' '){
      countSpace++;
      isSpace = 1;
    }
    else {
      if(isSpace){
        array[i-countSpace] = array[i];
        isSpace = 0;
        countSpace = 0;
      }
    }

  }
}
