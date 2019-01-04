#include <stdio.h>
#include <string.h>
#include "JSON.h" // <------- contains prototypes
#define jsonParse a
#define len strlen
#define myDebug(...) printf("line : %d function : %s", __LINE__, __FUNCTION__);printf(__VA_ARGS__)

// debug
#define debug 1
#if debug == 1 
#define myprintf printf
#else
  #define myprintf 
#endif

int main(void){

  //validTestData();
  printf("\n");
  printf("-----------------------------------\n");
  printf("       testing valid JSON          \n");
  printf("-----------------------------------\n");
  int size = 0;
  int iV= 1;
  char valid [] = "{\"hello\":123}"  ;           validTestData(valid);
  char valid2[] = "{\"hello\":10}" ;             validTestData(valid2);
  char valid3[] = "{\"a\":{\"b\":123}}";         validTestData(valid3);
  char valid4[] = "{\"a\" :  123}";              validTestData(valid4);
  char valid5[] = "{}";                          validTestData(valid5);
  char valid6[] = "{\"a\":124,\"h\":122}";       validTestData(valid6);
  char valid8[] = "{\"a\":\"{2\",\"h\":122}";    validTestData(valid8);
  char valid9[] = "{\"a\":\"{}\",\"h\":122}";    validTestData(valid9);
  char valid10[] = "{\"a\":\"\\\"\",\"h\":122}";   validTestData(valid10);
  char valid11[] = "{\"a\":\"\\t\",\"h\":122}";    validTestData(valid11);




  //invalidTestData();
  printf("\n\n");
  printf("-----------------------------------\n");
  printf("      testing invalid JSON         \n");
  printf("-----------------------------------\n");

  
  char *invalidAddress[] = {

              /*00*/                 "{\"he\"llo\":392}",               
              /*01*/                 "{\"hello\":3j3r}" ,               
              /*02*/                 "{\"hello\":33" ,                  
              /*03*/                 "{\"hello\":##}" ,                 
              /*04*/                 "\
                                       {\"a\":\
                                       {\"b\":124},\
                                       {\"c\":125},\
                                     }",                               
              /*05*/                 "{\"a\":{\"b\":124},{\"c\":125},", 
              /*06*/                 "{\"a\":{\"b\":124},,{\"c\":125},",
              /*07*/                 "{,\"a\":333}",                    
              /*08*/                 "{\"a\":341}a",                   

              /*09*/      "{\"a\":{\"b\":124},{\"c\":125},"  ,
              /*10*/      "{\"a\":{\"b\":124},,{\"c\":125}," ,
              /*11*/      "{,\"a\" :  123}"                  ,
              /*12*/      "{\"a\" :  123,}"                  ,
              /*13*/      ",{\"a\" :  123}"                   ,
              /*14*/      "}              "                   ,
              /*15*/      "{}}            "                   ,
              /*16*/      "{,},             "                 ,
              /*17*/      "{\"\"\":123}                      ",
              /*18*/      "{\"\\\":123}                      ",
              /*19*/      //"{\"hello\":123} 123"               ,
              /*20*/      //"{\"hello\":[1]          }         ",
              /*21*/      //"{\"hello\":[{\"a\":123}]}         ",





                            
  };
  for(int i = 0; i<sizeof(invalidAddress)/sizeof(char *); i++){
    invalidTestData(invalidAddress[i], i);  
  }
}
  
/*---------------------------------------------------------
**readObject function**
isValidJSON =  1 (True )
               0 (False)
-----------------------------------------------------------*/

