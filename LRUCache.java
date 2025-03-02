/*
Time complexity:- O(1)
Space complexity:- O(n) where n = capacity
*/
class LRUCache {

    class Node{
        int key;
        int val;
        Node prev;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    HashMap<Integer, Node> map;
    int capacity;
    Node head, tail;

    public LRUCache(int capacity) {
        
        this.capacity = capacity;
        map = new HashMap<>();
        //first node in doubly linkedlist
        head = new Node(-1, -1);
        //last node in doubly linkedlist
        tail = new Node(-1, -1);
        //we will add new nodes in between and in order of most recently used
        head.next = tail;
        tail.prev = head;
    }

    //remove node from current location
    public void removeNode(Node curr) {

        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
    }

    //recently used nodes should be added to head
    public void addToHead(Node curr) {

        curr.prev = head;
        curr.next = head.next;
        head.next = curr;
        curr.next.prev = curr;
    }
    
    //if the element is present then return val and put the node in front
    public int get(int key) {
        
        //key is not present
        if(!map.containsKey(key))
            return -1;

        //if key is already present
        Node curr = map.get(key);

        //remove node
        removeNode(curr);

        //add to front
        addToHead(curr);
        return curr.val;
    }
    
    public void put(int key, int value) {
        
        //if the key is already present then update the value
        if(map.containsKey(key)) {

            Node curr = map.get(key);
            curr.val = value;

            //now we need to remove and push it to front since it is recently used
            removeNode(curr);
            addToHead(curr);
        }
        else {

            //check capacity if its full
            if(map.size() == capacity) {             
                //remove node from tail to accomodate
                Node tailPrev = tail.prev;

                //remove node from both map and linked list
                removeNode(tailPrev);
                map.remove(tailPrev.key);
            }
            
            //add new node to list
            Node curr = new Node(key, value);
            map.put(key, curr);

            //add to the head since it is recently used
            addToHead(curr);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */