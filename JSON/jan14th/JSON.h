#ifndef JSON_H          //safeguard
#define JSON_H          //safeguard
#define jsonParse a


/*----------------------------------------
Function Prototypes
------------------------------------------*/
void skipSpace(char* jsonText, int* currentIndex);
void readObject(char* jsonText, int size, int * isValidJSON, int* currentIndex);
void readString(char* jsonText, int* currentIndex, int size, int *isValidJSON);
void readValue(char* jsonText, int* currentIndex, int *isObject, int *isValidJSON,int size);
void readArray(char* jsonText, int* currentIndex, int* isObject, int *isValidJSON, int size);
void readNumber(char* jsonText, int* currentIndex);
void validTestData(char* ar);
void invalidTestData(char* ar);
void jsonParse(char * jsonText,int size, int* isValidJson, int* currentIndex);

#endif // safeguard
