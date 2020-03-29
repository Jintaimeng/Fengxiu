package com.meng.missyou.model;

import com.meng.missyou.util.MapAndJson;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    private Long id;
    private String openid;
    private String nickname;
    private Long unifyUid;
    private String email;
    private String password;
    private String mobile;
    //private String group;
    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

}