void readObject(char* jsonText, int size, int* isValidJSON, int* currentIndex){
  int isObject = 0;
  *isValidJSON = 1;
  int isComma = 0;
  size = strlen(jsonText);
  for(; *currentIndex < size; (*currentIndex)++){
    skipSpace(jsonText, currentIndex);

  /*--------------------------------------------------
        1. Reading object  
  ----------------------------------------------------*/

    if(jsonText[*currentIndex] == '{'){ (*currentIndex)++;
      skipSpace(jsonText, currentIndex);

      /*----------------------------------------------------------------
        Repeating the same process until their is no [,] after the value
      -----------------------------------------------------------------*/

                             
                             
                             
      do{                    
                             


          if(jsonText[*currentIndex] == '"'){ 

            /*-------------------------------------------------------
              2. Reading String
              --------------------------------------------------------*/
            
            //(*currentIndex)++;
            readString(jsonText, currentIndex, size, isValidJSON);
            (*currentIndex)++;

          }

          else if(jsonText[*currentIndex] == '}'){ (*currentIndex)++;
            

            /*--------------------------------------------------------
              End of Object                   (Valid Object)
              --------------------------------------------------------*/

            return;

          } 

          else{
            /*---------------------------------------------------------
              Invalid Reason : does not have ["] or [}] after the [{]
              ---------------------------------------------------------*/
            *isValidJSON = 0;
            myprintf("isValidJSON = 0 in line %d\n",__LINE__);
            return;
          }skipSpace(jsonText, currentIndex);

          /*------------------------------------------------
            Eat colon  [:]
          -------------------------------------------------*/

          if(jsonText[*currentIndex] == ':'){ (*currentIndex)++; 
            myprintf("\nPassing through [:] current Index : %d \n" , *currentIndex);
            skipSpace(jsonText, currentIndex);
            /*---------------------------------------------------------
              3. Reading Value
            ----------------------------------------------------------*/

            readValue(jsonText, currentIndex, &isObject, isValidJSON, size);

          }
          else{
            //debug: telling the current Index and the error line number
            myprintf("current Index :  [%c], %d\n", jsonText[*currentIndex], *currentIndex);
            *isValidJSON = 0;
            myprintf("isValidJSON = 0 in line %d\n",__LINE__);
            return;

          } skipSpace(jsonText, currentIndex);
          
          if(jsonText[*currentIndex] == ','){  //---------------------------
            (*currentIndex)++;              //         eat comma
            skipSpace(jsonText, currentIndex); //---------------------------
            isComma = 1;                    
            if(jsonText[*currentIndex] == '}'){
              *isValidJSON = 0;
              return;
            }
          }            
          else{
            isComma = 0;      
          }


      }while(isComma);  // end of do while loop

    } // end of if conditional about  '{' 
    else{
      *isValidJSON = 0;
      return;
    }

    if(jsonText[*currentIndex] == '}'){(*currentIndex)++;
      
      return;
      //if((*currentIndex) < size){
      //  skipSpace(jsonText,currentIndex);
      //}
      //if((*currentIndex) != size){
      //  *isValidJSON = 0;
      //  printf("isValidJSON = 0 in line %d\n",__LINE__);
      //}

    }
    else{
      // debug: telling the current Index and the error line number
      myprintf("current Index :  %d [%c]\n",*currentIndex, jsonText[*currentIndex]);
      myprintf("%d error\n", __LINE__);
      *isValidJSON = 0;
      myprintf("isValidJSON = 0 in line %d\n",__LINE__);
      return;

    }
  } // end of for looping

} // end of  readObject function 

/*-----------------------------------------------------
  **skipSpace function**
-------------------------------------------------------*/
void skipSpace(char* jsonText, int* currentIndex){
  for( ; *currentIndex<strlen(jsonText); (*currentIndex)++){
    if(jsonText[*currentIndex] != ' '){
      return;
    }
  }
}
/*-------------------------------------------------------
  **readString function** 
---------------------------------------------------------*/
void readString(char* jsonText, int* currentIndex,int size, int *isValidJSON){
  (*currentIndex)++;
  for( ; *currentIndex<strlen(jsonText); (*currentIndex)++){ 
    myprintf("[%c] ", jsonText[*currentIndex]);
    skipSpace(jsonText, currentIndex);
    int isBackslash = 1;
    if(jsonText[*currentIndex] != '"'){ skipSpace(jsonText, currentIndex);
      while(isBackslash){ isBackslash = 0;
        if(jsonText[*currentIndex] == '\\'){ 
          (*currentIndex)++;
          skipSpace(jsonText, currentIndex);

          if(jsonText[*currentIndex] == '\"'){ 
          }
          else if(jsonText[*currentIndex] == '\\'){ 
          }
          //else if(jsonText[*currentIndex] == '\/'){
          //}
          else if(jsonText[*currentIndex] == '\b'){
          }
          else if(jsonText[*currentIndex] == '\f'){
          }
          else if(jsonText[*currentIndex] == '\n'){
          }
          else if(jsonText[*currentIndex] == '\r'){
          }
          else if(jsonText[*currentIndex] == '\t'){
          }
          //else if(jsonText[*currentIndex] == '\u'){
          //}
          else{
            myprintf("%d error, [%c] is not a string\n", __LINE__, jsonText[*currentIndex]);
            *isValidJSON = 0;
            return;
          }
          if(jsonText[*currentIndex] == '\\'){
            isBackslash = 1;
          }
        }
      }
    }
    //else if(*currentIndex == size && jsonText[*currentIndex] != '}'){
    else if(*currentIndex == size){
      // debug: printf if it inValid
      myprintf("line : %d error!!!\n",__LINE__);
      *isValidJSON = 0;
      myprintf("isValidJSON = 0 in line %d\n",__LINE__);
      return;
    }
    else{ // found ["]  
      return;
    }
  }
}

