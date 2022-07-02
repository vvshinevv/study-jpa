package com.study.jpa.study.jpa.lock.mapper;

import com.study.jpa.study.jpa.lock.domain.Point;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {
    Point findPointByIdForUpdate(Long id);

    Point findPointById(Long id);

    int persistPoint(Point point);

    int updatePoint(Point point);
}
