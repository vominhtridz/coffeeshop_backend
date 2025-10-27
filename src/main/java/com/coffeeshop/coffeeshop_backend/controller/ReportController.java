package com.coffeeshop.coffeeshop_backend.controller;

import com.coffeeshop.coffeeshop_backend.payload.response.DailyReportResponse;
import com.coffeeshop.coffeeshop_backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reports")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * API Thống kê tổng doanh thu và tổng khách trong ngày
     */
    @GetMapping("/daily")
    public DailyReportResponse getDailyReport() {
        return reportService.getDailyReport();
    }
}