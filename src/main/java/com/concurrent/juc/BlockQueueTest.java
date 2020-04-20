package com.concurrent.juc;

import java.util.concurrent.*;

/**
 * 写：
 * add方法:如果队列已满,则抛出异常；
 *
 * put方法:向队列尾部添加元素,队列已满的时候，阻塞等待。对应读方法take
 *
 * offer方法:向队列尾部添加元素,队列已满的时候，直接返回false。
 * 读：
 * remove移除并返回队列头部的元素,如果队列已空,则抛出NoSuchElementException异常.
 *
 * element返回队列头部的元素,如果队列为空.则抛出NoSuchElementException异常.
 *
 * poll移除并返回队列头部的元素,如果队列为空,则返回null；
 *
 * peek返回队列头部的元素,如果队列为空,则返回null；
 *
 * take移除并返回队列头部的元素,如果队列为空则阻塞；
 *
 * 读写操作时用到的锁：
 *
 * 当执行take、poll等操作时线程需要获取的锁
 *
 * ReentrantLock takeLock = new ReentrantLock();
 *
 * 当执行add、put、offer等操作时线程需要获取锁
 */
public class BlockQueueTest {
    /**
     * 基于数组实现的阻塞队列，先进先出队列，有界队列。在创建时必须制定容量大小。
     * 并可以指定公平性与非公平性，默认情况下是非公平的，即不保证等待时间最长的队列最优先能够访问队列。
     */
    static ArrayBlockingQueue arrayBlockingQueue;
    /**
     * 基于链表实现的阻塞队列，先进先出队列，有界队列。在创建时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
     */
    static LinkedBlockingQueue linkedBlockingQueue;
    /**
     * 按照元素的优先级对元素进行排序，按照优先级顺序出队。并且该阻塞队列为无界阻塞队列，
     * 即容量没有上限（源码中它没有容器满的信号标志）。
     */
    static PriorityBlockingQueue priorityBlockingQueue;

    /**
     * 基于PriorityQueue的延时阻塞队列，无界队列。DelayQueue中的元素只有当其指定的延迟时间到了，
     * 才能够从队列中获取到该元素。因为DelayQueue是一个无界队列，所以往队列中插入数据的操作永远不会被阻塞，
     * 而只有获取数据的操作才会被阻塞。
     */
    static DelayQueue delayQueue;
    /**
     * 一个不存储元素的阻塞队列。
     */
    static SynchronousQueue synchronousQueue;

    /**
     * 一个由链表结构组成的无界阻塞队列。
     */
    static LinkedTransferQueue linkedTransferQueue;

    /**
     * 个由链表结构组成的双向阻塞队列。
     */
    static LinkedBlockingDeque linkedBlockingDeque;


    public static void main(String[] args) throws InterruptedException {
        arrayBlockingQueue.put ("1");
        arrayBlockingQueue.add("1");
        arrayBlockingQueue.contains("1");
    }
}
