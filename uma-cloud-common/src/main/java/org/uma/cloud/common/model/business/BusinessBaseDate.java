package org.uma.cloud.common.model.business;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "business_base_date")
public class BusinessBaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * データ取得 基準日
     */
    @Column(nullable = false)
    private Long baseDate;


    /**
     * 必要なカラム増えるかも！
     */
}
