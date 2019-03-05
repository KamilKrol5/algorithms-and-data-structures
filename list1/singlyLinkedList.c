#include "singlyLinkedList.h"

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

int number_of_comparisons = 0;

struct node
{
    int value;
    struct node *next;
};

bool is_empty(struct singly_linked_list *list) {
    if (list->head == NULL)
        return true;
    return false;
}

void insert(struct singly_linked_list *list, int value) {
    struct node *new_node = malloc(sizeof(*new_node));
    new_node->next = list->head;
    new_node->value = value;
    list->head = new_node;
}

bool remove_all(struct singly_linked_list *list, int value) {
    bool return_val = false;
    struct node *tmp_prev = NULL;
    struct node *tmp_curr = list->head;
    while (tmp_curr != NULL) {
        if (tmp_curr->value == value) {
            return_val = true;
            if (tmp_prev != NULL) {
                tmp_prev->next = tmp_curr->next;
            } 
            else {
                list->head = list->head->next;
                // tmp_prev = NULL;
                tmp_curr = list->head;
                continue;           
            }
            
        }
        tmp_prev = tmp_curr;
        tmp_curr = tmp_curr->next;  
    }
    return return_val;
}

void print_list(struct singly_linked_list *list) {
    struct node *tmp_curr = list->head;
    printf("[ ");
    while (tmp_curr != NULL) {
        printf("%d ", tmp_curr->value);
        tmp_curr = tmp_curr->next;  
    }
    printf("]");
}