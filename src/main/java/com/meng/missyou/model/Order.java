package com.meng.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.meng.missyou.dto.OrderAddressDTO;
import com.meng.missyou.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private Date expiredTime;
    private Date placedTime;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO orderAddressDTO = GenericAndJson.jsonToObject(this.snapAddress, new TypeReference<OrderAddressDTO>() {
        });
        return orderAddressDTO;
    }

    public void setSnapAddress(OrderAddressDTO orderAddressDTO) {
        this.snapAddress = GenericAndJson.objectToJson(orderAddressDTO);
    }

    public List<OrderSku> getSnapItems() {
        if (this.snapItems == null) {
            return null;
        }
        List<OrderSku> orderSkuList = GenericAndJson.jsonToObject(this.snapItems, new TypeReference<List<OrderSku>>() {
        });
        return orderSkuList;
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

}
