package com.qhit.UI;

@SuppressWarnings("unused")
public class Paging {

    private int total;  //总数据条数
    private int pageCount;  //总页数
    private int currentPage = 1;  //当前页
    private int pageSize = 10;  //每页条数
    private int fromIndex = 0;  //检索起始点

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    private void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public void change(Paging paging) {
        //如果每页条数不大于0，重置为10
        if (paging.getPageSize() <= 0) paging.setPageSize(10);
        //计算总页数并赋值
        paging.setPageCount((int) Math.ceil(Double.valueOf(paging.getTotal() + "") / paging.getPageSize()));
        //如果当前页不大于0，重置为1
        if (paging.getCurrentPage() <= 0) paging.setCurrentPage(1);
        //如果当前页大于总页数，重置为最后一页
        if (paging.getCurrentPage() > paging.getPageCount()) paging.setCurrentPage(paging.getPageCount());
        //因为SQL语句中limit后不能跟数学计算，所以在此计算检索的起始号
        paging.setFromIndex((paging.getCurrentPage() - 1) * paging.getPageSize());
    }

}
