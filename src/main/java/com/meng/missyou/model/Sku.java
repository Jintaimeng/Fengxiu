/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-02-24 20:32
 */
package com.meng.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.meng.missyou.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
//@TypeDef(name="json", typeClass = JsonStringType.class)
public class Sku extends BaseEntity {
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private Long categoryId;
    private Long rootCategoryId;
    //@Convert(converter = ListAndJson.class)
    private String specs;
    private String code;
    private Long stock;
//    @Convert(converter = MapAndJson.class)
//    private Map<String, Object> test;

    public BigDecimal getActualPrice() {
        return discountPrice == null ? this.price : this.discountPrice;
    }

    public List<Spec> getSpecs() {
        //return specs;
        if (this.specs == null) {
            return Collections.emptyList();
        }
        return GenericAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public void setSpecs(List<Spec> specs) {
        //this.specs = specs;
        if (specs == null) {
            return;
        }
        this.specs = GenericAndJson.objectToJson(specs);
    }
}
