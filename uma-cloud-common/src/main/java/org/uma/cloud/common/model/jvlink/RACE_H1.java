package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseJvLink;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_H1 extends BaseJvLink {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer entryCount;
    private Integer starterCount;
    private Integer saleFlagWin;
    private Integer saleFlagShow;
    private Integer saleFlagBracketQ;
    private Integer saleFlagQuinella;
    private Integer saleFlagQuinellaPlace;
    private Integer saleFlagExacta;
    private Integer saleFlagTrio;
    private Integer showCashKey;
    private List<Integer> restoreHorseNoItems;
    private List<Integer> restoreBracketItems;
    private List<Integer> restoreSameBracketItems;
    private List<Vote> voteCountWins;
    private List<Vote> voteCountShows;
    private List<Vote> voteCountBracketQs;
    private List<VotePair> voteCountQuinellas;
    private List<VotePair> voteCountQuinellaPlaces;
    private List<VotePair> voteCountExactas;
    private List<VoteTriplet> voteCountTrios;
    private Long voteCountTotalWin;
    private Long voteCountTotalShow;
    private Long voteCountTotalBracketQ;
    private Long voteCountTotalQuinella;
    private Long voteCountTotalQuinellaPlace;
    private Long voteCountTotalExacta;
    private Long voteCountTotalTrio;
    private Long restoreVoteCountTotalWin;
    private Long restoreVoteCountTotalShow;
    private Long restoreVoteCountTotalBracketQ;
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