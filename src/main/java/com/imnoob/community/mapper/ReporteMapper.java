package com.imnoob.community.mapper;

import com.imnoob.community.model.Reporter;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteMapper {

    int insert(Reporter reporte);
}
