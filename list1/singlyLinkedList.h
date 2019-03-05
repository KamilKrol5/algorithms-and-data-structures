#pragma once
#include <stdbool.h>
#include <stdio.h>

extern int number_of_comparisons;

struct node;

struct singly_linked_list
{
    struct node *head;
};

/**
 * Insert operation on singly linked list, inserted object is at the beginning of the list
 * 
 * @param list A pointer to the list
 * @param value Int value to be inserted
 */
void insert(struct singly_linked_list *list, int value);

/**
 * Delete operation on singly linked list, it removes all occurences of this value
 * 
 * @param list A pointer to the list
 * @param value Value to be removed
 * @return true if value was present in the list and was successfully removed, 
 *         false if value was not present in the list
 */
bool remove_all(struct singly_linked_list *list, int value);

/**
 * Tell if a list is empty
 * 
 * @param list A pointer to the list
 * @return true if list is empty, false otherwise
 */
bool is_empty(struct singly_linked_list *list);

/**
 *
 */
bool findMTF(struct singly_linked_list *list, int value);

/**
 *
 */
bool findTRANS(struct singly_linked_list *list, int value);

/**
 * 
 */
void print_list(struct singly_linked_list *list);