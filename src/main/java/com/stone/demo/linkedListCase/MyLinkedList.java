package com.stone.demo.linkedListCase;

import java.util.Collection;

/**
 * Created by 石头 on 2017/7/4.
 */
public class MyLinkedList<T> {

    private Node<T> first;

    private Node<T> last;

    private int size = 0;


    public void add(T t) {
        Node<T> l = last;
        Node<T> node = new Node<T>(l, t, null);
        last = node;
        if (l == null) {
            first = node;
        }
        size++;
    }

    public void add(int seq, T t) {
        Node<T> n = this.getNode(seq);
        Node<T> node = new Node<T>(n.getPrev(), t, n);
//        n.getPrev().setNext(node);
//        n.setPrev(node);
        size++;
    }

    public boolean addAll(Collection<T> c){
        for (T t : c) {
            this.add(t);
        }
        return true;
    }

    public T get(int seq) {
        Node<T> node = this.getNode(seq);
        return node == null ? null : node.getItem();
    }

    public T remove(int seq) {
        Node<T> node = this.getNode(seq);
        T t = node.getItem();
        Node<T> prev = node.getPrev();
        Node<T> next = node.getNext();
        if (next != null) {
            next.setPrev(prev);
        }
        if (prev != null) {
            prev.setNext(next);
        }
        node = null;
        size--;
        return t;
    }

    public void removeAll() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean contain(T t) {
        for (int i = 0; i < size; i++) {
            if (this.getNode(i).getItem().equals(t)) {
                return true;
            }
        }
        return false;
    }

    private Node<T> getNode(int seq) {
        Node<T> node = first;
        while(seq > 0 && node != null) {
            node = node.getNext();
            seq--;
        }
        return node;
    }

    private Integer size() {
        size = 0;
        Node node = first;
        while(node != null) {
            node = node.getNext();
            size++;
        }
        return size;
    }



    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(T t) {
            this.item = t;
        }

        public Node(Node<T> prev, T t, Node<T> next) {
            this.prev = prev;
            this.item = t;
            this.next = next;
            if(prev != null){
                prev.setNext(this);
            }
            if(next != null){
                next.setPrev(this);
            }
        }
        public T getItem() {
            return item;
        }
        public void setItem(T item) {
            this.item = item;
        }
        public Node<T> getPrev() {
            return prev;
        }
        public void setPrev(Node<T> prev) {
            this.prev = prev;
            if(prev != null){
                prev.setNext(this);
            }
        }
        public Node<T> getNext() {
            return next;
        }
        public void setNext(Node<T> next) {
            this.next = next;
            if(next != null){
                next.setPrev(this);
            }
        }
    }
}
