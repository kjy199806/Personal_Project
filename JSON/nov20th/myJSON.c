#include <stdio.h>
#include <string.h>
#define readJSONformat a
#define len strlen
#define myDebug(...) printf("line : %d function : %s", __LINE__, __FUNCTION__);printf(__VA_ARGS__)
void readJSONformat(char* array, int size, int * isValidJSON);
void checkString(char* array, int firstIndex, int secondIndex, int* isvalidJSON);
void checkNum(char* array,int colonIndex, int size, int* isValidJSON);
void findQuote(char* array, int size, int* quoteIndex, int* secondquoteIndex, int* checkQuote);
void checkStruct(char* array, int* isValidJSON, int size, int checkQuote);
void removeSpace(char* array, int size);

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
  //remove all the spaces in the JSON
  removeSpace(array,size);
  printf("isvalid JSON after removing space :%s\n", *isValidJSON==1?"Pass":"Fail");
  size = strlen(array);

  //find quote index
  findQuote(array,strlen(array),&quoteIndex,&secondquoteIndex,&checkQuote);
  printf("isvalid value after fidning quote :%s\n", *isValidJSON==1?"Pass":"Fail");

  // checking if JSON string is valid
  checkString(array,quoteIndex,secondquoteIndex,isValidJSON);
  printf("isvalid value after checking JSON String: %s\n", *isValidJSON==1?"Pass":"Fail");

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
  printf("isvalid value after checking JSON format: %s\n", *isValidJSON==1?"Pass":"Fail");

  //checking if JSON value has only number
  checkNum(array, semicolonIndex,size,isValidJSON);
  printf("line : %d function: %s: isvalid value after checking JSON number: %s\n",__LINE__, __FUNCTION__, *isValidJSON==1?"Pass":"Fail");

  // checking JSON structure ex) quotes , brackets
  checkStruct(array,isValidJSON,size,checkQuote);
  //printf("isvalid value after checking structure: %s\n", *isValidJSON==1?"Pass":"Fail");
}

void checkString(char* array,int firstIndex, int secondIndex, int* isValidJSON){
  //printf("%s firstIndex : %d\n",array, firstIndex);
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
  myDebug("colonIndex : %d\n", colonIndex);

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

void removeSpace(char* array, int size){
  int spaceIndex= -1;
  int isSpace = 0;
  for(int i = 0; i <size; i++){
    if(!isSpace){
      if(array[i] == ' '){
        spaceIndex = i;
        isSpace = 1;
      }
    }
    if(isSpace){
      if(spaceIndex != i){
        if(array[i] != ' '){
          array[spaceIndex] = array[i];
          array[i] = ' ';
          i = spaceIndex;
          spaceIndex = -1;
          isSpace = 0;
        }
      }
    }
  }
  for(int i = 0; i <size; i++){
    if(array[i] == ' '){
      array[i] = '\0';
      break;
    }
  }
  //printf("starting debug\n");
  //for(int i = 0; i <strlen(array); i++){
  //  printf("[%c]", array[i]);
  //}
  //printf("end debug\n");
}






