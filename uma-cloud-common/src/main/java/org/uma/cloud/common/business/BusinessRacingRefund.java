package org.uma.cloud.common.business;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@TypeDefs({
        @TypeDef(name = "list", typeClass = ListArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table
public class BusinessRacingRefund implements Serializable {

    /**
     * レースID
     */
    @Id
    @Column(length = 16)
    private String raceId;

    /**
     * データ区分
     * "2" 出馬表（未確定）
     * "6" 速報成績（確定）
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * 返還馬番
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreHorseNoItems;

    /**
     * 単勝払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundWins;

    /**
     * 複勝払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundShows;

    /**
     * 枠連払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundBracketQs;

    /**
     * 馬連払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundQuinellas;

    /**
     * ワイド払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundQuinellaPlaces;

    /**
     * 馬単払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundExactas;

    /**
     * 3連複払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundTriplet> refundTrios;

    /**
     * 3連単払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundTriplet> refundTrifectas;


    @Data
    public static class refund {

        private String horseNo;

        private Integer refundMoney;

        private Integer betRank;
    }

    @Data
    public static class refundPair {

        private Pair<String, String> pairNo;

        private Integer refundMoney;

        private Integer betRank;
    }

    @Data
    public static class refundTriplet {

        private Triplet<String, String, String> tripletNo;

        private Integer refundMoney;

        private Integer betRank;
    }

}
