package com.sunny.hospital.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙宇豪 分頁工具類
 * */
public class Pager implements Serializable{
    private long rowCount;//总条数
    private int rowPerPage;//每页显示条数
    private int currentPage;//当前页
    private String username;//用户名
    private int pageCount;//总页数
    private int pagesPerDisplay;//每页显示条数
    private int displayCount;//显示条数

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPagesPerDisplay() {
        return pagesPerDisplay;
    }

    public void setPagesPerDisplay(int pagesPerDisplay) {
        this.pagesPerDisplay = pagesPerDisplay;
    }

    private Integer[] displayPages;

    public Pager(long rowCount, int currentPage) {
        this(rowCount, 8, currentPage, 8);
    }

    public Pager(long rowCount, int rowPerPage, int currentPage) {
        this(rowCount, rowPerPage, currentPage, 8);
    }

    public Pager(long rowCount, int rowPerPage, int currentPage,
                 int pagesPerDisplay) {
        super();
        this.rowCount = rowCount;
        this.rowPerPage = rowPerPage;
        this.currentPage = currentPage;
        this.pagesPerDisplay = pagesPerDisplay;
        this.pageCount = (int) Math.ceil((float) rowCount / rowPerPage);
        this.displayCount = (int) Math.ceil((float) this.pageCount
                / pagesPerDisplay);
        initDisplayPages();
    }

    public long getRowCount() {
        return rowCount;
    }

    public int getRowPerPage() {
        return rowPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getFirstRow() {
        return (this.currentPage - 1) * this.rowPerPage;
    }

    public boolean isHavePrevPage() {
        return Math.ceil((float) this.currentPage / pagesPerDisplay) > 1;
    }

    public boolean isHaveNextPage() {
        return Math.ceil((float) this.currentPage / pagesPerDisplay) < displayCount;
    }

    public int getNextDisplayFirstPage() {
        return displayPages[displayPages.length - 1].intValue() + 1;
    }

    public int getPrevDisplayLastPage() {
        return displayPages[0].intValue() - 1;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Integer[] getDisplayPages() {
        return this.displayPages;
    }

    public void initDisplayPages() {
        int currentDisplayNumber = (int) Math.ceil((float) this.currentPage
                / pagesPerDisplay);
        List<Integer> pages = new ArrayList<Integer>();
        for (int i = ((currentDisplayNumber - 1) * pagesPerDisplay + 1); i <= currentDisplayNumber
                * pagesPerDisplay; i++) {
            if (((i - 1) * this.rowPerPage + 1) <= this.rowCount)
                pages.add(i);
        }
        this.displayPages = (Integer[]) pages.toArray(new Integer[0]);
    }
}