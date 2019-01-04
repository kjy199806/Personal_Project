#include "stack.h"
#include<stdio.h>
#include <string.h>
#include <stdlib.h>

void init(struct stack_t* s, int maxSize){
  s->MAXSIZE = maxSize;
  s->stack = (int*)malloc(maxSize * sizeof(int));
  //char a[maxSize];
  s->top = -1;
}


void delete(struct stack_t* s){
  //free(s->stack);
}

int isempty(struct stack_t* s) {

   if(s->top == -1)
      return 1;
   else
      return 0;
}
   
int isfull(struct stack_t* s) {

   if(s->top == s->MAXSIZE)
      return 1;
   else
      return 0;
}

int peek(struct stack_t* s) {
   return s->stack[s->top];
}

int pop(struct stack_t* s) {
   int data;
  
   if(!isempty(s)) {
      data = s->stack[s->top];
      s->top = s->top - 1;   
      return data;
   } else {
      printf("Could not retrieve data, Stack is empty.\n");
   }
}

int push(struct stack_t* s, int data) {

   if(!isfull(s)) {
      s->top = s->top + 1;   
      s->stack[s->top] = data;
   } else {
      printf("Could not insert data, Stack is full.\n");
   }
}
