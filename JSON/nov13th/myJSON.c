#include <stdio.h>
#include <string.h>
#define readJSONformat a
#define len strlen
void readJSONformat(char* array, int size, int * isValidJSON);
void checkString(char* array, int firstIndex, int secondIndex, int* isvalidJSON);
void checkNum(char* array,int colonIndex, int size, int* isValidJSON);


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
  //printf("%s", array);
  size = len(ar) ; a(ar ,size,&iV); printf("%s: %s\n", ar, iV==1?"Pass":"Fail"); 
  size = len(ar1); a(ar1,size,&iV); printf("%s: %s\n", ar1,iV==0?"Pass":"Fail");
  size = len(ar2); a(ar2,size,&iV); printf("%s: %s\n", ar2,iV==0?"Pass":"Fail");
  size = len(ar3); a(ar3,size,&iV); printf("%s: %s\n", ar3,iV==0?"Pass":"Fail");
  size = len(ar4); a(ar4,size,&iV); printf("%s: %s\n", ar4,iV==0?"Pass":"Fail");
  size = len(ar5); a(ar5,size,&iV); printf("%s: %s\n", ar5,iV==1?"Pass":"Fail");
  size = len(ar6); a(ar6,size,&iV); printf("%s: %s\n", ar6,iV==1?"Pass":"Fail");
  size = len(ar7); a(ar7,size,&iV); printf("%s: %s\n", ar7,iV==1?"Pass":"Fail");

  //isValidJSON == 1 ? printf("valid\n"):printf("Invalid\n");



}
  
void readJSONformat(char* array, int size, int* isValidJSON){
  int checkQuote = 0;
  int quoteIndex = 0;
  int secondquoteIndex = 0;
  int countBracket = 0;
  *isValidJSON = 1;

  // finding first quote index in JSON
  if(checkQuote == 0){
    for(int i = 0 ; i < size; i++){
      if(array[i] == '"'){
        checkQuote = 1;
        quoteIndex = i;
        break;
      }
    }
  }

  // checking if JSON has second quote
  if(checkQuote == 1){
    for(int i = quoteIndex+1 ; i < size; i++){
      if(array[i] == '"'){
        checkQuote = 2;
        secondquoteIndex = i;
      }
    }
  }
  
  // checking if JSON string is valid
  checkString(array,quoteIndex,secondquoteIndex,isValidJSON);
   
  int semicolonIndex = -1;
  // checking JSON format
  if(array[secondquoteIndex+1] == ' '){
    for(int i = 0; i<size; i++){
      if(secondquoteIndex+1+i == ':'){
        semicolonIndex =secondquoteIndex+1+i;
        break;
      }
    }
  }
  if(semicolonIndex == -1){
    *isValidJSON = 0;
    return;
  }

  //checking if JSON value has only number
  checkNum(array, semicolonIndex,size,isValidJSON);

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

void checkString(char* array,int firstIndex, int secondIndex, int * isValidJSON){
  for(int i = firstIndex+1; i<secondIndex; i++){
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
