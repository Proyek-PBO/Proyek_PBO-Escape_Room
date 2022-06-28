package pbo.projek;

public class Inventory <E> extends Akun {
    private Node<E> head;

    public Inventory(String name) {
        super(name);
        head = null;
    }

    public void add(E item){
        Node baru = new Node(item);
        if(head == null){
            head = baru;
        }else{
            Node tmp = head;
            while(tmp.getNext() != null){
                tmp = tmp.getNext();
            }
            tmp.setNext(baru);
        }
    }
    
    public void remove(int idx){
        if (idx == 0) {
            if (head.getNext() != null) {
                head = head.getNext();
            }else{
                head = null;
            }
        }else{
            Node tmp = head;
            int c = 0;
            while(tmp.getNext() != null){
                if (c+1 != idx) {
                    tmp = tmp.getNext();
                    c++;
                }else{
                    if (tmp.getNext().getNext() == null) {
                        tmp.setNext(null);
                    }else{
                        tmp.setNext(tmp.getNext().getNext());
                    }
                    break;
                }
            }
        }
    }
    
    public void remove(javax.swing.JLabel o){
        if (head.getItem() == o) {
            if (head.getNext() != null) {
                head = head.getNext();
            }else{
                head = null;
            }
        }else{
            Node tmp = head;
            while(tmp.getNext() != null){
                if (tmp.getNext().getItem() != o) {
                    tmp = tmp.getNext();
                }else{
                    if (tmp.getNext().getNext() == null) {
                        tmp.setNext(null);
                    }else{
                        tmp.setNext(tmp.getNext().getNext());
                    }
                    break;
                }
            }
        }
    }
    
    public javax.swing.JLabel get(int idx){
        Node tmp = head;
        if (tmp == null) {
            return null;
        }
        int c = 0;
        while(c < idx && tmp.getNext() != null){
            tmp = tmp.getNext();
            c++;
        }
        if(c == idx){
            return (javax.swing.JLabel)tmp.getItem();
        }else{
            return null;
        }
    }
    
    public int size(){
        if(head == null){
            return -1;
        }
        int c = 0;
        Node tmp = head;
        while(tmp.getNext() != null){
            c++;
            tmp = tmp.getNext();
        }
        return c;
    }
}
