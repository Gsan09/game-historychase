package com.historychase.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Pager<T> {

    private T[] items;

    private int maxVisible;

    private int selectedPage;
    private List<PageListener> listeners;

    public Pager(T[] items){
        this(items,4);
    }

    public Pager(T[] items,int maxVisible){
        this.items = items;
        this.maxVisible = maxVisible;
        this.selectedPage = 1;
    }

    public int getPageCount(){
        return items.length / maxVisible + (items.length%maxVisible > 0?1:0);
    }

    public int getMaxVisible(){
        return  this.maxVisible;
    }

    public boolean hasNextPage(){
        return selectedPage < getPageCount();
    }

    public boolean hasPrevPage(){
        return selectedPage > 1;
    }

    public Pager next(){
        return hasNextPage()? gotoPage(selectedPage + 1):this;
    }

    public Pager prev(){
        return hasPrevPage()? gotoPage(selectedPage - 1):this;
    }

    public Pager gotoPage(int page){
        if(page < 1 && page > getPageCount())
            throw new IndexOutOfBoundsException("Page not found");

        int prev = selectedPage;
        selectedPage = page;
        if(listeners != null)
            for (PageListener listener : listeners) {
                Object[] items =  getPageItems(page);
                listener.onSwitchPage(selectedPage, prev,items);
            }
        return this;
    }

    public Pager addListener(PageListener listener){
        if(listeners == null)
            listeners = new ArrayList<PageListener>();
        listeners.add(listener);
        return this;
    }

    public Pager removeListener(PageListener listener){
        if(listeners == null || !listeners.contains(listener))
            throw new IndexOutOfBoundsException("Listener not found");
        listeners.remove(listener);
        return this;
    }

    public <E extends T> E[] getPageItems(int page,Class<E> type){
        if(page < 0 || page > getPageCount())
            throw new IndexOutOfBoundsException("Page not found");
        E visibleItems[] = (E[])Array.newInstance(type,maxVisible);
        for(int i=0;i<maxVisible;i++){
            int itemIndex = i+(maxVisible*(page-1));
            visibleItems[i] = itemIndex<items.length?(E)items[itemIndex]:null;
        }
        return visibleItems;
    }

    public <E extends T> E[] getPageItems(Class<E> type){
        return getPageItems(selectedPage,type);
    }

    public Object[] getPageItems(int page){
        if(page < 0 || page > getPageCount())
            throw new IndexOutOfBoundsException("Page not found");
        Object visibleItems[] = new Object[maxVisible];
        for(int i=0;i<maxVisible;i++){
            int itemIndex = i+(maxVisible*(page-1));
            visibleItems[i] = itemIndex<items.length?items[itemIndex]:null;
        }
        return visibleItems;
    }

    public Object[] getPageItems(){
        return getPageItems(selectedPage);
    }

    public interface PageListener{
        public abstract void onSwitchPage(int page,int prev,Object[] items);
    }
}