/*---------------------------------------------------------
  **readValue function**
Value can be (string)
             (number)
             (object)
             (array)
-----------------------------------------------------------*/
void readValue(char* jsonText, int* currentIndex, int* isObject,int *isValidJSON, int size){
  /*---------------------------------------------
                **For string**

  ---------------------------------------------*/

  if(jsonText[*currentIndex] == '"'){
    readString(jsonText, currentIndex,size, isValidJSON);
  }

  /*----------------------------------------------
                **For number**

  -----------------------------------------------*/

  else if(jsonText[*currentIndex] >= '0' && jsonText[*currentIndex] <= '9'){ 
    readNumber(jsonText, currentIndex);
  }

  /*-----------------------------------------------  
                **For object**

  -------------------------------------------------*/

  else if(jsonText[*currentIndex] == '{'){
    readObject(jsonText, size, isValidJSON, currentIndex);
    skipSpace(jsonText, currentIndex);
    //printf("current index : [%c]\n" , jsonText[*currentIndex]);
    return;
  }

  /*------------------------------------------------
                **For array**

  --------------------------------------------------*/

  else if(jsonText[*currentIndex] == '['){ 
    readArray(jsonText, currentIndex, isObject, isValidJSON, size);
    return;
  }

  /*------------------------------------------
    If it is not (string)
                 (number)
                 (object)
                 (array )
  -------------------------------------------*/

  else{
    *isValidJSON = 0;
    myprintf("isValidJSON = 0 in line %d\n",__LINE__);
    return;
  }
    
}

/*------------------------------------------------
            ** readArray function **

    An array begins with '[' and ends with ']'. 
    Values are separated by [,]

-------------------------------------------------*/

void readArray(char* jsonText,int* currentIndex,int* isObject,int* isValidJSON, int size){

  do{
    readValue(jsonText, currentIndex, isObject, isValidJSON, size);

    /*------------------------------------------
              If the value is (object)
    -------------------------------------------*/
    if(isObject){
      return;
    } skipSpace(jsonText, currentIndex); *currentIndex++;

  }while(jsonText[*currentIndex] == ','); skipSpace(jsonText, currentIndex);

  if(jsonText[*currentIndex] == ']'){
    return;
  }
  else{
    *isValidJSON = 0;
    myprintf("isValidJSON = 0 in line %d\n",__LINE__);
    return;
  }

}

/*--------------------------------------------------
              ** readNumber function**

----------------------------------------------------*/

void readNumber(char* jsonText, int* currentIndex){
  for( ; *currentIndex < strlen(jsonText); (*currentIndex)++){
    if(jsonText[*currentIndex] >= '0' && jsonText[*currentIndex] <= '9'){ 
    }
    else{
      return;
    }
  }
}

/*------------------------------------------------------------
                    ** valid function **
--------------------------------------------------------------
  ar = jsonText              |   #define readObject a                  
  iV = isValid            |   #define len strlen                   
  I = currentIndex        |   printf "pass" if it is working well
--------------------------------------------------------------*/

void validTestData(char* ar){
  int iV =1;
  int size = 0;
  int I=0;
  size =len(ar) ;a(ar,size,&iV,&I); printf("%s: %s\n", ar, iV==1?"Pass":"Fail"); 
}

/*------------------------------------------------------------
                    ** invalid function **
--------------------------------------------------------------
  ar = jsonText              |   #define readObject a                  
  iV = isValid            |   #define len strlen                   
  I = currentIndex        |   printf "pass" if it is working well
--------------------------------------------------------------*/

void invalidTestData(char* ar, int testNum){
  int iV =1;
  int size = 0;
  int I=0;
  size =len(ar);a(ar,size,&iV,&I); printf("testnum #%d: %s: |%s|\n",testNum, iV==0?"Pass":"Fail",ar);
  testNum++;
}

void jsonParse(char * jsonText,int size, int* isValidJson, int* currentIndex){
  readObject(jsonText, 0, isValidJson, currentIndex);
  if(*currentIndex < strlen(jsonText)){
    skipSpace(jsonText, currentIndex);
    if(*currentIndex < strlen(jsonText)){
      *isValidJson = 0; 
    }
  }
  
  //printf("currentIndex = [%c] [%c] [%c]\n", jsonText[*currentIndex],
  //    jsonText[*currentIndex+1], jsonText[*currentIndex+2]);
}
