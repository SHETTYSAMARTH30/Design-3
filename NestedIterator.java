/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/* Time complexity :- 
a) next() - O(1)
b) hasNext() - worst case - O(h), where h = maximum nesting depth (maximum number of lists inside each other). Ammortized case:- O(1)
Space complexity:- O(h) where h = maximum nesting depth (maximum number of lists inside each other).
*/
public class NestedIterator implements Iterator<Integer> {

    Stack<Iterator<NestedInteger>> stack;
    NestedInteger lastElement;

    public NestedIterator(List<NestedInteger> nestedList) {

        this.stack = new Stack<>();
        //we use iterator to iterate over the List of NestedInteger
        stack.push(nestedList.iterator());
    }

    @Override
    public Integer next() { //O(1)
        
        //since we called next() already in hasNext() function, the index has moved to next element
        //hence we already saved the answer in lastElement
        return lastElement.getInteger();
    }

    @Override
    public boolean hasNext() { //Worst case:- O(h) :- nested list, ammortized:- O(1)
        
        //hasNext will be called first hence we will perform all computation here 
        while(!stack.isEmpty()) {

            //check if there is next element, if not we pop the entire List
            if(!stack.peek().hasNext()) {

                stack.pop();
            }
            //if it is integer then we return true
            //but first we save the element before moving to next element, because we even want to return the element
            else if ((lastElement = stack.peek().next()).isInteger()){

                return true;
            }
            else {

                stack.push(lastElement.getList().iterator());
            }
        }

        //if stack is empty then there is no next element
        return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */

/* We want lazy loading. We do not want to load all the data onto an arraylist and then return values. We loose dynamicity. Hence we will use Iterator to maintain dynamicity */