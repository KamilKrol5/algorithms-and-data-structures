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
    insert(&my_list,8);
    insert(&my_list,5);
    insert(&my_list,5);
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
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,42));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,125));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,-1));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,5));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,5));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,6));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,7));
    print_list(&my_list); printf("\n");
    printf("%d ",findTRANS(&my_list,8));
    print_list(&my_list); printf("\n");
    return 0;
}
