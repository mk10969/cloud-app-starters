package org.uma.cloud.common.model;


import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.H1}
 */
@Getter
@Entity
@Table
public class VoteCount extends BaseModel {

    /**
     * {@link RacingDetails.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;

    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    private Integer entryCount;

    private Integer starterCount;

    private Integer saleFlagWin;

    private Integer saleFlagPlace;

    private Integer saleFlagBracketQuinella;

    private Integer saleFlagQuinella;

    private Integer saleFlagQuinellaPlace;

    private Integer saleFlagExacta;

    private Integer saleFlagTrio;

    private Integer placeCashKey;

    // 返還
    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreHorseNoItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreBracketItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreSameBracketItems;

    /**
     * 馬券種ごとの票数
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Vote> voteCountWins;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Vote> voteCountPlaces;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Vote> voteCountBracketQuinellas;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<VotePair> voteCountQuinellas;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<VotePair> voteCountQuinellaPlaces;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<VotePair> voteCountExactas;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<VoteTriplet> voteCountTrios;

    private Long voteCountTotalWin;

    private Long voteCountTotalPlace;

    private Long voteCountTotalBracketQuinella;

    private Long voteCountTotalQuinella;

    private Long voteCountTotalQuinellaPlace;

    private Long voteCountTotalExacta;

    private Long voteCountTotalTrio;

    private Long restoreVoteCountTotalWin;

    private Long restoreVoteCountTotalPlace;

    private Long restoreVoteCountTotalBracketQuinella;

    private Long restoreVoteCountTotalQuinella;

    private Long restoreVoteCountTotalQuinellaPlace;

    private Long restoreVoteCountTotalExacta;

    private Long restoreVoteCountTotalTrio;


    @Getter
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

    @Getter
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

    @Getter
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
