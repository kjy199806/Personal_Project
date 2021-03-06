#include <stdio.h>
#include <string.h>
#define readObject a
#define len strlen
#define myDebug(...) printf("line : %d function : %s", __LINE__, __FUNCTION__);printf(__VA_ARGS__)
#define debug 0
#if debug == 1
  #define myprintf printf
#else
  #define myprintf 
#endif
void skipSpace(char* array, int* currentIndex);
void readObject(char* array, int size, int * isValidJSON, int* currentIndex);
void readString(char* array, int* currentIndex, int size, int *isValidJSON);
void readValue(char* array, int* currentIndex, int *isObject, int *isValidJSON,int size);
void readArray(char* array, int* currentIndex, int* isObject, int *isValidJSON, int size);
void readNumber(char* array, int* currentIndex);

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
  char ar9[] = "{\"a\":124,\"h\":122}";
  char ar10[] = "\
                  {\"a\":\
                  {\"b\":124},\
                  {\"c\":125},\
                }";
  char ar11[] = "{\"a\":{\"b\":124},{\"c\":125},";
  char ar12[] = "{\"a\":{\"b\":124},,{\"c\":125},";

  //printf("%s", array);
  int I = 0;
  size =len(ar) ;a(ar,size,&iV,&I); printf("%s: %s\n", ar, iV==1?"Pass":"Fail"); 
  I = 0;
  size =len(ar1);a(ar1,size,&iV,&I); printf("%s: %s\n", ar1,iV==0?"Pass":"Fail");
  I = 0;
  size =len(ar2);a(ar2,size,&iV,&I); printf("%s: %s\n", ar2,iV==0?"Pass":"Fail");
  I = 0;
  size =len(ar3);a(ar3,size,&iV,&I); printf("%s: %s\n", ar3,iV==0?"Pass":"Fail");
  I = 0;
  size =len(ar4);a(ar4,size,&iV,&I); printf("%s: %s\n", ar4,iV==0?"Pass":"Fail");
  I = 0;
  size =len(ar5);a(ar5,size,&iV,&I); printf("%s: %s\n", ar5,iV==1?"Pass":"Fail");
  I = 0;
  size =len(ar6);a(ar6,size,&iV,&I); printf("%s: %s\n", ar6,iV==1?"Pass":"Fail");
  I = 0;
  size =len(ar7);a(ar7,size,&iV,&I); printf("%s: %s\n", ar7,iV==1?"Pass":"Fail");
  I = 0;
  size =len(ar8);a(ar8,size,&iV,&I); printf("%s: %s\n", ar8,iV==1?"Pass":"Fail");
  I = 0;
  size =len(ar9);a(ar9,size,&iV,&I); printf("%s: %s\n", ar9,iV==1?"Pass":"Fail");
  I = 0;
  size =len(ar10);a(ar10,size,&iV,&I);printf("%s: %s\n", ar10,iV==0?"Pass":"Fail");
  I = 0;
  size =len(ar11);a(ar11,size,&iV,&I);printf("%s: %s\n", ar11,iV==0?"Pass":"Fail");
  size =len(ar12);a(ar12,size,&iV,&I);printf("%s: %s\n", ar12,iV==0?"Pass":"Fail");

  //isValidJSON == 1 ? printf("valid\n"):printf("Invalid\n");



}
  
/*---------------------------------------------------------

              **readObject function**

    process : It will check the object format
        
    isValidJSON = return 1 (True )
                  return 0 (False)

-----------------------------------------------------------*/

