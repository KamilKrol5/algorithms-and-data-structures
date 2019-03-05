#include "singlyLinkedList.h"

int main(int argc, char const *argv[])
{
    struct singly_linked_list my_list = { NULL };
    insert(&my_list,5);
    insert(&my_list,7);
    insert(&my_list,6);
    insert(&my_list,125);
    insert(&my_list,0);
    insert(&my_list,-1);
    insert(&my_list,5);
    insert(&my_list,7);
    insert(&my_list,5);
    insert(&my_list,5);
    print_list(&my_list); printf("\n");
    remove_all(&my_list,5);
    print_list(&my_list); printf("\n");
    remove_all(&my_list,7);
    print_list(&my_list); printf("\n");
    return 0;
}
