package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

// TODO: 式別ごとに分ける
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RacingVote extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    /**
     * {@link RacingDetail}  重複
     */
//    @Column(nullable = false)
//    private LocalDate holdingDate;
//
//    @Column(length = 6, nullable = false)
//    private RaceCourseCode courseCd;
//
//    @Column(nullable = false)
//    private Integer holdingNo;
//
//    @Column(nullable = false)
//    private Integer holdingDay;
//
//    @Column(nullable = false)
//    private Integer raceNo;

    @Column(nullable = false)
    private Integer entryCount;

    @Column(nullable = false)
    private Integer starterCount;

    @Column(nullable = false)
    private Integer saleFlagWin;

    @Column(nullable = false)
    private Integer saleFlagShow;

    @Column(nullable = false)
    private Integer saleFlagBracketQ;

    @Column(nullable = false)
    private Integer saleFlagQuinella;

    @Column(nullable = false)
    private Integer saleFlagQuinellaPlace;

    @Column(nullable = false)
    private Integer saleFlagExacta;

    @Column(nullable = false)
    private Integer saleFlagTrio;

    @Column(nullable = false)
    private Integer showCashKey;

    // 返還
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreHorseNoItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreBracketItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreSameBracketItems;

    /**
     * 馬券種ごとの票数
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Vote> voteCountWins;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Vote> voteCountShows;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Vote> voteCountBracketQs;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<VotePair> voteCountQuinellas;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<VotePair> voteCountQuinellaPlaces;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<VotePair> voteCountExactas;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<VoteTriplet> voteCountTrios;

    @Column(nullable = false)
    private Long voteCountTotalWin;

    /**
     * Nullable
     */
    private Long voteCountTotalShow;

    @Column(nullable = false)
    private Long voteCountTotalBracketQ;

    @Column(nullable = false)
    private Long voteCountTotalQuinella;

    @Column(nullable = false)
    private Long voteCountTotalQuinellaPlace;

    @Column(nullable = false)
    private Long voteCountTotalExacta;

    @Column(nullable = false)
    private Long voteCountTotalTrio;

    /**
     * Nullable
     */
    private Long restoreVoteCountTotalWin;

    @Column(nullable = false)
    private Long restoreVoteCountTotalShow;

    @Column(nullable = false)
    private Long restoreVoteCountTotalBracketQ;

    /**
     * Nullable
     */
    private Long restoreVoteCountTotalQuinella;

    /**
     * Nullable
     */
    private Long restoreVoteCountTotalQuinellaPlace;

    /**
     * Nullable
     */
    private Long restoreVoteCountTotalExacta;

    /**
     * Nullable
     */
    private Long restoreVoteCountTotalTrio;


    @Data
    public static class Vote {

        /**
         * 馬番なので、String型を使う
         */
        private String horseNo;

        /**
         * ALL 0   :発売前取消し or 発売票数なし => 0 に変換される。
         * スペース :登録なし                   => null に変換される。
         */
        private Long voteCount;

        /**
         * スペース :登録なし   => null に変換する。
         * '---'   :発売前取消 => -100 に変換する。
         * '***'   :発売後取消 => -999 に変換する。
         */
        private Integer betRank;
    }

    @Data
    public static class VotePair {

        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;

        /**
         * ALL 0   :発売前取消し or 発売票数なし => 0 に変換される。
         * スペース :登録なし                   => null に変換される。
         */
        private Long voteCount;

        /**
         * スペース :登録なし   => null に変換する。
         * '---'   :発売前取消 => -100 に変換する。
         * '***'   :発売後取消 => -999 に変換する。
         */
        private Integer betRank;
    }

    @Data
    public static class VoteTriplet {

        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> tripletNo;

        /**
         * ALL 0   :発売前取消し or 発売票数なし => 0 に変換される。
         * スペース :登録なし                   => null に変換される。
         */
        private Long voteCount;

        /**
         * スペース :登録なし   => null に変換する。
         * '---'   :発売前取消 => -100 に変換する。
         * '***'   :発売後取消 => -999 に変換する。
         */
        private Integer betRank;
    }

}
