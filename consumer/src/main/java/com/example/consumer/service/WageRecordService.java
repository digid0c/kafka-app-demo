package com.example.consumer.service;

import com.example.consumer.config.WageProperties;
import com.example.consumer.persistence.entity.Employee;
import com.example.consumer.persistence.entity.WageRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Service
@Transactional
@RequiredArgsConstructor
public class WageRecordService {

    private static final Integer TAX_PERCENT_MIN_VALUE = 0;
    private static final Integer TAX_PERCENT_MAX_VALUE = 100;
    private static final int DECIMAL_SCALE = 2;

    private final WageProperties wageProperties;

    /**
     * Makes necessary wage calculations before creating actual wage record entity. Validates tax percent provided.
     * @param employee employee that wage record belongs to
     * @param baseWage base wage to perform calculations with
     * @return new wage record created
     * @throws IllegalArgumentException in case tax percent is not defined or out of range
     */
    public WageRecord create(Employee employee, BigDecimal baseWage) {
        Integer taxPercent = wageProperties.getTaxPercent();
        if (taxPercent == null || taxPercent < TAX_PERCENT_MIN_VALUE || taxPercent > TAX_PERCENT_MAX_VALUE) {
            throw new IllegalArgumentException("Tax percent is not defined or is out of range");
        }

        baseWage = baseWage.setScale(DECIMAL_SCALE, HALF_UP);

        BigDecimal taxAddedWage = baseWage.multiply(valueOf(taxPercent/100.0));
        taxAddedWage = taxAddedWage.setScale(DECIMAL_SCALE, HALF_UP);

        BigDecimal totalWage = baseWage.add(taxAddedWage);

        WageRecord wageRecord = new WageRecord();
        wageRecord.setEmployee(employee);
        wageRecord.setBaseWage(baseWage);
        wageRecord.setTaxPercent(taxPercent);
        wageRecord.setTaxAddedWage(taxAddedWage);
        wageRecord.setTotalWage(totalWage);
        wageRecord.setActive(TRUE);
        wageRecord.setCreatedAt(employee.getLastModifiedAt());
        wageRecord.setLastModifiedAt(employee.getLastModifiedAt());

        return wageRecord;
    }
}
