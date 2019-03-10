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
 * Checks if given element is present in the list, if it is, the element is taken to the beginning of the list
 * If there are multiple same-valued elements only the first is taken to the beginning
 * 
 * @param list A pointer to the list 
 * @param value Value to be found
 * @return true if value is present in the list, false otherwise
 */
bool findMTF(struct singly_linked_list *list, int value);

/**
 * Checks if given element is present in the list, if it is, the element is moved one place forward 
 * (closer to the beginning of the list), if found element is the head, nothing happens after checking 
 * If there are multiple same-valued elements only the first is moved one place forward
 *
 * @param list A pointer to the list 
 * @param value Value to be found
 * @return true if value is present in the list, false otherwise
 */
bool findTRANS(struct singly_linked_list *list, int value);

/**
 * Print list
 * 
 * @param list A pointer to the list to be printed
 */
void print_list(struct singly_linked_list *list);