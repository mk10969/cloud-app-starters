package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.repository.business.BusinessRacingHorseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessRacingHorseService {

    @Autowired
    private BusinessRacingHorseRepository repository;

    /**
     * @param raceId レースID
     * @return 最大 18個(出走可能競走馬数)
     */
    public List<BusinessRacingHorse> findAllByRaceId(String raceId) {
        return repository.findByRaceId(raceId);
    }

    /**
     * イベント通知 => Weight => 馬体重のみ更新
     *
     * @param weight
     */
    public void updateWeight(Weight weight) {
        List<BusinessRacingHorse> updatingRacingHorse = weight.getHorseWeights()
                .stream()
                .flatMap(horseWeight -> this.findAllByRaceId(weight.getRaceId())
                        .stream()
                        // 同じhorseNoのものだけ更新。
                        .filter(racingHorse -> racingHorse.getHorseNo().equals(horseWeight.getHorseNo()))
                        .peek(racingHorse -> {
                            racingHorse.setHorseWeight(horseWeight.getHorseWeight());
                            racingHorse.setChangeSign(horseWeight.getChangeSign());
                            racingHorse.setChangeAmount(horseWeight.getChangeAmount());
                        }))
                .collect(Collectors.toUnmodifiableList());
        // update
        // TODO : Transactionalされる？
        this.updateAll(updatingRacingHorse);
    }

    /**
     * イベント通知 => jockeyChange => 騎手のみ更新
     *
     * @param jockeyChange
     */
    public void updateJockeyChange(JockeyChange jockeyChange) {
        BusinessRacingHorse.CompositeId id = createId(jockeyChange.getRaceId(), jockeyChange.getHorseNo());
        BusinessRacingHorse racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        racingHorse.setJockeyNameShort(jockeyChange.getJockeyNameAfter());
        racingHorse.setLoadWeight(jockeyChange.getLoadWeightAfter());
        racingHorse.setJockeyApprentice(jockeyChange.getJockeyApprenticeCdAfter());

        this.update(racingHorse);
    }

    /**
     * イベント通知 => avoid => 出走取消 or 競走除外 更新
     *
     * @param avoid
     */
    public void updateAvoid(Avoid avoid) {
        BusinessRacingHorse.CompositeId id = createId(avoid.getRaceId(), avoid.getHorseNo());
        BusinessRacingHorse racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        // ここセンスない。。
        racingHorse.setExclude(Integer.parseInt(avoid.getDataDiv()));
        racingHorse.setExcludeReason(avoid.getReason());

        this.update(racingHorse);
    }


    @Transactional
    public void update(BusinessRacingHorse businessRacingHorse) {
        repository.save(businessRacingHorse);
    }

    @Transactional
    public void updateAll(List<BusinessRacingHorse> model) {
        repository.saveAll(model);
    }


    private BusinessRacingHorse.CompositeId createId(String raceId, String horseNo) {
        BusinessRacingHorse.CompositeId id = new BusinessRacingHorse.CompositeId();
        id.setRaceId(raceId);
        id.setHorseNo(horseNo);
        return id;
    }

}
