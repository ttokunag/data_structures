# Linked-List
### Author: Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)

## What is a linked-list?
A linked-list is a data structure which each element of it has **pointers** to other elements. The characteristics of linked-lists is that it's able to add/remove elements faster than the standard built-in lists such as the ArrayList in java. What makes differences is that the built-in lists change its capacity or shifts elements per add/remove operations. On the other hand, what a linked-list does is just changing the pointers of each element. This difference of contents modification process makes it possible to faster add/remove. A linked-list data structure is often used to implements other data structures such as a stack, a queue, and a deque(double ended queue).

## Types of linked-lists
#### Singly linked-list:
is a linked-list whose contents have only one poitner. Let `A` and `B` be elements in a linked-list, and `A` has a pointer to `B`(looks like **A -> B**, and usually B does NOT point to A). Then we can traverse A first then B. It never occurs that we see A after visiting B. Thus, a singly linked-list is useful if one knows that a program traverse it unidirectionally.
#### Doubly linked-list:
is a linked-list whose contents have two pointers which we usually call the predecessor or successor pointer. This is very useful when we expect that we will traverse the linked-list back and forth. Doubly linked-list is often used for other data structure implementations. Doubly linked-list structure looks like **A <=> B <=> C**
#### Dummy Nodes
A node in a linked-list which has no data in it is called a dummy node. The advantage of using dummy nodes is making algorithms implementations simpler because the head and the tail node have never been null.

## Complexity
In normal operation, the insert, deletion, search operations are `O(n)`. However, if use it to implements a deque, the insert, deletion operations are `O(1)` because we always see either ends and don't have to search a sepecific element.
