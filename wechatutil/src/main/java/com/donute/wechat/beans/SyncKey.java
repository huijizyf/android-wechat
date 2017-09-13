package com.donute.wechat.beans;

import java.util.List;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class SyncKey {
    private int Count;
    private List<KeyValuePair> List;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public java.util.List<KeyValuePair> getList() {
        return List;
    }

    public void setList(java.util.List<KeyValuePair> list) {
        List = list;
    }

    public static class KeyValuePair{
        private int Key;
        private int Val;

        public int getKey() {
            return Key;
        }

        public void setKey(int key) {
            Key = key;
        }

        public int getVal() {
            return Val;
        }

        public void setVal(int val) {
            Val = val;
        }
    }
}
