package com.meng.missyou.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagingVO<T> {
    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    public PagingVO(PagingVO<T> pageT) {
        this.initPageParameters(pageT);
        this.items = pageT.getItems();
    }

    void initPageParameters(PagingVO<T> pageT) {
        this.total = pageT.getTotal();
        this.count = pageT.getCount();
        this.page = pageT.getPage();
        this.totalPage = pageT.getTotalPage();
    }
}
