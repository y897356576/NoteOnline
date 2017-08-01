package com.stone.demo.hashAlgorithm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 石头 on 2017/7/20.
 */
class GeneralHashFunctionLibrary {

    /**
     *  MurMurHash2算法，是非加密HASH算法，性能很高，
     *  比传统的CRC32,MD5,SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     *  http://murmurhash.googlepages.com/
     */
    private static Long MMHash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }


    /*RSHash*/
    public long RSHash(String str) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return hash;
    }


    /*JSHash*/
    public long JSHash(String str) {
        long hash = 1315423911;
        for(int i = 0; i < str.length(); i++)
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        return hash;
    }


    /*PJWHash*/
    public long PJWHash(String str) {
        long BitsInUnsignedInt = (long)(4 * 8);
        long ThreeQuarters = (long)((BitsInUnsignedInt * 3) / 4);
        long OneEighth = (long)(BitsInUnsignedInt / 8);
        long HighBits = (long)(0xFFFFFFFF)<<(BitsInUnsignedInt-OneEighth);
        long hash = 0;
        long test = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + str.charAt(i);
            if((test = hash & HighBits) != 0)
                hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
        }
        return hash;
    }


    /*ELFHash*/
    public long ELFHash(String str) {
        long hash = 0;
        long x = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if(( x = hash & 0xF0000000L) != 0)
                hash ^= ( x >> 24);
            hash &= ~x;
        }
        return hash;
    }


    /*BKDRHash*/
    public long BKDRHash(String str) {
        long seed = 131;//31131131313131131313etc..
        long hash = 0;
        for(int i = 0; i < str.length(); i++)
            hash = (hash * seed) + str.charAt(i);
        return hash;
    }


    /*SDBMHash*/
    public long SDBMHash(String str) {
        long hash = 0;
        for(int i = 0; i < str.length(); i++)
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        return hash;
    }


    /*DJBHash*/
    public long DJBHash(String str) {
        long hash = 5381;
        for(int i = 0; i < str.length(); i++)
            hash = ((hash << 5) + hash) + str.charAt(i);
        return hash;
    }


    /*DEKHash*/
    public long DEKHash(String str) {
        long hash = str.length();
        for(int i = 0; i < str.length(); i++)
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        return hash;
    }


    /*BPHash*/
    public long BPHash(String str) {
        long hash=0;
        for(int i = 0;i < str.length(); i++)
            hash = hash << 7 ^ str.charAt(i);
        return hash;
    }


    /*FNVHash*/
    public long FNVHash(String str) {
        long fnv_prime = 0x811C9DC5;
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash *= fnv_prime;
            hash ^= str.charAt(i);
        }
        return hash;
    }


    /*APHash*/
    long APHash(String str) {
        long hash = 0xAAAAAAAA;
        for(int i = 0; i < str.length(); i++) {
            if((i & 1) == 0)
                hash ^=((hash << 7) ^ str.charAt(i) ^ (hash >> 3));
            else
                hash ^= (~((hash << 11) ^ str.charAt(i) ^ (hash >> 5)));
        }
        return hash;
    }
}