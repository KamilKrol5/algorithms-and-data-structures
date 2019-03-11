#include "singlyLinkedList.h"
#include <time.h>
#include <stdlib.h>

const int amount = 1000;

void shuffle(int *array, int size)
{
    if (size > 1) 
    {
        int i;
        for (i = 0; i < size - 1; i++) 
        {
          int j = i + rand() / (RAND_MAX / (size - i) + 1);
          int t = array[j];
          array[j] = array[i];
          array[i] = t;
        }
    }
}

int main(int argc, char const *argv[])
{
    srand(time(NULL));
    struct singly_linked_list my_list = { NULL };
    insert(&my_list,5);
    remove_all(&my_list,5);
    print_list(&my_list);
    printf("\n");
    // insert(&my_list,7);
    // insert(&my_list,6);
    // insert(&my_list,125);
    // insert(&my_list,0);
    // insert(&my_list,-1);
    // insert(&my_list,5);
    // insert(&my_list,8);
    // insert(&my_list,5);
    // insert(&my_list,5);
    // print_list(&my_list); printf("\n");
    // remove_all(&my_list,5);
    // print_list(&my_list); printf("\n");
    // remove_all(&my_list,7);
    // print_list(&my_list); printf("\n");
    // printf("%d ",findMTF(&my_list,125));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findMTF(&my_list,6));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findMTF(&my_list,-1));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findMTF(&my_list,0));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findMTF(&my_list,42));
    // print_list(&my_list); printf("\n");

    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,42));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,125));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,-1));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,5));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,5));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,6));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,7));
    // print_list(&my_list); printf("\n");
    // printf("%d ",findTRANS(&my_list,8));
    // print_list(&my_list); printf("\n");

    //prepare data
    int array[amount];
    struct singly_linked_list test_list = { NULL };
    for (int i=0;i < amount;i++) {
        array[i]=i+1;
    }
    shuffle (array,amount);
    for (int i=0;i < amount;i++) {
        insert(&test_list,array[i]);
    }
    print_list(&test_list);

    //test MTF
    int max;
    while (!is_empty(&test_list)) {
        //print_list(&test_list);
        max = -1;
        for (int y=1; y <= amount; y++) {
            if (findMTF(&test_list,y)) {
                if (y > max) {
                    max = y;
                }
            }
        }
        remove_all(&test_list,max);
    }
    printf("Comparisons (MTF): %d\n",number_of_comparisons);
    number_of_comparisons=0;

    //prepare data
    for (int i=0;i < amount;i++) {
        insert(&test_list,array[i]);
    }
    //test TRANS
    while (!is_empty(&test_list)) {
        //print_list(&test_list);
        max = -1;
        for (int y=1; y <= amount; y++) {
            if (findTRANS(&test_list,y)) {
                if (y > max) {
                    max = y;
                }
            }
        }
        remove_all(&test_list,max);
    }
    printf("Comparisons (TRANS): %d\n",number_of_comparisons);
    number_of_comparisons=0;

    return 0;
}