void readObject(char* array, int size, int* isValidJSON, int* currentIndex){
  int isObject = 0;
  *isValidJSON = 1;
  size = strlen(array);
  for(; *currentIndex < size; (*currentIndex)++){
    skipSpace(array, currentIndex);

  /*--------------------------------------------------
        1. Reading object  
  ----------------------------------------------------*/

    if(array[*currentIndex] == '{'){ (*currentIndex)++;
      skipSpace(array, currentIndex);

      /*----------------------------------------------------------------
        Repeating the same process until their is no [,] after the value
      -----------------------------------------------------------------*/

                   //---------------------------------------------------------
                   //           **Process of an (do while) **
                   //
      do{          // <--- (do while) is used when there is [,]
                   //---------------------------------------------------------

          if(array[*currentIndex] == ','){  //---------------------------
                                            //        **Process**
            (*currentIndex)++;              // after looping once index+1
            skipSpace(array, currentIndex); // to check the next index
          }                                 //---------------------------

          if(array[*currentIndex] == '"'){ (*currentIndex)++; 

            /*-------------------------------------------------------
              2. Reading String
              --------------------------------------------------------*/

            readString(array, currentIndex, size, isValidJSON);
            (*currentIndex)++;

          }

          else if(array[*currentIndex] == '}'){

            /*--------------------------------------------------------
              End of Object                   (Valid Object)
              --------------------------------------------------------*/

            return;

          } 

          else{
            /*---------------------------------------------------------
              Invalid Reason : does not have ["] or [}] after the [{]

              JSON Format : { (string) : (value) }
              ^                  ^
              | ["]   (or)  [}]  |
              |                  |
              ---------------------------------------------------------*/
            *isValidJSON = 0;
            return;
          }skipSpace(array, currentIndex);

        /*---------------------------------------------------------
          3. Reading Value
          ----------------------------------------------------------*/

          if(array[*currentIndex] == ':'){ (*currentIndex)++; 
            // debugging
            myprintf("Passing through [:] current Index : %d \n" , *currentIndex);
            skipSpace(array, currentIndex);

            readValue(array, currentIndex, &isObject, isValidJSON, size);

          }
          else{

          /*-------------------------------------------------------------
            Invalid Reason : require [:] after the String

            JSON Format :   { (string) : (value) }
                                       ^
                                       | (required)
                                       |
            --------------------------------------------------------------*/
            //debug: telling the current Index and the error line number
            myprintf("current Index :  %c\n", array[*currentIndex]);
            myprintf("%d error\n", __LINE__);
            *isValidJSON = 0;
            return;

          } skipSpace(array, currentIndex);
          
          //debugging if the while loop work or not
          //if(array[*currentIndex] == ','){
          //  myprintf("found [,]!!!\n");
          //}

      /*------------------------------ 
        continue looping if there is [,] 
        after the value
        --------------------------------*/

      }while(array[*currentIndex] == ',');  // end of do while loop

    } // end of if conditional about  '{' 

    if(array[*currentIndex] == '}'){

    /*----------------------------
      Found [}]  (Valid JSON)
      -----------------------------*/
      return;
    }
    else{
      // debug: telling the current Index and the error line number
      myprintf("current Index :  %d [%c]\n",*currentIndex, array[*currentIndex]);
      myprintf("%d error\n", __LINE__);
      *isValidJSON = 0;
      return;

    }
  } // end of for looping

} // end of  readObject function 

/*-----------------------------------------------------

              **skipSpace function**

  Process : continue to increase the current index 
            until the character is found

-------------------------------------------------------*/
void skipSpace(char* array, int* currentIndex){
  for( ; *currentIndex<strlen(array); (*currentIndex)++){
    if(array[*currentIndex] != ' '){
      return;
    }
  }
}

/*-------------------------------------------------------

              **readString function** 
  
              Any UNICODE character except
              ["]or [\] or control character 

---------------------------------------------------------*/
void readString(char* array, int* currentIndex,int size, int *isValidJSON){
  for( ; *currentIndex<strlen(array); (*currentIndex)++){
    skipSpace(array, currentIndex);
    if(array[*currentIndex] != '"'){
        
    }
    else if(*currentIndex == size && array[*currentIndex] != '}'){
      // debug: printf if it inValid
      myprintf("line : %d error!!!\n",__LINE__);
      *isValidJSON = 0;
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
void readValue(char* array, int* currentIndex, int* isObject,int *isValidJSON, int size){
  /*---------------------------------------------
                **For string**

  ---------------------------------------------*/

  if(array[*currentIndex] == '"'){
    readString(array, currentIndex,size, isValidJSON);
  }

  /*----------------------------------------------
                **For number**

  -----------------------------------------------*/

  else if(array[*currentIndex] >= '0' && array[*currentIndex] <= '9'){ 
    readNumber(array, currentIndex);
  }

  /*-----------------------------------------------  
                **For object**

  -------------------------------------------------*/

  else if(array[*currentIndex] == '{'){
    readObject(array, size, isValidJSON, currentIndex);
    skipSpace(array, currentIndex);
    if(array[*currentIndex] == '}'){
      (*currentIndex)++;
    }
    //printf("current index : [%c]\n" , array[*currentIndex]);
    return;
  }

  /*------------------------------------------------
                **For array**

  --------------------------------------------------*/

  else if(array[*currentIndex] == '['){ 
    readArray(array, currentIndex, isObject, isValidJSON, size);
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
    return;
  }
    
}

/*------------------------------------------------
            ** readArray function **

    An array begins with '[' and ends with ']'. 
    Values are separated by [,]

-------------------------------------------------*/

void readArray(char* array,int* currentIndex,int* isObject,int* isValidJSON, int size){

  do{
    readValue(array, currentIndex, isObject, isValidJSON, size);

    /*------------------------------------------
              If the value is (object)
    -------------------------------------------*/
    if(isObject){
      return;
    } skipSpace(array, currentIndex); *currentIndex++;

  }while(array[*currentIndex] == ','); skipSpace(array, currentIndex);

  if(array[*currentIndex] == ']'){
    return;
  }
  else{
    *isValidJSON = 0;
    return;
  }

}

/*--------------------------------------------------
              ** readNumber function**

----------------------------------------------------*/

void readNumber(char* array, int* currentIndex){
  for( ; *currentIndex < strlen(array); (*currentIndex)++){
    if(array[*currentIndex] >= '0' && array[*currentIndex] <= '9'){ 
    }
    else{
      return;
    }
  }
}


