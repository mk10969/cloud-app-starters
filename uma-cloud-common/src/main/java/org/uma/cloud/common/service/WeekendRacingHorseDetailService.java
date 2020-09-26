package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.WeekendRacingHorseDetail;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.repository.WeekendRacingHorseDetailRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeekendRacingHorseDetailService {

    @Autowired
    private WeekendRacingHorseDetailRepository repository;

    /**
     * @param raceId レースID
     * @return 最大 18個(出走可能競走馬数)
     */
    public List<WeekendRacingHorseDetail> findAllByRaceId(String raceId) {
        return repository.findByRaceId(raceId);
    }

    /**
     * イベント通知 => Weight => 馬体重のみ更新
     *
     * @param weight 馬体重変更
     */
    public List<WeekendRacingHorseDetail> updateAllWeight(Weight weight) {
        List<WeekendRacingHorseDetail> updatingRacingHorse = weight.getHorseWeights()
                .stream()
                .flatMap(horseWeight -> this.findAllByRaceId(weight.getRaceId())
                        .stream()
                        // 同じhorseNoのものだけ更新。
                        .filter(racingHorse -> racingHorse.getHorseNo().equals(horseWeight.getHorseNo()))
                        .peek(racingHorse -> {
                            racingHorse.setHorseWeight(horseWeight.getHorseWeight());
                            // 馬体重増減
                            if ("+".equals(horseWeight.getChangeSign())) {
                                racingHorse.setHorseWeightGainOrLoss(horseWeight.getChangeAmount());
                            } else if ("-".equals(horseWeight.getChangeSign())) {
                                racingHorse.setHorseWeightGainOrLoss(-horseWeight.getChangeAmount());
                            } else if ("".equals(horseWeight.getChangeSign())) {
                                if (horseWeight.getChangeAmount() == null) {
                                    // nullなら、０を入れる。新馬戦とかの場合、nullになる。（前回出走していないから）
                                    racingHorse.setHorseWeightGainOrLoss(0);
                                } else {
                                    // 計測不可能フラグの９９９が入る or 前走と増減なしの０が入る。
                                    racingHorse.setHorseWeightGainOrLoss(horseWeight.getChangeAmount());
                                }
                            }
                        }))
                .collect(Collectors.toUnmodifiableList());

        return this.updateAll(updatingRacingHorse);
    }

    /**
     * イベント通知 => jockeyChange => 騎手のみ更新
     *
     * @param jockeyChange 騎手変更
     */
    public WeekendRacingHorseDetail updateJockeyChange(JockeyChange jockeyChange) {
        WeekendRacingHorseDetail.CompositeId id = createId(jockeyChange.getRaceId(), jockeyChange.getHorseNo());
        WeekendRacingHorseDetail racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        racingHorse.setJockeyNameShort(jockeyChange.getJockeyNameAfter());
        racingHorse.setLoadWeight(jockeyChange.getLoadWeightAfter());
        racingHorse.setJockeyApprentice(jockeyChange.getJockeyApprenticeCdAfter());

        return this.update(racingHorse);
    }

    /**
     * イベント通知 => avoid => 出走取消 or 競走除外 のみ更新
     *
     * @param avoid 出走取消 or 競走除外
     */
    public WeekendRacingHorseDetail updateAvoid(Avoid avoid) {
        WeekendRacingHorseDetail.CompositeId id = createId(avoid.getRaceId(), avoid.getHorseNo());
        WeekendRacingHorseDetail racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        // ここセンスない。。
        racingHorse.setExclude(Integer.parseInt(avoid.getDataDiv()));
        racingHorse.setExcludeReason(avoid.getReason());

        return this.update(racingHorse);
    }


    @Transactional
    public WeekendRacingHorseDetail update(WeekendRacingHorseDetail weekendRacingHorseDetail) {
        return repository.save(weekendRacingHorseDetail);
    }

    @Transactional
    public List<WeekendRacingHorseDetail> updateAll(List<WeekendRacingHorseDetail> model) {
        return repository.saveAll(model);
    }


    private static WeekendRacingHorseDetail.CompositeId createId(String raceId, String horseNo) {
        WeekendRacingHorseDetail.CompositeId id = new WeekendRacingHorseDetail.CompositeId();
        id.setRaceId(raceId);
        id.setHorseNo(horseNo);
        return id;
    }

}
