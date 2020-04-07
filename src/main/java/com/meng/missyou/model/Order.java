package com.meng.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.meng.missyou.dto.OrderAddressDTO;
import com.meng.missyou.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO orderAddressDTO = GenericAndJson.jsonToObject(snapAddress, new TypeReference<OrderAddressDTO>() {
        });
        return orderAddressDTO;
    }

    public void setSnapAddress(OrderAddressDTO orderAddressDTO) {
        this.snapAddress = GenericAndJson.objectToJson(orderAddressDTO);
    }

    public OrderSku getSnapItems() {
        if (this.snapItems == null) {
            return null;
        }
        OrderSku orderSku = GenericAndJson.jsonToObject(snapItems, new TypeReference<OrderSku>() {
        });
        return orderSku;
    }

    public void setSnapItems(OrderSku orderSku) {
        this.snapItems = GenericAndJson.objectToJson(orderSku);
    }

}
